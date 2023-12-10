package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.PacientePostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.PacientePutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IPacienteMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import dev.acobano.springrestful.hospital.servicios.interfaces.IPacienteServicio;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Clase de testing para los métodos de la capa de controlador relacionados con la
 * gestión de los endpoints para llamadas HTTP relacionadas con entidades de clase 'Paciente'.
 * <>
 * @author Álvaro Cobano
 */
@WebMvcTest(PacienteControlador.class)
@Slf4j
class PacienteControladorTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPacienteServicio servicio;

    @MockBean
    private IPacienteMapeador mapeador;

    @MockBean
    private ICitaMapeador citaMapeador;



                                            // ***********************
                                            // ***  OBJETOS DUMMY  ***
                                            // ***********************

    private Paciente getDummyEntidad()
    {
        return Paciente.builder()
                .id(5L)
                .nombre("NombrePaciente")
                .apellidos("Apellidos paciente")
                .dni("98765432X")
                .genero(Genero.MASCULINO)
                .email("emailpaciente@test.com")
                .telefono("654777404")
                .direccion("Avda de la direccion de prueba, 133")
                .gravedad(Gravedad.MODERADA)
                .fechaNacimiento(LocalDateTime.MAX)
                .fechaIngreso(LocalDateTime.MAX)
                .medicoAsignado(this.getDummyMedicoAsignado())
                .citasAsignadas(List.of(this.getDummyCitaAsignada()))
                .build();
    }

    public Cita getDummyCitaAsignada()
    {
        return Cita.builder()
                .id(9L)
                .build();
    }

    private CitaResponseDTO getDummyCitaAsignadaDTO()
    {
        return CitaResponseDTO.builder()
                .id(9L)
                .paciente("NombrePaciente ApellidosPaciente")
                .build();
    }

    private Medico getDummyMedicoAsignado()
    {
        return Medico.builder().id(1L).build();
    }

    private PacientePostRequestDTO getDummyPostRequestDTO()
    {
        return PacientePostRequestDTO.builder()
                .nombre("NombrePaciente")
                .apellidos("ApellidosPaciente")
                .dni("98765432X")
                .build();
    }

    private String getDummyPostRequestJsonContent()
    {
        return "{\n" +
                "    \"nombre\": \"NombrePaciente\",\n" +
                "    \"apellidos\": \"Apellidos paciente\",\n" +
                "    \"dni\": \"98765432X\",\n" +
                "    \"email\": \"emailpaciente@test.com\",\n" +
                "    \"telefono\": \"654777404\",\n" +
                "    \"genero\": \"MASCULINO\",\n" +
                "    \"direccion\": \"Avda de la direccion de prueba, 133\",\n" +
                "    \"gravedad\": \"MODERADA\",\n" +
                "    \"fechaNacimiento\": \"DateTime prueba\",\n" +
                "    \"fechaIngreso\": \"DateTime prueba\",\n" +
                "    \"medicoId\": 1\n" +
                "}";
    }

    private String getDummyPutRequestJsonContent()
    {
        return "{\n" +
                "    \"nombre\": \"NombrePaciente\",\n" +
                "    \"apellidos\": \"Post request DTO\"\n" +
                "}";
    }

    private PacienteResponseDTO getDummyResponseDTO()
    {
        return PacienteResponseDTO.builder()
                .id(5L)
                .nombre("NombrePaciente")
                .apellidos("Apellidos paciente")
                .dni("98765432X")
                .genero("MASCULINO")
                .email("emailpaciente@test.com")
                .telefono("654777404")
                .direccion("Avda de la direccion de prueba, 133")
                .gravedad("MODERADA")
                .fechaNacimiento("DateTime prueba")
                .fechaIngreso("DateTime prueba")
                .build();
    }



                                            // ***************************
                                            // ***  CLASES de TESTING  ***
                                            // ***************************

    @Test
    public void obtenerPacienteTestOK() throws Exception
    {
        log.debug("---> obtenerPacienteTestOK");
        Long pacienteId = 5L;
        PacienteResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(servicio.buscarPaciente(pacienteId)).thenReturn(Optional.of(this.getDummyEntidad()));
        when(mapeador.convertirEntidadAResponseDto(any(Paciente.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes/{id}", pacienteId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(esperado.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos").value(esperado.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni").value(esperado.getDni()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value(esperado.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(esperado.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefono").value(esperado.getTelefono()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion").value(esperado.getDireccion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gravedad").value(esperado.getGravedad()));

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(pacienteId);
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Paciente.class));
        log.debug("<--- obtenerPacienteTestOK");
    }

    @Test
    public void obtenerPacienteNoContentKO() throws Exception
    {
        log.debug("---> obtenerPacienteNoContentKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarPaciente(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(idInexistente);
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Paciente.class));
        log.debug("<--- obtenerPacienteNoContentKO");
    }

    @Test
    public void listarPacientesTestOK() throws Exception
    {
        log.debug("---> listarPacientesTestOK");
        //Declaración de objetos de testing:
        List<Paciente> listaEntidades = List.of(this.getDummyEntidad());
        PacienteResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(servicio.leerListaPacientes()).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Paciente.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(esperado.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellidos").value(esperado.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dni").value(esperado.getDni()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genero").value(esperado.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(esperado.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].telefono").value(esperado.getTelefono()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].direccion").value(esperado.getDireccion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gravedad").value(esperado.getGravedad()));

        //Verificaciones:
        verify(servicio, times(1)).leerListaPacientes();
        verify(servicio, times(0)).filtrarPacientesPorGravedad(anyString());
        verify(mapeador, times(listaEntidades.size())).convertirEntidadAResponseDto(any(Paciente.class));
        log.debug("<--- listarPacientesTestOK");
    }

    @Test
    public void listarPacientesNoContextKO() throws Exception
    {
        log.debug("---> listarPacientesNoContextKO");
        //Definición de comportamiento:
        when(servicio.leerListaPacientes()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaPacientes();
        verify(servicio, times(0)).filtrarPacientesPorGravedad(anyString());
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Paciente.class));
        log.debug("<--- listarPacientesNoContextKO");
    }

    @Test
    public void listarPacientesPorGravedadTestOK() throws Exception
    {
        log.debug("---> listarPacientesPorGravedadTestOK");
        //Declaración de objetos de testing:
        String gravedad = "Gravedad prueba";
        PacienteResponseDTO esperado = this.getDummyResponseDTO();
        List<Paciente> listaEntidades = List.of(this.getDummyEntidad());

        //Definición de comportamiento:
        when(servicio.filtrarPacientesPorGravedad(gravedad)).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Paciente.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("gravedad", gravedad))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(esperado.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellidos").value(esperado.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dni").value(esperado.getDni()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genero").value(esperado.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(esperado.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].telefono").value(esperado.getTelefono()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].direccion").value(esperado.getDireccion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gravedad").value(esperado.getGravedad()));;

        //Verificaciones
        verify(servicio, times(0)).leerListaPacientes();
        verify(servicio, times(1)).filtrarPacientesPorGravedad(anyString());
        log.debug("<--- listarPacientesPorGravedadTestOK");
    }

    @Test
    public void obtenerCitasPorPacienteTestOK() throws Exception
    {
        log.debug("---> obtenerCitasPorPacienteTestOK");
        //Declaraciones de objetos de testing:
        Long pacienteId = 5L;
        CitaResponseDTO esperado = this.getDummyCitaAsignadaDTO();

        //Definición de comportamiento:
        when(servicio.buscarPaciente(pacienteId)).thenReturn(Optional.of(this.getDummyEntidad()));
        when(citaMapeador.convertirEntidadAResponseDto(any(Cita.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes/{id}/citas", pacienteId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].paciente").value(esperado.getPaciente()));

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(pacienteId);
        verify(citaMapeador, times(1)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- obtenerCitasPorPacienteTestOK");
    }

    @Test
    public void obtenerCitasPorPacienteNoContentKO() throws Exception
    {
        log.debug("---> obtenerCitasPorPacienteNoContentKO");
        //Declaración de objetos de testing;
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarPaciente(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/pacientes/{id}/citas", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(idInexistente);
        verify(citaMapeador, times(0)).convertirEntidadAResponseDto(any(Cita.class));
        log.debug("<--- obtenerCitasPorPacienteNoContentKO");
    }

    @Test
    public void guardarPacienteTestOK() throws Exception
    {
        log.debug("---> guardarPacienteTestOK");
        //Declaración de objetos de testing:
        BindingResult bindingResult = mock(BindingResult.class);
        PacienteResponseDTO respuesta = this.getDummyResponseDTO();

        //Definición de comportamiento:
        doNothing().when(servicio).guardarPaciente(any(Paciente.class));
        when(mapeador.convertirPostRequestDtoAEntidad(any())).thenReturn(this.getDummyEntidad());
        when(mapeador.convertirEntidadAResponseDto(any())).thenReturn(respuesta);
        when(bindingResult.hasErrors()).thenReturn(false);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyPostRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(respuesta.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(respuesta.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos").value(respuesta.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni").value(respuesta.getDni()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value(respuesta.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(respuesta.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefono").value(respuesta.getTelefono()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion").value(respuesta.getDireccion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gravedad").value(respuesta.getGravedad()));

        //Verificaciones:
        verify(servicio, times(1)).guardarPaciente(any(Paciente.class));
        verify(mapeador, times(1)).convertirPostRequestDtoAEntidad(any(PacientePostRequestDTO.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Paciente.class));

        log.debug("<--- guardarPacienteTestOK");
    }

    @Test
    public void guardarPacienteBadRequestKO() throws Exception
    {
        log.debug("---> guardarPacienteBadRequestKO");

        //Definición de comportamiento:
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).guardarPaciente(any(Paciente.class));
        verify(mapeador, times(0)).convertirPostRequestDtoAEntidad(any(PacientePostRequestDTO.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Paciente.class));
        log.debug("<--- guardarPacienteBadRequestKO");
    }

    @Test
    public void actualizarPacienteTestOK() throws Exception
    {
        log.debug("---> actualizarPacienteTestOK");
        //Declaración de objetos de testing:
        Long pacienteId = 5L;
        BindingResult bindingResult = mock(BindingResult.class);
        Paciente dummy = this.getDummyEntidad();
        PacienteResponseDTO respuesta = this.getDummyResponseDTO();
        respuesta.setApellidos("Put request DTO");

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarPaciente(pacienteId)).thenReturn(Optional.of(dummy));
        when(mapeador.convertirPutRequestDtoAEntidad(any(Paciente.class), any(PacientePutRequestDTO.class))).thenReturn(dummy);
        when(mapeador.convertirEntidadAResponseDto(any(Paciente.class))).thenReturn(respuesta);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/pacientes/{id}", pacienteId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyPutRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(pacienteId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(respuesta.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos").value(respuesta.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dni").value(respuesta.getDni()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value(respuesta.getGenero()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(respuesta.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefono").value(respuesta.getTelefono()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion").value(respuesta.getDireccion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gravedad").value(respuesta.getGravedad()));

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(pacienteId);
        verify(mapeador, times(1)).convertirPutRequestDtoAEntidad(any(Paciente.class), any(PacientePutRequestDTO.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Paciente.class));

        log.debug("<--- actualizarPacienteTestOK");
    }

    @Test
    public void actualizarPacienteNoContentKO() throws Exception
    {
        log.debug("---> actualizarPacienteNoContentKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarPaciente(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/pacientes/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.getDummyPutRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(idInexistente);
        verify(mapeador, times(0)).convertirPutRequestDtoAEntidad(any(Paciente.class), any(PacientePutRequestDTO.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Paciente.class));
        log.debug("<--- actualizarPacienteNoContentKO");
    }

    @Test
    public void actualizarPacienteBadRequestKO() throws Exception
    {
        log.debug("---> actualizarPacienteBadRequestKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/pacientes/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).buscarPaciente(idInexistente);
        verify(mapeador, times(0)).convertirPutRequestDtoAEntidad(any(Paciente.class), any(PacientePutRequestDTO.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Paciente.class));

        log.debug("<--- actualizarPacienteBadRequestKO");
    }

    @Test
    public void eliminarPacienteTestOK() throws Exception
    {
        log.debug("---> eliminarPacienteTestOK");
        //Declaración de objetos de testing:
        Long pacienteId = 5L;

        //Definición de comportamiento:
        when(servicio.buscarPaciente(pacienteId)).thenReturn(Optional.of(this.getDummyEntidad()));
        doNothing().when(servicio).eliminarPaciente(pacienteId);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/pacientes/{id}", pacienteId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(pacienteId);
        verify(servicio, times(1)).eliminarPaciente(pacienteId);
        log.debug("<--- eliminarPacienteTestOK");
    }

    @Test
    public void eliminarPacienteNoContentKO() throws Exception
    {
        log.debug("---> eliminarPacienteNoContentKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarPaciente(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/pacientes/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarPaciente(idInexistente);
        verify(servicio, times(0)).eliminarPaciente(anyLong());
        log.debug("<--- eliminarPacienteNoContentKO");
    }

    @Test
    public void eliminarListaPacientesTestOK() throws Exception
    {
        log.debug("---> eliminarListaPacientesTestOK");
        //Definición de comportamiento:
        when(servicio.leerListaPacientes()).thenReturn(List.of(this.getDummyEntidad()));
        doNothing().when(servicio).eliminarTodosPacientes();

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).leerListaPacientes();
        verify(servicio, times(1)).eliminarTodosPacientes();

        log.debug("<--- eliminarListaPacientesTestOK");
    }

    @Test
    public void eliminarListaPacientesNoContentKO() throws Exception
    {
        log.debug("---> eliminarListaPacientesNoContentKO");
        //Definición de comportamiento:
        when(servicio.leerListaPacientes()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/pacientes"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaPacientes();
        verify(servicio, times(0)).eliminarTodosPacientes();

        log.debug("<--- eliminarListaPacientesNoContentKO");
    }
}