package training.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Client {
    private static byte CLIENT_ID;
    int port = 4545;
    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer;

    public Client(byte clientId) {
        CLIENT_ID = clientId;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            socketChannel.connect(new InetSocketAddress("localhost", port));
            System.out.println("============= connect Success =====================");

            DbStatMain dbStatMain = new DbStatMain(CLIENT_ID);
            SessionStatMain sessionStatMain = new SessionStatMain(CLIENT_ID);
            SqlStatMain sqlStatMain = new SqlStatMain(CLIENT_ID);

            while (true) {
                TimeUnit.SECONDS.sleep(2); // 2초 지연
//                byteBuffer = dbStatMain.getDBSTAT();
//                byteBuffer = sessionStatMain.getSessionStat();
                byteBuffer = sqlStatMain.getSqlStat();
                byteBuffer.flip();
                socketChannel.write(byteBuffer);

                /*
                    질문1.
                    dbStatMain, SessionStatMain 이렇게 두개를 한꺼번에 던져서 서버에서 판단해서 insert하고 싶은데
                    bytebuffer를 어떻게 처리 해야할지
                    지금 짠 코드로 하면 마지막 bytebuffer인 SessionStatMain만 데이터가 들어가게 됨
                 */
                System.out.println("Sending Success");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }



    public static void main(String[] args) {
        new Client((byte) 13);
    }
}
