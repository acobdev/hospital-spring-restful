package dev.acobano.springrestful.hospital.validacion.anotaciones;

import dev.acobano.springrestful.hospital.validacion.validadores.ValidadorDni;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotación personalizada que proporciona de una entidad validadora pensada
 * para ser empleada en cadenas de texto con formato DNI.
 * <>
 * @author Álvaro Cobano
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorDni.class)
public @interface Dni {
    String message() default "El dato introducido en el campo 'DNI' no cumple las reglas de validación.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
