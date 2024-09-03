package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sexo de uma pessoa.")
public enum Sexo {
    @Schema(description = "Masculino.")
    MASCULINO,

    @Schema(description = "Feminino.")
    FEMININO
}
