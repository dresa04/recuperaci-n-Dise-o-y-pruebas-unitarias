package data;


import static org.junit.jupiter.api.Assertions.*;

import data.Exceptions.InvalidGeographicPointException;
import org.junit.jupiter.api.Test;

class GeographicPointTest {
    @Test
    void testValidCoordinates() {
        assertDoesNotThrow(() -> new GeographicPoint(45.0f, 90.0f));
    }

    @Test
    void testInvalidLatitudeLow() {
        Exception e = assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(-91, 0));
        assertEquals("La latitud debe estar entre -90 y 90 grados.", e.getMessage());
    }

    @Test
    void testInvalidLatitudeHigh() {
        Exception e = assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(91, 0));
        assertEquals("La latitud debe estar entre -90 y 90 grados.", e.getMessage());
    }

    @Test
    void testInvalidLongitudeLow() {
        Exception e = assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(0, -181));
        assertEquals("La longitud debe estar entre -180 y 180 grados.", e.getMessage());
    }

    @Test
    void testInvalidLongitudeHigh() {
        Exception e = assertThrows(InvalidGeographicPointException.class, () -> new GeographicPoint(0, 181));
        assertEquals("La longitud debe estar entre -180 y 180 grados.", e.getMessage());
    }
}
