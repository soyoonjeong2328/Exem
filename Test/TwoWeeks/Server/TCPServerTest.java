package TwoWeeks.Server;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.net.ServerSocket;
import java.net.Socket;


public class TCPServerTest {

    @Test
    @DisplayName("연결 성공 테스트")
    public void start() throws Exception {
        ServerSocket server = new ServerSocket(5555);
        Socket clientSocket = server.accept();
        Assert.assertTrue(clientSocket.isConnected());
        System.out.println("연결 성공");
    }

}