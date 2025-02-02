package services.smartfeatures.Interfaces;

import data.VehicleID;
import data.interfaces.VehicleIDInterface;
import services.Exceptions.QRImgException;

import java.awt.image.BufferedImage;


public interface QRDecoderInterface {
    VehicleIDInterface getVehicleID(BufferedImage QRImg) throws QRImgException;
}
