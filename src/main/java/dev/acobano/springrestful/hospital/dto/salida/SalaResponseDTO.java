package dev.acobano.springrestful.hospital.dto.salida;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de salida en el que se mostrarán todos los datos almacenados acerca de
 * una entidad de clase 'Sala' consultada por el usuario en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "SalaResponseDTO",
        description = "DTO de salida en el que se mostrarán todos los datos almacenados acerca " +
                "de una entidad de la clase 'Sala' consultada por el usuario en el sistema."
)
public class SalaResponseDTO 
{
                                    // *******************
                                    // ***  ATRIBUTOS  ***
                                    // *******************

    /**
     * El número identificador de la sala consultada en el sistema.
     */
    @Schema(
            name = "id",
            description = "Número identificador de la sala consultada",
            example = "1"
    )
    private Long id;

    /**
     * El número interno del edificio en cuyo lugar se encuentra la sala consultada.
     */
    @Schema(
            name = "numSala",
            description = "Número interno de la sala consultada",
            example = "101"
    )
    private int numSala;

    /**
     * El número total de citas que la sala tiene asignadas en el sistema.
     */
    @Schema(
            name = "citasAsignadas",
            description = "Número total de citas que la sala tiene asignadas",
            example = "1"
    )
    private int citasAsignadas;
}
