package miniproject.server;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    String IP = "127.0.0.1";
    int PORT = 9999;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;

    public Server() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true); //Blocking
        serverSocketChannel.bind(new InetSocketAddress(IP, PORT));

        while (true) {
            try {
                // 클라이언트와 입/출력하기
                socketChannel = serverSocketChannel.accept();
                ServerThread serverThread = new ServerThread(socketChannel);
                new Thread(serverThread).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
