package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.controller;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosDetalhamentoMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Motorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.MotoristaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/cliente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Cliente Controller", description = "Controller para gerenciamento de clientes (motoristas)")
public class ClienteController {

    @Schema(description = "Controller para gerenciamento de clientes (motoristas).")
    private final MotoristaService motoristaService;

    public ClienteController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @Transactional
    @Operation(summary = "Cadastrar um novo cliente", description = "Cria um novo cliente com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.")
    })
    public ResponseEntity<DadosListagemMotorista> cadastrar(
            @ModelAttribute @Valid DadosCadastroMotorista dadosCadastroMotorista,
            UriComponentsBuilder uriBuilder
    ) {
        Motorista motorista = motoristaService.cadastrarMotorista(dadosCadastroMotorista);
        URI uri = uriBuilder.path("/api/v1/cliente/{id}").buildAndExpand(motorista.getId()).toUri();
        return created(uri).body(new DadosListagemMotorista(motorista));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar um cliente", description = "Retorna os detalhes de um cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<DadosListagemMotorista> detalhar(@PathVariable Long id) {
        var motorista = motoristaService.buscarPorId(id);
        return ok(new DadosListagemMotorista(motorista));
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista paginada de todos os clientes, ativos e inativos.")
    @ApiResponse(responseCode = "200", description = "Lista de todos os clientes.")
    public ResponseEntity<Page<DadosListagemMotorista>> listarTodos(
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
        Page<DadosListagemMotorista> motoristas = motoristaService.listarMotoristas(paginacao);
        return ok(motoristas);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar clientes ativos", description = "Retorna uma lista paginada de clientes ativos.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes ativos.")
    public ResponseEntity<Page<DadosListagemMotorista>> listarAtivos(
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
        Page<DadosListagemMotorista> motoristas = motoristaService.listarMotoristasAtivos(paginacao);
        return ok(motoristas);
    }

    @GetMapping("/inativos")
    @Operation(summary = "Listar clientes inativos", description = "Retorna uma lista paginada de clientes inativos.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes inativos.")
    public ResponseEntity<Page<DadosListagemMotorista>> listarInativos(
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
        Page<DadosListagemMotorista> motoristas = motoristaService.listarMotoristasInativos(paginacao);
        return ok(motoristas);
    }

    @GetMapping("/detalhar-completo/{id}")
    @Operation(summary = "Detalhar um cliente completo", description = "Retorna os detalhes completos de um cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<DadosDetalhamentoMotorista> detalharCompleto(@PathVariable Long id) {
        Motorista motorista = motoristaService.buscarPorId(id);
        return ok(new DadosDetalhamentoMotorista(motorista));
    }

    @GetMapping("/pesquisar-and")
    @Operation(summary = "Pesquisar motoristas por critérios (AND)", description = "Retorna uma lista paginada de motoristas que correspondem a todos os critérios de pesquisa, utilizando junção AND.")
    @ApiResponse(responseCode = "200", description = "Lista de motoristas encontrados.")
    public ResponseEntity<Page<DadosDetalhamentoMotorista>> pesquisarMotoristasAnd(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataNascimento,
            @RequestParam(required = false) String numeroCNH,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) List<String> placasAlugueis,
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
        Page<DadosDetalhamentoMotorista> motoristas = motoristaService.pesquisarMotoristasAnd(
                nome,
                email,
                cpf,
                dataNascimento,
                numeroCNH,
                sexo,
                ativo,
                placasAlugueis,
                paginacao
        );

        return ok(motoristas);
    }

    @GetMapping("/pesquisar-or")
    @Operation(summary = "Pesquisar motoristas por critérios (OR)", description = "Retorna uma lista paginada de motoristas que correspondem a qualquer um dos critérios de pesquisa, utilizando junção OR.")
    @ApiResponse(responseCode = "200", description = "Lista de motoristas encontrados.")
    public ResponseEntity<Page<DadosDetalhamentoMotorista>> pesquisarMotoristasOr(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataNascimento,
            @RequestParam(required = false) String numeroCNH,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) List<String> placasAlugueis,
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
        Page<DadosDetalhamentoMotorista> motoristas = motoristaService.pesquisarMotoristasOr(
                nome,
                email,
                cpf,
                dataNascimento,
                numeroCNH,
                sexo,
                ativo,
                placasAlugueis,
                paginacao
        );

        return ok(motoristas);
    }

    @Transactional
    @PatchMapping
    @Operation(summary = "Atualizar alguns dados do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<DadosListagemMotorista> atualizar(@RequestBody @Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista) {
        Motorista motorista = motoristaService.atualizarMotorista(dadosAtualizacaoMotorista);
        return ok(new DadosListagemMotorista(motorista));
    }

    @Transactional
    @PatchMapping("/desativar/{id}")
    @Operation(summary = "Desativar um cliente, impedindo-o de realizar novos aluguéis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        motoristaService.desativarMotorista(id);
        return noContent().build();
    }

    @Transactional
    @PatchMapping("/ativar/{id}")
    @Operation(summary = "Ativar um cliente, permitindo-o de realizar novos aluguéis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente ativado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        motoristaService.ativarMotorista(id);
        return noContent().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um cliente da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoristaService.deletarMotorista(id);
        return noContent().build();
    }
}
