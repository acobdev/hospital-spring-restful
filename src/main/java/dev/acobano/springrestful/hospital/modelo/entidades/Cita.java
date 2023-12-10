package dev.acobano.springrestful.hospital.modelo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase de la capa de entidades que define todos los datos relacionados con una
 * cita médica en la base de datos de la aplicación Spring.
 * <>
 * @author Álvaro Cobano
 */

@Entity
@Table(name = "citas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cita 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cita_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_cita")
    private LocalDate fechaCita;
    
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_entrada")
    private LocalTime horaEntrada;
    
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_salida")
    private LocalTime horaSalida;
}
