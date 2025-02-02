package data.interfaces;

public interface VehicleIDInterface {
    int getId();
    StationIDInterface getStation();

    boolean equals(Object o) ;

    int hashCode() ;
    String toString() ;
}