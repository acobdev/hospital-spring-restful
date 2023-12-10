package dev.acobano.springrestful.hospital.dto.entrada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para llamadas HTTP de tipo POST en cuyo interior residen todos los datos
 * necesarios para guardar una nueva entidad de clase 'Paciente' en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Schema(
        title = "PacientePostRequestDTO",
        description = "DTO de entrada para llamadas HTTP de tipo POST en cuyo interior residen todos los " +
                "datos necesarios para guardar una nueva entidad de clase 'Paciente' en el sistema."
)
public class PacientePostRequestDTO 
{
                                    // *******************
                                    // ***  ATRIBUTOS  ***
                                    // *******************

    /**
     * El nombre del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "nombre",
            description = "Nombre del paciente a ingresar en el sistema",
            example = "Julia"
    )
    @NotBlank(message = "El campo 'Nombre' de un paciente no puede estar vacío.")
    @Size(
            min = 3,
            max = 20,
            message = "El campo 'Nombre' de un paciente debe tener entre 3 y 20 caracteres."
    )
    private String nombre;

    /**
     * Los apellidos del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "apellidos",
            description = "Apellidos del paciente a ingresar en el sistema",
            example = "Valero Arjona"
    )
    @NotBlank(message = "El campo 'Apellidos' de un paciente no puede estar vacío.")
    @Size(
            min = 3,
            max = 50,
            message = "El campo 'Apellidos' de un paciente debe tener entre 3 y 50 caracteres."
    )
    private String apellidos;

    /**
     * DNI del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "dni",
            description = "DNI del paciente a ingresar en el sistema",
            example = "13178724T"
    )
    @NotBlank(message = "El campo 'DNI' de un paciente no puede estar vacío.")
    @Size(
            min = 9,
            max = 9,
            message = "El campo 'DNI' debe tener 9 caracteres.")

    @Pattern(
            regexp = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$",
            message = "El dato introducido en el campo 'DNI' no cumple las reglas de validación."
    )
    private String dni;

    /**
     * El género del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "genero",
            description = "Género del paciente a ingresar en el sistema",
            example = "FEMENINO",
            allowableValues = {"MASCULINO", "FEMENINO", "NO_ESPECIFICADO"}
    )
    @NotBlank(message = "El campo 'Género' de un paciente no puede estar vacío.")
    @Pattern(
            regexp = "(MASCULINO|FEMENINO|NO_ESPECIFICADO)$",
            message = "El dato introducido en el campo 'Género' no cumple con las reglas de validación."
    )
    private String genero;

    /**
     * La dirección física donde vive el paciente a ingresar en el sistema.
     */
    @Schema(
            name = "direccion",
            description = "Dirección física donde vive el paciente a ingresar en el sistema",
            example = "C/ Esperanza Pedralbes, 78"
    )
    @NotBlank(message = "El campo 'Dirección' de un paciente no puede estar vacío.")
    @Size(
            min = 3,
            max = 50,
            message = "El campo 'Dirección' de un paciente puede tener hasta 50 caracteres."
    )
    private String direccion;

    /**
     * El correo electrónico del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "email",
            description = "Correo electrónico del paciente a ingresar en el sistema",
            example = "juliavalero73@mail.net"
    )
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El dato introducido en el campo 'Email' no cumple las reglas de validación."
    )
    @NotBlank(message = "El campo 'Email' de un paciente no puede ser nulo.")
    @Size(
            max = 35,
            message = "El campo 'Email' de un paciente puede tener hasta 35 caracteres"
    )
    private String email;

    /**
     * El número telefónico de contacto del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "telefono",
            description = "Número telefónico del paciente a ingresar en el sistema",
            example = "681555117"
    )
    @NotBlank(message = "El campo 'Teléfono' de un paciente no puede estar vacío.")
    @Size(
            min = 9,
            max = 9,
            message = "El campo 'Teléfono' debe de tener nueve cifras."
    )
    @Pattern(regexp = "^[6|9][0-9]{8}$",
             message = "El dato introducido en el campo 'Teléfono' no cumple las reglas de validación.")
    private String telefono;

    /**
     * La fecha de nacimiento del paciente a ingresar en el sistema en formato 'dd/MM/YYYY HH:mm:ss'
     */
    @Schema(
            name = "fechaNacimiento",
            description = "Fecha de nacimiento del paciente a ingresar en el sistema bajo formato 'dd/MM/YYYY HH:mm:ss'",
            example = "22/07/1973 13:35:00"
    )
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @NotBlank(message = "El campo 'Fecha nacimiento' de un paciente no puede estar vacío.")
    private String fechaNacimiento;

    /**
     * La fecha de ingreso en el hospital del paciente en formato 'dd/MM/YYYY HH:mm:ss'
     */
    @Schema(
            name = "fechaIngreso",
            description = "Fecha de ingreso en el hospital del paciente bajo formato 'dd/MM/YYYY HH:mm:ss'",
            example = "06/10/2012 20:45:30"
    )
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @NotBlank(message = "El campo 'Fecha ingreso' de un paciente no puede estar vacío.")
    private String fechaIngreso;

    /**
     * El número identificador del médico asignado al paciente a ingresar en el sistema.
     */
    @Schema(
            name = "medicoId",
            description = "Número identificador del médico asignado al paciente a ingresar en el sistema",
            example = "1"
    )
    @NotNull(message = "Todo paciente debe tener asignado un médico mediante su número identificador.")
    private Long medicoId;

    /**
     * La gravedad de la afección del paciente a ingresar en el sistema.
     */
    @Schema(
            name = "gravedad",
            description = "Gravedad de la afección del paciente a ingresar",
            example = "LEVE",
            allowableValues = {"ASINTOMATICA", "LEVE", "MODERADA", "GRAVE", "CRITICA"}
    )
    @NotBlank(message = "El campo 'Gravedad' de un paciente no puede ser nulo.")
    @Pattern(
            regexp = "(ASINTOMATICA|LEVE|MODERADA|GRAVE|CRITICA)$",
            message = "El dato introducido en el campo 'Gravedad' no cumple las reglas de validación."
    )
    private String gravedad;
}
