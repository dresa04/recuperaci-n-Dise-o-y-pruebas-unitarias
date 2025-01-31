package services;

import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Exceptions.QRImgException;
import services.smartfeatures.QRDecoder;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class QRDecoderTest {

    private QRDecoder qrDecoder;

    @BeforeEach
    void setUp() {
        qrDecoder = new QRDecoder();
    }

    @Test
    void testGetVehicleID_ValidImage() {
        // Cargar una imagen de prueba válida
        BufferedImage validImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        assertDoesNotThrow(() -> {
            VehicleID vehicleID = qrDecoder.getVehicleID(validImg);
            assertNotNull(vehicleID, "El VehicleID no debería ser nulo.");
        });
    }

    @Test
    void testGetVehicleID_NullImage() {
        assertThrows(QRImgException.class, () -> {
            qrDecoder.getVehicleID(null);
        }, "Debe lanzar QRImgException si la imagen es nula.");
    }

    @Test
    void testGetVehicleID_EmptyImage() {
        BufferedImage emptyImg = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);

        QRImgException exception = assertThrows(QRImgException.class, () -> {
            qrDecoder.getVehicleID(emptyImg);
        });

        assertEquals("La imagen está vacía o corrupta.", exception.getMessage());
    }

    @Test
    void testGetVehicleID_CorruptImage() {
        // Simular una imagen que provoca un error en la decodificación
        BufferedImage corruptedImg = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB) {
            @Override
            public int getWidth() {
                throw new RuntimeException("Error interno de la imagen");
            }
        };

        QRImgException exception = assertThrows(QRImgException.class, () -> {
            qrDecoder.getVehicleID(corruptedImg);
        });

        assertEquals("Fallo inesperado en la decodificación de QR.", exception.getMessage());
    }
}
