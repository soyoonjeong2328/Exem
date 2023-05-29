package TwoWeeks.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

public class NIONonBlockingClient {
    static Selector selector = null;
    private SocketChannel socketChannel = null;

    public void startServer() throws IOException{
        initServer();
        Receive receive = new Receive();
        new Thread(receive).start();
        startWriter();
    }

    private void initServer() throws IOException{
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(4000));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void startWriter(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        try{
            while(true){
                Scanner scanner = new Scanner(System.in);
                String message = scanner.next();
                byteBuffer.clear();
                byteBuffer.put(message.getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            clearBuffer(byteBuffer);
        }
    }

    static void clearBuffer(ByteBuffer buffer){
        if(buffer != null){
            buffer.clear();
        }
    }
}

class Receive implements Runnable{
    private CharsetDecoder decoder = null;

    public void run(){
        Charset charset = Charset.forName("UTF-8");
        decoder = charset.newDecoder();
        try{
            while(true){
                NIONonBlockingClient.selector.select();
                Iterator iterator = NIONonBlockingClient.selector.selectedKeys().iterator();

                while(iterator.hasNext()){
                    SelectionKey key = (SelectionKey) iterator.next();
                    if(key.isReadable())
                        read(key);
                    iterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        try{
            byteBuffer.flip();
            String data = decoder.decode(byteBuffer).toString();
            System.out.println("receive Message - " + data);
            NIONonBlockingClient.clearBuffer(byteBuffer);

        }catch (IOException e){
            try{
                socketChannel.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
        }
    }
}