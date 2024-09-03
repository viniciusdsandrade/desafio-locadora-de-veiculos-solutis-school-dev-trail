package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.impl;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosAtualizacaoCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosDetalhamentoCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.exception.DuplicateEntryException;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository.CarroRepository;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.CarroService;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.spec.CarroSpecs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.jpa.domain.Specification.where;

@Service("CarroService")
@Schema(description = "Serviço que implementa as operações relacionadas a carros.")
public class CarroServiceImpl implements CarroService {

    @Schema(description = "Logger para a classe CarroServiceImpl.")
    private static final Logger log = getLogger(CarroServiceImpl.class);

    @Schema(description = "Repositório JPA para a entidade Carro.")
    private final CarroRepository carroRepository;

    public CarroServiceImpl(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @Override
    @Transactional
    @Schema(description = "Cadastra um novo carro.")
    public Carro cadastrarCarro(@Valid DadosCadastroCarro dadosCadastroCarro) {
        log.info("Iniciando cadastro do carro: {}", dadosCadastroCarro);
        validarCamposDuplicadosEmDadosCadastroCarro(dadosCadastroCarro);
        log.info("Campos validados com sucesso");

        Carro carro = new Carro(dadosCadastroCarro);
        carroRepository.save(carro);

        log.info("Carro cadastrado com sucesso: {}", carro);
        return carro;
    }

    @Override
    @Schema(description = "Busca um carro pelo ID.")
    public Carro buscarPorId(Long id) {
        log.info("Buscando carro por ID: {}", id);
        Carro carro = existeCarroPeloId(id);
        log.info("Carro encontrado: {}", carro);
        return carro;
    }

    @Override
    @Transactional
    @Schema(description = "Atualiza um carro.")
    public Carro atualizarCarro(@Valid DadosAtualizacaoCarro dadosAtualizarCarro) {
        log.info("Atualizando carro com dados: {}", dadosAtualizarCarro);
        Carro carro = existeCarroPeloId(dadosAtualizarCarro.id());
        log.info("Carro encontrado para atualização: {}", carro);

        validarCamposDuplicadosEmDadosAtualizacaoCarro(dadosAtualizarCarro, carro); // Verifica se há campos para atualização que não permitem duplicação
        carro.atualizar(dadosAtualizarCarro);
        carroRepository.save(carro);
        log.info("Carro atualizado com sucesso: {}", carro);
        return carro;
    }

    @Override
    @Transactional
    @Schema(description = "Bloqueia um carro para aluguel.")
    public void bloquearCarroAluguel(Long id) {
        log.info("Desativando carro com ID: {}", id);
        Carro carro = existeCarroPeloId(id);
        carro.bloquearAluguel();
        carroRepository.save(carro);
        log.info("Carro desativado com sucesso: {}", carro);
    }

    @Override
    @Transactional
    @Schema(description = "Disponibiliza um carro para aluguel.")
    public void disponibilizarCarroAluguel(Long id) {
        log.info("Ativando carro com ID: {}", id);
        Carro carro = existeCarroPeloId(id);
        carro.disponibilizarAluguel();
        carroRepository.save(carro);
        log.info("Carro ativado com sucesso: {}", carro);
    }

    @Override
    @Transactional
    @Schema(description = "Exclui um carro.")
    public void excluirCarro(Long id) {
        log.info("Deletando carro com ID: {}", id);
        existeCarroPeloId(id);
        carroRepository.deleteById(id);
        log.info("Carro deletado com sucesso: {}", id);
    }

    @Override
    @Schema(description = "Lista todos os carros cadastrados.")
    public Page<DadosListagemCarro> listar(Pageable paginacao) {
        log.info("Listando carros com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAll(paginacao);
        log.info("Carros encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    @Override
    @Schema(description = "Lista todos os carros disponíveis para aluguel.")
    public Page<DadosListagemCarro> listarCarrosDisponiveis(Pageable paginacao) {
        log.info("Listando carros disponíveis com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAllByDisponivelTrue(paginacao);
        log.info("Carros disponíveis encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    @Override
    @Schema(description = "Lista todos os carros alugados.")
    public Page<DadosListagemCarro> listarCarrosAlugados(Pageable paginacao) {
        log.info("Listando carros alugados com paginação: {}", paginacao);
        Page<Carro> carros = carroRepository.findAllByDisponivelFalse(paginacao);
        log.info("Carros alugados encontrados: {}", carros);
        return carros.map(DadosListagemCarro::new);
    }

    @Override
    @Schema(description = "Pesquisa carros com base em critérios.")
    public Page<DadosDetalhamentoCarro> pesquisarCarrosAnd(
            String nome,
            String placa,
            String chassi,
            String cor,
            Boolean disponivel,
            BigDecimal valorDiariaMin,
            BigDecimal valorDiariaMax,
            String modeloDescricao,
            String fabricanteNome,
            String categoriaNome,
            List<String> acessoriosNomes,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de carros com os seguintes critérios:");
        log.info("Nome: {}", nome);
        log.info("Placa: {}", placa);
        log.info("Chassi: {}", chassi);
        log.info("Cor: {}", cor);
        log.info("Disponível: {}", disponivel);
        log.info("Valor da diária mínimo: {}", valorDiariaMin);
        log.info("Valor da diária máximo: {}", valorDiariaMax);
        log.info("Descrição do modelo: {}", modeloDescricao);
        log.info("Nome do fabricante: {}", fabricanteNome);
        log.info("Nome da categoria: {}", categoriaNome);
        log.info("Nomes dos acessórios: {}", acessoriosNomes);

        Specification<Carro> spec = where(null);

        spec = addSpecification(spec, nome, CarroSpecs::nomeContains, true);
        spec = addSpecification(spec, placa, CarroSpecs::placaEquals, true);
        spec = addSpecification(spec, chassi, CarroSpecs::chassiEquals, true);
        spec = addSpecification(spec, cor, CarroSpecs::corEquals, true);
        spec = addSpecification(spec, disponivel, CarroSpecs::disponivelEquals, true);
        spec = addSpecification(spec, valorDiariaMin, CarroSpecs::valorDiariaGreaterThanOrEqual, true);
        spec = addSpecification(spec, valorDiariaMax, CarroSpecs::valorDiariaLessThanOrEqual, true);
        spec = addSpecification(spec, modeloDescricao, CarroSpecs::modeloDescricaoContains, true);
        spec = addSpecification(spec, fabricanteNome, CarroSpecs::fabricanteNomeContains, true);
        spec = addSpecification(spec, categoriaNome, CarroSpecs::categoriaNomeEquals, true);

        if (acessoriosNomes != null && !acessoriosNomes.isEmpty())
            spec = addSpecification(spec, acessoriosNomes, CarroSpecs::temAcessorios, true);

        Page<DadosDetalhamentoCarro> resultados = carroRepository.findAll(spec, paginacao).map(DadosDetalhamentoCarro::new);
        log.info("Pesquisa de carros concluída. Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    @Override
    @Schema(description = "Pesquisa carros com base em critérios utilizando OR.")
    public Page<DadosDetalhamentoCarro> pesquisarCarrosOr(
            String nome,
            String placa,
            String chassi,
            String cor,
            Boolean disponivel,
            BigDecimal valorDiariaMin,
            BigDecimal valorDiariaMax,
            String modeloDescricao,
            String fabricanteNome,
            String categoriaNome,
            List<String> acessoriosNomes,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de carros com os seguintes critérios (OR):");
        log.info("Nome: {}", nome);
        log.info("Placa: {}", placa);
        log.info("Chassi: {}", chassi);
        log.info("Cor: {}", cor);
        log.info("Disponível: {}", disponivel);
        log.info("Valor da diária mínimo: {}", valorDiariaMin);
        log.info("Valor da diária máximo: {}", valorDiariaMax);
        log.info("Descrição do modelo: {}", modeloDescricao);
        log.info("Nome do fabricante: {}", fabricanteNome);
        log.info("Nome da categoria: {}", categoriaNome);
        log.info("Nomes dos acessórios: {}", acessoriosNomes);

        Specification<Carro> spec = where(null);

        spec = addSpecification(spec, nome, CarroSpecs::nomeContains, false);
        spec = addSpecification(spec, placa, CarroSpecs::placaEquals, false);
        spec = addSpecification(spec, chassi, CarroSpecs::chassiEquals, false);
        spec = addSpecification(spec, cor, CarroSpecs::corEquals, false);
        spec = addSpecification(spec, disponivel, CarroSpecs::disponivelEquals, false);
        spec = addSpecification(spec, valorDiariaMin, CarroSpecs::valorDiariaGreaterThanOrEqual, false);
        spec = addSpecification(spec, valorDiariaMax, CarroSpecs::valorDiariaLessThanOrEqual, false);
        spec = addSpecification(spec, modeloDescricao, CarroSpecs::modeloDescricaoContains, false);
        spec = addSpecification(spec, fabricanteNome, CarroSpecs::fabricanteNomeContains, false);
        spec = addSpecification(spec, categoriaNome, CarroSpecs::categoriaNomeEquals, false);

        if (acessoriosNomes != null && !acessoriosNomes.isEmpty())
            spec = addSpecification(spec, acessoriosNomes, CarroSpecs::temAcessorios, false);

        Page<DadosDetalhamentoCarro> resultados = carroRepository.findAll(spec, paginacao).map(DadosDetalhamentoCarro::new);
        log.info("Pesquisa de carros concluída (OR). Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    @Schema(description = "Adiciona uma especificação à especificação atual com base no tipo de junção (AND ou OR).")
    private <T> Specification<Carro> addSpecification(
            Specification<Carro> spec,
            T value,
            Function<T, Specification<Carro>> specBuilder,
            boolean isAnd
    ) {
        return value != null ? (isAnd ? spec.and(specBuilder.apply(value)) : spec.or(specBuilder.apply(value))) : spec;
    }

    @Schema(description = "Verifica se há campos duplicados ao cadastrar um carro.")
    private void validarCamposDuplicadosEmDadosAtualizacaoCarro(@Valid DadosAtualizacaoCarro dadosAtualizarCarro, Carro carroAtual) {
        List<String> erroDuplicados = new ArrayList<>();

        if (dadosAtualizarCarro.placa() != null && !dadosAtualizarCarro.placa().equals(carroAtual.getPlaca()))
            verificarDuplicidade(dadosAtualizarCarro.placa(), carroRepository.existsByPlaca(dadosAtualizarCarro.placa()), "Placa", erroDuplicados);

        if (dadosAtualizarCarro.chassi() != null && !dadosAtualizarCarro.chassi().equals(carroAtual.getChassi()))
            verificarDuplicidade(dadosAtualizarCarro.chassi(), carroRepository.existsByChassi(dadosAtualizarCarro.chassi()), "Chassi", erroDuplicados);

        if (!erroDuplicados.isEmpty()) {
            log.warn("Campos duplicados ao atualizar: {}", erroDuplicados);
            throw new DuplicateEntryException("Campos duplicados: " + String.join(", ", erroDuplicados));
        }
    }

    @Schema(description = "Verifica se há campos duplicados ao cadastrar um carro.")
    private void validarCamposDuplicadosEmDadosCadastroCarro(@Valid DadosCadastroCarro dadosCadastroCarro) {
        List<String> erroDuplicados = new ArrayList<>();

        verificarDuplicidade(dadosCadastroCarro.placa(), carroRepository.existsByPlaca(dadosCadastroCarro.placa()), "Placa", erroDuplicados);
        verificarDuplicidade(dadosCadastroCarro.chassi(), carroRepository.existsByChassi(dadosCadastroCarro.chassi()), "Chassi", erroDuplicados);

        if (!erroDuplicados.isEmpty()) {
            log.warn("Campos duplicados ao cadastrar: {}", erroDuplicados);
            throw new DuplicateEntryException("Campos duplicados: " + String.join(", ", erroDuplicados));
        }
    }

    @Schema(description = "Verifica se um valor já existe no banco de dados e adiciona uma mensagem de erro à lista de erros.")
    private void verificarDuplicidade(
            String valor,
            boolean existe,
            String campo,
            List<String> erros
    ) {
        if (existe) {
            log.warn("{} já cadastrado: {}", campo, valor);
            erros.add(campo + " já cadastrado");
        }
    }

    @Schema(description = "Verifica se um carro existe pelo ID.")
    private Carro existeCarroPeloId(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Carro não encontrado: {}", id);
                            return new EntityNotFoundException("Carro não encontrado");
                        }
                );
    }
}
