package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.acessorio;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Acessorio;
import io.swagger.v3.oas.annotations.media.Schema;

import static java.lang.String.valueOf;

@Schema(description = "Dados resumidos de um acessório para listagem.")
public record DadosListagemAcessorios(
        String nome
) {
    public DadosListagemAcessorios(Acessorio acessorio) {
        this(valueOf(acessorio.getDescricao()));
    }
}
