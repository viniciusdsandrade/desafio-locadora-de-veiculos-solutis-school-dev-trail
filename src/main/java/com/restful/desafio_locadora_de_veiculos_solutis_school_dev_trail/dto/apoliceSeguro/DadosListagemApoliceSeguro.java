package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.apoliceSeguro;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ApoliceSeguro;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

import static com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ApoliceSeguro.calcularValorTotalApoliceSeguro;
import static java.math.BigDecimal.ZERO;


@Schema(description = "Dados resumidos de uma apólice de seguro para listagem.")
public record DadosListagemApoliceSeguro(

        @Schema(description = "Valor da franquia da apólice de seguro.")
        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        BigDecimal valorFranquia,

        @Schema(description = "Indica se a apólice cobre danos a terceiros.")
        Boolean protecaoTerceiro,

        @Schema(description = "Indica se a apólice cobre danos por causas naturais.")
        Boolean protecaoCausasNaturais,

        @Schema(description = "Indica se a apólice cobre roubo do veículo.")
        Boolean protecaoRoubo
) {
    public DadosListagemApoliceSeguro(ApoliceSeguro apoliceSeguro) {
        this(
                calcularValorTotalApoliceSeguro(
                        apoliceSeguro.getProtecaoTerceiro(),
                        apoliceSeguro.getProtecaoCausasNaturais(),
                        apoliceSeguro.getProtecaoRoubo()
                ),
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );
    }

    public DadosListagemApoliceSeguro() {
        this(
                ZERO,
                false,
                false,
                false
        );
    }
}