package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Funcionário")
@Table(
        name = "tb_funcionario",
        schema = "db_locadora_veiculos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_funcionario_matricula", columnNames = "matricula"),
        }
)
@PrimaryKeyJoinColumn(name = "pessoa_id")
@Schema(description = "Entidade que representa um funcionário da locadora.")
public class Funcionario extends Pessoa {

    @Column(nullable = false)
    @Schema(description = "Matrícula única do funcionário.", example = "F00123")
    private String matricula;

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

        Funcionario that = (Funcionario) o;

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
        return "Funcionario{" +
                "id=" +  this.getId() + // Chama o getId() da classe pai (Pessoa)
                ", nome='" +  this.getNome() + '\'' + // Chama o getNome() da classe pai (Pessoa)
                ", cpf='" +  this.getCpf() + '\'' + // Chama o getCpf() da classe pai (Pessoa)
                ", dataNascimento=" +  this.getDataNascimento() + // Chama o getDataNascimento() da classe pai (Pessoa)
                ", email='" +  this.getEmail() + '\'' + // Chama o getEmail() da classe pai (Pessoa)
                ", matricula='" +  this.matricula + '\'' +
                ", dataCreated=" +  this.getDataCreated() + // Chama o getDataCreated() da classe pai (Pessoa)
                ", lastUpdated=" +  this.getLastUpdated() + // Chama o getLastUpdated() da classe pai (Pessoa)
                '}';
    }
}
