package maxim.lab5.util;

// класс для валидации ввода с клавиатуры (проверки на инт, флоат и т.д)
public class ValidateInput {

    // приватный конструктор для запрета создания экземлпяров класса
    private ValidateInput() {}

    public static boolean validInt(String aInt) {
        try {
            Integer.parseInt(aInt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validFloat(String aFloat) {
        try {
            Float.parseFloat(aFloat);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validLong(String aLong) {
        try {
            Long.parseLong(aLong);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
