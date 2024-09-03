package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("CarroRepository")
@Schema(description = "Repositório JPA para a entidade Carro.")
public interface CarroRepository extends JpaRepository<Carro, Long>, JpaSpecificationExecutor<Carro> {

    @Schema(description = "Verifica se existe um carro com a placa informada.")
    boolean existsByPlaca(String placa);

    @Schema(description = "Verifica se existe um carro com o chassi informado.")
    boolean existsByChassi(String chassi);

    @Schema(description = "Busca um carro pela placa.")
    Page<Carro> findAllByDisponivelTrue(Pageable pageable);

    @Schema(description = "Busca um carro pelo chassi.")
    Page<Carro> findAllByDisponivelFalse(Pageable paginacao);
}