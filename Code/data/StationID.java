package data;

import data.Exceptions.InvalidStationIDException;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;

public class StationID implements StationIDInterface {
    private final Integer ID;
    private final GeographicPointInterface geoPoint;

    public StationID(int ID, GeographicPointInterface geoPoint){
        this.ID = ID;
        this.geoPoint = geoPoint;
        if (ID <= 0) {
            throw new InvalidStationIDException("L'ID de la estació ha de ser un nombre positiu.");
        }
    }
    public int getID(){return ID;}
    public GeographicPointInterface getgeoPoint(){return geoPoint;}
    public boolean equals (Object o) {
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID gP = (StationID) o;
        eq = (ID == gP.ID) && (geoPoint.equals(gP.geoPoint));
        return eq;
    }
    public int hashCode() {
        int result = Integer.hashCode(ID);
        result = 31 * result + (geoPoint != null ? geoPoint.hashCode() : 0);
        return result;
    }
    public String toString(){
        return "StationID {" + "ID='" + ID + '\'' + "geoPoint='" + geoPoint.toString() + '}';
    }
}