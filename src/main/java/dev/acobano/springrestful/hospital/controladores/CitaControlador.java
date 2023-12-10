package dev.acobano.springrestful.hospital.controladores;

import dev.acobano.springrestful.hospital.dto.entrada.CitaPostRequestDTO;
import dev.acobano.springrestful.hospital.dto.entrada.CitaPutRequestDTO;
import dev.acobano.springrestful.hospital.dto.salida.CitaResponseDTO;
import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.servicios.interfaces.ICitaServicio;
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
 * que se encuentren relacionados con las entidades de clase 'Cita' de la aplicación Spring.
 * <>
 * @author Álvaro Cobano
 */
@RestController
@RequestMapping("/hospital/api/citas")
@Tag(
        name = "CitaControlador",
        description = "Clase de la capa de controlador encargada de manipular los endpoints de las llamadas " +
                "HTTP que se encuentren relacionados con las entidades de clase 'Cita' de la aplicación Spring."
)
@Slf4j
public class CitaControlador 
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private ICitaServicio servicio;

    @Autowired
    private ICitaMapeador mapeador;



                                            // *****************
                                            // ***  MÉTODOS  ***
                                            // *****************

    /**
     * Método que contiene el endpoint que obtiene los datos de una cita registrada en el sistema
     * cuyo número identificador coincida con el introducido como parámetro de entrada.
     *
     * @param citaId El número identificador de la cita a buscar en el sistema.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Buscar cita por ID",
            description = "Endpoint que obtiene los datos de una cita registrada en el sistema " +
                    "cuyo número identificador coincida con el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cita encontrada en el sistema con éxito",
                    content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CitaResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna cita con el ID introducido",
                    content = @Content
            )
    })
    @GetMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CitaResponseDTO> obtenerCita(
            @Parameter(
                    description = "El número identificador de la cita a buscar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long citaId
    ) {
        log.info("---> obtenerCita");
        Optional<Cita> optCita = this.servicio.buscarCita(citaId);
        
        if (!optCita.isPresent())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
        {
            CitaResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(optCita.get());
            log.info("<--- obtenerCita");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }

    /**
     * Método que desarrolla un endpoint que devuelve en formato de lista todas las citas registradas en el sistema.
     *
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Obtener lista de citas",
            description = "Endpoint que devuelve en formato de lista a todas las citas registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de citas retornada con éxito",
                    content = { @Content (
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CitaResponseDTO.class))
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna cita en el sistema para mostrar",
                    content = @Content
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CitaResponseDTO>> listarCitas()
    {
        log.info("---> listarCitas");
        List<Cita> listaCitas = this.servicio.leerListaCitas();
        
        if (listaCitas.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
        {
            List<CitaResponseDTO> listaDto = new ArrayList<>(listaCitas.size());

            for(Cita c : listaCitas)
                listaDto.add(this.mapeador.convertirEntidadAResponseDto(c));

            log.info("<--- listarCitas");
            return ResponseEntity.status(HttpStatus.OK).body(listaDto);
        }
    }

    /**
     * Método que define el endpoint que guarda en el sistema los datos de la nueva
     * cita expuestos en el DTO introducido en el body del request HTTP.
     *
     * @param dtoEntrada DTO de entrada con los datos de la cita a guardar en el sistema.
     * @param bindingResult Objeto encargado de verificar la validación de los datos del DTO.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Guardar nueva cita",
            description = "Endpoint que guarda en el sistema los datos de la nueva " +
                    "cita expuestos en el DTO introducido en el body del request HTTP."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cita insertada con éxito en el sistema",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = CitaResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos del DTO de entrada mal validados",
                    content = @Content
            )
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CitaResponseDTO> guardarCita(
            @Parameter(
                    description = "DTO de entrada con los datos de la cita a guardar en el sistema",
                    schema = @Schema(implementation = CitaPostRequestDTO.class)
            )
            @Valid @RequestBody
            CitaPostRequestDTO dtoEntrada,
            BindingResult bindingResult
    ) {
        log.info("---> guardarCita");

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //Mapeamos la nueva entidad 'Cita' a partir de los datos del DTO de entrada:
        Cita cita = this.mapeador.convertirPostResquestDtoAEntidad(dtoEntrada);

        //Llamamos a la capa de servicio para guardar la nueva entidad en el sistema:
        this.servicio.guardarCita(cita);

        //Mapeamos nuevamente dichos datos guardados en su respectivo DTO de salida:
        CitaResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(cita);
        log.info("<--- guardarCita");
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalida);
    }

    /**
     * Método para generar un endpoint que permite actualizar algunos o todos los datos preexistentes de
     * una determinada cita cuyo número identificador sea el introducido como parámetro de entrada.
     *
     * @param dtoEntrada DTO de entrada con los datos de la cita a actualizar en el sistema.
     * @param citaId El número identificador de la cita que se desea actualizar en el sistema.
     * @param bindingResult Objeto encargado de verificar la validación de los datos del DTO.
     * @return Objeto de la clase ResponseEntity en cuyo body se encuentra la respuesta de la llamada HTTP.
     */
    @Operation(
            summary = "Actualizar cita por ID",
            description = "Endpoint que permite actualizar algunos o todos los datos preexistentes de " +
                    "una determinada cita cuyo número identificador sea el introducido como parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos de la cita actualizados exitosamente",
                    content = { @Content (
                            mediaType = "application/json",
                            schema = @Schema(implementation = CitaResponseDTO.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna cita en el sistema con el ID especificado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos del DTO de entrada mal validados",
                    content = @Content
            )
    })
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CitaResponseDTO> actualizarCita(
            @Parameter(
                    description = "DTO de entrada con los datos de la cita a actualizar en el sistema",
                    schema = @Schema(implementation = CitaPutRequestDTO.class)
            )
            @Valid @RequestBody
            CitaPutRequestDTO dtoEntrada,
            @Parameter(
                    description = "Número identificador de la cita que se desea actualizar en el sistema",
                    example = "1"
            )
            @PathVariable("id")
            Long citaId,
            BindingResult bindingResult
    ){
        log.info("---> actualizarCita");

        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Optional<Cita> optCita = this.servicio.buscarCita(citaId);
        
        if (!optCita.isPresent())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
        {
            //Mapeamos los datos existentes en el DTO dentro de la entidad encontrada:
            Cita actualizada = this.mapeador.convertirPutRequestDtoAEntidad(optCita.get(), dtoEntrada);

            //Llamamos a la capa de servicio para guardar los nuevos datos:
            this.servicio.guardarCita(actualizada);

            //Mapeamos dichos datos actualizados en su correspondiente DTO de salida:
            CitaResponseDTO dtoSalida = this.mapeador.convertirEntidadAResponseDto(actualizada);
            log.info("<--- actualizarCita");
            return ResponseEntity.status(HttpStatus.OK).body(dtoSalida);
        }
    }

    /**
     * Método que ejecuta un endpoint encargado de eliminar del sistema a la cita
     * cuyo número identificador coincida con el introducido por parámetro de entrada.
     *
     * @param citaId El número identificador del paciente que se desea eliminar del sistema.
     * @return Objeto de la clase ResponseEntity.
     */
    @Operation(
            summary = "Eliminar cita por ID",
            description = "Endpoint encargado de eliminar del sistema a la cita " +
                    "cuyo número identificador coincida con el introducido por parámetro de entrada."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos de la cita eliminados exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna cita en el sistema con el ID especificado",
                    content = @Content
            )
    })
    @DeleteMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public ResponseEntity<Void> eliminarCita(
            @Parameter(
                    description = "Número identificador de la cita que se desea eliminar del sistema",
                    example = "1"
            )
            @PathVariable("id") Long citaId
    ) {
        log.info("---> eliminarCita");
        Optional<Cita> optCita = this.servicio.buscarCita(citaId);
        
        if (!optCita.isPresent())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
        {
            this.servicio.eliminarCita(citaId);
            log.info("<--- eliminarCita");
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
            summary = "Eliminar todas las citas",
            description = "Endpoint encargado de eliminar del sistema a todas los citas que se encuentren guardadas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de citas eliminada exitosamente del sistema",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe ninguna cita en el sistema para eliminar",
                    content = @Content
            )
    })
    @DeleteMapping
    public ResponseEntity<Void> eliminarListaCitas()
    {
        log.info("---> eliminarListaCitas");
        List<Cita> listaCitas = this.servicio.leerListaCitas();

        if (listaCitas.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
        {
            this.servicio.eliminarTodasCitas();
            log.info("<--- eliminarListaCitas");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
