package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Acessorio;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ModeloCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Cor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados para atualizar um carro existente.")
public record DadosAtualizacaoCarro(

        @Schema(description = "ID do carro a ser atualizado.")
        @NotNull(message = "ID é obrigatório")
        Long id,

        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @Schema(description = "Placa do carro.")
        String placa,

        @Schema(description = "Chassi do carro.")
        String chassi,

        @Schema(description = "Cor do carro.")
        Cor cor,

        @DecimalMax(value = "9,999,999.99", message = "O valorTotalParcial diário deve ser menor que R$ 9.999.999,00")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valorTotalParcial diário deve ser maior que zero")
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiario,

        @Schema(description = "Lista de acessórios do carro.")
        List<Acessorio> acessorio,

        @Schema(description = "Modelo do carro.")
        ModeloCarro modelo
) {
}