package dev.acobano.springrestful.hospital.modelo.entidades;

import dev.acobano.springrestful.hospital.modelo.enumerados.Especialidad;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase de la capa de entidades que define todos los datos relacionados
 * con un médico en la base de datos de la aplicación Spring.
 *
 * @author Álvaro Cobano
 */

@Entity
@Table(name = "medicos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medico 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medico_id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "dni")
    private String dni;
    
    @Column(name = "email")
    private String email;    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "especialidad")
    private Especialidad especialidad;
       
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_graduacion")
    private LocalDate fechaGraduacion;
        
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_incorporacion")
    private LocalDate fechaIncorporacion;
    
    @OneToMany(mappedBy = "medicoAsignado")
    private List<Paciente> pacientesAsignados;
}

