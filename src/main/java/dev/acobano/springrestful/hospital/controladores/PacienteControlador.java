package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.PacientePostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.PacientePutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.*;
import dev.acobano.springrestful.hospital.excepciones.PacienteNoEncontradoExcepcion;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IPacienteMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.servicios.interfaces.IPacienteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase de la capa de controlador encargada de manipular los endpoints de las llamadas HTTP
 * que se encuentren relacionados con las entidades de clase 'Paciente' de la aplicación Spring.
 * <>
 * @author Álvaro Cobano
 */

@RestController
@RequestMapping("/hospital/api/pacientes")
@Tag(
        name = "PacienteControlador",
        description = "Clase de la capa de controlador encargada de manipular los endpoints de las llamadas " +
                "HTTP que se encuentran relacionados con las entidades de clase 'Paciente' de la aplicación Spring."
)
@Slf4j
public class PacienteControlador
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private IPacienteServicio servicio;

    @Autowired
    private IPacienteMapeador mapeador;

    @Autowired
    private ICitaMapeador citaMapeador;



                                        // *****************
                                        // ***  MÉTODOS  ***
                                        // *****************

    /**
     * Método que contiene el endpoint que obtiene los datos de un paciente registrado en el sistema
     * cuyo número identificador coincida con el introducido como parámetro de entrada.
     *
     * @param pacienteId Número identificador del paciente a buscar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Buscar paciente por ID",
            description = "Endpoint que obtiene los datos de un paciente registrado en el sistema " +
                    "cuyo número identificador coincida con el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado en el sistema con éxito",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún paciente en el sistema con el ID introducido",
                    content = @Content
            )
    })
    @GetMapping (
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PacienteResponseDTO> obtenerPaciente(
            @Parameter (
                    description = "El número identificador del paciente a buscar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long pacienteId
    ) {
        log.info("---> obtenerPaciente");
        //Buscamos en la capa de servicio un posible paciente con el ID del parámetro de entrada:
        Optional<Paciente> optionalPaciente = this.servicio.buscarPaciente(pacienteId);
        
        if (!optionalPaciente.isPresent())
            throw new PacienteNoEncontradoExcepcion("No existe ningún paciente en el sistema con el ID especificado");
        else
        {
            //En caso de encontrarse, enviamos sus datos en su DTO de salida:
            PacienteResponseDTO dto = this.mapeador.convertirEntidadAResponseDto(optionalPaciente.get());
            log.info("<--- obtenerPaciente");
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
    }

    /**
     * Método que desarrolla un endpoint que devuelve en formato de lista
     * a todos los pacientes registrados en el sistema.
     *
     * @param gravedad Parámetro opcional para buscar pacientes por una determinada gravedad de afección.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Obtener lista de pacientes",
            description = "Endpoint que devuelve en formato de lista a todos los pacientes registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pacientes retornada con éxito",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PacienteResponseDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún paciente en el sistema para mostrar",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes(
            @Parameter(
                    description = "Parámetro opcional para buscar pacientes por una determinada gravedad de afección.",
                    example = "LEVE"
            )
            @Pattern(
                    regexp = "(ASINTOMATICA|LEVE|MODERADA|GRAVE|CRITICA)$",
                    message = "El dato introducido en el parámetro 'Gravedad' no cumple con las reglas de validación."
            )
            @RequestParam(required = false)
            String gravedad
    ) {
        log.info("---> listarPacientes");
        List<Paciente> listaPacientes;

        //Filtramos la lista de pacientes en función de la presencia o no de la cadena de parámetro de entrada:
        if (Objects.isNull(gravedad))
            listaPacientes = this.servicio.leerListaPacientes();
        else
            listaPacientes = this.servicio.filtrarPacientesPorGravedad(gravedad);
        
        if (listaPacientes.isEmpty())
            throw new PacienteNoEncontradoExcepcion("No existe ningún paciente en el sistema para mostrar");
        else
        {
            //Mapeamos toda la lista de entidades a sus correspondientes DTO de salida:
            List<PacienteResponseDTO> listaDto = new ArrayList<>(listaPacientes.size());

            for (Paciente p : listaPacientes)
                listaDto.add(this.mapeador.convertirEntidadAResponseDto(p));

            log.info("<--- listarPacientes");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que recoge un endpoint que devuelve una lista de DTOs con datos de todas los citas asignados
     * al paciente cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param pacienteId Número identificador del paciente cuyas citas se desea buscar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Obtener citas asignadas a un paciente",
            description = "Endpoint que devuelve una lista de DTOs con datos de todas los citas asignadas " +
                    "al paciente cuyo número identificador sea el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de citas asignadas retornada exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PacienteMedicoDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún paciente en el sistema con el ID especificado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @GetMapping(
            value = "/{id}/citas",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorPaciente(
            @Parameter(
                    description = "El número identificador del paciente cuyas citas se desea buscar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long pacienteId
    ) {
        log.info("---> obtenerCitasPorPaciente");
        //Buscamos en la capa de servicio el paciente con ID especificado:
        Optional<Paciente> optPaciente = this.servicio.buscarPaciente(pacienteId);
        
        if (!optPaciente.isPresent())
            throw new PacienteNoEncontradoExcepcion("No existe ningún paciente en el sistema con el ID especificado");
        else
        {
            //En caso de existir, mapeamos las citas asignadas a dicho paciente:
            List<Cita> listaCitas = optPaciente.get().getCitasAsignadas();
            List<CitaResponseDTO> listaDto = new ArrayList<>(listaCitas.size());

            for(Cita c : listaCitas)
                listaDto.add(this.citaMapeador.convertirEntidadAResponseDto(c));

            log.info("<--- obtenerCitasPorPaciente");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que define el endpoint que guarda en el sistema los datos del nuevo
     * paciente expuestos en el DTO introducido en el body del request HTTP.
     *
     * @param dtoEntrada DTO de entrada con los datos del paciente a guardar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Guardar nuevo paciente",
            description = "Endpoint que guarda en el sistema los datos del nuevo médico " +
                    "expuestos en el DTO introducido en el body del request HTTP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Paciente insertado con éxito en el sistema",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos del DTO de entrada mal validados",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidacionErrorResponseDTO.class)
                    )}
            )
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PacienteResponseDTO> guardarPaciente(
            @Parameter(
                    description = "DTO de entrada con los datos del paciente a guardar en el sistema.",
                    schema = @Schema(implementation = PacientePostRequestDTO.class)
            )
            @Valid @RequestBody
            PacientePostRequestDTO dtoEntrada
    ){
        log.info("---> guardarPaciente");

        //Extraemos los datos del DTO de entrada y los guardamos en una nueva entidad Paciente:
        Paciente pacienteAGuardar = this.mapeador.convertirPostRequestDtoAEntidad(dtoEntrada);

        //Llamamos a la capa de servicio para guardar la entidad en el sistema:
        this.servicio.guardarPaciente(pacienteAGuardar);

        //Devolvemos los datos de la entidad guardada remapeados a su DTO de salida:
        PacienteResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(pacienteAGuardar);
        log.info("<--- guardarPaciente");
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalida);
    }

    /**
     * Método para generar un endpoint que permite actualizar algunos o todos los datos preexistentes de
     * un determinado paciente cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param dtoEntrada DTO de entrada con los datos del paciente a actualizar en el sistema.
     * @param pacienteId El número identificador del paciente que se desea actualizar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Actualizar paciente por ID",
            description = "Endpoint que permite actualizar algunos o todos los datos preexistentes de un " +
                    "determinado paciente cuyo número identificador sea el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos del paciente actualizados exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún paciente en el sistema con el ID especificado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos del DTO de entrada mal validados",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidacionErrorResponseDTO.class)
                    )}
            )
    })
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente (
            @Parameter(
                    description = "DTO de entrada con los datos del paciente a actualizar en el sistema.",
                    schema = @Schema(implementation = PacientePutRequestDTO.class)
            )
            @Valid @RequestBody
            PacientePutRequestDTO dtoEntrada,
            @Parameter(
                    description = "Número identificador del paciente que se desea actualizar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long pacienteId
    ){
        log.info("---> actualizarPaciente");

        //Buscamos en el sistema a través del servicio si el paciente con ID especificado existe:
        Optional<Paciente> optPaciente = this.servicio.buscarPaciente(pacienteId);
        
        if (!optPaciente.isPresent())
            throw new PacienteNoEncontradoExcepcion("No existe ningún paciente en el sistema con el ID especificado");
        else
        {
            //Mapeamos los datos obtenidos del DTO de entrada en la entidad encontrada:
            Paciente actualizado = this.mapeador.convertirPutRequestDtoAEntidad(optPaciente.get(), dtoEntrada);

            //Llamamos a la capa de servicio para guardar el paciente ya actualizado:
            this.servicio.guardarPaciente(actualizado);

            //Mapeamos nuevamente la entidad actualizada en su correspondiente DTO de salida:
            PacienteResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(actualizado);
            log.info("<--- actualizarPaciente");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }

    /**
     * Método que ejecuta un endpoint encargado de eliminar del sistema al paciente
     * cuyo número identificador coincida con el introducido por parámetro de entrada.
     *
     * @param pacienteId Número identificador del paciente que se desea eliminar del sistema.
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(
            summary = "Eliminar paciente por ID",
            description = "Endpoint encargado de eliminar del sistema al paciente cuyo número " +
                    "identificador coincida con el introducido por parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos del paciente eliminados exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún paciente en el sistema con el ID especificado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @DeleteMapping(
            value = "/{id}"
    )
    public ResponseEntity<Void> eliminarPaciente(
            @Parameter(
                    description = "Número identificador del paciente que se desea eliminar del sistema",
                    example = "1"
            )
            @PathVariable("id") Long pacienteId
    ) {
        log.info("---> eliminarPaciente");
        //Buscamos en el servicio si el paciente con ID seleccionado existe:
        Optional<Paciente> optPaciente = this.servicio.buscarPaciente(pacienteId);
        
        if (!optPaciente.isPresent())
            throw new PacienteNoEncontradoExcepcion("No existe ningún paciente en el sistema con el ID especificado");
        else
        {
            //Llamamos a la capa de servicio para eliminar el paciente encontrado del sistema:
            this.servicio.eliminarPaciente(pacienteId);
            log.info("<--- eliminarPaciente");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    /**
     * Método que genera un endpoint encargado de eliminar del sistema
     * a todos los pacientes que se encuentren guardados en él.
     *
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(
            summary = "Eliminar todos los pacientes",
            description = "Endpoint encargado de eliminar del sistema a todos los pacientes que se encuentren guardados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pacientes eliminada exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún paciente en el sistema para eliminar",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @DeleteMapping
    public ResponseEntity<Void> eliminarListaPacientes()
    {
        log.info("---> eliminarListaPacientes");

        //Corroboramos primero si existen pacientes en el sistema:
        List<Paciente> listaPacientes = this.servicio.leerListaPacientes();

        if (listaPacientes.isEmpty())
            throw new PacienteNoEncontradoExcepcion("No existe ningún paciente en el sistema para eliminar");
        else
        {
            //En caso afirmativo, se llama al servicio para eliminarlos a todos:
            this.servicio.eliminarTodosPacientes();
            log.info("<--- eliminarListaPacientes");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
