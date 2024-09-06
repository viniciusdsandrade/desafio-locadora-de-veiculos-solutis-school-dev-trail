package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Implementação dos serviços relacionados ao aluguel de veículos.")
public interface AluguelService {

    @Schema(description = "Pesquisa aluguéis com base em critérios (AND).")
    Page<DadosListagemAluguel> pesquisarAlugueisAnd(
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
    );

    @Schema(description = "Pesquisa aluguéis com base em critérios (OR).")
    Page<DadosListagemAluguel> pesquisarAlugueisOr(
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
    );
}
