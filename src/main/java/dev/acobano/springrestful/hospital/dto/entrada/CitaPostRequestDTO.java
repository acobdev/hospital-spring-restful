package dev.acobano.springrestful.hospital.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para llamadas HTTP de tipo POST en cuyo interior residen todos los datos
 * necesarios para guardar una nueva entidad de clase 'Cita' en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Schema(
        title = "CitaPostRequestDTO",
        description = "DTO de entrada para llamadas HTTP de tipo POST en cuyo interior residen todos " +
                "los datos necesarios para guardar una nueva entidad de clase 'Cita' en el sistema."
)
public class CitaPostRequestDTO 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    /**
     * El número identificador del paciente cuya cita tiene asignada.
     */
    @Schema(
            name = "pacienteId",
            description = "Número identificador del paciente cuya cita tiene asignada",
            example = "1"
    )
    @NotNull(message = "Toda cita debe tener asignada un paciente mediante su número identificador.")
    private Long pacienteId;

    /**
     * Número identificador de la sala cuya cita ha sido asignada
     */
    @Schema(
            name = "salaId",
            description = "Número identificador de la sala cuya cita ha sido asignada",
            example = "1"
    )
    @NotNull(message = "Toda cita debe tener asignada una sala mediante su número identificador.")
    private Long salaId;

    /**
     * La fecha acordada para la cita médica en formato 'dd/MM/yyyy'
     */
    @Schema(
            name = "fechaCita",
            description = "Fecha acordada para la cita médica en formato 'dd/MM/yyyy'",
            example = "05/12/2018"
    )
    @NotBlank(message = "El campo 'fechaCita' no puede estar vacío.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String fechaCita;

    /**
     * La hora de inicio de la cita médica en formato 'HH:mm:ss'
     */
    @Schema(
            name = "horaEntrada",
            description = "Hora de inicio de la cita médica en formato 'HH:mm:ss'",
            example = "16:15:00"
    )
    @NotBlank(message = "El campo 'horaEntrada' no puede estar vacío.")
    @JsonFormat(pattern = "HH:mm:ss")
    private String horaEntrada;

    /**
     * La hora de finalización de la cita médica en formato 'HH:mm:ss'
     */
    @Schema(
            name = "horaSalida",
            description = "Hora de finalización de la cita médica en formato 'HH:mm:ss'",
            example = "16:45:00"
    )
    @NotBlank(message = "El campo 'horaSalida' no puede estar vacío.")
    @JsonFormat(pattern = "HH:mm:ss")
    private String horaSalida;
}
