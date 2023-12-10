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
 * una entidad de clase 'Médico' consultado por el usuario en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MedicoResponseDTO",
        description = "DTO de salida en el que se mostrarán los datos almacenados " +
                      "del médico consultado por el usuario en el sistema.")
public class MedicoResponseDTO 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    /**
     * El número identificador del médico consultado en el sistema.
     */
    @Schema(
            name = "id",
            description = "Número identificador del médico consultado en el sistema",
            example = "1"
    )
    private Long id;

    /**
     * El nombre del médico consultado en el sistema.
     */
    @Schema(
            name = "nombre",
            description = "Nombre del médico consultado en el sistema",
            example = "Daniel"
    )
    private String nombre;

    /**
     * Los apellidos del médico consultado en el sistema.
     */
    @Schema(
            name = "apellidos",
            description = "Apellidos del médico consultado en el sistema",
            example = "Ferrero Portillo"
    )
    private String apellidos;

    /**
     * El DNI del médico consultado en el sistema.
     */
    @Schema(
            name = "dni",
            description = "DNI del médico consultado en el sistema",
            example = "90190141C"
    )
    private String dni;

    /**
     * El correo electrónico del médico consultado en el sistema.
     */
    @Schema(
            name = "email",
            description = "Correo electrónico del médico consultado en el sistema",
            example = "hugomedico@mail.com"
    )
    private String email;

    /**
     * La fecha de graduación del médico consultado en el sistema en formato 'dd/MM/YYYY'
     */
    @Schema(
            name = "fechaGraduacion",
            description = "Fecha de graduación del médico consultado en el sistema en formato 'dd/MM/YYYY'",
            example = "13/05/1987"
    )
    @JsonFormat(pattern = "dd/MM/YYYY")
    private String fechaGraduacion;

    /**
     * La fecha de incorporación del médico consultado en el sistema en formato 'dd/MM/YYYY'
     */
    @Schema(
            name = "fechaIncorporacion",
            description = "Fecha de incorporación del médico consultado en el sistema en formato 'dd/MM/YYYY'",
            example = "26/11/1998"
    )
    @JsonFormat(pattern = "dd/MM/YYYY")
    private String fechaIncorporacion;

    /**
     * La especialidad del médico consultado en el sistema.
     */
    @Schema(
            name = "especialidad",
            description = "Especialidad del médico consultado en el sistema",
            example = "CARDIOLOGIA",
            allowableValues = {"CARDIOLOGIA", "CIRUGIA", "DERMATOLOGIA", "GINECOLOGIA", "OFTALMOLOGIA",
                    "ONCOLOGIA", "PEDIATRIA", "PSIQUIATRIA", "TRAUMATOLOGIA"}
    )
    private String especialidad;

    /**
     * Número de pacientes asginados al médico consultado en el sistema.
     */
    @Schema(
            name = "pacientesAsignados",
            description = "Número de pacientes asginados al médico consultado en el sistema",
            example = "1"
    )
    private int pacientesAsignados;
}
