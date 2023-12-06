package maxim.lab5.model.pressure;

import maxim.lab5.model.parent.Device;
import maxim.lab5.util.InReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


public class PressureMonitor extends Device {

    private Integer lastSystolicPressure;
    private Integer lastDiastolicPressure;
    private LocalDateTime lastMeasurementTime;
    private List<String> pressureData = new ArrayList<>();
    private boolean isAutomaticMeasurementMode = false; // по умолчанию "автоматический" режим измерения выключен
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public void newMeasure(Integer systolicPressure, Integer diastolicPressure) {
        this.lastSystolicPressure = systolicPressure;
        this.lastDiastolicPressure = diastolicPressure;
        this.lastMeasurementTime = LocalDateTime.now();
        pressureData.add(systolicPressure + "/" + diastolicPressure + " " + lastMeasurementTime.format(formatter));
    }

    public void resetMeasures() {
        this.pressureData.clear();
        this.pulse.clear();
    }

    public void switchMode() {
        isAutomaticMeasurementMode = !isAutomaticMeasurementMode;
    }

    public void checkIfCurrentPressureIsHighAndDisplay() {
        if (lastSystolicPressure == null || lastDiastolicPressure == null) {
            System.out.println("Данных о давлении нет.");
        } else if (lastSystolicPressure > 130 && lastDiastolicPressure < 80) {
            System.out.println("У вас высокое систолическое давление: " + lastSystolicPressure + "/" +
                    lastDiastolicPressure + " Время измерения: " + lastMeasurementTime.format(formatter));
        } else if (lastSystolicPressure < 120 && lastDiastolicPressure > 95) {
            System.out.println("У вас высокое диастолическое давление: " + lastSystolicPressure + "/" +
                    lastDiastolicPressure + " Время измерения: " + lastMeasurementTime.format(formatter));
        } else if (lastSystolicPressure > 130 && lastDiastolicPressure > 95) {
            System.out.println("У вас высокое давление: " + lastSystolicPressure + "/" +
                    lastDiastolicPressure + " Время измерения: " + lastMeasurementTime.format(formatter));
        } else {
            System.out.println("У вас нормальное давление: " + lastSystolicPressure + "/" +
                    lastDiastolicPressure + " Время измерения: " + lastMeasurementTime.format(formatter));
        }
    }

    @Override
    public void getReport() {
        StringBuilder sb = new StringBuilder();
        OptionalDouble averagePulse = pulse.stream()
                .mapToInt(Integer::intValue)
                .average();

        sb.append("Отчет для пользователя ").append(this.profile.getName()).append("\n");

        if (averagePulse.isPresent()) {
            sb.append("Средний пульс: ").append(String.format("%.2f", averagePulse.getAsDouble())).append("\n");
        } else {
            sb.append("Данных о пульсе нет").append("\n");
        }

        if (pressureData.isEmpty()) {
            sb.append("Данных об измерениях давления нет");
        } else {
            int totalSystolicPressure = 0;
            int totalDiastolicPressure = 0;
            int measurementCount = 0;

            for (String measurement : pressureData) {
                String[] parts = measurement.split(" ");
                String pressure = parts[0];
                String[] pressureValues = pressure.split("/");
                int systolicPressure = Integer.parseInt(pressureValues[0]);
                int diastolicPressure = Integer.parseInt(pressureValues[1]);

                totalSystolicPressure += systolicPressure;
                totalDiastolicPressure += diastolicPressure;
                measurementCount++;
            }

            int averageSystolicPressure = totalSystolicPressure / measurementCount;
            int averageDiastolicPressure = totalDiastolicPressure / measurementCount;
            sb.append("Среднее систолическое давление: ").append(averageSystolicPressure).append("\n");
            sb.append("Среднее диастолическое давление: ").append(averageDiastolicPressure).append("\n");
        }

        System.out.println(sb);
    }

    @Override
    public String toString() {
        return "PressureMonitor{" +
                "lastSystolicPressure=" + lastSystolicPressure +
                ", lastDiastolicPressure=" + lastDiastolicPressure +
                ", lastMeasurementTime=" + lastMeasurementTime.format(formatter) +
                ", pressureData=" + pressureData +
                ", isAutomaticMeasurementMode=" + (isAutomaticMeasurementMode ? "Автоматический" : "Ручной") +
                ", name='" + name + '\'' +
                ", batteryLevel=" + batteryLevel +
                ", serialNumber='" + serialNumber + '\'' +
                ", isPutOn=" + isPutOn +
                ", profile=" + profile +
                ", pulse=" + pulse +
                '}';
    }
}
