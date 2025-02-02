package services;

import data.StationID;
import data.GeographicPoint; // Asegúrate de importar la clase GeographicPoint
import data.UserAccount;
import data.VehicleID; // Asegúrate de que esta clase implemente VehicleIDInterface
import micromobility.*;
import org.junit.jupiter.api.Test;
import services.smartfeatures.QRDecoder;
import services.smartfeatures.UnbondedBTSignal;
import services.ServerInterface;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import data.interfaces.*;

import static org.junit.jupiter.api.Assertions.*;

class UnbondedBTSignalTest {

    @Test
    void testBTbroadcast() throws InterruptedException {
        // Crear una instancia de GeographicPoint (usando latitud y longitud como ejemplo)
        GeographicPoint geoPoint = new GeographicPoint(41.616F, 0.622F); // Ajusta los valores según lo que necesites

        // Crear una instancia de StationID con un ID de estación y el punto geográfico
        StationID station = new StationID(1, geoPoint);

        // Crear una lista de historial de transmisiones
        List<String> broadcastHistory = new ArrayList<>();

        // Crear la instancia de VehicleID pasando tanto el int como la instancia de StationID
        VehicleID vehicleID = new VehicleID(123, station); // Pasar tanto el ID del vehículo como la estación

        // Crear la instancia de QRDecoder con el VehicleID
        QRDecoder qrDecoder = new QRDecoder(vehicleID); // Pasar la instancia de VehicleID

        // Crear los mapas necesarios para la instancia de Server
        Map<VehicleIDInterface, Boolean> vehicleAvailability = new HashMap<>();
        Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations = new HashMap<>();
        Map<VehicleIDInterface, StationIDInterface> vehicleStation = new HashMap<>();
        Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords = new HashMap<>();

        // Simulamos que el vehículo está registrado y disponible
        vehicleAvailability.put(vehicleID, true);
        vehicleLocations.put(vehicleID, geoPoint);
        vehicleStation.put(vehicleID, station);

        // Crear la instancia de Server usando el constructor
        ServerInterface server = new Server(vehicleAvailability, vehicleLocations, vehicleStation, userJourneyRecords);

        // Crear la instancia de JourneyRealizeHandler con los parámetros necesarios
// Crear una ubicación inicial
        GeographicPointInterface initialLocation = new GeographicPoint(41.616F, 0.622F);

// Crear un usuario de prueba (Mock o Dummy)
        UserAccountInterface user = new UserAccount("1", "one", "random@hotmail.com", "patata", 0);

// Crear un vehículo de prueba
        PMVehicle vehicle = new PMVehicle(vehicleID.getId(), station.getgeoPoint(), PMVState.Available);
        JourneyServiceInterface jS = new JourneyService(vehicle);
// Ahora sí, crear el JourneyRealizeHandler con todos los parámetros
        JourneyRealizeHandler journeyHandler = new JourneyRealizeHandler(
                initialLocation, // location
                user,            // user
                qrDecoder,       // qrDecoder
                server,          // server
                station,         // initialStation
                vehicle,          // vehicle
                jS
        );

        // Crear la instancia de UnbondedBTSignal con la station, broadcastHistory y journeyHandler
        UnbondedBTSignal btSignal = new UnbondedBTSignal(journeyHandler, station, broadcastHistory);

        // Iniciar el hilo de emisión
        Thread broadcastThread = new Thread(() -> {
            try {
                btSignal.BTbroadcast();
            } catch (ConnectException | InterruptedException e) {
                fail("ConnectException should not occur during normal execution");
            }
        });

        broadcastThread.start();

        // Dejar correr el hilo por 3 segundos para probar
        Thread.sleep(3000);

        // Interrumpir el hilo
        broadcastThread.interrupt();

        // Esperar que el hilo termine
        broadcastThread.join();

        // Verificar que el hilo se haya detenido correctamente
        assertFalse(broadcastThread.isAlive(), "The broadcast thread should be stopped.");

        // Verificar que se haya agregado al menos un mensaje al historial
        assertFalse(broadcastHistory.isEmpty(), "Broadcast history should not be empty.");
        assertTrue(broadcastHistory.size() >= 2, "At least two messages should have been broadcasted.");
        assertEquals("Broadcasting station ID: 1", broadcastHistory.get(0), "The first broadcast message should be correct.");
    }

    @Test
    void testInitializationWithNullStation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UnbondedBTSignal(null, null, null),
                "Should throw IllegalArgumentException when the station is null.");
        assertEquals("Station cannot be null", exception.getMessage());
    }

    @Test
    void testInitializationWithNullBroadcastHistory() {
        // Crear una instancia de GeographicPoint (usando latitud y longitud como ejemplo)
        GeographicPoint geoPoint = new GeographicPoint(41.616F, 0.622F); // Ajusta según lo necesario

        // Crear una instancia de StationID con un ID de estación y el punto geográfico
        StationID station = new StationID(1, geoPoint);

        // Crear una instancia de VehicleID
        VehicleID vehicleID = new VehicleID(123, station);

        // Crear la instancia de QRDecoder con el VehicleID
        QRDecoder qrDecoder = new QRDecoder(vehicleID);

        // Crear los mapas necesarios para la instancia de Server
        Map<VehicleIDInterface, Boolean> vehicleAvailability = new HashMap<>();
        Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations = new HashMap<>();
        Map<VehicleIDInterface, StationIDInterface> vehicleStation = new HashMap<>();
        Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords = new HashMap<>();

        // Simulamos que el vehículo está registrado y disponible
        vehicleAvailability.put(vehicleID, true);
        vehicleLocations.put(vehicleID, geoPoint);
        vehicleStation.put(vehicleID, station);

        // Crear la instancia de Server
        ServerInterface server = new Server(vehicleAvailability, vehicleLocations, vehicleStation, userJourneyRecords);

        // Crear la instancia de JourneyRealizeHandler
        GeographicPointInterface initialLocation = new GeographicPoint(41.616F, 0.622F);

// Crear un usuario de prueba (Mock o Dummy)
        UserAccountInterface user = new UserAccount("1", "one", "random@hotmail.com", "patata", 0);

// Crear un vehículo de prueba
        PMVehicle vehicle = new PMVehicle(vehicleID.getId(), station.getgeoPoint(), PMVState.Available);

        JourneyServiceInterface jS = new JourneyService(vehicle);

// Ahora sí, crear el JourneyRealizeHandler con todos los parámetros
        JourneyRealizeHandler journeyHandler = new JourneyRealizeHandler(
                initialLocation, // location
                user,            // user
                qrDecoder,       // qrDecoder
                server,          // server
                station,         // initialStation
                vehicle,          // vehicle
                jS
        );
        // Verificar que se lanza la excepción correctamente si el broadcastHistory es null
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new UnbondedBTSignal(journeyHandler,station, null),
                "Should throw IllegalArgumentException when the broadcast history is null.");
        assertEquals("Broadcast history cannot be null", exception.getMessage());
    }

}
