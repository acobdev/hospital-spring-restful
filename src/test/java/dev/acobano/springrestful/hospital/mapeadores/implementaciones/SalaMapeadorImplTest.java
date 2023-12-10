package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.dto.entrada.SalaRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.SalaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de testing para los métodos de la capa de mapeadores relacionados con la
 * traducción las entidades de clase 'Sala' y sus respectivos DTO de entrada y salida.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@ContextConfiguration(classes = SalaMapeadorImpl.class)
@Slf4j
class SalaMapeadorImplTest
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private SalaMapeadorImpl mapeador;

    @MockBean
    private IFechaMapeador fechaMapeador;



                                        // ***********************
                                        // ***  OBJETOS DUMMY  ***
                                        // ***********************

    private Sala getDummyEntidadSinCita()
    {
        return Sala.builder()
                .id(1L)
                .numero(101)
                .citasAsignadas(Collections.emptyList())
                .build();
    }

    private Sala getDummyEntidadConCitaAsignada()
    {
        return Sala.builder()
                .id(9L)
                .numero(202)
                .citasAsignadas(List.of(Cita.builder().build()))
                .build();
    }

    private SalaRequestDTO getDummyRequestDTO()
    {
        return SalaRequestDTO.builder()
                .numSala(101)
                .build();
    }

    private SalaResponseDTO getDummyResponseDTOSinCitas()
    {
        return SalaResponseDTO.builder()
                .id(1L)
                .numSala(101)
                .citasAsignadas(0)
                .build();
    }

    private SalaResponseDTO getDummyResponseDTOConCitaAsignada()
    {
        return SalaResponseDTO.builder()
                .id(9L)
                .numSala(202)
                .citasAsignadas(1)
                .build();
    }



                                        // ***************************
                                        // ***  CLASES de TESTING  ***
                                        // ***************************

    @Test
    public void convertirRequestDtoAEntidadTestOK()
    {
        log.debug("---> convertirRequestDtoAEntidadTestOK");
        //Declaraciones de objetos de testing:
        SalaRequestDTO entrada = this.getDummyRequestDTO();
        Sala esperado = this.getDummyEntidadSinCita();
        Sala resultado = this.mapeador.convertirRequestDtoAEntidad(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertNull(resultado.getId()),
                () -> assertEquals(esperado.getNumero(), resultado.getNumero()),
                () -> assertNull(resultado.getCitasAsignadas())
        );

        log.debug("<--- convertirRequestDtoAEntidadTestOK");
    }

    @Test
    public void convertirRequestDtoAEntidadTestNull()
    {
        log.debug("---> convertirRequestDtoAEntidadTestNull");
        assertNull(this.mapeador.convertirRequestDtoAEntidad(null));
        log.debug("<--- convertirRequestDtoAEntidadTestNull");
    }

    @Test
    public void convertirEntidadSinCitasAResponseDtoTestOK()
    {
        log.debug("---> convertirEntidadSinCitasAResponseDtoTestOK");
        //Declaraciones de objetos de testing:
        Sala entrada = this.getDummyEntidadSinCita();
        SalaResponseDTO esperado = this.getDummyResponseDTOSinCitas();
        SalaResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getId(), resultado.getId()),
                () -> assertEquals(esperado.getNumSala(), resultado.getNumSala()),
                () -> assertEquals(esperado.getCitasAsignadas(), resultado.getCitasAsignadas())
        );
        log.debug("<--- convertirEntidadSinCitasAResponseDtoTestOK");
    }

    @Test
    public void convertirEntidadConCitaAsignadaAResponseDtoTestOK()
    {
        log.debug("---> convertirEntidadConCitaAsignadaAResponseDtoTestOK");
        //Declaraciones de objetos de testing:
        Sala entrada = this.getDummyEntidadConCitaAsignada();
        SalaResponseDTO esperado = this.getDummyResponseDTOConCitaAsignada();
        SalaResponseDTO resultado = this.mapeador.convertirEntidadAResponseDto(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getId(), resultado.getId()),
                () -> assertEquals(esperado.getNumSala(), resultado.getNumSala()),
                () -> assertEquals(esperado.getCitasAsignadas(), resultado.getCitasAsignadas())
        );
        log.debug("<--- convertirEntidadConCitaAsignadaAResponseDtoTestOK");
    }

    @Test
    public void convertirEntidadAResponseDtoTestNull()
    {
        log.debug("---> convertirEntidadAResponseDtoTestNull");
        assertNull(this.mapeador.convertirEntidadAResponseDto(null));
        log.debug("<--- convertirEntidadAResponseDtoTestNull");
    }
}