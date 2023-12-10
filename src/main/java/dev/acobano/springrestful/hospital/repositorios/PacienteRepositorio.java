package dev.acobano.springrestful.hospital.repositorios;

import dev.acobano.springrestful.hospital.modelo.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Clase de la capa de repositorio extendida de JpaRepository que sirve
 * para realizar CRUD en la BBDD con entidades de clase 'Paciente'.
 * <>
 * @author Álvaro Cobano
 */
@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    
}
