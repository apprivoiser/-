package com.hnu.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hnu.dao.LogDao;
import com.hnu.dao.impl.LogDaoImpl;
import com.hnu.pojo.Log;
import com.hnu.pojo.User;
import com.hnu.server.port.PORT;
import com.hnu.service.UserService;
import com.hnu.service.impl.UserServiceImpl;

/**
 * Servlet implementation class SystemServlet
 */
public class SystemServlet extends HttpServlet {
	//������־����
		Logger logger =Logger.getLogger(UserServlet.class);
		//��ȡservice�����
		UserService us=new UserServiceImpl();
		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			//������������ʽ
			req.setCharacterEncoding("utf-8");
			//������Ӧ�����ʽ
			resp.setContentType("text/html;charset=utf-8");
			//��ȡ������
			String oper=req.getParameter("oper");
			if("change".equals(oper)){
				//�����޸Ĺ���
				String mailboxSize=req.getParameter("mailboxSize");
				String SMTPport=req.getParameter("SMTPport");
				String POP3port=req.getParameter("POP3port");
				if(!mailboxSize.equals(""))
					try {
						PORT.changeMailboxSize(Integer.valueOf(mailboxSize).intValue());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				Log log=new Log();
	            log.setDate(new Timestamp(System.currentTimeMillis()));
				if(!SMTPport.equals(""))
					try {
						PORT.changeSMTPPORT(Integer.valueOf(SMTPport).intValue());
						log.setOperation("SMTPport��"+SMTPport);
		            	log.setState("1");
		            	LogDao logDao = new LogDaoImpl();
		                logDao.insert(log);
					} catch (NumberFormatException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(!POP3port.equals(""))
					try {
						PORT.changePOP3PORT(Integer.valueOf(POP3port).intValue());
						log.setOperation("POP3port��"+POP3port);
		            	log.setState("1");
		            	LogDao logDao = new LogDaoImpl();
		                logDao.insert(log);
					} catch (NumberFormatException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				systemShow(req,resp);
			}else if("show".equals(oper)) {
				systemShow(req,resp);
			}else{
				logger.debug("û���ҵ���Ӧ�Ĳ�������"+oper);
			}
		}
		//��ʾ
		private void systemShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			//��������
//				//����service
//				List<User> lu=us.userShowService();
//				//�ж�
//				if(lu!=null){
//					//����ѯ���û����ݴ洢��request����
//					req.setAttribute("lu",lu);
//					//����ת��
//					req.getRequestDispatcher("/user/showUser.jsp").forward(req, resp);
//					return;
//				}
			req.setAttribute("mailboxSize",PORT.mailboxSize);
			req.setAttribute("SMTPPORT",PORT.SMTPPORT);
			req.setAttribute("POP3PORT",PORT.POP3PORT);
			req.getRequestDispatcher("/system/systemOper.jsp").forward(req, resp);
			return;
			
		}
		//�û��޸�����
		private void userChangePwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			//��ȡ����
				String newPwd=req.getParameter("newPwd");
				//��session�л�ȡ�û���Ϣ
				User u=(User)req.getSession().getAttribute("user");
				int uid=u.getUid();
			//��������
				//����service����
				int index=us.userChangePwdService(newPwd,uid);
				if(index>0){
					//��ȡsession����
					HttpSession hs=req.getSession();
					hs.setAttribute("pwd","true");
					//�ض��򵽵�¼ҳ��
					resp.sendRedirect("/mg/login.jsp");
				}
		}
		//�û��˳�
		private void userOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			//��ȡsession����
			HttpSession hs=req.getSession();
			//ǿ������session
			hs.invalidate();
			//�ض��򵽵�¼ҳ��
			resp.sendRedirect("/mg/login.jsp");
		}
		//�����¼
		private void checkUserLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
					//��ȡ������Ϣ
					String uname=req.getParameter("uname");
					String pwd=req.getParameter("pwd");
					//����������Ϣ
						//У��
						User u=us.checkUserLoginService(uname, pwd);
						if(u!=null){
							//��ȡsession����
							HttpSession hs=req.getSession();
							//���û����ݴ洢��session��
							hs.setAttribute("user", u);
							//�ض���
							resp.sendRedirect("/mg/main/main.jsp");
							return;
						}else{
							//��ӱ�ʶ����request��
							req.setAttribute("flag",0);
							//����ת��
							req.getRequestDispatcher("/login.jsp").forward(req, resp);
							return;
						}
					//��Ӧ������
						//ֱ����Ӧ
						//����ת��
						
						
		}
		
		
	}

