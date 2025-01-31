package services.smartfeatures;

import services.Exceptions.PMVPhisicalException;
import services.Exceptions.ProceduralException;

import java.net.ConnectException;

/**
 * Interfaz para el control de los microcontroladores de los vehículos.
 */
public interface ArduinoMicroControllerInterface {
    void setBTconnection() throws ConnectException;

    void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException;

    void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException;

    void undoBTconnection();
}
