package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Aluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("AluguelRepository")
@Schema(description = "Repositório JPA para a entidade Aluguel.")
public interface AluguelRepository extends JpaRepository<Aluguel, Long>, JpaSpecificationExecutor<Aluguel> {
}
