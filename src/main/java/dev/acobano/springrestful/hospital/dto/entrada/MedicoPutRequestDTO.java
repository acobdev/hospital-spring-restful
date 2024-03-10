package dev.acobano.springrestful.hospital.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.acobano.springrestful.hospital.validacion.anotaciones.Dni;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO de entrada para llamadas HTTP de tipo PUT en cuyo interior residen todos los
 * datos necesarios para actualizar a un médico preexistente en el sistema.
 * <>
 * @author Álvaro Cobano
 */

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Schema(title = "MedicoPutRequestDTO",
        description = "DTO de entrada para llamadas HTTP PUT en cuyo interior residen todos los datos " +
                      "necesarios para actualizar a un médico existente en el sistema.")
public class MedicoPutRequestDTO 
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    /**
     * El nombre del médico a modificar en el sistema.
     */
    @Schema(
            name = "nombre",
            description = "Nombre del médico a modificar en el sistema",
            example = "Daniel",
            nullable = true
    )
    @Size(
            min = 3,
            max = 20,
            message = "El campo 'Nombre' de un médico debe tener entre 3 y 20 caracteres."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String nombre;

    /**
     * Los apellidos del médico a modificar en el sistema.
     */
    @Schema(
            name = "apellidos",
            description = "Apellidos del médico a modificar en el sistema",
            example = "Ferrero Portillo",
            nullable = true
    )
    @Size(
            min = 3,
            max = 50,
            message = "El campo 'Apellidos' de un médico debe tener entre 3 y 50 caracteres."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String apellidos;

    /**
     * El DNI del médico a modificar en el sistema.
     */
    @Schema(
            name = "dni",
            description = "DNI del médico a modificar en el sistema",
            example = "90190141C",
            nullable = true
    )
    @Pattern(
            regexp = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$",
            message = "El campo 'DNI' debe estar compuesto por ocho cifras y una letra de correcta validación."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String dni;

    /**
     * El correo electrónico del médico a modificar en el sistema.
     */
    @Schema(
            name = "email",
            description = "Correo electrónico del médico a modificar en el sistema",
            example = "hugomedico@mail.com",
            nullable = true
    )
    @Size(
            max = 35,
            message = "El campo 'Email' de un médico no puede tener más de 35 caracteres."
    )
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El dato introducido en el campo 'Email' no cumple las reglas de validación."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String email;

    /**
     * La fecha de graduación del médico a modificar en formato 'dd/MM/YYYY'
     */
    @Schema(
            name = "fechaGraduacion",
            description = "Fecha de graduación del médico a modificar en formato 'dd/MM/YYYY'",
            example = "13/05/1987",
            nullable = true
    )
    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$",
            message = "El campo 'fechaGraduacion' debe tener formato 'dd/MM/yyyy'."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String fechaGraduacion;

    /**
     * La fecha de incorporación del médico a modificar en formato 'dd/MM/YYYY'
     */
    @Schema(
            name = "fechaIncorporacion",
            description = "Fecha de incorporación del médico a modificar en formato 'dd/MM/YYYY'",
            example = "26/11/1998",
            nullable = true
    )
    @Pattern(
            regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$",
            message = "El campo 'fechaIncorporacion' debe tener formato 'dd/MM/yyyy'."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String fechaIncorporacion;

    /**
     * La especialidad del médico a modificar en el sistema.
     */
    @Schema(
            name = "especialidad",
            description = "Especialidad del médico a modificar en el sistema",
            nullable = true,
            example = "CARDIOLOGIA",
            allowableValues = {"CARDIOLOGIA", "CIRUGIA", "DERMATOLOGIA", "GINECOLOGIA", "OFTALMOLOGIA",
                    "ONCOLOGIA", "PEDIATRIA", "PSIQUIATRIA", "TRAUMATOLOGIA"}
    )
    @Pattern(
            regexp = "(CARDIOLOGIA|CIRUGIA|DERMATOLOGIA|GINECOLOGIA|OFTALMOLOGIA|ONCOLOGIA|PEDIATRIA|PSIQUIATRIA|TRAUMATOLOGIA)$",
            message = "El dato introducido en el campo 'Especialidad' no cumple con las reglas de validación."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String especialidad;
}
