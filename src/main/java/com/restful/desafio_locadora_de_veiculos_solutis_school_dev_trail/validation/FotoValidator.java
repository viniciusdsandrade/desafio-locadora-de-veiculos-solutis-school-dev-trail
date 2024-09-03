package com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation;

import com.restful.desafio_locadora_de_veiculos_solutis_school_dev_trail.validation.annotations.Picture;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "Validador para fotos.")
public class FotoValidator implements ConstraintValidator<Picture, MultipartFile> {

    @Schema(description = "Propriedade que define o tamanho máximo da foto.")
    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxSizeProperty;

    private long maxSize;
    private String[] allowedTypes;

    @Override
    public void initialize(Picture constraintAnnotation) {
        this.maxSize = parseMaxSize(maxSizeProperty); // Usa maxSizeProperty aqui
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A foto é obrigatória.")
                    .addConstraintViolation();
            return false;
        }

        if (file.getSize() > maxSize) { // Usa maxSize aqui
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A foto deve ter no máximo " + (maxSize / 1048576) + " MB.")
                    .addConstraintViolation();
            return false;
        }

        String contentType = file.getContentType();
        if (contentType == null || !isValidContentType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O formato da foto é inválido. Tipos permitidos: JPEG, PNG.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isValidContentType(String contentType) {
        for (String allowedType : allowedTypes) {
            if (allowedType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }

    private long parseMaxSize(String maxSizeProperty) {
        String numericPart = maxSizeProperty.replaceAll("[^0-9]", ""); // Extrai a parte numérica
        String unit = maxSizeProperty.replaceAll("[0-9]", "").toUpperCase(); // Extrai a unidade

        long multiplier = switch (unit) {
            case "KB" -> 1024;
            case "MB" -> 1024 * 1024;
            case "GB" -> 1024 * 1024 * 1024;
            default -> // Assume bytes as default
                    1;
        };

        return Long.parseLong(numericPart) * multiplier; // Calcula o tamanho em bytes
    }

}
