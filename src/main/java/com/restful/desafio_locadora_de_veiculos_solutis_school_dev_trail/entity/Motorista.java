package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.NONE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "Motorista")
@Table(
        name = "tb_motorista",
        schema = "db_locadora_veiculos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_motorista_numero_cnh", columnNames = "numerocnh")
        }
)
@PrimaryKeyJoinColumn(name = "pessoa_id")
@Schema(description = "Entidade que representa um motorista.")
public class Motorista extends Pessoa {

    @Column(nullable = false)
    @Schema(description = "Número da Carteira Nacional de Habilitação (CNH) do motorista.")
    private String numeroCNH;

    /**
     * Lista de aluguéis realizados por este motorista.
     * <p>
     * Representa o lado "um" no relacionamento um-para-muitos com a entidade {@link Aluguel}.
     * Cada {@link Motorista} pode estar associado a múltiplos {@link Aluguel}es.
     * A lista é carregada de forma "lazy", ou seja, somente quando for acessada.
     * </p>
     *
     * @see Aluguel
     */
    @OneToMany(mappedBy = "motorista", fetch = LAZY, cascade = ALL, orphanRemoval = true)

    @Setter(NONE)
    @Schema(description = "Lista de aluguéis realizados pelo motorista.")
    private List<Aluguel> alugueis = new ArrayList<>(); // Inicializa a lista de aluguéis com uma lista vazia pois um motorista pode não ter aluguéis associados

    public Motorista(@Valid DadosCadastroMotorista dadosCadastroMotorista) {
        this.setNome(dadosCadastroMotorista.nome());
        this.setDataNascimento(dadosCadastroMotorista.dataNascimento());
        this.setCpf(dadosCadastroMotorista.cpf());
        this.setEmail(dadosCadastroMotorista.email());
        this.setSexo(dadosCadastroMotorista.sexo());
        this.setLastUpdated(now());
        this.ativar();
        this.numeroCNH = dadosCadastroMotorista.numeroCNH();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        ofNullable(dadosAtualizacaoMotorista.nome()).ifPresent(this::setNome);
        ofNullable(dadosAtualizacaoMotorista.dataNascimento()).ifPresent(this::setDataNascimento);
        ofNullable(dadosAtualizacaoMotorista.email()).ifPresent(this::setEmail);
        ofNullable(dadosAtualizacaoMotorista.sexo()).ifPresent(this::setSexo);
        ofNullable(dadosAtualizacaoMotorista.numeroCNH()).ifPresent(value -> this.numeroCNH = value);
        ofNullable(dadosAtualizacaoMotorista.cpf()).ifPresent(this::setCpf);
        this.setLastUpdated(now());
    }

    public void adicionarAluguel(Aluguel aluguel) {
        this.alugueis.add(aluguel);
    }

    public void adicionarListaAlugueis(List<Aluguel> alugueis) {
        this.alugueis.addAll(alugueis);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Motorista that = (Motorista) o;

        return getId() != null &&
                Objects.equals(this.getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Motorista{" +
                "id=" +  this.getId() + // Chama o getId() da classe pai (Pessoa)
                ", nome='" +  this.getNome() + '\'' + // Chama o getNome() da classe pai (Pessoa)
                ", cpf='" +  this.getCpf() + '\'' + // Chama o getCpf() da classe pai (Pessoa)
                ", dataNascimento=" +  this.getDataNascimento() + // Chama o getDataNascimento() da classe pai (Pessoa)
                ", email='" +  this.getEmail() + '\'' + // Chama o getEmail() da classe pai (Pessoa)
                ", sexo=" +  this.getSexo() + // Chama o getSexo() da classe pai (Pessoa)
                ", ativo=" +  this.getAtivo() + // Chama o getAtivo() da classe pai (Pessoa)
                ", numeroCNH='" +  this.numeroCNH + '\'' +
                ", alugueis=" +  this.alugueis +
                ", dataCreated=" +  this.getDataCreated() + // Chama o getDataCreated() da classe pai (Pessoa)
                ", lastUpdated=" +  this.getLastUpdated() + // Chama o getLastUpdated() da classe pai (Pessoa)
                '}';
    }
}