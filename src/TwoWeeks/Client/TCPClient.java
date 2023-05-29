package TwoWeeks.Client;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private Socket clientSocket;
    private PrintWriter pw2;
    private BufferedReader br2;
    private String data;

    public void startConnection(String ip, int port) throws Exception{
        clientSocket = new Socket(ip, port);
    }

    public String sendMessage() throws Exception{
        br2 = new BufferedReader(new InputStreamReader(System.in));

        InputStream in = clientSocket.getInputStream();
        OutputStream out = clientSocket.getOutputStream();

        pw2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
        BufferedReader ser_br = new BufferedReader(new InputStreamReader(in));

        while (true){
            System.out.print("데이터 입력 : " );
            data = br2.readLine();
            System.out.println("종료 시 end 입력하세요!");
            if(data.equals("end")){
                break;
            }
            pw2.println(data);
            pw2.flush();

            String response = ser_br.readLine();
            System.out.println("서버에서 온 응답 : " + response);
        }
        return data;
    }

    public void stopConnection() throws Exception{
        pw2.close();
        br2.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws Exception{
        TCPClient client = new TCPClient();
        client.startConnection("127.0.0.1", 9090);
        client.sendMessage();
        client.stopConnection();
    }
}

/*
    보통 소켓을 먼저 close()하는것은 클라이언트!
    1. 클라이언트는 연결을 요청하고 3wayhanshake 과정을 거쳐 소켓 연결
    2. 연결된 소켓을 이용해 HTTP Request, 서버는 Http Response
        - 이때 서버는 응답 이후, 연결된 소켓을 끊지 않고 유지
    3. 응답을 받은 클라이언트는 필요없어진 소켓을 close()
 */