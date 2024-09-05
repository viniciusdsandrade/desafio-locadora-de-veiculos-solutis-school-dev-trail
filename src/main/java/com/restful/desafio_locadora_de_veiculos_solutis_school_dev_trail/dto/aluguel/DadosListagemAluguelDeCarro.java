package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.apoliceSeguro.DadosListagemApoliceSeguro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Aluguel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados resumidos de um aluguel para a listagem de carros.")
public record DadosListagemAluguelDeCarro(

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data do pedido do aluguel.")
        LocalDate dataPedido,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de entrega do carro.")
        LocalDate dataEntrega,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data prevista para devolução do carro.")
        LocalDate dataDevolucaoPrevista,

        @Schema(description = "Valor total final do aluguel, formatado como moeda brasileira (R$).")
        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        BigDecimal valor,

        @Schema(description = "Dados resumidos da apólice de seguro associada ao aluguel.")
        DadosListagemApoliceSeguro apolice
) {

    public DadosListagemAluguelDeCarro(Aluguel aluguel) {
        this(
                aluguel.getDataPedido(),
                aluguel.getDataRetirada(),
                aluguel.getDataDevolucaoPrevista(),
                aluguel.getValorTotalFinal(),
                new DadosListagemApoliceSeguro(aluguel.getApoliceSeguro())
        );
    }
}