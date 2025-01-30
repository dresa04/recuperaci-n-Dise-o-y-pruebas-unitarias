package data;


import data.Exceptions.InvalidVehicleIDException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    private VehicleID vehicle1;
    private VehicleID vehicle2;
    private StationID station1;
    private StationID station2;

    @BeforeEach
    void setUp() {
        station1 = new StationID("ST123456");
        station2 = new StationID("ST654321");
        vehicle1 = new VehicleID(100, station1);
        vehicle2 = new VehicleID(200, station2);
    }

    @Test
    void testConstructor_ValidVehicle() {
        assertNotNull(vehicle1);
        assertEquals(100, vehicle1.getId());
        assertEquals(station1, vehicle1.getStation());
    }

    @Test
    void testConstructor_InvalidID() {
        Exception exception = assertThrows(InvalidVehicleIDException.class, () ->
                new VehicleID(-1, station1)
        );
        assertEquals("El ID del vehículo debe ser un número positivo.", exception.getMessage());
    }

    @Test
    void testConstructor_NullStation() {
        Exception exception = assertThrows(InvalidVehicleIDException.class, () ->
                new VehicleID(100, null)
        );
        assertEquals("La estación asociada no puede ser null.", exception.getMessage());
    }

    @Test
    void testEquals_SameIDAndStation() {
        VehicleID vehicle3 = new VehicleID(100, station1);
        assertEquals(vehicle1, vehicle3);
    }

    @Test
    void testEquals_DifferentVehicle() {
        assertNotEquals(vehicle1, vehicle2);
    }

    @Test
    void testHashCode() {
        VehicleID vehicle3 = new VehicleID(100, station1);
        assertEquals(vehicle1.hashCode(), vehicle3.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("VehicleID{id=100, station=StationID{id='ST123456'}}", vehicle1.toString());
    }
}
