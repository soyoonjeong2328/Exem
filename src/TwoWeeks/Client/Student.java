package TwoWeeks.Client;

import java.io.Serializable;

public class Student extends Person implements Serializable {
    int score;
    String address;
    /*
        super() : 자식 클래스가 자신을 생성할때 부모 클래스의 생성자를 불러 초기화할때 사용
        this : 현재 클래스의 멤버변수를 지정할때 사용
     */
    public Student(String name, String job, int age, int score, String address) {
        super(name,job,age);
        this.score =score;
        this.address =address;
    }

    public int getScore() {
        return score;
    }
    public String getAddress() {
        return address;
    }
}
