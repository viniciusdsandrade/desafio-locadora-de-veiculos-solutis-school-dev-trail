package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.TipoPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusCarrinhoAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "CarrinhoAluguel")
@Table(
        name = "tb_carrinho_aluguel",
        schema = "db_locadora_veiculos"
)
public class CarrinhoAluguel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Schema(description = "Status do carrinho de aluguel.")
    private StatusCarrinhoAluguel statusCarrinho;

    @Enumerated(STRING)
    @Schema(description = "Tipo de pagamento utilizado para o aluguel.")
    private TipoPagamento tipoPagamento;

    @Enumerated(STRING)
    @Schema(description = "Status do pagamento do aluguel.")
    private StatusPagamento statusPagamento;

    @Schema(description = "Data em que o pagamento foi efetuado.")
    private LocalDate dataPagamento;

    @Schema(description = "Data em que o aluguel foi cancelado.")
    private LocalDate dataCancelamento;

    @Column(name = "campo_pix")
    private String campoPix;

    @Column(name = "campo_boleto")
    private String campoBoleto;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    @Column(name = "validade_cartao")
    private String validadeCartao;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "pagamento_dinheiro")
    private String pagamentoDinheiro;

    @Setter(NONE)
    @OneToMany(mappedBy = "carrinhoAluguel", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    @Schema(description = "Lista de alugueis do carrinho.")
    private List<Aluguel> alugueis = new ArrayList<>(); // Inicializa a lista, pois a entidade não depende de aluguel para existir

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    @Schema(description = "Motorista que possui o carrinho.")
    private Motorista motorista; // Motorista que possui o carrinho

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora da criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;

    public BigDecimal getValorTotal() {
        return alugueis.stream()
                .map(Aluguel::getValorTotalFinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
