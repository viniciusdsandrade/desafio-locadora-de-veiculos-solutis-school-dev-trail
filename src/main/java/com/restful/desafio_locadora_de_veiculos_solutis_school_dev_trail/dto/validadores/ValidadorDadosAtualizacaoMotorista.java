package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.validadores;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import jakarta.validation.Valid;

public interface ValidadorDadosAtualizacaoMotorista {
    void validarAtualizacaoMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista);
}
