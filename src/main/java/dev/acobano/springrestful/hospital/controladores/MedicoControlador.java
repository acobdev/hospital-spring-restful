package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.MedicoPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.MedicoPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.ApiErrorResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.MedicoResponseDTO;
import dev.acobano.springrestful.hospital.dto.salida.PacienteMedicoDTO;
import dev.acobano.springrestful.hospital.dto.salida.ValidacionErrorResponseDTO;
import dev.acobano.springrestful.hospital.excepciones.MedicoNoEncontradoExcepcion;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.IMedicoMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.servicios.interfaces.IMedicoServicio;

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
 * que se encuentren relacionados con las entidades de clase 'Médico' de la aplicación Spring.
 * <>
 * @author Álvaro Cobano
 */
@RestController
@RequestMapping("/hospital/api/medicos")
@Tag(
        name = "MedicoControlador",
        description = "Clase de la capa de controlador encargada de manipular los endpoints de las llamadas " +
                "HTTP que se encuentren relacionados con las entidades de clase 'Médico' de la aplicación Spring."
)
@Slf4j
public class MedicoControlador
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Autowired
    private IMedicoServicio servicio;

    @Autowired
    private IMedicoMapeador mapeador;



                                        // *****************
                                        // ***  MÉTODOS  ***
                                        // *****************

    /**
     * Método que contiene el endpoint que obtiene los datos de un médico registrado en el sistema
     * cuyo número identificador coincida con el introducido como parámetro de entrada.
     *
     * @param medicoId Número identificador del médico a buscar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Buscar médico por ID",
            description = "Endpoint que obtiene los datos de un médico registrado en el sistema cuyo" +
                    " número identificador coincida con el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médico encontrado en el sistema con éxito",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún médico con el ID introducido",
                    content = @Content
            )
    })
    @GetMapping (
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MedicoResponseDTO> obtenerMedico(
            @Parameter (
                    description = "El número identificador del médico a buscar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long medicoId
    ) {
        log.info("---> obtenerMedico");
        //Llamamos a la capa de servicio para ver si existe algún médico con el ID especificado:
        Optional<Medico> optMedico = this.servicio.buscarMedico(medicoId);
        
        if (!optMedico.isPresent())
            throw new MedicoNoEncontradoExcepcion("No existe ningún médico con el ID introducido");
        else
        {
            //En caso afirmativo, lo devolvemos al body del response mapeado en su DTO de salida:
            MedicoResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(optMedico.get());
            log.info("<--- obtenerMedico");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }


    /**
     * Método que desarrolla un endpoint que devuelve en formato de lista
     * a todos los médicos registrados en el sistema.
     *
     * @param nombre Parámetro opcional para buscar por un nombre específico.
     * @param especialidad Parámetro opcional para buscar por una especialidad específica.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Obtener lista de médicos",
            description = "Endpoint que devuelve en formato de lista a todos los médicos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de médicos retornada con éxito",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MedicoResponseDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún médico en el sistema para mostrar",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicoResponseDTO>> listarMedicos(
            @Parameter(
                    description = "Parámetro opcional para buscar por un nombre específico",
                    example = "Daniel"
            )
            @RequestParam(required = false)
            String nombre,
            @Parameter(
                    description = "Parámetro opcional para buscar por una especialidad específica.",
                    example = "CARDIOLOGIA"
            )
            @Pattern(
                    regexp = "(CIRUGIA|PEDIATRIA|ONCOLOGIA|CARDIOLOGIA|GINECOLOGIA|TRAUMATOLOGIA|DERMATOLOGIA|PSIQUIATRIA|OFTALMOLOGIA)$",
                    message = "El dato introducido en el parámetro 'Especialidad' no cumple con las reglas de validación."
            )
            @RequestParam(required = false)
            String especialidad
    ) {
        log.info("---> listarMedicos");
        List<Medico> listaMedicos;

        //Llamamos a los diferentes filtros de la capa de servicio según los parámetros de entrada recibidos:
        if (!Objects.isNull(nombre) && !Objects.isNull(especialidad))
            listaMedicos = this.servicio.filtrarMedicosPorNombreYEspecialidad(nombre, especialidad);
        else if (!Objects.isNull(nombre))
            listaMedicos = this.servicio.filtrarMedicosPorNombre(nombre);
        else if (!Objects.isNull(especialidad))
            listaMedicos = this.servicio.filtrarMedicosPorEspecialidad(especialidad);
        else
            listaMedicos = this.servicio.leerListaMedicos();
        
        if (listaMedicos.isEmpty())
            throw new MedicoNoEncontradoExcepcion("No existe ningún médico en el sistema para mostrar");
        else
        {
            //Mapeamos la lista de entidades Médico a sus correspondientes DTOs de salida:
            List<MedicoResponseDTO> listaDto = new ArrayList<>(listaMedicos.size());

            for (Medico medico : listaMedicos)
                listaDto.add(this.mapeador.convertirEntidadAResponseDto(medico));

            log.info("<--- listarMedicos");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que recoge un endpoint que devuelve una lista de DTOs con datos de todos los pacientes asignados
     * al médico cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param medicoId Número identificador del médico cuyos pacientes se desee listar.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(summary = "Obtener pacientes asignados a un médico",
    description = "Endpoint que devuelve una lista de DTOs con datos de todos los pacientes asignados " +
                  "al médico cuyo número identificador sea el introducido como parámetro de entrada.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pacientes asignados retornada exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PacienteMedicoDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún médico en el sistema con el ID especificado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @GetMapping(
            value = "/{id}/pacientes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<PacienteMedicoDTO>> obtenerPacientesPorMedico(
            @Parameter(
                    description = "El número identificador del médico a buscar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long medicoId
    ) {
        log.info("---> obtenerPacientesPorMedico");
        //Llamamos al servicio para localizar al médico dentro del sistema:
        Optional<Medico> optMedico = this.servicio.buscarMedico(medicoId);
        
        if (!optMedico.isPresent())
            throw new MedicoNoEncontradoExcepcion("No existe ningún médico en el sistema con el ID especificado");
        else
        {
            //En caso de existir, mapeamos a su DTO los pacientes asignados a ese médico:
            List<PacienteMedicoDTO> listaDto =
                    this.mapeador.convertirPacientesAsignadosADto(optMedico.get());
            
            log.info("<--- obtenerPacientesPorMedico");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que define el endpoint que guarda en el sistema los datos del nuevo médico
     * expuestos en el DTO introducido en el body del request HTTP.
     *
     * @param dtoEntrada DTO de entrada con los datos del médico a guardar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(summary = "Guardar nuevo médico",
               description = "Endpoint que guarda en el sistema los datos del nuevo médico " +
                             "expuestos en el DTO introducido en el body del request HTTP.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Médico insertado con éxito en el sistema",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class)
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
    public ResponseEntity<MedicoResponseDTO> guardarMedico(
            @Parameter(
                    description = "DTO de entrada con los datos del médico a guardar en el sistema",
                    schema = @Schema(implementation = MedicoPostRequestDTO.class)
            )
            @Valid @RequestBody
            MedicoPostRequestDTO dtoEntrada
    ){
        log.info("---> guardarMedico");

        //Mapeamos del DTO de entrada los datos a guardar del nuevo médico:
        Medico medicoAGuardar = this.mapeador.convertirPostRequestDtoAEntidad(dtoEntrada);

        //Llamamos al servicio para guardar la nueva entidad Médico:
        this.servicio.guardarMedico(medicoAGuardar);

        //Mapeamos los datos de la nueva entidad a su respectivo DTO de salida:
        MedicoResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(medicoAGuardar);
        log.info("<--- guardarMedico");
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalida);
    }

    /**
     * Método para generar un endpoint que permite actualizar algunos o todos los datos preexistentes de
     * un determinado médico cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param dtoEntrada DTO de entrada con los datos del médico a actualizar en el sistema.
     * @param medicoId El número identificador del médico que se desea actualizar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(summary = "Actualizar médico por ID",
               description = "Endpoint que permite actualizar algunos o todos los datos preexistentes de un " +
                       "determinado médico cuyo número identificador sea el introducido como parámetro de entrada.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos del médico actualizados exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = MedicoResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún médico en el sistema con el ID especificado",
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
    public ResponseEntity<MedicoResponseDTO> actualizarMedico(
            @Parameter(
                    description = "DTO de entrada con los datos del médico a actualizar",
                    schema = @Schema(implementation = MedicoPutRequestDTO.class)
            )
            @Valid @RequestBody
            MedicoPutRequestDTO dtoEntrada,
            @Parameter(
                    description = "El número identificador del médico que se desea actualizar",
                    example = "1"
            )
            @PathVariable("id")
            Long medicoId
    ){
        log.info("---> actualizarMedico");

        //Buscamos en el servicio si el médico con ID especificado existe:
        Optional<Medico> optMedico = this.servicio.buscarMedico(medicoId);
        
        if (!optMedico.isPresent())
            throw new MedicoNoEncontradoExcepcion("No existe ningún médico en el sistema con el ID especificado");
        else
        {
            //Recogemos la entidad actualizada empleando el mapeador:
            Medico actualizado = this.mapeador.convertirPutRequestDtoAEntidad(optMedico.get(), dtoEntrada);

            //Guardamos la entidad actualizada en el repositorio:
            this.servicio.guardarMedico(actualizado);

            //Devolvemos el DTO de la entidad actualizada:
            MedicoResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(actualizado);
            log.info("<--- actualizarMedico");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }

    /**
     * Método que ejecuta un endpoint encargado de eliminar del sistema al médico cuyo número
     * identificador coincida con el introducido por parámetro de entrada.
     *
     * @param medicoId El número identificador del médico que se desea eliminar del sistema.
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(summary = "Eliminar médico por ID",
               description = "Endpoint encargado de eliminar del sistema al médico cuyo número " +
                             "identificador coincida con el introducido por parámetro de entrada.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos del médico eliminados exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún médico en el sistema con el ID especificado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarMedico(
            @Parameter(
                    description = "El número identificador del médico que se desea eliminar del sistema",
                    example = "1"
            )
            @PathVariable("id") Long medicoId
    ) {
        log.info("---> eliminarMedico");
        //Buscamos en el sistema el posible médico con el ID introducido:
        Optional<Medico> optMedico = this.servicio.buscarMedico(medicoId);
        
        if (!optMedico.isPresent())
            throw new MedicoNoEncontradoExcepcion("No existe ningún médico en el sistema con el ID especificado");
        else
        {
            //En caso de existir, llamamos al método del servicio para eliminarlo:
            this.servicio.eliminarMedico(medicoId);  
            log.info("<--- eliminarMedico");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    /**
     * Método que genera un endpoint encargado de eliminar del sistema
     * a todos los médicos que se encuentren guardados en él.
     *
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(summary = "Eliminar todos los médicos",
               description = "Endpoint encargado de eliminar del sistema a todos " +
                             "los médicos que se encuentren guardados en él")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de médicos eliminada exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ningún médico en el sistema para eliminar",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @DeleteMapping
    public ResponseEntity<Void> eliminarListaMedicos()
    {
        log.info("---> eliminarListaMedicos");
        //Buscamos en el sistema la lista de todos los médicos existentes:
        List<Medico> listaMedicos = this.servicio.leerListaMedicos();

        if (listaMedicos.isEmpty())
            throw new MedicoNoEncontradoExcepcion("No existe ningún médico en el sistema para eliminar");
        else
        {
            //Llamamos a la capa de servicio para eliminarlos a todos del sistema:
            this.servicio.eliminarTodosMedicos();
            log.info("<--- eliminarListaMedicos");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
