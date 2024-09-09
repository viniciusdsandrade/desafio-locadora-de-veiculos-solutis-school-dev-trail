package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.controller;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.aluguel.DadosListagemAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.enums.StatusAluguel;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service.AluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/aluguel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Aluguel Controller", description = "Controller para gerenciamento de aluguéis")
@Schema(description = "Controller para gerenciamento de aluguéis.")
public class AluguelController {

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @GetMapping("/pesquisar-and")
    @Operation(summary = "Pesquisar aluguéis por critérios (AND)", description = "Retorna uma lista paginada de aluguéis que correspondem a todos os critérios de pesquisa.")
    @ApiResponse(responseCode = "200", description = "Lista de aluguéis encontrados.")
    public ResponseEntity<Page<DadosListagemAluguel>> pesquisarAlugueisAnd(
            @RequestParam(required = false) StatusAluguel statusAluguel,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataPedidoInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataPedidoFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataRetiradaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataRetiradaFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataDevolucaoPrevistaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataDevolucaoPrevistaFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataDevolucaoEfetivaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate dataDevolucaoEfetivaFim,
            @RequestParam(required = false) BigDecimal valorTotalInicialMin,
            @RequestParam(required = false) BigDecimal valorTotalInicialMax,
            @RequestParam(required = false) BigDecimal valorTotalFinalMin,
            @RequestParam(required = false) BigDecimal valorTotalFinalMax,
            @RequestParam(required = false) List<String> emailsMotoristas,
            @RequestParam(required = false) List<String> placasCarros,
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
        Page<DadosListagemAluguel> alugueis = aluguelService.pesquisarAlugueisAnd(
                statusAluguel,
                dataPedidoInicio,
                dataPedidoFim,
                dataRetiradaInicio,
                dataRetiradaFim,
                dataDevolucaoPrevistaInicio,
                dataDevolucaoPrevistaFim,
                dataDevolucaoEfetivaInicio,
                dataDevolucaoEfetivaFim,
                valorTotalInicialMin,
                valorTotalInicialMax,
                valorTotalFinalMin,
                valorTotalFinalMax,
                emailsMotoristas,
                placasCarros,
                paginacao
        );

        return ok(alugueis);
    }

    @GetMapping("/pesquisar-or")
    @Operation(summary = "Pesquisar aluguéis por critérios (OR)", description = "Retorna uma lista paginada de aluguéis que correspondem a pelo menos um dos critérios de pesquisa.")
    @ApiResponse(responseCode = "200", description = "Lista de aluguéis encontrados.")
    public ResponseEntity<Page<DadosListagemAluguel>> pesquisarAlugueisOr(
            @RequestParam(required = false) StatusAluguel statusAluguel,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPedidoInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPedidoFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRetiradaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRetiradaFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoPrevistaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoPrevistaFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoEfetivaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucaoEfetivaFim,
            @RequestParam(required = false) BigDecimal valorTotalInicialMin,
            @RequestParam(required = false) BigDecimal valorTotalInicialMax,
            @RequestParam(required = false) BigDecimal valorTotalFinalMin,
            @RequestParam(required = false) BigDecimal valorTotalFinalMax,
            @RequestParam(required = false) List<String> emailsMotoristas,
            @RequestParam(required = false) List<String> placasCarros,
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
        Page<DadosListagemAluguel> alugueis = aluguelService.pesquisarAlugueisOr(
                statusAluguel,
                dataPedidoInicio,
                dataPedidoFim,
                dataRetiradaInicio,
                dataRetiradaFim,
                dataDevolucaoPrevistaInicio,
                dataDevolucaoPrevistaFim,
                dataDevolucaoEfetivaInicio,
                dataDevolucaoEfetivaFim,
                valorTotalInicialMin,
                valorTotalInicialMax,
                valorTotalFinalMin,
                valorTotalFinalMax,
                emailsMotoristas,
                placasCarros,
                paginacao
        );

        return ok(alugueis);
    }
}
