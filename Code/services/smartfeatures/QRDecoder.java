package services.smartfeatures;

import services.Exceptions.QRImgException;
import data.interfaces.VehicleIDInterface;
import services.smartfeatures.Interfaces.QRDecoderInterface;

import java.awt.image.BufferedImage;

/**
 * Implementación del decodificador de códigos QR.
 */
public class QRDecoder implements QRDecoderInterface {

    private final VehicleIDInterface vehicle;

    // Constructor que recibe el vehículo que debe devolver
    public QRDecoder(VehicleIDInterface vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        this.vehicle = vehicle;
    }
    @Override
    public VehicleIDInterface getVehicleID(BufferedImage QRImg) throws QRImgException {
        if (QRImg == null) {
            throw new QRImgException("The QR image is null or corrupted.");
        }

        // Devuelve el vehículo configurado en la inicialización
        return vehicle;
    }
}
