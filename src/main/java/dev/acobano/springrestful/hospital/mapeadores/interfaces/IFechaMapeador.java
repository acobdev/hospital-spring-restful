package dev.acobano.springrestful.hospital.mapeadores.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Interfaz de la capa de mapeadores que implementa los métodos que gestionan la traducción
 * entre los objetos Java para fechas (LocalDate, LocalTime y LocalDateTime) y las diferentes
 * cadenas de texto con sus formatos especificados.
 * <>
 * @author Álvaro Cobano
 */
public interface IFechaMapeador 
{
    String convertirLocalDateAString(LocalDate fecha);
    LocalDate convertirStringALocalDate(String fechaString);
    String convertirLocalTimeAString(LocalTime hora);
    LocalTime convertirStringALocalTime(String horaString);
    String convertirLocalDateTimeAString(LocalDateTime fecha);
    LocalDateTime convertirStringALocalDateTime(String fechaString);
}
