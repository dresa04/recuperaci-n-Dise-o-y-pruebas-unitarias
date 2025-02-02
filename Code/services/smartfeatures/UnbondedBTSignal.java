package services.smartfeatures;

import data.interfaces.StationIDInterface;
import micromobility.JourneyRealizeHandler;
import data.StationID;
import services.smartfeatures.Interfaces.UnbondedBTSignalInterface;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class UnbondedBTSignal implements UnbondedBTSignalInterface {
    private final JourneyRealizeHandler journeyHandler;
    private final StationIDInterface station;
    private final List<String> broadcastHistory;

    // ✅ Constructor modificado para recibir `JourneyRealizeHandler` y `StationID`
    public UnbondedBTSignal(JourneyRealizeHandler journeyHandler, StationIDInterface station, List<String> broadcastHistory) {
        this.journeyHandler = journeyHandler;
        if (station == null) {
            throw new IllegalArgumentException("Station cannot be null");
        }
        if (broadcastHistory == null) {
            throw new IllegalArgumentException("Broadcast history cannot be null");
        }
        this.station = station;
        this.broadcastHistory = broadcastHistory;
    }

    @Override
    public void BTbroadcast() throws ConnectException {
        while (true) {
            try {
                // ✅ Llamada a `broadcastStationID()`
                journeyHandler.broadcastStationID(station);

                // ✅ Mensaje de depuración y registro
                String message = "Broadcasting station ID: " + station.getID();
                broadcastHistory.add(message);
                System.out.println(message);

                // ✅ Espera de 1 segundo antes de la siguiente transmisión
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción del hilo
                break; // Salir del bucle si se interrumpe
            } catch (ConnectException e) {
                throw new ConnectException("Error de conexión Bluetooth al transmitir el ID de la estación.");
            }
        }
    }
}
