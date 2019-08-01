package com.hnu.server.smtp;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.hnu.dao.LogDao;
import com.hnu.dao.MailDao;
import com.hnu.dao.UserDao;
import com.hnu.dao.impl.LogDaoImpl;
import com.hnu.dao.impl.MailDaoImpl;
import com.hnu.dao.impl.UserDaoImpl;
import com.hnu.pojo.Log;
import com.hnu.pojo.User;
import com.hnu.server.port.PORT;
import com.hnu.server.queue.userMailQueue;

public class SMTP {

    private static ServerSocket SERVER_SOCKET =null;;
    
    private static String mailServer = "127.0.0.1";
    File file =new File("javaio-appendfile.txt");

    public static void SMTPOpen() throws Exception{
        try {
            SERVER_SOCKET = new ServerSocket(PORT.SMTPPORT);
            System.out.println("******SMTP���������������ȴ��ͻ�������*****");
            
            Log log=new Log();
            log.setOperation("******SMTP���������������ȴ��ͻ�������*****");
            log.setDate(new Timestamp(System.currentTimeMillis()));
            log.setState("1");
            LogDao logDao = new LogDaoImpl();
            logDao.insert(log);
            
            Socket socket = null;
//            int cnt=0;
            
//            UserDao userDao=new UserDaoImpl();
//            List<User> lu=userDao.userShowDao();
//            for(User u:lu) {
//            	userMailQueue.insert(u.getUid());
//            }
            
            while(!SERVER_SOCKET.isClosed()){
                //ѭ�������ͻ��˵�����
                socket = SERVER_SOCKET.accept();
                Thread.sleep(10);
                //�½�һ���߳�ServerSocket��������
                if(!SERVER_SOCKET.isClosed())
                	new SMTPServerSocketThread(socket).start();
                
//                cnt++;
//                if(cnt==2)break;
            }
            System.out.println("******SMTP�������ѹر�*****");
            log=new Log();
            log.setOperation("******SMTP�������ѹر�*****");
            log.setDate(new Timestamp(System.currentTimeMillis()));
            log.setState("1");
            logDao = new LogDaoImpl();
            logDao.insert(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void SMTPClose() throws InterruptedException {
    	try {
    		new Socket("127.0.0.1",PORT.SMTPPORT);
    		Thread.sleep(5);
			SERVER_SOCKET.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
    }

    public static void main(String[] args) throws Exception {
    	SMTPOpen();
    }
}