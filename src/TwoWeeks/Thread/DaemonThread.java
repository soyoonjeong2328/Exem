package TwoWeeks.Thread;

public class DaemonThread extends Thread{
    @Override
    public void run() {
        try{
            System.out.println("Daemon Thread Start!");
            sleep(10000);
            System.out.println("Daemon Thread End");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DaemonThread t = new DaemonThread();
        t.setDaemon(true); // Thread가 종료되지 않기 때문 끝나지 않음
        t.start();
        System.out.println("Main Method End!");
    }
}
