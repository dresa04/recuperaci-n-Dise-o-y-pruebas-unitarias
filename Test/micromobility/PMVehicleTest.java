package micromobility;

import data.GeographicPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Exceptions.ProceduralException;

import static org.junit.jupiter.api.Assertions.*;

class PMVehicleTest {

    private PMVehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new PMVehicle(101, new GeographicPoint(41.123F, 1.456F), PMVState.Available);
    }

    @Test
    void testInitialState() {
        assertEquals(PMVState.Available, vehicle.getState(), "El estado inicial debe ser Available");
    }

    @Test
    void testSetNotAvailb() throws ProceduralException {
        vehicle.setNotAvailb();
        assertEquals(PMVState.NotAvailable, vehicle.getState(), "El estado debe cambiar a NotAvailable");
    }

    @Test
    void testSetNotAvailbAlreadyNotAvailable() throws ProceduralException {
        vehicle.setNotAvailb();
        ProceduralException exception = assertThrows(ProceduralException.class, vehicle::setNotAvailb);
        assertEquals("El vehículo ya está en estado NotAvailable.", exception.getMessage());
    }

    @Test
    void testSetUnderWay() throws ProceduralException {
        vehicle.setUnderWay();
        assertEquals(PMVState.UnderWay, vehicle.getState(), "El estado debe cambiar a UnderWay");
    }

    @Test
    void testSetUnderWayWhenNotAvailable() throws ProceduralException {
        vehicle.setNotAvailb();
        ProceduralException exception = assertThrows(ProceduralException.class, vehicle::setUnderWay);
        assertEquals("No se puede iniciar un trayecto si el vehículo no está disponible.", exception.getMessage());
    }

    @Test
    void testSetAvailb() throws ProceduralException {
        vehicle.setNotAvailb();
        vehicle.setAvailb();
        assertEquals(PMVState.Available, vehicle.getState(), "El estado debe cambiar a Available");
    }

    @Test
    void testSetAvailbWhenAlreadyAvailable() {
        ProceduralException exception = assertThrows(ProceduralException.class, vehicle::setAvailb);
        assertEquals("El vehículo ya está en estado Available.", exception.getMessage());
    }

    @Test
    void testSetAvailbWhenUnderWay() throws ProceduralException {
        vehicle.setUnderWay();
        ProceduralException exception = assertThrows(ProceduralException.class, vehicle::setAvailb);
        assertEquals("No se puede cambiar a Available mientras el vehículo está en movimiento.", exception.getMessage());
    }

    @Test
    void testSetLocation() {
        GeographicPoint newLocation = new GeographicPoint(40.7128F, (float) -74.0060);
        vehicle.setLocation(newLocation);
        assertEquals(newLocation, vehicle.getLocation(), "La ubicación debe actualizarse correctamente");
    }

    @Test
    void testSetLocationNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicle.setLocation(null));
        assertEquals("La ubicación no puede ser nula.", exception.getMessage());
    }
}
