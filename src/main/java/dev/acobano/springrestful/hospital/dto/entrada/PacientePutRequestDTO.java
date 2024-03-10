package dev.acobano.springrestful.hospital.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.acobano.springrestful.hospital.validacion.anotaciones.Dni;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para llamadas HTTP de tipo PUT en cuyo interior residen todos los
 * datos necesarios para actualizar a un paciente preexistente en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Schema(
        title = "PacientePutRequestDTO",
        description = "DTO de entrada para llamadas HTTP de tipo PUT en cuyo interior residen todos los " +
                "datos necesarios para modificar a una entidad preexistente de clase 'Paciente' en el sistema."
)
public class PacientePutRequestDTO 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    /**
     * El nombre del paciente a modificar en el sistema.
     */
    @Schema(
            name = "nombre",
            description = "Nombre del paciente a modificar en el sistema",
            example = "Julia",
            nullable = true
    )
    @Size(
            min = 3,
            max = 20,
            message = "El campo 'Nombre' de un paciente debe tener entre 3 y 20 caracteres."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String nombre;

    /**
     * Los apellidos del paciente a modificar en el sistema.
     */
    @Schema(
            name = "apellidos",
            description = "Apellidos del paciente a modificar en el sistema",
            example = "Valero Arjona",
            nullable = true
    )
    @Size(
            min = 3,
            max = 50,
            message = "El campo 'Apellidos' de un paciente debe tener entre 3 y 50 caracteres."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String apellidos;

    /**
     * DNI del paciente a modificar en el sistema.
     */
    @Schema(
            name = "dni",
            description = "DNI del paciente a modificar en el sistema",
            example = "13178724T",
            nullable = true
    )
    @Size(
            min = 9,
            max = 9,
            message = "El campo 'DNI' debe tener 9 caracteres."
    )
    @Pattern(
            regexp = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$",
            message = "El campo 'DNI' debe estar compuesto por ocho cifras y una letra de correcta validación."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @Dni
    private String dni;

    /**
     * El género del paciente a modificar en el sistema.
     */
    @Schema(
            name = "genero",
            description = "Género del paciente a modificar en el sistema",
            example = "FEMENINO",
            allowableValues = {"MASCULINO", "FEMENINO", "NO_ESPECIFICADO"},
            nullable = true
    )
    @Pattern(
            regexp = "MASCULINO|FEMENINO|NO_ESPECIFICADO",
            message = "El dato introducido en el campo 'Especialidad' no cumple con las reglas de validación."
                     + "Únicamente se admiten los valores 'MASCULINO', 'FEMENINO' y 'NO_ESPECIFICADO'"
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String genero;

    /**
     * La dirección física donde vive el paciente a modificar en el sistema.
     */
    @Schema(
            name = "direccion",
            description = "Dirección física donde vive el paciente a modificar en el sistema",
            example = "C/ Esperanza Pedralbes, 78",
            nullable = true
    )
    @Size(max = 50, message = "El campo 'Dirección' de un paciente puede tener hasta 50 caracteres.")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String direccion;

    /**
     * El correo electrónico del paciente a modificar en el sistema.
     */
    @Schema(
            name = "email",
            description = "Correo electrónico del paciente a modificar en el sistema",
            example = "juliavalero@mail.net",
            nullable = true
    )
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El dato introducido en el campo 'Email' no cumple las reglas de validación."
    )
    @Size(
            max = 35,
            message = "El campo 'Email' de un paciente puede tener hasta 35 caracteres."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String email;

    /**
     * El número telefónico de contacto del paciente a modificar en el sistema.
     */
    @Schema(
            name = "telefono",
            description = "Número telefónico del paciente a modificar en el sistema",
            example = "681555117",
            nullable = true
    )
    @Size(
            min = 9,
            max = 9,
            message = "El campo 'Teléfono' debe tener 9 cifras."
    )
    @Pattern(
            regexp = "^[6|9][0-9]{8}$",
            message = "El dato introducido en el campo 'Teléfono' no cumple las reglas de validación."
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String telefono;

    /**
     * La fecha de nacimiento del paciente a modificar en el sistema en formato 'dd/MM/YYYY HH:mm:ss'
     */
    @Schema(
            name = "fechaNacimiento",
            description = "Fecha de nacimiento del paciente a modificar en el sistema bajo formato 'dd/MM/YYYY HH:mm:ss'",
            example = "22/07/1973 13:35:00",
            nullable = true
    )
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String fechaNacimiento;

    /**
     * La fecha de ingreso en el hospital del paciente en formato 'dd/MM/YYYY HH:mm:ss'
     */
    @Schema(
            name = "fechaIngreso",
            description = "Fecha de ingreso en el hospital del paciente bajo formato 'dd/MM/YYYY HH:mm:ss'",
            example = "06/10/2012 20:45:30",
            nullable = true
    )
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String fechaIngreso;

    /**
     * El número identificador del médico asignado al paciente a modificar en el sistema.
     */
    @Schema(
            name = "medicoId",
            description = "Número identificador del médico asignado al paciente a modificar en el sistema",
            example = "1",
            nullable = true
    )
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Long medicoId;

    /**
     * La gravedad de la afección del paciente a modificar en el sistema.
     */
    @Schema(
            name = "gravedad",
            description = "Gravedad de la afección del paciente a modificar",
            example = "LEVE",
            allowableValues = {"ASINTOMATICA", "LEVE", "MODERADA", "GRAVE", "CRITICA"},
            nullable = true
    )
    @Pattern(regexp = "(ASINTOMATICA|LEVE|MODERADA|GRAVE|CRITICA)$",
             message = "El dato introducido en el campo 'Gravedad' no cumple con las reglas de validación."
                     + "Únicamente se admiten los valores 'ASINTOMATICA', 'LEVE', 'MODERADA', 'GRAVE' y 'CRITICA'")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String gravedad;
}
