package dev.acobano.springrestful.hospital.mapeadores.interfaces;

import dev.acobano.springrestful.hospital.dto.entrada.SalaRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.SalaResponseDTO;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;

/**
 * Interfaz de la capa de mapeadores que implementa los métodos que gestionan la traducción
 * de datos entre las entidades de clase 'Sala' y sus DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
public interface ISalaMapeador 
{
    Sala convertirRequestDtoAEntidad(SalaRequestDTO dto);
    SalaResponseDTO convertirEntidadAResponseDto(Sala entidad);
}
