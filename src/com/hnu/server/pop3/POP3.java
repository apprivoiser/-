package com.hnu.server.pop3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;

import com.hnu.dao.LogDao;
import com.hnu.dao.impl.LogDaoImpl;
import com.hnu.pojo.Log;
import com.hnu.server.port.PORT;
import com.hnu.server.queue.userMailQueue;


public class POP3 {

    private static ServerSocket SERVER_SOCKET =null;
    
    private static String mailServer = "127.0.0.1";

    public static void POP3Open() throws Exception{
        try {
            SERVER_SOCKET = new ServerSocket(PORT.POP3PORT);
            System.out.println("******POP3���������������ȴ��ͻ�������*****");
            Log log=new Log();
            log.setOperation("******POP3���������������ȴ��ͻ�������*****");
            log.setDate(new Timestamp(System.currentTimeMillis()));
            log.setState("1");
            LogDao logDao = new LogDaoImpl();
            logDao.insert(log);
            Socket socket = null;
//            int cnt=0;
            while(!SERVER_SOCKET.isClosed()){
                //ѭ�������ͻ��˵�����
                socket = SERVER_SOCKET.accept();
                Thread.sleep(10);
                //�½�һ���߳�ServerSocket��������
                if(!SERVER_SOCKET.isClosed())
                	new POP3ServerSocketThread(socket).start();
                
//                cnt++;
//                if(cnt>=2)break;
            }
            System.out.println("******POP3�������ѹر�*****");
            log=new Log();
            log.setOperation("******POP3�������ѹر�*****");
            log.setDate(new Timestamp(System.currentTimeMillis()));
            log.setState("1");
            logDao = new LogDaoImpl();
            logDao.insert(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void POP3Close() throws InterruptedException {
    	try {
    		new Socket("127.0.0.1",PORT.POP3PORT);
    		Thread.sleep(5);
			SERVER_SOCKET.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    }

    public static void main(String[] args) throws Exception {
    	POP3Open();
    }
}