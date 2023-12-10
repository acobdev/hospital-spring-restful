package dev.acobano.springrestful.hospital.mapeadores.interfaces;

import dev.acobano.springrestful.hospital.dto.entrada.PacientePostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.PacientePutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteResponseDTO;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;

/**
 * Interfaz de la capa de mapeadores que implementa los métodos que gestionan la traducción
 * de datos entre las entidades de clase 'Paciente' y sus DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
public interface IPacienteMapeador 
{
    Paciente convertirPostRequestDtoAEntidad(PacientePostRequestDTO dto);
    Paciente convertirPutRequestDtoAEntidad(Paciente entidad, PacientePutRequestDTO dto);
    PacienteResponseDTO convertirEntidadAResponseDto(Paciente entidad);
    Genero convertirGenero (String generoString);
    Gravedad convertirGravedad (String gravedadString);
}
