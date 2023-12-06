package maxim.lab5.storage;

import maxim.lab5.model.fitness.FitnessBracelet;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.pressure.PressureMonitor;
import maxim.lab5.util.InReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static maxim.lab5.util.StringConstants.UPDATE_FITNESS;


// класс-хранилище списка устройств
public class DeviceStorage {

    private final List<Device> devices = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void add(Device device) {
        devices.add(device);
    }

    // краткое отображение информации о всех устройствах
    public void displayDevices() {
        if (devices.isEmpty()) {
            System.out.println("Устройств нет.");
        }

        for (Device device : devices) {
            System.out.println(device.getName() + "(" + device.getClass().getSimpleName() + ") - серийный номер " +
                    device.getSerialNumber() + " - пользователь: " + device.getProfile().getName()) ;
        }
    }

    // удаление устройства по серийному номеру
    public void deleteBySerialNumber(String serialNumber) {
        Device deviceToRemove = findDeviceBySerialNumber(serialNumber);

        if (deviceToRemove != null) {
            devices.remove(deviceToRemove);
            System.out.println("Устройство успешно удалено");
        } else {
            System.out.println("Устройства с таким id нет");
        }
    }

    // обновление устройства по серийному номеру
    public void updateBySerialNumber(String serialNumber) {
        Device deviceToUpdate = findDeviceBySerialNumber(serialNumber);

        if (deviceToUpdate != null) {
            if (deviceToUpdate instanceof FitnessBracelet) {
                updateFitnessBracelet((FitnessBracelet) deviceToUpdate);
            } else if (deviceToUpdate instanceof PressureMonitor) {
                updatePressureMonitor((PressureMonitor) deviceToUpdate);
            }

        } else {
            System.out.println("Устройства с таким id нет");
        }
    }

    private void updatePressureMonitor(PressureMonitor deviceToUpdate) {

    }

    // обновление полей фитнесс браслета
    // логичным показлось не делать возможным обновление всех полей устройства
    // пример: количество шагов задается путем "взаимодействия" с устройством (там их можно добавить или
    // сбросить инфомарцию о пройденных шагах)
    private void updateFitnessBracelet(FitnessBracelet deviceToUpdate) {
        System.out.println(UPDATE_FITNESS);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        switch (command) {
            case "name" -> {
                System.out.print("Введите новое имя: ");
                deviceToUpdate.setName(scanner.nextLine());
            }
            case "serial" -> {
                System.out.print("Введите новый серийный номер: ");
                deviceToUpdate.setSerialNumber(scanner.nextLine());
            }
            case "user" -> {
                System.out.println("Введите новые данные пользователя: ");
                deviceToUpdate.setProfile(InReader.readProfile());
            }
            default -> System.out.println("Такого параметра нет.");
        }
    }

    // получение устройства по серийному номеру
    public Device getDeviceBySerialNumber(String serialNumber) {
        return findDeviceBySerialNumber(serialNumber);
    }

    // поиск устройства по серийному номеру
    private Device findDeviceBySerialNumber(String serialNumber) {
        for (Device device : devices) {
            if (device.getSerialNumber().equals(serialNumber)) {
                return device;
            }
        }
        return null;
    }

}
