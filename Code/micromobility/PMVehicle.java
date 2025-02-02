package micromobility;

import data.GeographicPoint;
import data.interfaces.GeographicPointInterface;
import services.Exceptions.ProceduralException;

/**
 * Representa un vehículo de micromovilidad dentro del sistema compartido.
 */
public class PMVehicle {
    private final int vehicleID;
    private PMVState state;
    private GeographicPointInterface location;

    /**
     * Constructor del vehículo.
     * @param vehicleID Identificador único del vehículo.
     * @param initialLocation Ubicación inicial del vehículo.
     */
    public PMVehicle(int vehicleID, GeographicPointInterface initialLocation, PMVState initialState) {
        if (vehicleID == 0) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or empty.");
        }
        if (initialLocation == null) {
            throw new IllegalArgumentException("Initial location cannot be null.");
        }
        if (initialState == null) {
            throw new IllegalArgumentException("Initial state cannot be null.");
        }
        this.vehicleID = vehicleID;
        this.location = initialLocation;
        this.state = PMVState.Available; // Estado inicial por defecto
    }

    // Métodos getter
    public int getVehicleID() {
        return vehicleID;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPointInterface getLocation() {
        return location;
    }

    // Métodos setter controlados con excepciones
    public void setNotAvailb() throws ProceduralException {
        if (state == PMVState.NotAvailable) {
            throw new ProceduralException("El vehículo ya está en estado NotAvailable.");
        }
        this.state = PMVState.NotAvailable;
    }

    public static void setUnderWay() throws ProceduralException {
        if (state == PMVState.UnderWay) {
            throw new ProceduralException("El vehículo ya está en estado UnderWay.");
        }
        if (state == PMVState.NotAvailable) {
            throw new ProceduralException("No se puede iniciar un trayecto si el vehículo no está disponible.");
        }
        this.state = PMVState.UnderWay;
    }

    public void setAvailb() throws ProceduralException {
        if (state == PMVState.Available) {
            throw new ProceduralException("El vehículo ya está en estado Available.");
        }
        if (state == PMVState.UnderWay) {
            throw new ProceduralException("No se puede cambiar a Available mientras el vehículo está en movimiento.");
        }
        this.state = PMVState.Available;
    }

    public void setLocation(GeographicPoint gP) {
        if (gP == null) {
            throw new IllegalArgumentException("La ubicación no puede ser nula.");
        }
        this.location = gP;
    }
}
