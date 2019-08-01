package com.hnu.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hnu.dao.UserDao;
import com.hnu.dao.impl.UserDaoImpl;
import com.hnu.pojo.Mail;
import com.hnu.pojo.User;
import com.hnu.server.queue.userMailQueue;
import com.hnu.service.MailService;
import com.hnu.service.UserService;
import com.hnu.service.impl.MailServiceImpl;
import com.hnu.service.impl.UserServiceImpl;

/**
 * Servlet implementation class MailServlet
 */
public class MailServlet extends HttpServlet {
	//������־����
	Logger logger =Logger.getLogger(UserServlet.class);
	//��ȡservice�����
	MailService ms=new MailServiceImpl();
	UserService us=new UserServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
		resp.setContentType("text/html;charset=utf-8");
		//��ȡ������
//		System.out.println("#####"+req.getParameter("id"));
//		System.out.println("#####"+req.getParameter("oper"));
		String oper=req.getParameter("oper");
		if("login".equals(oper)){
			//���õ�¼������
//			checkUserLogin(req,resp);
		}else if("out".equals(oper)){
			//�����˳�����
//			userOut(req,resp);
		}else if("pwd".equals(oper)){
			//���������޸Ĺ���
//			userChangePwd(req,resp);	
		}else if("show".equals(oper)){
			//������ʾ�����û�����
			mailShow(req,resp);
		}else if("send".equals(oper)){
			//����ע�Ṧ��
			try {
				sendMail(req,resp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			logger.debug("û���ҵ���Ӧ�Ĳ�������"+oper);
		}
	}
	//ע���û�
	private void sendMail(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		//��ȡ������Ϣ
			String subject=req.getParameter("subject");
			System.out.println("*********"+subject);
			HttpSession hs=req.getSession();
			String from_user = ((User)hs.getAttribute("user")).getUname();
			System.out.println("*********"+from_user);
			String to_user=req.getParameter("to_user");
			System.out.println("*********"+to_user);
			String content=req.getParameter("content");
			System.out.println("*********"+content);
	 		Date date = new Date();
	 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 		String time = df.format(date);
	 		System.out.println("*********"+time);
//	 		Timestamp now = time.;
	 		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 		Timestamp ts = new Timestamp(format.parse(time).getTime());
//			int type=req.getParameter("type")!=""?Integer.parseInt(req.getParameter("type")):0;
			System.out.println(subject+":"+to_user+":"+content);
//			User u=new User(0, uname, pwd, type);
			Mail mail = new Mail(0, from_user,to_user,subject,ts,content);
			System.out.println("*********"+mail.toString());
		//����������Ϣ
			//����ҵ��㴦��
			int index=ms.insertService(mail);
		//��Ӧ������
			if(index>0){
				//��ȡsession
				
				hs.setAttribute("reg", "true");
				//�ض���
				resp.sendRedirect("/mg/index.jsp");
			}
		
	}
	//��ʾ���е��û���Ϣ
	private void mailShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��������
			//����service
			List<User> lu=us.userShowService();
			//�ж�
			if(lu!=null){
				//����ѯ���û����ݴ洢��request����
				req.setAttribute("lu",lu);
				//����ת��
		req.getRequestDispatcher("/mail/sendMail.jsp").forward(req, resp);
		return;
			}
	}
//	//�û��޸�����
//	private void userChangePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		//��ȡ����
//			String newPwd=req.getParameter("newPwd");
//			//��session�л�ȡ�û���Ϣ
//			User u=(User)req.getSession().getAttribute("user");
//			int uid=u.getUid();
//		//��������
//			//����service����
//			int index=us.userChangePwdService(newPwd,uid);
//			if(index>0){
//				//��ȡsession����
//				HttpSession hs=req.getSession();
//				hs.setAttribute("pwd","true");
//				//�ض��򵽵�¼ҳ��
//				resp.sendRedirect("/mg/login.jsp");
//			}
//	}
//	//�û��˳�
//	private void userOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		//��ȡsession����
//		HttpSession hs=req.getSession();
//		//ǿ������session
//		hs.invalidate();
//		//�ض��򵽵�¼ҳ��
//		resp.sendRedirect("/mg/login.jsp");
//	}
//	//�����¼
//	private void checkUserLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//				//��ȡ������Ϣ
//				String uname=req.getParameter("uname");
//				String pwd=req.getParameter("pwd");
//				//����������Ϣ
//					//У��
//					User u=us.checkUserLoginService(uname, pwd);
//					if(u!=null){
//						//��ȡsession����
//						HttpSession hs=req.getSession();
//						//���û����ݴ洢��session��
//						hs.setAttribute("user", u);
//						//�ض���
//						resp.sendRedirect("/mg/main/main.jsp");
//						return;
//					}else{
//						//��ӱ�ʶ����request��
//						req.setAttribute("flag",0);
//						//����ת��
//						req.getRequestDispatcher("/login.jsp").forward(req, resp);
//						return;
//					}
//				//��Ӧ������
//					//ֱ����Ӧ
//					//����ת��
//					
					
//	}
	
	
}

