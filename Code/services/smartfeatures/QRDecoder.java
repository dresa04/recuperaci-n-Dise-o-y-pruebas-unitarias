package services.smartfeatures;

import services.Exceptions.QRImgException;
import data.interfaces.VehicleIDInterface;
import services.smartfeatures.Interfaces.QRDecoderInterface;

import java.awt.image.BufferedImage;

/**
 * Implementación del decodificador de códigos QR.
 * Simula la funcionalidad de un lector de códigos QR que asocia un vehículo a una imagen QR escaneada.
 */
public class QRDecoder implements QRDecoderInterface {

    private final VehicleIDInterface vehicle; // Vehículo asociado al decodificador

    /**
     * Constructor que inicializa el decodificador con un vehículo específico.
     *
     * @param vehicle El vehículo asociado al código QR.
     * @throws IllegalArgumentException Si el vehículo proporcionado es nulo.
     */
    public QRDecoder(VehicleIDInterface vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        this.vehicle = vehicle;
    }

    /**
     * Obtiene el identificador del vehículo a partir de la imagen de un código QR.
     *
     * @param QRImg La imagen del código QR a decodificar.
     * @return El identificador del vehículo asociado.
     * @throws QRImgException Si la imagen del código QR es nula o está dañada.
     */
    @Override
    public VehicleIDInterface getVehicleID(BufferedImage QRImg) throws QRImgException {
        if (QRImg == null) {
            throw new QRImgException("The QR image is null or corrupted.");
        }

        // Retorna el vehículo preconfigurado en la inicialización,
        // ya que en esta implementación no se decodifica realmente la imagen QR.
        return vehicle;
    }
}
