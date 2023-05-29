package TwoWeeks.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class MainClient {
    String ipaddress;
    static final int port = 3434;
    Socket Client;
    BufferedReader br;
    PrintWriter pw;
    BufferedReader ois;
    String sendData;

    String userId;
    ReceiveDataThread rt;
    boolean endflag=  false;

    public MainClient(String id, String ip) {
        userId = id;
        try{
            System.out.println("============ 클라이언트 ==============");
            Client = new Socket(ip, port);
            br = new BufferedReader(new InputStreamReader(System.in));
            ois = new BufferedReader(new InputStreamReader(Client.getInputStream()));
            pw = new PrintWriter(Client.getOutputStream());

            pw.println(userId);
            pw.flush();

            rt = new ReceiveDataThread(Client, ois);
            Thread t = new Thread(rt);
            t.start();

            while(true){
                sendData = br.readLine();
                pw.println(sendData);
                pw.flush();
                if(sendData.equals("exit")){
                    endflag = true;
                    break;
                }
            }
            System.out.println("클라이언트 접속을 종료합니다.");
            System.exit(0);
        }catch (Exception e){
            if(!endflag) e.printStackTrace();
        }finally {
            try{
                Client.close(); // 최상위 소켓만 닫으면 나머지는 알아서 close
                System.exit(0);
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.next();
        new MainClient(id, "localhost");
    }
}
