package services.smartfeatures;

import micromobility.PMVehicle;
import services.Exceptions.PMVPhisicalException;
import services.Exceptions.ProceduralException;
import services.smartfeatures.Interfaces.ArduinoMicroControllerInterface;

import java.net.ConnectException;
import java.util.Random;

/**
 * Implementación de la interfaz ArduinoMicroControllerInterface.
 */
public class ArduinoMicroController implements ArduinoMicroControllerInterface {
    private boolean btConnected;
    private boolean isDriving;

    public ArduinoMicroController() {
        this.btConnected = false;
        this.isDriving = false;
    }

    @Override
    public void setBTconnection() throws ConnectException {
        if (btConnected) {
            System.out.println("El canal Bluetooth ya está establecido.");
            return;
        }
        if (new Random().nextInt(100) < 10) { // 10% de fallo en la conexión
            throw new ConnectException("No se pudo establecer la conexión Bluetooth.");
        }
        btConnected = true;
        System.out.println("Conexión Bluetooth establecida correctamente.");
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("No hay conexión Bluetooth con el smartphone.");
        }
        if (isDriving) {
            throw new ProceduralException("El vehículo ya está en movimiento.");
        }
        if (new Random().nextInt(100) < 5) { // 5% de fallo mecánico
            throw new PMVPhisicalException("Fallo en el sistema de arranque del vehículo.");
        }

        // Llamar a setUnderWay() para cambiar correctamente el estado del vehículo
        PMVehicle.setUnderWay();

        isDriving = true;
        System.out.println("El vehículo ha comenzado a moverse.");
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("No hay conexión Bluetooth con el smartphone.");
        }
        if (!isDriving) {
            throw new ProceduralException("El vehículo ya está detenido.");
        }
        if (new Random().nextInt(100) < 5) { // 5% de fallo en los frenos
            throw new PMVPhisicalException("Fallo en el sistema de frenos del vehículo.");
        }
        isDriving = false;
        System.out.println("El vehículo se ha detenido correctamente.");
    }

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
