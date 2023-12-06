package maxim.lab5.model.parent;

/**
 * @author Kirill Emelyanov
 */

public class UserProfile {

    private String name;
    private Float height;
    private Float weight;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                '}';
    }

}
