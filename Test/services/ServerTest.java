package services;

import data.*;
import micromobility.JourneyService;
import micromobility.JourneyServiceInterface;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import data.interfaces.UserAccountInterface;
import data.interfaces.VehicleIDInterface;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    private Server server;
    private UserAccountInterface user;
    private VehicleIDInterface vehicle;
    private StationIDInterface station;
    private GeographicPointInterface location;
    private JourneyServiceInterface journey;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        final Map<VehicleIDInterface, Boolean> vehicleAvailability = new HashMap<>();
        final Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations = new HashMap<>();
        final Map<VehicleIDInterface, StationIDInterface> vehicleStation = new HashMap<>();
        final Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords = new HashMap<>();
        server = new Server(vehicleAvailability, vehicleLocations, vehicleStation, userJourneyRecords);

        // Instanciamos los objetos a través de sus interfaces
        user = new UserAccount("user1", "User One", "user1@example.com", "password", 100); // Debe ser compatible con UserAccountInterface
        vehicle = new VehicleID(1, new StationID(1, new GeographicPoint(1.0F, 2.0F))); // Debe ser compatible con VehicleIDInterface
        station = new StationID(1, new GeographicPoint(1.0F, 2.0F)); // Debe ser compatible con StationIDInterface
        location = new GeographicPoint(1.0F, 2.0F); // Debe ser compatible con GeographicPointInterface
        GeographicPoint location = new GeographicPoint(40.7128F, (float) -74.0060); // Coordenadas de ejemplo (latitud, longitud)
        PMVehicle pmVehicle = new PMVehicle(123, location, PMVState.Available);
        journey= new JourneyService(pmVehicle);
        date = LocalDateTime.now();

        // Añadimos el vehículo al servidor con disponibilidad
        server.setPairing(user, vehicle, station, location, date, journey);
    }

    @Test
    void testCheckPMVAvail_VehicleNotFound_ThrowsConnectException() {
        VehicleIDInterface unregisteredVehicle = new VehicleID(999, new StationID(2, new GeographicPoint(3.0F, 4.0F)));

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            server.checkPMVAvail(unregisteredVehicle);
        });
        assertEquals("Connection failed: Vehicle ID not found in the server.", exception.getMessage());
    }

    @Test
    void testCheckPMVAvail_VehicleNotAvailable_ThrowsPMVNotAvailException() throws ConnectException, InvalidPairingArgsException {
        // Finalizamos un pairing y dejamos el vehículo no disponible
        server.stopPairing(user, vehicle, station, location, date, new BigDecimal(20), new BigDecimal(5), 10, new BigDecimal("2.5"), journey);

        assertDoesNotThrow(() -> server.checkPMVAvail(vehicle));
    }

    @Test
    void testRegisterPairing_VehicleNotFound_ThrowsConnectException() {
        VehicleIDInterface unregisteredVehicle = new VehicleID(999, new StationID(2, new GeographicPoint(3.0F, 4.0F)));

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            server.registerPairing(user, unregisteredVehicle, station, location, date, journey);
        });
        assertEquals("El vehículo no está registrado en el servidor.", exception.getMessage());
    }

    @Test
    void testRegisterPairing_StationMismatch_ThrowsInvalidPairingArgsException() {
        StationIDInterface wrongStation = new StationID(2, new GeographicPoint(3.0F, 4.0F));

        InvalidPairingArgsException exception = assertThrows(InvalidPairingArgsException.class, () -> {
            server.registerPairing(user, vehicle, wrongStation, location, date, journey);
        });
        assertEquals("La estación proporcionada no coincide con la estación del vehículo.", exception.getMessage());
    }

    @Test
    void testStopPairing_VehicleNotFound_ThrowsConnectException() {
        VehicleIDInterface unregisteredVehicle = new VehicleID(999, new StationID(2, new GeographicPoint(3.0F, 4.0F)));

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            server.stopPairing(user, unregisteredVehicle, station, location, date, new BigDecimal(20), new BigDecimal(5), 10, new BigDecimal("2.5"), journey);
        });
        assertEquals("El vehículo no está registrado en el servidor.", exception.getMessage());
    }

    @Test
    void testStopPairing_InvalidStation_ThrowsInvalidPairingArgsException() {
        StationIDInterface wrongStation = new StationID(2, new GeographicPoint(3.0F, 4.0F));

        InvalidPairingArgsException exception = assertThrows(InvalidPairingArgsException.class, () -> {
            server.stopPairing(user, vehicle, wrongStation, location, date, new BigDecimal(20), new BigDecimal(5), 10, new BigDecimal("2.5"), journey);
        });
        assertEquals("La estación proporcionada no coincide con la estación del vehículo.", exception.getMessage());
    }

    @Test
    void testStopPairing_Success() throws InvalidPairingArgsException, ConnectException {
        // Registrar pairing y detenerlo
        server.registerPairing(user, vehicle, station, location, date, journey);
        server.stopPairing(user, vehicle, station, location, date, new BigDecimal(20), new BigDecimal(5), 10, new BigDecimal("2.5"), journey);

        // Verificar que no lanza excepciones
        assertDoesNotThrow(() -> server.checkPMVAvail(vehicle));
    }

    @Test
    void testRegisterLocation_VehicleNotFound_ThrowsConnectException() {
        VehicleIDInterface unregisteredVehicle = new VehicleID(999, new StationID(2, new GeographicPoint(3.0F, 4.0F)));

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            server.registerLocation(unregisteredVehicle, station);
        });
        assertEquals("El vehículo no está registrado en el servidor.", exception.getMessage());
    }

    @Test
    void testRegisterLocation_StationMismatch_ThrowsConnectException() {
        StationIDInterface wrongStation = new StationID(2, new GeographicPoint(3.0F, 4.0F));

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            server.registerLocation(vehicle, wrongStation);
        });
        assertEquals("La estación proporcionada no coincide con la estación registrada para el vehículo.", exception.getMessage());
    }


}
