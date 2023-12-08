package maxim.lab5.model.pressure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kirill Emelyanov
 */

class PressureMonitorTest {

    private PressureMonitor monitor;

    // инициализация новым объектом перед каждым тестом
    @BeforeEach
    void beforeEach() {
        monitor = new PressureMonitor();
    }

    @Test
    @DisplayName("Проверка на добавление данных измерения давления")
    void newMeasureTest() {
        Integer systolic = 120;
        Integer diastolic = 90;

        monitor.newMeasure(systolic, diastolic);

        assertEquals(systolic, monitor.getLastSystolicPressure());
        assertEquals(diastolic, monitor.getLastDiastolicPressure());
        assertNotNull(monitor.getLastMeasurementTime());
    }

    @Test
    @DisplayName("Проверка на сохранение измерения в список")
    void pressureDataTest() {
        Integer systolic = 130;
        Integer diastolic = 95;

        monitor.newMeasure(systolic, diastolic);

        assertEquals("130/95", monitor.getPressureData().get(0).substring(0, 6));
    }

    @Test
    @DisplayName("Проверка на корректное изменение режима работы")
    void switchModeTest() {
        assertFalse(monitor.isAutomaticMeasurementMode());
        monitor.switchMode();
        assertTrue(monitor.isAutomaticMeasurementMode());
    }

    @Test
    @DisplayName("Проверка на корректный сброс данных")
    void resetTest() {
        Integer systolic = 130;
        Integer diastolic = 95;

        monitor.newMeasure(systolic, diastolic);
        monitor.resetMeasures();

        assertNull(monitor.getLastDiastolicPressure());
        assertNull(monitor.getLastSystolicPressure());
        assertNull(monitor.getLastMeasurementTime());
        assertEquals(Collections.emptyList(), monitor.getPressureData());
    }

}