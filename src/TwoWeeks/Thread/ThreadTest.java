package TwoWeeks.Thread;

public class ThreadTest extends Thread{
    @Override
    public void run() {
        try{
            for(int i=0; i< 10; i++){
                Thread.sleep(500);
                System.out.println("Thread : "+ i);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Thread1{
    public static void main(String[] args) {
        ThreadTest t1 = new ThreadTest();
        ThreadTest t2 = new ThreadTest();

        // 동시에 같은 숫자가 나옴
//        t1.start();
//        t2.start();

        // 번갈아가며 나옴
        t1.run();
        t2.run();
    }
}
