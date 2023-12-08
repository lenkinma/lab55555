package maxim.lab5.model.watch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class WatchTest {

    private Watch watch;

    // инициализация новым объектом перед каждым тестом
    @BeforeEach
    void beforeEach() {
        watch = new Watch();
    }

    @Test
    @DisplayName("Проверка на корректное добавление пульса")
    void addPulseTest() {
        List<Integer> pulseToAdd = new java.util.ArrayList<>(List.of(90, 100));
        watch.addPulse(pulseToAdd);

        assertEquals(pulseToAdd, watch.getPulse());

        pulseToAdd.add(110); // добавим еще одну запись о пульсе и проверим
        watch.addPulse(List.of(110)); // что она успешно добавилась к уже имеющимся

        assertEquals(pulseToAdd, watch.getPulse());
    }

    @Test
    @DisplayName("Проверка на корректный сброс данных")
    void resetTest() {
        List<Integer> pulseToAdd = new java.util.ArrayList<>(List.of(90, 100));
        watch.addPulse(pulseToAdd);

        assertEquals(pulseToAdd, watch.getPulse());

        watch.reset(); // сбросим данные, в т.ч. пульс

        assertEquals(Collections.emptyList(), watch.getPulse());
    }

    @Test
    @DisplayName("Проверка корректной установки другого часового пояса")
    void changeTimeZoneTest() {
        watch.changeTimeZone(ZoneId.of("UTC+5"));

        assertEquals(ZoneId.of("UTC+5"), watch.getZoneId());

        LocalDateTime localDateTime = LocalDateTime.now(); // получим текущее время (UTC+3)
        LocalDateTime zonedDateTime = watch.getCurrentTime().toLocalDateTime(); // получим установленное время UTC+5
        Duration duration = Duration.between(zonedDateTime, localDateTime); // найдем разницу, должна быть 2 часа

        assertEquals(-2, duration.toHours()); // проверим
    }

    @Test
    @DisplayName("Проверка корректной установки системного часового пояса")
    void setupSystemTimeZoneTest() {
        watch.changeTimeZone(ZoneId.of("UTC+5"));

        assertEquals(ZoneId.of("UTC+5"), watch.getZoneId());

        watch.setUpSystemTimezone();

        assertEquals(ZoneId.systemDefault(), watch.getZoneId());
    }

    @Test
    @DisplayName("Проверка на инициализацию таймера при запуске и присваивание null при остановке")
    void timerTest() {
        watch.startTimer();
        assertNotNull(watch.getTimer());
        watch.stopTimer();
        assertNull(watch.getTimer());
    }

}