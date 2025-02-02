package micromobility;

import data.interfaces.*;

import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.QRImgException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.ProceduralException;
import services.Exceptions.PairingNotFoundException;

import java.rmi.ConnectException;

public interface JourneyRealizeHandlerInterface {

    // Método para gestionar el escaneo del código QR del vehículo y emparejamiento
    void scanQR() throws ConnectException, InvalidPairingArgsException, QRImgException, PMVNotAvailException;

    // Método para gestionar el inicio del trayecto
    void startDriving() throws ConnectException, ProceduralException;

    // Método para gestionar la detención del vehículo
    void stopDriving() throws ConnectException, ProceduralException;

    // Método para gestionar el desemparejamiento del vehículo al finalizar el trayecto
    void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException;

    // Método para obtener el ID de la estación de VMP a través del canal BT
    void broadcastStationID(StationIDInterface statID) throws ConnectException;

    // Método para obtener el punto de origen del trayecto
    void setOriginPoint(GeographicPointInterface originPoint);

    // Método para establecer el punto de destino del trayecto
    void setEndPoint(GeographicPointInterface endPoint);

    // Método para obtener los datos del viaje de un objeto JourneyService
    JourneyService getJourneyService();

    // Método para actualizar el estado del viaje
    void updateJourneyState(boolean inProgress);
}
