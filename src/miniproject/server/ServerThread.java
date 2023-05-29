package miniproject.server;

import miniproject.db.StatConstant;
import miniproject.server.insert.*;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerThread extends Thread{
    private SocketChannel socketChannel;

    public ServerThread(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            int result = (Short.BYTES + Byte.BYTES + Long.BYTES + Long.BYTES + Integer.BYTES);

            // 클라이언트한테 데이터 받은거 이쪽에서 처리
            while (true) {
                ByteBuffer headerBuffer = ByteBuffer.allocateDirect(result);
                socketChannel.read(headerBuffer);
                headerBuffer.flip();

                // header
                short type = headerBuffer.getShort();
                byte clientId = headerBuffer.get();
                long size = headerBuffer.getLong();
                long time = headerBuffer.getLong();
                int count = headerBuffer.getInt();
                System.out.println("header : " + type + " | " + clientId + " | " + size + " | " + time + " | " + count);

                // body
                ByteBuffer bodyBuffer = ByteBuffer.allocateDirect((int) size);
                socketChannel.read(bodyBuffer);
                bodyBuffer.flip();

                // type구분해서 데이터 insert
                if(StatConstant.JmxHeapDataStat == type){
                    JmxHeapDataStatInsert.insert(bodyBuffer, clientId, time, count);
                }else if(StatConstant.JmxClassData == type){
                    JmxClassDataInsert.insert(bodyBuffer, clientId, time, count);
                }else if(StatConstant.JmxThreadData == type){
                    JmxThreadDataInsert.insert(bodyBuffer, clientId, time, count);
                }else if(StatConstant.JmxGcData == type){
                    JmxGcDataInsert.insert(bodyBuffer, clientId, time, count);
                }else if (StatConstant.ClassStat == type){
                    ClassStatDataInsert.insert(bodyBuffer, clientId , time, count);
                }
            }
        }catch (Exception e){
            System.out.println("====== 에러 발생 ======= ");
            System.out.println("Error = " + e.toString());
        }
    }
}
