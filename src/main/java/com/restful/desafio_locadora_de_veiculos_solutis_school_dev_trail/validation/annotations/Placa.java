package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.PlacaValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
@Constraint(validatedBy = PlacaValidator.class)
@Schema(description = "Valida se a placa informada é válida.")
public @interface Placa {

    String message() default "Placa inválida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
