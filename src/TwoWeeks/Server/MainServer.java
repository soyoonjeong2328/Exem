package TwoWeeks.Server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainServer {
    int port = 3434;
    ServerSocket server ;
    Socket child;

    HashMap<String, PrintWriter> hm;

    public MainServer() {
        ChatServerThread sr;
        Thread t;
        try{
            server = new ServerSocket(port);
            System.out.println("======================================");
            System.out.println("              채팅서버                 ");
            System.out.println("======================================");
            hm = new HashMap<>();

            while(true){
                child = server.accept();
                if(child != null){
                    sr = new ChatServerThread(child, hm);
                    t = new Thread(sr);
                    t.start();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MainServer();
    }
}
