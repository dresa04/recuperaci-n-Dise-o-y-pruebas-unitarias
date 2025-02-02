package services.smartfeatures;

import micromobility.PMVehicle;
import services.Exceptions.PMVPhisicalException;
import services.Exceptions.ProceduralException;
import services.smartfeatures.Interfaces.ArduinoMicroControllerInterface;

import java.net.ConnectException;
import java.util.Random;

/**
 * Implementación de la interfaz ArduinoMicroControllerInterface.
 *
 * Simula el comportamiento de un microcontrolador Arduino encargado de gestionar
 * la conexión Bluetooth con el smartphone y el control del vehículo eléctrico de movilidad personal (PMV).
 */
public class ArduinoMicroController implements ArduinoMicroControllerInterface {
    private boolean btConnected; // Indica si la conexión Bluetooth está activa.
    private boolean isDriving;  // Indica si el vehículo está en movimiento.

    /**
     * Constructor que inicializa el estado del microcontrolador.
     * La conexión Bluetooth está inicialmente desactivada y el vehículo detenido.
     */
    public ArduinoMicroController() {
        this.btConnected = false;
        this.isDriving = false;
    }

    /**
     * Establece la conexión Bluetooth con el smartphone.
     *
     * @throws ConnectException Si no se puede establecer la conexión Bluetooth (simulación de fallo en un 10% de los casos).
     */
    @Override
    public void setBTconnection() throws ConnectException {
        if (btConnected) {
            // Si ya está conectado, no se realiza ninguna acción.
            System.out.println("El canal Bluetooth ya está establecido.");
            return;
        }
        if (new Random().nextInt(100) < 10) { // Simulación de fallo con un 10% de probabilidad
            throw new ConnectException("No se pudo establecer la conexión Bluetooth.");
        }
        btConnected = true;
        System.out.println("Conexión Bluetooth establecida correctamente.");
    }

    /**
     * Inicia el movimiento del vehículo.
     *
     * @throws ConnectException       Si no hay conexión Bluetooth con el smartphone.
     * @throws ProceduralException    Si el vehículo ya está en movimiento.
     * @throws PMVPhisicalException   Si ocurre un fallo mecánico en el arranque (5% de probabilidad).
     */
    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("No hay conexión Bluetooth con el smartphone.");
        }
        if (isDriving) {
            throw new ProceduralException("El vehículo ya está en movimiento.");
        }
        if (new Random().nextInt(100) < 5) { // Simulación de fallo mecánico con un 5% de probabilidad
            throw new PMVPhisicalException("Fallo en el sistema de arranque del vehículo.");
        }

        // Cambia el estado del vehículo a "en marcha" en la clase PMVehicle
        PMVehicle.setUnderWay();

        isDriving = true;
        System.out.println("El vehículo ha comenzado a moverse.");
    }

    /**
     * Detiene el movimiento del vehículo.
     *
     * @throws ConnectException       Si no hay conexión Bluetooth con el smartphone.
     * @throws ProceduralException    Si el vehículo ya está detenido.
     * @throws PMVPhisicalException   Si ocurre un fallo en los frenos (5% de probabilidad).
     */
    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("No hay conexión Bluetooth con el smartphone.");
        }
        if (!isDriving) {
            throw new ProceduralException("El vehículo ya está detenido.");
        }
        if (new Random().nextInt(100) < 5) { // Simulación de fallo en los frenos con un 5% de probabilidad
            throw new PMVPhisicalException("Fallo en el sistema de frenos del vehículo.");
        }
        isDriving = false;
        System.out.println("El vehículo se ha detenido correctamente.");
    }

    /**
     * Finaliza la conexión Bluetooth con el smartphone.
     * Si no hay una conexión activa, simplemente informa al usuario.
     */
    @Override
    public void undoBTconnection() {
        if (!btConnected) {
            System.out.println("No hay conexión Bluetooth activa para deshacer.");
            return;
        }
        btConnected = false;
        System.out.println("Conexión Bluetooth finalizada.");
    }
}
