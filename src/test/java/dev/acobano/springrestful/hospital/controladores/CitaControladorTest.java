package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.CitaPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.CitaPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import dev.acobano.springrestful.hospital.servicios.interfaces.ICitaServicio;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de controlador relacionados con la
 * gestión de los endpoints para llamadas HTTP relacionadas con entidades de clase 'Cita'.
 * <>
 * @author Álvaro Cobano
 */
@WebMvcTest(CitaControlador.class)
@Slf4j
class CitaControladorTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICitaServicio servicio;

    @MockBean
    private ICitaMapeador mapeador;



                                            // ***********************
                                            // ***  OBJETOS DUMMY  ***
                                            // ***********************

    private Cita getDummyEntidad()
    {
        return Cita.builder()
                .id(11L)
                .paciente(Paciente.builder()
                        .id(5L)
                        .nombre("NombrePaciente")
                        .apellidos("Apellidos Paciente")
                        .gravedad(Gravedad.LEVE)
                        .medicoAsignado(Medico.builder()
                                .nombre("NombreMedico")
                                .apellidos("Apellidos Medico")
                                .especialidad(Especialidad.TRAUMATOLOGIA)
                                .build())
                        .build())
                .sala(Sala.builder()
                        .id(1L)
                        .numero(101)
                        .build())
                .fechaCita(LocalDate.MAX)
                .horaEntrada(LocalTime.NOON)
                .horaSalida(LocalTime.NOON)
                .build();
    }

    private String getDummyRequestJsonContent()
    {
        return "{\n" +
                "    \"pacienteId\": 1,\n" +
                "    \"salaId\": 1,\n" +
                "    \"fechaCita\": \"01/12/2020\",\n" +
                "    \"horaEntrada\": \"16:15:00\",\n" +
                "    \"horaSalida\": \"16:45:00\"\n" +
                "}";
    }

    private CitaResponseDTO getDummyResponseDTO()
    {
        return CitaResponseDTO.builder()
                .id(11L)
                .paciente("NombrePaciente Apellidos Paciente")
                .medico("NombreMedico Apellidos Medico")
                .gravedad("LEVE")
                .especialidad("TRAUMATOLOGIA")
                .fechaCita("Fecha dummy")
                .horaEntrada("Hora dummy")
                .horaSalida("Hora dummy")
                .build();
    }


                                        // ***************************
                                        // ***  CLASES DE TESTING  ***
                                        // ***************************

    @Test
    public void obtenerCitaTestOK() throws Exception
    {
        log.debug("---> obtenerCitaTestOK");
        Long citaId = 11L;
        CitaResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(servicio.buscarCita(citaId)).thenReturn(Optional.of(this.getDummyEntidad()));
        when(mapeador.convertirEntidadAResponseDto(any(Cita.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/citas/{id}", citaId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(citaId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente").value(esperado.getPaciente()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medico").value(esperado.getMedico()));

        //Verificaciones:
        verify(servicio, times(1)).buscarCita(citaId);
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- obtenerCitaTestOK");
    }

    @Test
    public void obtenerCitaNoContentKO() throws Exception
    {
        log.debug("---> obtenerCitaNoContentKO");
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarCita(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/citas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarCita(idInexistente);
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- obtenerCitaNoContentKO");
    }

    @Test
    public void listarCitasTestOK() throws Exception
    {
        log.debug("---> listarCitasTestOK");
        List<Cita> listaEntidades = List.of(this.getDummyEntidad());
        CitaResponseDTO esperado = this.getDummyResponseDTO();

        //Definición del comportamiento:
        when(servicio.leerListaCitas()).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Cita.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/citas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medico").value(esperado.getMedico()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paciente").value(esperado.getPaciente()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numSala").value(esperado.getNumSala()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].especialidad").value(esperado.getEspecialidad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gravedad").value(esperado.getGravedad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fechaCita").value(esperado.getFechaCita()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].horaEntrada").value(esperado.getHoraEntrada()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].horaSalida").value(esperado.getHoraSalida()));

        //Verificaciones:
        verify(servicio, times(1)).leerListaCitas();
        verify(mapeador, times(listaEntidades.size())).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- listarCitasTestOK");
    }

    @Test
    public void listarCitasNoContentKO() throws Exception
    {
        log.debug("---> listarCitasNoContentKO");

        //Definición de comportamiento:
        when(servicio.leerListaCitas()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/citas"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaCitas();
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- listarCitasNoContentKO");
    }

    @Test
    public void guardarCitaTestOK() throws Exception
    {
        log.debug("---> guardarCitaTestOK");
        BindingResult bindingResult = mock(BindingResult.class);
        CitaResponseDTO esperado = this.getDummyResponseDTO();

        //Definición del comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(mapeador.convertirPostResquestDtoAEntidad(any(CitaPostRequestDTO.class))).thenReturn(this.getDummyEntidad());
        doNothing().when(servicio).guardarCita(any(Cita.class));
        when(mapeador.convertirEntidadAResponseDto(any(Cita.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/citas")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medico").value(esperado.getMedico()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente").value(esperado.getPaciente()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSala").value(esperado.getNumSala()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.especialidad").value(esperado.getEspecialidad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gravedad").value(esperado.getGravedad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaCita").value(esperado.getFechaCita()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horaEntrada").value(esperado.getHoraEntrada()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horaSalida").value(esperado.getHoraSalida()));

        //Verificaciones:
        verify(mapeador, times(1)).convertirPostResquestDtoAEntidad(any(CitaPostRequestDTO.class));
        verify(servicio, times(1)).guardarCita(any(Cita.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- guardarCitaTestOK");
    }

    @Test
    public void guardarCitaBadRequestKO() throws Exception
    {
        log.debug("---> guardarCitaBadRequestKO");
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/citas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(mapeador, times(0)).convertirPostResquestDtoAEntidad(any(CitaPostRequestDTO.class));
        verify(servicio, times(0)).guardarCita(any(Cita.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- guardarCitaBadRequestKO");
    }

    @Test
    public void actualizarCitaTestOK() throws Exception
    {
        log.debug("---> actualizarCitaTestOK");
        Long citaId = 11L;
        BindingResult bindingResult = mock(BindingResult.class);
        CitaResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarCita(citaId)).thenReturn(Optional.of(this.getDummyEntidad()));
        when(mapeador.convertirPutRequestDtoAEntidad(any(Cita.class), any(CitaPutRequestDTO.class)))
                .thenReturn(this.getDummyEntidad());
        doNothing().when(servicio).guardarCita(any(Cita.class));
        when(mapeador.convertirEntidadAResponseDto(any(Cita.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/citas/{id}", citaId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medico").value(esperado.getMedico()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paciente").value(esperado.getPaciente()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numSala").value(esperado.getNumSala()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.especialidad").value(esperado.getEspecialidad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gravedad").value(esperado.getGravedad()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaCita").value(esperado.getFechaCita()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horaEntrada").value(esperado.getHoraEntrada()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.horaSalida").value(esperado.getHoraSalida()));

        //Verificaciones:
        verify(servicio, times(1)).buscarCita(citaId);
        verify(mapeador, times(1)).convertirPutRequestDtoAEntidad(any(Cita.class), any(CitaPutRequestDTO.class));
        verify(servicio, times(1)).guardarCita(any(Cita.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- actualizarCitaTestOK");
    }

    @Test
    public void actualizarCitaNoContentKO() throws Exception
    {
        log.debug("---> actualizarCitaNoContentKO");
        Long idInexistente = 999L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarCita(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/citas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.getDummyRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarCita(idInexistente);
        verify(mapeador, times(0)).convertirPutRequestDtoAEntidad(any(Cita.class), any(CitaPutRequestDTO.class));
        verify(servicio, times(0)).guardarCita(any(Cita.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- actualizarCitaNoContentKO");
    }

    @Test
    public void actualizarCitaBadRequestKO() throws Exception
    {
        log.debug("---> actualizarCitaBadRequestKO");
        Long idInencontrable = 666L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/citas/{id}", idInencontrable)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).buscarCita(idInencontrable);
        verify(mapeador, times(0)).convertirPutRequestDtoAEntidad(any(Cita.class), any(CitaPutRequestDTO.class));
        verify(servicio, times(0)).guardarCita(any(Cita.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- actualizarCitaBadRequestKO");
    }

    @Test
    public void eliminarCitaTestOK() throws Exception
    {
        log.debug("---> eliminarCitaTestOK");
        Long citaId = 11L;

        //Definiciones de comportamiento:
        when(servicio.buscarCita(citaId)).thenReturn(Optional.of(this.getDummyEntidad()));
        doNothing().when(servicio).eliminarCita(citaId);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/citas/{id}", citaId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).buscarCita(citaId);
        verify(servicio, times(1)).eliminarCita(citaId);
        log.debug("<--- eliminarCitaTestOK");
    }

    @Test
    public void eliminarCitaNoContentKO() throws Exception
    {
        log.debug("---> eliminarCitaNoContentKO");
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarCita(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/citas/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarCita(idInexistente);
        verify(servicio, times(0)).eliminarCita(idInexistente);
        log.debug("<--- eliminarCitaNoContentKO");
    }

    @Test
    public void eliminarListaCitasTestOK() throws Exception
    {
        log.debug("---> eliminarListaCitasTestOK");

        //Definiciones de comportamiento:
        when(servicio.leerListaCitas()).thenReturn(List.of(this.getDummyEntidad()));
        doNothing().when(servicio).eliminarTodasCitas();

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/citas"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).leerListaCitas();
        verify(servicio, times(1)).eliminarTodasCitas();
        log.debug("<--- eliminarListaCitasTestOK");
    }

    @Test
    public void eliminarListaCitasNoContentKO() throws Exception
    {
        log.debug("---> eliminarListaCitasNoContentKO");

        //Definición de comportamiento:
        when(servicio.leerListaCitas()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/citas"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaCitas();
        verify(servicio, times(0)).eliminarTodasCitas();
        log.debug("<--- eliminarListaCitasNoContentKO");
    }
}