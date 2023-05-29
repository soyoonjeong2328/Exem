package TwoWeeks.Client;

import java.io.Serializable;

public class Person implements Serializable {
    String name; // 이름
    String job; // 직업
    int age;  // 나이

    public Person(String name, String job, int age) {
        this.name = name;
        this.job = job;
        this.age =age;
    }

    public String getName() {
        return name;
    }
    public String getJob() {
        return job;
    }
    public int getAge() {
        return age;
    }
}
