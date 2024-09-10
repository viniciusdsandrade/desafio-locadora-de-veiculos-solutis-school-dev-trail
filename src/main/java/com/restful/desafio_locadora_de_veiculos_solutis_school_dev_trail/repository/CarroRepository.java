package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("CarroRepository")
@Schema(description = "Repositório JPA para a entidade Carro.")
public interface CarroRepository extends JpaRepository<Carro, Long>, JpaSpecificationExecutor<Carro> {

    @Schema(description = "Verifica se existe um carro com a placa informada.")
    boolean existsByPlaca(String placa);

    @Schema(description = "Verifica se existe um carro com o chassi informado.")
    boolean existsByChassi(String chassi);

    @Query(value = "SELECT DISTINCT c " +
                   "FROM Carro c " +
                   "LEFT JOIN FETCH c.modeloCarro mc " +
                   "LEFT JOIN FETCH mc.fabricante " +
                   "LEFT JOIN FETCH c.acessorios " +
                   "WHERE c IN (SELECT c2 FROM Carro c2 LEFT JOIN c2.alugueis)",
            countQuery = "SELECT COUNT(DISTINCT c) " +
                         "FROM Carro c " +
                         "LEFT JOIN c.alugueis")
    @Schema(description = "Busca todos os carros.")
    @NotNull
    Page<Carro> findAll(@NotNull Pageable paginacao);

    @Query(value = "SELECT DISTINCT c " +
                   "FROM Carro c " +
                   "LEFT JOIN FETCH c.modeloCarro mc " +
                   "LEFT JOIN FETCH mc.fabricante " +
                   "LEFT JOIN FETCH c.acessorios " +
                   "WHERE c.disponivel = true AND c IN (SELECT c2 FROM Carro c2 LEFT JOIN c2.alugueis)",
            countQuery = "SELECT COUNT(DISTINCT c) " +
                         "FROM Carro c " +
                         "LEFT JOIN c.alugueis " +
                         "WHERE c.disponivel = true")
    Page<Carro> findAllByDisponivelTrue(Pageable pageable);

    @Query(value = "SELECT DISTINCT c " +
                   "FROM Carro c " +
                   "LEFT JOIN FETCH c.modeloCarro mc " +
                   "LEFT JOIN FETCH mc.fabricante " +
                   "LEFT JOIN FETCH c.acessorios " +
                   "WHERE c.disponivel = false AND c IN (SELECT c2 FROM Carro c2 LEFT JOIN c2.alugueis)",
            countQuery = "SELECT COUNT(DISTINCT c) " +
                         "FROM Carro c " +
                         "LEFT JOIN c.alugueis " +
                         "WHERE c.disponivel = false")
    Page<Carro> findAllByDisponivelFalse(Pageable paginacao);
}