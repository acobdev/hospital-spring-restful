package dev.acobano.springrestful.hospital.mapeadores.interfaces;

import dev.acobano.springrestful.hospital.dto.entrada.MedicoPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.MedicoPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.MedicoResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteMedicoDTO;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import java.util.List;

/**
 * Interfaz de la capa de mapeadores que implementa los métodos que gestionan la traducción
 * de datos entre las entidades de clase 'Médico' y sus DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
public interface IMedicoMapeador 
{
    Medico convertirPostRequestDtoAEntidad(MedicoPostRequestDTO dto);
    Medico convertirPutRequestDtoAEntidad(Medico entidad, MedicoPutRequestDTO dto);
    MedicoResponseDTO convertirEntidadAResponseDto(Medico entidad);
    List<PacienteMedicoDTO> convertirPacientesAsignadosADto(Medico medico);
    Especialidad convertirEspecialidad(String especialidadString);
}
