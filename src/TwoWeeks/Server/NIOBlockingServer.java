package TwoWeeks.Server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOBlockingServer {
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;
    private InetSocketAddress isa;
    private ByteBuffer byteBuffer;
    private Charset charset;

    public void start(int port) throws Exception{
        serverSocketChannel = ServerSocketChannel.open(); // ServerSocketChannel 얻기
        serverSocketChannel.configureBlocking(true);
        serverSocketChannel.bind(new InetSocketAddress(port));
        while (true){
            System.out.println("연결 기다리는 중");
            socketChannel = serverSocketChannel.accept();

            isa = (InetSocketAddress) socketChannel.getRemoteAddress();
            System.out.println("연결 수락 : " + isa);
            break;
        }
    }

    public String Message(String message) throws Exception{
        charset = Charset.forName("UTF-8");
        byteBuffer = ByteBuffer.allocate(1024); // 버퍼 할당
        int byteCount = socketChannel.read(byteBuffer); // 데이터 읽기
        byteBuffer.flip();

        String data = charset.decode(byteBuffer).toString();
        System.out.println("데이터 받기 성공 : " + data);

        byteBuffer = charset.encode(message);
        socketChannel.write(byteBuffer);
        System.out.println("데이터 보내기 성공!");
        return message;
    }

    public void stop() throws Exception{
        socketChannel.close();
    }


    public static void main(String[] args) throws Exception{
        NIOBlockingServer blockingServer = new NIOBlockingServer();
        blockingServer.start(3033);
        blockingServer.Message("Hello Client");
        blockingServer.stop();
    }
}

/*
    NIO는 블로킹 방식과 넌블로킹 방식 모두 지원하며, 현재는 블로킹 방식을 이용하여 구현하였습니다.
    NIO에서 제공되는 채널은 양방향이 가능하기 때문에 데이터를 서로 읽거나 쓸 수 있다는 특징이 있습니다.
    Message메서드에서 보시다시피 NIO 채널의 특징인 데이터를 버퍼로 읽거나 들이거나 버퍼에 들어있는 데이터를 채널로 쓰게 됩니다.
    Blocking 방식을 사용하였기 때문에 Selector을 사용하지 않았습니다.
 */