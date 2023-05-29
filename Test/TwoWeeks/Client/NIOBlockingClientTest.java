package TwoWeeks.Client;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


class NIOBlockingClientTest {
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer;
    private Charset charset;

    @BeforeEach
    void setup() throws Exception{
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 4000));
    }

    @Test
    @DisplayName("연결 성공")
    void startConnection() throws Exception{
        socketChannel.configureBlocking(true);
        Assert.assertTrue(socketChannel.isBlocking());
        System.out.println("블로킹 성공");

        Assert.assertTrue(socketChannel.isConnected());
        System.out.println("연결 성공");
    }

    @Test
    @DisplayName("연결 실패")
    void FailConnection() throws Exception{
        socketChannel.configureBlocking(true);
        Assert.assertTrue(socketChannel.isBlocking());
        System.out.println("블로킹 성공");

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
        Assert.assertTrue(socketChannel.isConnected());
    }

    @Test
    @DisplayName("데이터 보내기")
    void sendMessage() throws Exception{
        System.out.println("연결 성공");

        charset = Charset.forName("UTF-8");
        byteBuffer = charset.encode("Hello Server");
        socketChannel.write(byteBuffer);
        Assert.assertNotNull(byteBuffer);
        System.out.println("데이터 보내기 성공");

        byteBuffer = ByteBuffer.allocate(1024);
        int byteCount = socketChannel.read(byteBuffer);
        byteBuffer.flip();

        String data = charset.decode(byteBuffer).toString();
        Assert.assertNotNull(data);
        System.out.println("데이터 받기 성공 : " + data);
    }
}