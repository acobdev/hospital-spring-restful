package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.MedicoPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.MedicoPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.MedicoResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteMedicoDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IMedicoMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase de la capa de mapeadores encargado de la traducción entre la
 * entidad 'Médico' y sus respectivos DTOs de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@Component
@Slf4j
public class MedicoMapeadorImpl implements IMedicoMapeador
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
     * 'MedicoPostRequestDTO' hacia un nuevo objeto de la entidad 'Médico'.
     *
     * @param dto Datos del DTO de entrada de la clase 'MedicoPostRequestDTO'.
     * @return Objeto de la clase 'Médico' con los datos DTO insertados.
     */
    @Override
    public Medico convertirPostRequestDtoAEntidad(MedicoPostRequestDTO dto) 
    {
        log.info("---> convertirPostRequestDtoAEntidad");
        if (Objects.isNull(dto))
            return null;
        
        Medico entidad = new Medico();
        entidad.setNombre(dto.getNombre().trim());
        entidad.setApellidos(dto.getApellidos().trim());
        entidad.setDni(dto.getDni().trim());
        entidad.setEmail((dto.getEmail().trim()));
        entidad.setEspecialidad(this.convertirEspecialidad(dto.getEspecialidad().trim()));
        entidad.setFechaGraduacion(this.fechaMapeador
                .convertirStringALocalDate(dto.getFechaGraduacion().trim()));
        entidad.setFechaIncorporacion(this.fechaMapeador
                .convertirStringALocalDate(dto.getFechaIncorporacion().trim()));
        log.info("<--- convertirPostRequestDtoAEntidad");
        return entidad;
    }

    /**
     * Método que actualiza los datos contenidos en un objeto de la entidad 'Médico'
     * con la información contenida en un DTO de entrada de clase 'MedicoPutRequestDTO'.
     *
     * @param entidad Objeto de la clase 'Médico' a actualizar.
     * @param dto DTO de entrada de la clase 'MedicoPutRequestDTO'
     * @return Objeto de la clase 'Médico' con los datos ya actualizados.
     */
    @Override
    public Medico convertirPutRequestDtoAEntidad(Medico entidad, MedicoPutRequestDTO dto) 
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
        
        if (!Objects.isNull(dto.getEmail()) && !dto.getEmail().isBlank() && !dto.getEmail().isEmpty())
            entidad.setEmail(dto.getEmail().trim());
        
        if(!Objects.isNull(dto.getFechaGraduacion()) &&
           !dto.getFechaGraduacion().isBlank() && !dto.getFechaGraduacion().isEmpty())
            entidad.setFechaGraduacion(this.fechaMapeador
                    .convertirStringALocalDate(dto.getFechaGraduacion().trim()));
        
        if(!Objects.isNull(dto.getFechaIncorporacion()) &&
           !dto.getFechaIncorporacion().isBlank() && !dto.getFechaIncorporacion().isEmpty())
            entidad.setFechaIncorporacion(this.fechaMapeador
                    .convertirStringALocalDate(dto.getFechaIncorporacion().trim()));
        
        if (!Objects.isNull(dto.getEspecialidad())
                && !dto.getEspecialidad().isBlank() && !dto.getEspecialidad().isEmpty())
            entidad.setEspecialidad(this.convertirEspecialidad(dto.getEspecialidad().trim()));

        log.info("<--- convertirPutRequestDtoAEntidad");
        return entidad;
    }

    /**
     * Método que transforma los datos de una entidad de clase 'Médico' y los
     * envuelve dentro de un DTO de salida de clase 'MedicoResponseDTO'.
     *
     * @param entidad Objeto de la clase 'Médico'.
     * @return Datos de la entidad envueltos en un DTO de salida de clase 'MedicoResponseDTO'.
     */
    @Override
    public MedicoResponseDTO convertirEntidadAResponseDto(Medico entidad) 
    {
        log.info("---> convertirEntidadAResponseDto");

        if (Objects.isNull(entidad))
            return null;
        
        MedicoResponseDTO dto = new MedicoResponseDTO();
        
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setApellidos(entidad.getApellidos());
        dto.setDni(entidad.getDni());
        dto.setEmail(entidad.getEmail());
        dto.setEspecialidad(entidad.getEspecialidad().name());
        dto.setFechaGraduacion(this.fechaMapeador.
                convertirLocalDateAString(entidad.getFechaGraduacion()));
        dto.setFechaIncorporacion(this.fechaMapeador
                .convertirLocalDateAString(entidad.getFechaIncorporacion()));

        if (Objects.isNull(entidad.getPacientesAsignados()) || entidad.getPacientesAsignados().isEmpty())
            dto.setPacientesAsignados(0);
        else
            dto.setPacientesAsignados(entidad.getPacientesAsignados().size());

        log.info("<--- convertirEntidadAResponseDto");
        return dto;
    }

    /**
     * Método que devuelve como lista de DTOs de salida todos aquellos datos relacionados con
     * entidades 'Paciente' que se encuentren asignados a la entidad 'Médico' que se le
     * pasa como parámetro de entrada.
     *
     * @param medico Objeto de la clase 'Médico' cuyos pacientes se desee listar.
     * @return Lista de objetos de la clase 'Paciente' envueltos en DTOs de salida de clase 'PacienteMedicoDTO'.
     */
    @Override
    public List<PacienteMedicoDTO> convertirPacientesAsignadosADto(Medico medico) 
    {
        log.info("---> convertirPacientesAsignadosADto");

        if (Objects.isNull(medico))
            return null;

        List<PacienteMedicoDTO> listaDto = new ArrayList<>(medico.getPacientesAsignados().size());
        
        for (Paciente p : medico.getPacientesAsignados())
        {
            PacienteMedicoDTO dto = new PacienteMedicoDTO();
            
            dto.setId(p.getId());
            dto.setNombre(p.getNombre() + " " + p.getApellidos());
            dto.setGenero(p.getGenero().name());
            dto.setGravedad(p.getGravedad().name());
            dto.setDireccion(p.getDireccion());
            dto.setEmail(p.getEmail());
            dto.setTelefono(p.getTelefono());            
            dto.setFechaNacimiento(this.fechaMapeador
                    .convertirLocalDateTimeAString(p.getFechaNacimiento()));
            dto.setFechaIngreso(this.fechaMapeador
                    .convertirLocalDateTimeAString(p.getFechaIngreso()));
            
            listaDto.add(dto);
        }

        log.info("<--- convertirPacientesAsignadosADto");
        return listaDto;
    }

    /**
     * Método que traduce una cadena de texto de entrada en una determinada
     * instancia perteneciente a la clase enumerada 'Especialidad'.
     *
     * @param especialidadString Cadena de texto con el nombre de la especialidad a buscar.
     * @return Objeto de la clase enumerada 'Especialidad' con los datos obtenidos.
     */
    @Override
    public Especialidad convertirEspecialidad(String especialidadString) 
    {
        log.info("---> convertirEspecialidad");

        if (Objects.isNull(especialidadString))
            return null;

        Especialidad especialidad = switch (especialidadString)
        {
            case "CIRUGIA" -> Especialidad.CIRUGIA;
            case "PEDIATRIA" -> Especialidad.PEDIATRIA;
            case "ONCOLOGIA" -> Especialidad.ONCOLOGIA;
            case "CARDIOLOGIA" -> Especialidad.CARDIOLOGIA;
            case "GINECOLOGIA" -> Especialidad.GINECOLOGIA;
            case "TRAUMATOLOGIA" -> Especialidad.TRAUMATOLOGIA;
            case "DERMATOLOGIA" -> Especialidad.DERMATOLOGIA;
            case "PSIQUIATRIA" -> Especialidad.PSIQUIATRIA;
            case "OFTALMOLOGIA" -> Especialidad.OFTALMOLOGIA;
            default -> null;
        };

        log.info("<--- convertirEspecialidad");
        return especialidad;
    }
}
