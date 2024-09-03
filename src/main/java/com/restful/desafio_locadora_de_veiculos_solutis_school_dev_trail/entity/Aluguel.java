package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ApoliceSeguro.calcularValorTotalApoliceSeguro;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_WRITE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.NONE;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.math.BigDecimal.valueOf;
import static java.time.temporal.ChronoUnit.DAYS;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "Aluguel")
@Table(name = "tb_aluguel", schema = "db_locadora_veiculos")
@Schema(description = "Representa um aluguel de carro.")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Data em que o aluguel foi solicitado.")
    private LocalDate dataPedido;

    @Column(nullable = false)
    @Schema(description = "Data em que o carro foi entregue ao motorista.")
    private LocalDate dataRetirada;

    @Column(nullable = false)
    @Schema(description = "Data prevista para a devolução do carro.")
    private LocalDate dataDevolucaoPrevista;

    @Schema(description = "Data efetiva da devolução do carro (pode ser nula).")
    private LocalDate dataDevolucaoEfetiva;

    @Column(name = "valor_total_inicial", precision = 10, scale = 2)
    @Schema(description = "Valor total inicial do aluguel, calculado com base na data de devolução prevista.")
    private BigDecimal valorTotalInicial;

    @Column(precision = 10, scale = 2)
    @Schema(description = "Valor total final do aluguel, calculado com base na data de devolução efetiva (se disponível).")
    private BigDecimal valorTotalFinal;

    @Enumerated(STRING)
    @Schema(description = "Status atual do aluguel.")
    private StatusAluguel statusAluguel;

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

    @Getter
    @Transient
    private Map<String, Object> camposAdicionais = new HashMap<>();

    /**
     * Motorista que realizou este aluguel.
     * <p>
     * Representa o lado "muitos" no relacionamento um-para-muitos com a entidade {@link Motorista}.
     * Cada {@link Aluguel} está associado a um único {@link Motorista}.
     * O atributo `motorista_id` na tabela `tb_aluguel` atua como chave estrangeira, referenciando
     * a tabela `tb_motorista`.
     * </p>
     *
     * @see Motorista
     */
    @JsonIgnore
    @Setter(NONE)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    @Schema(description = "Motorista que realizou o aluguel.")
    private Motorista motorista;

    /**
     * Carro associado a este aluguel.
     * <p>
     * Representa o lado "muitos" no relacionamento um-para-muitos com a entidade {@link Carro}.
     * Cada {@link Aluguel} está obrigatoriamente associado a um único {@link Carro}.
     * O atributo `carro_id` na tabela `tb_aluguel` atua como chave estrangeira, referenciando
     * a tabela `tb_carro`.
     * </p>
     *
     * @see Carro
     */
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    @Schema(description = "Carro alugado.")
    private Carro carro;

    /**
     * Apólice de seguro associada ao aluguel.
     * <p>
     * Este campo estabelece uma relação um-para-um com a {@link ApoliceSeguro}. Cada aluguel deve
     * ter uma apólice de seguro associada, e a apólice é obrigatória. A anotação {@code @JoinColumn}
     * define a coluna de junção na tabela 'tb_aluguel' que referencia a apólice de seguro.
     * </p>
     *
     * @see ApoliceSeguro
     */
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "apolice_seguro_id", nullable = false)
    @Schema(description = "Apólice de seguro associada ao aluguel.")
    private ApoliceSeguro apoliceSeguro;

    @CreationTimestamp
    @Setter(NONE)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    @Schema(description = "Data e hora da criação do registro.", accessMode = READ_ONLY)
    private LocalDateTime dataCreated;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro.", accessMode = READ_WRITE)
    private LocalDateTime lastUpdated;

    public void adicionarCampo(String nome, Object valor) {
        camposAdicionais.put(nome, valor);
    }

    public void adicionarApoliceSeguro(ApoliceSeguro apoliceSeguro) {
        this.apoliceSeguro = apoliceSeguro;
    }

    public void adicionarCarro(Carro carro) {
        this.carro = carro;
    }

    public void adicionarMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    @PostLoad
    private void calcularValores() {
        this.valorTotalInicial = calcularValorTotal(dataDevolucaoPrevista);
        this.valorTotalFinal = dataDevolucaoEfetiva != null ? calcularValorTotal(dataDevolucaoEfetiva) : valorTotalInicial;
    }

    @Schema(description = "Calcula o valorTotalParcial total do aluguel (com base na data de devolução e no valorTotalParcial da apólice de seguro).")
    private BigDecimal calcularValorTotal(LocalDate dataDevolucao) {
        long quantidadeDias = DAYS.between(dataRetirada, dataDevolucao);
        BigDecimal valorDiarias = carro.getValorDiaria().multiply(valueOf(quantidadeDias));
        return valorDiarias.add(calcularValorFranquia());
    }

    private BigDecimal calcularValorFranquia() {
        return calcularValorTotalApoliceSeguro(
                apoliceSeguro.getProtecaoTerceiro(),
                apoliceSeguro.getProtecaoCausasNaturais(),
                apoliceSeguro.getProtecaoRoubo()
        );
    }

    @Override
    public String toString() {
        return "Aluguel{id=" + id + ", dataPedido=" + dataPedido + ", dataRetirada=" + dataRetirada + ", dataDevolucaoPrevista=" + dataDevolucaoPrevista + ", valorTotalParcial=" + valorTotalFinal + ", motorista=" + motorista + ", carro=" + carro + ", apoliceSeguro=" + apoliceSeguro + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Aluguel aluguel = (Aluguel) o;
        return getId() != null && Objects.equals(getId(), aluguel.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}