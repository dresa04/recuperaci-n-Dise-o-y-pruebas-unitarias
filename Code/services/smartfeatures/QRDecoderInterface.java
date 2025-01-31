package services.smartfeatures;

import data.VehicleID;
import services.Exceptions.QRImgException;

import java.awt.image.BufferedImage;

/**
 * Interfaz para la decodificación de códigos QR.
 */
public interface QRDecoderInterface {
    VehicleID getVehicleID(BufferedImage QRImg) throws QRImgException, QRImgException;
}
