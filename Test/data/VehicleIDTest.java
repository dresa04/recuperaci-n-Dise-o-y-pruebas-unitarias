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
        // Creación de puntos geográficos para las estaciones
        GeographicPoint geoPoint1 = new GeographicPoint(41.123f, 2.345f);
        GeographicPoint geoPoint2 = new GeographicPoint(40.567f, 3.456f);

        station1 = new StationID(1, geoPoint1);
        station2 = new StationID(2, geoPoint2);
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
        assertEquals(vehicle1, vehicle3, "Dos vehículos con el mismo ID y estación deberían ser iguales.");
    }

    @Test
    void testEquals_DifferentID() {
        VehicleID vehicle3 = new VehicleID(300, station1);
        assertNotEquals(vehicle1, vehicle3, "Vehículos con diferente ID no deberían ser iguales.");
    }

    @Test
    void testEquals_DifferentStation() {
        VehicleID vehicle3 = new VehicleID(100, station2);
        assertNotEquals(vehicle1, vehicle3, "Vehículos con la misma ID pero distinta estación no deberían ser iguales.");
    }

    @Test
    void testHashCode() {
        VehicleID vehicle3 = new VehicleID(100, station1);
        assertEquals(vehicle1.hashCode(), vehicle3.hashCode(), "El hashCode debería ser el mismo para objetos iguales.");
    }

    @Test
    void testToString() {
        String expectedString = "VehicleID{id=100, station=StationID {ID='1'geoPoint='GeographicPoint{latitude=41.123, longitude=2.345}}}";
        assertEquals(expectedString, vehicle1.toString(), "El método toString no devuelve el formato esperado.");
    }
}
