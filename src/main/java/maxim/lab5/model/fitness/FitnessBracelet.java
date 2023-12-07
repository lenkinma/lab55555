package maxim.lab5.model.fitness;

import maxim.lab5.model.parent.Device;

import java.util.OptionalDouble;


public class FitnessBracelet extends Device {

    private FitnessMode mode;
    private Double kcal = 0.0; // по дефолту 0 шагов, соответственно и 0 калорий
    private Long steps = 0L;

    public FitnessBracelet() {
        super();
    }

    // функция для перерасчета сожженных калорий
    // перерасчет необходим, если пользователь изменил режим, добавил или сбросил число шагов
    public void recalculateBurnedKcal() {
        this.kcal = steps * mode.getCoefficient();
    }

    // отображение количества соженных калорий
    public void displayBurnedKcal() {
        recalculateBurnedKcal();
        System.out.printf("Сожжено калорий: %.2f", kcal);
    }

    // изменение режима работы
    public void changeModeTo(FitnessMode mode) {
        this.mode = mode;
        recalculateBurnedKcal();
        System.out.println("Изменен режим работы браслета.");
    }

    // сброс пульса, шагов и калорий
    public void resetHealthData() {
        this.steps = 0L;
        this.pulse.clear();
        recalculateBurnedKcal();
        System.out.println("Данные о пульсе, калориях и шагах сброшены");
    }

    // добавление шагов
    public void addSteps(Long steps) {
        this.steps += steps;
        recalculateBurnedKcal();
    }

    // реализация абстрактного метода, получение отчета
    @Override
    public void getReport() {
        StringBuilder sb = new StringBuilder();
        OptionalDouble averagePulse = pulse.stream()
                .mapToInt(Integer::intValue)
                .average();

        sb.append("Отчет для пользователя ").append(this.profile.getName()).append("\n")
                .append("Пройдено шагов: ").append(this.steps).append("\n");

        recalculateBurnedKcal();

        sb.append("Сожжено калорий: ").append(String.format("%.2f", kcal)).append("\n");

        if (averagePulse.isPresent()) {
            sb.append("Средний пульс: ").append(String.format("%.2f", averagePulse.getAsDouble())).append("\n");
        } else {
            sb.append("Данных о пульсе нет").append("\n");
        }

        System.out.println(sb);
    }

    public void setMode(FitnessMode mode) {
        this.mode = mode;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "FitnessBracelet{" +
                "mode=" + mode +
                ", steps=" + steps +
                ", name='" + name + '\'' +
                ", batteryLevel=" + batteryLevel +
                ", serialNumber='" + serialNumber + '\'' +
                ", isPutOn=" + isPutOn +
                ", profile=" + profile +
                ", pulse=" + pulse +
                '}';
    }
}
