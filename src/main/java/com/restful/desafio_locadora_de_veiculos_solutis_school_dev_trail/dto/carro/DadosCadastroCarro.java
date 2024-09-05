package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Acessorio;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ModeloCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Cor;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Chassi;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Placa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados necessários para cadastrar um novo carro.")
public record DadosCadastroCarro(

        @NotBlank(message = "O nome do carro é obrigatório")
        @Schema(description = "Nome do modelo do carro.")
        String nome,

        @NotBlank(message = "A placa do carro é obrigatória")
        @Schema(description = "Placa do carro.")
        @Placa(message = "Placa inválida")
        String placa,

        @NotBlank(message = "O chassi do carro é obrigatório")
        @Schema(description = "Chassi do carro.")
        @Chassi(message = "Chassi inválido")
        String chassi,

        @NotNull(message = "A cor do carro é obrigatória")
        @Schema(description = "Cor do carro.")
        Cor cor,

        @DecimalMax(value = "9,999,999.99", message = "O valorTotalParcial diário deve ser menor que R$ 9.999.999,00")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valorTotalParcial diário deve ser maior que zero")
        @NotNull(message = "O valorTotalParcial diário do aluguel é obrigatório")
        @Schema(description = "Valor da diária do aluguel do carro.")
        BigDecimal valorDiario,

        @NotNull(message = "A lista de acessórios não pode ser nula")
        @Size(min = 1, message = "O carro deve ter pelo menos um acessório")
        @Schema(description = "Lista de acessórios incluídos no carro.")
        List<Acessorio> acessorios,

        @NotNull(message = "O modelo do carro é obrigatório")
        @Schema(description = "Modelo do carro ao qual este carro pertence.")
        ModeloCarro modelo
) {
}