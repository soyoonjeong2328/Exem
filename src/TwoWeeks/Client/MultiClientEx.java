package TwoWeeks.Client;

import java.io.*;
import java.net.Socket;

public class MultiClientEx {
    Socket client = null; // 서버와 통신하기위한 클라이언트 소켓
    String ip;
    static final int PORT = 9090;

    BufferedReader br; // 키보드로부터 메시지를 읽어올 입력 스트림
    InputStream is;// 서버가 보낸 데이터 읽기 위한 입력 스트림 저장
    ObjectInputStream ois; // 서버로부터 데이터를 전송 받기 위한 입력 스트림

    OutputStream os; // 서버로 메시지를 보내기 위한 출력 스트림 저장
    ObjectOutputStream oos; // 서버에 데이터를 전송하기 위한 출력 스트림

    public MultiClientEx(String ip) {
        this.ip = ip;
        try {
            System.out.println("======================== 클라이언트 (사용자) =======================");

            client = new Socket(ip, PORT); // 소켓 객체 생성
            System.out.println(client);
            br = new BufferedReader(new InputStreamReader(System.in)); // 키보드로부터 메세지 읽어올 입력 스트림

            os = client.getOutputStream(); // 서버로 메시지를 보내기 위한 출력스트림 얻기
            oos = new ObjectOutputStream(os); // 출력 스트림을 ObjectOutPutStream으로 변환

            is = client.getInputStream(); // 데이터 수신받기 위해 클라이언트로부터 입력 스트림 얻어오기
            ois = new ObjectInputStream(is); // 입력 스트림 ObjectInputStream으로 변환

            while (true) {
                System.out.println("**** 회사원의 정보를 입력하려면 start 종료할예정이면 stop를 입력하세요. ****");
                System.out.print("입력 >> ");
                String options = br.readLine();
                switch (options) {
                    case "start":
                        System.out.print("이름 >> ");
                        String name = br.readLine();
                        System.out.print("직업 >> ");
                        String job = br.readLine();
                        System.out.print("나이 >> ");
                        int age = Integer.parseInt(br.readLine());
                        System.out.print("연봉 >> ");
                        int score = Integer.parseInt(br.readLine());
                        System.out.print("주소 >> ");
                        String address = br.readLine();
                        Student student = new Student(name, job, age, score, address); // Stuent 객체에 담아 서버로 전달
                        oos.writeObject(student); // 직렬화
                        System.out.println("서버로 전송 완료!");
                        /*
                            직렬화 하는 이유?
                            - 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부 자바 시스템에서도 사용할 수 있도록
                            바이트 형태로 데이터 변환하는 기술
                         */
                        oos.flush(); // 출력 스트림 비우기
                        break;

                    case "stop":
                        System.out.println("종료 합니다.");
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                        break;
                }
                if (options.equals("stop")) {
                    System.out.println("프로그램 종료");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            try {
//                if (is != null) is.close();
//                if (ois != null) ois.close();
//                if (os != null) os.close();
//                if (oos != null) oos.close();
                if (client != null) client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MultiClientEx("localhost");
    }
}


