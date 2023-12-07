package maxim.lab5.util;

import maxim.lab5.model.fitness.FitnessBracelet;
import maxim.lab5.model.fitness.FitnessMode;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.parent.UserProfile;
import maxim.lab5.model.pressure.PressureMonitor;
import maxim.lab5.model.watch.Watch;
import maxim.lab5.storage.DeviceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// класс ддля считывания данных с клавиатуры и их валидациии
// считывание некоторых полей вынесено в отдельные методы во избежание
// дублирование кода (некоторые методы используются в нескольких местах)
public class InReader {

    private InReader() {
    }

    private static final Logger log = LoggerFactory.getLogger(InReader.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final DeviceStorage deviceStorage = StorageFactory.getSingleton();

    public static FitnessBracelet readFitnessBracelet() {
        FitnessBracelet fitnessBracelet = new FitnessBracelet();
        readCommonFields(fitnessBracelet);
        fitnessBracelet.setSteps(readSteps());
        fitnessBracelet.setMode(readFitnessMode());
        return fitnessBracelet;
    }

    public static PressureMonitor readPressureMonitor() {
        PressureMonitor pressureMonitor = new PressureMonitor();
        readCommonFields(pressureMonitor);
        return pressureMonitor;
    }

    public static Watch readWatch() {
        Watch watch = new Watch();
        readCommonFields(watch);
        return watch;
    }

    public static String readSerialNumber() {
        return scanner.nextLine().trim();
    }

    public static String readTZ() {
        System.out.println("Введите смещение относительно UTC (всемирное координированное время).");
        System.out.print("Допустимые значения от -12 до 12: ");
        String offset = scanner.nextLine();
        while (!ValidateInput.validInt(offset) || Integer.parseInt(offset) < -12 || Integer.parseInt(offset) > 12) {
            System.out.print("Недопустимое значение. Допустимый диапазон [-12...12]. Попробуйте еще раз: ");
            log.warn("Введено недопустимое значение временной зоны: {}", offset);
            offset = scanner.nextLine();
        }
        return "UTC" + (Integer.parseInt(offset) >= 0 ? "+" : "") + offset;
    }

    public static String readSerialNumberUnique() {
        System.out.print("Введите серийный номер устройства: ");
        String serialNumber = scanner.nextLine().trim();
        while (deviceStorage.getDeviceBySerialNumber(serialNumber) != null) {
            System.out.println("Устройство с таким серийным номером уже есть. Номер должен быть уникальным.");
            System.out.print("Введите серийный номер устройства: ");
            log.warn("Попытка добавить устройство с дублирующимся серийным номером: {}", serialNumber);
            serialNumber = scanner.nextLine().trim();
        }
        return serialNumber;
    }

    public static Long readSteps() {
        System.out.print("Введите количество шагов: ");
        String steps = scanner.nextLine();
        while (!ValidateInput.validLong(steps) || Long.parseLong(steps) <= 0) {
            System.out.println("Некорректный ввод. Число шагов - целое число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите количество шагов пользователя устройства: ");
            log.warn("Попытка ввести некорректное число шагов: {}", steps);
            steps = scanner.nextLine();
        }
        return Long.valueOf(steps);
    }

    public static FitnessMode readFitnessMode() {
        System.out.print("Введите режим фитнесс браслета (бег, плавание, лыжи, ходьба, сон): ");
        String mode = scanner.nextLine();
        while (!FitnessMode.hasFitnessMode(mode)) {
            System.out.println("Некорректный режим работы. Попробуйте еще раз.");
            System.out.print("Введите режим фитнесс браслета: ");
            log.warn("Попытка установить несуществующий режим работы браслета: {}", mode);
            mode = scanner.nextLine();
        }
        return FitnessMode.findFitnessMode(mode);
    }

    public static UserProfile readProfile() {
        UserProfile profile = new UserProfile();
        System.out.print("Введите имя пользователя устройства: ");
        profile.setName(scanner.nextLine());
        System.out.print("Введите возраст пользователя устройства: ");
        String age = scanner.nextLine();
        while (!ValidateInput.validInt(age) || Integer.parseInt(age) <= 0) {
            System.out.println("Некорректный ввод. Возраст - целое число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите возраст пользователя устройства: ");
            log.warn("Попытка установить некорректный возраст: {}", age);
            age = scanner.nextLine();
        }
        profile.setAge(Integer.valueOf(age));
        System.out.print("Введите вес пользователя устройства: ");
        String weight = scanner.nextLine();
        while (!ValidateInput.validFloat(weight) || Float.parseFloat(weight) <= 0) {
            System.out.println("Некорректный ввод. Вес - вещественное число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите вес пользователя устройства: ");
            log.warn("Попытка установить некорректный вес: {}", weight);
            weight = scanner.nextLine();
        }
        profile.setWeight(Float.valueOf(weight));
        System.out.print("Введите рост пользователя устройства: ");
        String height = scanner.nextLine();
        while (!ValidateInput.validFloat(height) || Float.parseFloat(height) <= 0) {
            System.out.println("Некорректный ввод. Рост - вещественное число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите рост пользователя устройства: ");
            log.warn("Попытка установить некорректный рост: {}", height);
            height = scanner.nextLine();
        }
        profile.setHeight(Float.valueOf(height));
        return profile;
    }

    public static List<Integer> readPulse() {
        System.out.print("Введите значения пульса (возможно несколько значений, каждое с новой строки, для завершения введите 'q'): ");
        List<Integer> pulse = new ArrayList<>();
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("q")) {
            if (ValidateInput.validInt(input) && Integer.parseInt(input) > 0) {
                Integer pulseValue = Integer.parseInt(input);
                pulse.add(pulseValue);
            } else {
                log.warn("Попытка добавить некорретный пульс: {}", input);
                System.out.println("Некорректный ввод. Пожалуйста, введите хотя бы одно целое число большее нуля.");
            }
            input = scanner.nextLine();
        }
        return pulse;
    }

    public static Integer readSystolicPressure() {
        System.out.print("Введите систолическое давление: ");
        String systolicPressure = scanner.nextLine();
        while (!ValidateInput.validInt(systolicPressure) || Integer.parseInt(systolicPressure) <= 0) {
            System.out.println("Некорректный ввод. Давление - целое число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите систолическое давление: ");
            log.warn("Попытка добавить некорректное давление: {}", systolicPressure);
            systolicPressure = scanner.nextLine();
        }
        return Integer.parseInt(systolicPressure);
    }

    public static Integer readDiastolicPressure() {
        System.out.print("Введите диастолическое давление: ");
        String diastolicPressure = scanner.nextLine();
        while (!ValidateInput.validInt(diastolicPressure) || Integer.parseInt(diastolicPressure) <= 0) {
            System.out.println("Некорректный ввод. Давление - целое число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите диастолическое давление: ");
            log.warn("Попытка добавить некорректное давление: {}", diastolicPressure);
            diastolicPressure = scanner.nextLine();
        }
        return Integer.parseInt(diastolicPressure);
    }

    private static void readCommonFields(Device device) {
        System.out.print("Введите имя устройства: ");
        device.setName(scanner.nextLine());
        System.out.print("Введите уровень заряда: ");
        String batteryLevel = scanner.nextLine();
        while (!ValidateInput.validInt(batteryLevel) || Integer.parseInt(batteryLevel) <= 0 || Integer.parseInt(batteryLevel) >= 100) {
            System.out.println("Некорректный ввод. Уровень заряда - целое число от 0 до 100. Попробуйте еще раз.");
            System.out.print("Введите уровень заряда: ");
            log.warn("Попытка установить некорректное значение заряда аккумулятора: {}", batteryLevel);
            batteryLevel = scanner.nextLine();
        }
        device.setBatteryLevel(Integer.valueOf(batteryLevel));
        device.setSerialNumber(readSerialNumberUnique());
        device.setProfile(readProfile());
        device.setPulse(readPulse());
    }
}
