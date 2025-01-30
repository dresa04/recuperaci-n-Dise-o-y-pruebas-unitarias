package data;

import data.Exceptions.InvalidStationIDException;
import data.interfaces.StationIDInterface;
import java.util.Objects;

public final class StationID implements StationIDInterface {
    private final String id;

    public StationID(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidStationIDException("El identificador de estación no puede ser null o vacío.");
        }
        if (!id.matches("^ST\\d{6}$")) { // Formato: ST seguido de 6 dígitos
            throw new InvalidStationIDException("El identificador de estación debe seguir el formato STXXXXXX.");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID stationID = (StationID) o;
        return id.equals(stationID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StationID{" + "id='" + id + '\'' + '}';
    }

}
