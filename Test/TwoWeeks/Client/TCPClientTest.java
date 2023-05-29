package TwoWeeks.Client;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

class TCPClientTest {
    private Socket socket;

    @Test
    @DisplayName("연결 성공")
    void Connection() throws Exception{
        socket = new Socket("localhost", 5555);
        Assert.assertTrue(socket.isConnected());
        System.out.println("연결 성공");
    }

    @Test
    @DisplayName("연결 실패")
    void FailConnection() throws Exception{
        Socket socket = new Socket("localhost", 9090);
        Assert.assertTrue(socket.isConnected());
    }

    @Test
    @DisplayName("메세지 보내기 성공 테스트 ")
    void startConnection()throws Exception{
        TCPClient client = new TCPClient();
        client.startConnection("localhost", 5555);
        String dd = "hi";
        BufferedReader br = new BufferedReader(new StringReader(dd));
        String response = "hi";
        Assert.assertEquals(br.readLine(), response);
    }

    @Test
    @DisplayName("메세지 보내기 실패 테스트")
    void FailSendMessage() throws Exception{
        TCPClient client = new TCPClient();
        client.startConnection("localhost", 5555);
        String dd = "hi";
        BufferedReader br = new BufferedReader(new StringReader(dd));
        String response = "hello";
        Assert.assertEquals(br.readLine(), response);
    }
}