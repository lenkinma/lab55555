package maxim.lab5.model.watch;


import com.fasterxml.jackson.annotation.JsonFormat;
import maxim.lab5.model.parent.Device;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.OptionalDouble;

import static maxim.lab5.util.Constants.formatter;
import static maxim.lab5.util.Constants.formatterAsString;

public class Watch extends Device {

    private LocalDateTime timer;
    private ZoneId zoneId = ZoneId.systemDefault();
    private ZonedDateTime currentTime = ZonedDateTime.now();

    // получить текущее время с заданным часовым поясом
    public void showCurrentTimeWithOffset() {
        currentTime = ZonedDateTime.now().withZoneSameInstant(zoneId);
        System.out.println("Текущее время: " + currentTime.format(formatter) + " ("
                + zoneId.getDisplayName(TextStyle.NARROW, Locale.ROOT) + ")");
    }

    // смена часового пояса
    public void changeTimeZone(ZoneId zoneId) {
        this.zoneId = zoneId;
        System.out.println("Изменена временная зона. Текущая временная зона: "
                + zoneId.getDisplayName(TextStyle.NARROW, Locale.ROOT));
    }

    // сброс таймера, пульса и установленного часового пояса
    public void reset() {
        this.zoneId = ZoneId.systemDefault();
        this.timer = null;
        this.pulse.clear();
        System.out.println("Время установлено на системное. Данные о пульсе сброшены. Таймер отключен.");
    }

    // утсановка системного времени
    public void setUpSystemTimezone() {
        this.zoneId = ZoneId.systemDefault();
        System.out.println("Установлена временная зона, использующаяся в вашем компьюетере по умолчанию.");
    }

    // старт таймер
    public void startTimer() {
        if (timer == null) {
            timer = LocalDateTime.now();
            System.out.println("Таймер запущен.");
        } else {
            System.out.println("Таймер уже запущен.");
        }

    }

    // стоп таймер
    public void stopTimer() {
        if (timer != null) {
            System.out.println("Таймер остановлен. Секунд прошло: " + Duration.between(timer, LocalDateTime.now()).toSeconds());
            timer = null;
        } else {
            System.out.println("Таймер не был запущен");
        }
    }

    // получение отчета
    @Override
    public void getReport() {
        currentTime = ZonedDateTime.now().withZoneSameInstant(zoneId);
        StringBuilder sb = new StringBuilder();
        sb.append("Отчет для пользователя ").append(this.profile.getName()).append("\n");

        OptionalDouble averagePulse = pulse.stream()
                .mapToInt(Integer::intValue)
                .average();

        if (averagePulse.isPresent()) {
            sb.append("Средний пульс: ").append(String.format("%.2f", averagePulse.getAsDouble())).append("\n");
        } else {
            sb.append("Данных о пульсе нет").append("\n");
        }

        sb.append("Текущее всемирное время: ")
                .append(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC-0")).format(formatter)).append("\n");
        sb.append("Текущее время в системе: ").append(LocalDateTime.now().format(formatter)).append("\n");
        sb.append("Текущее установленное на часах время: ").append(currentTime.format(formatter)).append("\n");
        if (timer == null) {
            sb.append("Таймер отключен.").append("\n");
        } else {
            sb.append("Таймер включен.").append("\n");
        }

        System.out.println(sb);
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = formatterAsString)
    public LocalDateTime getTimer() {
        return timer;
    }

    public void setTimer(LocalDateTime timer) {
        this.timer = timer;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public void setCurrentTime(ZonedDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = formatterAsString)
    public ZonedDateTime getCurrentTime() {
        return currentTime.withZoneSameInstant(zoneId);
    }

    @Override
    public String toString() {
        return "Watch{" +
                "timer=" + (timer != null ? timer.format(formatter) : "Нет данных") +
                ", zoneId=" + zoneId +
                ", currentTime=" + (currentTime != null ? currentTime.format(formatter) : "Нет данных") +
                ", name='" + name + '\'' +
                ", batteryLevel=" + batteryLevel +
                ", serialNumber='" + serialNumber + '\'' +
                ", isPutOn=" + isPutOn +
                ", profile=" + profile +
                ", pulse=" + pulse +
                '}';
    }
}
