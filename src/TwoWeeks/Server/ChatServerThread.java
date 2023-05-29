package TwoWeeks.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class ChatServerThread implements Runnable{
    Socket child;
    BufferedReader ois;
    PrintWriter pw;

    String userId;
    HashMap<String, PrintWriter> hm;
    InetAddress ip;
    String msg;

    public ChatServerThread(Socket s, HashMap<String ,PrintWriter> h){
        child = s;
        hm = h;
        try{
            ois = new BufferedReader(new InputStreamReader(child.getInputStream()));
            pw = new PrintWriter(child.getOutputStream());
            userId = ois.readLine();
            ip = child.getInetAddress();
            System.out.println(ip + "로부터 " + userId + "님이 접속하셨습니다.");
            broadcast(userId + "님이 접속하셨습니다.");
            synchronized (hm){
                hm.put(userId, pw);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        String receiveData;
        try{
            while ((receiveData = ois.readLine()) != null){
                if(receiveData.equals("quit")){
                    synchronized (hm){
                        hm.remove(userId);
                    }
                    break;
                }else if (receiveData.indexOf("to") >= 0){
                    sendMsg(receiveData);
                }else{
                    System.out.println(userId + ">> " + receiveData);
                    broadcast(userId + ">> " + receiveData);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            synchronized (hm){
                hm.remove(userId);
            }
            broadcast(userId + "님이 퇴장하셨습니다.");
            System.out.println(userId + "님이 퇴장했습니다.");
            try{
                if(child != null){
                    ois.close();
                    pw.close();
                    child.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void broadcast(String message){
        synchronized (hm){
            try{
                for (PrintWriter oos : hm.values()){
                    oos.println(message);
                    oos.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String message){
        int begin = message.indexOf(" ")+ 1;
        int end = message.indexOf(" ", begin);
        if(end != -1){
            String id = message.substring(begin, end);
            String msg = message.substring(end+1);
            PrintWriter oos = hm.get(id);

            try{
                if(oos != null){
                    oos.println(userId + "님이 다음과 같은 귓속말을 보내셨습니다.");
                    oos.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
