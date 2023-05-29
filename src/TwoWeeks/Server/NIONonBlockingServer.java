package TwoWeeks.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class NIONonBlockingServer {
    private Selector selector = null;
    private Vector room = new Vector();

    public void initServer() throws IOException{
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(4000));

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void startServer() throws Exception{
        System.out.println("Server start!");

        while (true){
            selector.select();

            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator iterator = selectionKeySet.iterator();

            while(iterator.hasNext()){
                SelectionKey selectionKey = (SelectionKey) iterator.next();

                if(selectionKey.isAcceptable()){
                    accept(selectionKey);
                }else if(selectionKey.isReadable()){
                    read(selectionKey);
                }
                iterator.remove();
            }
        }
    }

    private void accept(SelectionKey key) throws Exception{
        ServerSocketChannel server = (ServerSocketChannel) key.channel();

        SocketChannel socketChannel = server.accept();

        if(socketChannel == null){
            return;
        }
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        room.add(socketChannel); // 접속자 추가
        System.out.println(socketChannel.toString() + "클라이언트가 접속했습니다.");
    }

    private void read(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024); // buffer 생성

        try{
            socketChannel.read(byteBuffer);
        }catch (IOException e){
            try{
                socketChannel.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
            room.remove(socketChannel);
            e.printStackTrace();
        }

        try{
            broadcast(byteBuffer);
        }catch (Exception e){
            e.printStackTrace();
        }
        byteBuffer.clear();
    }

    private void broadcast(ByteBuffer byteBuffer) throws IOException{
        byteBuffer.flip();
        Iterator iterator = room.iterator();

        while(iterator.hasNext()){
            SocketChannel socketChannel = (SocketChannel) iterator.next();

            if(socketChannel != null){
                socketChannel.write(byteBuffer);
                byteBuffer.rewind();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NIONonBlockingServer nioNonBlockingServer = new NIONonBlockingServer();
        nioNonBlockingServer.initServer();
        nioNonBlockingServer.startServer();

    }
}
