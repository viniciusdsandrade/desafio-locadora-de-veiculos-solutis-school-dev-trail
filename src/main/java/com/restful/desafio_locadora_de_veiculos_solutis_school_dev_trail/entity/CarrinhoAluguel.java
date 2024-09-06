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

    @Column(name = "termos_aluguel")
    @Schema(description = "Termos do aluguel aceitos pelo cliente.")
    private String termos;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Schema(description = "Indica se o usuário aceitou os termos do aluguel.")
    private Boolean termosAceitos = false;

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora da criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;


    public void adicionarMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public void adicionarAluguel(Aluguel aluguel) {
        alugueis.add(aluguel);
        aluguel.setCarrinhoAluguel(this);
    }

    public void removerAluguel(Aluguel aluguel) {
        alugueis.remove(aluguel);
        aluguel.setCarrinhoAluguel(null);
    }

    public void adicionarCarro(Carro carro) {
        alugueis.forEach(aluguel -> aluguel.setCarro(carro));
    }

    public BigDecimal getValorTotalInicial() {
        return alugueis.stream()
                .map(Aluguel::getValorTotalInicial)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        CarrinhoAluguel that = (CarrinhoAluguel) o;

        return this.id.equals(that.id) &&
                this.statusCarrinho == that.statusCarrinho &&
                this.tipoPagamento == that.tipoPagamento &&
                this.statusPagamento == that.statusPagamento &&
                this.dataPagamento.equals(that.dataPagamento) &&
                this.dataCancelamento.equals(that.dataCancelamento) &&
                this.campoPix.equals(that.campoPix) &&
                this.campoBoleto.equals(that.campoBoleto) &&
                this.numeroCartao.equals(that.numeroCartao) &&
                this.validadeCartao.equals(that.validadeCartao) &&
                this.cvv.equals(that.cvv) &&
                this.pagamentoDinheiro.equals(that.pagamentoDinheiro) &&
                this.alugueis.equals(that.alugueis) &&
                this.motorista.equals(that.motorista) &&
                this.dataCreated.equals(that.dataCreated) &&
                this.lastUpdated.equals(that.lastUpdated);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id.hashCode();

        result *= prime + statusCarrinho.hashCode();
        result *= prime + tipoPagamento.hashCode();
        result *= prime + statusPagamento.hashCode();
        result *= prime + dataPagamento.hashCode();
        result *= prime + dataCancelamento.hashCode();
        result *= prime + campoPix.hashCode();
        result *= prime + campoBoleto.hashCode();
        result *= prime + numeroCartao.hashCode();
        result *= prime + validadeCartao.hashCode();
        result *= prime + cvv.hashCode();
        result *= prime + pagamentoDinheiro.hashCode();
        result *= prime + alugueis.hashCode();
        result *= prime + motorista.hashCode();
        result *= prime + dataCreated.hashCode();
        result *= prime + lastUpdated.hashCode();

        if (result < 0) result = -result;

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CarrinhoAluguel{");
        sb.append("id=").append(id);
        sb.append(", statusCarrinho=").append(statusCarrinho);
        sb.append(", tipoPagamento=").append(tipoPagamento);
        sb.append(", statusPagamento=").append(statusPagamento);
        sb.append(", dataPagamento=").append(dataPagamento);
        sb.append(", dataCancelamento=").append(dataCancelamento);

        if (campoPix != null) sb.append(", campoPix='").append(campoPix).append('\'');
        if (campoBoleto != null) sb.append(", campoBoleto='").append(campoBoleto).append('\'');
        if (numeroCartao != null) sb.append(", numeroCartao='").append(numeroCartao).append('\'');
        if (validadeCartao != null) sb.append(", validadeCartao='").append(validadeCartao).append('\'');
        if (cvv != null) sb.append(", cvv='").append(cvv).append('\'');
        if (pagamentoDinheiro != null) sb.append(", pagamentoDinheiro='").append(pagamentoDinheiro).append('\'');

        sb.append(", alugueis=[");
        for (int i = 0; i < alugueis.size(); i++) {
            sb.append(alugueis.get(i).toString()); // Chama o toStringResumido() de Aluguel
            if (i < alugueis.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");

        sb.append(", motorista=").append(motorista.toString()); // Chama o toStringResumido() de Motorista

        sb.append(", dataCreated=").append(dataCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append(", valorTotalInicial=").append(getValorTotalInicial()); // Inclui o valor total
        sb.append('}');
        return sb.toString();
    }


}
