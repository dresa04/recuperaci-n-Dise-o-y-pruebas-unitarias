package micromobility;

import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class JourneyService implements JourneyServiceInterface {

    private LocalDateTime initDate; // Fecha y hora de inicio del viaje
    private int initHour; // Hora de inicio
    private int duration; // Duración del viaje en minutos
    private BigDecimal distance; // Distancia del viaje en kilómetros
    private BigDecimal avgSpeed; // Velocidad promedio durante el viaje
    private GeographicPointInterface originPoint; // Punto de origen del viaje
    private GeographicPointInterface endPoint; // Punto de destino del viaje
    private LocalDateTime endDate; // Fecha y hora de finalización del viaje
    private int endHour; // Hora de finalización
    private BigDecimal importAmount; // Importe total a pagar
    private String serviceID; // Identificador del servicio de viaje
    private boolean inProgress; // Indica si el viaje está en progreso
    private StationIDInterface orgStatID; // Estación de origen
    private StationIDInterface endStatID; // Estación de destino
    private GeographicPointInterface currentLocation; // Ubicación actual del viaje

    /**
     * Constructor del servicio de viaje.
     * Inicializa el servicio de viaje con los parámetros del vehículo y la fecha de inicio.
     *
     * @param vehicle Vehículo que realiza el viaje.
     */
    public JourneyService(PMVehicle vehicle) {
        this.inProgress = false;
        this.initDate = LocalDateTime.now(); // Se inicializa con la fecha y hora actual
        this.initHour = initDate.getHour();
        this.duration = 0; // Aún no ha comenzado el viaje
        this.distance = BigDecimal.valueOf(0.0f);
        this.avgSpeed = BigDecimal.valueOf(0.0f);
        this.originPoint = vehicle.getLocation(); // Ubicación inicial del vehículo
        this.currentLocation = this.originPoint; // Inicialmente la ubicación es la de origen
        this.endPoint = null; // Se establecerá al finalizar el viaje
        this.endDate = null; // No hay fecha de finalización aún
        this.endHour = -1; // Valor inválido hasta que termine el viaje
        this.importAmount = BigDecimal.ZERO;
        this.endStatID = null; // Se establecerá cuando termine el viaje
    }

    // Métodos de la interfaz JourneyServiceInterface

    @Override
    public void setInitDate(String initDate) {
        this.initDate = LocalDateTime.parse(initDate);
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    @Override
    public void setInitHour(String initHour) {
        this.initHour = Integer.parseInt(initHour);
    }

    public int getInitHour() {
        return initHour;
    }

    @Override
    public void setDuration(double duration) {
        this.duration = (int) duration;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    @Override
    public void setAvgSpeed(BigDecimal avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public BigDecimal getAvgSpeed() {
        return avgSpeed;
    }

    @Override
    public void setImport(BigDecimal imp) {
        this.importAmount = imp;
    }

    @Override
    public double getImport() {
        return this.importAmount.doubleValue();
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public void setEndHour(String endHour) {
        this.endHour = Integer.parseInt(endHour);
    }

    public int getEndHour() {
        return endHour;
    }

    @Override
    public void setServiceInit(LocalDateTime initDate, GeographicPointInterface origin) {
        this.initDate = initDate;
        this.originPoint = origin;
        this.currentLocation = origin;
        this.inProgress = true;
    }

    @Override
    public void setServiceFinish(LocalDateTime endDate, GeographicPointInterface end, BigDecimal imp, BigDecimal avgSpeed, BigDecimal dist, float duration) {
        this.endDate = endDate;
        this.endPoint = end;
        this.importAmount = imp;
        this.avgSpeed = avgSpeed;
        this.distance = dist;
        this.duration = (int) duration;
        this.inProgress = false;
    }

    @Override
    public boolean getInProgress() {
        return this.inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    public void updateCurrentLocation(GeographicPointInterface location) {
        this.currentLocation = location;
    }

    public GeographicPointInterface getCurrentLocation() {
        return currentLocation;
    }

    public GeographicPointInterface getOriginPoint() {
        return originPoint;
    }

    public void setOriginPoint(GeographicPointInterface originPoint) {
        this.originPoint = originPoint;
    }

    public GeographicPointInterface getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GeographicPointInterface endPoint) {
        this.endPoint = endPoint;
    }

    public BigDecimal getImportAmount() {
        return importAmount;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public StationIDInterface getOrgStatID() {
        return orgStatID;
    }

    public void setOrgStatID(StationIDInterface orgStatID) {
        this.orgStatID = orgStatID;
    }

    public StationIDInterface getEndStatID() {
        return endStatID;
    }

    public void setEndStatID(StationIDInterface endStatID) {
        this.endStatID = endStatID;
    }
}
