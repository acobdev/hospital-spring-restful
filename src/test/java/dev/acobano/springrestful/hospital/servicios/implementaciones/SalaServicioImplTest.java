package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.repositorios.SalaRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.ISalaServicio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de servicio relacionados con la
 * gestión lógica de los métodos relacionados con entidades de clase 'Sala'.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@Slf4j
class SalaServicioImplTest
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private ISalaServicio servicio;

    @MockBean
    private SalaRepositorio repositorio;



                                        // ***********************
                                        // ***  OBJETOS DUMMY  ***
                                        // ***********************

    private Sala getDummyEntidad()
    {
        return Sala.builder()
                .id(1L)
                .numero(404)
                .citasAsignadas(Collections.emptyList())
                .build();
    }


                                    // ****************************
                                    // ***  MÉTODOS de TESTING  ***
                                    // ****************************

    @Test
    public void buscarSalaTestOK()
    {
        log.debug("---> buscarSalaTestOK");
        Long salaId = 1L;
        Optional<Sala> esperado = Optional.of(this.getDummyEntidad());
        when(repositorio.findById(salaId)).thenReturn(esperado);
        Optional<Sala> resultado = this.servicio.buscarSala(salaId);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isPresent()),
                () -> assertEquals(esperado.get().getId(), resultado.get().getId()),
                () -> assertEquals(esperado.get().getNumero(), resultado.get().getNumero()),
                () -> assertEquals(esperado.get().getCitasAsignadas(), resultado.get().getCitasAsignadas())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findById(salaId);
        log.debug("<--- buscarSalaTestOK");
    }

    @Test
    public void buscarSalaTestKO()
    {
         log.debug("---> buscarSalaTestKO");
         Long idIntexistente = 999L;
         when(repositorio.findById(idIntexistente)).thenReturn(Optional.empty());
         Optional<Sala> resultado = this.servicio.buscarSala(idIntexistente);

         //Aseveraciones:
         assertAll(
                 () -> assertNotNull(resultado),
                 () -> assertTrue(resultado.isEmpty())
         );

         //Verificaciones:
         verify(repositorio, times(1)).findById(idIntexistente);
         log.debug("<--- buscarSalaTestKO");
    }

    @Test
    public void leerListaSalasTestOK()
    {
        log.debug("---> leerListaSalasTestOK");
        List<Sala> esperado = List.of(this.getDummyEntidad());
        when(repositorio.findAll()).thenReturn(esperado);
        List<Sala> resultado = this.servicio.leerListaSalas();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNumero(), resultado.get(0).getNumero()),
                () -> assertEquals(esperado.get(0).getCitasAsignadas(), resultado.get(0).getCitasAsignadas())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaSalasTestOK");
    }

    @Test
    public void leerListaSalasTestKO()
    {
        log.debug("---> leerListaSalasTestKO");
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        List<Sala> resultado = this.servicio.leerListaSalas();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isEmpty())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaSalasTestKO");
    }

    @Test
    public void guardarSalaTestOK()
    {
        log.debug("---> guardarSalaTestOK");
        Sala esperado = this.getDummyEntidad();
        when(repositorio.save(esperado)).thenReturn(esperado);
        this.servicio.guardarSala(esperado);
        verify(repositorio, times(1)).save(any(Sala.class));
        log.debug("<--- guardarSalaTestOK");
    }

    @Test
    public void eliminarSalaTestOK()
    {
        log.debug("---> eliminarSalaTestOK");
        Long salaId = 1L;
        doNothing().when(repositorio).deleteById(salaId);
        this.servicio.eliminarSala(salaId);
        verify(repositorio, times(1)).deleteById(salaId);
        log.debug("<--- eliminarSalaTestOK");
    }

    @Test
    public void eliminarTodasSalasTestOK()
    {
        log.debug("---> eliminarTodasSalasTestOK");
        doNothing().when(repositorio).deleteAll();
        this.servicio.eliminarTodasSalas();
        verify(repositorio, times(1)).deleteAll();
        log.debug("<--- eliminarTodasSalasTestOK");
    }
}