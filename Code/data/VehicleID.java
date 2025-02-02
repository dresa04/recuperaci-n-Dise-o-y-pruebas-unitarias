package data;

import data.Exceptions.InvalidVehicleIDException;
import data.interfaces.StationIDInterface;
import data.interfaces.VehicleIDInterface;

import java.util.Objects;

/**
 * Clase inmutable que representa un identificador único de un vehículo en el sistema de micromovilidad.
 * Cada vehículo está asociado a una estación específica.
 */
public final class VehicleID implements VehicleIDInterface {
    private final int id;
    private final StationIDInterface station;

    /**
     * Crea un identificador de vehículo con el ID y la estación especificados.
     *
     * @param id      Identificador único del vehículo. Debe ser un número positivo.
     * @param station Estación a la que pertenece el vehículo. No puede ser null.
     * @throws InvalidVehicleIDException Si el ID es menor o igual a 0, o si la estación es null.
     */
    public VehicleID(int id, StationIDInterface station) {
        if (station == null) {
            throw new InvalidVehicleIDException("La estació no pot ser null.");
        }
        if (id <= 0) {
            throw new InvalidVehicleIDException("L'ID del vehicle ha de ser un nombre positiu.");
        }
        this.id = id;
        this.station = station;
    }

    /**
     * Obtiene el identificador único del vehículo.
     *
     * @return ID del vehículo.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la estación a la que pertenece el vehículo.
     *
     * @return Estación asociada al vehículo.
     */
    public StationIDInterface getStation() {
        return station;
    }

    /**
     * Compara este identificador de vehículo con otro para determinar si son iguales.
     * Dos identificadores de vehículo son iguales si tienen el mismo ID y pertenecen a la misma estación.
     *
     * @param o Objeto con el que se compara.
     * @return {@code true} si ambos objetos representan el mismo identificador de vehículo, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return id == vehicleID.id && station.equals(vehicleID.station);
    }

    /**
     * Genera un código hash basado en el ID del vehículo y su estación asociada.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, station);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena con el formato "VehicleID {id=X, station=Y}".
     */
    @Override
    public String toString() {
        return "VehicleID{" +
                "id=" + id +
                ", station=" + station +
                '}';
    }
}
