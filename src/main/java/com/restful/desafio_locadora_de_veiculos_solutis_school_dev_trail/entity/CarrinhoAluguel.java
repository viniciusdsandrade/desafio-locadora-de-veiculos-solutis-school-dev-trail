package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusCarrinhoAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
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

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status do carrinho de aluguel.")
    private StatusCarrinhoAluguel statusCarrinho;

    @Schema(description = "Data em que o aluguel foi cancelado.")
    private LocalDate dataCancelamento;

    @Setter(NONE)
    @OneToMany(mappedBy = "carrinhoAluguel", cascade = ALL, orphanRemoval = true, fetch = LAZY)
    @Schema(description = "Lista de alugueis do carrinho.")
    private List<Aluguel> alugueis = new ArrayList<>(); // Inicializa a lista, pois a entidade não depende de aluguel para existir

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "motorista_id", nullable = false)
    @Schema(description = "Motorista que possui o carrinho.")
    private Motorista motorista; // Motorista que possui o carrinho

    @Column(name = "termos_aluguel")
    @Schema(description = "Termos do aluguel aceitos pelo cliente.")
    private String termos;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Schema(description = "Indica se o usuário aceitou os termos do aluguel.")
    private Boolean termosAceitos = false;

    @OneToOne(cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @JoinColumn(name = "metodo_pagamento_id", unique = true)
    @Schema(description = "Método de pagamento do carrinho.")
    private MetodoPagamento metodoPagamento;

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

    public void adicionarMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
        metodoPagamento.setCarrinhoAluguel(this);
    }

    public void removerMetodoPagamento() {
        if (this.metodoPagamento != null) {
            this.metodoPagamento.setCarrinhoAluguel(null);
            this.metodoPagamento = null;
        }
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

        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        CarrinhoAluguel that = (CarrinhoAluguel) o;

        return this.id.equals(that.id) &&
               this.statusCarrinho == that.statusCarrinho &&
               this.dataCancelamento.equals(that.dataCancelamento) &&
               this.alugueis.equals(that.alugueis) &&
               this.motorista.equals(that.motorista) &&
               this.termos.equals(that.termos) &&
               this.termosAceitos.equals(that.termosAceitos) &&
               this.metodoPagamento.equals(that.metodoPagamento) &&
               this.dataCreated.equals(that.dataCreated) &&
               this.lastUpdated.equals(that.lastUpdated);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id.hashCode();

        result *= prime + statusCarrinho.hashCode();
        result *= prime + dataCancelamento.hashCode();
        result *= prime + alugueis.hashCode();
        result *= prime + motorista.hashCode();
        result *= prime + termos.hashCode();
        result *= prime + termosAceitos.hashCode();
        result *= prime + metodoPagamento.hashCode();
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
        sb.append(", dataCancelamento=").append(dataCancelamento);

        sb.append(", alugueis=[");
        for (int i = 0; i < alugueis.size(); i++) {
            sb.append(alugueis.get(i).toString()); // Chama o toStringResumido() de Aluguel
            if (i < alugueis.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");

        sb.append(", motorista=").append(motorista.toString()); // Chama o toStringResumido() de Motorista
        sb.append(", termos='").append(termos).append('\'');
        sb.append(", termosAceitos=").append(termosAceitos);
        sb.append(", metodoPagamento=").append(metodoPagamento.toString()); // Chama o toStringResumido() de MetodoPagamento
        sb.append(", dataCreated=").append(dataCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append(", valorTotalInicial=").append(getValorTotalInicial()); // Inclui o valor total
        sb.append('}');
        return sb.toString();
    }
}
