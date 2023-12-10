package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import dev.acobano.springrestful.hospital.mapeadores.interfaces.IFechaMapeador;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Clase de la capa de mapeadores encargado de la traducción entre los objetos Java para
 * fechas (LocalDate, LocalTime y LocalDateTime) y las diferentes cadenas de texto con
 * sus formatos especificados.
 * <>
 * @author Álvaro Cobano
 */
@Component
@Slf4j
public class FechaMapeadorImpl implements IFechaMapeador
{
                                        // *****************
                                        // ***  MÉTODOS  ***
                                        // *****************

    /**
     * Método que traduce un objeto de la clase LocalDate a una cadena de texto con formato 'dd/MM/yyyy'
     *
     * @param fecha Objeto de la clase LocalDate con los datos de la fecha a transformar.
     * @return Cadena de texto con formato 'dd/MM/yyyy'
     */
    @Override
    public String convertirLocalDateAString(LocalDate fecha) 
    {
        log.info("---> convertirLocalDateAString");
        int dia = fecha.getDayOfMonth();
        int mes = fecha.getMonthValue();
        int anio = fecha.getYear();
        StringBuilder builder = new StringBuilder();
        
        if (dia < 10)
            builder.append("0");
        
        builder.append(dia).append("/");
        
        if (mes < 10)
            builder.append("0");
        
        builder.append(mes).append("/").append(anio);
        log.info("<--- convertirLocalDateAString");
        return builder.toString();
    }

    /**
     * Método que traduce una cadena de texto con formato 'dd/MM/yyyy' a un objeto de la clase LocalDate.
     *
     * @param fechaString Cadena de texto con formato 'dd/MM/yyyy' con los datos de la fecha a transformar.
     * @return Objeto de la clase LocalDate.
     */
    @Override
    public LocalDate convertirStringALocalDate(String fechaString) 
    {
        log.info("---> convertirStringALocalDate");
        String[] stringDatos = fechaString.split("/");
        LocalDate fecha = LocalDate.of(Integer.parseInt(stringDatos[2]), 
                                       Integer.parseInt(stringDatos[1]), 
                                       Integer.parseInt(stringDatos[0]));
        log.info("<--- convertirStringALocalDate");
        return fecha;
        
    }

    /**
     * Método que traduce un objeto de la clase LocalTime a una cadena de texto con formato 'HH:mm:ss'
     *
     * @param horario Objeto de la clase LocalTime.
     * @return Cadena de texto con formato 'HH:mm:ss'
     */
    @Override
    public String convertirLocalTimeAString(LocalTime horario) 
    {
        log.info("---> convertirLocalTimeAString");
        int hora = horario.getHour();
        int minutos = horario.getMinute();
        int segundos = horario.getSecond();
        StringBuilder builder = new StringBuilder();
        
        if (hora < 10)
            builder.append("0");
        
        builder.append(hora).append(":");
        
        if (minutos < 10)
            builder.append("0");
        
        builder.append(minutos).append(":");
        
        if (segundos < 10)
            builder.append("0");

        builder.append(segundos);
        
        log.info("<--- convertirLocalTimeAString");
        return builder.toString();
    }

    /**
     * Método que traduce una cadena de texto con formato 'HH:mm:ss' a un objeto de la clase LocalTime.
     *
     * @param horaString Cadena de texto con formato 'HH:mm:ss'
     * @return Objeto de la clase LocalTime
     */
    @Override
    public LocalTime convertirStringALocalTime(String horaString) 
    {
        log.info("---> convertirStringALocalTime");
        String[] stringDatos = horaString.split(":");
        LocalTime horario = LocalTime.of(Integer.parseInt(stringDatos[0]),
                                         Integer.parseInt(stringDatos[1]),
                                         Integer.parseInt(stringDatos[2]));
        log.info("<--- convertirStringALocalTime");
        return horario;
    }

    /**
     * Método que transforma un objeto de la clase LocalDateTime a una cadena
     * de texto con formato 'dd/MM/yyyy HH:mm:ss'
     *
     * @param fecha Objeto de la clase LocalDateTime
     * @return Cadena de texto con formato 'dd/MM/yyyy HH:mm:ss'
     */
    @Override
    public String convertirLocalDateTimeAString(LocalDateTime fecha)
    {
        log.info("---> convertirLocalDateTimeAString");
        int dia = fecha.getDayOfMonth();
        int mes = fecha.getMonthValue();
        int anio = fecha.getYear();
        int hora = fecha.getHour();
        int minutos = fecha.getMinute();
        int segundos = fecha.getSecond();
        StringBuilder builder = new StringBuilder();
        
        if (dia < 10)
            builder.append("0");
        
        builder.append(dia).append("/");
        
        if (mes < 10)
            builder.append("0");
        
        builder.append(mes).append("/").append(anio).append(" ");
               
        if (hora < 10)
            builder.append("0");
        
        builder.append(hora).append(":");
        
        if (minutos < 10)
            builder.append("0");
        
        builder.append(minutos).append(":");
        
        if (segundos < 10)
            builder.append("0");

        builder.append(segundos);
        
        log.info("<--- convertirLocalDateTimeAString");
        return builder.toString();
    }

    /**
     * Método que transforma una cadena de texto con formato 'dd/MM/yyyy HH:mm:ss'
     * a un objeto de la clase LocalDateTime.
     *
     * @param fechaString Cadena de texto con formato 'dd/MM/yyyy HH:mm:ss'
     * @return Objeto de la clase LocalDateTime.
     */
    @Override
    public LocalDateTime convertirStringALocalDateTime(String fechaString) 
    {
        log.info("---> convertirStringALocalDateTime");
        String[] stringDatos = fechaString.split(" ");
        String[] stringFecha = stringDatos[0].split("/");
        String[] stringHora = stringDatos[1].split(":");
        LocalDateTime fecha = 
                LocalDateTime.of(Integer.parseInt(stringFecha[2]),
                Integer.parseInt(stringFecha[1]), 
                Integer.parseInt(stringFecha[0]), 
                Integer.parseInt(stringHora[0]),
                Integer.parseInt(stringHora[1]),
                Integer.parseInt(stringHora[2]));
        log.info("<--- convertirStringALocalDateTime");
        return fecha;
    }
}
