package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.NONE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "MetodoPagamento")
@Table(
        name = "tb_metodo_pagamento",
        schema = "db_locadora_veiculos"
)
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Schema(description = "Tipo de pagamento utilizado.")
    private TipoPagamento tipoPagamento;

    @Enumerated(STRING)
    @Schema(description = "Status do pagamento.")
    private StatusPagamento statusPagamento;

    @Schema(description = "Data em que o pagamento foi efetuado.")
    private LocalDate dataPagamento;

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

    @OneToOne(mappedBy = "metodoPagamento", cascade = ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private CarrinhoAluguel carrinhoAluguel;

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora da criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        MetodoPagamento that = (MetodoPagamento) o;

        return this.id.equals(that.id) &&
               this.tipoPagamento == that.tipoPagamento &&
               this.statusPagamento == that.statusPagamento &&
               this.dataPagamento.equals(that.dataPagamento) &&
               this.campoPix.equals(that.campoPix) &&
               this.campoBoleto.equals(that.campoBoleto) &&
               this.numeroCartao.equals(that.numeroCartao) &&
               this.validadeCartao.equals(that.validadeCartao) &&
               this.cvv.equals(that.cvv) &&
               this.pagamentoDinheiro.equals(that.pagamentoDinheiro) &&
               this.carrinhoAluguel.equals(that.carrinhoAluguel) &&
               this.dataCreated.equals(that.dataCreated) &&
               this.lastUpdated.equals(that.lastUpdated);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id.hashCode();

        result *= prime + tipoPagamento.hashCode();
        result *= prime + statusPagamento.hashCode();
        result *= prime + dataPagamento.hashCode();
        result *= prime + campoPix.hashCode();
        result *= prime + campoBoleto.hashCode();
        result *= prime + numeroCartao.hashCode();
        result *= prime + validadeCartao.hashCode();
        result *= prime + cvv.hashCode();
        result *= prime + pagamentoDinheiro.hashCode();
        result *= prime + carrinhoAluguel.hashCode();
        result *= prime + dataCreated.hashCode();
        result *= prime + lastUpdated.hashCode();

        if (result < 0) result *= -1;

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MetodoPagamento{");
        sb.append("id=").append(id);
        sb.append(", tipoPagamento=").append(tipoPagamento);
        sb.append(", statusPagamento=").append(statusPagamento);
        sb.append(", dataPagamento=").append(dataPagamento);
        sb.append(", campoPix='").append(campoPix).append('\'');
        sb.append(", campoBoleto='").append(campoBoleto).append('\'');
        sb.append(", numeroCartao='").append(numeroCartao).append('\'');
        sb.append(", validadeCartao='").append(validadeCartao).append('\'');
        sb.append(", cvv='").append(cvv).append('\'');
        sb.append(", pagamentoDinheiro='").append(pagamentoDinheiro).append('\'');
        if (carrinhoAluguel != null)
            sb.append(", carrinhoAluguel=").append(carrinhoAluguel.toString()); // Chama o toString() de CarrinhoAluguel
        else sb.append(", carrinhoAluguel=null");
        sb.append(", dataCreated=").append(dataCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}