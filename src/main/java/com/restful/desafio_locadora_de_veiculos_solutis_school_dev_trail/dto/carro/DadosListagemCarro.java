package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.config.BigDecimalCurrencySerializer;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.acessorio.DadosListagemAcessorios;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Cor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@Schema(description = "Dados resumidos de um carro para listagem.")
public record DadosListagemCarro(

        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @Schema(description = "Placa do carro.")
        String placa,

        @Schema(description = "Nome do modelo do carro.")
        String modeloCarro,

        @Schema(description = "Cor do carro.")
        Cor cor,

        @JsonSerialize(using = BigDecimalCurrencySerializer.class)
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiaria,

        @Schema(description = "Indica se o carro está disponível para aluguel.")
        boolean disponivel,

        @Schema(description = "Lista de nomes dos acessórios do carro.")
        List<String> acessorios
) {
    public DadosListagemCarro(Carro carro) {
        this(
                carro.getNome(),
                carro.getPlaca(),
                carro.getModeloCarro() != null ? carro.getModeloCarro().getDescricaoModeloCarro() : null,
                carro.getCor(),
                carro.getValorDiaria(),
                carro.isDisponivel(),
                carro.getAcessorios()
                        .stream()
                        .map(DadosListagemAcessorios::new)
                        .map(DadosListagemAcessorios::nome)
                        .collect(toList())
        );
    }

    public DadosListagemCarro(String s) {
        this(s, s, s, null, ZERO, false, List.of(s));
    }

}
