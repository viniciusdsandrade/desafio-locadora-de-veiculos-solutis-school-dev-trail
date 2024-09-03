package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.service;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosAtualizacaoMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosCadastroMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosDetalhamentoMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.dto.motorista.DadosListagemMotorista;
import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.entity.Motorista;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Interface que define os serviços relacionados a motoristas.")
public interface MotoristaService {

    @Transactional
    @Schema(description = "Cadastra um novo motorista.")
    Motorista cadastrarMotorista(@Valid DadosCadastroMotorista motorista);

    @Schema(description = "Busca um motorista pelo seu ID.")
    Motorista buscarPorId(Long id);

    @Transactional
    @Schema(description = "Atualiza os dados de um motorista.")
    Motorista atualizarMotorista(@Valid DadosAtualizacaoMotorista dadosAtualizacaoMotorista);

    @Transactional
    @Schema(description = "Deleta um motorista.")
    void deletarMotorista(Long id);

    @Transactional
    @Schema(description = "Ativa um motorista.")
    void desativarMotorista(Long id);

    @Schema(description = "Lista todos os motoristas cadastrados.")
    Page<DadosListagemMotorista> listar(Pageable paginacao);

    /**
     * Pesquisa motoristas com base nos filtros informados.
     *
     * @param nome           O nome do motorista a ser pesquisado.
     * @param email          O email do motorista a ser pesquisado.
     * @param cpf            O CPF do motorista a ser pesquisado.
     * @param dataNascimento A data de nascimento do motorista a ser pesquisada.
     * @param numeroCNH      O número da CNH do motorista a ser pesquisado.
     * @param sexo           O sexo do motorista a ser pesquisado.
     * @param ativo          O status ativo do motorista a ser pesquisado.
     * @param placasAlugueis A lista de placas dos carros alugados pelos motoristas a serem pesquisados.
     * @param paginacao      As informações de paginação da pesquisa.
     * @return Uma página de {@link DadosDetalhamentoMotorista} contendo os motoristas que atendem aos critérios de pesquisa.
     */
    @Schema(description = "Pesquisa motoristas com base nos filtros informados.")
    Page<DadosDetalhamentoMotorista> pesquisarMotoristas(
            String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String numeroCNH,
            String sexo,
            Boolean ativo,
            List<String> placasAlugueis,
            Pageable paginacao
    );
}
