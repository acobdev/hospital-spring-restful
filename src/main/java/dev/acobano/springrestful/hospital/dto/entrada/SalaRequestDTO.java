package dev.acobano.springrestful.hospital.dto.entrada;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada para llamadas HTTP en cuyo interior residen todos los datos
 * necesarios para guardar o actualizar entidad de clase 'Sala' en el sistema.
 * <>
 * @author Álvaro Cobano
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        title = "SalaRequestDTO",
        description = "DTO de entrada para llamadas HTTP en cuyo interior residen todos los datos " +
                "necesarios para guardar o actualizar una entidad de clase 'Sala' en el sistema."
)
public class SalaRequestDTO 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    /**
     * El número interno del edificio en cuyo lugar se encuentra la sala a guardar en el sistema.
     */
    @NotNull(message = "El campo 'numSala' no puede estar vacío.")
    @Schema(
            name = "numSala",
            description = "Número interno de la sala a guardar",
            example = "101"
    )
    private int numSala;
}
