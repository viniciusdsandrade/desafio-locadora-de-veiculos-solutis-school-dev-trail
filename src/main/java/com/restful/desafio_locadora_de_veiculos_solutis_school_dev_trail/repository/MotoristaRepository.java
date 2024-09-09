package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("MotoristaRepository")
@Schema(description = "Repositório JPA para a entidade Motorista.")
public interface MotoristaRepository extends JpaRepository<Motorista, Long>, JpaSpecificationExecutor<Motorista> {

    @Schema(description = "Verifica se existe um motorista com o CPF informado.")
    boolean existsByCpf(String cpf);

    @Schema(description = "Verifica se existe um motorista com o e-mail informado.")
    boolean existsByEmail(String email);

    @Schema(description = "Verifica se existe um motorista com o número da CNH informado.")
    boolean existsByNumeroCNH(String numeroCNH);

    @Schema(description = "Busca um motorista pelo CPF.")
    Optional<Motorista> findByEmail(String email);

    @Schema(description = "Busca todos os motoristas ativos.")
    @Query(value = "SELECT m " +
                   "FROM Motorista m " +
                   "WHERE m.ativo = true",
            countQuery = "SELECT COUNT(m) " +
                         "FROM Motorista m " +
                         "WHERE m.ativo = true")
    Page<Motorista> findAllByAtivoTrue(Pageable pageable);

    @Schema(description = "Busca todos os motoristas inativos.")
    @Query(value = "SELECT m " +
                   "FROM Motorista m " +
                   "WHERE m.ativo = false",
            countQuery = "SELECT COUNT(m) " +
                         "FROM Motorista m " +
                         "WHERE m.ativo = false")
    Page<Motorista> findAllByAtivoFalse(Pageable pageable);

    @Schema(description = "Busca todos os motoristas.")
    @Query(value = "SELECT m FROM Motorista m",
            countQuery = "SELECT COUNT(m) FROM Motorista m")
    @NotNull
    Page<Motorista> findAll(@NotNull Pageable pageable);
}