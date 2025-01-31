package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class UnbondedBTSignalTest {

    private UnbondedBTSignal unbondedBTSignal;
    private JourneyRealizeHandler journeyRealizeHandler;

    @BeforeEach
    void setUp() {
        // Instancia real de JourneyRealizeHandler
        journeyRealizeHandler = new JourneyRealizeHandler();
        // Usamos la instancia de JourneyRealizeHandler en UnbondedBTSignal
        unbondedBTSignal = new UnbondedBTSignal(journeyRealizeHandler);
    }

    @Test
    void testBTbroadcast_noInterrupt() throws ConnectException {
        // Act & Assert
        assertDoesNotThrow(() -> {
            // Llamamos al método BTbroadcast, que debería funcionar sin interrupciones
            unbondedBTSignal.BTbroadcast();
        }, "No debe haber excepciones al emitir el ID de la estación.");
    }

    @Test
    void testBTbroadcast_withInterrupt() {
        // Act & Assert
        assertThrows(ConnectException.class, () -> {
            // Creamos un hilo que ejecute BTbroadcast
            Thread thread = new Thread(() -> {
                try {
                    unbondedBTSignal.BTbroadcast();
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            });

            thread.start();
            // Interrumpimos el hilo después de un tiempo
            Thread.sleep(1000); // Simulamos un breve retraso antes de interrumpir
            thread.interrupt();
            thread.join(); // Espera a que el hilo termine
        }, "Debe lanzar ConnectException al ser interrumpido.");
    }

    @Test
    void testBTbroadcast_withConnectException() {
        // Modificamos el comportamiento de broadcastStationID() para lanzar una excepción
        JourneyRealizeHandler faultyHandler = new JourneyRealizeHandler() {
            @Override
            public void broadcastStationID() {
                // Simulamos un error de conexión
                throw new RuntimeException("Error de conexión");
            }
        };

        unbondedBTSignal = new UnbondedBTSignal(faultyHandler);

        // Act & Assert
        ConnectException exception = assertThrows(ConnectException.class, () -> {
            // Llamamos al método BTbroadcast, lo que debería lanzar una ConnectException
            unbondedBTSignal.BTbroadcast();
        });
        assertEquals("Error de conexión Bluetooth al emitir el ID de estación.", exception.getMessage());
    }
}
