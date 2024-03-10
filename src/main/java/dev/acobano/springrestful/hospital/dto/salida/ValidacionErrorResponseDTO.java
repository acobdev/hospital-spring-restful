package dev.acobano.springrestful.hospital.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * DTO de salida de datos invocado cuando existe algún error en la validación de nuevos
 * datos en el sistema, provocando una respuesta en la llamada HTTP 400 - BAD REQUEST
 * <>
 * @author Álvaro Cobano
 */
@Getter @Setter
@Schema(
        title = "ValidacionErrorResponseDTO",
        description = "DTO de salida de datos encargado de informar de errores en la validación de " +
                "nuevos datos en el sistema, provocando una respuesta en la llamada HTTP 400 - BAD REQUEST"
)
public class ValidacionErrorResponseDTO
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
            example = "400"
    )
    private int codigo;

    /**
     * El estado de la respuesta a la llamada HTTP.
     */
    @Schema(
            name = "estado",
            description = "Estado de la respuesta a la llamada HTTP",
            example = "BAD_REQUEST"
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

    /**
     * Colección de cadenas de texto donde se informa de los campos y los mensajes
     * de las fallas en la validación de datos desde el DTO de entrada.
     */
    @Schema(
            name = "errores",
            description = "Colección de cadenas de texto donde se informa de los campos y los mensajes de las fallas en la validación"
    )
    private Map<String, String> errores;


                                    /* ********************* */
                                    /* ***  CONSTRUCTOR  *** */
                                    /* ********************* */
    public ValidacionErrorResponseDTO(Map<String, String> errores)
    {
        this.fecha = new Date();
        this.codigo = 400;
        this.estado = "BAD_REQUEST";
        this.mensaje = "Datos del DTO de entrada mal validados";
        this.errores = errores;
    }
}
