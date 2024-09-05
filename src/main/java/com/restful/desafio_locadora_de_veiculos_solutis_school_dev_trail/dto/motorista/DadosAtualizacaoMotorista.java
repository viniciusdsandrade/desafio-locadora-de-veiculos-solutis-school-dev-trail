package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Sexo;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Adulto;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.CNH;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados para atualizar um motorista existente.")
public record DadosAtualizacaoMotorista(

        @Schema(description = "ID do motorista a ser atualizado.")
        @NotNull(message = "ID é obrigatório")
        Long id,

        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome completo do motorista.")
        String nome,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de nascimento do motorista.")
        @Adulto(message = "Você não é maior de idade")
        LocalDate dataNascimento,

        @Schema(description = "CPF do motorista.")
        @CPF(message = "CPF inválido")
        @Column(unique = true)
        String cpf,

        @Schema(description = "Endereço de email do motorista.")
        @Email(message = "E-mail inválido")
        @Column(unique = true)
        String email,

        @Schema(description = "Número da CNH do motorista.")
        @CNH(message = "Número da CNH inválido")
        @Column(unique = true)
        String numeroCNH,

        @Schema(description = "Sexo do motorista.")
        Sexo sexo
) {
}