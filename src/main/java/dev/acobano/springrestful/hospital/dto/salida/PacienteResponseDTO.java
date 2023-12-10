package dev.acobano.springrestful.hospital.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de salida en el que se mostrarán los datos almacenados acerca de
 * una entidad de clase 'Paciente' consultado por el usuario en el sistema.
 * <>
 * @author Álvaro Cobano
 */

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "PacienteResponseDTO",
        description = "DTO de salida en el que se mostrarán los datos almacenados acerca de " +
                "una entidad de clase 'Paciente' consultado por el usuario en el sistema."
)
public class PacienteResponseDTO 
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    /**
     * El número identificador del paciente consultado en el sistema.
     */
    @Schema(
            name = "id",
            description = "Número identificador del paciente consultado en el sistema",
            example = "1"
    )
    private Long id;

    /**
     * El nombre del paciente consultado en el sistema.
     */
    @Schema(
            name = "nombre",
            description = "Nombre del paciente consultado en el sistema",
            example = "Julia"
    )
    private String nombre;

    /**
     * Los apellidos del médico consultado en el sistema.
     */
    @Schema(
            name = "apellidos",
            description = "Apellidos del médico consultado en el sistema",
            example = "Valero Arjona"
    )
    private String apellidos;

    /**
     * El DNI del médico consultado en el sistema.
     */
    @Schema(
            name = "dni",
            description = "DNI del médico consultado en el sistema",
            example = "13178724T"
    )
    private String dni;

    /**
     * Género del paciente consultado en el sistema.
     */
    @Schema(
            name = "genero",
            description = "Género del paciente consultado en el sistema",
            example = "FEMENINO",
            allowableValues = {"MASCULINO", "FEMENINO", "NO_ESPECIFICADO"}
    )
    private String genero;

    /**
     * Dirección física donde vive el paciente consultado en el sistema.
     */
    @Schema(
            name = "direccion",
            description = "Dirección física donde vive el paciente consultado en el sistema",
            example = "C/ Esperanza Pedralbes, 78"
    )
    private String direccion;

    /**
     * Correo electrónico del paciente consultado en el sistema.
     */
    @Schema(
            name = "email",
            description = "Correo electrónico del paciente consultado en el sistema",
            example = "juliavalero73@mail.net"
    )
    private String email;

    /**
     * Número telefónico del paciente consultado en el sistema.
     */
    @Schema(
            name = "telefono",
            description = "Número telefónico del paciente consultado en el sistema",
            example = "681555117"
    )
    private String telefono;

    /**
     * Fecha de nacimiento del paciente consultado en el sistema bajo formato 'dd/MM/YYYY HH:mm:ss'
     */
    @Schema(
            name = "fechaNacimiento",
            description = "Fecha de nacimiento del paciente consultado en el sistema bajo formato 'dd/MM/YYYY HH:mm:ss'",
            example = "22/07/1973 13:35:00"
    )
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    private String fechaNacimiento;

    /**
     * Fecha de ingreso en el hospital del paciente bajo formato 'dd/MM/YYYY HH:mm:ss'
     */
    @Schema(
            name = "fechaIngreso",
            description = "Fecha de ingreso en el hospital del paciente bajo formato 'dd/MM/YYYY HH:mm:ss'",
            example = "06/10/2012 20:45:30"
    )
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    private String fechaIngreso;

    /**
     * Nombre completo del médico asignado al paciente consultado en el sistema
     */
    @Schema(
            name = "medicoAsignado",
            description = "Nombre completo del médico asignado al paciente consultado en el sistema",
            example = "Daniel Ferrero Portillo"
    )
    private String medicoAsignado;

    /**
     * Especialidad del médico asignado al paciente consultado en el sistema
     */
    @Schema(
            name = "areaTratamiento",
            description = "La especialidad del médico asignado al paciente consultado en el sistema",
            example = "CARDIOLOGIA",
            allowableValues = {"CARDIOLOGIA", "CIRUGIA", "DERMATOLOGIA", "GINECOLOGIA", "OFTALMOLOGIA",
                    "ONCOLOGIA", "PEDIATRIA", "PSIQUIATRIA", "TRAUMATOLOGIA"}
    )
    private String areaTratamiento;

    /**
     * Gravedad de la afección del paciente consultado en el sistema
     */
    @Schema(
            name = "gravedad",
            description = "Gravedad de la afección del paciente consultado en el sistema",
            example = "LEVE",
            allowableValues = {"ASINTOMATICA", "LEVE", "MODERADA", "GRAVE", "CRITICA"}
    )
    private String gravedad;

    /**
     * Número de citas registradas a nombre del paciente consultado en el sistema.
     */
    @Schema(
            name = "citasRegistradas",
            description = "Número de citas registradas a nombre del paciente consultado en el sistema",
            example = "1"
    )
    private int citasRegistradas;
}
