package modelo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author KEVIN
 */
public class FechaUtil {
    
    /**
     * Obtiene el año actual.
     * @return El año actual como entero.
     */
    public static int obtenerAnioActual() {
        return LocalDateTime.now().getYear();
    }

    /**
     * Obtiene el mes actual.
     * @return El mes actual como entero (1-12).
     */
    public static int obtenerMesActual() {
        return LocalDateTime.now().getMonthValue();
    }

    /**
     * Obtiene la fecha y hora actuales.
     * @return Un objeto LocalDateTime con la fecha y hora actuales.
     */
    public static LocalDateTime obtenerFechaHoraActual() {
        return LocalDateTime.now();
    }

    /**
     * Formatea la fecha y hora actuales a un formato específico.
     * @param formato El formato deseado, por ejemplo, "yyyy-MM-dd HH:mm:ss".
     * @return La fecha y hora formateadas como String.
     */
    public static String obtenerFechaHoraFormateada(String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return LocalDateTime.now().format(formatter);
    }
}
