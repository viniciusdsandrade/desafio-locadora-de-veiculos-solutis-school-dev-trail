package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Sexo;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Adulto;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.CNH;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;

@Schema(description = "Dados necessários para cadastrar um novo motorista.")
public record DadosCadastroMotorista(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome completo do motorista.")
        String nome,

        @NotNull(message = "Data de nascimento é obrigatória")
        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de nascimento do motorista.")
        @Adulto(message = "Você não é maior de idade")
        LocalDate dataNascimento,

        @NotBlank(message = "CPF é obrigatório")
        @Column(unique = true)
        @CPF(message = "CPF inválido")
        @Schema(description = "CPF do motorista.")
        String cpf,

        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email é inválido")
        @Column(unique = true)
        @Schema(description = "Endereço de email do motorista.")
        String email,

        @NotBlank(message = "Número da CNH é obrigatório")
        @Column(unique = true)
        @Schema(description = "Número da CNH do motorista.")
        @CNH(message = "Número da CNH inválido")
        String numeroCNH,

        @NotNull(message = "Sexo é obrigatório")
        @Schema(description = "Sexo do motorista.")
        Sexo sexo,

        @CreationTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
        @Schema(description = "Data e hora de criação do registro do motorista.", accessMode = READ_ONLY)
        LocalDateTime dataCreated,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", accessMode = READ_WRITE)
        LocalDateTime lastUpdated
) {
}