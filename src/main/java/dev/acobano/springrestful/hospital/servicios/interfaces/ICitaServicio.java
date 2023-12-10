package dev.acobano.springrestful.hospital.servicios.interfaces;

import dev.acobano.springrestful.hospital.modelo.entidades.Cita;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de la capa de servicio que implementa los métodos los cuales gestionan
 * la sección lógica de la aplicación relacionada con las entidades de clase 'Cita'.
 * <>
 * @author Álvaro Cobano
 */
public interface ICitaServicio 
{
    Optional<Cita> buscarCita(Long id);
    List<Cita> leerListaCitas();
    void guardarCita(Cita entidad);
    void eliminarCita(Long id);
    void eliminarTodasCitas();
}
