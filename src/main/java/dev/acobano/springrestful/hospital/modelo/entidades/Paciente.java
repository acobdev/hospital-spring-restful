package dev.acobano.springrestful.hospital.modelo.entidades;

import dev.acobano.springrestful.hospital.modelo.enumerados.Genero;
import dev.acobano.springrestful.hospital.modelo.enumerados.Gravedad;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase de la capa de entidades que define todos los datos relacionados
 * con un paciente en la base de datos de la aplicación Spring.
 *
 * @author Álvaro Cobano
 */

@Entity
@Table(name = "pacientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paciente 
{
                                        // *******************
                                        // ***  ATRIBUTOS  ***
                                        // *******************

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paciente_id")
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "dni")
    private String dni;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gravedad")
    private Gravedad gravedad;
    
    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;
    
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medicoAsignado;
    
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Cita> citasAsignadas;
}
