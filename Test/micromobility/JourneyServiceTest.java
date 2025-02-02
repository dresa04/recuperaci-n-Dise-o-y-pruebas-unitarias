package micromobility;

import static micromobility.PMVState.Available;
import static org.junit.jupiter.api.Assertions.*;

import data.GeographicPoint;
import data.StationID;
import data.interfaces.GeographicPointInterface;
import data.interfaces.StationIDInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class JourneyServiceTest {

    private JourneyService journeyService;

    @BeforeEach
    void setUp() {
        // Inicializar el servicio de viaje antes de cada prueba
// Crear un objeto GeographicPoint
        GeographicPoint location = new GeographicPoint(10.0F, 20.0F); // Ubicación (ejemplo de latitud y longitud)

// Crear un PMVehicle con un ID y la ubicación
        PMVehicle vehicle = new PMVehicle(1, location, Available); // El 1 es el ID del vehículo, y location es la ubicación

// Ahora puedes crear un JourneyService utilizando este vehículo
        journeyService = new JourneyService(vehicle);

  // Usando un mock o instanciación de PMVehicle si es necesario
    }

    @Test
    void testSetAndGetInitDate() {
        LocalDateTime initDate = LocalDateTime.now();
        journeyService.setInitDate(String.valueOf(initDate));
        assertEquals(initDate, journeyService.getInitDate(), "El valor de initDate no es correcto");
    }

    @Test
    void testSetAndGetInitHour() {
        int initHour = 10;
        journeyService.setInitHour(String.valueOf(initHour));
        assertEquals(initHour, journeyService.getInitHour(), "El valor de initHour no es correcto");
    }

    @Test
    void testSetAndGetDuration() {
        int duration = 30;
        journeyService.setDuration(duration);
        assertEquals(duration, journeyService.getDuration(), "El valor de duration no es correcto");
    }

    @Test
    void testSetAndGetDistance() {
        float distance = 10.5f;
        journeyService.setDistance(BigDecimal.valueOf(distance));
        assertEquals(distance, journeyService.getDistance().floatValue(), 0.0001, "El valor de distance no es correcto");
    }

    @Test
    void testSetAndGetAvgSpeed() {
        float avgSpeed = 15.2f;
        journeyService.setAvgSpeed(BigDecimal.valueOf(avgSpeed));
        assertEquals(avgSpeed, journeyService.getAvgSpeed().floatValue(), 0.0001, "El valor de avgSpeed no es correcto");
    }


    @Test
    void testSetAndGetOriginPoint() {
        // Crear el objeto con latitud y longitud (por ejemplo, 10.0 y 20.0)
        GeographicPointInterface originPoint = new GeographicPoint(10.0f, 20.0f);

        // Establecer el punto de origen en el servicio
        journeyService.setOriginPoint(originPoint);

        // Verificar que el punto de origen se ha establecido correctamente
        assertEquals(originPoint, journeyService.getOriginPoint(), "El valor de originPoint no es correcto");
    }


    @Test
    void testSetAndGetEndPoint() {
        GeographicPointInterface endPoint = new GeographicPoint(10.0f, 20.0f); // Suponiendo que tengas una implementación de GeographicPoint
        journeyService.setEndPoint(endPoint);
        assertEquals(endPoint, journeyService.getEndPoint(), "El valor de endPoint no es correcto");
    }

    @Test
    void testSetAndGetEndDate() {
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);
        journeyService.setEndDate(String.valueOf(endDate));
        assertEquals(endDate, journeyService.getEndDate(), "El valor de endDate no es correcto");
    }

    @Test
    void testSetAndGetEndHour() {
        int endHour = 11;
        journeyService.setEndHour(String.valueOf(endHour));
        assertEquals(endHour, journeyService.getEndHour(), "El valor de endHour no es correcto");
    }

    @Test
    void testSetAndGetImportAmount() {
        BigDecimal importAmount = new BigDecimal("25.75");
        journeyService.setImport(importAmount);
        assertEquals(importAmount, journeyService.getImportAmount(), "El valor de importAmount no es correcto");
    }

    @Test
    void testSetAndGetServiceID() {
        String serviceID = "12345";
        journeyService.setServiceID(serviceID);
        assertEquals(serviceID, journeyService.getServiceID(), "El valor de serviceID no es correcto");
    }

    @Test
    void testSetAndGetInProgress() {
        journeyService.setInProgress(true);
        assertTrue(journeyService.getInProgress(), "El valor de inProgress no es correcto");
    }

    @Test
    void testSetAndGetOrgStatID() {
        GeographicPointInterface orgGeoP = new GeographicPoint(1.1F, 1.2F);
        StationIDInterface orgStatID = new StationID(1, orgGeoP); // Suponiendo que tengas una implementación de StationID
        journeyService.setOrgStatID(orgStatID);
        assertEquals(orgStatID, journeyService.getOrgStatID(), "El valor de orgStatID no es correcto");
    }

    @Test
    void testSetAndGetEndStatID() {
        GeographicPointInterface orgGeoP = new GeographicPoint(1.1F, 1.2F);
        StationIDInterface endStatID = new StationID(1, orgGeoP);
        journeyService.setEndStatID(endStatID);// Suponiendo que tengas una implementación de StationID        journeyService.setEndStatID(endStatID);
        assertEquals(endStatID, journeyService.getEndStatID(), "El valor de endStatID no es correcto");
    }
}
