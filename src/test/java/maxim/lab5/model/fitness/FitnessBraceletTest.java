package maxim.lab5.model.fitness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kirill Emelyanov
 */

class FitnessBraceletTest {

    private FitnessBracelet bracelet;

    // инициализация новым объектом перед каждым тестом
    @BeforeEach
    void beforeEach() {
        bracelet = new FitnessBracelet();
        bracelet.setMode(FitnessMode.WALKING);
        bracelet.setSteps(1000L);
    }

    @Test
    @DisplayName("Проверка на корректное изменение режима")
    void changeModeTest() {
       bracelet.changeModeTo(FitnessMode.SKIING);
       assertEquals("лыжи", bracelet.getMode().getName());
    }

    @Test
    @DisplayName("Проверка на корректное изменение режима")
    void addStepsTest() {
       assertEquals(1000L, bracelet.getSteps());

       bracelet.addSteps(2000L);

       assertEquals(3000L, bracelet.getSteps());
    }

    @Test
    @DisplayName("Проверка на корректное число калорий")
    void burnedKcalTest() {
        bracelet.recalculateBurnedKcal();
        assertEquals(140.0, bracelet.getKcal());
    }

    @Test
    @DisplayName("Проверка на корректный сброс данных")
    void resetTest() {
        bracelet.resetHealthData();
        assertEquals(Collections.emptyList(), bracelet.getPulse());
        assertEquals(0.0, bracelet.getKcal());
        assertEquals(0L, bracelet.getSteps());
    }

}