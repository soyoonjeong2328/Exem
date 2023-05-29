package TwoWeeks.Server;

import TwoWeeks.Client.Student;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServerEx {
    ServerSocket server;
    static final int PORT = 9090;
    Socket child; // 클라이언트와 통신하기 위한 소켓

    public MultiServerEx() { // 생성자
        try {
            server = new ServerSocket(PORT); // 소켓 생성
        } catch (Exception e) {
            e.printStackTrace(); // 에러 메시지 출력후
            System.exit(0); // 프로그램 종료
        }

        System.out.println(" ================= 다중 사용자 접속 채팅 서버 ================");
        System.out.println("서버는 클라이언트 소켓의 접속 요청을 기다리고 있음. ");

        while (true) {
            try {
                // 클라이언트 요청이 없으면 대기상태
                child = server.accept(); // 접속 허용

                // 멀티 스레드 객체 생성
                EchoServerThread childThread = new EchoServerThread(child);
                Thread t = new Thread(childThread);
                t.start();
                break;
            } catch (Exception e) {
                e.printStackTrace(); // 에러메시지 출력 후
                System.exit(0); // 프로그램 종료
            }
        }
    }

    public static void main(String[] args) {
        new MultiServerEx(); // 생성자 호출
    }
}

// Runnable 인터페이스 구현
// 접속 유지하면서 데이터 송수신하기 위해 스레드 클래스 생성
class EchoServerThread implements Runnable {
    Socket socket; // 클라이언트와 통신하기 위한 소켓
    InputStream is; // 클라이언트와 연결된 입력 스트림 저장
    ObjectInputStream ois; // 데이터 전송받기 위한 입력 스트림

    OutputStream os; // 출력 스트림 저장
    ObjectOutputStream oos; // 데이터 전송하기 위한 출려 ㄱ스트림

    public EchoServerThread(Socket s) {
        socket = s; // 소켓 정보를 socket에 저장
        try {
            System.out.println(socket.toString() + "로부터 연결 요청을 받음");

            is = socket.getInputStream(); // 클라이언트로부터 데이터를 받기 위한 입력 스트림
            ois = new ObjectInputStream(is);

            os = socket.getOutputStream(); // 클라이언트로부터 받은 메시지를 보내기 위한 출력 스트림
            oos = new ObjectOutputStream(os);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
        쓰레드가 실행되면 run() 메소드를 호출하여 작업
        run()로 다른 메서드 오버라이딩 없이 실행만 하고 싶을 때 사용
        Thread 클래스를 상속하면 다른 클래스를 상속할 수 없기 때문에 상속할 클래스가 있을 때 사용
     */
    @Override
    public void run() {
        try {
            while (true) {
                Student student = (Student) ois.readObject(); // 역직렬화

                // String 필드 log 기록
                String txt = "";
                if (student.getName() != null && student.getJob() != null && student.getAddress() != null) {
                    txt = txt.concat(student.getName() + " " + student.getJob() + " " + student.getAddress());
                    makeFile(txt);
                }

                System.out.println("이름 : " + student.getName());
                System.out.println("직업 : " + student.getJob());
                System.out.println("나이 : " + student.getAge());
                System.out.println("연봉 : " + student.getScore());
                System.out.println("주소 : " + student.getAddress());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
//                ois.close();
//                oos.close();
                socket.close();
                /*
                    소켓을 닫으면 소켓의 InputStream과 OutputStream도 닫힌다.
                    가장 최상위 것만 close해주면 그와 관련된 것들은 닫힘!!
                 */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void makeFile(String txt) {
        String fileName = "D:\\Exem\\logFile.log"; // 로그 파일 생성
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
            bw.write(txt + "\r\n"); // 입력을 받아 파일에 작성
            bw.flush();
            bw.close();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}