package services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import data.*;
import services.*;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

class ServerTest {

    @Test
    void testCheckPMVAvail_vehicleAvailable() {
        // Arrange
        Server server = new Server();
        VehicleID vehicleID = new VehicleID(2, new StationID("station1")); // ID par, se supone que está disponible.

        // Act & Assert
        assertDoesNotThrow(() -> server.checkPMVAvail(vehicleID), "No debe haber excepciones si el vehículo está disponible.");
    }

    @Test
    void testCheckPMVAvail_vehicleNotAvailable() {
        // Arrange
        Server server = new Server();
        VehicleID vehicleID = new VehicleID(3, new StationID("station1")); // ID impar, se supone que no está disponible.

        // Act & Assert
        PMVNotAvailException exception = assertThrows(PMVNotAvailException.class, () -> server.checkPMVAvail(vehicleID));
        assertEquals("El vehículo está vinculado con otro usuario.", exception.getMessage());
    }

    @Test
    void testRegisterPairing_validArguments() {
        // Arrange
        Server server = new Server();
        UserAccount user = new UserAccount("user123", "testUser", "test@example.com", "password123", 100);
        VehicleID vehicleID = new VehicleID(2, new StationID("station1"));
        StationID stationID = new StationID("station1");
        GeographicPoint location = new GeographicPoint(40.7128F, (float) -74.0060);
        LocalDateTime date = LocalDateTime.now();

        // Act & Assert
        assertDoesNotThrow(() -> server.registerPairing(user, vehicleID, stationID, location, date),
                "No debe haber excepciones con argumentos válidos.");
    }

    @Test
    void testRegisterPairing_invalidArguments() {
        // Arrange
        Server server = new Server();
        UserAccount user = new UserAccount("user123", "testUser", "test@example.com", "password123", 100);
        VehicleID vehicleID = null;  // Argumento inválido
        StationID stationID = new StationID("station1");
        GeographicPoint location = new GeographicPoint(40.7128F, (float) -74.0060);
        LocalDateTime date = LocalDateTime.now();

        // Act & Assert
        InvalidPairingArgsException exception = assertThrows(InvalidPairingArgsException.class,
                () -> server.registerPairing(user, vehicleID, stationID, location, date));
        assertEquals("Algún argumento de emparejamiento es inválido.", exception.getMessage());
    }

    @Test
    void testStopPairing_validArguments() {
        // Arrange
        Server server = new Server();
        UserAccount user = new UserAccount("user123", "testUser", "test@example.com", "password123", 100);
        VehicleID vehicleID = new VehicleID(2, new StationID("station1"));
        StationID stationID = new StationID("station1");
        GeographicPoint location = new GeographicPoint(40.7128F, (float) -74.0060);
        LocalDateTime date = LocalDateTime.now();
        float avSp = 15.5f;
        float dist = 10.5f;
        int dur = 30;
        BigDecimal imp = new BigDecimal("5.75");

        // Act & Assert
        assertDoesNotThrow(() -> server.stopPairing(user, vehicleID, stationID, location, date, avSp, dist, dur, imp),
                "No debe haber excepciones con argumentos válidos.");
    }

    @Test
    void testStopPairing_invalidArguments() {
        // Arrange
        Server server = new Server();
        UserAccount user = null;  // Argumento inválido
        VehicleID vehicleID = new VehicleID(2, new StationID("station1"));
        StationID stationID = new StationID("station1");
        GeographicPoint location = new GeographicPoint(40.7128F, (float) -74.0060);
        LocalDateTime date = LocalDateTime.now();
        float avSp = 15.5f;
        float dist = 10.5f;
        int dur = 30;
        BigDecimal imp = new BigDecimal("5.75");

        // Act & Assert
        InvalidPairingArgsException exception = assertThrows(InvalidPairingArgsException.class,
                () -> server.stopPairing(user, vehicleID, stationID, location, date, avSp, dist, dur, imp));
        assertEquals("Argumentos inválidos para finalizar el emparejamiento.", exception.getMessage());
    }

    @Test
    void testSetPairing() {
        // Arrange
        Server server = new Server();
        UserAccount user = new UserAccount("user123", "testUser", "test@example.com", "password123", 100);
        VehicleID vehicleID = new VehicleID(2, new StationID("station1"));
        StationID stationID = new StationID("station1");
        GeographicPoint location = new GeographicPoint(40.7128F, (float) -74.0060);
        LocalDateTime date = LocalDateTime.now();

        // Act & Assert
        assertDoesNotThrow(() -> server.setPairing(user, vehicleID, stationID, location, date),
                "No debe haber excepciones al registrar el emparejamiento.");
    }

    @Test
    void testUnPairRegisterService_pairingExists() {
        // Arrange
        Server server = new Server();
        JourneyService service = new JourneyService();

        // Act & Assert
        assertDoesNotThrow(() -> server.unPairRegisterService(service),
                "No debe haber excepciones si el emparejamiento existe.");
    }

    @Test
    void testUnPairRegisterService_pairingNotFound() {
        // Arrange
        Server server = new Server();
        JourneyService service = null;  // Simula que el servicio no existe.

        // Act & Assert
        PairingNotFoundException exception = assertThrows(PairingNotFoundException.class,
                () -> server.unPairRegisterService(service));
        assertEquals("No se encontró el emparejamiento.", exception.getMessage());
    }

    @Test
    void testRegisterLocation() {
        // Arrange
        Server server = new Server();
        VehicleID vehicleID = new VehicleID(2, new StationID("station1"));
        StationID stationID = new StationID("station1");

        // Act & Assert
        assertDoesNotThrow(() -> server.registerLocation(vehicleID, stationID),
                "No debe haber excepciones al registrar la ubicación.");
    }
}
