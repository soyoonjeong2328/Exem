package training.server;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    int port = 4545;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;

    public Server() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true); //Blocking
        serverSocketChannel.bind(new InetSocketAddress(port));

        while (true) {
            try {
                // 클라이언트와 입/출력하기
                socketChannel = serverSocketChannel.accept();
                ServerThread ServerThread = new ServerThread(socketChannel);
                new Thread(ServerThread).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
