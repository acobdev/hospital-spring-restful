package dev.acobano.springrestful.hospital.servicios.implementaciones;

import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import dev.acobano.springrestful.hospital.repositorios.SalaRepositorio;
import dev.acobano.springrestful.hospital.servicios.interfaces.ISalaServicio;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de la capa de servicio encargado de gestionar la sección lógica de
 * la aplicación relacionada con las entidades de clase 'Sala'.
 * <>
 * @author Álvaro Cobano
 */
@Service
@Slf4j
public class SalaServicioImpl implements ISalaServicio
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************

    @Autowired
    private SalaRepositorio repositorio;



                                            // *****************
                                            // ***  MÉTODOS  ***
                                            // *****************

    @Override
    @Transactional(readOnly = true)
    public Optional<Sala> buscarSala(Long id)
    {
        log.info("---> buscarSala");
        Optional<Sala> optSala = this.repositorio.findById(id);
        log.info("<--- buscarSala");
        return optSala;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sala> leerListaSalas()
    {
        log.info("---> leerListaSalas");
        List<Sala> listaEntidades = this.repositorio.findAll();
        log.info("<--- leerListaSalas");
        return listaEntidades;
    }

    @Override
    @Transactional
    public void guardarSala(Sala entidad)
    {
        log.info("---> guardarSala");
        this.repositorio.save(entidad);
        log.info("<--- guardarSala");
    }

    @Override
    @Transactional
    public void eliminarSala(Long id) 
    {
        log.info("---> eliminarSala");
        this.repositorio.deleteById(id);
        log.info("<--- eliminarSala");
    }

    @Override
    @Transactional
    public void eliminarTodasSalas()
    {
        log.info("---> eliminarTodasSalas");
        this.repositorio.deleteAll();
        log.info("<--- eliminarTodasSalas");
    }
}
