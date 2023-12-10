package dev.acobano.springrestful.hospital.mapeadores.implementaciones;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Clase de testing para los métodos de la capa de mapeadores para la traducción
 * de objetos del paquete Date de Java y su parseo a cadenas de texto y viceversa.
 * <>
 * @author Álvaro Cobano
 */
@SpringBootTest
@ContextConfiguration(classes = FechaMapeadorImpl.class)
@Slf4j
public class FechaMapeadorImplTest
{
                                            // *******************
                                            // ***  ATRIBUTOS  ***
                                            // *******************
    
    @Autowired
    private FechaMapeadorImpl mapeador;



                                            // ***********************
                                            // ***  OBJETOS DUMMY  ***
                                            // ***********************
    
    private LocalDate getDummyLocalDate()
    {
        return LocalDate.of(1931, 4, 4);
    }
    
    private String getDummyLocalDateString()
    {
        return "04/04/1931";
    }
    
    private LocalTime getDummyLocalTime()
    {
        return LocalTime.of(4, 5, 0);
    }
    
    private String getDummyLocalTimeString()
    {
        return "04:05:00";
    }
    
    private LocalDateTime getDummyLocalDateTime()
    {
        return LocalDateTime.of(1873, 2, 1, 5, 0, 5);
    }
    
    private String getDummyLocalDateTimeString()
    {
        return "01/02/1873 05:00:05";
    }



                                        // ***************************
                                        // ***  CLASES de TESTING  ***
                                        // ***************************
    
    @Test
    public void convertirLocalDateAStringTestOK()
    {
        log.debug("---> convertirLocalDateAStringTestOK");
        //Declaraciones de objetos de testing:
        LocalDate entrada = this.getDummyLocalDate();
        String esperado = this.getDummyLocalDateString();
        String resultado = this.mapeador.convertirLocalDateAString(entrada);

        //Aseveraciones:
        assertNotNull(resultado);

        for(int i=0; i<esperado.length(); i++)
            assertEquals(esperado.charAt(i), resultado.charAt(i));

        log.debug("<--- convertirLocalDateAStringTestOK");
    }
    
    @Test
    public void convertirStringALocalDateTestOK()
    {
        log.debug("---> convertirStringALocalDateTestOK");
        //Declaración de objetos de testing:
        String entrada = this.getDummyLocalDateString();
        LocalDate esperado = this.getDummyLocalDate();
        LocalDate resultado = this.mapeador.convertirStringALocalDate(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getDayOfMonth(), resultado.getDayOfMonth()),
                () -> assertEquals(esperado.getMonth(), resultado.getMonth()),
                () -> assertEquals(esperado.getYear(), resultado.getYear())
        );
        log.debug("<--- convertirStringALocalDateTestOK");
    }
    
    @Test
    public void convertirLocalTimeAStringTestOK()
    {
        log.debug("---> convertirLocalTimeAStringTestOK");
        //Declaraciones de objetos de testing:
        LocalTime entrada = this.getDummyLocalTime();
        String esperado = this.getDummyLocalTimeString();
        String resultado = this.mapeador.convertirLocalTimeAString(entrada);

        //Aseveraciones:
        assertNotNull(resultado);

        for (int i=0; i<esperado.length(); i++)
            assertEquals(esperado.charAt(i), resultado.charAt(i));

        log.debug("<--- convertirLocalTimeAStringTestOK");
    }
    
    @Test
    public void convertirStringALocalTimeTestOK()
    {
        log.debug("---> convertirStringALocalTimeTestOK");
        //Declaraciones de objetos de testing:
        String entrada = this.getDummyLocalTimeString();
        LocalTime esperado = this.getDummyLocalTime();
        LocalTime resultado = this.mapeador.convertirStringALocalTime(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getHour(), resultado.getHour()),
                () -> assertEquals(esperado.getMinute(), resultado.getMinute()),
                () -> assertEquals(esperado.getSecond(), resultado.getSecond())
        );
        log.debug("<--- convertirStringALocalTimeTestOK");
    }
    
    @Test
    public void convertirLocalDateTimeAStringTestOK()
    {
        log.debug("---> convertirLocalDateTimeAStringTestOK");
        //Declaraciones de objetos de testing:
        LocalDateTime entrada = this.getDummyLocalDateTime();
        String esperado = this.getDummyLocalDateTimeString();
        String resultado = this.mapeador.convertirLocalDateTimeAString(entrada);

        //Aseveraciones:
        assertNotNull(resultado);

        for (int i=0; i<esperado.length(); i++)
            assertEquals(esperado.charAt(i), resultado.charAt(i));

        log.debug("<--- convertirLocalDateTimeAStringTestOK");
    }
    
    @Test
    public void convertirStringALocalDateTimeTestOK()
    {
        log.debug("---> convertirStringALocalDateTimeTestOK");
        //Declaraciones de objetos de testing:
        String entrada = this.getDummyLocalDateTimeString();
        LocalDateTime esperado = this.getDummyLocalDateTime();
        LocalDateTime resultado = this.mapeador.convertirStringALocalDateTime(entrada);

        //Aseveraciones:
        assertAll (
                () -> assertNotNull(resultado),
                () -> assertEquals(esperado.getDayOfMonth(), resultado.getDayOfMonth()),
                () -> assertEquals(esperado.getMonth(), resultado.getMonth()),
                () -> assertEquals(esperado.getYear(), resultado.getYear()),
                () -> assertEquals(esperado.getHour(), resultado.getHour()),
                () -> assertEquals(esperado.getMinute(), resultado.getMinute()),
                () -> assertEquals(esperado.getSecond(), resultado.getSecond())
        );
        log.debug("<--- convertirStringALocalDateTimeTestOK");
    }
}
