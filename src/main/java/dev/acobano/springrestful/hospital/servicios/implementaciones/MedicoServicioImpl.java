package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import dev.acobano.springrestful.hospital.repositorios.MedicoRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.IMedicoServicio;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de la capa de servicio encargado de gestionar la sección lógica de
 * la aplicación relacionada con las entidades de clase 'Médico'.
 * <>
 * @author Álvaro Cobano
 */
@Service
@Slf4j
public class MedicoServicioImpl implements IMedicoServicio
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private MedicoRepositorio repositorio;



                                            // *****************
                                            // ***  MÉTODOS  ***
                                            // *****************
    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> buscarMedico(Long id)
    {
        //Obtenemos la posible entidad llamando al repositorio:        
        log.info("---> buscarMedico");
        Optional<Medico> optMedico = this.repositorio.findById(id); 
        log.info("<--- buscarMedico");
        return optMedico;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> leerListaMedicos()
    {
        log.info("---> leerListaMedicos");
        List<Medico> listaEntidades = this.repositorio.findAll();        
        log.info("<--- leerListaMedicos");
        return listaEntidades;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Medico> filtrarMedicosPorNombre(String nombre)
    {
        log.info("---> filtrarMedicosPorNombre");
        //Filtramos la lista por nombre usando los flujos del JDK 8:
        List<Medico> listaFiltrada = this.repositorio.findAll()
                .stream()
                .filter(medico -> medico.getNombre().equalsIgnoreCase(nombre))
                .toList();
        log.info("<--- filtrarMedicosPorNombre");
        return listaFiltrada;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Medico> filtrarMedicosPorEspecialidad(String especialidad)
    {
        log.info("---> filtrarMedicosPorEspecialidad");
        //Filtramos la lista por especialidad usando los flujos del JDK 8:
        List<Medico> listaFiltrada = this.repositorio.findAll()
                .stream()
                .filter(medico -> medico.getEspecialidad().name().equalsIgnoreCase(especialidad))
                .toList();
        log.info("<--- filtrarMedicosPorEspecialidad");
        return listaFiltrada;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Medico> filtrarMedicosPorNombreYEspecialidad(String nombre, String especialidad)
    {
        log.info("---> filtrarMedicosPorNombreYEspecialidad");
        //Filtramos la lista por nombre y especialidad usando los flujos del JDK 8:
        List<Medico> listaFiltrada = this.repositorio.findAll()
                .stream()
                .filter(medico -> medico.getNombre().equalsIgnoreCase(nombre)
                        && medico.getEspecialidad().name().equalsIgnoreCase(especialidad)
                ).toList();
        log.info("<--- filtrarMedicosPorNombreYEspecialidad");
        return listaFiltrada;
    }

    @Override
    @Transactional
    public void guardarMedico(Medico medico)
    {
        log.info("---> guardarMedico");
        this.repositorio.save(medico);
        log.info("<--- guardarMedico");
    }
    @Override
    @Transactional
    public void eliminarMedico(Long id) 
    {
        log.info("---> eliminarMedico");
        this.repositorio.deleteById(id);
        log.info("<--- eliminarMedico");
    }

    @Override
    @Transactional
    public void eliminarTodosMedicos()
    {
        log.info("---> eliminarTodosMedicos");
        this.repositorio.deleteAll();
        log.info("<--- eliminarTodosMedicos");
    }
}
