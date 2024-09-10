package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.metodoPagamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.MetodoPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.TipoPagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public record DadosListagemMetodoPagamento(

        Long id,

        TipoPagamento tipoPagamento,

        StatusPagamento statusPagamento,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDate dataPagamento,

        String campoPix,

        String campoBoleto,

        String numeroCartao,

        String validadeCartao,

        String cvv,

        String pagamentoDinheiro,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDateTime dataCreated,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDateTime lastUpdated
) {

    public DadosListagemMetodoPagamento(MetodoPagamento metodoPagamento) {
        this(
                metodoPagamento.getId(),
                metodoPagamento.getTipoPagamento(),
                metodoPagamento.getStatusPagamento(),
                metodoPagamento.getDataPagamento(),
                metodoPagamento.getCampoPix(),
                metodoPagamento.getCampoBoleto(),
                metodoPagamento.getNumeroCartao(),
                metodoPagamento.getValidadeCartao(),
                metodoPagamento.getCvv(),
                metodoPagamento.getPagamentoDinheiro(),
                metodoPagamento.getDataCreated(),
                metodoPagamento.getLastUpdated()
        );
    }
}