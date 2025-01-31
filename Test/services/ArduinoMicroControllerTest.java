package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Exceptions.ProceduralException;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.Interfaces.ArduinoMicroControllerInterface;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class ArduinoMicroControllerTest {
    private ArduinoMicroControllerInterface controller;

    @BeforeEach
    void setUp() {
        controller = new ArduinoMicroController();
    }

    @Test
    void testBTConnection() {
        assertDoesNotThrow(() -> controller.setBTconnection());
        assertDoesNotThrow(() -> controller.undoBTconnection());
    }

    @Test
    void testStartDrivingWithoutBT() {
        assertThrows(ConnectException.class, () -> controller.startDriving());
    }

    @Test
    void testStartDrivingWithBT() {
        assertDoesNotThrow(() -> {
            controller.setBTconnection();
            controller.startDriving();
        });
    }

    @Test
    void testStopDrivingWithoutBT() {
        assertThrows(ConnectException.class, () -> controller.stopDriving());
    }

    @Test
    void testStopDrivingWithoutStarting() {
        assertDoesNotThrow(() -> controller.setBTconnection());
        assertThrows(ProceduralException.class, () -> controller.stopDriving());
    }

    @Test
    void testStartAndStopDriving() {
        assertDoesNotThrow(() -> {
            controller.setBTconnection();
            controller.startDriving();
            controller.stopDriving();
        });
    }
}
