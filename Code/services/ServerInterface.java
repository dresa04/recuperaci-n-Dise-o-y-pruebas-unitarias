package services;

import java.net.ConnectException;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import data.interfaces.UserAccountInterface;
import data.interfaces.VehicleIDInterface;
import micromobility.JourneyService;
import micromobility.JourneyServiceInterface;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ServerInterface { // External service for the persistent storage
    // To be invoked by the use case controller
    void checkPMVAvail(VehicleIDInterface vhID)
            throws PMVNotAvailException, ConnectException;
    void registerPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st, GeographicPointInterface loc, LocalDateTime date, JourneyServiceInterface journey)
            throws InvalidPairingArgsException, ConnectException;
    void stopPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st,
                     GeographicPointInterface loc, LocalDateTime date, BigDecimal avSp, BigDecimal dist,
                     int dur, BigDecimal imp, JourneyServiceInterface journey)
            throws InvalidPairingArgsException, ConnectException;
    // Internal operations
    void setPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st, GeographicPointInterface loc, LocalDateTime date, JourneyServiceInterface journey);

    void unPairRegisterService(JourneyService s)
            throws PairingNotFoundException;

    void registerLocation(VehicleIDInterface veh, StationIDInterface st) throws ConnectException;
}
