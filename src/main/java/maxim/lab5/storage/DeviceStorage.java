package maxim.lab5.storage;

import maxim.lab5.model.parent.Device;
import maxim.lab5.util.InReader;
import maxim.lab5.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static maxim.lab5.util.Constants.UPDATE_FITNESS;


// класс-хранилище списка устройств
public class DeviceStorage {

    private final List<Device> devices = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(DeviceStorage.class);

    public void add(Device device) {
        devices.add(device);
    }

    // краткое отображение информации о всех устройствах
    public void displayDevices() {
        log.info("Пользователь просмотрел список устройств");

        if (devices.isEmpty()) {
            System.out.println("Устройств нет.");
        }

        for (Device device : devices) {
            System.out.println(device.getName() + "(" + device.getClass().getSimpleName() + ") - серийный номер " +
                    device.getSerialNumber() + " - пользователь: " + device.getProfile().getName());
        }
    }

    // удаление устройства по серийному номеру
    public void deleteBySerialNumber(String serialNumber) {
        Device deviceToRemove = findDeviceBySerialNumber(serialNumber);

        if (deviceToRemove != null) {
            devices.remove(deviceToRemove);
            log.info("Пользователь удалил устройство: {}", serialNumber);
            System.out.println("Устройство успешно удалено");
        } else {
            log.warn("Попытка удалить устройство с несуществующим серийным номером: {}", serialNumber);
            System.out.println("Устройства с таким серийным номером нет");
        }
    }

    // обновление устройства по серийному номеру
    public void updateBySerialNumber(String serialNumber) {
        Device deviceToUpdate = findDeviceBySerialNumber(serialNumber);
        if (deviceToUpdate != null) {
            update(deviceToUpdate);
        } else {
            System.out.println("Устройства с таким id нет");
        }
    }

    //сохранение json
    public void saveJSON() {
        JsonHelper.saveJSON(devices);
        log.info("Данные об устройствах записаны в json");
    }

    //считывание json
    public void loadJSON() {
        List<Device> jsonDevices = JsonHelper.readJSON();
        for (Device device : jsonDevices) {
            String serial = device.getSerialNumber();
            if (isContains(serial)) {
                System.out.println("В текущем списке уже есть устройство с серийным номером " + serial + ". Пропуск...");
                log.warn("Дубликат серийного номера: {}. Пропуск...", serial);
            } else {
                System.out.println("Устройство " +
                        device.getName() + "(серийный номер " + serial + ") добавлено.");
                devices.add(device);
            }
        }
        log.info("Данные об устройствах считаны из json");
    }

    private boolean isContains(String serial) {
        return devices.stream()
                .anyMatch(device -> serial.equals(device.getSerialNumber()));
    }

    // обновление полей
    // логичным показлось не делать возможным обновление всех полей устройства
    // пример: количество шагов задается путем "взаимодействия" с устройством, а конкретно с фитнесс браслетом
    // пример 2: давление, опять же, можно обновить взаимодействую с тонометром
    // пример 3: смена временной зоны в часах, опять же, производиться путем взаимодействия с ними, а не обновлением
    private void update(Device deviceToUpdate) {
        System.out.println(UPDATE_FITNESS);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        switch (command) {
            case "name" -> {
                System.out.print("Введите новое имя устройства: ");
                deviceToUpdate.setName(scanner.nextLine());
                System.out.println("Имя устройства обновлено.");
                log.info("Пользователь обновил имя устройства с серийным номером: {}", deviceToUpdate.getSerialNumber());
            }
            case "serial" -> {
                System.out.print("Введите новый серийный номер: ");
                deviceToUpdate.setSerialNumber(scanner.nextLine());
                System.out.println("Серийный номер устройства обновлен.");
                log.info("Пользователь обновил серийный номер устройства: {}", deviceToUpdate.getSerialNumber());

            }
            case "user" -> {
                System.out.println("Введите новые данные пользователя: ");
                deviceToUpdate.setProfile(InReader.readProfile());
                System.out.println("Данные пользователя обновлены.");
                log.info("Пользователь обновил данные о пользователе на устройстве с серийным номером: {}", deviceToUpdate.getSerialNumber());

            }
            default -> {
                System.out.println("Такого параметра нет.");
                log.warn("Попытка обновить несуществующий параметр: {}", command);
            }
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
