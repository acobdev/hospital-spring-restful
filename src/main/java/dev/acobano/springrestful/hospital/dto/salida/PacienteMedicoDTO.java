package dev.acobano.springrestful.hospital.dto.salida;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de salida de datos en el que se mostrarán todos los datos almacenados acerca
 * de una entidad 'Paciente' que se encuentra asignada a una entidad 'Médico'.
 * <>
 * @author Álvaro Cobano
 */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "PacienteMedicoDTO",
        description = "DTO de salida de datos en el que se mostrarán todos los datos almacenados " +
                "acerca de una entidad 'Paciente' que se encuentra asignada a una entidad 'Médico'"
)
public class PacienteMedicoDTO 
{
                                    // *******************
                                    // ***  ATRIBUTOS  ***
                                    // *******************

    /**
     * Número identificador del paciente asignado al médico consultado.
     */
    @Schema(
            name = "id",
            description = "El número identificador del paciente asignado",
            example = "1"
    )
    private Long id;

    /**
     * Nombre completo del paciente asignado al médico consultado.
     */
    @Schema(
            name = "nombre",
            description = "El nombre completo del paciente asignado",
            example = "Julia Valero Arjona"
    )
    private String nombre;

    /**
     * Género del paciente asignado del médico consultado.
     */
    @Schema(
            name = "genero",
            description = "El género del paciente asignado",
            example = "FEMENINO",
            allowableValues = {"MASCULINO", "FEMENINO", "NO_ESPECIFICADO"}
    )
    private String genero;

    /**
     * Gravedad de la afección del paciente asignado del médico consultado.
     */
    @Schema(
            name = "gravedad",
            description = "La gravedad de la afección del paciente asignado",
            example = "LEVE",
            allowableValues = {"ASINTOMATICA", "LEVE", "MODERADA", "GRAVE", "CRITICA"}
    )
    private String gravedad;

    /**
     * Dirección física donde vive el paciente asignado del médico consultado.
     */
    @Schema(
            name = "direccion",
            description = "La dirección física donde vive el paciente asignado",
            example = "C/ Esperanza Pedralbes, 78"
    )
    private String direccion;

    /**
     * Correo electrónico del paciente asignado del médico consultado.
     */
    @Schema(
            name = "email",
            description = "El correo electrónico del paciente asignado",
            example = "juliavalero@mail.net"
    )
    private String email;

    /**
     * Número telefónico de contacto del paciente asignado del médico consultado.
     */
    @Schema(
            name = "telefono",
            description = "El número telefónico de contacto del paciente asignado",
            example = "681555117"
    )
    private String telefono;

    /**
     * Fecha de nacimiento del paciente asignado en formato 'dd/MM/yyyy hh:mm:ss'
     */
    @Schema(
            name = "fechaNacimiento",
            description = "La fecha de nacimiento del paciente asignado en formato 'dd/MM/yyyy hh:mm:ss'",
            example = "22/07/1973 13:35:00"
    )
    private String fechaNacimiento;

    /**
     * Fecha de ingreso en el hospital del paciente asignado en formato 'dd/MM/yyyy hh:mm:ss'
     */
    @Schema(
            name = "fechaIngreso",
            description = "La fecha de ingreso en el hospital del paciente asignado en formato 'dd/MM/yyyy hh:mm:ss",
            example = "06/10/2012 20:45:30"
    )
    private String fechaIngreso;
}
