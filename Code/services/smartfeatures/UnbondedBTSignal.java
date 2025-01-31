package services.smartfeatures;

import services.Exceptions.BTConnectionException;

import java.util.concurrent.TimeUnit;

/**
 * Implementación del canal de comunicación Bluetooth sin emparejar para transmitir el ID
 * de la estación.
 */
public class UnbondedBTSignal implements UnbondedBTSignalInterface {

    private final JourneyRealizeHandler journeyRealizeHandler;

    public UnbondedBTSignal(JourneyRealizeHandler journeyRealizeHandler) {
        this.journeyRealizeHandler = journeyRealizeHandler;
    }

    @Override
    public void BTbroadcast() throws BTConnectionException {
        try {
            while (true) {
                // Llamar al método broadcast de la clase JourneyRealizeHandler
                journeyRealizeHandler.broadcastStationID();

                // Simular un intervalo de tiempo antes de la siguiente transmisión
                TimeUnit.SECONDS.sleep(5); // Espera 5 segundos (puedes ajustar este intervalo)
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BTConnectionException("Error de conexión Bluetooth al emitir el ID de estación.", e);
        } catch (RuntimeException e) {
            throw new BTConnectionException("Fallo inesperado en la transmisión Bluetooth.", e);
        }
    }
}
