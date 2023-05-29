package TwoWeeks.Client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class NIOBlockingClient {
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer;
    private Charset charset;

    public void startConnection(String ip, int port) throws Exception{
        socketChannel = SocketChannel.open(); // socketChannel 얻기
        socketChannel.configureBlocking(true); // 블로킹 설정
        System.out.println("연결 요청");
        // 서버 연결 요청(서버 IP와 포트 정보를 가진 InetSocketAddress 객체 매개값)
        // connect() 메서드는 연결이 완료 될때 블로킹, 완료되면 리턴
        // 블로킹 : 요청한 작업이 성공하거나 에러가 발생하기 전까지 응답을 돌려주지 않는 것
        socketChannel.connect(new InetSocketAddress(ip, port));
        System.out.println("연결 성공");
    }

    public String sendMessage(String message) throws Exception{
        charset = Charset.forName("UTF-8");
        byteBuffer = charset.encode(message);
        socketChannel.write(byteBuffer);
        System.out.println("데이터 보내기 성공");

        byteBuffer = ByteBuffer.allocate(1024); // 버퍼 할당
        int byteCount = socketChannel.read(byteBuffer); // 데이터 읽기
        byteBuffer.flip();

        String data = charset.decode(byteBuffer).toString();
        System.out.println("데이터 받기 성공 : " + data);
        return data;
    }

    public void stop() throws Exception{
        socketChannel.close();
    }

    public static void main(String[] args) throws Exception{
        NIOBlockingClient blockingClient = new NIOBlockingClient();
        blockingClient.startConnection("127.0.0.1", 4000);
        blockingClient.sendMessage("Hello Server");
        blockingClient.stop();
    }
}