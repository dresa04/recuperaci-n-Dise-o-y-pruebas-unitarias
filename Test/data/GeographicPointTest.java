package data;

import data.Exceptions.InvalidGeographicPointException;
import data.interfaces.GeographicPointInterface;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeographicPointTest {

    @Test
    void testValidGeographicPoint() {
        GeographicPointInterface point = new GeographicPoint(41.1234f, 2.5678f);
        assertEquals(41.1234f, point.getLatitude(), "La latitud no es la esperada.");
        assertEquals(2.5678f, point.getLongitude(), "La longitud no es la esperada.");
    }

    @Test
    void testInvalidLatitudeThrowsException() {
        assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(91.0f, 10.0f),
                "Se esperaba una excepción para latitud > 90.");
        assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(-91.0f, 10.0f),
                "Se esperaba una excepción para latitud < -90.");
    }

    @Test
    void testInvalidLongitudeThrowsException() {
        assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(10.0f, 181.0f),
                "Se esperaba una excepción para longitud > 180.");
        assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(10.0f, -181.0f),
                "Se esperaba una excepción para longitud < -180.");
    }

    @Test
    void testEqualsAndHashCode() {
        GeographicPointInterface point1 = new GeographicPoint(41.1234f, 2.5678f);
        GeographicPointInterface point2 = new GeographicPoint(41.1234f, 2.5678f);
        GeographicPointInterface point3 = new GeographicPoint(42.0000f, 3.0000f);

        assertEquals(point1, point2, "Los puntos deberían ser iguales.");
        assertNotEquals(point1, point3, "Los puntos deberían ser diferentes.");

        assertEquals(point1.hashCode(), point2.hashCode(), "Los códigos hash deberían ser iguales.");
        assertNotEquals(point1.hashCode(), point3.hashCode(), "Los códigos hash deberían ser diferentes.");
    }

    @Test
    void testToString() {
        GeographicPointInterface point = new GeographicPoint(41.1234f, 2.5678f);
        String expectedString = "GeographicPoint{latitude=41.1234, longitude=2.5678}";
        assertEquals(expectedString, point.toString(), "El método toString no devuelve el formato esperado.");
    }
}
