package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.impl;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Aluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository.AluguelRepository;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.AluguelService;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.spec.AluguelSpecs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.jpa.domain.Specification.where;

@Service("aluguelService")
@Schema(description = "Implementação dos serviços relacionados ao aluguel de veículos.")
public class AluguelServiceImpl implements AluguelService {

    private static final Logger log = getLogger(AluguelServiceImpl.class);

    private final AluguelRepository aluguelRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    @Schema(description = "Verifica se existe um aluguel pelo id.")
    private Aluguel existeAluguelPeloId(Long id) {
        return aluguelRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("Aluguel não encontrado pelo id: {}", id);
                            return new EntityNotFoundException("Aluguel não encontrado.");
                        }
                );
    }

    @Override
    @Schema(description = "Pesquisa aluguéis com base em critérios (AND).")
    public Page<DadosListagemAluguel> pesquisarAlugueisAnd(
            StatusAluguel statusAluguel,
            LocalDate dataPedidoInicio,
            LocalDate dataPedidoFim,
            LocalDate dataRetiradaInicio,
            LocalDate dataRetiradaFim,
            LocalDate dataDevolucaoPrevistaInicio,
            LocalDate dataDevolucaoPrevistaFim,
            LocalDate dataDevolucaoEfetivaInicio,
            LocalDate dataDevolucaoEfetivaFim,
            BigDecimal valorTotalInicialMin,
            BigDecimal valorTotalInicialMax,
            BigDecimal valorTotalFinalMin,
            BigDecimal valorTotalFinalMax,
            List<String> emailsMotoristas,
            List<String> placasCarros,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de aluguéis com os seguintes critérios (AND):");
        log.info("Status: {}", statusAluguel);
        log.info("Data de pedido (início): {}", dataPedidoInicio);
        log.info("Data de pedido (fim): {}", dataPedidoFim);
        log.info("Data de retirada (início): {}", dataRetiradaInicio);
        log.info("Data de retirada (fim): {}", dataRetiradaFim);
        log.info("Data de devolução prevista (início): {}", dataDevolucaoPrevistaInicio);
        log.info("Data de devolução prevista (fim): {}", dataDevolucaoPrevistaFim);
        log.info("Data de devolução efetiva (início): {}", dataDevolucaoEfetivaInicio);
        log.info("Data de devolução efetiva (fim): {}", dataDevolucaoEfetivaFim);
        log.info("Valor total inicial (mínimo): {}", valorTotalInicialMin);
        log.info("Valor total inicial (máximo): {}", valorTotalInicialMax);
        log.info("Valor total final (mínimo): {}", valorTotalFinalMin);
        log.info("Valor total final (máximo): {}", valorTotalFinalMax);
        log.info("E-mails dos motoristas: {}", emailsMotoristas);
        log.info("Placas dos carros: {}", placasCarros);

        Specification<Aluguel> spec = where(null);

        spec = addSpecification(spec, statusAluguel, AluguelSpecs::statusEquals, true);
        spec = addSpecification(spec, dataPedidoInicio, dataPedidoFim, AluguelSpecs::dataPedidoBetween, true);
        spec = addSpecification(spec, dataRetiradaInicio, dataRetiradaFim, AluguelSpecs::dataRetiradaBetween, true);
        spec = addSpecification(spec, dataDevolucaoPrevistaInicio, dataDevolucaoPrevistaFim, AluguelSpecs::dataDevolucaoPrevistaBetween, true);
        spec = addSpecification(spec, dataDevolucaoEfetivaInicio, dataDevolucaoEfetivaFim, AluguelSpecs::dataDevolucaoEfetivaBetween, true);
        spec = addSpecification(spec, valorTotalInicialMin, valorTotalInicialMax, AluguelSpecs::valorTotalInicialBetween, true);
        spec = addSpecification(spec, valorTotalFinalMin, valorTotalFinalMax, AluguelSpecs::valorTotalFinalBetween, true);
        spec = addSpecification(spec, emailsMotoristas, AluguelSpecs::motoristasComEmails, true);
        spec = addSpecification(spec, placasCarros, AluguelSpecs::carrosComPlacas, true);

        Page<DadosListagemAluguel> resultados = aluguelRepository.findAll(spec, paginacao).map(DadosListagemAluguel::new);
        log.info("Pesquisa de aluguéis concluída (AND). Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    @Override
    @Schema(description = "Pesquisa aluguéis com base em critérios (OR).")
    public Page<DadosListagemAluguel> pesquisarAlugueisOr(
            StatusAluguel statusAluguel,
            LocalDate dataPedidoInicio,
            LocalDate dataPedidoFim,
            LocalDate dataRetiradaInicio,
            LocalDate dataRetiradaFim,
            LocalDate dataDevolucaoPrevistaInicio,
            LocalDate dataDevolucaoPrevistaFim,
            LocalDate dataDevolucaoEfetivaInicio,
            LocalDate dataDevolucaoEfetivaFim,
            BigDecimal valorTotalInicialMin,
            BigDecimal valorTotalInicialMax,
            BigDecimal valorTotalFinalMin,
            BigDecimal valorTotalFinalMax,
            List<String> emailsMotoristas,
            List<String> placasCarros,
            Pageable paginacao
    ) {
        log.info("Iniciando pesquisa de aluguéis com os seguintes critérios (OR):");
        log.info("Status: {}", statusAluguel);
        log.info("Data de pedido (início): {}", dataPedidoInicio);
        log.info("Data de pedido (fim): {}", dataPedidoFim);
        log.info("Data de retirada (início): {}", dataRetiradaInicio);
        log.info("Data de retirada (fim): {}", dataRetiradaFim);
        log.info("Data de devolução prevista (início): {}", dataDevolucaoPrevistaInicio);
        log.info("Data de devolução prevista (fim): {}", dataDevolucaoPrevistaFim);
        log.info("Data de devolução efetiva (início): {}", dataDevolucaoEfetivaInicio);
        log.info("Data de devolução efetiva (fim): {}", dataDevolucaoEfetivaFim);
        log.info("Valor total inicial (mínimo): {}", valorTotalInicialMin);
        log.info("Valor total inicial (máximo): {}", valorTotalInicialMax);
        log.info("Valor total final (mínimo): {}", valorTotalFinalMin);
        log.info("Valor total final (máximo): {}", valorTotalFinalMax);
        log.info("E-mails dos motoristas: {}", emailsMotoristas);
        log.info("Placas dos carros: {}", placasCarros);

        Specification<Aluguel> spec = where(null);

        spec = addSpecification(spec, statusAluguel, AluguelSpecs::statusEquals, false);
        spec = addSpecification(spec, dataPedidoInicio, dataPedidoFim, AluguelSpecs::dataPedidoBetween, false);
        spec = addSpecification(spec, dataRetiradaInicio, dataRetiradaFim, AluguelSpecs::dataRetiradaBetween, false);
        spec = addSpecification(spec, dataDevolucaoPrevistaInicio, dataDevolucaoPrevistaFim, AluguelSpecs::dataDevolucaoPrevistaBetween, false);
        spec = addSpecification(spec, dataDevolucaoEfetivaInicio, dataDevolucaoEfetivaFim, AluguelSpecs::dataDevolucaoEfetivaBetween, false);
        spec = addSpecification(spec, valorTotalInicialMin, valorTotalInicialMax, AluguelSpecs::valorTotalInicialBetween, false);
        spec = addSpecification(spec, valorTotalFinalMin, valorTotalFinalMax, AluguelSpecs::valorTotalFinalBetween, false);
        spec = addSpecification(spec, emailsMotoristas, AluguelSpecs::motoristasComEmails, false);
        spec = addSpecification(spec, placasCarros, AluguelSpecs::carrosComPlacas, false);

        Page<DadosListagemAluguel> resultados = aluguelRepository.findAll(spec, paginacao).map(DadosListagemAluguel::new);
        log.info("Pesquisa de aluguéis concluída (OR). Número de resultados encontrados: {}", resultados.getTotalElements());
        return resultados;
    }

    @Schema(description = "Adiciona uma especificação à especificação atual com base no tipo de junção (AND ou OR).")
    private <T> Specification<Aluguel> addSpecification(
            Specification<Aluguel> spec,
            T value,
            Function<T, Specification<Aluguel>> specBuilder,
            boolean isAnd
    ) {
        return value != null ? (isAnd ? spec.and(specBuilder.apply(value)) : spec.or(specBuilder.apply(value))) : spec;
    }

    @Schema(description = "Adiciona uma especificação à especificação atual com base no tipo de junção (AND ou OR).")
    private <T> Specification<Aluguel> addSpecification(
            Specification<Aluguel> spec,
            T value1,
            T value2,
            BiFunction<T, T, Specification<Aluguel>> specBuilder,
            boolean isAnd
    ) {
        return value1 != null && value2 != null ? (isAnd ? spec.and(specBuilder.apply(value1, value2)) : spec.or(specBuilder.apply(value1, value2))) : spec;
    }
}
