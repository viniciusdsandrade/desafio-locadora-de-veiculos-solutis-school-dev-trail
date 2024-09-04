package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do carrinho de aluguel.")
public enum StatusCarrinhoAluguel {
    @Schema(description = "Carrinho de aluguel aberto, aguardando itens.")
    ABERTO,

    @Schema(description = "Carrinho de aluguel fechado, pronto para pagamento.")
    FECHADO
}