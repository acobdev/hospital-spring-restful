package dev.acobano.springrestful.hospital.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * DTO de salida utilizado únicamente en casos en los que alguna llamada HTTP al sistema
 * provoque una excepción, ya sea controlada o no, para envolver la información de la respuesta.
 * <>
 * @author Álvaro Cobano
 */
@Getter @Setter
@Schema(
        title = "ApiErrorResponseDTO",
        description = "DTO de salida utilizado para envolver la información resultante " +
                    "de una llamada HTTP que ha dado una respuesta diferente a 2XX."
)
public class ApiErrorResponseDTO
{
                                    /* ******************* */
                                    /* ***  ATRIBUTOS  *** */
                                    /* ******************* */
    /**
     * La marca temporal en la que ha saltado la excepción controlada por la API.
     */
    @Schema(
            name = "fecha",
            description = "Marca temporal en la que ha saltado la excepción controlada por la API",
            example = "10/03/2024 21:55:38"
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/YYYY HH:mm:ss"
    )
    private Date fecha;

    /**
     * El código de la respuesta a la llamada HTTP.
     */
    @Schema(
            name = "codigo",
            description = "Código de la respuesta a la llamada HTTP",
            example = "404"
    )
    private int codigo;

    /**
     * El estado de la respuesta a la llamada HTTP.
     */
    @Schema(
            name = "estado",
            description = "Estado de la respuesta a la llamada HTTP",
            example = "NOT_FOUND"
    )
    private String estado;

    /**
     * El mensaje personalizado de la excepción que ha provocado el fallo en la llamada HTTP.
     */
    @Schema(
            name = "mensaje",
            description = "Mensaje personalizado de la excepción que ha provocado el fallo en la llamada HTTP",
            example = "No se ha encontrado ningún elemento en el sistema con el ID introducido."
    )
    private String mensaje;


                            /* ********************* */
                            /* ***  CONSTRUCTOR  *** */
                            /* ********************* */
    public ApiErrorResponseDTO(HttpStatus estado, String mensaje)
    {
        this.fecha = new Date();
        this.codigo = estado.value();
        this.estado = estado.name();
        this.mensaje = mensaje;
    }
}
