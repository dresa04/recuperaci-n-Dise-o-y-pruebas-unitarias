package services;

import data.VehicleID;
import data.StationID;
import data.GeographicPoint;
import data.interfaces.VehicleIDInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Exceptions.QRImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class QRDecoderTest {

    private QRDecoder qrDecoder;
    private VehicleID vehicle; // Instancia real de VehicleID

    @BeforeEach
    void setUp() {
        // Crear una instancia real de VehicleID
        GeographicPoint geoPoint = new GeographicPoint(41.616F, 0.622F); // Ubicación de prueba
        StationID station = new StationID(1, geoPoint); // Estación de prueba
        vehicle = new VehicleID(123, station); // Vehículo real

        // Inicializamos el QRDecoder con un vehículo real
        qrDecoder = new QRDecoder(vehicle);
    }

    @Test
    void testGetVehicleID_ThrowsExceptionWhenQRImgIsNull() {
        QRImgException exception = assertThrows(QRImgException.class, () -> qrDecoder.getVehicleID(null));
        assertEquals("The QR image is null or corrupted.", exception.getMessage());
    }

    @Test
    void testGetVehicleID_ReturnsVehicleIDWhenQRImgIsValid() {
        BufferedImage validImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        VehicleIDInterface result = assertDoesNotThrow(() -> qrDecoder.getVehicleID(validImg));
        assertNotNull(result, "The returned VehicleID should not be null.");
        assertEquals(vehicle, result, "The returned VehicleID should match the initialized one.");
    }
}
