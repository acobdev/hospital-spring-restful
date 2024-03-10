package dev.acobano.springrestful.hospital.validacion.validadores;

import dev.acobano.springrestful.hospital.validacion.anotaciones.Dni;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

/**
 * Clase que implementa la interfaz ConstraintValidator para poder implementar
 * la lógica de validación que será empleada por la anotación personalizada 'DNI'.
 * <>
 * @author Álvaro Cobano
 */
public class ValidadorDni implements ConstraintValidator<Dni, String>
{
    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        //Validamos en primer lugar que el DNI no venga vacío:
        if (Objects.isNull(value) || value.isBlank())
            return false;

        //Dejamos la cadena de texto bien formateada para tratarla posteriormente:
        String dni = value.toUpperCase().trim().replaceAll(" ", "");

        //Condición de validación que el DNI debe tener 9 caracteres (8 cifras + 1 letra):
        if (dni.length() != 9)
            return false;

        //Algoritmo de validación de la letra del DNI:
        char letra = dni.charAt(dni.length() - 1);
        long cifras = Long.parseLong(dni.substring(0, 7));
        int resto = (int) (cifras % 23);
        return letra == LETRAS_DNI.charAt(resto);
    }
}
