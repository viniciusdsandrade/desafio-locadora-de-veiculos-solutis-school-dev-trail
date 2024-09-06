package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carrinhoAluguel;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.CarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusCarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.TipoPagamento;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DadosListagemCarrinhoAluguel(
        Long id,
        StatusCarrinhoAluguel statusCarrinho,
        TipoPagamento tipoPagamento,
        StatusPagamento statusPagamento,
        LocalDate dataPagamento,
        LocalDate dataCancelamento,
        String campoPix,
        String campoBoleto,
        String numeroCartao,
        String validadeCartao,
        String cvv,
        String pagamentoDinheiro,
        List<DadosListagemAluguel> alugueis,
        DadosListagemMotorista motorista,
        String termos,
        Boolean termosAceitos,
        BigDecimal valorTotalInicial,
        LocalDateTime dataCreated,
        LocalDateTime lastUpdated
) {

    public DadosListagemCarrinhoAluguel(CarrinhoAluguel carrinhoAluguel) {
        this(
                carrinhoAluguel.getId(),
                carrinhoAluguel.getStatusCarrinho(),
                carrinhoAluguel.getTipoPagamento(),
                carrinhoAluguel.getStatusPagamento(),
                carrinhoAluguel.getDataPagamento(),
                carrinhoAluguel.getDataCancelamento(),
                carrinhoAluguel.getCampoPix(),
                carrinhoAluguel.getCampoBoleto(),
                carrinhoAluguel.getNumeroCartao(),
                carrinhoAluguel.getValidadeCartao(),
                carrinhoAluguel.getCvv(),
                carrinhoAluguel.getPagamentoDinheiro(),
                carrinhoAluguel.getAlugueis()
                        .stream()
                        .map(DadosListagemAluguel::new)
                        .toList(),
                new DadosListagemMotorista(carrinhoAluguel.getMotorista()),
                carrinhoAluguel.getTermos(),
                carrinhoAluguel.getTermosAceitos(),
                carrinhoAluguel.getValorTotalInicial(),
                carrinhoAluguel.getDataCreated(),
                carrinhoAluguel.getLastUpdated()
        );
    }
}