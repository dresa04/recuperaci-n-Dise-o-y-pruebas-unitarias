package services.Interfaces;

import java.net.ConnectException;
import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ServerInterface {
    // External services involved in the shared micromobility system
    void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException;

    void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException;

    void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                     float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException;

    // Internal operations
    void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date);

    void unPairRegisterService(JourneyService s) throws PairingNotFoundException;

    void registerLocation(VehicleID veh, StationID st);
}
