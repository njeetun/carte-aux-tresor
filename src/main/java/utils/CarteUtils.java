package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CarteUtils {

    private CarteUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Convertir un localDateTime en string suivant le format voulu
     *
     * @param localDateTime localDateTime
     * @param pattern       le pattern
     * @return date-heure
     */
    public static String convertLocalDateTimeToString(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);

    }

}
