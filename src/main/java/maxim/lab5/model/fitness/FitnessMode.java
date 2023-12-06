package maxim.lab5.model.fitness;


// перечсиление всех доступных режимов фитнесс браслета
public enum FitnessMode {

    // 1 параметр - строковое значение, вводимое пользователем с клавиатуры,
    // по которому ищется соответствующий режим
    // 2 параметр - коэффициент, используемый при расчете соженных калорий
    RUNNING("бег", 0.20),
    SWIMMING("плавание", 0.15),
    SKIING("лыжи", 0.18),
    WALKING("ходьба", 0.14),
    SLEEPING("сон", 0.05);

    private final String name;
    private final Double cf;

    FitnessMode(String name, Double cf) {
        this.name = name;
        this.cf = cf;
    }

    // проверка на то, что введеный пользователем режим работы существует
    public static boolean hasFitnessMode(String targetMode) {
        for (FitnessMode mode : FitnessMode.values()) {
            if (mode.getName().equalsIgnoreCase(targetMode)) {
                return true;
            }
        }
        return false;
    }

    // метод для возврата соответствующего пользовательскому вводу режима
    public static FitnessMode findFitnessMode(String userInput) {
        for (FitnessMode mode : FitnessMode.values()) {
            if (mode.getName().equalsIgnoreCase(userInput)) {
                return mode;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Double getCoefficient() {
        return cf;
    }

}
