package data.interfaces;

import data.interfaces.StationIDInterface;

public interface VehicleIDInterface {
    int getId();
    StationIDInterface getStation();

    boolean equals(Object o) ;

    int hashCode() ;
    String toString() ;
}