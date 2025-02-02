package services;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import data.*;
import micromobility.JourneyServiceInterface;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import data.interfaces.UserAccountInterface;
import data.interfaces.VehicleIDInterface;
import micromobility.JourneyService;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;

public class Server implements ServerInterface {
    // Almacenamiento simulado para datos persistentes
    private final Map<VehicleIDInterface, Boolean> vehicleAvailability = new HashMap<>();
    private final Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations = new HashMap<>();
    private final Map<VehicleIDInterface, StationIDInterface> vehicleStation = new HashMap<>();
    private final Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords = new HashMap<>();

    // Constructor para inicializar los mapas
    public Server(Map<VehicleIDInterface, Boolean> vehicleAvailability,
                  Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations,
                  Map<VehicleIDInterface, StationIDInterface> vehicleStation,
                  Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords) {
        this.vehicleAvailability.putAll(vehicleAvailability);
        this.vehicleLocations.putAll(vehicleLocations);
        this.vehicleStation.putAll(vehicleStation);
        this.userJourneyRecords.putAll(userJourneyRecords);
    }

    @Override
    public void checkPMVAvail(VehicleIDInterface vhID) throws PMVNotAvailException, ConnectException {
        if (!vehicleAvailability.containsKey(vhID)) {
            throw new ConnectException("Connection failed: Vehicle ID not found in the server.");
        }
        if (!vehicleAvailability.get(vhID)) {
            throw new PMVNotAvailException("The vehicle is not available for pairing.");
        }
    }

    // Otros métodos omitidos por brevedad...


@Override
    public void registerPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st, GeographicPointInterface loc, LocalDateTime date, JourneyServiceInterface journey)
            throws InvalidPairingArgsException, ConnectException {

        // Verificar si el vehículo está registrado en el sistema
        if (!vehicleAvailability.containsKey(veh)) {
            throw new ConnectException("El vehículo no está registrado en el servidor.");
        }

        // Verificar si la estación proporcionada coincide con la del vehículo
        StationIDInterface currentStation = veh.getStation();
        if (!currentStation.equals(st)) {
            throw new InvalidPairingArgsException("La estación proporcionada no coincide con la estación del vehículo.");
        }

        // Verificar si la ubicación proporcionada coincide con la estación
        GeographicPointInterface stationLocation = st.getgeoPoint();
        if (!stationLocation.equals(loc)) {
            throw new InvalidPairingArgsException("La ubicación proporcionada no coincide con la de la estación.");
        }

        setPairing(user, veh, st, loc, date, journey);

    }


    @Override
    public void stopPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st,
                            GeographicPointInterface loc, LocalDateTime date, float avSp, float dist,
                            int dur, BigDecimal imp, JourneyServiceInterface journey)
            throws InvalidPairingArgsException, ConnectException {

        // Verificar si el vehículo está registrado en el sistema
        if (!vehicleAvailability.containsKey(veh)) {
            throw new ConnectException("El vehículo no está registrado en el servidor.");
        }

        // Verificar si el vehículo tiene una estación registrada en el sistema
        if (!vehicleStation.containsKey(veh)) {
            throw new InvalidPairingArgsException("El vehículo no tiene una estación registrada.");
        }

        // Verificar si la estación proporcionada coincide con la del vehículo
        StationIDInterface currentStation = veh.getStation();
        if (!currentStation.equals(st)) {
            throw new InvalidPairingArgsException("La estación proporcionada no coincide con la estación del vehículo.");
        }

        // Verificar si la ubicación proporcionada coincide con la estación
        GeographicPointInterface stationLocation = st.getgeoPoint();
        if (!stationLocation.equals(loc)) {
            throw new InvalidPairingArgsException("La ubicación proporcionada no coincide con la de la estación.");
        }

        // Verificar que el usuario tiene un viaje registrado en curso
        if (!userJourneyRecords.containsKey(user) ) {
            throw new InvalidPairingArgsException("No hay un viaje en curso asociado a este usuario.");
        }

        // Finalizar la jornada, almacenando los datos del trayecto
        journey.setServiceFinish(date, loc, imp, avSp, dist, dur);

        registerLocation(veh, st);
        // Marcar el vehículo como disponible y actualizar su ubicación
        vehicleAvailability.put(veh, true);
        vehicleLocations.put(veh, loc);


        // Remover el registro del usuario, indicando que el viaje ha finalizado
        userJourneyRecords.remove(user);
    }

    @Override
    public void setPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st, GeographicPointInterface loc, LocalDateTime date, JourneyServiceInterface journey) {
        vehicleAvailability.put(veh, false);
        vehicleLocations.put(veh, loc);
        vehicleStation.put(veh, st);
        journey.setServiceInit(date, loc);
        userJourneyRecords.put(user, journey);

    }


    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (!userJourneyRecords.containsValue(s)) {
            throw new PairingNotFoundException("No matching journey service record found.");
        }

        userJourneyRecords.values().remove(s);
    }

    @Override
    public void registerLocation(VehicleIDInterface veh, StationIDInterface st) throws ConnectException {
        // Verificar si el vehículo existe en el servidor
        if (!vehicleAvailability.containsKey(veh)) {
            throw new ConnectException("El vehículo no está registrado en el servidor.");
        }

        // Verificar si el vehículo tiene una estación registrada
        if (!vehicleStation.containsKey(veh)) {
            throw new ConnectException("El vehículo no tiene una estación registrada.");
        }

        // Verificar que la estación proporcionada es la misma que la registrada para el vehículo
        StationIDInterface currentStation = veh.getStation();
        if (!currentStation.equals(st)) {
            throw new ConnectException("La estación proporcionada no coincide con la estación registrada para el vehículo.");
        }

        // Actualizar la ubicación y el estado del vehículo
        GeographicPointInterface stationLocation = st.getgeoPoint();
        vehicleLocations.put(veh, stationLocation); // Actualizar ubicación
        vehicleAvailability.put(veh, true); // Marcar el vehículo como disponible

        // Actualizar la estación asociada al vehículo
        vehicleStation.put(veh, st);
    }

}