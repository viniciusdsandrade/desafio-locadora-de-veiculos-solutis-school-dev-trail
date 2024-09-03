package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.impl;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosDetalhamentoMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Motorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.exception.DuplicateEntryException;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository.MotoristaRepository;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.MotoristaService;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.spec.MotoristaSpecs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.String.join;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.jpa.domain.Specification.where;

@Service("motoristaService")
@Schema(description = "Serviço que implementa as operações relacionadas a motoristas.")
public class MotoristaServiceImpl implements MotoristaService {

    @Schema(description = "Logger para a classe para MotoristaServiceImpl.")
    private static final Logger log = getLogger(MotoristaServiceImpl.class);

    @Schema(description = "Repositório JPA para a entidade Motorista.")
    private final MotoristaRepository motoristaRepository;

    public MotoristaServiceImpl(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @Override
    @Transactional
    @Schema(description = "Cadastra um novo motorista.")
    public Motorista cadastrarMotorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {
        log.info("Iniciando cadastro do motorista: {}", dadosCadastroMotorista);
        validarCamposDuplicados(dadosCadastroMotorista);
        log.info("Campos únicos validados com sucesso");

        Motorista motorista = new Motorista(dadosCadastroMotorista);
        motorista.ativar();
        motoristaRepository.save(motorista);

        log.info("Motorista cadastrado com sucesso: {}", motorista);
        return motorista;
    }

    @Override
    @Schema(description = "Busca um motorista pelo ID.")
    public Motorista buscarPorId(Long id) {
        log.info("Buscando motorista por ID: {}", id);
        Motorista motorista = existeMotoristaPeloId(id);
        log.info("Motorista encontrado: {}", motorista);
        return motorista;
    }

    @Override
    @Transactional
    @Schema(description = "Atualiza um motorista com base nos dados informados.")
    public Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        log.info("Atualizando motorista com dados: {}", dadosAtualizacaoMotorista);

        Motorista motorista = existeMotoristaPeloIdNoDto(dadosAtualizacaoMotorista);
        log.info("Motorista encontrado para atualização: {}", motorista);

        validarAtualizacaoComDadosUnicos(dadosAtualizacaoMotorista, motorista);
        log.info("Dados únicos validados com sucesso");

        motorista.atualizarInformacoes(dadosAtualizacaoMotorista);
        motoristaRepository.save(motorista);

        log.info("Motorista atualizado com sucesso: {}", motorista);
        return motorista;
    }

    @Override
    @Transactional
    @Schema(description = "Deleta um motorista pelo ID.")
    public void deletarMotorista(Long id) {
        log.info("Deletando motorista com ID: {}", id);
        Motorista motorista = existeMotoristaPeloId(id);

        log.info("Motorista encontrado para deleção: {}", motorista);
        motoristaRepository.delete(motorista);
        log.info("Motorista deletado com sucesso: {}", id);
    }

    @Override
    @Transactional
    @Schema(description = "Desativa um motorista pelo ID.")
    public void desativarMotorista(Long id) {
        log.info("Desativando motorista com ID: {}", id);

        Motorista motorista = existeMotoristaPeloId(id);
        log.info("Motorista encontrado para desativação: {}", motorista);

        motorista.desativar();

        motoristaRepository.save(motorista);
        log.info("Motorista desativado com sucesso: {}", motorista);
    }

    @Override
    @Transactional
    @Schema(description = "Ativa um motorista pelo ID.")
    public void ativarMotorista(Long id) {
        log.info("Ativando motorista com ID: {}", id);

        Motorista motorista = existeMotoristaPeloId(id);
        log.info("Motorista encontrado para ativação: {}", motorista);

        motorista.ativar();

        motoristaRepository.save(motorista);
        log.info("Motorista ativado com sucesso: {}", motorista);
    }

    @Override
    @Schema(description = "Lista motoristas com paginação.")
    public Page<DadosListagemMotorista> listar(Pageable paginacao) {
        log.info("Listando motoristas com paginação: {}", paginacao);
        Page<Motorista> motoristas = motoristaRepository.findAllByAtivoTrue(paginacao);
        log.info("Motoristas listados com sucesso: {}", motoristas);
        return motoristas.map(DadosListagemMotorista::new);
    }

    @Override
    @Schema(description = "Pesquisa motoristas com base nos filtros informados, usando junção AND.")
    public Page<DadosDetalhamentoMotorista> pesquisarMotoristasAnd(
            String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String numeroCNH,
            String sexo,
            Boolean ativo,
            List<String> placasAlugueis,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de motoristas com os seguintes critérios (AND):");
        log.info("Nome: {}", nome);
        log.info("Email: {}", email);
        log.info("CPF: {}", cpf);
        log.info("Data de Nascimento: {}", dataNascimento);
        log.info("Número da CNH: {}", numeroCNH);
        log.info("Sexo: {}", sexo);
        log.info("Ativo: {}", ativo);
        log.info("Placas de Aluguéis: {}", placasAlugueis);

        Specification<Motorista> spec = where(null);

        spec = addSpecification(spec, nome, MotoristaSpecs::nomeContains, true);
        spec = addSpecification(spec, email, MotoristaSpecs::emailEquals, true);
        spec = addSpecification(spec, cpf, MotoristaSpecs::cpfEquals, true);
        spec = addSpecification(spec, dataNascimento, MotoristaSpecs::dataNascimentoEquals, true);
        spec = addSpecification(spec, numeroCNH, MotoristaSpecs::numeroCNHEquals, true);
        spec = addSpecification(spec, sexo, MotoristaSpecs::sexoEquals, true);
        spec = addSpecification(spec, ativo, MotoristaSpecs::ativoEquals, true);
        spec = addSpecification(spec, placasAlugueis, MotoristaSpecs::alugueisComPlacas, true);

        Page<DadosDetalhamentoMotorista> resultados = motoristaRepository.findAll(spec, paginacao)
                .map(DadosDetalhamentoMotorista::new);
        log.info("Pesquisa de motoristas concluída (AND). Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    @Override
    @Schema(description = "Pesquisa motoristas com base nos filtros informados, usando junção OR.")
    public Page<DadosDetalhamentoMotorista> pesquisarMotoristasOr(
            String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String numeroCNH,
            String sexo,
            Boolean ativo,
            List<String> placasAlugueis,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de motoristas com os seguintes critérios (OR):");
        log.info("Nome: {}", nome);
        log.info("Email: {}", email);
        log.info("CPF: {}", cpf);
        log.info("Data de Nascimento: {}", dataNascimento);
        log.info("Número da CNH: {}", numeroCNH);
        log.info("Sexo: {}", sexo);
        log.info("Ativo: {}", ativo);
        log.info("Placas de Aluguéis: {}", placasAlugueis);

        Specification<Motorista> spec = where(null);

        spec = addSpecification(spec, nome, MotoristaSpecs::nomeContains, false);
        spec = addSpecification(spec, email, MotoristaSpecs::emailEquals, false);
        spec = addSpecification(spec, cpf, MotoristaSpecs::cpfEquals, false);
        spec = addSpecification(spec, dataNascimento, MotoristaSpecs::dataNascimentoEquals, false);
        spec = addSpecification(spec, numeroCNH, MotoristaSpecs::numeroCNHEquals, false);
        spec = addSpecification(spec, sexo, MotoristaSpecs::sexoEquals, false);
        spec = addSpecification(spec, ativo, MotoristaSpecs::ativoEquals, false);
        spec = addSpecification(spec, placasAlugueis, MotoristaSpecs::alugueisComPlacas, false);

        Page<DadosDetalhamentoMotorista> resultados = motoristaRepository.findAll(spec, paginacao)
                .map(DadosDetalhamentoMotorista::new);
        log.info("Pesquisa de motoristas concluída (OR). Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    @Schema(description = "Adiciona uma especificação à especificação atual com base no tipo de junção (AND ou OR).")
    private <T> Specification<Motorista> addSpecification(
            Specification<Motorista> spec,
            T value,
            Function<T, Specification<Motorista>> specBuilder,
            boolean isAnd
    ) {
        return value != null ? (isAnd ? spec.and(specBuilder.apply(value)) : spec.or(specBuilder.apply(value))) : spec;
    }

    @Schema(description = "Verifica se um motorista existe com base no ID.")
    private Motorista existeMotoristaPeloId(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> {
                            log.warn("Motorista não encontrado para desativação: {}", id);
                            return new EntityNotFoundException("Motorista não encontrado");
                        }
                );
    }

    @Schema(description = "Verifica se um motorista existe com base no ID informado no DTO.")
    private Motorista existeMotoristaPeloIdNoDto(DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        return motoristaRepository.findById(dadosAtualizacaoMotorista.id())
                .orElseThrow(() -> {
                            log.warn("Motorista não encontrado para atualização: {}", dadosAtualizacaoMotorista.id());
                            return new EntityNotFoundException("Motorista não encontrado");
                        }
                );
    }

    @Schema(description = "Valida se os dados únicos do motorista estão corretos.")
    private void validarAtualizacaoComDadosUnicos(DadosAtualizacaoMotorista dados, Motorista motoristaAtual) {
        List<String> errosDuplicidade = new ArrayList<>();

        if (dados.cpf() != null && !dados.cpf().equals(motoristaAtual.getCpf()))
            verificarDuplicidade(dados.cpf(), motoristaRepository.existsByCpf(dados.cpf()), "CPF", errosDuplicidade);

        if (dados.email() != null && !dados.email().equals(motoristaAtual.getEmail()))
            verificarDuplicidade(dados.email(), motoristaRepository.existsByEmail(dados.email()), "e-mail", errosDuplicidade);

        if (dados.numeroCNH() != null && !dados.numeroCNH().equals(motoristaAtual.getNumeroCNH()))
            verificarDuplicidade(dados.numeroCNH(), motoristaRepository.existsByNumeroCNH(dados.numeroCNH()), "número da CNH", errosDuplicidade);

        // Se houver duplicidades, lançar exceção com todos os erros.
        if (!errosDuplicidade.isEmpty()) {
            String mensagemErro = join(", ", errosDuplicidade);
            log.warn("Tentativa de atualização com dados duplicados: {}", mensagemErro);
            throw new DuplicateEntryException("Já existem motoristas cadastrados com os seguintes campos duplicados: " + mensagemErro);
        }
    }

    @Schema(description = "Verifica se um valor já existe no banco de dados e, se existir, adiciona à lista de erros.")
    private void verificarDuplicidade(String valor, boolean existe, String campo, List<String> errosDuplicidade) {
        if (existe) {
            log.warn("Tentativa de atualização com {} duplicado: {}", campo, valor);
            errosDuplicidade.add(campo + ": " + valor);
        }
    }

    @Schema(description = "Valida se os campos únicos do motorista não estão duplicados.")
    private void validarCamposDuplicados(DadosCadastroMotorista dados) {
        List<String> errosDuplicados = new ArrayList<>();

        if (motoristaRepository.existsByCpf(dados.cpf())) {
            log.warn("Tentativa de cadastro com CPF duplicado: {}", dados.cpf());
            errosDuplicados.add("Já existe um motorista cadastrado com esse CPF");
        }

        if (motoristaRepository.existsByEmail(dados.email())) {
            log.warn("Tentativa de cadastro com e-mail duplicado: {}", dados.email());
            errosDuplicados.add("Já existe um motorista cadastrado com esse e-mail");
        }

        if (motoristaRepository.existsByNumeroCNH(dados.numeroCNH())) {
            log.warn("Tentativa de cadastro com número da CNH duplicado: {}", dados.numeroCNH());
            errosDuplicados.add("Já existe um motorista cadastrado com esse número da CNH");
        }

        if (!errosDuplicados.isEmpty()) {
            String mensagemErro = join("\n", errosDuplicados); // Junta as mensagens de erro com quebra de linha
            throw new DuplicateEntryException(mensagemErro);
        }
    }
}