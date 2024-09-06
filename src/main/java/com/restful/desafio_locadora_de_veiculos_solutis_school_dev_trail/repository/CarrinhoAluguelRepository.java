package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.CarrinhoAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository("carrinhoRepository")
@Schema(description = "Repository para acessar a entidade CarrinhoAluguel")
public interface CarrinhoAluguelRepository extends JpaRepository<CarrinhoAluguel, Long>, JpaSpecificationExecutor<CarrinhoAluguel> {
}
