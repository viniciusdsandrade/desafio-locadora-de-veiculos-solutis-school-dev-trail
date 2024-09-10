package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carrinhoAluguel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.CarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusCarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.metodoPagamento.DadosListagemMetodoPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public record DadosListagemCarrinhoAluguel(

        Long id,

        StatusCarrinhoAluguel statusCarrinho,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDate dataCancelamento,

        List<DadosListagemAluguel> alugueis,

        DadosListagemMotorista motorista,

        String termos,

        Boolean termosAceitos,

        DadosListagemMetodoPagamento metodoPagamento,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorTotalInicial,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDateTime dataCreated,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        LocalDateTime lastUpdated
) {

    public DadosListagemCarrinhoAluguel(CarrinhoAluguel carrinhoAluguel) {
        this(
                carrinhoAluguel.getId(),
                carrinhoAluguel.getStatusCarrinho(),
                carrinhoAluguel.getDataCancelamento(),
                carrinhoAluguel.getAlugueis()
                        .stream()
                        .map(DadosListagemAluguel::new)
                        .toList(),
                new DadosListagemMotorista(carrinhoAluguel.getMotorista()),
                carrinhoAluguel.getTermos(),
                carrinhoAluguel.getTermosAceitos(),
                new DadosListagemMetodoPagamento(carrinhoAluguel.getMetodoPagamento()),
                carrinhoAluguel.getValorTotalInicial(),
                carrinhoAluguel.getDataCreated(),
                carrinhoAluguel.getLastUpdated()
        );
    }
}