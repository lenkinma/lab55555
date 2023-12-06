package maxim.lab5.util;

import maxim.lab5.model.fitness.FitnessBracelet;
import maxim.lab5.model.fitness.FitnessMode;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.parent.UserProfile;
import maxim.lab5.model.pressure.PressureMonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// класс ддля считывания данных с клавиатуры и их валидациии
// считывание некоторых полей вынесено в отдельные методы во избежание
// дублирование кода (некоторые методы используются в нескольких местах)
public class InReader {

    private InReader() {}

    private static final Scanner scanner = new Scanner(System.in);

    public static FitnessBracelet readFitnessBracelet() {
        FitnessBracelet fitnessBracelet = (FitnessBracelet) readCommonFields();
        fitnessBracelet.setSteps(readSteps());
        fitnessBracelet.setMode(readFitnessMode());
        return fitnessBracelet;
    }

    public static PressureMonitor readPressureMonitor() {
        return (PressureMonitor) readCommonFields();
    }

    public static String readSerialNumber() {
        return scanner.nextLine().trim();
    }

    public static Long readSteps() {
        System.out.print("Введите количество шагов: ");
        String steps = scanner.nextLine();
        while (!ValidateInput.validLong(steps) || Long.parseLong(steps) <= 0) {
            System.out.println("Некорректный ввод. Число шагов - целое число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите количество шагов пользователя устройства: ");
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
            age = scanner.nextLine();
        }
        profile.setAge(Integer.valueOf(age));
        System.out.print("Введите вес пользователя устройства: ");
        String weight = scanner.nextLine();
        while (!ValidateInput.validFloat(weight) || Float.parseFloat(weight) <= 0) {
            System.out.println("Некорректный ввод. Вес - вещественное число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите вес пользователя устройства: ");
            weight = scanner.nextLine();
        }
        profile.setWeight(Float.valueOf(weight));
        System.out.print("Введите рост пользователя устройства: ");
        String height = scanner.nextLine();
        while (!ValidateInput.validFloat(height) || Float.parseFloat(height) <= 0) {
            System.out.println("Некорректный ввод. Рост - вещественное число большее нуля. Попробуйте еще раз.");
            System.out.print("Введите рост пользователя устройства: ");
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
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число большее нуля.");
            }
            input = scanner.nextLine();
        }
        return pulse;
    }

    private static Device readCommonFields() {
        FitnessBracelet fitnessBracelet = new FitnessBracelet();
        System.out.print("Введите имя устройства: ");
        fitnessBracelet.setName(scanner.nextLine());
        System.out.print("Введите уровень заряда: ");
        String batteryLevel = scanner.nextLine();
        while (!ValidateInput.validInt(batteryLevel)
                || Integer.parseInt(batteryLevel) <= 0 || Integer.parseInt(batteryLevel) >= 100) {
            System.out.println("Некорректный ввод. Уровень заряда - целое число от 0 до 100. Попробуйте еще раз.");
            System.out.print("Введите уровень заряда: ");
            batteryLevel = scanner.nextLine();
        }
        fitnessBracelet.setBatteryLevel(Integer.valueOf(batteryLevel));
        System.out.print("Введите серийный номер устройства: ");
        fitnessBracelet.setSerialNumber(scanner.nextLine());
        fitnessBracelet.setProfile(readProfile());
        fitnessBracelet.setPulse(readPulse());
        return fitnessBracelet;
    }
}
