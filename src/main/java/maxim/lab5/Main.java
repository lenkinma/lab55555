package maxim.lab5;

import maxim.lab5.model.fitness.FitnessBracelet;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.pressure.PressureMonitor;
import maxim.lab5.model.watch.Watch;
import maxim.lab5.storage.DeviceStorage;
import maxim.lab5.util.InReader;
import maxim.lab5.util.StorageFactory;
import maxim.lab5.util.ValidateInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Scanner;

import static maxim.lab5.util.Constants.*;


public class Main {

    private static final DeviceStorage storage = StorageFactory.getSingleton();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("Приложение запущено");

        System.out.println(MENU);
        String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
        while (!command.equals("exit")) {
            switch (command) {
                case "add" -> addDevice();
                case "delete" -> removeDevice();
                case "update" -> updateDevice();
                case "process" -> processDevice();
                case "display" -> storage.displayDevices();
                case "save" -> storage.saveJSON();
                case "load" -> storage.loadJSON();
                default -> {
                    log.warn("Попытка ввода несуществующей команды: {}", command);
                    System.out.println("Такой команды нет");
                }
            }

            System.out.println(MENU);
            command = scanner.nextLine();
        }
        log.info("Приложение остановлено");
    }

    // взаимодействие с устройствами
    private static void processDevice() {
        System.out.print("Введите серийный номер устройства для взаимодействия: ");
        String serialNumber = InReader.readSerialNumber();
        Device device = storage.getDeviceBySerialNumber(serialNumber);
        if (device == null) {
            System.out.println("Устройство с таким серийным номером не найдено.");
            log.warn("Устройства с серийным номером {} нет", serialNumber);
            return;
        }
        log.info("Пользователь взаимодействует с устройством с серийным номером {}", serialNumber);
        if (device instanceof FitnessBracelet) {
            processFitnessBracelet((FitnessBracelet) device);
        } else if (device instanceof PressureMonitor) {
            processPressureMonitor((PressureMonitor) device);
        } else if (device instanceof Watch) {
            processWatch((Watch) device);
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
                log.info("Изменен заряд тонометра: {}", pressureMonitor.getSerialNumber());
                pressureMonitor.chargeOrDischarge(Integer.valueOf(percentage));
            }
            case "on" -> {
                pressureMonitor.putOn();
                log.info("Пользователь надел тонометр: {}", pressureMonitor.getSerialNumber());

            }
            case "off" -> {
                pressureMonitor.takeOff();
                log.info("Пользователь снял тонометр: {}", pressureMonitor.getSerialNumber());

            }
            case "reset" -> {
                pressureMonitor.resetMeasures();
                log.info("Пользователь сбросил данные об измерениях с тонометра: {}", pressureMonitor.getSerialNumber());

            }
            case "report" -> {
                pressureMonitor.getReport();
                log.info("Пользователь запросил отчет с тонометра: {}", pressureMonitor.getSerialNumber());

            }
            case "mode" -> {
                pressureMonitor.switchMode();
                log.info("Пользователь изменил режим работы тонометра: {}", pressureMonitor.getSerialNumber());

            }
            case "addpulse" -> {
                pressureMonitor.addPulse(InReader.readPulse());
                log.info("Пользователь добавил новые данные о пульсе на тонометр: {}", pressureMonitor.getSerialNumber());

            }
            case "measure" -> {
                pressureMonitor.newMeasure(InReader.readSystolicPressure(), InReader.readDiastolicPressure());
                log.info("Пользователь добавил новое измерение давления на тонометр: {}", pressureMonitor.getSerialNumber());

            }
            case "check" -> {
                pressureMonitor.checkIfCurrentPressureIsHighAndDisplay();
                log.info("Пользователь просмотрел инфомрацию о давлении с тонометра: {}", pressureMonitor.getSerialNumber());

            }
            case "show" -> {
                System.out.println(pressureMonitor.toString());
                log.info("Пользователь просмотрел инфомрацию об тонометре: {}", pressureMonitor.getSerialNumber());
            }
            default -> {
                System.out.println("Такой команды нет");
                log.warn("Попытка ввода несуществующей команды: {}", command);
            }
        }
    }

    private static void processWatch(Watch watch) {
        System.out.println(PROCESS_WATCH);
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
                log.info("Изменен заряд часов: {}", watch.getSerialNumber());
                watch.chargeOrDischarge(Integer.valueOf(percentage));
            }
            case "on" -> {
                watch.putOn();
                log.info("Пользователь надел часы: {}", watch.getSerialNumber());
            }
            case "off" -> {
                watch.takeOff();
                log.info("Пользователь снял часы: {}", watch.getSerialNumber());
            }
            case "reset" -> {
                watch.reset();
                log.info("Пользователь сбросил данные с часов: {}", watch.getSerialNumber());
            }
            case "report" -> {
                watch.getReport();
                log.info("Пользователь запросил отчет с часов: {}", watch.getSerialNumber());
            }
            case "addpulse" -> {
                watch.addPulse(InReader.readPulse());
                log.info("Пользователь добавил данные о пульсе на часы: {}", watch.getSerialNumber());
            }
            case "start" -> {
                watch.startTimer();
                log.info("Пользователь запустил таймер на часах: {}", watch.getSerialNumber());
            }
            case "stop" -> {
                watch.stopTimer();
                log.info("Пользователь остановил таймер на часах: {}", watch.getSerialNumber());
            }
            case "time" -> {
                watch.showCurrentTimeWithOffset();
                log.info("Пользователь просмотрел текущее время на часах: {}", watch.getSerialNumber());
            }
            case "systz" -> {
                watch.setUpSystemTimezone();
                log.info("Пользователь установил временную зону, используюяся текущей системой на часах: {}", watch.getSerialNumber());
            }
            case "tz" -> {
                watch.changeTimeZone(ZoneId.of(InReader.readTZ()));
                log.info("Пользователь установил новую временную зону на часах: {}", watch.getSerialNumber());
            }
            case "show" -> {
                System.out.println(watch.toString());
                log.info("Пользователь запросил информацию о часах: {}", watch.getSerialNumber());
            }
            default -> {
                System.out.println("Такой команды нет");
                log.warn("Попытка ввода несуществующей команды: {}", command);
            }
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
                log.info("Изменен заряд фитнесс браслета: {}", bracelet.getSerialNumber());
                bracelet.chargeOrDischarge(Integer.valueOf(percentage));
            }
            case "on" -> {
                bracelet.putOn();
                log.info("Пользователь надел фитнес браслет: {}", bracelet.getSerialNumber());
            }
            case "off" -> {
                bracelet.takeOff();
                log.info("Пользователь снял фитнесс браслет: {}", bracelet.getSerialNumber());
            }
            case "reset" -> {
                bracelet.resetHealthData();
                log.info("Пользователь сбросил данные с фитнесс браслета: {}", bracelet.getSerialNumber());
            }
            case "report" -> {
                bracelet.getReport();
                log.info("Пользователь запросил отчет с фитнесс браслета: {}", bracelet.getSerialNumber());
            }
            case "kcal" -> {
                bracelet.displayBurnedKcal();
                log.info("Пользователь запросил кол-во калорий с фитнесс браслета: {}", bracelet.getSerialNumber());
            }
            case "mode" -> {
                bracelet.changeModeTo(InReader.readFitnessMode());
                log.info("Пользователь изменил режим работы фитнесс браслета: {}", bracelet.getSerialNumber());
            }
            case "addsteps" -> {
                bracelet.addSteps(InReader.readSteps());
                log.info("Пользователь добавил данные о шагах с фитнесс браслета: {}", bracelet.getSerialNumber());
            }
            case "addpulse" -> {
                bracelet.addPulse(InReader.readPulse());
                log.info("ИПользователь добавил данные о пульсе на фитнесс браслет: {}", bracelet.getSerialNumber());
            }
            case "show" -> {
                System.out.println(bracelet.toString());
                log.info("Пользователь запрос инфомарцию о фитнесс браслете: {}", bracelet.getSerialNumber());
            }
            default -> {
                System.out.println("Такой команды нет");
                log.warn("Попытка ввода несуществующей команды: {}", command);
            }
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
                fitnessBracelet.recalculateBurnedKcal();
                storage.add(fitnessBracelet);
                System.out.println("Фитнесс браслет добавлен.");
                log.info("Добавлен новый фитнесс браслет: {}", fitnessBracelet.getSerialNumber());
            }
            case "tono" -> {
                PressureMonitor pressureMonitor = InReader.readPressureMonitor();
                storage.add(pressureMonitor);
                System.out.println("Тонометр добавлен.");
                log.info("Добавлен новый тонометр: {}", pressureMonitor.getSerialNumber());
            }
            case "watch" -> {
                Watch watch = InReader.readWatch();
                storage.add(watch);
                System.out.println("Часы добавлены.");
                log.info("Добавлены новые часы: {}", watch.getSerialNumber());
            }
            default -> {
                System.out.println("Такого устройства нет.");
                log.warn("Попытка добавления несуществующего устройства: {}", command);
            }
        }
    }

}
