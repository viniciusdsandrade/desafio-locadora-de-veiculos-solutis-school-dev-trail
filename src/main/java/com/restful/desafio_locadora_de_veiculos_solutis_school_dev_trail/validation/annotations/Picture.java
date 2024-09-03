package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.FotoValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = FotoValidator.class)
@Schema(description = "Valida se a foto informada é válida.")
public @interface Picture {

    String message() default "Foto inválida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long maxSize() default 10 * 1024 * 1024; // 5MB por padrão

    String[] allowedTypes() default {"image/jpeg", "image/png"};
}