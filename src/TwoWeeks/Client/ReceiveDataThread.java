package TwoWeeks.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ReceiveDataThread implements Runnable{
    Socket client;
    BufferedReader br;
    String receiveData;

    public ReceiveDataThread(Socket s, BufferedReader ois) {
        client = s;
        this.br = ois;
    }

    @Override
    public void run() {
        try{
            while((receiveData = br.readLine()) != null)
                System.out.println(receiveData);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                br.close();
                client.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
        }
    }
}
