package maxim.lab5;

import maxim.lab5.model.fitness.FitnessBracelet;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.pressure.PressureMonitor;
import maxim.lab5.storage.DeviceStorage;
import maxim.lab5.util.InReader;
import maxim.lab5.util.StorageFactory;
import maxim.lab5.util.ValidateInput;

import java.util.Locale;
import java.util.Scanner;

import static maxim.lab5.util.StringConstants.*;


public class Main {

    private static final DeviceStorage storage = StorageFactory.getSingleton();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(MENU);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        while (!command.equals("exit")) {
            switch (command) {
                case "add" -> addDevice();
                case "delete" -> removeDevice();
                case "update" -> updateDevice();
                case "process" -> processDevice();
                case "display" -> storage.displayDevices();
                default -> System.out.println("Такой команды нет");
            }

            System.out.println(MENU);
            command = scanner.nextLine();
        }
    }

    // взаимодействие с устройствами
    private static void processDevice() {
        System.out.print("Введите серийный номер устройства для взаимодействия: ");
        String serialNumber = InReader.readSerialNumber();
        Device device = storage.getDeviceBySerialNumber(serialNumber);
        if (device == null) {
            System.out.println("Устройство с таким серийным номером не найдено.");
            return;
        }
        if (device instanceof FitnessBracelet) {
            processFitnessBracelet((FitnessBracelet) device);
        } else if (device instanceof PressureMonitor) {
            processPressureMonitor((PressureMonitor) device);
        }
    }

    private static void processPressureMonitor(PressureMonitor pressureMonitor) {
        System.out.println(PROCESS_MONITOR);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        switch (command) {
            case "battery" -> {
                System.out.print("Измените заряд устройства (отрицательное число - убавить заряд, положительное - прибавить): ");
                String percentage = scanner.nextLine();
                while (!ValidateInput.validInt(percentage)) {
                    System.out.println("Некорректный ввод. Попробуйте еще раз.");
                    System.out.print("Введите уровень заряда: ");
                    percentage = scanner.nextLine();
                }
                pressureMonitor.chargeOrDischarge(Integer.valueOf(percentage));
            }
            case "on" -> pressureMonitor.putOn();
            case "off" -> pressureMonitor.takeOff();
            case "reset" -> pressureMonitor.resetMeasures();
            case "report" -> pressureMonitor.getReport();
            case "mode" -> pressureMonitor.switchMode();
            case "addpulse" -> pressureMonitor.addPulse(InReader.readPulse());
            case "measure" -> pressureMonitor.newMeasure(InReader.readSystolicPressure(), InReader.readDiastolicPressure());
            case "check" -> pressureMonitor.checkIfCurrentPressureIsHighAndDisplay();
            case "show" -> System.out.println(pressureMonitor.toString());
            default -> System.out.println("Такой команды нет");
        }
    }

    // взаимодействие с фитнесс-браслетом
    private static void processFitnessBracelet(FitnessBracelet bracelet) {
        System.out.println(PROCESS_FITNESS);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        switch (command) {
            case "battery" -> {
                System.out.print("Измените заряд устройства (отрицательное число - убавить заряд, положительное - прибавить): ");
                String percentage = scanner.nextLine();
                while (!ValidateInput.validInt(percentage)) {
                    System.out.println("Некорректный ввод. Попробуйте еще раз.");
                    System.out.print("Введите уровень заряда: ");
                    percentage = scanner.nextLine();
                }
                bracelet.chargeOrDischarge(Integer.valueOf(percentage));
            }
            case "on" -> bracelet.putOn();
            case "off" -> bracelet.takeOff();
            case "reset" -> bracelet.resetHealthData();
            case "report" -> bracelet.getReport();
            case "kcal" -> bracelet.displayBurnedKcal();
            case "mode" -> bracelet.changeModeTo(InReader.readFitnessMode());
            case "addsteps" -> bracelet.addSteps(InReader.readSteps());
            case "addpulse" -> bracelet.addPulse(InReader.readPulse());
            case "show" -> System.out.println(bracelet.toString());
            default -> System.out.println("Такой команды нет");
        }
    }

    // обновление параметров устройства по серийному номеру (выступает как идентификатор)
    private static void updateDevice() {
        System.out.print("Введите серийный номер устройства для обновления: ");
        String serialNumber = InReader.readSerialNumber();
        storage.updateBySerialNumber(serialNumber);
    }

    // удаление устройства по серийному номеру (выступает как идентификатор)
    private static void removeDevice() {
        System.out.print("Введите серийный номер устройства для удаления: ");
        String serialNumber = InReader.readSerialNumber();
        storage.deleteBySerialNumber(serialNumber);
    }

    // добавление нового устройства
    private static void addDevice() {
        System.out.println(DEVICE_TYPE);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT);
        switch (command) {
            case "fitness" -> {
                FitnessBracelet fitnessBracelet = InReader.readFitnessBracelet();
                storage.add(fitnessBracelet);
                System.out.println("Фитнесс браслет добавлен.");
            }
            case "tono" -> {
                PressureMonitor pressureMonitor = InReader.readPressureMonitor();
                storage.add(pressureMonitor);
                System.out.println("Тонометр добавлен.");
            }
            case "watch" -> {

            }
            default -> System.out.println("Такого устройства нет.");
        }
    }

}
