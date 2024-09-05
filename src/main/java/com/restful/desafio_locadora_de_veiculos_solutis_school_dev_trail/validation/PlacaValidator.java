package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Placa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Schema(description = "Validador que verifica se a placa informada é válida.")
public class PlacaValidator implements ConstraintValidator<Placa, String> {

    private static final Pattern PLACA_ANTIGA_PATTERN = compile("^[A-Z]{3}-\\d{4}$");
    private static final Pattern PLACA_MERCOSUL_PATTERN = compile("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");

    public PlacaValidator() {}

    @Override
    @Schema(description = "Inicializa o validador.")
    public void initialize(Placa constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Schema(description = "Verifica se a placa informada é válida.")
    public boolean isValid(String placa, ConstraintValidatorContext constraintValidatorContext) {
        if (placa == null || placa.trim().isEmpty()) return false;
        placa = placa.toUpperCase();
        return PLACA_ANTIGA_PATTERN.matcher(placa).matches() || PLACA_MERCOSUL_PATTERN.matcher(placa).matches();
    }
}