package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import dev.acobano.springrestful.hospital.repositorios.MedicoRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.IMedicoServicio;
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
 * gestión lógica de los métodos relacionados con entidades de clase 'Médico'.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@Slf4j
class MedicoServicioImplTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private IMedicoServicio servicio;

    @MockBean
    private MedicoRepositorio repositorio;



                                            // ***********************
                                            // ***  OBJETOS DUMMY  ***
                                            // ***********************

    private Medico getDummyEntidad()
    {
        return Medico.builder()
                .id(7L)
                .nombre("Medico dummy")
                .apellidos("Entidad")
                .especialidad(Especialidad.PEDIATRIA)
                .build();
    }

    private List<Medico> getDummyListaEntidades()
    {
        return List.of(Medico.builder()
                        .id(7L)
                        .nombre("Medico dummy")
                        .apellidos("Lista entidades")
                        .especialidad(Especialidad.PEDIATRIA)
                        .build(),
                Medico.builder()
                        .id(8L)
                        .nombre("Medico dummy")
                        .especialidad(Especialidad.PSIQUIATRIA)
                        .build(),
                Medico.builder()
                        .id(9L)
                        .nombre("Dummy medico")
                        .especialidad(Especialidad.PEDIATRIA)
                        .build());
    }


                                    // ****************************
                                    // ***  MÉTODOS de TESTING  ***
                                    // ****************************

    @Test
    public void leerMedicoTestOK()
    {
        log.debug("---> leerMedicoTestOK");
        //Definición de comportamiento:
        Long medicoId = 7L;
        Optional<Medico> esperado = Optional.of(this.getDummyEntidad());
        when(repositorio.findById(medicoId)).thenReturn(esperado);
        Optional<Medico> resultado = this.servicio.buscarMedico(medicoId);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isPresent()),
                () -> assertEquals(esperado.get().getId(), resultado.get().getId()),
                () -> assertEquals(esperado.get().getNombre(), resultado.get().getNombre()),
                () -> assertEquals(esperado.get().getApellidos(), resultado.get().getApellidos())
        );

        //Verifiaciones:
        verify(repositorio, times(1)).findById(medicoId);
        log.debug("<--- leerMedicoTestOK");
    }

    @Test
    public void leerMedicoTestKO()
    {
        log.debug("---> leerMedicoTestKO");
        //Definición de comportamiento:
        Long idInexistente = 999L;
        when(repositorio.findById(idInexistente)).thenReturn(Optional.empty());
        Optional<Medico> resultado = this.repositorio.findById(idInexistente);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isPresent())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findById(idInexistente);
        log.debug("<--- leerMedicoTestKO");
    }

    @Test
    public void leerListaMedicosTestOK()
    {
        log.debug("---> leerListaMedicosTestOK");
        //Declaraciones de objetos dummy:
        List<Medico> esperado = this.getDummyListaEntidades();

        //Definición del comportamiento:
        when(repositorio.findAll()).thenReturn(esperado);

        //Ejecución del método:
        List<Medico> resultado = this.servicio.leerListaMedicos();

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(3, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getApellidos(), resultado.get(0).getApellidos()),
                () -> assertEquals(esperado.get(0).getEspecialidad(), resultado.get(0).getEspecialidad()),
                () -> assertEquals(esperado.get(1).getId(), resultado.get(1).getId()),
                () -> assertEquals(esperado.get(1).getNombre(), resultado.get(1).getNombre()),
                () -> assertEquals(esperado.get(1).getApellidos(), resultado.get(1).getApellidos()),
                () -> assertEquals(esperado.get(1).getEspecialidad(), resultado.get(1).getEspecialidad()),
                () -> assertEquals(esperado.get(2).getId(), resultado.get(2).getId()),
                () -> assertEquals(esperado.get(2).getNombre(), resultado.get(2).getNombre()),
                () -> assertEquals(esperado.get(2).getApellidos(), resultado.get(2).getApellidos()),
                () -> assertEquals(esperado.get(2).getEspecialidad(), resultado.get(2).getEspecialidad())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaMedicosTestOK");
    }

    @Test
    public void leerListaMedicosTestKO()
    {
        log.debug("---> leerListaMedicosTestKO");
        when(repositorio.findAll()).thenReturn(Collections.emptyList());
        List<Medico> resultado = this.servicio.leerListaMedicos();

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertTrue(resultado.isEmpty())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerListaMedicosTestKO");
    }

    @Test
    public void leerMedicosPorNombreTestOK()
    {
        log.debug("---> leerMedicosPorNombreTestOK");
        String entrada = "Medico dummy";
        List<Medico> esperado = List.of(Medico.builder()
                        .id(7L)
                        .nombre("Medico dummy")
                        .especialidad(Especialidad.PEDIATRIA)
                        .build(),
                Medico.builder()
                        .id(8L)
                        .nombre("Medico dummy")
                        .especialidad(Especialidad.PSIQUIATRIA)
                        .build());

        when(repositorio.findAll()).thenReturn(this.getDummyListaEntidades());
        List<Medico> resultado = this.servicio.filtrarMedicosPorNombre(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isEmpty()),
                () -> assertEquals(2, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getEspecialidad(), resultado.get(0).getEspecialidad()),
                () -> assertEquals(esperado.get(1).getId(), resultado.get(1).getId()),
                () -> assertEquals(esperado.get(1).getNombre(), resultado.get(1).getNombre()),
                () -> assertEquals(esperado.get(1).getEspecialidad(), resultado.get(1).getEspecialidad())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerMedicosPorNombreTestOK");
    }

    @Test
    public void leerMedicosPorEspecialidadTestOK()
    {
        log.debug("---> leerMedicosPorEspecialidadTestOK");
        String entrada = "PEDIATRIA";
        List<Medico> esperado = List.of(Medico.builder()
                        .id(7L)
                        .nombre("Medico dummy")
                        .apellidos("Lista entidades")
                        .especialidad(Especialidad.PEDIATRIA)
                        .build(),
                Medico.builder()
                        .id(9L)
                        .nombre("Dummy medico")
                        .especialidad(Especialidad.PEDIATRIA)
                        .build());

        when(repositorio.findAll()).thenReturn(this.getDummyListaEntidades());
        List<Medico> resultado = this.servicio.filtrarMedicosPorEspecialidad(entrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isEmpty()),
                () -> assertEquals(2, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getEspecialidad(), resultado.get(0).getEspecialidad()),
                () -> assertEquals(esperado.get(1).getId(), resultado.get(1).getId()),
                () -> assertEquals(esperado.get(1).getNombre(), resultado.get(1).getNombre()),
                () -> assertEquals(esperado.get(1).getEspecialidad(), resultado.get(1).getEspecialidad())
        );

        //Verificaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerMedicosPorEspecialidadTestOK");
    }

    @Test
    public void leerMedicosPorNombreYEspecialidadTestOK()
    {
        log.debug("---> leerMedicosPorNombreYEspecialidadTestOK");
        //Definición de comportamiento:
        String nombreEntrada = "Medico dummy";
        String especialidadEntrada = "PEDIATRIA";
        List<Medico> esperado = List.of(this.getDummyEntidad());
        when(repositorio.findAll()).thenReturn(this.getDummyListaEntidades());
        List<Medico> resultado = this.servicio.filtrarMedicosPorNombreYEspecialidad(nombreEntrada, especialidadEntrada);

        //Aseveraciones:
        assertAll(
                () -> assertNotNull(resultado),
                () -> assertFalse(resultado.isEmpty()),
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals(esperado.size(), resultado.size()),
                () -> assertEquals(esperado.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(esperado.get(0).getNombre(), resultado.get(0).getNombre()),
                () -> assertEquals(esperado.get(0).getEspecialidad(), resultado.get(0).getEspecialidad())
        );

        //Verifiaciones:
        verify(repositorio, times(1)).findAll();
        log.debug("<--- leerMedicosPorNombreYEspecialidadTestOK");
    }

    @Test
    public void guardarMedicoTestOK()
    {
        log.debug("---> guardarMedicoTestOK");
        Medico esperado = this.getDummyEntidad();
        when(repositorio.save(esperado)).thenReturn(esperado);
        servicio.guardarMedico(esperado);
        verify(repositorio, times(1)).save(esperado);
        log.debug("<--- guardarMedicoTestOK");
    }

    @Test
    public void eliminarMedicoTestOK()
    {
        log.debug("---> eliminarMedicoTestOK");
        Long medicoId = 3L;
        doNothing().when(repositorio).deleteById(medicoId);
        servicio.eliminarMedico(medicoId);
        verify(repositorio, times(1)).deleteById(medicoId);
        log.debug("<--- eliminarMedicoTestOK");
    }

    @Test
    public void eliminarTodosMedicosTestOK()
    {
        log.debug("---> eliminarTodosMedicosTestOK");
        doNothing().when(repositorio).deleteAll();
        servicio.eliminarTodosMedicos();
        verify(repositorio, times(1)).deleteAll();
        log.debug("<--- eliminarTodosMedicosTestOK");
    }
}