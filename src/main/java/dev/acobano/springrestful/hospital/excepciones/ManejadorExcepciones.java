package dev.acobano.springrestful.hospital.excepciones;

import dev.acobano.springrestful.hospital.dto.salida.ApiErrorResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.ValidacionErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase con anotación @RestControllerAdvice que se encargará de redirigir y gestionar
 * las diferentes excepciones que el sistema pueda lanzar debido a un mal uso dado por
 * el usuario.
 * <>
 * @author Álvaro Cobano
 */
@Slf4j
@RestControllerAdvice
public class ManejadorExcepciones
{
                    /* ******************************************************** */
                    /* ***  MÉTODOS MANEJADORES DE EXCEPCIONES DEL SISTEMA  *** */
                    /* ******************************************************** */

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorResponseDTO> manejarRuntimeException(RuntimeException e)
    {
        log.error("---> EXCEPCIÓN RuntimeException CAPTURADA POR EL MANEJADOR");
        return manejarInternalServerError(e);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrorResponseDTO> manejarNullPointerException(NullPointerException e)
    {
        log.error("---> EXCEPCIÓN NullPointerException CAPTURADA POR EL MANEJADOR");
        return manejarInternalServerError(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidacionErrorResponseDTO> manejarArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error("---> EXCEPCIÓN MethodArgumentNotValidException CAPTURADA POR EL MANEJADOR");
        Map<String, String> errores = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        return manejarBadRequest(errores);
    }


                    /* *********************************************************** */
                    /* ***  MÉTODOS MANEJADORES DE EXCEPCIONES PERSONALIZADAS  *** */
                    /* *********************************************************** */

    @ExceptionHandler(CitaNoEncontradaExcepcion.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiErrorResponseDTO> manejarCitaNoEncontradaExcepcion(CitaNoEncontradaExcepcion e)
    {
        log.error("---> EXCEPCIÓN CitaNoEncontradaExcepcion CAPTURADA POR EL MANEJADOR");
        return manejarNoContent(e);
    }

    @ExceptionHandler(value = MedicoNoEncontradoExcepcion.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiErrorResponseDTO> manejarMedicoNoEncontradoExcepcion(MedicoNoEncontradoExcepcion e)
    {
        log.error("---> EXCEPCIÓN MedicoNoEncontradoExcepcion CAPTURADA POR EL MANEJADOR");
        return manejarNoContent(e);
    }

    @ExceptionHandler(PacienteNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiErrorResponseDTO> manejarPacienteNoEncontradoExcepcion(PacienteNoEncontradoExcepcion e)
    {
        log.error("---> EXCEPCIÓN PacienteNoEncontradoExcepcion CAPTURADA POR EL MANEJADOR");
        return manejarNoContent(e);
    }

    @ExceptionHandler(SalaNoEncontradaExcepcion.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiErrorResponseDTO> manejarSalaNoEncontradaExcepcion(SalaNoEncontradaExcepcion e)
    {
        log.error("---> EXCEPCIÓN SalaNoEncontradaExcepcion CAPTURADA POR EL MANEJADOR");
        return manejarNoContent(e);
    }


                    /* ******************************************************************* */
                    /* ***  MÉTODOS MANEJADORES DE LOS ESTADOS DE LAS RESPUESTAS HTTP  *** */
                    /* ******************************************************************* */
    public ResponseEntity<ApiErrorResponseDTO> manejarNoContent(Exception e)
    {
        ApiErrorResponseDTO respuesta = new ApiErrorResponseDTO(HttpStatus.NO_CONTENT, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    public ResponseEntity<ValidacionErrorResponseDTO> manejarBadRequest(Map<String, String> errores)
    {
        ValidacionErrorResponseDTO respuesta = new ValidacionErrorResponseDTO(errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    public ResponseEntity<ApiErrorResponseDTO> manejarInternalServerError(Exception e)
    {
        ApiErrorResponseDTO respuesta = new ApiErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
