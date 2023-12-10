package dev.acobano.springrestful.hospital.modelo.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase de la capa de entidades que define todos los datos relacionados con
 * una sala hospitalaria en la base de datos de la aplicación Spring.
 *
 * @author Álvaro Cobano
 */
@Entity
@Table(name = "salas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sala 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long id;
    
    @Column(name = "num_sala")
    private int numero;
    
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Cita> citasAsignadas;
}