package TwoWeeks.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter pw;

    public void start(int port) throws Exception{
        serverSocket = new ServerSocket(port);
        System.out.println("======= 소켓 생성 ========");
        clientSocket = serverSocket.accept();
        InetAddress inet = clientSocket.getInetAddress();
        System.out.println("접속 되었습니다 IP : " + inet);
    }

    public void Message() throws Exception{
        OutputStream out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();

        br = new BufferedReader(new InputStreamReader(in));
        pw = new PrintWriter(new OutputStreamWriter(out));


        String data = null;
        while((data = br.readLine()) != null){
            System.out.println("전송 받은 데이터 : " + data);

            String response = "전송 완료";
            pw.println(response);
            pw.flush();
        }
        pw.close();
    }

    public void stop() throws Exception{
        br.close();
        pw.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {
        TCPServer server = new TCPServer();
        server.start(9090);
        server.Message();
        server.stop();
    }

    /*
        TCP/IP에서 기억해야 할것은 3way HandShaking 과정을 통해 연결
        클라이언트에서 데이터를 보내면 서버쪽에서 데이터를 받았다는 과정을 전달을 통해
        제대로 연결되었다는 것을 확인
     */


}