package maxim.lab5.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// класс для валидации ввода с клавиатуры (проверки на инт, флоат и т.д)
public class ValidateInput {

    private static final Logger log = LoggerFactory.getLogger(ValidateInput.class);

    // приватный конструктор для запрета создания экземлпяров класса
    private ValidateInput() {}

    public static boolean validInt(String aInt) {
        try {
            Integer.parseInt(aInt);
            return true;
        } catch (NumberFormatException e) {
            log.error("Некорректный ввод. Ожидалось целое число, получено {}", aInt);
            return false;
        }
    }

    public static boolean validFloat(String aFloat) {
        try {
            Float.parseFloat(aFloat);
            return true;
        } catch (NumberFormatException e) {
            log.error("Некорректный ввод. Ожидалось вещественное число, получено {}", aFloat);
            return false;
        }
    }

    public static boolean validLong(String aLong) {
        try {
            Long.parseLong(aLong);
            return true;
        } catch (NumberFormatException e) {
            log.error("Некорректный ввод. Ожидалось целое число, получено {}", aLong);
            return false;
        }
    }

}
