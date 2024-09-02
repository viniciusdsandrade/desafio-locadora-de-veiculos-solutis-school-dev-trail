package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.CNHValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
@Constraint(validatedBy = CNHValidator.class)
@Schema(description = "Valida se um número de CNH é válido de acordo com o algoritmo de verificação brasileiro.")
public @interface CNH {

    String message() default "CNH inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}