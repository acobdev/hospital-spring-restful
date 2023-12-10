package dev.acobano.springrestful.hospital.servicios.interfaces;

import dev.acobano.springrestful.hospital.modelo.entidades.Sala;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de la capa de servicio que implementa los métodos los cuales gestionan
 * la sección lógica de la aplicación relacionada con las entidades de clase 'Sala'.
 * <>
 * @author Álvaro Cobano
 */
public interface ISalaServicio 
{
    Optional<Sala> buscarSala(Long id);
    List<Sala> leerListaSalas();
    void guardarSala(Sala entidad);
    void eliminarSala(Long id);
    void eliminarTodasSalas();
}
