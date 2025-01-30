package data;

import data.Exceptions.InvalidStationIDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationIDTest {

    private StationID station1;
    private StationID station2;

    @BeforeEach
    void setUp() {
        station1 = new StationID("ST123456");
        station2 = new StationID("ST654321");
    }

    @Test
    void testConstructor_ValidID() {
        assertNotNull(station1);
        assertEquals("ST123456", station1.getId());
    }

    @Test
    void testConstructor_NullID() {
        Exception exception = assertThrows(InvalidStationIDException.class, () ->
                new StationID(null)
        );
        assertEquals("El identificador de estación no puede ser null o vacío.", exception.getMessage());
    }

    @Test
    void testConstructor_EmptyID() {
        Exception exception = assertThrows(InvalidStationIDException.class, () ->
                new StationID("")
        );
        assertEquals("El identificador de estación no puede ser null o vacío.", exception.getMessage());
    }

    @Test
    void testConstructor_InvalidFormat() {
        Exception exception = assertThrows(InvalidStationIDException.class, () ->
                new StationID("123456ST")
        );
        assertEquals("El identificador de estación debe seguir el formato STXXXXXX.", exception.getMessage());
    }

    @Test
    void testEquals_SameID() {
        StationID stationDuplicate = new StationID("ST123456");
        assertEquals(station1, stationDuplicate);
    }

    @Test
    void testEquals_DifferentID() {
        assertNotEquals(station1, station2);
    }

    @Test
    void testHashCode() {
        StationID stationDuplicate = new StationID("ST123456");
        assertEquals(station1.hashCode(), stationDuplicate.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("StationID{id='ST123456'}", station1.toString());
    }
}
