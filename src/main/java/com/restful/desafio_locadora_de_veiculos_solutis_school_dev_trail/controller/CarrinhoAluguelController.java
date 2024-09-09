package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.controller;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosCadastroAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosAtualizacaoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carrinhoAluguel.DadosListagemCarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.CarrinhoAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.CarrinhoAluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("carrinhos")
@Tag(name = "Carrinho", description = "API para gerenciar carrinhos de aluguel")
public class CarrinhoAluguelController {

    @Schema(name = "CarrinhoAluguelController")
    private final CarrinhoAluguelService carrinhoAluguelService;

    public CarrinhoAluguelController(CarrinhoAluguelService carrinhoAluguelService) {
        this.carrinhoAluguelService = carrinhoAluguelService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo carrinho de aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrinho criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.")
    })
    public ResponseEntity<CarrinhoAluguel> criarCarrinhoEAdicionarAluguel(
            @RequestBody @Valid DadosCadastroAluguel dados,
            UriComponentsBuilder uriBuilder
    ) {
        CarrinhoAluguel carrinho = carrinhoAluguelService.criarCarrinhoAluguelAndAdicionarAluguel(dados);
        URI uri = uriBuilder.path("/carrinhos/{id}").buildAndExpand(carrinho.getId()).toUri();
        return created(uri).body(carrinho);
    }

    @GetMapping
    @Operation(summary = "Listar carrinhos de aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinhos encontrados.")
    })
    public ResponseEntity<Page<DadosListagemCarrinhoAluguel>> listar(
            @PageableDefault(size = 5) Pageable paginacao
    ) {
        Page<DadosListagemCarrinhoAluguel> carrinhos = carrinhoAluguelService.listarCarrinhosAluguel(paginacao);
        return ok(carrinhos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar um carrinho de aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinho encontrado."),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.")
    })
    public ResponseEntity<DadosListagemCarrinhoAluguel> detalhar(@PathVariable Long id) {
        CarrinhoAluguel carrinho = carrinhoAluguelService.buscarCarrinhoAluguelPorId(id);
        return ok(new DadosListagemCarrinhoAluguel(carrinho));
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar um aluguel no carrinho", description = "Permite atualizar os dados de um aluguel existente no carrinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel atualizado no carrinho com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel ou aluguel não encontrados")
    })
    public ResponseEntity<DadosListagemCarrinhoAluguel> atualizarAluguelNoCarrinho(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoAluguel dadosAtualizacaoAluguel
    ) {
        CarrinhoAluguel carrinho = carrinhoAluguelService.atualizarAluguel(id, dadosAtualizacaoAluguel);
        return ok(new DadosListagemCarrinhoAluguel(carrinho));
    }

    @PutMapping("/{idCarrinho}/alugueis/{idAluguel}")
    @Transactional
    @Operation(summary = "Remover um aluguel do carrinho", description = "Remove um aluguel específico de um carrinho de aluguel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluguel removido do carrinho com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel ou aluguel não encontrados")
    })
    public ResponseEntity<DadosListagemCarrinhoAluguel> removerAluguelDoCarrinho(@PathVariable Long idCarrinho, @PathVariable Long idAluguel) {
        CarrinhoAluguel carrinho = carrinhoAluguelService.removerAluguel(idCarrinho, idAluguel); // Passe ambos os IDs para o serviço
        return ok(new DadosListagemCarrinhoAluguel(carrinho));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Remover um carrinho de aluguel", description = "Remove um carrinho de aluguel e todos os seus aluguéis associados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrinho de aluguel removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carrinho de aluguel não encontrado")
    })
    public ResponseEntity<Void> removerCarrinho(@PathVariable Long id) {
        carrinhoAluguelService.excluirCarrinhoAluguel(id);
        return noContent().build();
    }
}