package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.validadores;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import jakarta.validation.Valid;

public interface ValidadorDadosCadastroCarro {
    void validarDadosCadastroCarro(@Valid DadosCadastroCarro dadosCadastroCarro);
}
