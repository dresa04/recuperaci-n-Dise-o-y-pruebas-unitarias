package micromobility;

import data.GeographicPoint;
import data.interfaces.GeographicPointInterface;
import services.Exceptions.ProceduralException;

/**
 * Representa un vehículo de micromovilidad dentro del sistema compartido.
 * Un vehículo tiene un estado (Disponible, No disponible, en Trayecto) y una ubicación geográfica.
 */
public class PMVehicle {
    private final int vehicleID; // Identificador único del vehículo
    private static PMVState state; // Estado actual del vehículo
    private GeographicPointInterface location; // Ubicación del vehículo

    /**
     * Constructor del vehículo.
     * Inicializa un vehículo con un ID, una ubicación y un estado inicial.
     *
     * @param vehicleID Identificador único del vehículo.
     * @param initialLocation Ubicación inicial del vehículo.
     * @param initialState Estado inicial del vehículo.
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo o inválido.
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
        state = PMVState.Available; // Estado inicial por defecto
    }

    // Métodos getter para acceder a los atributos
    public int getVehicleID() {
        return vehicleID;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPointInterface getLocation() {
        return location;
    }

    // Métodos setter controlados con excepciones para asegurar cambios válidos de estado

    /**
     * Cambia el estado del vehículo a "No Disponible".
     * Si el vehículo ya está en este estado, se lanza una excepción.
     *
     * @throws ProceduralException Si el vehículo ya está en estado "NotAvailable".
     */
    public void setNotAvailb() throws ProceduralException {
        if (state == PMVState.NotAvailable) {
            throw new ProceduralException("El vehículo ya está en estado NotAvailable.");
        }
        state = PMVState.NotAvailable;
    }

    /**
     * Cambia el estado del vehículo a "En Trayecto".
     * Si el vehículo no está disponible o ya está en trayecto, se lanza una excepción.
     *
     * @throws ProceduralException Si el vehículo ya está en trayecto o no está disponible.
     */
    public static void setUnderWay() throws ProceduralException {
        if (state == PMVState.UnderWay) {
            throw new ProceduralException("El vehículo ya está en estado UnderWay.");
        }
        if (state == PMVState.NotAvailable) {
            throw new ProceduralException("No se puede iniciar un trayecto si el vehículo no está disponible.");
        }
        state = PMVState.UnderWay;
    }

    /**
     * Cambia el estado del vehículo a "Disponible".
     * Si el vehículo ya está en este estado o está en trayecto, se lanza una excepción.
     *
     * @throws ProceduralException Si el vehículo ya está en estado "Available" o está en movimiento.
     */
    public void setAvailb() throws ProceduralException {
        if (state == PMVState.Available) {
            throw new ProceduralException("El vehículo ya está en estado Available.");
        }
        if (state == PMVState.UnderWay) {
            throw new ProceduralException("No se puede cambiar a Available mientras el vehículo está en movimiento.");
        }
        state = PMVState.Available;
    }

    /**
     * Cambia la ubicación del vehículo.
     * Se lanza una excepción si la nueva ubicación es nula.
     *
     * @param gP Nueva ubicación del vehículo.
     * @throws IllegalArgumentException Si la nueva ubicación es nula.
     */
    public void setLocation(GeographicPoint gP) {
        if (gP == null) {
            throw new IllegalArgumentException("La ubicación no puede ser nula.");
        }
        this.location = gP;
    }
}
