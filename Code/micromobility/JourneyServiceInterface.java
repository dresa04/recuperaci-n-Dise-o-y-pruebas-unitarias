package micromobility;

import data.interfaces.GeographicPointInterface;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface JourneyServiceInterface {

    // Obtener y establecer la fecha de inicio del viaje
    void setInitDate(String initDate);
    LocalDateTime getInitDate();

    // Obtener y establecer la hora de inicio del viaje
    void setInitHour(String initHour);
    int getInitHour();

    // Obtener y establecer el punto de origen del viaje
    void setOriginPoint(GeographicPointInterface originPoint);
    GeographicPointInterface getOriginPoint();

    // Obtener y establecer el punto de destino del viaje
    void setEndPoint(GeographicPointInterface endPoint);
    GeographicPointInterface getEndPoint();

    // Obtener y establecer la fecha de finalización del viaje
    void setEndDate(String endDate);
    LocalDateTime getEndDate();

    // Obtener y establecer la hora de finalización del viaje
    void setEndHour(String endHour);
    int getEndHour();

    // Obtener y establecer la duración del viaje
    void setDuration(double duration);
    double getDuration();

    // Obtener y establecer la distancia del viaje
    void setDistance(double distance);
    double getDistance();

    // Obtener y establecer la velocidad promedio durante el viaje
    void setAvgSpeed(double avgSpeed);
    double getAvgSpeed();

    // Obtener y establecer el importe del viaje
    void setImport(BigDecimal imp);
    double getImport();

    // Obtener y establecer el estado de progreso del viaje
    void setInProgress(boolean inProgress);
    boolean getInProgress();

    void setServiceInit(LocalDateTime initDate, GeographicPointInterface Origin);

    void setServiceFinish(LocalDateTime endDate, GeographicPointInterface End, BigDecimal imp, float avSP, float dist, float duration);


    // Obtener el ID del servicio de viaje
    String getServiceID();
}
