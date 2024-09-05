package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Sexo;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Adulto;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.CNH;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Picture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados necessários para cadastrar um novo motorista.")
public record DadosCadastroMotorista(

        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome completo do motorista.")
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Schema(description = "Data de nascimento do motorista.")
        @NotNull(message = "Data de nascimento é obrigatória")
        @Adulto(message = "Você não é maior de idade")
        LocalDate dataNascimento,

        @Schema(description = "CPF do motorista.")
        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        @Column(unique = true)
        String cpf,

        @Schema(description = "Endereço de email do motorista.")
        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email é inválido")
        @Column(unique = true)
        String email,

        @Schema(description = "Número da CNH do motorista.")
        @NotBlank(message = "Número da CNH é obrigatório")
        @CNH(message = "Número da CNH inválido")
        @Column(unique = true)
        String numeroCNH,

        @Schema(description = "Sexo do motorista.")
        @NotNull(message = "Sexo é obrigatório")
        Sexo sexo,

        @Picture(message = "A foto deve ser uma imagem JPEG ou PNG e deve ter no máximo 1MB.")
        @Schema(description = "Foto do motorista.", type = "string", format = "binary")
        MultipartFile foto
) {
}