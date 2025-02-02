package micromobility;

import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class JourneyService implements JourneyServiceInterface {

        private LocalDateTime initDate;
        private int initHour;
        private int duration;
        private float distance;
        private float avgSpeed;
        private GeographicPointInterface originPoint;
        private GeographicPointInterface endPoint;
        private LocalDateTime endDate;
        private int endHour;
        private BigDecimal imp;
        private BigDecimal importAmount;
        private String serviceID;
        private boolean inProgress;
        private StationIDInterface orgStatID;
        private StationIDInterface endStatID;

        public JourneyService(PMVehicle vehicle) {
            this.inProgress = false;
            this.initDate = LocalDateTime.now(); // Se inicializa con la fecha y hora actual
            this.initHour = initDate.getHour();
            this.duration = 0; // Aún no ha comenzado el viaje
            this.distance = 0.0f;
            this.avgSpeed = 0.0f;
            this.originPoint = vehicle.getLocation(); // Suponiendo que el vehículo tiene un método para obtener la ubicación
            this.endPoint = null; // Se establecerá al finalizar el viaje
            this.endDate = null; // No hay fecha de finalización aún
            this.endHour = -1; // Valor inválido hasta que termine el viaje
            this.imp = BigDecimal.ZERO;
            this.endStatID = null; // Se establecerá cuando termine el viaje
        }


    @Override
    public void setInitDate(String initDate) {

    }

    // Métodos Getters y Setters
    public LocalDateTime getInitDate() {
        return initDate;
    }

    @Override
    public void setInitHour(String initHour) {

    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public int getInitHour() {
        return initHour;
    }

    public void setInitHour(int initHour) {
        this.initHour = initHour;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public void setDistance(double distance) {

    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public void setAvgSpeed(double avgSpeed) {

    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    @Override
    public void setImport(BigDecimal imp) {

    }



    @Override
    public double getImport() {
        return 0;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
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

    @Override
    public void setEndDate(String endDate) {

    }

    public void setEndPoint(GeographicPointInterface endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public void setEndHour(String endHour) {

    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getEndHour() {
        return endHour;
    }

    @Override
    public void setDuration(double duration) {

    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public BigDecimal getImportAmount() {
        return importAmount;
    }

    public void setImportAmount(BigDecimal importAmount) {
        this.importAmount = importAmount;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    public boolean getInProgress() {
        return false;
    }

    @Override
    public void setServiceInit(LocalDateTime initDate, GeographicPointInterface Origin) {

    }

    @Override
    public void setServiceFinish(LocalDateTime endDate, GeographicPointInterface End, BigDecimal imp, float avSP, float dist, float duration) {
        this.endDate = endDate;
        this.endPoint = End;
        this.importAmount = imp;
        this.avgSpeed = avSP;
        this.distance = dist;
        this.duration = (int) duration;
        this.inProgress = false;
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
