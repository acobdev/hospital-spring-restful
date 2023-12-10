package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.PacientePostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.PacientePutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IPacienteMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import dev.acobano.springrestful.hospital.servicios.interfaces.IMedicoServicio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de mapeadores relacionados con la
 * traducción las entidades de clase 'Paciente' y sus respectivos DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@ContextConfiguration(classes = PacienteMapeadorImpl.class)
@Slf4j
class PacienteMapeadorImplTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private IPacienteMapeador mapeador;

    @MockBean
    private IFechaMapeador fechaMapeador;

    @MockBean
    private IMedicoServicio medicoServicio;



                                        // ***********************
                                        // ***  OBJETOS DUMMY  ***
                                        // ***********************
    private final Genero MASCULINO = Genero.MASCULINO;
    private final Genero FEMENINO = Genero.FEMENINO;
    private final Genero NO_ESPECIFICADO = Genero.NO_ESPECIFICADO;

    private final Gravedad ASINTOMATICA = Gravedad.ASINTOMATICA;
    private final Gravedad LEVE = Gravedad.LEVE;
    private final Gravedad MODERADA = Gravedad.MODERADA;
    private final Gravedad GRAVE = Gravedad.GRAVE;
    private final Gravedad CRITICA = Gravedad.CRITICA;

    private Paciente getDummyEntidadSinCitas()
    {
        return Paciente.builder()
                .id(5L)
                .nombre("Paciente prueba")
                .apellidos("Entidad")
                .dni("98765432X")
                .direccion("Avda direccion de prueba, 15")
                .email("paciente@prueba.es")
                .telefono("955789555")
                .genero(MASCULINO)
                .gravedad(LEVE)
                .medicoAsignado(this.getDummyMedicoAsignado())
                .citasAsignadas(Collections.emptyList())
                .build();
    }

    private Paciente getDummyEntidadConCitaAsignada()
    {
        return Paciente.builder()
                .id(5L)
                .nombre("Paciente prueba")
                .apellidos("Entidad")
                .dni("98765432X")
                .direccion("Avda direccion de prueba, 15")
                .email("paciente@prueba.es")
                .telefono("955789555")
                .genero(MASCULINO)
                .gravedad(LEVE)
                .medicoAsignado(this.getDummyMedicoAsignado())
                .citasAsignadas(List.of(this.getDummyCitaAsignada()))
                .build();
    }

    private Medico getDummyMedicoAsignado()
    {
        return Medico.builder()
                .id(9L)
                .nombre("Medico asignado")
                .apellidos("Paciente mapeador test")
                .especialidad(Especialidad.TRAUMATOLOGIA)
                .build();
    }

    private Cita getDummyCitaAsignada()
    {
        return Cita.builder()
                .id(7L)
                .sala(Sala.builder()
                        .id(2L)
                        .numero(101)
                        .build())
                .paciente(Paciente.builder()
                        .id(5L)
                        .medicoAsignado(this.getDummyMedicoAsignado())
                        .gravedad(LEVE)
                        .build())
                .build();
    }

    private PacientePostRequestDTO getDummyPostRequestDTO()
    {
        return PacientePostRequestDTO.builder()
                .nombre("Paciente prueba")
                .apellidos("Post request DTO")
                .dni("98765432X")
                .direccion("Avda direccion de prueba, 15")
                .email("paciente@prueba.es")
                .telefono("955789555")
                .medicoId(9L)
                .genero("MASCULINO")
                .gravedad("LEVE")
                .fechaNacimiento("DateTime prueba")
                .fechaIngreso("Otro DateTime prueba")
                .build();
    }

    private PacientePutRequestDTO getDummyPutRequestDTO()
    {
        return PacientePutRequestDTO.builder()
                .nombre("Paciente prueba")
                .apellidos("Put request DTO")
                .dni("98765432X")
                .direccion("Avda direccion de prueba, 15")
                .email("paciente@prueba.es")
                .telefono("955789555")
                .genero("MASCULINO")
                .gravedad("LEVE")
                .fechaNacimiento("DateTime prueba")
                .fechaIngreso("DateTime prueba")
                .medicoId(9L)
                .build();
    }

    private PacienteResponseDTO getDummyResponseDTO()
    {
        return PacienteResponseDTO.builder()
                .id(5L)
                .nombre("Paciente prueba")
                .apellidos("Put request DTO")
                .dni("98765432X")
                .direccion("Avda direccion de prueba, 15")
                .email("paciente@prueba.es")
                .telefono("955789555")
                .genero("MASCULINO")
                .gravedad("LEVE")
                .fechaNacimiento("DateTime prueba")
                .fechaIngreso("DateTime prueba")
                .medicoAsignado("Medico asignado Paciente mapeador test")
                .areaTratamiento("TRAUMATOLOGIA")
                .build();
    }



                                    // ***************************
                                    // ***  CLASES de TESTING  ***
                                    // ***************************

    @Test
    public void convertirPostRequestDtoAEntidadTestOK()
    {
        log.debug("---> convertirPostRequestDtoAEntidadTestOK");

        //Definiciones de comportamiento:
        when(fechaMapeador.convertirStringALocalDateTime(anyString())).thenReturn(LocalDateTime.MIN);
        when(medicoServicio.buscarMedico(anyLong())).thenReturn(Optional.of(this.getDummyMedicoAsignado()));

        //Declaración de objetos de testing:
        PacientePostRequestDTO entrada = this.getDummyPostRequestDTO();
        Paciente esperado = this.getDummyEntidadSinCitas();
        Paciente resultado = this.mapeador.convertirPostRequestDtoAEntidad(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getDireccion(), resultado.getDireccion()),
                () -> assertEquals(esperado.getTelefono(), resultado.getTelefono()),
                () -> assertEquals(esperado.getGenero(), resultado.getGenero()),
                () -> assertEquals(esperado.getGravedad(), resultado.getGravedad()),
                () -> assertEquals(esperado.getMedicoAsignado().getId(), resultado.getMedicoAsignado().getId())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirStringALocalDateTime(anyString());
        verify(medicoServicio, times(1)).buscarMedico(anyLong());

        log.debug("<--- convertirPostRequestDtoAEntidadTestOK");
    }

    @Test
    public void convertirPostRequestDtoAEntidadTestNull()
    {
        log.debug("---> convertirPostRequestDtoAEntidadTestNull");
        assertNull(this.mapeador.convertirPostRequestDtoAEntidad(null));
        verify(fechaMapeador, times(0)).convertirStringALocalDateTime(anyString());
        verify(medicoServicio, times(0)).buscarMedico(anyLong());
        log.debug("<--- convertirPostRequestDtoAEntidadTestNull");
    }

    @Test
    public void convertirPutRequestDtoAEntidadTestOK()
    {
        log.debug("---> convertirPutRequestDtoAEntidadTestOK");

        //Definiciones de comportamiento:
        when(fechaMapeador.convertirStringALocalDateTime(anyString()))
                .thenReturn(LocalDateTime.of(1995, 11, 5, 12, 15, 20));
        when(medicoServicio.buscarMedico(anyLong())).thenReturn(Optional.of(this.getDummyMedicoAsignado()));

        //Declaraciones de objetos de testing:
        PacientePutRequestDTO entrada = this.getDummyPutRequestDTO();
        Paciente esperado = this.getDummyEntidadSinCitas();
        Paciente resultado = this.mapeador.convertirPutRequestDtoAEntidad(esperado, entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getDireccion(), resultado.getDireccion()),
                () -> assertEquals(esperado.getTelefono(), resultado.getTelefono()),
                () -> assertEquals(esperado.getGenero(), resultado.getGenero()),
                () -> assertEquals(esperado.getGravedad(), resultado.getGravedad()),
                () -> assertEquals(esperado.getMedicoAsignado().getId(), resultado.getMedicoAsignado().getId())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirStringALocalDateTime(anyString());
        verify(medicoServicio, times(1)).buscarMedico(anyLong());

        log.debug("<--- convertirPutRequestDtoAEntidadTestOK");
    }

    @Test
    public void convertirPutRequestDtoAEntidadTestNull()
    {
        log.debug("---> convertirPutRequestDtoAEntidadTestNull");

        //Aseveraciones:
        assertAll(
                () -> assertNull(mapeador.convertirPutRequestDtoAEntidad(this.getDummyEntidadSinCitas(), null)),
                () -> assertNull(mapeador.convertirPutRequestDtoAEntidad(null, this.getDummyPutRequestDTO())),
                () -> assertNull(mapeador.convertirPutRequestDtoAEntidad(null, null))
        );

        //Verificaciones:
        verify(fechaMapeador, times(0)).convertirStringALocalDateTime(anyString());
        verify(medicoServicio, times(0)).buscarMedico(anyLong());

        log.debug("<--- convertirPutRequestDtoAEntidadTestNull");
    }

    @Test
    public void convertirEntidadAResponseDtoSinCitaAsignadaTestOK()
    {
        log.debug("---> convertirEntidadAResponseDtoSinCitaAsignadaTestOK");

        //Declaraciones de comportamiento:
        when(fechaMapeador.convertirLocalDateTimeAString(any())).thenReturn("DateTime prueba");

        //Definición de los objetos de testing:
        Paciente entrada = this.getDummyEntidadSinCitas();
        PacienteResponseDTO esperado = this.getDummyResponseDTO();
        PacienteResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getId(), resultado.getId()),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getDireccion(), resultado.getDireccion()),
                () -> assertEquals(esperado.getTelefono(), resultado.getTelefono()),
                () -> assertEquals(esperado.getGenero(), resultado.getGenero()),
                () -> assertEquals(esperado.getGravedad(), resultado.getGravedad()),
                () -> assertEquals(esperado.getMedicoAsignado(), resultado.getMedicoAsignado()),
                () -> assertEquals(esperado.getAreaTratamiento(), resultado.getAreaTratamiento()),
                () -> assertEquals(0, resultado.getCitasRegistradas()),
                () -> assertEquals(esperado.getCitasRegistradas(), resultado.getCitasRegistradas())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirLocalDateTimeAString(any());

        log.debug("<--- convertirEntidadAResponseDtoSinCitaAsignadaTestOK");
    }

    @Test
    public void convertirEntidadAResponseDtoConCitaAsignadaTestOK()
    {
        log.debug("---> convertirEntidadAResponseDtoConCitaAsignadaTestOK");
        //Definiciones de comportamiento:
        when(fechaMapeador.convertirLocalDateTimeAString(any())).thenReturn("DateTime prueba");

        //Declaraciones de objetos de testing:
        Paciente entrada = this.getDummyEntidadConCitaAsignada();
        PacienteResponseDTO esperado = this.getDummyResponseDTO();
        PacienteResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getId(), resultado.getId()),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getDireccion(), resultado.getDireccion()),
                () -> assertEquals(esperado.getTelefono(), resultado.getTelefono()),
                () -> assertEquals(esperado.getGenero(), resultado.getGenero()),
                () -> assertEquals(esperado.getGravedad(), resultado.getGravedad()),
                () -> assertEquals(esperado.getMedicoAsignado(), resultado.getMedicoAsignado()),
                () -> assertEquals(esperado.getAreaTratamiento(), resultado.getAreaTratamiento()),
                () -> assertEquals(1, resultado.getCitasRegistradas())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirLocalDateTimeAString(any());

        log.debug("<--- convertirEntidadAResponseDtoConCitaAsignadaTestOK");
    }

    @Test
    public void convertirEntidadAResponseDtoTestNull()
    {
        log.debug("---> convertirEntidadAResponseDtoTestNull");
        assertNull(this.mapeador.convertirEntidadAResponseDto(null));
        verify(fechaMapeador, times(0)).convertirLocalDateTimeAString(any());
        log.debug("<--- convertirEntidadAResponseDtoTestNull");
    }

    @Test
    public void convertirGeneroTest()
    {
        log.debug("---> convertirGeneroTest");
        assertAll(
                () -> assertEquals(MASCULINO, this.mapeador.convertirGenero("MASCULINO")),
                () -> assertEquals(FEMENINO, this.mapeador.convertirGenero("FEMENINO")),
                () -> assertEquals(NO_ESPECIFICADO, this.mapeador.convertirGenero("NO_ESPECIFICADO")),
                () -> assertNull(this.mapeador.convertirGenero("Cualquier otro String")),
                () -> assertNull(this.mapeador.convertirGenero(null))
        );
        log.debug("<--- convertirGeneroTest");
    }

    @Test
    public void convertirGravedadTest()
    {
        log.debug("---> convertirGravedadTest");
        assertAll(
                () -> assertEquals(ASINTOMATICA, this.mapeador.convertirGravedad("ASINTOMATICA")),
                () -> assertEquals(LEVE, this.mapeador.convertirGravedad("LEVE")),
                () -> assertEquals(MODERADA, this.mapeador.convertirGravedad("MODERADA")),
                () -> assertEquals(GRAVE, this.mapeador.convertirGravedad("GRAVE")),
                () -> assertEquals(CRITICA, this.mapeador.convertirGravedad("CRITICA")),
                () -> assertNull(this.mapeador.convertirGravedad("Cualquier otro String")),
                () -> assertNull(this.mapeador.convertirGravedad(null))
        );
        log.debug("<--- convertirGravedadTest");
    }
}