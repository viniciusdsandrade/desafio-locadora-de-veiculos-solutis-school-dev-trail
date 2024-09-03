package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.spec;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Aluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe utilitária para criar especificações JPA para a entidade {@link Motorista}.
 * <p>
 * Esta classe fornece métodos estáticos que retornam objetos {@link Specification}
 * para construir consultas dinâmicas para a entidade {@link Motorista},
 * permitindo a pesquisa de motoristas com base em diferentes critérios.
 * </p>
 */
@Schema(description = "Classe utilitária para criar especificações JPA para a entidade Motorista.")
public class MotoristaSpecs {

    /**
     * Cria uma especificação para pesquisar motoristas cujo nome contenha o valor especificado.
     *
     * @param nome O valor a ser pesquisado no nome do motorista.
     * @return A especificação JPA para a pesquisa por nome.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas cujo nome contenha o valor especificado.")
    public static Specification<Motorista> nomeContains(String nome) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    }

    /**
     * Cria uma especificação para pesquisar motoristas com o email especificado.
     *
     * @param email O email do motorista a ser pesquisado.
     * @return A especificação JPA para a pesquisa por email.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas com o email especificado.")
    public static Specification<Motorista> emailEquals(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    /**
     * Cria uma especificação para pesquisar motoristas com o CPF especificado.
     *
     * @param cpf O CPF do motorista a ser pesquisado.
     * @return A especificação JPA para a pesquisa por CPF.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas com o CPF especificado.")
    public static Specification<Motorista> cpfEquals(String cpf) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cpf"), cpf);
    }

    /**
     * Cria uma especificação para pesquisar motoristas com a data de nascimento especificada.
     *
     * @param dataNascimento A data de nascimento do motorista a ser pesquisada.
     * @return A especificação JPA para a pesquisa por data de nascimento.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas com a data de nascimento especificada.")
    public static Specification<Motorista> dataNascimentoEquals(LocalDate dataNascimento) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dataNascimento"), dataNascimento);
    }

    /**
     * Cria uma especificação para pesquisar motoristas com o número da CNH especificado.
     *
     * @param numeroCNH O número da CNH do motorista a ser pesquisado.
     * @return A especificação JPA para a pesquisa por número da CNH.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas com o número da CNH especificado.")
    public static Specification<Motorista> numeroCNHEquals(String numeroCNH) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("numeroCNH"), numeroCNH);
    }

    /**
     * Cria uma especificação para pesquisar motoristas que realizaram aluguéis com as placas especificadas.
     *
     * @param placasAlugueis A lista de placas dos carros alugados pelos motoristas a serem pesquisados.
     * @return A especificação JPA para a pesquisa por placas de aluguéis.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas que realizaram aluguéis com as placas especificadas.")
    public static Specification<Motorista> alugueisComPlacas(List<String> placasAlugueis) {
        return (root, query, criteriaBuilder) -> {
            Join<Motorista, Aluguel> alugueisJoin = root.join("alugueis");
            Join<Aluguel, Carro> carroJoin = alugueisJoin.join("carro");
            return criteriaBuilder.or(placasAlugueis.stream()
                    .map(placa -> criteriaBuilder.equal(carroJoin.get("placa"), placa))
                    .toArray(Predicate[]::new));
        };
    }

    /**
     * Cria uma especificação para pesquisar motoristas com o sexo especificado.
     *
     * @param sexo O sexo do motorista a ser pesquisado.
     * @return A especificação JPA para a pesquisa por sexo.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas com o sexo especificado.")
    public static Specification<Motorista> sexoEquals(String sexo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("sexo"), sexo);
    }

    /**
     * Cria uma especificação para pesquisar motoristas com a disponibilidade especificada.
     *
     * @param ativo A disponibilidade do motorista a ser pesquisada (true para ativo, false para inativo).
     * @return A especificação JPA para a pesquisa por disponibilidade.
     */
    @Schema(description = "Cria uma especificação para pesquisar motoristas com a disponibilidade especificada.")
    public static Specification<Motorista> ativoEquals(Boolean ativo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativo"), ativo);
    }
}