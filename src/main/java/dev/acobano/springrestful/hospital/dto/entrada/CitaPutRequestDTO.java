package dev.acobano.springrestful.hospital.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para llamadas HTTP de tipo PUT en cuyo interior residen todos los
 * datos necesarios para actualizar a una cita preexistente en el sistema.
 * <>
 * @author Álvaro Cobano
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Schema(
        title = "CitaPutRequestDTO",
        description = "DTO de entrada para llamadas HTTP de tipo PUT en cuyo interior residen todos " +
                "los datos necesarios para actualizar a una cita preexistente en el sistema."
)
public class CitaPutRequestDTO 
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
            example = "1",
            nullable = true
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Long pacienteId;

    /**
     * Número identificador de la sala cuya cita ha sido asignada
     */
    @Schema(
            name = "salaId",
            description = "Número identificador de la sala cuya cita ha sido asignada",
            example = "1",
            nullable = true
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Long salaId;

    /**
     * La fecha acordada para la cita médica en formato 'dd/MM/yyyy'
     */
    @Schema(
            name = "fechaCita",
            description = "Fecha acordada para la cita médica en formato 'dd/MM/yyyy'",
            example = "05/12/2018",
            nullable = true
    )
    @JsonFormat(pattern = "dd/MM/YYYY")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String fechaCita;

    /**
     * La hora de inicio de la cita médica en formato 'HH:mm:ss'
     */
    @Schema(
            name = "horaEntrada",
            description = "Hora de inicio de la cita médica en formato 'HH:mm:ss'",
            example = "16:15:00",
            nullable = true
    )
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String horaEntrada;

    /**
     * La hora de finalización de la cita médica en formato 'HH:mm:ss'
     */
    @Schema(
            name = "horaSalida",
            description = "Hora de finalización de la cita médica en formato 'HH:mm:ss'",
            example = "16:45:00",
            nullable = true
    )
    @JsonFormat(pattern = "HH:mm:ss")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String horaSalida;
}