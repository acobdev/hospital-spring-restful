package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.MedicoPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.MedicoPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.MedicoResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteMedicoDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de mapeadores relacionados con la
 * traducción las entidades de clase 'Médico' y sus respectivos DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@ContextConfiguration(classes = MedicoMapeadorImpl.class)
@Slf4j
class MedicoMapeadorImplTest
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private MedicoMapeadorImpl mapeador;

    @MockBean
    private IFechaMapeador fechaMapeador;



                                        // ***********************
                                        // ***  OBJETOS DUMMY  ***
                                        // ***********************

    private final Especialidad CARDIOLOGIA = Especialidad.CARDIOLOGIA;
    private final Especialidad CIRUGIA = Especialidad.CIRUGIA;
    private final Especialidad DERMATOLOGIA = Especialidad.DERMATOLOGIA;
    private final Especialidad GINECOLOGIA = Especialidad.GINECOLOGIA;
    private final Especialidad OFTALMOLOGIA = Especialidad.OFTALMOLOGIA;
    private final Especialidad ONCOLOGIA = Especialidad.ONCOLOGIA;
    private final Especialidad PEDIATRIA = Especialidad.PEDIATRIA;
    private final Especialidad PSIQUIATRIA = Especialidad.PSIQUIATRIA;
    private final Especialidad TRAUMATOLOGIA = Especialidad.TRAUMATOLOGIA;

    private Medico getDummyEntidad()
    {
        Paciente asignado = Paciente.builder()
                .id(33L)
                .nombre("Paciente asignado")
                .apellidos("Test médico mapeador")
                .genero(Genero.NO_ESPECIFICADO)
                .gravedad(Gravedad.LEVE)
                .direccion("C/ direccion de prueba, 123")
                .telefono("666777888")
                .email("pacienteprueba@medicomapeador.com")
                .fechaNacimiento(LocalDateTime.MAX)
                .fechaIngreso(LocalDateTime.MAX)
                .build();

        return Medico.builder()
                .id(777L)
                .nombre("Médico dummy")
                .apellidos("Test de prueba unitaria")
                .email("medico@test.com")
                .dni("12345678C")
                .especialidad(CARDIOLOGIA)
                .fechaGraduacion(LocalDate.MIN)
                .fechaIncorporacion(LocalDate.MIN)
                .pacientesAsignados(List.of(asignado))
                .build();
    }

    private MedicoPostRequestDTO getDummyPostRequestDTO()
    {
        return MedicoPostRequestDTO.builder()
                .nombre("Médico dummy")
                .apellidos("Test de prueba unitaria")
                .email("medico@test.com")
                .dni("12345678C")
                .especialidad("CARDIOLOGIA")
                .fechaGraduacion("Date prueba")
                .fechaIncorporacion("Date prueba")
                .build();
    }

    private MedicoPutRequestDTO getDummyPutRequestDTO()
    {
        return MedicoPutRequestDTO.builder()
                .nombre("Médico dummy")
                .apellidos("Test de prueba unitaria")
                .email("medico@test.com")
                .dni("12345678C")
                .especialidad("CARDIOLOGIA")
                .fechaGraduacion("Date prueba")
                .fechaIncorporacion("Date prueba")
                .build();
    }

    private MedicoResponseDTO getDummyResponseDTO()
    {
        return MedicoResponseDTO.builder()
                .id(777L)
                .nombre("Médico dummy")
                .apellidos("Test de prueba unitaria")
                .dni("12345678C")
                .email("medico@test.com")
                .especialidad("CARDIOLOGIA")
                .fechaGraduacion("Date prueba")
                .fechaIncorporacion("Date prueba")
                .pacientesAsignados(1)
                .build();
    }

    private List<PacienteMedicoDTO> getListaPacientesDTO()
    {
        return List.of(PacienteMedicoDTO.builder()
                .id(33L)
                .nombre("Paciente asignado Test médico mapeador")
                .genero("NO_ESPECIFICADO")
                .gravedad("LEVE")
                .direccion("C/ direccion de prueba, 123")
                .email("pacienteprueba@medicomapeador.com")
                .telefono("666777888")
                .fechaNacimiento("DateTime prueba")
                .fechaIngreso("DateTime prueba")
                .build());
    }



                                    // ***************************
                                    // ***  CLASES de TESTING  ***
                                    // ***************************

    @Test
    public void convertirPostRequestDtoAEntidadTestOK()
    {
        log.debug("---> convertirPostRequestDtoAEntidadTestOK");

        //Definición del comportamiento:
        when(fechaMapeador.convertirStringALocalDate(anyString())).thenReturn(LocalDate.MIN);

        //Declaración de los objetos de testing:
        MedicoPostRequestDTO entrada = this.getDummyPostRequestDTO();
        Medico esperado = this.getDummyEntidad();
        Medico resultado = this.mapeador.convertirPostRequestDtoAEntidad(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getApellidos(), resultado.getApellidos()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getEspecialidad(), resultado.getEspecialidad()),
                () -> assertEquals(esperado.getFechaGraduacion(), resultado.getFechaGraduacion()),
                () -> assertEquals(esperado.getFechaIncorporacion(), resultado.getFechaIncorporacion())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirStringALocalDate(anyString());

        log.debug("<--- convertirPostRequestDtoAEntidadOK");
    }

    @Test
    public void convertirPostRequestDtoAEntidadTestNull()
    {
        log.debug("---> convertirPostRequestDtoAEntidadTestNull");
        assertNull(this.mapeador.convertirPostRequestDtoAEntidad(null));
        verify(fechaMapeador, times(0)).convertirStringALocalDate(anyString());
        log.debug("<--- convertirPostRequestDtoAEntidadTestNull");
    }

    @Test
    public void convertirPutRequestDtoAEntidadTestOK()
    {
        log.debug("---> convertirPutRequestDtoAEntidadTestOK");

        //Definiciones de comportamiento:
        when(fechaMapeador.convertirStringALocalDate(anyString())).thenReturn(LocalDate.MIN);

        //Declaraciones de los objetos de testing:
        Medico entidadEntrada = this.getDummyEntidad();
        MedicoPutRequestDTO dtoEntrada = this.getDummyPutRequestDTO();
        Medico esperado = this.getDummyEntidad();
        Medico resultado = this.mapeador.convertirPutRequestDtoAEntidad(entidadEntrada, dtoEntrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getApellidos(), resultado.getApellidos()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getEspecialidad(), resultado.getEspecialidad()),
                () -> assertEquals(esperado.getFechaGraduacion(), resultado.getFechaGraduacion()),
                () -> assertEquals(esperado.getFechaIncorporacion(), resultado.getFechaIncorporacion())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirStringALocalDate(anyString());

        log.debug("<--- convertirPutRequestDtoAEntidadTestOK");
    }

    @Test
    public void convertirPutRequestDtoAEntidadTestNull()
    {
        log.debug("---> convertirPutRequestDtoAEntidadTestNull");
        assertAll(
                () -> assertNull(this.mapeador.convertirPutRequestDtoAEntidad(this.getDummyEntidad(), null)),
                () -> assertNull(this.mapeador.convertirPutRequestDtoAEntidad(null, this.getDummyPutRequestDTO())),
                () -> assertNull(this.mapeador.convertirPutRequestDtoAEntidad(null, null))
        );
        verify(fechaMapeador, times(0)).convertirStringALocalDate(anyString());
        log.debug("<--- convertirPutRequestDtoAEntidadTestNull");
    }

    @Test
    public void convertirEntidadAResponseDtoTestOK()
    {
        log.debug("---> convertirEntidadAResponseDtoTestOK");

        //Definiciones de comportamiento:
        when(fechaMapeador.convertirLocalDateAString(any())).thenReturn("Date prueba");

        //Declaración de los objetos de testing:
        Medico entrada = this.getDummyEntidad();
        MedicoResponseDTO esperado = this.getDummyResponseDTO();
        MedicoResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getNombre(), resultado.getNombre()),
                () -> assertEquals(esperado.getApellidos(), resultado.getApellidos()),
                () -> assertEquals(esperado.getDni(), resultado.getDni()),
                () -> assertEquals(esperado.getEmail(), resultado.getEmail()),
                () -> assertEquals(esperado.getEspecialidad(), resultado.getEspecialidad()),
                () -> assertEquals(esperado.getFechaGraduacion(), resultado.getFechaGraduacion()),
                () -> assertEquals(esperado.getFechaIncorporacion(), resultado.getFechaIncorporacion()),
                () -> assertEquals(esperado.getPacientesAsignados(), resultado.getPacientesAsignados())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirLocalDateAString(any());

        log.debug("<--- convertirEntidadAResponseDtoTestOK");
    }

    @Test
    public void convertirEntidadSinPacientesAsignadosAResponseDtoTestOK()
    {
        log.debug("---> convertirEntidadSinPacientesAsignadosAResponseDtoTestOK");

        //Definiciones de comportamiento:
        when(fechaMapeador.convertirLocalDateAString(any())).thenReturn("Date prueba");

        //Declaración de los objetos de testing:
        Medico entrada = this.getDummyEntidad();
        entrada.setPacientesAsignados(Collections.emptyList());
        MedicoResponseDTO esperado = this.getDummyResponseDTO();
        esperado.setPacientesAsignados(0);
        MedicoResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getPacientesAsignados(), resultado.getPacientesAsignados())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2)).convertirLocalDateAString(any());

        log.debug("<--- convertirEntidadAResponseSinPacientesAsignadosDtoTestOK");
    }

    @Test
    public void convertirEntidadAResponseDtoTestNull()
    {
        log.debug("---> convertirEntidadAResponseDtoTestNull");
        assertNull(this.mapeador.convertirEntidadAResponseDto(null));
        verify(fechaMapeador, times(0)).convertirLocalDateAString(any());
        log.debug("<--- convertirEntidadAResponseDtoTestNull");
    }

    @Test
    public void convertirPacientesAsignadosADtoTestOK()
    {
        log.debug("---> convertirPacientesAsignadosADtoTestOK");

        //Definiciones de comportamiento:
        when(fechaMapeador.convertirLocalDateTimeAString(any())).thenReturn("DateTime prueba");

        //Declaración de los objetos de testing:
        Medico entrada = this.getDummyEntidad();
        List<PacienteMedicoDTO> esperado = this.getListaPacientesDTO();
        List<PacienteMedicoDTO> resultado = this.mapeador.convertirPacientesAsignadosADto(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isEmpty()),
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getGenero(), resultado.get(0).getGenero()),
                () -> assertEquals(esperado.get(0).getDireccion(), resultado.get(0).getDireccion()),
                () -> assertEquals(esperado.get(0).getTelefono(), resultado.get(0).getTelefono()),
                () -> assertEquals(esperado.get(0).getEmail(), resultado.get(0).getEmail()),
                () -> assertEquals(esperado.get(0).getGravedad(), resultado.get(0).getGravedad()),
                () -> assertEquals(esperado.get(0).getFechaNacimiento(), resultado.get(0).getFechaNacimiento()),
                () -> assertEquals(esperado.get(0).getFechaIngreso(), resultado.get(0).getFechaIngreso())
        );

        //Verificaciones:
        verify(fechaMapeador, times(2 * resultado.size())).convertirLocalDateTimeAString(any());
        log.debug("<--- convertirPacientesAsignadosADtoTestOK");
    }

    @Test
    public void convertirPacientesAsignadosADtoTestNull()
    {
        log.debug("---> convertirPacientesAsignadosADtoTestNull");
        List<PacienteMedicoDTO> resultado = this.mapeador.convertirPacientesAsignadosADto(null);
        assertNull(resultado);
        verify(fechaMapeador, times(0)).convertirLocalDateTimeAString(any());
        log.debug("<--- convertirPacientesAsignadosADtoTestNull");
    }

    @Test
    public void convertirEspecialidadTest()
    {
        log.debug("---> convertirEspecialidadTest");
        assertAll (
                () -> assertEquals(CIRUGIA, this.mapeador.convertirEspecialidad("CIRUGIA")),
                () -> assertEquals(DERMATOLOGIA, this.mapeador.convertirEspecialidad("DERMATOLOGIA")),
                () -> assertEquals(GINECOLOGIA, this.mapeador.convertirEspecialidad("GINECOLOGIA")),
                () -> assertEquals(OFTALMOLOGIA, this.mapeador.convertirEspecialidad("OFTALMOLOGIA")),
                () -> assertEquals(ONCOLOGIA, this.mapeador.convertirEspecialidad("ONCOLOGIA")),
                () -> assertEquals(PEDIATRIA, this.mapeador.convertirEspecialidad("PEDIATRIA")),
                () -> assertEquals(PSIQUIATRIA, this.mapeador.convertirEspecialidad("PSIQUIATRIA")),
                () -> assertEquals(TRAUMATOLOGIA, this.mapeador.convertirEspecialidad("TRAUMATOLOGIA")),
                () -> assertNull(this.mapeador.convertirEspecialidad("Cualquier otra String")),
                () -> assertNull(this.mapeador.convertirEspecialidad(null))
        );
        log.debug("<--- convertirEspecialidadTest");
    }
}
