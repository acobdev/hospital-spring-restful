package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.PacientePostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.PacientePutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IPacienteMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import dev.acobano.springrestful.hospital.servicios.interfaces.IMedicoServicio;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase de la capa de mapeadores encargado de la traducción entre la
 * entidad 'Paciente' y sus respectivos DTOs de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@Component
@Slf4j
public class PacienteMapeadorImpl implements IPacienteMapeador
{
                                    // *******************
                                    // ***  ATRIBUTOS  ***
                                    // *******************

    @Autowired
    private IMedicoServicio medicoServicio;
    
    @Autowired
    private IFechaMapeador fechaMapeador;



                                    // *****************
                                    // ***  MÉTODOS  ***
                                    // *****************

    /**
     * Método que traduce los datos envueltos dentro de un DTO de entrada de clase
     * 'PacientePostRequestDTO' hacia un nuevo objeto de la entidad 'Paciente'.
     *
     * @param dto Datos del DTO de entrada de la clase 'PacientePostRequestDTO'.
     * @return Objeto de la clase 'Paciente' con los datos DTO insertados.
     */
    @Override
    public Paciente convertirPostRequestDtoAEntidad(PacientePostRequestDTO dto)
    {
        log.info("---> convertirPostRequestDtoAEntidad");

        if (Objects.isNull(dto))
            return null;

        Paciente entidad = new Paciente();
        entidad.setNombre(dto.getNombre().trim());
        entidad.setApellidos(dto.getApellidos().trim());
        entidad.setDni(dto.getDni().trim());
        entidad.setEmail((dto.getEmail().trim()));
        entidad.setDireccion(dto.getDireccion().trim());
        entidad.setTelefono(dto.getTelefono().trim());
        entidad.setGenero(this.convertirGenero(dto.getGenero().trim()));
        entidad.setGravedad(this.convertirGravedad(dto.getGravedad().trim()));
        entidad.setFechaNacimiento(this.fechaMapeador
                .convertirStringALocalDateTime(dto.getFechaNacimiento().trim()));
        entidad.setFechaIngreso(this.fechaMapeador
                .convertirStringALocalDateTime(dto.getFechaIngreso().trim()));
                
        //En el caso de que exista un médico con el ID especificado,
        //se llamará a la entidad para establecer la relación correspondiente.
        if (!Objects.isNull(dto.getMedicoId()))
        {
            Optional<Medico> optMedico = this.medicoServicio.buscarMedico(dto.getMedicoId());
            optMedico.ifPresent(entidad::setMedicoAsignado);
        }

        log.info("<--- convertirPostRequestDtoAEntidad");
        return entidad;
    }

    /**
     * Método que actualiza los datos contenidos en un objeto de la entidad 'Paciente'
     * con la información contenida en un DTO de entrada de clase 'PacientePutRequestDTO'.
     *
     * @param entidad Objeto de la clase 'Paciente' a actualizar.
     * @param dto DTO de entrada de la clase 'PacientePutRequestDTO'
     * @return Objeto de la clase 'Paciente' con los datos ya actualizados.
     */
    @Override
    public Paciente convertirPutRequestDtoAEntidad(Paciente entidad, PacientePutRequestDTO dto) 
    {
        log.info("---> convertirPutRequestDtoAEntidad");

        if (Objects.isNull(entidad) || Objects.isNull(dto))
            return null;

        if (!Objects.isNull(dto.getNombre()) && !dto.getNombre().isBlank() && !dto.getNombre().isEmpty())
            entidad.setNombre(dto.getNombre().trim());
        
        if (!Objects.isNull(dto.getApellidos()) && !dto.getApellidos().isBlank() && !dto.getApellidos().isEmpty())
            entidad.setApellidos(dto.getApellidos().trim());
        
        if (!Objects.isNull(dto.getDni()) && !dto.getDni().isBlank() && !dto.getDni().isEmpty())
            entidad.setDni(dto.getDni().trim());
        
        if (!Objects.isNull(dto.getDireccion()) && !dto.getDireccion().isBlank() && !dto.getDireccion().isEmpty())
            entidad.setDireccion(dto.getDireccion().trim());
        
        if (!Objects.isNull(dto.getEmail()) && !dto.getEmail().isBlank() && !dto.getEmail().isEmpty())
            entidad.setEmail(dto.getEmail().trim());
        
        if (!Objects.isNull(dto.getTelefono()) && !dto.getTelefono().isBlank() && !dto.getTelefono().isEmpty())
            entidad.setTelefono(dto.getTelefono().trim());
        
        if (!Objects.isNull(dto.getGenero()) && !dto.getGenero().isBlank() && !dto.getGenero().isEmpty())
            entidad.setGenero(this.convertirGenero(dto.getGenero().trim()));
        
        if (!Objects.isNull(dto.getGravedad()) && !dto.getGravedad().isBlank() && !dto.getGravedad().isEmpty())
            entidad.setGravedad(this.convertirGravedad(dto.getGravedad().trim()));
        
        if (!Objects.isNull(dto.getFechaNacimiento())
                && !dto.getFechaNacimiento().isBlank() && !dto.getFechaNacimiento().isEmpty())
            entidad.setFechaNacimiento(this.fechaMapeador
                    .convertirStringALocalDateTime(dto.getFechaNacimiento().trim()));
        
        if(!Objects.isNull(dto.getFechaIngreso())
                && !dto.getFechaIngreso().isBlank() && !dto.getFechaIngreso().isEmpty())
            entidad.setFechaNacimiento(this.fechaMapeador
                    .convertirStringALocalDateTime(dto.getFechaIngreso().trim()));
        
        if (!Objects.isNull(dto.getMedicoId()))
        {
            Optional<Medico> optMedico = this.medicoServicio.buscarMedico(dto.getMedicoId());
            optMedico.ifPresent(entidad::setMedicoAsignado);
        }

        log.info("<--- convertirPutRequestDtoAEntidad");
        return entidad;
    }

    /**
     * Método que transforma los datos de una entidad de clase 'Paciente' y los
     * envuelve dentro de un DTO de salida de clase 'PacienteResponseDTO'.
     *
     * @param entidad Objeto de la clase 'Paciente'.
     * @return Datos de la entidad envueltos en un DTO de salida de clase 'PacienteResponseDTO'.
     */
    @Override
    public PacienteResponseDTO convertirEntidadAResponseDto(Paciente entidad) 
    {
        log.info("---> convertirEntidadAResponseDto");

        if (Objects.isNull(entidad))
            return null;

        PacienteResponseDTO dto = new PacienteResponseDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setApellidos(entidad.getApellidos());
        dto.setDni(entidad.getDni());
        dto.setEmail(entidad.getEmail());
        dto.setDireccion(entidad.getDireccion());
        dto.setTelefono(entidad.getTelefono());
        dto.setGenero(entidad.getGenero().name());
        dto.setFechaNacimiento(this.fechaMapeador
                .convertirLocalDateTimeAString(entidad.getFechaNacimiento()));
        dto.setFechaIngreso(this.fechaMapeador
                .convertirLocalDateTimeAString(entidad.getFechaIngreso()));
        dto.setGravedad(entidad.getGravedad().name());
        dto.setMedicoAsignado(entidad.getMedicoAsignado().getNombre() + " " +
                              entidad.getMedicoAsignado().getApellidos());
        dto.setAreaTratamiento(entidad.getMedicoAsignado().getEspecialidad().name());
        
        if (Objects.isNull(entidad.getCitasAsignadas()) || entidad.getCitasAsignadas().isEmpty())
            dto.setCitasRegistradas(0);
        else
            dto.setCitasRegistradas(entidad.getCitasAsignadas().size());

        log.info("<--- convertirEntidadAResponseDto");
        return dto;
    }
    
    /**
     * Método que se encarga del mapeo entre entidades y DTOs del
     * atributo enumerado 'Género' presente en los pacientes.
     * @param generoString Cadena de texto con la información sobre el género
     * del paciente.
     * @return Objeto de la clase enumerada 'Genero'. POSIBLES VALORES:
     * MASCULINO | FEMENINO | NO_ESPECIFICADO
     */
    @Override
    public Genero convertirGenero (String generoString)
    {
        log.info("---> convertirGenero");
        if (Objects.isNull(generoString))
            return null;

        Genero genero = switch (generoString)
        {
            case "MASCULINO" -> Genero.MASCULINO;
            case "FEMENINO" -> Genero.FEMENINO;
            case "NO_ESPECIFICADO" -> Genero.NO_ESPECIFICADO;
            default -> null;
        };

        log.info("<--- convertirGenero");
        return genero;
    }
    
    /**
     * Método que se encarga del mapeo entre entidades y DTOs del
     * atributo enumerado 'Gravedad' presente en los pacientes.
     * @param gravedadString Cadena de texto con la información sobre la
     * gravedad de la afección del paciente.
     * @return Objeto de la clase enumerada 'Gravedad'. POSIBLES VALORES:
     * ASINTOMATICA | LEVE | MODERADA | GRAVE | CRITICA
     */
    @Override
    public Gravedad convertirGravedad (String gravedadString)
    {
        log.info("---> convertirGravedad");
        if (Objects.isNull(gravedadString))
            return null;

        Gravedad gravedad = switch (gravedadString)
        {
            case "ASINTOMATICA" -> Gravedad.ASINTOMATICA;
            case "LEVE" -> Gravedad.LEVE;
            case "MODERADA" -> Gravedad.MODERADA;
            case "GRAVE" -> Gravedad.GRAVE;
            case "CRITICA" -> Gravedad.CRITICA;
            default -> null;
        };

        log.info("<--- convertirGravedad");
        return gravedad;
    }
}
