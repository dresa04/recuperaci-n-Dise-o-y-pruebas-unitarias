package services.smartfeatures;

import data.interfaces.StationIDInterface;
import micromobility.JourneyRealizeHandler;
import services.smartfeatures.Interfaces.UnbondedBTSignalInterface;

import java.net.ConnectException;
import java.util.List;

/**
 * Implementación de la interfaz UnbondedBTSignalInterface.
 * Esta clase gestiona la transmisión repetida del ID de estación a través de Bluetooth.
 */
public class UnbondedBTSignal implements UnbondedBTSignalInterface {
    private final JourneyRealizeHandler journeyHandler; // Manejador de viajes para interactuar con la estación
    private final StationIDInterface station; // Estación a la que se le enviará el ID
    private final List<String> broadcastHistory; // Historial de mensajes de difusión

    /**
     * Constructor de la clase UnbondedBTSignal.
     * Inicializa el manejador de viajes, la estación y el historial de difusión.
     *
     * @param journeyHandler El manejador de viajes que gestiona la difusión del ID de la estación.
     * @param station La estación que se va a transmitir.
     * @param broadcastHistory Historial donde se almacenarán los mensajes de difusión.
     * @throws IllegalArgumentException Si la estación o el historial de difusión son nulos.
     */
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

    /**
     * Difunde repetidamente el ID de la estación a través de Bluetooth.
     * El proceso se interrumpe si se recibe una señal de interrupción o si ocurre un error de conexión.
     *
     * @throws ConnectException Si hay un error de conexión Bluetooth.
     */
    @Override
    public void BTbroadcast() throws ConnectException, InterruptedException {
        while (true) {
            // Llama al método broadcastStationID() del JourneyRealizeHandler
            // para transmitir el ID de la estación a través de Bluetooth
            journeyHandler.broadcastStationID(station);

            // Mensaje de depuración y registro en el historial de difusión
            String message = "Broadcasting station ID: " + station.getID();
            broadcastHistory.add(message);
            System.out.println(message);

            // Espera de 1 segundo antes de realizar la siguiente transmisión
            Thread.sleep(1000);
        }
    }
}
