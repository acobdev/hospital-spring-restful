package dev.acobano.springrestful.hospital.servicios.interfaces;

import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de la capa de servicio que implementa los métodos los cuales gestionan
 * la sección lógica de la aplicación relacionada con las entidades de clase 'Paciente'.
 * <>
 * @author Álvaro Cobano
 */
public interface IPacienteServicio 
{
    Optional<Paciente> buscarPaciente(Long id);
    List<Paciente> leerListaPacientes();
    List<Paciente> filtrarPacientesPorGravedad(String gravedad);
    void guardarPaciente(Paciente pacienteAGuardar);
    void eliminarPaciente(Long id);
    void eliminarTodosPacientes();
}
