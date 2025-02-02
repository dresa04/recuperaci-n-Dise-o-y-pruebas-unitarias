package services;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import micromobility.JourneyServiceInterface;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import data.interfaces.UserAccountInterface;
import data.interfaces.VehicleIDInterface;
import micromobility.JourneyService;
import services.Exceptions.InvalidPairingArgsException;
import services.Exceptions.PMVNotAvailException;
import services.Exceptions.PairingNotFoundException;

/**
 * Clase que simula un servidor que gestiona la disponibilidad de vehículos,
 * su ubicación y la asignación de viajes a los usuarios.
 */
public class Server implements ServerInterface {
    // Almacenamiento simulado para datos persistentes
    private final Map<VehicleIDInterface, Boolean> vehicleAvailability = new HashMap<>();
    private final Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations = new HashMap<>();
    private final Map<VehicleIDInterface, StationIDInterface> vehicleStation = new HashMap<>();
    private final Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords = new HashMap<>();

    /**
     * Constructor que inicializa el servidor con datos preexistentes.
     *
     * @param vehicleAvailability Mapa que indica la disponibilidad de los vehículos.
     * @param vehicleLocations    Mapa que almacena la ubicación actual de los vehículos.
     * @param vehicleStation      Mapa que asocia cada vehículo con su estación de origen.
     * @param userJourneyRecords  Mapa que registra los viajes en curso de los usuarios.
     */
    public Server(Map<VehicleIDInterface, Boolean> vehicleAvailability,
                  Map<VehicleIDInterface, GeographicPointInterface> vehicleLocations,
                  Map<VehicleIDInterface, StationIDInterface> vehicleStation,
                  Map<UserAccountInterface, JourneyServiceInterface> userJourneyRecords) {
        this.vehicleAvailability.putAll(vehicleAvailability);
        this.vehicleLocations.putAll(vehicleLocations);
        this.vehicleStation.putAll(vehicleStation);
        this.userJourneyRecords.putAll(userJourneyRecords);
    }

    /**
     * Verifica si un vehículo de movilidad personal (PMV) está disponible para ser emparejado.
     *
     * @param vhID Identificador del vehículo.
     * @throws PMVNotAvailException Si el vehículo no está disponible.
     * @throws ConnectException     Si el vehículo no está registrado en el servidor.
     */
    @Override
    public void checkPMVAvail(VehicleIDInterface vhID) throws PMVNotAvailException, ConnectException {
        if (!vehicleAvailability.containsKey(vhID)) {
            throw new ConnectException("Connection failed: Vehicle ID not found in the server.");
        }
        if (!vehicleAvailability.get(vhID)) {
            throw new PMVNotAvailException("The vehicle is not available for pairing.");
        }
    }

    /**
     * Registra el emparejamiento de un usuario con un vehículo en una estación específica.
     *
     * @param user    Usuario que realiza el emparejamiento.
     * @param veh     Vehículo a emparejar.
     * @param st      Estación donde se encuentra el vehículo.
     * @param loc     Ubicación del emparejamiento.
     * @param date    Fecha y hora del emparejamiento.
     * @param journey Servicio de viaje asociado.
     * @throws InvalidPairingArgsException Si los datos del emparejamiento no son válidos.
     * @throws ConnectException            Si el vehículo no está registrado en el servidor.
     */
    @Override
    public void registerPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st,
                                GeographicPointInterface loc, LocalDateTime date, JourneyServiceInterface journey)
            throws InvalidPairingArgsException, ConnectException {

        if (!vehicleAvailability.containsKey(veh)) {
            throw new ConnectException("El vehículo no está registrado en el servidor.");
        }

        if (!veh.getStation().equals(st)) {
            throw new InvalidPairingArgsException("La estación proporcionada no coincide con la estación del vehículo.");
        }

        if (!st.getgeoPoint().equals(loc)) {
            throw new InvalidPairingArgsException("La ubicación proporcionada no coincide con la de la estación.");
        }

        setPairing(user, veh, st, loc, date, journey);
    }

    /**
     * Finaliza un emparejamiento de usuario con un vehículo y registra los datos del trayecto.
     *
     * @param user    Usuario que finaliza el trayecto.
     * @param veh     Vehículo usado en el trayecto.
     * @param st      Estación donde finaliza el trayecto.
     * @param loc     Ubicación del final del trayecto.
     * @param date    Fecha y hora de finalización.
     * @param avSp    Velocidad media del trayecto.
     * @param dist    Distancia recorrida.
     * @param dur     Duración del trayecto en minutos.
     * @param imp     Importe total del trayecto.
     * @param journey Servicio de viaje asociado.
     * @throws InvalidPairingArgsException Si no hay un emparejamiento válido para finalizar.
     * @throws ConnectException            Si el vehículo no está registrado en el servidor.
     */
    @Override
    public void stopPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st,
                            GeographicPointInterface loc, LocalDateTime date, BigDecimal avSp, BigDecimal dist,
                            int dur, BigDecimal imp, JourneyServiceInterface journey)
            throws InvalidPairingArgsException, ConnectException {

        if (!vehicleAvailability.containsKey(veh)) {
            throw new ConnectException("El vehículo no está registrado en el servidor.");
        }

        if (!vehicleStation.containsKey(veh)) {
            throw new InvalidPairingArgsException("El vehículo no tiene una estación registrada.");
        }

        if (!veh.getStation().equals(st)) {
            throw new InvalidPairingArgsException("La estación proporcionada no coincide con la estación del vehículo.");
        }

        if (!st.getgeoPoint().equals(loc)) {
            throw new InvalidPairingArgsException("La ubicación proporcionada no coincide con la de la estación.");
        }

        if (!userJourneyRecords.containsKey(user)) {
            throw new InvalidPairingArgsException("No hay un viaje en curso asociado a este usuario.");
        }

        journey.setServiceFinish(date, loc, imp, avSp, dist, dur);

        registerLocation(veh, st);
        vehicleAvailability.put(veh, true);
        vehicleLocations.put(veh, loc);
        userJourneyRecords.remove(user);
    }

    /**
     * Marca un vehículo como emparejado con un usuario y actualiza su información en el servidor.
     *
     * @param user    Usuario asociado.
     * @param veh     Vehículo emparejado.
     * @param st      Estación de inicio del viaje.
     * @param loc     Ubicación del inicio del viaje.
     * @param date    Fecha y hora de inicio del emparejamiento.
     * @param journey Servicio de viaje asociado.
     */
    @Override
    public void setPairing(UserAccountInterface user, VehicleIDInterface veh, StationIDInterface st,
                           GeographicPointInterface loc, LocalDateTime date, JourneyServiceInterface journey) {
        vehicleAvailability.put(veh, false);
        vehicleLocations.put(veh, loc);
        vehicleStation.put(veh, st);
        journey.setServiceInit(date, loc);
        userJourneyRecords.put(user, journey);
    }

    /**
     * Desempareja un servicio de viaje en el servidor.
     *
     * @param s Servicio de viaje a desemparejar.
     * @throws PairingNotFoundException Si el emparejamiento no existe.
     */
    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (!userJourneyRecords.containsValue(s)) {
            throw new PairingNotFoundException("No matching journey service record found.");
        }

        userJourneyRecords.values().remove(s);
    }

    /**
     * Actualiza la ubicación de un vehículo en el servidor y lo marca como disponible.
     *
     * @param veh Vehículo cuya ubicación se actualiza.
     * @param st  Estación donde se encuentra el vehículo.
     * @throws ConnectException Si el vehículo no está registrado o la estación no coincide.
     */
    @Override
    public void registerLocation(VehicleIDInterface veh, StationIDInterface st) throws ConnectException {
        if (!vehicleAvailability.containsKey(veh)) {
            throw new ConnectException("El vehículo no está registrado en el servidor.");
        }

        if (!vehicleStation.containsKey(veh)) {
            throw new ConnectException("El vehículo no tiene una estación registrada.");
        }

        if (!veh.getStation().equals(st)) {
            throw new ConnectException("La estación proporcionada no coincide con la estación registrada para el vehículo.");
        }

        GeographicPointInterface stationLocation = st.getgeoPoint();
        vehicleLocations.put(veh, stationLocation);
        vehicleAvailability.put(veh, true);
        vehicleStation.put(veh, st);
    }
}
