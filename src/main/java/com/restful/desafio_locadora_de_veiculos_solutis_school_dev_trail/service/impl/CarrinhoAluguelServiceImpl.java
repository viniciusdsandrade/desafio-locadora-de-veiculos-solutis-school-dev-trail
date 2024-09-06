package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.impl;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosAtualizacaoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.*;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.exception.CarroIndisponivelException;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository.*;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.CarrinhoAluguelService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carrinhoAluguel.DadosListagemCarrinhoAluguel;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ApoliceSeguro.calcularValorTotalApoliceSeguro;
import static com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusAluguel.INCOMPLETO;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.slf4j.LoggerFactory.getLogger;

@Service("carrinhoAluguelService")
public class CarrinhoAluguelServiceImpl implements CarrinhoAluguelService {

    private static final Logger log = getLogger(CarrinhoAluguelServiceImpl.class);

    @Schema(description = "Serviço de carrinho de aluguel")
    private final CarrinhoAluguelRepository carrinhoAluguelRepository;

    @Schema(description = "Repositório de aluguel")
    private final AluguelRepository aluguelRepository;

    @Schema(description = "Repositório de motorista")
    private final MotoristaRepository motoristaRepository;

    @Schema(description = "Construtor de CarrinhoAluguelServiceImpl")
    private final CarroRepository carroRepository;

    @Schema(description = "Repositório de apólice de seguro")
    private final ApoliceSeguroRepository apoliceSeguroRepository;

    public CarrinhoAluguelServiceImpl(CarrinhoAluguelRepository carrinhoAluguelRepository,
                                      AluguelRepository aluguelRepository,
                                      MotoristaRepository motoristaRepository,
                                      CarroRepository carroRepository,
                                      ApoliceSeguroRepository apoliceSeguroRepository) {
        this.carrinhoAluguelRepository = carrinhoAluguelRepository;
        this.aluguelRepository = aluguelRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
        this.apoliceSeguroRepository = apoliceSeguroRepository;
    }

    @Override
    @Schema(description = "Lista os carrinhos de aluguel")
    public Page<DadosListagemCarrinhoAluguel> listarCarrinhosAluguel(Pageable pageable) {
        log.info("Listando carrinhos de aluguel");
        Page<CarrinhoAluguel> carrinhos = carrinhoAluguelRepository.findAll(pageable);
        log.info("Carrinhos de aluguel listados com sucesso");
        return carrinhos.map(DadosListagemCarrinhoAluguel::new);
    }

    @Override
    @Schema(description = "Busca um carrinho de aluguel pelo id")
    public CarrinhoAluguel buscarCarrinhoAluguelPorId(Long id) {
        log.info("Buscando carrinho de aluguel por id: {}", id);
        CarrinhoAluguel carrinho = existeCarrinhoAluguelPeloId(id);
        log.info("Carrinho de aluguel encontrado com sucesso");
        return carrinho;
    }

    @Override
    @Transactional
    @Schema(description = "Adiciona um aluguel ao carrinho")
    public CarrinhoAluguel criarCarrinhoAluguelAndAdicionarAluguel(@Valid DadosCadastroAluguel dadosAluguel) {
        Motorista motorista = buscarMotoristaPorEmail(dadosAluguel.emailMotorista());
        Carro carro = buscarCarroDisponivelPorId(dadosAluguel.idCarro());
        ApoliceSeguro apoliceSeguro = buscarApoliceSeguro(dadosAluguel.apoliceSeguro());

        BigDecimal valorTotalInicial = calcularValorTotalInicial(
                dadosAluguel.dataRetirada(),
                dadosAluguel.dataDevolucaoPrevista(),
                carro.getValorDiaria(),
                apoliceSeguro
        );

        Aluguel aluguel = criarAluguel(dadosAluguel, valorTotalInicial, motorista, apoliceSeguro, carro);
        aluguelRepository.save(aluguel);

        CarrinhoAluguel carrinho = criarCarrinhoAluguel(motorista, carro, aluguel);
        carrinhoAluguelRepository.save(carrinho);

        return carrinho;
    }

    @Override
    @Transactional
    @Schema(description = "Adiciona um aluguel a um carrinho existente")
    public CarrinhoAluguel adicionarAluguel(Long idCarrinho, @Valid DadosCadastroAluguel dadosAluguel) {
        CarrinhoAluguel carrinho = existeCarrinhoAluguelPeloId(idCarrinho); // Verifica se o carrinho existe
        Motorista motorista = buscarMotoristaPorEmail(dadosAluguel.emailMotorista());
        Carro carro = buscarCarroDisponivelPorId(dadosAluguel.idCarro());
        ApoliceSeguro apoliceSeguro = buscarApoliceSeguro(dadosAluguel.apoliceSeguro());

        BigDecimal valorTotalInicial = calcularValorTotalInicial(
                dadosAluguel.dataRetirada(),
                dadosAluguel.dataDevolucaoPrevista(),
                carro.getValorDiaria(),
                apoliceSeguro
        );

        Aluguel aluguel = criarAluguel(dadosAluguel, valorTotalInicial, motorista, apoliceSeguro, carro);
        aluguelRepository.save(aluguel);

        carrinho.adicionarAluguel(aluguel); // Adiciona o aluguel ao carrinho existente
        return carrinhoAluguelRepository.save(carrinho); // Salva o carrinho atualizado
    }

    @Override
    @Transactional
    @Schema(description = "Atualiza um aluguel no carrinho")
    public CarrinhoAluguel atualizarAluguel(Long idCarrinho, @Valid DadosAtualizacaoAluguel dadosAluguelAtualizados) {
        CarrinhoAluguel carrinho = existeCarrinhoAluguelPeloId(idCarrinho);
        Aluguel aluguelParaAtualizar = existeAluguelNoCarrinhoPeloId(dadosAluguelAtualizados.idAluguel());

        boolean dataRetiradaAlterada = !aluguelParaAtualizar.getDataRetirada().equals(dadosAluguelAtualizados.dataRetirada());
        boolean dataDevolucaoPrevistaAlterada = !aluguelParaAtualizar.getDataDevolucaoPrevista().equals(dadosAluguelAtualizados.dataDevolucaoPrevista());

        boolean recalcularValorTotal = dataRetiradaAlterada || dataDevolucaoPrevistaAlterada;

        aluguelParaAtualizar.atualizar(dadosAluguelAtualizados);

        // Recalcula o valor total do aluguel somente se as datas de retirada ou devolução prevista forem alteradas
        if (recalcularValorTotal) {
            BigDecimal novoValorTotal = calcularValorTotalInicial(
                    aluguelParaAtualizar.getDataRetirada(),
                    aluguelParaAtualizar.getDataDevolucaoPrevista(),
                    aluguelParaAtualizar.getCarro().getValorDiaria(),
                    aluguelParaAtualizar.getApoliceSeguro()
            );
            aluguelParaAtualizar.setValorTotalInicial(novoValorTotal);
            aluguelParaAtualizar.setValorTotalFinal(novoValorTotal);
        }

        aluguelRepository.save(aluguelParaAtualizar); // Salva as alterações no aluguel

        return carrinho; // Retorna o carrinho (não precisa ser salvo novamente)
    }

    @Override
    @Transactional
    @Schema(description = "Remove um aluguel do carrinho")
    public CarrinhoAluguel removerAluguel(Long idCarrinho, Long idAluguel) {
        CarrinhoAluguel carrinho = existeCarrinhoAluguelPeloId(idCarrinho); // Use o idCarrinho para buscar o carrinho
        Aluguel aluguelParaRemover = existeAluguelNoCarrinhoPeloId(idAluguel); // Use o idAluguel para buscar o aluguel

        carrinho.removerAluguel(aluguelParaRemover); // Remove o aluguel do carrinho
        aluguelRepository.delete(aluguelParaRemover); // Remove o aluguel do repositório
        carrinhoAluguelRepository.save(carrinho); // Salva o carrinho atualizado (opcional)

        return carrinho; // Retorna o carrinho atualizado
    }

    @Override
    @Transactional
    @Schema(description = "Remove um carrinho de aluguel")
    public void excluirCarrinhoAluguel(Long idCarrinho) {
        CarrinhoAluguel carrinhoParaRemover = existeCarrinhoAluguelPeloId(idCarrinho);

        // Remova os aluguéis associados ao carrinho (opcional, dependendo da sua lógica)
        aluguelRepository.deleteAll(carrinhoParaRemover.getAlugueis());

        carrinhoAluguelRepository.delete(carrinhoParaRemover); // Remove o carrinho do repositório
    }

    @Schema(description = "Verifica se existe um aluguel no carrinho pelo id")
    private Aluguel existeAluguelNoCarrinhoPeloId(Long idAluguel) {
        return aluguelRepository.findById(idAluguel)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado no carrinho"));
    }

    @Schema(description = "Verifica se existe um carrinho de aluguel pelo id")
    private CarrinhoAluguel existeCarrinhoAluguelPeloId(Long id) {
        return carrinhoAluguelRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("Carrinho de aluguel não encontrado");
                            return new EntityNotFoundException("Carrinho de aluguel não encontrado");
                        }
                );
    }

    @Schema(description = "Busca um motorista pelo e-mail")
    private Motorista buscarMotoristaPorEmail(String emailMotorista) {
        return motoristaRepository.findByEmail(emailMotorista)
                .orElseThrow(
                        () -> {
                            log.error("Motorista não encontrado");
                            return new EntityNotFoundException("Motorista não encontrado");
                        }
                );
    }

    @Schema(description = "Busca um carro disponível pelo id")
    private Carro buscarCarroDisponivelPorId(Long idCarro) {
        Carro carro = carroRepository.findById(idCarro)
                .orElseThrow(() -> {
                    log.error("Carro não encontrado");
                    return new EntityNotFoundException("Carro não encontrado");
                });

        if (!carro.isDisponivel()) {
            log.error("Carro não disponível para aluguel");
            throw new CarroIndisponivelException("Carro não disponível para aluguel");
        }

        return carro;
    }

    @Schema(description = "Busca uma apólice de seguro")
    private ApoliceSeguro buscarApoliceSeguro(ApoliceSeguro dadosApoliceSeguro) {
        return apoliceSeguroRepository
                .findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
                        dadosApoliceSeguro.getProtecaoTerceiro(),
                        dadosApoliceSeguro.getProtecaoCausasNaturais(),
                        dadosApoliceSeguro.getProtecaoRoubo()
                );
    }

    @Schema(description = "Cria um novo aluguel")
    private Aluguel criarAluguel(
            DadosCadastroAluguel dadosAluguel,
            BigDecimal valorTotalInicial,
            Motorista motorista,
            ApoliceSeguro apoliceSeguro,
            Carro carro
    ) {
        Aluguel aluguel = new Aluguel();
        aluguel.setDataPedido(now());
        aluguel.setDataRetirada(dadosAluguel.dataRetirada());
        aluguel.setDataDevolucaoPrevista(dadosAluguel.dataDevolucaoPrevista());
        aluguel.setValorTotalInicial(valorTotalInicial);
        aluguel.setValorTotalFinal(valorTotalInicial);
        aluguel.setStatusAluguel(INCOMPLETO);
        aluguel.adicionarMotorista(motorista);
        aluguel.adicionarApoliceSeguro(apoliceSeguro);
        aluguel.adicionarCarro(carro);
        return aluguel;
    }

    @Schema(description = "Cria um novo carrinho de aluguel")
    private CarrinhoAluguel criarCarrinhoAluguel(
            Motorista motorista,
            Carro carro,
            Aluguel aluguel
    ) {
        CarrinhoAluguel carrinho = new CarrinhoAluguel();
        carrinho.adicionarMotorista(motorista);
        carrinho.adicionarCarro(carro);
        carrinho.adicionarAluguel(aluguel);
        return carrinho;
    }

    @Schema(description = "Calcula o valor total inicial do aluguel")
    private BigDecimal calcularValorTotalInicial(
            @FutureOrPresent(message = "A data de retirada deve ser hoje ou uma data futura.")
            @NotNull(message = "A data de retirada é obrigatória.")
            LocalDate dataRetirada,

            @Future(message = "A data de devolução prevista deve ser uma data futura.")
            @NotNull(message = "A data de devolução prevista é obrigatória.")
            LocalDate dataDevolucaoPrevista,

            BigDecimal valorDiaria,

            ApoliceSeguro apoliceSeguro
    ) {
        BigDecimal valorAluguel, valorSeguro, valorTotalInicial;
        long diasAluguel;

        diasAluguel = DAYS.between(dataRetirada, dataDevolucaoPrevista);

        if (diasAluguel <= 0)
            throw new IllegalArgumentException("A data de devolução deve ser posterior à data de retirada.");

        valorAluguel = valorDiaria.multiply(BigDecimal.valueOf(diasAluguel));
        valorSeguro = calcularValorTotalApoliceSeguro(
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );

        valorTotalInicial = valorAluguel.add(valorSeguro);

        return valorTotalInicial;
    }
}
