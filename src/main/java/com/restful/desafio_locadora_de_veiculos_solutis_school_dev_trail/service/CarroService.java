package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosAtualizacaoCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosDetalhamentoCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Interface que define os serviços relacionados a carros.")
public interface CarroService {

    @Transactional
    Carro cadastrarCarro(@Valid DadosCadastroCarro dadosCadastroCarro);

    @Schema(description = "Busca um carro pelo seu identificador.")
    Carro buscarPorId(Long id);

    @Transactional(rollbackOn = Exception.class)
    @Schema(description = "Atualiza um carro.")
    Carro atualizarCarro(@Valid DadosAtualizacaoCarro dadosAtualizarCarro);

    @Transactional
    @Schema(description = "Exclui um carro.")
    void excluirCarro(Long id);

    @Transactional
    @Schema(description = "Disponibiliza um carro para aluguel.")
    void disponibilizarCarroAluguel(Long id);

    @Transactional
    @Schema(description = "Bloqueia um carro para aluguel.")
    void bloquearCarroAluguel(Long id);

    @Schema(description = "Lista os carros cadastrados.")
    Page<DadosListagemCarro> listar(Pageable paginacao);

    @Schema(description = "Lista os carros disponíveis para aluguel.")
    Page<DadosListagemCarro> listarCarrosDisponiveis(Pageable paginacao);

    @Schema(description = "Lista os carros alugados.")
    Page<DadosListagemCarro> listarCarrosAlugados(Pageable paginacao);

    @Schema(description = "Pesquisa carros com base nos filtros informados.")
    Page<DadosDetalhamentoCarro> pesquisarCarros(
            String nome,
            String placa,
            String chassi,
            String cor,
            Boolean disponivel,
            BigDecimal valorDiariaMin,
            BigDecimal valorDiariaMax,
            String modeloDescricao,
            String fabricanteNome,
            String categoriaNome,
            List<String> acessoriosNomes,
            Pageable paginacao
    );
}