package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.CitaPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.CitaPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import dev.acobano.springrestful.hospital.servicios.interfaces.IPacienteServicio;
import dev.acobano.springrestful.hospital.servicios.interfaces.ISalaServicio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de mapeadores relacionados con la
 * traducción las entidades de clase 'Cita' y sus respectivos DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@ContextConfiguration(classes = CitaMapeadorImpl.class)
@Slf4j
class CitaMapeadorImplTest
{
                                                // *******************
                                                // ***  ATRIBUTOS  ***
                                                // *******************

    @Autowired
    private CitaMapeadorImpl mapeador;

    @MockBean
    private IPacienteServicio pacienteServicio;

    @MockBean
    private ISalaServicio salaServicio;

    @MockBean
    private IFechaMapeador fechaMapeador;



                                                // ***********************
                                                // ***  OBJETOS DUMMY  ***
                                                // ***********************

    private Cita getDummyEntidad()
    {
        return Cita.builder()
                .id(9L)
                .paciente(this.getDummyPacienteAsignado())
                .sala(this.getDummySalaAsignada())
                .fechaCita(LocalDate.MIN)
                .horaEntrada(LocalTime.NOON)
                .horaSalida(LocalTime.NOON)
                .build();
    }

    private Paciente getDummyPacienteAsignado()
    {
        return Paciente.builder()
                .id(5L)
                .nombre("NombrePaciente")
                .apellidos("ApellidosPaciente")
                .medicoAsignado(Medico.builder()
                        .nombre("NombreMedico")
                        .apellidos("ApellidosMedico")
                        .especialidad(Especialidad.TRAUMATOLOGIA)
                        .build())
                .gravedad(Gravedad.MODERADA)
                .build();
    }

    private Sala getDummySalaAsignada()
    {
        return Sala.builder()
                .id(3L)
                .numero(33)
                .build();
    }

    private CitaPostRequestDTO getDummyPostRequestDTO()
    {
        return CitaPostRequestDTO.builder()
                .pacienteId(5L)
                .salaId(3L)
                .fechaCita("Date prueba")
                .horaEntrada("Time prueba")
                .horaSalida("Time prueba")
                .build();
    }

    private CitaPutRequestDTO getDummyPutRequestDTO()
    {
        return CitaPutRequestDTO.builder()
                .pacienteId(5L)
                .salaId(3L)
                .fechaCita("Date prueba")
                .horaEntrada("Time prueba")
                .horaSalida("Time prueba")
                .build();
    }

    private CitaResponseDTO getDummyResponseDTO()
    {
        return CitaResponseDTO.builder()
                .id(9L)
                .medico("NombreMedico ApellidosMedico")
                .paciente("NombrePaciente ApellidosPaciente")
                .numSala(33)
                .gravedad("MODERADA")
                .especialidad("TRAUMATOLOGIA")
                .fechaCita("Date prueba")
                .horaEntrada("Time prueba")
                .horaSalida("Time prueba")
                .build();
    }



                                        // ***************************
                                        // ***  CLASES de TESTING  ***
                                        // ***************************

    @Test
    public void convertirPostResquestDtoAEntidadTestOK()
    {
        log.debug("---> convertirPostResquestDtoAEntidadTestOK");

        //Definición del comportamiento:
        when(pacienteServicio.buscarPaciente(anyLong())).thenReturn(Optional.of(this.getDummyPacienteAsignado()));
        when(salaServicio.buscarSala(anyLong())).thenReturn(Optional.of(this.getDummySalaAsignada()));
        when(fechaMapeador.convertirStringALocalDate(anyString())).thenReturn(LocalDate.MIN);
        when(fechaMapeador.convertirStringALocalTime(anyString())).thenReturn(LocalTime.NOON);

        //Declaración de los objetos de testing:
        CitaPostRequestDTO entrada = this.getDummyPostRequestDTO();
        Cita esperado = this.getDummyEntidad();
        Cita resultado = this.mapeador.convertirPostResquestDtoAEntidad(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertNotNull(resultado.getPaciente()),
                () -> assertNotNull(resultado.getSala()),
                () -> assertEquals(esperado.getPaciente().getId(), resultado.getPaciente().getId()),
                () -> assertEquals(esperado.getSala().getId(), resultado.getSala().getId()),
                () -> assertEquals(esperado.getFechaCita(), resultado.getFechaCita()),
                () -> assertEquals(esperado.getHoraEntrada(), resultado.getHoraEntrada()),
                () -> assertEquals(esperado.getHoraSalida(), resultado.getHoraSalida())
        );

        //Verificaciones:
        verify(pacienteServicio, times(1)).buscarPaciente(anyLong());
        verify(salaServicio, times(1)).buscarSala(anyLong());
        verify(fechaMapeador, times(1)).convertirStringALocalDate(anyString());
        verify(fechaMapeador, times(2)).convertirStringALocalTime(anyString());

        log.debug("<--- convertirPostResquestDtoAEntidadTestOK");
    }

    @Test
    public void convertirPostResquestDtoAEntidadTestNull()
    {
        log.debug("---> convertirPostResquestDtoAEntidadTestNull");
        assertNull(this.mapeador.convertirPostResquestDtoAEntidad(null));
        log.debug("<--- convertirPostResquestDtoAEntidadTestNull");
    }

    @Test
    public void convertirPutRequestDtoAEntidadTestOK()
    {
        log.debug("---> convertirPutRequestDtoAEntidadTestOK");

        //Definición del comportamiento:
        when(pacienteServicio.buscarPaciente(anyLong())).thenReturn(Optional.of(this.getDummyPacienteAsignado()));
        when(salaServicio.buscarSala(anyLong())).thenReturn(Optional.of(this.getDummySalaAsignada()));
        when(fechaMapeador.convertirStringALocalDate(anyString())).thenReturn(LocalDate.MIN);
        when(fechaMapeador.convertirStringALocalTime(anyString())).thenReturn(LocalTime.NOON);

        //Declaraciones de los objetos de testing:
        CitaPutRequestDTO entrada = this.getDummyPutRequestDTO();
        Cita esperado = this.getDummyEntidad();
        Cita resultado = this.mapeador.convertirPutRequestDtoAEntidad(new Cita(), entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertNotNull(resultado.getPaciente()),
                () -> assertNotNull(resultado.getSala()),
                () -> assertEquals(esperado.getPaciente().getId(), resultado.getPaciente().getId()),
                () -> assertEquals(esperado.getSala().getId(), resultado.getSala().getId()),
                () -> assertEquals(esperado.getFechaCita(), resultado.getFechaCita()),
                () -> assertEquals(esperado.getHoraEntrada(), resultado.getHoraEntrada()),
                () -> assertEquals(esperado.getHoraSalida(), resultado.getHoraSalida())
        );

        //Verificaciones:
        verify(pacienteServicio, times(1)).buscarPaciente(anyLong());
        verify(salaServicio, times(1)).buscarSala(anyLong());
        verify(fechaMapeador, times(1)).convertirStringALocalDate(anyString());
        verify(fechaMapeador, times(2)).convertirStringALocalTime(anyString());

        log.debug("<--- convertirPutRequestDtoAEntidadTestOK");
    }

    @Test
    public void convertirPutRequestDtoAEntidadTestNull()
    {
        log.debug("---> convertirPutRequestDtoAEntidadTestNull");

        assertAll(
                () -> assertNull(mapeador.convertirPutRequestDtoAEntidad(this.getDummyEntidad(), null)),
                () -> assertNull(mapeador.convertirPutRequestDtoAEntidad(null, this.getDummyPutRequestDTO())),
                () -> assertNull(mapeador.convertirPutRequestDtoAEntidad(null, null))
        );

        log.debug("<--- convertirPutRequestDtoAEntidadTestNull");
    }

    @Test
    public void convertirEntidadAResponseDtoTestOK()
    {
        log.debug("---> convertirEntidadAResponseDtoTestOK");

        //Definición de comportamiento:
        when(fechaMapeador.convertirLocalDateAString(any())).thenReturn("Date prueba");
        when(fechaMapeador.convertirLocalTimeAString(any())).thenReturn("Time prueba");

        //Declaraciones de objetos de testing:
        Cita entrada = this.getDummyEntidad();
        CitaResponseDTO esperado = this.getDummyResponseDTO();
        CitaResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getId(), resultado.getId()),
                () -> assertEquals(esperado.getMedico(), resultado.getMedico()),
                () -> assertEquals(esperado.getPaciente(), resultado.getPaciente()),
                () -> assertEquals(esperado.getNumSala(), resultado.getNumSala()),
                () -> assertEquals(esperado.getEspecialidad(), resultado.getEspecialidad()),
                () -> assertEquals(esperado.getGravedad(), resultado.getGravedad()),
                () -> assertEquals(esperado.getFechaCita(), resultado.getFechaCita()),
                () -> assertEquals(esperado.getHoraEntrada(), resultado.getHoraEntrada()),
                () -> assertEquals(esperado.getHoraSalida(), resultado.getHoraSalida())
        );

        //Verificaciones:
        verify(fechaMapeador, times(1)).convertirLocalDateAString(any());
        verify(fechaMapeador, times(2)).convertirLocalTimeAString(any());

        log.debug("<--- convertirEntidadAResponseDtoTestOK");
    }

    @Test
    public void convertirEntidadAResponseDtoTestNull()
    {
        log.debug("---> convertirEntidadAResponseDtoTestNull");
        assertNull(this.mapeador.convertirEntidadAResponseDto(null));
        log.debug("<--- convertirEntidadAResponseDtoTestNull");
    }
}