package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.repositorios.CitaRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.ICitaServicio;
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
 * gestión lógica de los métodos relacionados con entidades de clase 'Cita'.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@Slf4j
class CitaServicioImplTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private ICitaServicio servicio;

    @MockBean
    private CitaRepositorio repositorio;



                                            // ***********************
                                            // ***  OBJETOS DUMMY  ***
                                            // ***********************

    private Cita getDummyEntidad()
    {
        return Cita.builder()
                .id(1L)
                .paciente(Paciente.builder()
                        .nombre("NombrePaciente")
                        .apellidos("Apellidos Paciente")
                        .medicoAsignado(Medico.builder()
                                .nombre("NombreMedico")
                                .apellidos("Apellidos Medico")
                                .build())
                        .build())
                .sala(Sala.builder()
                        .numero(101)
                        .build())
                .build();
    }


                                        // ****************************
                                        // ***  MÉTODOS de TESTING  ***
                                        // ****************************

    @Test
    public void buscarCitaTestOK()
    {
        log.debug("---> buscarCitaTestOK");
        //Definición de comportamiento:
        Long citaId = 1L;
        Optional<Cita> esperado = Optional.of(this.getDummyEntidad());
        when(repositorio.findById(citaId)).thenReturn(esperado);
        Optional<Cita> resultado = this.servicio.buscarCita(citaId);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isPresent()),
                () -> assertEquals(esperado.get().getId(), resultado.get().getId()),
                () -> assertEquals(esperado.get().getPaciente().getNombre(), resultado.get().getPaciente().getNombre()),
                () -> assertEquals(esperado.get().getPaciente().getApellidos(), resultado.get().getPaciente().getApellidos()),
                () -> assertEquals(esperado.get().getPaciente().getMedicoAsignado().getNombre(),
                        resultado.get().getPaciente().getMedicoAsignado().getNombre()),
                () -> assertEquals(esperado.get().getSala().getNumero(), resultado.get().getSala().getNumero())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findById(citaId);
        log.debug("<--- buscarCitaTestOK");
    }

    @Test
    public void buscarCitaTestKO()
    {
        log.debug("");
        Long idInexistente = 999L;
        when(repositorio.findById(idInexistente)).thenReturn(Optional.empty());
        Optional<Cita> resultado = this.servicio.buscarCita(idInexistente);

        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isPresent())
        );

        verify(repositorio, times(1)).findById(idInexistente);
        log.debug("");
    }

    @Test
    public void leerListaCitasTestOK()
    {
        log.debug("");
        Cita esperado = this.getDummyEntidad();
        when(repositorio.findAll()).thenReturn(List.of(esperado));
        List<Cita> resultado = this.servicio.leerListaCitas();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isEmpty()),
                () -> assertEquals(esperado.getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.getPaciente().getNombre(), resultado.get(0).getPaciente().getNombre()),
                () -> assertEquals(esperado.getPaciente().getApellidos(), resultado.get(0).getPaciente().getApellidos()),
                () -> assertEquals(esperado.getPaciente().getMedicoAsignado().getNombre(),
                        resultado.get(0).getPaciente().getMedicoAsignado().getNombre()),
                () -> assertEquals(esperado.getSala().getNumero(), resultado.get(0).getSala().getNumero())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("");
    }

    @Test
    public void leerListaCitasTestKO()
    {
        log.debug("---> leerListaCitasTestKO");
        //Definición de comportamiento:
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        List<Cita> resultado = this.servicio.leerListaCitas();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isEmpty())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaCitasTestKO");
    }

    @Test
    public void guardarCitaTestOK()
    {
        log.debug("---> guardarCitaTestOK");
        Cita esperada = this.getDummyEntidad();
        when(repositorio.save(esperada)).thenReturn(esperada);
        this.servicio.guardarCita(esperada);
        verify(repositorio, times(1)).save(any(Cita.class));
        log.debug("<--- guardarCitaTestOK");
    }

    @Test
    public void eliminarCitaTestOK()
    {
        log.debug("---> eliminarCitaTestOK");
        Long citaId = 1L;
        doNothing().when(repositorio).deleteById(citaId);
        this.servicio.eliminarCita(citaId);
        verify(repositorio, times(1)).deleteById(citaId);
        log.debug("<--- eliminarCitaTestOK");
    }

    @Test
    public void eliminarTodasCitasTestOK()
    {
        log.debug("---> eliminarTodasCitasTestOK");
        doNothing().when(repositorio).deleteAll();
        this.servicio.eliminarTodasCitas();
        verify(repositorio, times(1)).deleteAll();
        log.debug("<--- eliminarTodasCitasTestOK");
    }
}