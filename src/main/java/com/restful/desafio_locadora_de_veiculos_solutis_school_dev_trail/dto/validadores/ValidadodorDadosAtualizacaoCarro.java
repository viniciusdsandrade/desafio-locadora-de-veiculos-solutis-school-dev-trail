package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.validadores;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosAtualizacaoCarro;
import jakarta.validation.Valid;

public interface ValidadodorDadosAtualizacaoCarro {
    void validarAtualizacaoCarro(@Valid DadosAtualizacaoCarro dadosAtualizacaoCarro);
}
