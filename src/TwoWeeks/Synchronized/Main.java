package TwoWeeks.Synchronized;

public class Main {
    public static void main(String[] args) {
        A a = new A();
        A a2 = new A();
        Thread thread1 = new Thread(() -> {
            a.run("thread1");
        });
        Thread thread2 = new Thread(() -> {
            a2.run("thread2");
        });
        thread1.start();
        thread2.start();
    }
}
