package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.impl;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Aluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.repository.AluguelRepository;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.AluguelService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service("aluguelService")
@Schema(description = "Implementação dos serviços relacionados ao aluguel de veículos.")
public class AluguelServiceImpl implements AluguelService {

    private static final Logger log = getLogger(AluguelServiceImpl.class);

    private final AluguelRepository aluguelRepository;

    public AluguelServiceImpl(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    @Schema(description = "Verifica se existe um aluguel pelo id.")
    private Aluguel existeAluguelPeloId(Long id) {
        return aluguelRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Aluguel não encontrado pelo id: {}", id);
                    return new EntityNotFoundException("Aluguel não encontrado.");
                });
    }


}
