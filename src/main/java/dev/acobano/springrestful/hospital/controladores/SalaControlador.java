package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.SalaRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.*;
import dev.acobano.springrestful.hospital.excepciones.SalaNoEncontradaExcepcion;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ISalaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.servicios.interfaces.ISalaServicio;
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
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase de la capa de controlador encargada de manipular los endpoints de las llamadas HTTP
 * que se encuentren relacionados con las entidades de clase 'Sala' de la aplicación Spring.
 * <>
 * @author Álvaro Cobano
 */
@RestController
@RequestMapping("hospital/api/salas")
@Tag(
        name = "SalaControlador",
        description = "Clase de la capa de controlador encargada de manipular los endpoints de las llamadas " +
                "HTTP que se encuentren relacionados con las entidades de clase 'Sala' de la aplicación Spring."
)
@Slf4j
public class SalaControlador 
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private ISalaServicio servicio;

    @Autowired
    private ISalaMapeador mapeador;

    @Autowired
    private ICitaMapeador citaMapeador;



                                            // *****************
                                            // ***  MÉTODOS  ***
                                            // *****************

    /**
     * Método que contiene el endpoint que obtiene los datos de una sala registrada en el sistema
     * cuyo número identificador coincida con el introducido como parámetro de entrada.
     *
     * @param salaId Número identificador de la sala a buscar en el sistema
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Buscar sala por ID",
            description = "Endpoint que obtiene los datos de una sala registrada en el sistema " +
                    "cuyo número identificador coincida con el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sala encontrada en el sistema con éxito",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna sala en el sistema con el ID introducido",
                    content = @Content
            )
    })
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SalaResponseDTO> obtenerSala(
            @Parameter(
                    description = "El número identificador de la sala a buscar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long salaId
    ) {
        log.info("---> obtenerSala");
        //Buscamos la sala con el ID de entrada dentro de la capa de servicio:
        Optional<Sala> optSala = this.servicio.buscarSala(salaId);
        
        if (!optSala.isPresent())
            throw new SalaNoEncontradaExcepcion("No existe ninguna sala en el sistema con el ID especificado");
        else
        {
            //Mapeamos los datos de dicha sala a su DTO de salida:
            SalaResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(optSala.get());
            log.info("<--- obtenerSala");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }

    /**
     * Método que desarrolla un endpoint que devuelve en formato de lista
     * a todos las salas registradas en el sistema.
     *
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Obtener lista de salas",
            description = "Endpoint que devuelve en formato de lista a todos las salas registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de salas retornada con éxito",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SalaResponseDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna sala en el sistema para mostrar",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SalaResponseDTO>> listarSalas()
    {
        log.info("---> listarSalas");
        //Traemos en formato lista todas las salas registradas en el sistema:
        List<Sala> listaSalas = this.servicio.leerListaSalas();
        
        if (listaSalas.isEmpty())
            throw new SalaNoEncontradaExcepcion("No existe ninguna sala en el sistema para mostrar");
        else
        {
            //Las mapeamos todas a su respectivo DTO de salida:
            List<SalaResponseDTO> listaDto = new ArrayList<>(listaSalas.size());

            for (Sala s : listaSalas)
                listaDto.add(this.mapeador.convertirEntidadAResponseDto(s));

            log.info("<--- listarSalas");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que recoge un endpoint que devuelve una lista de DTOs con datos de todas las citas asignados
     * a la sala cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param salaId Número identificador de la sala cuyas citas se desee listar.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Obtener citas asignadas a una sala",
            description = "Endpoint que devuelve una lista de DTOs con datos de todos las citas asignadas " +
                    "a la sala cuyo número identificador sea el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de citas asignados retornada exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PacienteMedicoDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna sala en el sistema con el ID especificado",
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
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorSala(
            @Parameter(
                    description = "Número identificador de la sala cuyas citas se desee listar.",
                    example = "1"
            )
            @PathVariable("id")
            Long salaId
    ) {
        log.info("---> obtenerCitasPorSala");
        //Buscamos en el servicio si la sala con el ID de entrada se encuentra en el sistema:
        Optional<Sala> optSala = this.servicio.buscarSala(salaId);
        
        if (!optSala.isPresent())
            throw new SalaNoEncontradaExcepcion("No existe ninguna sala en el sistema con el ID especificado");
        else
        {
            //Recogemos todas las citas de la sala encontrada:
            List<Cita> listaCitas = optSala.get().getCitasAsignadas();
            List<CitaResponseDTO> listaDto = new ArrayList<>(listaCitas.size());

            //Mapeamos las citas a su respectivo DTO de salida:
            for (Cita c : listaCitas)
                listaDto.add(this.citaMapeador.convertirEntidadAResponseDto(c));

            log.info("<--- obtenerCitasPorSala");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que define el endpoint que guarda en el sistema los datos de la nueva
     * sala expuestos en el DTO introducido en el body del request HTTP.
     *
     * @param dtoEntrada DTO de entrada con los datos del médico a guardar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Guardar nueva sala",
            description = "Endpoint que guarda en el sistema los datos de la nueva sala " +
                    "expuestos en el DTO introducido en el body del request HTTP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Sala insertada con éxito en el sistema",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class)
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
    public ResponseEntity<SalaResponseDTO> guardarSala(
            @Parameter(
                    description = "DTO de entrada con los datos de la sala a guardar en el sistema",
                    schema = @Schema(implementation = SalaRequestDTO.class)
            )
            @Valid @RequestBody
            SalaRequestDTO dtoEntrada
    ) {
        log.info("---> guardarSala");

        //Mapeamos los datos del DTO de entrada en su correspondiente entidad:
        Sala sala = this.mapeador.convertirRequestDtoAEntidad(dtoEntrada);

        //Llamamos a la capa de servicio para guardar la nueva entidad en el sistema:
        this.servicio.guardarSala(sala);

        //Mapeamos la nueva entidad a su respectivo DTO de salida:
        SalaResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(sala);
        log.info("<--- guardarSala");
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalida);
    }

    /**
     * Método para generar un endpoint que permite actualizar algunos o todos los datos preexistentes de
     * una determinada sala cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param dtoEntrada DTO de entrada con los datos de la sala a actualizar en el sistema.
     * @param salaId Número identificador del médico que se desea actualizar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Actualizar sala por ID",
            description = "Endpoint que permite actualizar algunos o todos los datos preexistentes de" +
                    "una determinada sala cuyo número identificador sea el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos de la sala actualizados exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = SalaResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna sala en el sistema con el ID especificado",
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
    public ResponseEntity<SalaResponseDTO> actualizarSala(
            @Parameter(
                    description = "DTO de entrada con los datos de la sala a actualizar en el sistema",
                    schema = @Schema(implementation = SalaRequestDTO.class)
            )
            @Valid @RequestBody
            SalaRequestDTO dtoEntrada,
            @Parameter(
                    description = "El número identificador de la sala que se desea actualizar",
                    example = "1"
            )
            @PathVariable("id")
            Long salaId
    ) {
        log.info("---> actualizarSala");
        Optional<Sala> optSala = this.servicio.buscarSala(salaId);
        
        if (!optSala.isPresent())
            throw new SalaNoEncontradaExcepcion("No existe ninguna sala en el sistema con el ID especificado");
        else
        {
            //Mapeamos los datos del DTO de entrada en una entidad actualizada:
            Sala actualizada = optSala.get();
            actualizada.setNumero(dtoEntrada.getNumSala());

            //Llamamos a la capa de servicio para guardar la entidad a actualizar:
            this.servicio.guardarSala(actualizada);

            //Mapeamos nuevamente la entidad hacia su correspondiente DTO de salida:
            SalaResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(actualizada);
            log.info("<--- actualizarSala");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }

    /**
     * Método que ejecuta un endpoint encargado de eliminar del sistema a la sala cuyo
     * número identificador coincida con el introducido por parámetro de entrada.
     *
     * @param salaId Número identificador de la sala que se desea eliminar del sistema.
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(
            summary = "Eliminar sala por ID",
            description = "Endpoint encargado de eliminar del sistema a la sala cuyo número " +
                    "identificador coincida con el introducido por parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos de la sala eliminados exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna sala en el sistema con el ID especificado",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminarSala(
            @Parameter(
                    description = "El número identificador de la sala que se desea eliminar del sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long salaId
    ) {
        log.info("---> eliminarSala");
        //Buscamos en la capa de servicio si la sala con ID especificado existe:
        Optional<Sala> optSala = this.servicio.buscarSala(salaId);
        
        if (!optSala.isPresent())
            throw new SalaNoEncontradaExcepcion("No existe ninguna sala en el sistema con el ID especificado");
        else
        {
            //De ser así, llamamos nuevamente al servicio para eliminarla del sistema:
            this.servicio.eliminarSala(salaId);
            log.info("<--- eliminarSala");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    /**
     * Método que genera un endpoint encargado de eliminar del sistema
     * a todas los salas que se encuentren guardadas en él.
     *
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(
            summary = "Eliminar todas las salas",
            description = "Endpoint encargado de eliminar del sistema todas las salas que se encuentren guardadas en él."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de salas eliminada exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna sala en el sistema para eliminar",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponseDTO.class)
                    )}
            )
    })
    @DeleteMapping
    public ResponseEntity<Void> eliminarListaSalas()
    {
        log.info("---> eliminarListaSalas");
        //Buscamos en primer lugar si existen salas para eliminar en el sistema:
        List<Sala> listaSalas = this.servicio.leerListaSalas();

        if (listaSalas.isEmpty())
            throw new SalaNoEncontradaExcepcion("No existe ninguna sala en el sistema para eliminar");
        else
        {
            //En caso afirmativo, llamamos al servicio para que las elimine todas:
            this.servicio.eliminarTodasSalas();
            log.info("<--- eliminarListaSalas");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
