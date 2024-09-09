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

    @Schema(description = "Busca todos os carros.")
    @Query(value = "SELECT c " +
                   "FROM Carro c " +
                   "JOIN FETCH c.acessorios " +
                   "JOIN FETCH c.modeloCarro",
            countQuery = "SELECT COUNT(c) " +
                         "FROM Carro c")
    @NotNull
    Page<Carro> findAll(@NotNull Pageable paginacao);

    @Schema(description = "Busca um carro pela placa.")
    @Query(value = "SELECT c " +
                   "FROM Carro c " +
                   "JOIN FETCH c.acessorios " +
                   "JOIN FETCH c.modeloCarro " +
                   "WHERE c.disponivel = true",
            countQuery = "SELECT COUNT(c) " +
                         "FROM Carro c " +
                         "WHERE c.disponivel = true")
    Page<Carro> findAllByDisponivelTrue(Pageable pageable);

    @Schema(description = "Busca um carro pelo chassi.")
    @Query(value = "SELECT c " +
                   "FROM Carro c " +
                   "JOIN FETCH c.acessorios " +
                   "JOIN FETCH c.modeloCarro " +
                   "WHERE c.disponivel = false",
            countQuery = "SELECT COUNT(c) " +
                         "FROM Carro c " +
                         "WHERE c.disponivel = false")
    Page<Carro> findAllByDisponivelFalse(Pageable paginacao);
}