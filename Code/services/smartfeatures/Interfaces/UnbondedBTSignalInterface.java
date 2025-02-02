package services.smartfeatures.Interfaces;

import services.Exceptions.BTConnectionException;

import java.net.ConnectException;

/**
 * Representa el canal de comunicación Bluetooth sin emparejar, usado para descubrir
 * las estaciones de vehículos. Transmite de manera continua el ID de la estación.
 */
public interface UnbondedBTSignalInterface {

    /**
     * Método encargado de transmitir el ID de la estación de manera continua por Bluetooth.
     *
     * @throws ConnectException Si ocurre un error en la conexión Bluetooth.
     */
    void BTbroadcast() throws ConnectException, BTConnectionException, InterruptedException;
}
