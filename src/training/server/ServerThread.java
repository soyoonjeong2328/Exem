package training.server;


import training.client.DbStatMain;
import training.client.SessionStatMain;
import training.client.SqlStatMain;
import training.db.StatConstant;
import training.server.insert.DbStatInsert;
import training.server.insert.SessionStatInsert;
import training.server.insert.SqlStatInsert;

import java.nio.ByteBuffer;

import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread {
    private SocketChannel socketChannel;
    private byte CLIENT_ID;

    public ServerThread(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            DbStatMain dbStatMain = new DbStatMain(CLIENT_ID);
            SessionStatMain sessionStatMain = new SessionStatMain(CLIENT_ID);
            SqlStatMain sqlStatMain = new SqlStatMain(CLIENT_ID);
            int result = (Short.BYTES + Byte.BYTES + Long.BYTES + Long.BYTES + Integer.BYTES);

            // 클라이언트한테 데이터 받기
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(result);
                socketChannel.read(byteBuffer);
                byteBuffer.flip(); // 읽기 모드 변경

                // header
                short type = byteBuffer.getShort();
                byte clientId = byteBuffer.get();
                long size = byteBuffer.getLong();
                long time = byteBuffer.getLong();
                int count = byteBuffer.getInt();
                System.out.println("header : " + type + " | " + clientId + " | " + size + " | " + time + " | " + count);

                // body
                ByteBuffer bodyBuffer = ByteBuffer.allocate((int) size);
                socketChannel.read(bodyBuffer);
                bodyBuffer.flip();

                if (type == StatConstant.DB_STAT) {
                    DbStatInsert.insert(bodyBuffer, clientId, time, count);
                }else if(type == StatConstant.SESSION_STAT){
                    SessionStatInsert.insert(bodyBuffer, clientId, time, count);
                }else if(type == StatConstant.SQL_STAT){
                    SqlStatInsert.insert(bodyBuffer, clientId, time, count);
                }
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.toString());
        }
    }






}
