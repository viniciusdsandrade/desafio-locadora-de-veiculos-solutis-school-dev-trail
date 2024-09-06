package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Exceção personalizada lançada quando ocorre uma tentativa de alugar um carro que não está disponível.
 * <p>
 * Esta exceção é tipicamente usada em operações de aluguel de carros,
 * onde é necessário garantir que o carro selecionado esteja disponível para locação.
 * Ao ser lançada, esta exceção resulta em uma resposta HTTP com o status 400 (Bad Request),
 * indicando ao cliente que a operação não pôde ser concluída devido à indisponibilidade do carro.
 * </p>
 *
 * @see RuntimeException
 * @see ResponseStatus
 */
@ResponseStatus(BAD_REQUEST)
@Schema(description = "Exceção lançada quando um carro está indisponível para aluguel.")
public class CarroIndisponivelException extends RuntimeException {

  /**
   * Construtor para criar uma nova instância de {@code CarroIndisponivelException} com uma mensagem de erro detalhada.
   * <p>
   * A mensagem deve descrever claramente a razão da indisponibilidade do carro, como a data em que o carro
   * já está alugado ou se o carro está em manutenção. Essa mensagem será útil para a depuração e para fornecer
   * feedback adequado ao cliente da API.
   * </p>
   */
  public CarroIndisponivelException(String message) {
    super(message);
  }
}