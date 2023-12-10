package dev.acobano.springrestful.hospital.servicios.interfaces;

import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de la capa de servicio que implementa los métodos los cuales gestionan
 * la sección lógica de la aplicación relacionada con las entidades de clase 'Médico'.
 * <>
 * @author Álvaro Cobano
 */
public interface IMedicoServicio 
{
    Optional<Medico> buscarMedico(Long id);
    List<Medico> leerListaMedicos();
    List<Medico> filtrarMedicosPorNombre(String nombre);
    List<Medico> filtrarMedicosPorEspecialidad(String especialidad);
    List<Medico> filtrarMedicosPorNombreYEspecialidad(String nombre, String especialidad);
    void guardarMedico(Medico medico);
    void eliminarMedico(Long id);
    void eliminarTodosMedicos();
}
