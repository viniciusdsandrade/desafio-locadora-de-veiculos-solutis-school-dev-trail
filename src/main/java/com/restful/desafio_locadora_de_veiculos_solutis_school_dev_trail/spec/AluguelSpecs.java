package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.spec;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Aluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Motorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe utilitária para criar especificações JPA para a entidade {@link Aluguel}.
 * <p>
 * Esta classe fornece métodos estáticos que retornam objetos {@link Specification}
 * para construir consultas dinâmicas para a entidade {@link Aluguel},
 * permitindo a pesquisa de aluguéis com base em diferentes critérios.
 * </p>
 */
@Schema(description = "Classe utilitária para criar especificações JPA para a entidade Aluguel.")
public class AluguelSpecs {

    /**
     * Cria uma especificação para pesquisar aluguéis com o status especificado.
     *
     * @param statusAluguel O status do aluguel a ser pesquisado.
     * @return A especificação JPA para a pesquisa por status do aluguel.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com o status especificado.")
    public static Specification<Aluguel> statusEquals(StatusAluguel statusAluguel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("statusAluguel"), statusAluguel);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de pedido dentro do intervalo especificado.
     *
     * @param dataInicio A data inicial do intervalo.
     * @param dataFim    A data final do intervalo.
     * @return A especificação JPA para a pesquisa por data de pedido.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de pedido dentro do intervalo especificado.")
    public static Specification<Aluguel> dataPedidoBetween(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("dataPedido"), dataInicio, dataFim);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de retirada dentro do intervalo especificado.
     *
     * @param dataInicio A data inicial do intervalo.
     * @param dataFim    A data final do intervalo.
     * @return A especificação JPA para a pesquisa por data de retirada.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de retirada dentro do intervalo especificado.")
    public static Specification<Aluguel> dataRetiradaBetween(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("dataRetirada"), dataInicio, dataFim);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de devolução prevista dentro do intervalo especificado.
     *
     * @param dataInicio A data inicial do intervalo.
     * @param dataFim    A data final do intervalo.
     * @return A especificação JPA para a pesquisa por data de devolução prevista.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de devolução prevista dentro do intervalo especificado.")
    public static Specification<Aluguel> dataDevolucaoPrevistaBetween(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("dataDevolucaoPrevista"), dataInicio, dataFim);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com a data de devolução efetiva dentro do intervalo especificado.
     *
     * @param dataInicio A data inicial do intervalo.
     * @param dataFim    A data final do intervalo.
     * @return A especificação JPA para a pesquisa por data de devolução efetiva.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com a data de devolução efetiva dentro do intervalo especificado.")
    public static Specification<Aluguel> dataDevolucaoEfetivaBetween(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("dataDevolucaoEfetiva"), dataInicio, dataFim);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com o valor total inicial dentro do intervalo especificado.
     *
     * @param valorMinimo O valor mínimo do intervalo.
     * @param valorMaximo O valor máximo do intervalo.
     * @return A especificação JPA para a pesquisa por valor total inicial.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com o valor total inicial dentro do intervalo especificado.")
    public static Specification<Aluguel> valorTotalInicialBetween(BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("valorTotalInicial"), valorMinimo, valorMaximo);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis com o valor total final dentro do intervalo especificado.
     *
     * @param valorMinimo O valor mínimo do intervalo.
     * @param valorMaximo O valor máximo do intervalo.
     * @return A especificação JPA para a pesquisa por valor total final.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis com o valor total final dentro do intervalo especificado.")
    public static Specification<Aluguel> valorTotalFinalBetween(BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("valorTotalFinal"), valorMinimo, valorMaximo);
    }

    /**
     * Cria uma especificação para pesquisar aluguéis realizados por motoristas com os emails especificados.
     *
     * @param emailsMotoristas A lista de emails dos motoristas que realizaram os aluguéis.
     * @return A especificação JPA para a pesquisa por emails de motoristas.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis realizados por motoristas com os emails especificados.")
    public static Specification<Aluguel> motoristasComEmails(List<String> emailsMotoristas) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Motorista> motoristaJoin = root.join("motorista");
            return criteriaBuilder.or(emailsMotoristas.stream()
                    .map(email -> criteriaBuilder.equal(motoristaJoin.get("email"), email))
                    .toArray(Predicate[]::new));
        };
    }

    /**
     * Cria uma especificação para pesquisar aluguéis de carros com as placas especificadas.
     *
     * @param placasCarros A lista de placas dos carros alugados.
     * @return A especificação JPA para a pesquisa por placas de carros.
     */
    @Schema(description = "Cria uma especificação para pesquisar aluguéis de carros com as placas especificadas.")
    public static Specification<Aluguel> carrosComPlacas(List<String> placasCarros) {
        return (root, query, criteriaBuilder) -> {
            Join<Aluguel, Carro> carroJoin = root.join("carro");
            return criteriaBuilder.or(placasCarros.stream()
                    .map(placa -> criteriaBuilder.equal(carroJoin.get("placa"), placa))
                    .toArray(Predicate[]::new));
        };
    }
}