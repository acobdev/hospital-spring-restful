package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.SalaRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.SalaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ISalaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase de la capa de mapeadores encargado de la traducción entre la
 * entidad 'Sala' y sus respectivos DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@Component
@Slf4j
public class SalaMapeadorImpl implements ISalaMapeador
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private IFechaMapeador fechaMapeador;



                                        // *****************
                                        // ***  MÉTODOS  ***
                                        // *****************

    /**
     * Método que traduce los datos envueltos dentro de un DTO de entrada de clase
     * 'SalaRequestDTO' hacia un nuevo objeto de la entidad 'Sala'.
     *
     * @param dto Datos del DTO de entrada de la clase 'SalaRequestDTO'.
     * @return Objeto de la clase 'Sala' con los datos DTO insertados.
     */
    @Override
    public Sala convertirRequestDtoAEntidad(SalaRequestDTO dto) 
    {
        log.info("---> convertirRequestDtoAEntidad");

        if (Objects.isNull(dto))
            return null;

        Sala entidad = new Sala();
        entidad.setNumero(dto.getNumSala());
        log.info("<--- convertirRequestDtoAEntidad");
        return entidad;
    }

    /**
     * Método que transforma los datos de una entidad de clase 'Sala' y los
     * envuelve dentro de un DTO de salida de clase 'SalaResponseDTO'.
     *
     * @param entidad Objeto de la clase 'Sala'.
     * @return Datos de la entidad envueltos en un DTO de salida de clase 'SalaResponseDTO'.
     */
    @Override
    public SalaResponseDTO convertirEntidadAResponseDto(Sala entidad) 
    {
        log.info("---> convertirEntidadAResponseDto");

        if (Objects.isNull(entidad))
            return null;

        SalaResponseDTO dto = new SalaResponseDTO();
        dto.setId(entidad.getId());
        dto.setNumSala(entidad.getNumero());
        
        if (Objects.isNull(entidad.getCitasAsignadas()) || entidad.getCitasAsignadas().isEmpty())
            dto.setCitasAsignadas(0);
        else
            dto.setCitasAsignadas(entidad.getCitasAsignadas().size());

        log.info("<--- convertirEntidadAResponseDto");
        return dto;
    }
}
