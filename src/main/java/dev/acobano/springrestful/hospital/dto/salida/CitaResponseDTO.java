package dev.acobano.springrestful.hospital.dto.salida;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de salida en el que se mostrarán los datos almacenados acerca de
 * una entidad de clase 'Cita' consultada por el usuario en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        title = "CitaResponseDTO",
        description = "DTO de salida en el que se mostrarán los datos almacenados acerca de " +
                "una entidad de clase 'Cita' consultada por el usuario en el sistema."
)
public class CitaResponseDTO 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    /**
     * El número identificador de la cita consultada en el sistema.
     */
    @Schema(
            name = "id",
            description = "Número identificador de la cita consultada",
            example = "1"
    )
    private Long id;

    /**
     * El nombre completo del médico que dirigirá la cita consultada en el sistema.
     */
    @Schema(
            name = "medico",
            description = "Nombre completo del médico que dirigirá la cita consultada",
            example = "Daniel Ferrero Portillo"
    )
    private String medico;

    /**
     * El nombre completo del paciente que recibirá la cita consultada en el sistema.
     */
    @Schema(
            name = "paciente",
            description = "Nombre completo del paciente que recibirá la cita consultada",
            example = "Julia Valero Arjona"
    )
    private String paciente;

    /**
     * El número interno de la sala donde se realizará la cita consultada en el sistema.
     */
    @Schema(
            name = "numSala",
            description = "Número interno de la sala donde se realizará la cita consultada",
            example = "101"
    )
    private int numSala;

    /**
     * La especialidad del médico asignado a la cita consultada en el sistema.
     */
    @Schema(
            name = "especialidad",
            description = "Especialidad del médico asignado a la cita consultada",
            example = "CARDIOLOGIA",
            allowableValues = {"CARDIOLOGIA", "CIRUGIA", "DERMATOLOGIA", "GINECOLOGIA", "OFTALMOLOGIA",
                    "ONCOLOGIA", "PEDIATRIA", "PSIQUIATRIA", "TRAUMATOLOGIA"}
    )
    private String especialidad;

    /**
     * La gravedad de la afección del paciente asignado a la cita consultada en el sistema.
     */
    @Schema(
            name = "gravedad",
            description = "Gravedad de la afección del paciente asignado a la cita consultada",
            example = "LEVE",
            allowableValues = {"ASINTOMATICA", "LEVE", "MODERADA", "GRAVE", "CRITICA"}
    )
    private String gravedad;

    /**
     * La fecha acordada para la cita médica en formato 'dd/MM/yyyy'
     */
    @Schema(
            name = "fechaCita",
            description = "Fecha acordada para la cita médica en formato 'dd/MM/yyyy'",
            example = "05/12/2018"
    )
    private String fechaCita;

    /**
     * La hora de inicio de la cita médica en formato 'HH:mm:ss'
     */
    @Schema(
            name = "horaEntrada",
            description = "Hora de inicio de la cita médica en formato 'HH:mm:ss'",
            example = "16:15:00"
    )
    private String horaEntrada;

    /**
     * La hora de finalización de la cita médica en formato 'HH:mm:ss'
     */
    @Schema(
            name = "horaSalida",
            description = "Hora de finalización de la cita médica en formato 'HH:mm:ss'",
            example = "16:45:00"
    )
    private String horaSalida;
}
