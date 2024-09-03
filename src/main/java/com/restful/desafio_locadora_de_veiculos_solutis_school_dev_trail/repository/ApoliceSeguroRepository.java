package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.ApoliceSeguro;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ApoliceSeguroRepository")
@Schema(description = "Repositório JPA para a entidade ApoliceSeguro.")
public interface ApoliceSeguroRepository extends JpaRepository<ApoliceSeguro, Long> {

    @Schema(description = "Encontra uma apólice de seguro por suas coberturas.")
    ApoliceSeguro findByProtecaoCausasNaturaisAndProtecaoTerceiroAndProtecaoRoubo(
            boolean coberturaRoubo,
            boolean coberturaTerceiros,
            boolean coberturaAcidentes
    );
}