package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.mapeadores.interfaces.ICitaMapeador;
import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import dev.acobano.springrestful.hospital.repositorios.CitaRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.ICitaServicio;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de la capa de servicio encargado de gestionar la sección lógica de
 * la aplicación relacionada con las entidades de clase 'Cita'.
 * <>
 * @author Álvaro Cobano
 */
@Service
@Slf4j
public class CitaServicioImpl implements ICitaServicio
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private CitaRepositorio repositorio;
    
    @Autowired
    private ICitaMapeador mapeador;



                                            // *****************
                                            // ***  MÉTODOS  ***
                                            // *****************


    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarCita(Long id)
    {
        log.info("---> buscarCita");
        Optional<Cita> optCita = this.repositorio.findById(id);
        log.info("<--- buscarCita");
        return optCita;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> leerListaCitas()
    {
        log.info("---> leerListaCitas");
        List<Cita> listaCitas = this.repositorio.findAll();
        log.info("<--- leerListaCitas");
        return listaCitas;
    }

    @Override
    @Transactional
    public void guardarCita(Cita entidad)
    {
        log.info("---> guardarCita");
        this.repositorio.save(entidad);
        log.info("<--- guardarCita");
    }

    @Override
    @Transactional
    public void eliminarCita(Long id) 
    {
        log.info("---> eliminarCita");
        this.repositorio.deleteById(id);
        log.info("<--- eliminarCita");
    }

    @Override
    @Transactional
    public void eliminarTodasCitas()
    {
        log.info("---> eliminarTodasCitas");
        this.repositorio.deleteAll();
        log.info("<--- eliminarTodasCitas");
    }
}
