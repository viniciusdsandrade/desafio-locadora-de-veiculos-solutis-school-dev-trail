package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Acessorio;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ModeloCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.Cor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;

@Schema(description = "Dados para atualizar um carro existente.")
public record DadosAtualizacaoCarro(

        @NotNull(message = "ID é obrigatório")
        @Schema(description = "ID do carro a ser atualizado.", example = "1")
        Long id,

        @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
        @Schema(description = "Nome do modelo do carro.", example = "Corolla")
        String nome,

        @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "A placa deve seguir o formato ABC-1234")
        @Schema(description = "Placa do carro.", example = "ABC-1234")
        String placa,

        @Size(min = 17, max = 17, message = "O chassi deve ter 17 caracteres")
        @Schema(description = "Chassi do carro.", example = "1HGBH41JXMN109186")
        String chassi,

        @Schema(description = "Cor do carro.", example = "Preto")
        Cor cor,

        @DecimalMin(value = "0.0", inclusive = false, message = "O valorTotalParcial diário deve ser maior que zero")
        @Schema(description = "Valor da diária do aluguel do carro.", example = "150.00")
        BigDecimal valorDiario,

        @Schema(description = "Lista de acessórios do carro.")
        List<Acessorio> acessorio,

        @Schema(description = "Modelo do carro.")
        ModeloCarro modelo,

        @UpdateTimestamp
        @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Schema(description = "Data e hora da última atualização do registro do motorista.", accessMode = READ_WRITE)
        LocalDateTime lastUpdated
) {
}