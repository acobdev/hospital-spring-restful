package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.CitaPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.CitaPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.servicios.interfaces.IPacienteServicio;
import dev.acobano.springrestful.hospital.servicios.interfaces.ISalaServicio;

import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase de la capa de mapeadores encargado de la traducción entre la
 * entidad 'Cita' y sus respectivos DTOs de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@Component
@Slf4j
public class CitaMapeadorImpl implements ICitaMapeador
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private IPacienteServicio pacienteServicio;
    
    @Autowired
    private ISalaServicio salaServicio;
    
    @Autowired
    private IFechaMapeador fechaMapeador;
    


                                        // *****************
                                        // ***  MÉTODOS  ***
                                        // *****************

    /**
     * Método que traduce los datos envueltos dentro de un DTO de entrada de clase
     * 'CitaPostRequestDTO' hacia un nuevo objeto de la entidad 'Cita'.
     *
     * @param dto Datos del DTO de entrada de la clase 'CitaPostRequestDTO'.
     * @return Objeto de la clase 'Cita' con los datos DTO insertados.
     */
    @Override
    public Cita convertirPostResquestDtoAEntidad(CitaPostRequestDTO dto)
    {
        log.info("---> convertirPostResquestDtoAEntidad");
        if (Objects.isNull(dto))
            return null;

        Cita entidad = new Cita();
        
        if (!Objects.isNull(dto.getPacienteId()))
        {
            Optional<Paciente> optPaciente = this.pacienteServicio.buscarPaciente(dto.getPacienteId());
            optPaciente.ifPresent(entidad::setPaciente);
        }
        
        if (!Objects.isNull(dto.getSalaId()))
        {
            Optional<Sala> optSala = this.salaServicio.buscarSala(dto.getSalaId());
            optSala.ifPresent(entidad::setSala);
        }
        
        entidad.setFechaCita(this.fechaMapeador
                .convertirStringALocalDate(dto.getFechaCita().trim()));
        entidad.setHoraEntrada(this.fechaMapeador
                .convertirStringALocalTime(dto.getHoraEntrada().trim()));
        entidad.setHoraSalida(this.fechaMapeador
                .convertirStringALocalTime(dto.getHoraSalida().trim()));

        log.info("<--- convertirPostResquestDtoAEntidad");
        return entidad;
    }

    /**
     * Método que actualiza los datos contenidos en un objeto de la entidad 'Médico'
     * con la información contenida en un DTO de entrada de clase 'CitaPutRequestDTO'.
     *
     * @param entidad Objeto de la clase 'Médico' a actualizar.
     * @param dto DTO de entrada de la clase 'CitaPutRequestDTO'
     * @return Objeto de la clase 'Cita' con los datos ya actualizados.
     */
    @Override
    public Cita convertirPutRequestDtoAEntidad(Cita entidad, CitaPutRequestDTO dto)
    {
        log.info("---> convertirPutRequestDtoAEntidad");
        if (Objects.isNull(entidad) || Objects.isNull(dto))
            return null;

        if (!Objects.isNull(dto.getPacienteId()))
        {
            Optional<Paciente> optPaciente = this.pacienteServicio.buscarPaciente(dto.getPacienteId());
            optPaciente.ifPresent(entidad::setPaciente);
        }
        
        if (!Objects.isNull(dto.getSalaId()))
        {
            Optional<Sala> optSala = this.salaServicio.buscarSala(dto.getSalaId());
            optSala.ifPresent(entidad::setSala);
        }
        
        if (!Objects.isNull(dto.getFechaCita()) &&
            !dto.getFechaCita().isBlank() && !dto.getFechaCita().isEmpty())
            entidad.setFechaCita(this.fechaMapeador
                    .convertirStringALocalDate(dto.getFechaCita().trim()));
        
        if (!Objects.isNull(dto.getHoraEntrada()) &&
            !dto.getHoraEntrada().isBlank() && !dto.getHoraEntrada().isEmpty())
            entidad.setHoraEntrada(this.fechaMapeador
                    .convertirStringALocalTime(dto.getHoraEntrada().trim()));
        
        if (!Objects.isNull(dto.getHoraSalida()) &&
            !dto.getHoraSalida().isBlank() && !dto.getHoraSalida().isEmpty())
            entidad.setHoraSalida(this.fechaMapeador
                    .convertirStringALocalTime(dto.getHoraSalida().trim()));

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
    public CitaResponseDTO convertirEntidadAResponseDto(Cita entidad)
    {
        log.info("---> convertirEntidadAResponseDto");

        if (Objects.isNull(entidad))
            return null;

        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setId(entidad.getId());
        dto.setMedico(entidad.getPaciente().getMedicoAsignado().getNombre() + " " +
                      entidad.getPaciente().getMedicoAsignado().getApellidos());
        dto.setPaciente(entidad.getPaciente().getNombre() + " " +
                        entidad.getPaciente().getApellidos());
        dto.setNumSala(entidad.getSala().getNumero());
        dto.setGravedad(entidad.getPaciente().getGravedad().name());
        dto.setEspecialidad(entidad.getPaciente().getMedicoAsignado().getEspecialidad().name());
        dto.setFechaCita(this.fechaMapeador.convertirLocalDateAString(entidad.getFechaCita()));
        dto.setHoraEntrada(this.fechaMapeador.convertirLocalTimeAString(entidad.getHoraEntrada()));
        dto.setHoraSalida(this.fechaMapeador.convertirLocalTimeAString(entidad.getHoraSalida()));
        log.info("<--- convertirEntidadAResponseDto");
        return dto;
    }   
}
