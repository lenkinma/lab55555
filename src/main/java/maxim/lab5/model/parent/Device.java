package maxim.lab5.model.parent;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import maxim.lab5.model.fitness.FitnessBracelet;
import maxim.lab5.model.pressure.PressureMonitor;
import maxim.lab5.model.watch.Watch;

import java.util.ArrayList;
import java.util.List;


// абстрактный класс родитель
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Watch.class, name = "Watch"),
        @JsonSubTypes.Type(value = PressureMonitor.class, name = "PressureMonitor"),
        @JsonSubTypes.Type(value = FitnessBracelet.class, name = "FitnessBracelet")
})
public abstract class Device {

    protected String name;
    protected Integer batteryLevel;
    protected String serialNumber;
    protected Boolean isPutOn = false; // по умолчанию снято
    protected UserProfile profile;
    protected List<Integer> pulse = new ArrayList<>();

    protected Device() {
    }

    protected Device(String name, Integer batteryLevel, String serialNumber, Boolean isPutOn, UserProfile profile, List<Integer> pulse) {
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.serialNumber = serialNumber;
        this.isPutOn = isPutOn;
        this.profile = profile;
        this.pulse = pulse;
    }

    // общая реализация для всех наследников
    public void putOn() {
        isPutOn = true;
        System.out.println("Устройство " + name + " надето.");
    }

    // общая реализация для всех наследников
    public void takeOff() {
        isPutOn = false;
        System.out.println("Устройство " + name + " снято.");
    }

    // общая реализация для всех наследников
    public void chargeOrDischarge(Integer percentage) {
        if (batteryLevel + percentage > 100) {
            this.batteryLevel = 100;
        } else {
            batteryLevel += percentage;
        }
        System.out.println("Заряд изменен. Текущий заряд: " + batteryLevel);
    }

    // общая реализация для всех наследников
    public void addPulse(List<Integer> pulse) {
        this.pulse.addAll(pulse);
    }

    // каждый наследник будет реализовывать этот абстрактный метод
    // подразумевается, что различные устройства могут генерировать различные "отчеты", следовательно
    // метод стоит объявить абстрактным
    public abstract void getReport();

    // геттеры и сеттеры, преимущественно используются только при тестировании

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean getPutOn() {
        return isPutOn;
    }

    public void setPutOn(Boolean putOn) {
        isPutOn = putOn;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Integer> getPulse() {
        return pulse;
    }

    public void setPulse(List<Integer> pulse) {
        this.pulse = pulse;
    }
}
