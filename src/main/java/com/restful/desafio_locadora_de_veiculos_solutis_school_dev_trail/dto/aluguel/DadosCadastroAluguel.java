package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ApoliceSeguro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Schema(description = "Dados mínimos para alugar um veículo.")
public record DadosCadastroAluguel(

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @FutureOrPresent(message = "A data de retirada deve ser hoje ou uma data futura.")
        @NotNull(message = "A data de retirada é obrigatória.")
        @Schema(description = "Data de retirada do veículo.")
        LocalDate dataRetirada,

        @JsonFormat(pattern = "dd/MM/yyyy", shape = STRING, locale = "pt-BR", timezone = "Brazil/East")
        @Future(message = "A data de devolução prevista deve ser uma data futura.")
        @NotNull(message = "A data de devolução prevista é obrigatória.")
        @Schema(description = "Data de devolução prevista do veículo.")
        LocalDate dataDevolucaoPrevista,

        @NotNull(message = "Os dados da apólice de seguro são obrigatórios.")
        @Schema(description = "Dados da apólice de seguro do aluguel.")
        ApoliceSeguro apoliceSeguro,

        @NotBlank(message = "O email do motorista é obrigatório.")
        @Schema(description = "Endereço de email do motorista.")
        @Email(message = "Email inválido.")
        String emailMotorista,

        @Schema(description = "ID do carro a ser alugado.")
        @NotNull(message = "O ID do carro é obrigatório.")
        Long idCarro
) {
}
