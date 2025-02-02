package data;

import data.Exceptions.InvalidStationIDException;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationIDTest {

    @Test
    void testValidStationID() {
        GeographicPointInterface geoPoint = new GeographicPoint(41.1234f, 2.5678f);
        StationIDInterface station = new StationID(1, geoPoint);

        assertEquals(1, station.getID(), "El ID de la estación no es el esperado.");
        assertEquals(geoPoint, station.getgeoPoint(), "El punto geográfico no es el esperado.");
    }

    @Test
    void testInvalidStationIDThrowsException() {
        GeographicPointInterface geoPoint = new GeographicPoint(41.1234f, 2.5678f);

        assertThrows(InvalidStationIDException.class, () -> new StationID(0, geoPoint),
                "Se esperaba una excepción para ID igual a 0.");
        assertThrows(InvalidStationIDException.class, () -> new StationID(-5, geoPoint),
                "Se esperaba una excepción para ID negativo.");
    }

    @Test
    void testEqualsAndHashCode() {
        GeographicPointInterface geoPoint1 = new GeographicPoint(41.1234f, 2.5678f);
        GeographicPointInterface geoPoint2 = new GeographicPoint(42.5678f, 3.7890f);

        StationIDInterface station1 = new StationID(1, geoPoint1);
        StationIDInterface station2 = new StationID(1, geoPoint1);
        StationIDInterface station3 = new StationID(2, geoPoint2);

        assertEquals(station1, station2, "Las estaciones deberían ser iguales.");
        assertNotEquals(station1, station3, "Las estaciones deberían ser diferentes.");

        assertEquals(station1.hashCode(), station2.hashCode(), "Los códigos hash deberían ser iguales.");
        assertNotEquals(station1.hashCode(), station3.hashCode(), "Los códigos hash deberían ser diferentes.");
    }

    @Test
    void testToString() {
        GeographicPointInterface geoPoint = new GeographicPoint(41.1234f, 2.5678f);
        StationIDInterface station = new StationID(1, geoPoint);

        String expectedString = "StationID {ID='1'geoPoint='GeographicPoint{latitude=41.1234, longitude=2.5678}}";
        assertEquals(expectedString, station.toString(), "El método toString no devuelve el formato esperado.");
    }
}
