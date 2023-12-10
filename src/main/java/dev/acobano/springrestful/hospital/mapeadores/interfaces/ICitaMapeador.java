package dev.acobano.springrestful.hospital.mapeadores.interfaces;

import dev.acobano.springrestful.hospital.dto.entrada.CitaPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.CitaPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;

/**
 * Interfaz de la capa de mapeadores que implementa los métodos que gestionan la traducción
 * de datos entre las entidades de clase 'Cita' y sus DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
public interface ICitaMapeador 
{
    Cita convertirPostResquestDtoAEntidad(CitaPostRequestDTO dto);
    Cita convertirPutRequestDtoAEntidad(Cita entidad, CitaPutRequestDTO dto);
    CitaResponseDTO convertirEntidadAResponseDto(Cita entidad);
}
