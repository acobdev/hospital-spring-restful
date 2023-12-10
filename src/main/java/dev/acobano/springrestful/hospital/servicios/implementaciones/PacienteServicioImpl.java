package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.mapeadores.interfaces.IPacienteMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import dev.acobano.springrestful.hospital.repositorios.PacienteRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.IPacienteServicio;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de la capa de servicio encargado de gestionar la sección lógica de
 * la aplicación relacionada con las entidades de clase 'Paciente'.
 * <>
 * @author Álvaro Cobano
 */
@Service
@Slf4j
public class PacienteServicioImpl implements IPacienteServicio
{
                                // *******************
                                // ***  ATRIBUTOS  ***
                                // *******************

    @Autowired
    private PacienteRepositorio repositorio;



                                // *****************
                                // ***  MÉTODOS  ***
                                // *****************

    /**
     * Método que realiza una llamada al repositorio para encontrar a un paciente
     * cuyo número identificador coincida con el introducido como parámetro de entrada.
     *
     * @param id Número identificador del paciente a buscar en el sistema.
     * @return Objeto de la clase Optional en cuyo interior podría encontrarse el paciente buscado.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPaciente(Long id)
    {
        log.info("---> buscarPaciente");
        Optional<Paciente> optPaciente = this.repositorio.findById(id);
        log.info("<--- buscarPaciente");
        return optPaciente;
    }

    /**
     * Método que realiza una llamada al repositorio para mostrar una lista
     * con todos los pacientes que existen registrados dentro del sistema.
     *
     * @return Lista de entidades 'Paciente' con todos los datos disponibles.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> leerListaPacientes()
    {
        log.info("---> leerListaPacientes");
        List<Paciente> listaPacientes = this.repositorio.findAll();
        log.info("<--- leerListaPacientes");
        return listaPacientes;
    }

    /**
     * Método que realiza una llamada al repositorio para devolver una lista
     * de pacientes cuyo parámetro 'Gravedad' coincida con el introducido por
     * parámetro de entrada.
     *
     * @param gravedad Cadena de texto con la gravedad de la afección que se desea buscar.
     * @return Lista de entidades 'Paciente' con todos los datos disponibles.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Paciente> filtrarPacientesPorGravedad(String gravedad)
    {
        log.info("---> filtrarPacientesPorGravedad");

        //Filtramos la lista por gravedad usando los flujos del JDK 8:
        List<Paciente> listaFiltrada = this.repositorio.findAll()
                .stream()
                .filter(paciente -> paciente.getGravedad().name().equalsIgnoreCase(gravedad))
                .toList();
        
        log.info("<--- filtrarPacientesPorGravedad");
        return listaFiltrada;
    }

    /**
     * Método que realiza una llamada al repositorio para guardar en el sistema
     * a un nuevo paciente introducido como parámetro de entrada.
     *
     * @param pacienteAGuardar Entidad cuyos datos están listos para ser guardados en el sistema.
     */
    @Override
    @Transactional
    public void guardarPaciente(Paciente pacienteAGuardar)
    {
        log.info("---> guardarPaciente");
        this.repositorio.save(pacienteAGuardar);
        log.info("<--- guardarPaciente");
    }

    /**
     * Método que realiza una llamada al repositorio para eliminar del sistema al
     * paciente cuyo número identificador coincida con el introducido por parámetro
     * de entrada.
     *
     * @param id Número identificador del paciente a eliminar.
     */
    @Override
    @Transactional
    public void eliminarPaciente(Long id) 
    {
        log.info("---> eliminarPaciente");
        this.repositorio.deleteById(id);
        log.info("<--- eliminarPaciente");
    }

    /**
     * Método que realiza una llamada al repositorio para eliminar del sistema
     * a todos los pacientes que se encuentren registradas en él.
     */
    @Override
    public void eliminarTodosPacientes()
    {
        log.info("---> eliminarTodosPacientes");
        this.repositorio.deleteAll();
        log.info("<--- eliminarTodosPacientes");
    }
}
