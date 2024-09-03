package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.DescricaoAcessorio;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static lombok.AccessLevel.NONE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Acessorio")
@Table(
        name = "tb_acessorio",
        schema = "db_locadora_veiculos",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_acessorio_descricao", columnNames = "descricao")
        }
)
@Schema(description = "Representa um acessório de carro.")
public class Acessorio {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    @Schema(description = "Descrição do tipo de acessório.")
    private DescricaoAcessorio descricao;

    /**
     * Conjunto de carros que possuem este acessório.
     * <p>
     * Representa o lado "muitos" no relacionamento muitos-para-muitos com a entidade {@link Carro}.
     * Um acessório pode estar presente em vários carros, e a lista de carros é mapeada através da tabela
     * de junção `tb_carro_acessorio`.
     * </p>
     * <p>
     * A anotação `@ManyToMany(mappedBy = "acessorios")` indica que a relação é gerenciada pela
     * entidade `Carro`, através do atributo `acessorios` na classe `Carro`.
     * </p>
     * <p>
     * A anotação `@Column(nullable = true)` indica que a lista de carros pode ser nula, ou seja,
     * um acessório pode não estar presente em nenhum carro.
     * </p>
     * <p>
     * A anotação `@Setter(AccessLevel.NONE)` impede a modificação direta do conjunto de carros.
     * A manipulação do conjunto deve ser feita por meio de métodos específicos na classe `Acessorio`,
     * garantindo a consistência dos dados.
     * </p>
     * <p>
     * A utilização de `Set` ao invés de `List` garante que não haja carros duplicados na lista,
     * já que um carro não pode ter o mesmo acessório mais de uma vez. Além disso, `Set` não garante
     * a ordem de inserção dos carros, o que é adequado nesse contexto, pois a ordem dos carros
     * que possuem um acessório não é relevante.
     * </p>
     */
    @ManyToMany(mappedBy = "acessorios", fetch = LAZY)
    @Setter(NONE)
    @Schema(description = "Conjunto de carros que possuem este acessório.")
    private Set<Carro> carros = new HashSet<>(); // Inicializado, pois um acessório não precisa estar associado a nenhum carro

    public void adicionarCarro(Carro carro) {
        carros.add(carro);
        carro.getAcessorios().add(this);
    }

    public void removerCarro(Carro carro) {
        carros.remove(carro);
        carro.getAcessorios().remove(this);
    }

    public void adicionarCarros(Set<Carro> carros) {
        carros.forEach(this::adicionarCarro);
    }

    public void removerCarros(Set<Carro> carros) {
        carros.forEach(this::removerCarro);
    }

    @Override
    public String toString() {
        return "Acessorio{id=" + id + ", descricao=" + descricao + '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Acessorio acessorio = (Acessorio) o;
        return getId() != null && Objects.equals(getId(), acessorio.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}