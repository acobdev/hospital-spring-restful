package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import dev.acobano.springrestful.hospital.repositorios.PacienteRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.IPacienteServicio;
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
 * gestión lógica de los métodos relacionados con entidades de clase 'Paciente'.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@Slf4j
class PacienteServicioImplTest
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private IPacienteServicio servicio;

    @MockBean
    private PacienteRepositorio repositorio;



                                        // ***********************
                                        // ***  OBJETOS DUMMY  ***
                                        // ***********************


    private Paciente getDummyEntidad()
    {
        return Paciente.builder()
                .id(3L)
                .nombre("NombrePaciente")
                .apellidos("Apellidos Paciente")
                .genero(Genero.NO_ESPECIFICADO)
                .dni("98765432X")
                .email("paciente@test.com")
                .direccion("Avda prueba, 123")
                .telefono("666777888")
                .gravedad(Gravedad.ASINTOMATICA)
                .build();
    }

    private List<Paciente> getDummyListaEntidades()
    {
        return List.of(Paciente.builder()
                        .id(3L)
                        .nombre("NombrePaciente")
                        .apellidos("Apellidos Paciente")
                        .genero(Genero.NO_ESPECIFICADO)
                        .dni("98765432X")
                        .email("paciente@test.com")
                        .direccion("Avda prueba, 123")
                        .telefono("666777888")
                        .gravedad(Gravedad.ASINTOMATICA)
                        .build(),
                Paciente.builder()
                        .id(4L)
                        .nombre("NombrePaciente2")
                        .apellidos("Apellidos ListaPaciente")
                        .genero(Genero.NO_ESPECIFICADO)
                        .dni("65784392T")
                        .email("paciente@test.net")
                        .direccion("Avda prueba, 321")
                        .telefono("666555444")
                        .gravedad(Gravedad.GRAVE)
                        .build());
    }


                                    // ****************************
                                    // ***  MÉTODOS de TESTING  ***
                                    // ****************************

    @Test
    public void buscarPacienteTestOK()
    {
        log.debug("---> buscarPacienteTestOK");
        Long pacienteId = 3L;
        Optional<Paciente> esperado = Optional.of(this.getDummyEntidad());
        when(repositorio.findById(pacienteId)).thenReturn(esperado);
        Optional<Paciente> resultado = this.servicio.buscarPaciente(pacienteId);

        // Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isPresent()),
                () -> assertEquals(esperado.get().getId(), resultado.get().getId()),
                () -> assertEquals(esperado.get().getNombre(), resultado.get().getNombre()),
                () -> assertEquals(esperado.get().getApellidos(), resultado.get().getApellidos()),
                () -> assertEquals(esperado.get().getDni(), resultado.get().getDni()),
                () -> assertEquals(esperado.get().getGenero(), resultado.get().getGenero()),
                () -> assertEquals(esperado.get().getEmail(), resultado.get().getEmail()),
                () -> assertEquals(esperado.get().getDireccion(), resultado.get().getDireccion()),
                () -> assertEquals(esperado.get().getTelefono(), resultado.get().getTelefono()),
                () -> assertEquals(esperado.get().getGravedad(), resultado.get().getGravedad())
        );

        // Verificaciones:
        verify(repositorio, times(1)).findById(pacienteId);
        log.debug("<--- buscarPacienteTestOK");
    }

    @Test
    public void buscarPacienteTestKO()
    {
        log.debug("---> buscarPacienteTestKO");
        Long idInexistente = 999L;
        when(repositorio.findById(idInexistente)).thenReturn(Optional.empty());
        Optional<Paciente> resultado = this.servicio.buscarPaciente(idInexistente);

        // Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isPresent())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findById(idInexistente);
        log.debug("<--- leerPacienteTestKO");
    }

    @Test
    public void leerListaPacientesTestOK()
    {
        log.debug("---> leerListaPacientesTestOK");
        //Declaración del objeto de testing:
        List<Paciente> esperado = this.getDummyListaEntidades();

        //Definición de comportamiento:
        when(repositorio.findAll()).thenReturn(esperado);

        //Ejecución del método:
        List<Paciente> resultado = this.servicio.leerListaPacientes();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(2, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getApellidos(), resultado.get(0).getApellidos()),
                () -> assertEquals(esperado.get(0).getDni(), resultado.get(0).getDni()),
                () -> assertEquals(esperado.get(0).getGenero(), resultado.get(0).getGenero()),
                () -> assertEquals(esperado.get(0).getEmail(), resultado.get(0).getEmail()),
                () -> assertEquals(esperado.get(0).getDireccion(), resultado.get(0).getDireccion()),
                () -> assertEquals(esperado.get(0).getTelefono(), resultado.get(0).getTelefono()),
                () -> assertEquals(esperado.get(0).getGravedad(), resultado.get(0).getGravedad()),
                () -> assertEquals(esperado.get(1).getId(), resultado.get(1).getId()),
                () -> assertEquals(esperado.get(1).getNombre(), resultado.get(1).getNombre()),
                () -> assertEquals(esperado.get(1).getApellidos(), resultado.get(1).getApellidos()),
                () -> assertEquals(esperado.get(1).getDni(), resultado.get(1).getDni()),
                () -> assertEquals(esperado.get(1).getGenero(), resultado.get(1).getGenero()),
                () -> assertEquals(esperado.get(1).getEmail(), resultado.get(1).getEmail()),
                () -> assertEquals(esperado.get(1).getDireccion(), resultado.get(1).getDireccion()),
                () -> assertEquals(esperado.get(1).getTelefono(), resultado.get(1).getTelefono()),
                () -> assertEquals(esperado.get(1).getGravedad(), resultado.get(1).getGravedad())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaPacientesTestOK");
    }

    @Test
    public void leerListaPacientesTestKO()
    {
        log.debug("---> leerListaPacientesTestKO");
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        List<Paciente> resultado = this.servicio.leerListaPacientes();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isEmpty())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaPacientesTestKO");
    }

    @Test
    public void filtrarPacientesPorGravedadTestOK()
    {
        log.debug("---> filtrarPacientesPorGravedadTestOK");
        String entrada = "ASINTOMATICA";
        List<Paciente> esperado = List.of(this.getDummyEntidad());
        when(repositorio.findAll()).thenReturn(this.getDummyListaEntidades());
        List<Paciente> resultado = this.servicio.filtrarPacientesPorGravedad(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isEmpty()),
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getApellidos(), resultado.get(0).getApellidos()),
                () -> assertEquals(esperado.get(0).getDni(), resultado.get(0).getDni()),
                () -> assertEquals(esperado.get(0).getGenero(), resultado.get(0).getGenero()),
                () -> assertEquals(esperado.get(0).getEmail(), resultado.get(0).getEmail()),
                () -> assertEquals(esperado.get(0).getDireccion(), resultado.get(0).getDireccion()),
                () -> assertEquals(esperado.get(0).getTelefono(), resultado.get(0).getTelefono()),
                () -> assertEquals(esperado.get(0).getGravedad(), resultado.get(0).getGravedad())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- filtrarPacientesPorGravedadTestOK");
    }

    @Test
    public void filtrarPacientesPorGravedadTestKO()
    {
        log.debug("---> filtrarPacientesPorGravedadTestKO");
        String entrada = "String test";
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        List<Paciente> resultado = this.servicio.filtrarPacientesPorGravedad(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isEmpty())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- filtrarPacientesPorGravedadTestKO");
    }

    @Test
    public void guardarPacienteTestOK()
    {
        log.debug("---> guardarPacienteTestOK");
        Paciente esperado = this.getDummyEntidad();
        when(repositorio.save(esperado)).thenReturn(esperado);
        this.servicio.guardarPaciente(esperado);
        verify(repositorio, times(1)).save(any(Paciente.class));
        log.debug("<--- guardarPacienteTestOK");
    }

    @Test
    public void eliminarPacienteTestOK()
    {
        log.debug("---> eliminarPacienteTestOK");
        Long pacienteId = 5L;
        doNothing().when(repositorio).deleteById(pacienteId);
        this.servicio.eliminarPaciente(pacienteId);
        verify(repositorio, times(1)).deleteById(pacienteId);
        log.debug("<--- eliminarPacienteTestOK");
    }

    @Test
    public void eliminarTodosPacientesTestOK()
    {
        log.debug("---> eliminarTodosPacientesTestOK");
        doNothing().when(repositorio).deleteAll();
        this.servicio.eliminarTodosPacientes();
        verify(repositorio, times(1)).deleteAll();
        log.debug("<--- eliminarTodosPacientesTestOK");
    }
}