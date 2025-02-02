package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import data.interfaces.UserAccountInterface;
import data.interfaces.VehicleIDInterface;
import services.Exceptions.*;
import services.ServerInterface;
import services.smartfeatures.Interfaces.QRDecoderInterface;
import services.smartfeatures.QRDecoder;

import java.net.ConnectException;

import java.awt.image.BufferedImage;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JourneyRealizeHandler {

    private final QRDecoderInterface qrDecoder;
    private final ServerInterface server;

    private BufferedImage qrImage;
    private UserAccountInterface user; // Cambio a interfaz
    private StationIDInterface station; // Cambio a interfaz
    private final StationIDInterface initialStation; // Cambio a interfaz
    private GeographicPointInterface location; // Cambio a interfaz
    private GeographicPointInterface initialLocation; // Cambio a interfaz
    private VehicleIDInterface vehicleID; // Cambio a interfaz
    private float averageSpeed;
    private float distance;
    private int duration;
    private BigDecimal amount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private JourneyServiceInterface journey;
    boolean inProgress;
    PMVehicle vehicle;

    public JourneyRealizeHandler(GeographicPointInterface initialLocation, UserAccountInterface user, QRDecoderInterface qrDecoder, ServerInterface server, StationIDInterface initialStation, PMVehicle vehicle) {
        this.qrDecoder = qrDecoder;
        this.server = server;
        this.station = initialStation;
        this.initialStation = initialStation;
        this.inProgress = false; // Inicialmente no hay un viaje en curso
        this.vehicle = vehicle;
        this.user = user;
        this.initialLocation = initialLocation;
    }


    // Método para recibir el ID de la estación via Bluetooth
    public void broadcastStationID(StationIDInterface statID) throws ConnectException {
        if (statID == null) {
            throw new ConnectException("Error: No se recibió un ID de estación válido.");
        }

        // Asignamos el ID de la estación al sistema
        this.station = statID;
        this.location = (GeographicPoint) station.getgeoPoint(); // Actualizamos la ubicación
    }

    // Método para escanear el código QR y vincular el vehículo
    public void scanQR() throws ConnectException, InvalidPairingArgsException, QRImgException, PMVNotAvailException, ProceduralException {
        if (qrImage == null) {
            throw new ProceduralException("La imagen del QR no existe.");
        }

        // 1. Decodificar el QR para obtener el VehicleID
        vehicleID = qrDecoder.getVehicleID(qrImage);

        // 2. Verificar disponibilidad del vehículo
        server.checkPMVAvail(vehicleID);

        journey = new JourneyService(vehicle);

        // 3. Registrar el emparejamiento
        server.registerPairing(user, vehicleID, station, initialLocation, LocalDateTime.now(), journey);

        initialLocation = station.getgeoPoint(); // Guardamos la posicion inicial para calcular la distancia posteriormente

            // Crear una nueva instancia de JourneyService
            this.journey = new JourneyService(vehicle);
            this.journey.setInitDate(String.valueOf(LocalDateTime.now()));
            this.journey.setInitHour(String.valueOf(LocalDateTime.now().getHour()));
            this.journey.setOriginPoint(station.getgeoPoint());
            this.journey.setOriginPoint(station.getgeoPoint());

            // Establecer la conexión BT entre el vehículo y el smartphone
            establishBluetoothConnection();
        }

    // Método para iniciar el desplazamiento
    public void startDriving() throws ConnectException, ProceduralException {
        if (vehicleID == null || !isBluetoothConnected()) {
            throw new ConnectException("La conexión Bluetooth no está establecida correctamente.");
        }


        // Iniciar trayecto
        this.journey.setInProgress(true);
        vehicle.setUnderWay();

        // Guardar la fecha de inicio
        this.startDate = LocalDateTime.now();
        this.journey.setInitDate(String.valueOf(startDate));
        this.journey.setInitHour(String.valueOf(startDate.getHour()));

        this.inProgress = true;
    }

    // Método para detener el desplazamiento
    public void stopDriving() throws ConnectException, ProceduralException {
        if (!inProgress) {
            throw new ProceduralException("El viaje no está en progreso.");
        }

        // Detener el trayecto
        vehicle.setNotAvailb();
        this.inProgress = false;

        // Guardar la fecha de finalización
        this.endDate = LocalDateTime.now();
        this.journey.setEndDate(String.valueOf(endDate));
        this.journey.setEndHour(String.valueOf(endDate.getHour()));

    }

    // Método para desemparejar el vehículo
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (vehicleID == null || inProgress) {
            throw new PairingNotFoundException("No hay vehículo emparejado o el viaje no está en progreso.");
        }

        // Desemparejar vehículo
        this.journey.setInProgress(false);

        // Calcular duración, distancia y velocidad promedio
        this.duration = (int) Duration.between(startDate, endDate).toMinutes();
        this.journey.setDuration(duration);
        this.journey.setDistance(distance);
        this.journey.setAvgSpeed(averageSpeed);

        // Calcular el importe del servicio
        this.amount = calculateAmount();
        this.journey.setImport(amount);

        // Finalizar el servicio
        server.stopPairing(user, vehicleID, vehicleID.getStation(), vehicle.getLocation(), endDate, averageSpeed, distance, duration, amount, journey );
        vehicle.setAvailb();

    }

    private boolean isBluetoothConnected() {
        // Lógica para comprobar la conexión Bluetooth (simplificada)
        return true;
    }

    private void establishBluetoothConnection() {
        // Establecer conexión Bluetooth entre el vehículo y el smartphone
    }

    private BigDecimal calculateAmount() {
        // Lógica para calcular el importe basado en duración, distancia y velocidad promedio
        return new BigDecimal(10); // Ejemplo simplificado
    }



    // Métodos adicionales de gestión de QR y vehículos
    public void setQrImage(BufferedImage qrImage) {
        this.qrImage = qrImage;
    }

    public GeographicPointInterface getStation() {
        return this.station.getgeoPoint();
    }
}
