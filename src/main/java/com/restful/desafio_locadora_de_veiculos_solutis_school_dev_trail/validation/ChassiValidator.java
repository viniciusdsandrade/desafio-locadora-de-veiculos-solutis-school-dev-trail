package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Chassi;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Schema(description = "Validador que verifica se o chassi informado é válido.")
public class ChassiValidator implements ConstraintValidator<Chassi, String> {

    public ChassiValidator() {}

    @Override
    @Schema(description = "Inicializa o validador.")
    public void initialize(Chassi constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Schema(description = "Verifica se o chassi informado é válido.")
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // todo : implementar validação de chassi
        return true;
    }
}