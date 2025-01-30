package data;

import data.interfaces.GeographicPointInterface;

import java.util.Objects;

public final class GeographicPoint implements GeographicPointInterface {
    private final float latitude;
    private final float longitude;

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

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint that = (GeographicPoint) o;
        return Float.compare(that.latitude, latitude) == 0 &&
                Float.compare(that.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "GeographicPoint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public class InvalidGeographicPointException extends RuntimeException {
        public InvalidGeographicPointException(String message) {
            super(message);
        }
    }

}
