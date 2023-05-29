package TwoWeeks.Server;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;



class NIOBlockingServerTest {
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer;
    private Charset charset;

    @BeforeEach
    @DisplayName("설정")
    void setup() throws Exception{
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 4000));
    }

    @Test
    @DisplayName("연결 성공")
    void start() throws Exception {
        serverSocketChannel.configureBlocking(true);
        socketChannel = serverSocketChannel.accept();
        Assert.assertTrue(socketChannel.isConnected());
        System.out.println("연결 성공");
    }

    @Test
    @DisplayName("데이터 주고 받기")
    void message() throws Exception{
        socketChannel = serverSocketChannel.accept();
        System.out.println("연결 성공");

        charset = Charset.forName("UTF-8");
        byteBuffer = ByteBuffer.allocate(1024);
        int byteCount = socketChannel.read(byteBuffer);
        byteBuffer.flip();

        String data = charset.decode(byteBuffer).toString();
        Assert.assertNotNull(data);
        System.out.println("데이터 받기 성공 : " + data);

        byteBuffer = charset.encode("Hello Client");
        socketChannel.write(byteBuffer);
        System.out.println("데이터 보내기 성공");
    }
}