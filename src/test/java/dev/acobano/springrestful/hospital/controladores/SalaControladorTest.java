package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.SalaRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.SalaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ISalaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.servicios.interfaces.ISalaServicio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de controlador relacionados con la
 * gestión de los endpoints para llamadas HTTP relacionadas con entidades de clase 'Sala'.
 * <>
 * @author Álvaro Cobano
 */
@WebMvcTest(SalaControlador.class)
@Slf4j
class SalaControladorTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISalaServicio servicio;

    @MockBean
    private ISalaMapeador mapeador;

    @MockBean
    private ICitaMapeador citaMapeador;



                                            // ***********************
                                            // ***  OBJETOS DUMMY  ***
                                            // ***********************

    private Sala getDummyEntidadSinCitas()
    {
        return Sala.builder()
                .id(1L)
                .numero(404)
                .citasAsignadas(Collections.emptyList())
                .build();
    }

    private Sala getDummyEntidadConCitaAsociada()
    {
        return Sala.builder()
                .id(2L)
                .numero(200)
                .citasAsignadas(List.of(Cita.builder()
                        .id(8L)
                        .build()))
                .build();
    }

    private String getDummyRequestBodyJsonContent()
    {
        return "{\n" +
                "    \"numSala\": 404\n" +
                "}";
    }

    private SalaResponseDTO getDummyResponseDTO()
    {
        return SalaResponseDTO.builder()
                .id(1L)
                .numSala(404)
                .citasAsignadas(0)
                .build();
    }



                                            // ***************************
                                            // ***  CLASES de TESTING  ***
                                            // ***************************

    @Test
    public void obtenerSalaTestOK() throws Exception
    {
        log.debug("---> obtenerSalaTestOK");
        //Declaración de objetos de testing:
        Long salaId = 1L;

        //Definición de comportamiento:
        when(servicio.buscarSala(salaId)).thenReturn(Optional.of(this.getDummyEntidadSinCitas()));
        when(mapeador.convertirEntidadAResponseDto(any(Sala.class))).thenReturn(this.getDummyResponseDTO());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/salas/{id}", salaId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(salaId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSala").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.citasAsignadas").value(0));

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(salaId);
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- obtenerSalaTestOK");
    }

    @Test
    public void obtenerSalaNoContentKO() throws Exception
    {
        log.debug("---> obtenerSalaNoContentKO");
        //Declaraciones de objetos de testing:
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarSala(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/salas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(idInexistente);
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- obtenerSalaNoContentKO");
    }

    @Test
    public void listarSalasTestOK() throws Exception
    {
        log.debug("---> listarSalasTestOK");
        //Declaración de objetos de testing:
        SalaResponseDTO esperado = this.getDummyResponseDTO();
        List<Sala> listaSalas = List.of(this.getDummyEntidadSinCitas());

        //Definición de comportamiento:
        when(servicio.leerListaSalas()).thenReturn(listaSalas);
        when(mapeador.convertirEntidadAResponseDto(any(Sala.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/salas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numSala").value(esperado.getNumSala()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].citasAsignadas").value(esperado.getCitasAsignadas()));

        //Verificaciones:
        verify(servicio, times(1)).leerListaSalas();
        verify(mapeador, times(listaSalas.size())).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- listarSalasTestOK");
    }

    @Test
    public void listarSalasNoContentKO() throws Exception
    {
        log.debug("---> listarSalasNoContentKO");
        //Definición de comportamiento:
        when(servicio.leerListaSalas()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/salas"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaSalas();
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- listarSalasNoContentKO");
    }

    @Test
    public void obtenerCitasPorSalaTestOK() throws Exception
    {
        log.debug("---> obtenerCitasPorSalaTestOK");
        //Declaraciones de objetos de testing:
        Long salaId = 2L;
        Sala entidad = this.getDummyEntidadConCitaAsociada();
        CitaResponseDTO esperado = CitaResponseDTO.builder()
                .id(8L)
                .build();

        //Definición de comportamiento:
        when(servicio.buscarSala(salaId)).thenReturn(Optional.of(entidad));
        when(citaMapeador.convertirEntidadAResponseDto(any(Cita.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/salas/{id}/citas", salaId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()));

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(salaId);
        verify(citaMapeador, times(entidad.getCitasAsignadas().size())).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- obtenerCitasPorSalaTestOK");
    }

    @Test
    public void obtenerCitasPorSalaNoContentKO() throws Exception
    {
        log.debug("---> obtenerCitasPorSalaNoContentKO");
        //Declaración de objetos de testing:
        Long salaId = 1L;

        //Definición de comportamiento:
        when(servicio.buscarSala(salaId)).thenReturn(Optional.of(this.getDummyEntidadSinCitas()));

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/salas/{id}/citas", salaId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(salaId);
        verify(citaMapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- obtenerCitasPorSalaNoContentKO");
    }

    @Test
    public void guardarSalaTestOK() throws Exception
    {
        log.debug("---> guardarSalaTestOK");
        //Declaraciones de objetos de testing:
        BindingResult bindingResult = mock(BindingResult.class);
        Sala dummy = this.getDummyEntidadSinCitas();
        SalaResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        doNothing().when(servicio).guardarSala(dummy);
        when(mapeador.convertirRequestDtoAEntidad(any(SalaRequestDTO.class))).thenReturn(dummy);
        when(mapeador.convertirEntidadAResponseDto(any(Sala.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/salas")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyRequestBodyJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSala").value(esperado.getNumSala()));

        //Verificaciones:
        verify(servicio, times(1)).guardarSala(any(Sala.class));
        verify(mapeador, times(1)).convertirRequestDtoAEntidad(any(SalaRequestDTO.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- guardarSalaTestOK");
    }

    @Test
    public void guardarSalaBadRequestKO() throws Exception
    {
        log.debug("---> guardarSalaBadRequestKO");
        //Definición de comportamiento:
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/salas")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).guardarSala(any(Sala.class));
        verify(mapeador, times(0)).convertirRequestDtoAEntidad(any(SalaRequestDTO.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- guardarSalaBadRequestKO");
    }

    @Test
    public void actualizarSalaTestOK() throws Exception
    {
        log.debug("---> actualizarSalaTestOK");
        //Declaración de objetos de testing:
        Long salaId = 1L;
        BindingResult bindingResult = mock(BindingResult.class);
        Sala dummy = this.getDummyEntidadSinCitas();
        SalaResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarSala(salaId)).thenReturn(Optional.of(dummy));
        doNothing().when(servicio).guardarSala(dummy);
        when(mapeador.convertirEntidadAResponseDto(dummy)).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/salas/{id}", salaId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyRequestBodyJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(salaId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSala").value(esperado.getNumSala()));

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(salaId);
        verify(servicio, times(1)).guardarSala(any(Sala.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- actualizarSalaTestOK");
    }

    @Test
    public void actualizarSalaNoContentKO() throws Exception
    {
        log.debug("---> actualizarSalaNoContentKO");
        //Declaraciones de objetos de testing:
        Long idInexistente = 999L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarSala(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/salas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyRequestBodyJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(idInexistente);
        verify(servicio, times(0)).guardarSala(any(Sala.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- actualizarSalaNoContentKO");
    }

    @Test
    public void actualizarSalaBadRequestKO() throws Exception
    {
        log.debug("---> actualizarSalaBadRequestKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/salas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).buscarSala(idInexistente);
        verify(servicio, times(0)).guardarSala(any(Sala.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Sala.class));
        log.debug("<--- actualizarSalaBadRequestKO");
    }

    @Test
    public void eliminarSalaTestOK() throws Exception
    {
        log.debug("---> eliminarSalaTestOK");
        //Declaración de objetos de testing:
        Long salaId = 1L;

        //Definición de comportamiento:
        when(servicio.buscarSala(salaId)).thenReturn(Optional.of(this.getDummyEntidadSinCitas()));
        doNothing().when(servicio).eliminarSala(salaId);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/salas/{id}", salaId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(salaId);
        verify(servicio, times(1)).eliminarSala(salaId);
        log.debug("<--- eliminarSalaTestOK");
    }

    @Test
    public void eliminarSalaNoContentKO() throws Exception
    {
        log.debug("---> eliminarSalaNoContentKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarSala(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/salas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarSala(idInexistente);
        verify(servicio, times(0)).eliminarSala(idInexistente);
        log.debug("<--- eliminarSalaNoContentKO");
    }

    @Test
    public void eliminarListaSalasTestOK() throws Exception
    {
        log.debug("---> eliminarListaSalasTestOK");
        //Definición de comportamiento:
        when(servicio.leerListaSalas()).thenReturn(List.of(this.getDummyEntidadSinCitas()));
        doNothing().when(servicio).eliminarTodasSalas();

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/salas")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).leerListaSalas();
        verify(servicio, times(1)).eliminarTodasSalas();
        log.debug("<--- eliminarListaSalasTestOK");
    }

    @Test
    public void eliminarListaSalasNoContentKO() throws Exception
    {
        log.debug("---> eliminarListaSalasNoContentKO");
        //Definición de comportamiento:
        when(servicio.leerListaSalas()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/salas")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaSalas();
        verify(servicio, times(0)).eliminarTodasSalas();
        log.debug("<--- eliminarListaSalasNoContentKO");
    }
}