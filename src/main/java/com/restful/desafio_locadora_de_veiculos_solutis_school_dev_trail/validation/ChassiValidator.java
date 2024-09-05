package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Chassi;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Schema(description = "Validador que verifica se o chassi informado é válido.")
public class ChassiValidator implements ConstraintValidator<Chassi, String> {

    private static final Pattern CHASSI_PATTERN = compile("^[A-HJ-NPR-Z0-9]{17}$");

    public ChassiValidator() {}

    @Override
    @Schema(description = "Inicializa o validador.")
    public void initialize(Chassi constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Schema(description = "Verifica se o chassi informado é válido.")
    public boolean isValid(String chassi, ConstraintValidatorContext constraintValidatorContext) {
        if (chassi == null || chassi.trim().isEmpty()) return false;

        chassi = chassi.toUpperCase();

        if (!CHASSI_PATTERN.matcher(chassi).matches()) return false;

        // Verifica o dígito verificador (último dígito do chassi)
        try {
            int checkDigit = calculateCheckDigit(chassi.substring(0, 17));
            int lastDigit = Integer.parseInt(chassi.substring(16, 17));
            return checkDigit == lastDigit;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Schema(description = "Calcula o dígito verificador do chassi.")
    private int calculateCheckDigit(String chassi) {
        int sum = 0;
        int weight = 8;

        for (int i = 0; i < chassi.length(); i++) {
            char c = chassi.charAt(i);
            int value;

            if (Character.isDigit(c)) {
                value = Integer.parseInt(String.valueOf(c));
            } else {
                value = c - 'A' + 1;
                if (value > 9) {
                    value--;
                }
            }

            sum += value * weight;
            weight--;
        }

        int remainder = sum % 11;

        if (remainder == 10) return 0;
        else return remainder;
    }
}