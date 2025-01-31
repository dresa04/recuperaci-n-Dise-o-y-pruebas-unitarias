package services.smartfeatures;

import data.StationID;
import data.VehicleID;
import services.Exceptions.QRImgException;
import services.smartfeatures.Interfaces.QRDecoderInterface;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Implementación del decodificador de códigos QR.
 */
public class QRDecoder implements QRDecoderInterface {

    @Override
    public VehicleID getVehicleID(BufferedImage QRImg) throws QRImgException {
        if (QRImg == null) {
            throw new QRImgException("La imagen proporcionada es nula.");
        }

        // Simulación de análisis de la imagen (en un caso real se usaría una librería como ZXing)
        try {
            int width = QRImg.getWidth();
            int height = QRImg.getHeight();

            if (width == 0 || height == 0) {
                throw new QRImgException("La imagen está vacía o corrupta.");
            }

            // Simulación de extracción de ID a partir de la imagen QR
            int fakeID = new Random().nextInt(1000); // Simulación de un ID obtenido
            return new VehicleID(fakeID, new StationID(1)); // Suponiendo que StationID acepta un entero

        } catch (RuntimeException e) {
            throw new QRImgException("Fallo inesperado en la decodificación de QR.");
        }
    }
}
