package dev.acobano.springrestful.hospital.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase heredada de RuntimeException que se encargará de controlar y lanzar un aviso
 * al manejador de excepciones en caso de no encontrar al paciente deseado en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class PacienteNoEncontradoExcepcion extends RuntimeException
{
    private static final long serialVersionUID = 3L
            ;
    public PacienteNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
}
