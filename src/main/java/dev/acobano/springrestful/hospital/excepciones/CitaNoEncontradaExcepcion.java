package dev.acobano.springrestful.hospital.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase heredada de RuntimeException que se encargará de controlar y lanzar un
 * aviso al manejador de excepciones en caso de no encontrar la cita deseada en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class CitaNoEncontradaExcepcion extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public CitaNoEncontradaExcepcion(String mensaje) {
        super(mensaje);
    }
}
