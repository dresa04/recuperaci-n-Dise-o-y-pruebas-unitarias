package data;

import data.Exceptions.InvalidStationIDException;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;

/**
 * Representa la identificación de una estación en el sistema de micromovilidad.
 * Cada estación tiene un identificador único y una ubicación geográfica.
 * Esta clase es inmutable y garantiza que los identificadores sean positivos.
 */
public class StationID implements StationIDInterface {
    private final Integer ID;
    private final GeographicPointInterface geoPoint;

    /**
     * Crea una instancia de una estación con un ID y una ubicación geográfica.
     *
     * @param ID        Identificador único de la estación. Debe ser un número positivo.
     * @param geoPoint  Punto geográfico asociado a la estación.
     * @throws InvalidStationIDException Si el ID es menor o igual a 0.
     */
    public StationID(int ID, GeographicPointInterface geoPoint) {
        if (ID <= 0) {
            throw new InvalidStationIDException("L'ID de la estació ha de ser un nombre positiu.");
        }
        this.ID = ID;
        this.geoPoint = geoPoint;
    }

    /**
     * Obtiene el identificador único de la estación.
     *
     * @return ID de la estación.
     */
    public int getID() {
        return ID;
    }

    /**
     * Obtiene la ubicación geográfica de la estación.
     *
     * @return Objeto {@link GeographicPointInterface} que representa la ubicación de la estación.
     */
    public GeographicPointInterface getgeoPoint() {
        return geoPoint;
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si tienen el mismo ID y la misma ubicación geográfica.
     *
     * @param o Objeto con el que se compara.
     * @return {@code true} si ambos objetos representan la misma estación, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID station = (StationID) o;
        return ID.equals(station.ID) && geoPoint.equals(station.geoPoint);
    }

    /**
     * Genera un código hash basado en el ID y la ubicación de la estación.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(ID);
        result = 31 * result + (geoPoint != null ? geoPoint.hashCode() : 0);
        return result;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena con el formato "StationID {ID=X, geoPoint=Y}".
     */
    @Override
    public String toString() {
        return "StationID {ID=" + ID + ", geoPoint=" + geoPoint + "}";
    }
}
