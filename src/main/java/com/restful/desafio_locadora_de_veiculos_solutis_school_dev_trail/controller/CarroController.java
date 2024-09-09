package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.controller;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosAtualizacaoCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosCadastroCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosDetalhamentoCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.carro.DadosListagemCarro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Carro;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carro")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Carro Controller", description = "Controller para gerenciamento de carros")
public class CarroController {

    @Schema(description = "Controller para gerenciamento de carros.")
    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Cadastrar um novo carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carro criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.")
    })
    public ResponseEntity<DadosListagemCarro> cadastrar(
            @RequestBody @Valid DadosCadastroCarro dadosCadastroCarro,
            UriComponentsBuilder uriBuilder
    ) {
        Carro carro = carroService.cadastrarCarro(dadosCadastroCarro);
        URI uri = uriBuilder.path("/api/v1/carro/{id}").buildAndExpand(carro.getId()).toUri();
        return created(uri).body(new DadosListagemCarro(carro));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar um carro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro encontrado."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<DadosDetalhamentoCarro> detalhar(@PathVariable Long id) {
        Carro carro = carroService.buscarPorId(id);
        return ok(new DadosDetalhamentoCarro(carro));
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar carros", description = "Retorna uma lista paginada de carros.")
    @ApiResponse(responseCode = "200", description = "Lista de carros.")
    public ResponseEntity<Page<DadosListagemCarro>> listarTodos(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {

        if (limit != null && offset != null) {
            page = offset / limit;
            size = limit;
        }

        Pageable paginacao = of(page, size, by(sort));
        Page<DadosListagemCarro> carros = carroService.listarCarros(paginacao);
        return ok(carros);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar carros disponíveis", description = "Retorna uma lista paginada de carros disponíveis para aluguel.")
    @ApiResponse(responseCode = "200", description = "Lista de carros disponíveis.")
    public ResponseEntity<Page<DadosDetalhamentoCarro>> listarCarrosDisponiveis(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {

        if (limit != null && offset != null) {
            page = offset / limit;
            size = limit;
        }

        Pageable paginacao = of(page, size, by(sort));
        Page<DadosDetalhamentoCarro> carros = carroService.listarCarrosDisponiveis(paginacao);

        return ok(carros);
    }

    @GetMapping("/alugados")
    @Operation(summary = "Listar carros alugados", description = "Retorna uma lista paginada de carros que estão atualmente alugados.")
    @ApiResponse(responseCode = "200", description = "Lista de carros alugados.")
    public ResponseEntity<Page<DadosDetalhamentoCarro>> listarCarrosAlugados(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {
        if (limit != null && offset != null) {
            page = offset / limit;
            size = limit;
        }

        Pageable paginacao = of(page, size, by(sort));
        Page<DadosDetalhamentoCarro> carros = carroService.listarCarrosAlugados(paginacao);

        return ok(carros);
    }

    @GetMapping("/detalhar-completo/{id}")
    @Operation(summary = "Detalhar um carro completo incluindo todos os seus aluguéis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro encontrado."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<DadosDetalhamentoCarro> detalharCompleto(@PathVariable Long id) {
        Carro carro = carroService.buscarPorId(id);
        return ok(new DadosDetalhamentoCarro(carro));
    }

    @GetMapping("/pesquisar-and")
    @Operation(summary = "Pesquisar carros por critérios", description = "Retorna uma lista paginada de carros que correspondem aos critérios de pesquisa.")
    @ApiResponse(responseCode = "200", description = "Lista de carros encontrados.")
    public ResponseEntity<Page<DadosDetalhamentoCarro>> pesquisarCarrosAnd(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) BigDecimal valorDiariaMin,
            @RequestParam(required = false) BigDecimal valorDiariaMax,
            @RequestParam(required = false) String modeloDescricao,
            @RequestParam(required = false) String fabricanteNome,
            @RequestParam(required = false) String categoriaNome,
            @RequestParam(required = false) List<String> acessoriosNomes,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {

        if (limit != null && offset != null) {
            page = offset / limit;
            size = limit;
        }

        Pageable paginacao = of(page, size, by(sort));
        Page<DadosDetalhamentoCarro> carros = carroService.pesquisarCarrosAnd(
                nome,
                placa,
                chassi,
                cor,
                disponivel,
                valorDiariaMin,
                valorDiariaMax,
                modeloDescricao,
                fabricanteNome,
                categoriaNome,
                acessoriosNomes,
                paginacao
        );

        return ok(carros);
    }

    @GetMapping("/pesquisar-or")
    @Operation(summary = "Pesquisar carros por critérios com OR", description = "Retorna uma lista paginada de carros que correspondem a qualquer um dos critérios de pesquisa utilizando OR.")
    @ApiResponse(responseCode = "200", description = "Lista de carros encontrados.")
    public ResponseEntity<Page<DadosDetalhamentoCarro>> pesquisarCarrosOr(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) BigDecimal valorDiariaMin,
            @RequestParam(required = false) BigDecimal valorDiariaMax,
            @RequestParam(required = false) String modeloDescricao,
            @RequestParam(required = false) String fabricanteNome,
            @RequestParam(required = false) String categoriaNome,
            @RequestParam(required = false) List<String> acessoriosNomes,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {

        if (limit != null && offset != null) {
            page = offset / limit;
            size = limit;
        }

        Pageable paginacao = of(page, size, by(sort));
        Page<DadosDetalhamentoCarro> carros = carroService.pesquisarCarrosOr(
                nome,
                placa,
                chassi,
                cor,
                disponivel,
                valorDiariaMin,
                valorDiariaMax,
                modeloDescricao,
                fabricanteNome,
                categoriaNome,
                acessoriosNomes,
                paginacao
        );

        return ok(carros);
    }

    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear aluguel de um carro", description = "Bloqueia um carro para que ele não possa ser alugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro bloqueado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<Void> bloquearAluguel(@PathVariable Long id) {
        carroService.bloquearCarroAluguel(id);
        return noContent().build();
    }

    @PatchMapping("/{id}/disponibilizar")
    @Operation(summary = "Disponibilizar aluguel de um carro", description = "Disponibiliza um carro para que ele possa ser alugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro disponibilizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<Void> disponibilizarAluguel(@PathVariable Long id) {
        carroService.disponibilizarCarroAluguel(id);
        return noContent().build();
    }

    @Transactional
    @PatchMapping
    @Operation(summary = "Atualizar um carro", description = "Atualiza parte ou todos os dados de um carro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carro atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<DadosListagemCarro> atualizar(@RequestBody @Valid DadosAtualizacaoCarro dadosAtualizacaoCarro) {
        Carro carro = carroService.atualizarCarro(dadosAtualizacaoCarro);
        return ok(new DadosListagemCarro(carro));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar um carro", description = "Aos desativar um carro, ele não poderá ser alugado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carro desativado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado.")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        carroService.excluirCarro(id);
        return noContent().build();
    }
}