package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.MedicoPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.MedicoResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteMedicoDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IMedicoMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import dev.acobano.springrestful.hospital.servicios.interfaces.IMedicoServicio;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

/**
 * Clase de testing para los métodos de la capa de controlador relacionados con la
 * gestión de los endpoints para llamadas HTTP relacionadas con entidades de clase 'Médico'.
 * <>
 * @author Álvaro Cobano
 */
@WebMvcTest(MedicoControlador.class)
@Slf4j
public class MedicoControladorTest
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private IMedicoServicio servicio;

    @MockBean
    private IMedicoMapeador mapeador;



                                        // ***********************
                                        // ***  OBJETOS DUMMY  ***
                                        // ***********************

    private String getDummyPostRequestJsonContent()
    {
        return "{\n" +
            "    \"nombre\": \"MedicoDummy\",\n" +
            "    \"apellidos\": \"PostRequest DTO\",\n" +
            "    \"dni\": \"12345678C\",\n" +
            "    \"email\": \"medicoprueba@test.com\",\n" +
            "    \"especialidad\": \"CIRUGIA\",\n" +
            "    \"fechaGraduacion\": \"01/10/1983\",\n" +
            "    \"fechaIncorporacion\": \"05/09/1992\"\n" +
            "}";
    }

    private String getDummyPutRequestJsonContent()
    {
        return "{\n" +
                "    \"nombre\": \"MedicoDummy\",\n" +
                "    \"apellidos\": \"PutRequest DTO\"" +
                "}";
    }

    private MedicoResponseDTO getDummyResponseDTO()
    {
        return MedicoResponseDTO.builder()
                .id(3L)
                .nombre("Medico dummy")
                .apellidos("ResponseDTO")
                .build();
    }

    private Medico getDummyEntidad()
    {
        return Medico.builder()
                .id(7L)
                .nombre("Medico dummy")
                .apellidos("Entidad")
                .especialidad(Especialidad.OFTALMOLOGIA)
                .build();
    }

    private List<PacienteMedicoDTO> getDummyPacienteMedicoDTO()
    {
        return List.of(PacienteMedicoDTO.builder()
                .id(8L)
                .nombre("Paciente Dummy")
                .email("paciente@prueba.com").build());
    }



                                    // ***************************
                                    // ***  CLASES DE TESTING  ***
                                    // ***************************
    @Test
    public void obtenerMedicoOK() throws Exception
    {
        log.debug("---> obtenerMedicoOK");
        Long medicoId = 3L;

        //Definición de comportamiento:
        when(servicio.buscarMedico(medicoId)).thenReturn(Optional.of(this.getDummyEntidad()));
        when(this.mapeador.convertirEntidadAResponseDto(any(Medico.class))).thenReturn(this.getDummyResponseDTO());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos/{id}", medicoId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos").value("ResponseDTO"));

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(medicoId);
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- obtenerMedicoOK");
    }

    @Test
    public void obtenerMedicoNoContentKO() throws Exception
    {
        log.debug("---> obtenerMedicoNoContentKO");
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarMedico(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(idInexistente);
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- obtenerMedicoNoContentKO");
    }

    @Test
    public void listarMedicosOK() throws Exception
    {
        log.debug("---> listarMedicosOK");
        //Declaración de objetos de testing:
        MedicoResponseDTO esperado = this.getDummyResponseDTO();
        List<Medico> listaEntidades = List.of(this.getDummyEntidad());

        //Definición de comportamiento:
        when(servicio.leerListaMedicos()).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Medico.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(esperado.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellidos").value(esperado.getApellidos()));

        //Verificaciones:
        verify(servicio, times(1)).leerListaMedicos();
        verify(servicio, times(0)).filtrarMedicosPorNombre(anyString());
        verify(servicio, times(0)).filtrarMedicosPorEspecialidad(anyString());
        verify(servicio, times(0)).filtrarMedicosPorNombreYEspecialidad(anyString(), anyString());
        verify(mapeador, times(listaEntidades.size())).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- listarMedicosOK");
    }

    @Test
    public void listarMedicosNoContentKO() throws Exception
    {
        log.debug("---> listarMedicosNoContentKO");
        //Definición de comportamiento:
        when(servicio.leerListaMedicos()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaMedicos();
        verify(servicio, times(0)).filtrarMedicosPorNombre(anyString());
        verify(servicio, times(0)).filtrarMedicosPorEspecialidad(anyString());
        verify(servicio, times(0)).filtrarMedicosPorNombreYEspecialidad(anyString(), anyString());
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- listarMedicosNoContentKO");
    }

    @Test
    public void listarMedicosPorNombreOK() throws Exception
    {
        log.debug("---> listarMedicosPorNombreOK");
        //Declaraciones de objetos de testing:
        String nombre = "Nombre prueba";
        MedicoResponseDTO respuesta = this.getDummyResponseDTO();
        respuesta.setNombre(nombre);
        List<Medico> listaEntidades = List.of(this.getDummyEntidad());

        //Definición de comportamiento:
        when(servicio.filtrarMedicosPorNombre(nombre)).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Medico.class))).thenReturn(respuesta);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .param("nombre", nombre))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(respuesta.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(respuesta.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellidos").value(respuesta.getApellidos()));

        //Verificaciones:
        verify(servicio, times(0)).leerListaMedicos();
        verify(servicio, times(1)).filtrarMedicosPorNombre(nombre);
        verify(servicio, times(0)).filtrarMedicosPorEspecialidad(anyString());
        verify(servicio, times(0)).filtrarMedicosPorNombreYEspecialidad(anyString(), anyString());
        verify(mapeador, times(listaEntidades.size())).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- listarMedicosPorNombreOK");
    }

    @Test
    public void listarMedicosPorEspecialidadOK() throws Exception
    {
        log.debug("---> listarMedicosPorEspecialidadOK");
        //Declaraciones de objetos de testing:
        String especialidad = "Especialidad prueba";
        MedicoResponseDTO respuesta = this.getDummyResponseDTO();
        respuesta.setEspecialidad(especialidad);
        List<Medico> listaEntidades = List.of(this.getDummyEntidad());

        //Definición de comportamiento:
        when(servicio.filtrarMedicosPorEspecialidad(especialidad)).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Medico.class))).thenReturn(respuesta);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("especialidad", especialidad))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(respuesta.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(respuesta.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellidos").value(respuesta.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].especialidad").value(respuesta.getEspecialidad()));

        //Verificaciones:
        verify(servicio, times(0)).leerListaMedicos();
        verify(servicio, times(0)).filtrarMedicosPorNombre(anyString());
        verify(servicio, times(1)).filtrarMedicosPorEspecialidad(especialidad);
        verify(servicio, times(0)).filtrarMedicosPorNombreYEspecialidad(anyString(), anyString());
        verify(mapeador, times(listaEntidades.size())).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- listarMedicosPorEspecialidadOK");
    }

    @Test
    public void listarMedicosPorNombreYEspecialidadOK() throws Exception
    {
        log.debug("---> listarMedicosPorNombreYEspecialidadOK");
        //Declaración de objetos de testing:
        String nombre = "Nombre prueba";
        String especialidad = "Especialidad prueba";
        MedicoResponseDTO respuesta = this.getDummyResponseDTO();
        respuesta.setNombre(nombre);
        respuesta.setEspecialidad(especialidad);
        List<Medico> listaEntidades = List.of(this.getDummyEntidad());

        //Definición de comportamiento:
        when(servicio.filtrarMedicosPorNombreYEspecialidad(nombre, especialidad)).thenReturn(listaEntidades);
        when(mapeador.convertirEntidadAResponseDto(any(Medico.class))).thenReturn(respuesta);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("nombre", nombre)
                        .param("especialidad", especialidad))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(respuesta.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(respuesta.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].apellidos").value(respuesta.getApellidos()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].especialidad").value(respuesta.getEspecialidad()));

        //Verificaciones:
        verify(servicio, times(0)).leerListaMedicos();
        verify(servicio, times(0)).filtrarMedicosPorNombre(anyString());
        verify(servicio, times(0)).filtrarMedicosPorEspecialidad(anyString());
        verify(servicio, times(1)).filtrarMedicosPorNombreYEspecialidad(nombre, especialidad);
        verify(mapeador, times(listaEntidades.size())).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- listarMedicosPorNombreYEspecialidadOK");
    }

    @Test
    public void obtenerPacientesPorMedicoOK() throws Exception
    {
        log.debug("---> obtenerPacientesPorMedicoOK");
        //Declaración de objetos de testing:
        Long medicoId = 7L;
        Medico dummy = this.getDummyEntidad();
        List<PacienteMedicoDTO> respuesta = this.getDummyPacienteMedicoDTO();

        //Definición de comportamiento:
        when(servicio.buscarMedico(medicoId)).thenReturn(Optional.of(dummy));
        when(mapeador.convertirPacientesAsignadosADto(any(Medico.class))).thenReturn(respuesta);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos/{id}/pacientes", medicoId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(respuesta.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(respuesta.get(0).getNombre()));

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(medicoId);
        verify(mapeador, times(1)).convertirPacientesAsignadosADto(any(Medico.class));
        log.debug("<--- obtenerPacientesPorMedicoOK");
    }

    @Test
    public void obtenerPacientesPorMedicoNoContentKO() throws Exception
    {
        log.debug("---> obtenerPacientesPorMedicoNoContentKO");
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarMedico(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hospital/api/medicos/{id}/pacientes", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(idInexistente);
        verify(mapeador, times(0)).convertirPacientesAsignadosADto(any(Medico.class));
        log.debug("<--- obtenerPacientesPorMedicoNoContentKO");
    }

    @Test
    public void guardarMedicoTestOK() throws Exception
    {
        log.debug("---> guardarMedicoTestOK");
        //Declaración de objetos de testing:
        MedicoResponseDTO esperado = this.getDummyResponseDTO();
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        doNothing().when(servicio).guardarMedico(this.getDummyEntidad());
        when(mapeador.convertirEntidadAResponseDto(any())).thenReturn(esperado);
        when(bindingResult.hasErrors()).thenReturn(false);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/medicos")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(this.getDummyPostRequestJsonContent()))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3L))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(esperado.getNombre()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos").value(esperado.getApellidos()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.dni").value(esperado.getDni()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(esperado.getEmail()));

        //Verificaciones:
        verify(servicio, times(1)).guardarMedico(any());
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any());
        log.debug("<--- guardarMedicoTestOK");
    }

    @Test
    public void guardarMedicoTestBadRequestKO() throws Exception
    {
        log.debug("---> guardarMedicoTestBadRequestKO");
        //Declaración de objetos de testing:
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/hospital/api/medicos")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).guardarMedico(any(Medico.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- guardarMedicoTestBadRequestKO");
    }

    @Test
    public void actualizarMedicoOK() throws Exception
    {
        log.debug("---> actualizarMedicoOK");
        //Declaración de objetos de testing:
        Long medicoId = 3L;
        BindingResult bindingResult = mock(BindingResult.class);
        MedicoResponseDTO esperado = this.getDummyResponseDTO();

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarMedico(medicoId)).thenReturn(Optional.of(this.getDummyEntidad()));
        when(mapeador.convertirPutRequestDtoAEntidad(any(Medico.class), any(MedicoPutRequestDTO.class)))
                .thenReturn(this.getDummyEntidad());
        when(mapeador.convertirEntidadAResponseDto(any(Medico.class))).thenReturn(esperado);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/medicos/{id}", medicoId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.getDummyPutRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(esperado.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(esperado.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellidos").value(esperado.getApellidos()));

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(medicoId);
        verify(mapeador, times(1)).convertirPutRequestDtoAEntidad(any(Medico.class), any(MedicoPutRequestDTO.class));
        verify(mapeador, times(1)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- actualizarMedicoOK");
    }

    @Test
    public void actualizarMedicoBadRequestKO() throws Exception
    {
        log.debug("---> actualizarMedicoBadRequestKO");
        //Declaración de objetos de testing:
        Long idInencontrable = 666L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(true);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/medicos/{id}", idInencontrable)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verificaciones:
        verify(servicio, times(0)).buscarMedico(any());
        verify(mapeador, times(0)).convertirPutRequestDtoAEntidad(any(Medico.class), any(MedicoPutRequestDTO.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- actualizarMedicoBadRequestKO");
    }

    @Test
    public void actualizarMedicoNoContentKO() throws Exception
    {
        log.debug("---> actualizarMedicoNoContentKO");
        //Declaración de objetos de testing:
        Long idInexistente = 999L;
        BindingResult bindingResult = mock(BindingResult.class);

        //Definición de comportamiento:
        when(bindingResult.hasErrors()).thenReturn(false);
        when(servicio.buscarMedico(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/hospital/api/medicos/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.getDummyPutRequestJsonContent()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(idInexistente);
        verify(mapeador, times(0)).convertirPutRequestDtoAEntidad(any(Medico.class), any(MedicoPutRequestDTO.class));
        verify(mapeador, times(0)).convertirEntidadAResponseDto(any(Medico.class));
        log.debug("<--- actualizarMedicoNoContentKO");
    }

    @Test
    public void eliminarMedicoOK() throws Exception
    {
        log.debug("---> eliminarMedicoOK");
        Long medicoId = 1L;

        //Definición de comportamiento:
        when(servicio.buscarMedico(medicoId)).thenReturn(Optional.of(this.getDummyEntidad()));
        doNothing().when(servicio).eliminarMedico(medicoId);

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/medicos/{id}", medicoId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(medicoId);
        verify(servicio, times(1)).eliminarMedico(medicoId);
        log.debug("<--- eliminarMedicoOK");
    }

    @Test
    public void eliminarMedicoNoContentKO() throws Exception
    {
        log.debug("---> eliminarMedicoNoContentKO");
        Long idInexistente = 999L;

        //Definición de comportamiento:
        when(servicio.buscarMedico(idInexistente)).thenReturn(Optional.empty());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/medicos/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).buscarMedico(idInexistente);
        verify(servicio, times(0)).eliminarMedico(idInexistente);
        log.debug("<--- eliminarMedicoNoContentKO");
    }

    @Test
    public void eliminarListaMedicosOK() throws Exception
    {
        log.debug("---> eliminarListaMedicosOK");
        //Definición de comportamiento:
        when(servicio.leerListaMedicos()).thenReturn(List.of(this.getDummyEntidad()));
        doNothing().when(servicio).eliminarTodosMedicos();

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/medicos"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //Verificaciones:
        verify(servicio, times(1)).leerListaMedicos();
        verify(servicio, times(1)).eliminarTodosMedicos();
        log.debug("<--- eliminarListaMedicosOK");
    }

    @Test
    public void eliminarListaMedicosNoContentKO() throws Exception
    {
        log.debug("---> eliminarListaMedicosNoContentKO");
        //Definición de comportamiento:
        when(servicio.leerListaMedicos()).thenReturn(Collections.emptyList());

        //Llamada al controlador mock:
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/hospital/api/medicos"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //Verificaciones:
        verify(servicio, times(1)).leerListaMedicos();
        verify(servicio, times(0)).eliminarTodosMedicos();
        log.debug("<--- eliminarListaMedicosNoContentKO");
    }
}
