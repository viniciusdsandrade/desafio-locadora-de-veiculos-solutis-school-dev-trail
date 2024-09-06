package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosAtualizacaoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carrinhoAluguel.DadosListagemCarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.CarrinhoAluguel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Schema(description = "Interface de serviço para o carrinho de aluguel.")
public interface CarrinhoAluguelService {

    @Schema(description = "Lista todos os carrinhos de aluguel")
    Page<DadosListagemCarrinhoAluguel> listarCarrinhosAluguel(Pageable pageable);

    @Schema(description = "Busca um carrinho de aluguel por ID")
    CarrinhoAluguel buscarCarrinhoAluguelPorId(Long id);

    @Transactional
    @Schema(description = "Cria um carrinho de aluguel e adiciona um aluguel a ele")
    CarrinhoAluguel criarCarrinhoAluguelAndAdicionarAluguel(@Valid DadosCadastroAluguel aluguel);

    @Transactional
    @Schema(description = "Adiciona um aluguel a um carrinho existente")
    CarrinhoAluguel adicionarAluguel(Long idCarrinho, @Valid DadosCadastroAluguel dadosAluguel);

    @Transactional
    @Schema(description = "Atualiza um aluguel em um carrinho de aluguel")
    CarrinhoAluguel atualizarAluguel(Long idCarrinho, @Valid DadosAtualizacaoAluguel dadosAluguelAtualizados);

    @Transactional
    @Schema(description = "Remove um aluguel de um carrinho de aluguel")
    CarrinhoAluguel removerAluguel(Long idCarrinho, Long idAluguel);

    @Transactional
    @Schema(description = "Exclui um carrinho de aluguel")
    void excluirCarrinhoAluguel(Long idCarrinho);
}
