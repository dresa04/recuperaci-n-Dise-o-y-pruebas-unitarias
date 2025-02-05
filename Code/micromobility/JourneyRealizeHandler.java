package micromobility;

import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import data.interfaces.UserAccountInterface;
import data.interfaces.VehicleIDInterface;
import services.Exceptions.*;
import services.ServerInterface;
import services.smartfeatures.Interfaces.QRDecoderInterface;

import java.math.RoundingMode;
import java.net.ConnectException;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Clase que gestiona el proceso de realizar un viaje, incluyendo la interacción con el vehículo, el escaneo de QR,
 * la conexión Bluetooth y la comunicación con el servidor para registrar el inicio y fin del viaje.
 */
public class JourneyRealizeHandler {

    private QRDecoderInterface qrDecoder;
    private ServerInterface server;
    private JourneyServiceInterface journeyService; // Se inyecta una interfaz para manejar los viajes
    private BufferedImage qrImage;
    private UserAccountInterface user;
    private StationIDInterface station;
    private GeographicPointInterface initialLocation;
    private VehicleIDInterface vehicleID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    boolean inProgress;
    private PMVehicle vehicle;

    /**
     * Constructor de la clase JourneyRealizeHandler.
     * Inicializa el manejador de viajes con los servicios necesarios.
     *
     * @param initialLocation Ubicación inicial del viaje.
     * @param user Cuenta de usuario que realiza el viaje.
     * @param qrDecoder Decodificador de QR para obtener el ID del vehículo.
     * @param server Servicio para gestionar la comunicación con el servidor.
     * @param initialStation Estación inicial del vehículo.
     * @param vehicle Vehículo que realiza el viaje.
     * @param journeyService Servicio para gestionar la información del viaje.
     */
    public JourneyRealizeHandler(GeographicPointInterface initialLocation,
                                 UserAccountInterface user,
                                 QRDecoderInterface qrDecoder,
                                 ServerInterface server,
                                 StationIDInterface initialStation,
                                 PMVehicle vehicle,
                                 JourneyServiceInterface journeyService) {
        this.qrDecoder = qrDecoder;
        this.server = server;
        this.station = initialStation;
        this.vehicle = vehicle;
        this.user = user;
        this.initialLocation = initialLocation;
        this.journeyService = journeyService;
        this.inProgress = false;
    }

    // Métodos de configuración (para posibles inyecciones posteriores)
    public void setQrDecoder(QRDecoderInterface qrDecoder) {
        this.qrDecoder = qrDecoder;
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    public void setJourneyService(JourneyServiceInterface journeyService) {
        this.journeyService = journeyService;
    }

    /**
     * Recibe el ID de la estación vía Bluetooth y actualiza la ubicación.
     *
     * @param statID ID de la estación recibida por Bluetooth.
     * @throws ConnectException Si no se recibe un ID válido de estación.
     */
    public void broadcastStationID(StationIDInterface statID) throws ConnectException {
        if (statID == null) {
            throw new ConnectException("Error: No se recibió un ID de estación válido.");
        }
        this.station = statID;
        GeographicPointInterface location = station.getgeoPoint(); // Actualiza la ubicación
    }

    /**
     * Escanea el código QR y vincula el vehículo con el usuario.
     *
     * @throws ConnectException Si la conexión Bluetooth no es válida.
     * @throws InvalidPairingArgsException Si los argumentos de emparejamiento son inválidos.
     * @throws QRImgException Si la imagen QR no es válida.
     * @throws PMVNotAvailException Si el vehículo no está disponible.
     * @throws ProceduralException Si hay un error en el proceso de emparejamiento.
     */
    public void scanQR() throws ConnectException, InvalidPairingArgsException, QRImgException, PMVNotAvailException, ProceduralException {
        if (qrImage == null) {
            throw new ProceduralException("La imagen del QR no existe.");
        }

        vehicleID = qrDecoder.getVehicleID(qrImage);
        server.checkPMVAvail(vehicleID);
        server.registerPairing(user, vehicleID, station, initialLocation, LocalDateTime.now(), journeyService);
        establishBluetoothConnection();
    }

    /**
     * Inicia el viaje, registrando el comienzo en el sistema.
     *
     * @throws ConnectException Si la conexión Bluetooth no está establecida.
     * @throws ProceduralException Si el viaje no puede iniciarse correctamente.
     */
    public void startDriving() throws ConnectException, ProceduralException {
        if (vehicleID == null || !isBluetoothConnected()) {
            throw new ConnectException("La conexión Bluetooth no está establecida correctamente.");
        }

        this.journeyService.setInProgress(true);
        vehicle.setUnderWay();
        this.startDate = LocalDateTime.now();
        this.journeyService.setInitDate(String.valueOf(startDate));
        this.journeyService.setInitHour(String.valueOf(startDate.getHour()));
        this.inProgress = true;
    }

    /**
     * Detiene el viaje, registrando el final y calculando la duración, distancia, velocidad y el importe.
     *
     * @throws ConnectException Si la conexión Bluetooth no es válida.
     * @throws ProceduralException Si el viaje no está en progreso.
     * @throws InvalidPairingArgsException Si los argumentos del emparejamiento son inválidos.
     */
    public void stopDriving() throws ConnectException, ProceduralException, InvalidPairingArgsException {
        if (!inProgress) {
            throw new ProceduralException("El viaje no está en progreso.");
        }

        vehicle.setNotAvailb();
        this.inProgress = false;

        this.endDate = LocalDateTime.now();
        this.journeyService.setEndDate(String.valueOf(endDate));
        this.journeyService.setEndHour(String.valueOf(endDate.getHour()));

        int duration = (int) Duration.between(startDate, endDate).toMinutes();
        this.journeyService.setDuration(duration);

        BigDecimal calculatedDistance = calculateDistance();
        this.journeyService.setDistance(calculatedDistance);
        BigDecimal avgSpeed = calculateAverageSpeed(calculatedDistance, duration);
        this.journeyService.setAvgSpeed(avgSpeed);

        BigDecimal amount = calculateAmount();
        this.journeyService.setImport(amount);

        server.stopPairing(user, vehicleID, vehicleID.getStation(), vehicle.getLocation(), endDate, avgSpeed, calculatedDistance, duration, amount, journeyService);
        vehicle.setAvailb();
    }

    /**
     * Calcula la distancia recorrida durante el trayecto.
     *
     * @return La distancia calculada como BigDecimal.
     */
    private BigDecimal calculateDistance() {
        return new BigDecimal(5); // Ejemplo simplificado, se debe calcular basado en las ubicaciones
    }

    /**
     * Calcula la velocidad promedio del viaje.
     *
     * @param distance Distancia recorrida durante el viaje.
     * @param duration Duración del viaje en minutos.
     * @return La velocidad promedio como BigDecimal.
     */
    private BigDecimal calculateAverageSpeed(BigDecimal distance, int duration) {
        if (duration == 0) {
            return BigDecimal.ZERO;
        }
        return distance.divide(new BigDecimal(duration), 2, RoundingMode.HALF_UP);
    }

    /**
     * Desempareja el vehículo y finaliza el servicio.
     *
     * @throws ConnectException Si la conexión Bluetooth no es válida.
     * @throws InvalidPairingArgsException Si los argumentos de emparejamiento son inválidos.
     * @throws PairingNotFoundException Si no se encuentra un emparejamiento válido.
     * @throws ProceduralException Si el viaje no está en progreso.
     */
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (vehicleID == null || inProgress) {
            throw new PairingNotFoundException("No hay vehículo emparejado o el viaje no está en progreso.");
        }

        this.journeyService.setInProgress(false);
        server.stopPairing(user, vehicleID, vehicleID.getStation(), vehicle.getLocation(), endDate, journeyService.getAvgSpeed(), journeyService.getDistance(), (int) Duration.between(startDate, endDate).toMinutes(), calculateAmount(), journeyService);
        vehicle.setAvailb();
    }

    /**
     * Verifica si la conexión Bluetooth está activa.
     *
     * @return true si la conexión Bluetooth está activa, false si no lo está.
     */
    private boolean isBluetoothConnected() {
        return true; // Lógica simplificada
    }

    /**
     * Establece la conexión Bluetooth entre el vehículo y el dispositivo móvil.
     */
    private void establishBluetoothConnection() {
        // Lógica para establecer la conexión Bluetooth
    }

    /**
     * Calcula el importe a cobrar por el servicio de viaje.
     *
     * @return El importe calculado como BigDecimal.
     */
    private BigDecimal calculateAmount() {
        return new BigDecimal(10); // Ejemplo simplificado
    }

    /**
     * Establece la imagen del QR que se escaneará.
     *
     * @param qrImage Imagen del QR.
     */
    public void setQrImage(BufferedImage qrImage) {
        this.qrImage = qrImage;
    }

    /**
     * Obtiene la ubicación de la estación.
     *
     * @return La ubicación geográfica de la estación.
     */
    public GeographicPointInterface getStation() {
        return this.station.getgeoPoint();
    }
}
