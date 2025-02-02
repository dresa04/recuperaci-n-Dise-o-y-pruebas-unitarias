package data;

import data.Exceptions.InvalidGeographicPointException;
import data.interfaces.GeographicPointInterface;

/**
 * Representa un punto geográfico en coordenadas de latitud y longitud.
 * La latitud debe estar entre -90 y 90 grados.
 * La longitud debe estar entre -180 y 180 grados.
 * Esta clase es inmutable y sigue el principio de encapsulación.
 */
public final class GeographicPoint implements GeographicPointInterface {
    private final float latitude;
    private final float longitude;

    /**
     * Crea un punto geográfico con la latitud y longitud especificadas.
     *
     * @param latitude  Latitud en grados, debe estar en el rango [-90, 90].
     * @param longitude Longitud en grados, debe estar en el rango [-180, 180].
     * @throws InvalidGeographicPointException Si la latitud o la longitud están fuera de los límites permitidos.
     */
    public GeographicPoint(float latitude, float longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new InvalidGeographicPointException("La latitud debe estar entre -90 y 90 grados.");
        }
        if (longitude < -180 || longitude > 180) {
            throw new InvalidGeographicPointException("La longitud debe estar entre -180 y 180 grados.");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Devuelve la latitud del punto geográfico.
     *
     * @return Latitud en grados.
     */
    @Override
    public float getLatitude() {
        return latitude;
    }

    /**
     * Devuelve la longitud del punto geográfico.
     *
     * @return Longitud en grados.
     */
    @Override
    public float getLongitude() {
        return longitude;
    }

    /**
     * Compara este punto geográfico con otro objeto.
     * Dos objetos son iguales si tienen la misma latitud y longitud.
     *
     * @param o Objeto con el que se compara.
     * @return {@code true} si ambos objetos representan el mismo punto geográfico, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        return latitude == gP.latitude && longitude == gP.longitude;
    }

    /**
     * Devuelve un código hash para el punto geográfico.
     *
     * @return Código hash basado en la latitud y longitud.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        return result;
    }

    /**
     * Devuelve una representación en cadena del punto geográfico.
     *
     * @return Cadena en el formato "GeographicPoint{latitude=X, longitude=Y}".
     */
    @Override
    public String toString() {
        return "GeographicPoint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

