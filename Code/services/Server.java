package services;


import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;
import services.Interfaces.ServerInterface;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public final class Server implements ServerInterface {
    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        // Lógica para verificar la disponibilidad del vehículo
        if (vhID == null) {
            throw new PMVNotAvailException("El vehículo no está disponible.");
        }
        // Simulamos la verificación de disponibilidad
        if (!isVehicleAvailable(vhID)) {
            throw new PMVNotAvailException("El vehículo está vinculado con otro usuario.");
        }
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException {
        // Lógica para registrar un emparejamiento
        if (user == null || veh == null || st == null || loc == null) {
            throw new InvalidPairingArgsException("Algún argumento de emparejamiento es inválido.");
        }
        // Aquí se realizarían las comprobaciones y registros reales
        // Simulamos el emparejamiento
        System.out.println("Emparejamiento realizado con éxito.");
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date,
                            float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {
        // Lógica para finalizar un emparejamiento
        if (veh == null || user == null || st == null || loc == null) {
            throw new InvalidPairingArgsException("Argumentos inválidos para finalizar el emparejamiento.");
        }
        // Simulamos el proceso de finalización de emparejamiento
        System.out.println("Desemparejamiento realizado. Datos registrados.");
    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        // Lógica para guardar el emparejamiento en la base de datos
        System.out.println("Emparejamiento registrado en la base de datos.");
    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        // Lógica para deshacer el emparejamiento
        if (s == null || !pairingExists(s)) {
            throw new PairingNotFoundException("No se encontró el emparejamiento.");
        }
        // Simulamos la desregistración
        System.out.println("Emparejamiento deshecho.");
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        // Lógica para registrar la nueva ubicación del vehículo
        System.out.println("Ubicación del vehículo registrada.");
    }

    private boolean isVehicleAvailable(VehicleID vhID) {
        // Simulamos una verificación de disponibilidad
        return vhID.getId() % 2 == 0;  // Simula que solo los vehículos con ID par están disponibles
    }

    private boolean pairingExists(JourneyService s) {
        // Simulamos la existencia del emparejamiento
        return true;
    }
}
