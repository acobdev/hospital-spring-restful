package dev.acobano.springrestful.hospital.repositorios;

import dev.acobano.springrestful.hospital.modelo.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de la capa de repositorio extendida de JpaRepository que sirve
 * para realizar CRUD en la BBDD con entidades de clase 'Médico'.
 * <>
 * @author Álvaro Cobano
 */
@Repository
public interface MedicoRepositorio extends JpaRepository<Medico, Long> {
    
}
