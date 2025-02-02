package micromobility;

import data.*;
import data.UserAccount;
import data.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Exceptions.*;
import services.Server;
import services.ServerInterface;
import services.smartfeatures.Interfaces.QRDecoderInterface;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerTest {

    private JourneyRealizeHandler journeyHandler;
    private PMVehicle vehicle;
    private VehicleIDInterface vehicleID;
    private BufferedImage validQR;

    @BeforeEach
    void setUp() {
        // Crear datos reales para las pruebas
        GeographicPointInterface initialLocation = new GeographicPoint(41.616F, 0.622F);
        UserAccountInterface user = new UserAccount("1", "2", "example@gmail.com", "123456", 0);

        // Crear una estación y un vehículo
        StationIDInterface station = new StationID(1, initialLocation);
        vehicleID = new VehicleID(123, station);
        vehicle = new PMVehicle(123, initialLocation, PMVState.Available);

        // Crear el QRDecoder con el VehicleID
        QRDecoderInterface qrDecoder = new QRDecoder(vehicleID);

        // Crear un servidor con datos de prueba
        Map<VehicleIDInterface, Boolean> vehicleAvailability = new HashMap<>();
        Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations = new HashMap<>();
        Map<VehicleIDInterface, StationIDInterface> vehicleStations = new HashMap<>();
        Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords = new HashMap<>();

        vehicleAvailability.put(vehicleID, true);
        vehicleLocations.put(vehicleID, initialLocation);
        vehicleStations.put(vehicleID, station);

        ServerInterface server = new Server(vehicleAvailability, vehicleLocations, vehicleStations, userJourneyRecords);
        JourneyServiceInterface jS = new JourneyService(vehicle);

        // Inicializar JourneyRealizeHandler
        journeyHandler = new JourneyRealizeHandler (initialLocation, user, qrDecoder, server, station, vehicle, jS);

        // Crear una imagen QR de prueba
        validQR = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    void testBroadcastStationID_Success() throws ConnectException {
        // Probar si se actualiza correctamente la estación
        StationID newStation = new StationID(2, new GeographicPoint(41.7F, 0.63F));
        journeyHandler.broadcastStationID(newStation);

        assertEquals(newStation.getgeoPoint(), journeyHandler.getStation(), "La estación debería actualizarse correctamente.");
    }

    @Test
    void testBroadcastStationID_ThrowsExceptionWhenNull() {
        // Verificar que lanza excepción cuando la estación es nula
        ConnectException exception = assertThrows(ConnectException.class, () -> journeyHandler.broadcastStationID(null));
        assertEquals("Error: No se recibió un ID de estación válido.", exception.getMessage());
    }

    @Test
    void testScanQR_Success() {
        // Asignar la imagen QR válida
        journeyHandler.setQrImage(validQR);

        assertDoesNotThrow(() -> journeyHandler.scanQR(), "El escaneo de QR debería funcionar sin excepciones.");
    }

    @Test
    void testScanQR_ThrowsProceduralExceptionWhenQRImageIsNull() {
        ProceduralException exception = assertThrows(ProceduralException.class, () -> journeyHandler.scanQR());
        assertEquals("La imagen del QR no existe.", exception.getMessage());
    }

    @Test
    void testStartDriving_Success() {
        assertDoesNotThrow(() -> {
            journeyHandler.setQrImage(validQR);
            journeyHandler.scanQR();
            journeyHandler.startDriving();
        });

        assertTrue(journeyHandler.inProgress);
    }

    @Test
    void testStartDriving_ThrowsConnectExceptionWhenNoBluetooth() {
        ConnectException exception = assertThrows(ConnectException.class, () -> journeyHandler.startDriving());
        assertEquals("La conexión Bluetooth no está establecida correctamente.", exception.getMessage());
    }

    @Test
    void testStopDriving_Success() throws ConnectException, ProceduralException, QRImgException, InvalidPairingArgsException, PMVNotAvailException {
        journeyHandler.setQrImage(validQR);
        journeyHandler.scanQR();
        journeyHandler.startDriving();
        journeyHandler.stopDriving();

        assertFalse(journeyHandler.inProgress);
    }

    @Test
    void testStopDriving_ThrowsProceduralExceptionWhenNotInProgress() {
        ProceduralException exception = assertThrows(ProceduralException.class, () -> journeyHandler.stopDriving());
        assertEquals("El viaje no está en progreso.", exception.getMessage());
    }


    @Test
    void testUnPairVehicle_ThrowsPairingNotFoundExceptionWhenNotPaired() {
        PairingNotFoundException exception = assertThrows(PairingNotFoundException.class, () -> journeyHandler.unPairVehicle());
        assertEquals("No hay vehículo emparejado o el viaje no está en progreso.", exception.getMessage());
    }
}
