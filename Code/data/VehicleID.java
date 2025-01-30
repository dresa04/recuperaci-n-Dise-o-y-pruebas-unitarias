package data;

import data.Exceptions.InvalidVehicleIDException;
import data.interfaces.VehicleIDInterface;

import java.util.Objects;

public final class VehicleID implements VehicleIDInterface {
    private final int id;
    private final StationID station;

    public VehicleID(int id, StationID station) {
        if (id <= 0) {
            throw new InvalidVehicleIDException("El ID del vehículo debe ser un número positivo.");
        }
        if (station == null) {
            throw new InvalidVehicleIDException("La estación asociada no puede ser null.");
        }
        this.id = id;
        this.station = station;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public StationID getStation() {
        return station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return id == vehicleID.id && station.equals(vehicleID.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, station);
    }

    @Override
    public String toString() {
        return "VehicleID{id=" + id + ", station=" + station + "}";
    }


}
