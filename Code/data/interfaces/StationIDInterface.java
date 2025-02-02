package data.interfaces;

public interface StationIDInterface {
    int getID();
    GeographicPointInterface getgeoPoint();
    boolean equals(Object o);
    int hashCode();
    String toString();

}
