package com.hnu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hnu.pojo.User;
import com.hnu.server.switches.MyThread;
import com.hnu.service.UserService;
import com.hnu.service.impl.UserServiceImpl;

/**
 * Servlet implementation class ServiceServlet
 */
public class ServiceServlet extends HttpServlet {
	//������־����
			Logger logger =Logger.getLogger(ServiceServlet.class);
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
				System.out.println(oper+"!!!!!!!!!!!!");
				if("1".equals(oper)){
					MyThread.serviceSwitch(1);
				}else if("2".equals(oper)){
					MyThread.serviceSwitch(2);
				}else if("3".equals(oper)){
					MyThread.serviceSwitch(3);	
				}else if("4".equals(oper)){
					MyThread.serviceSwitch(4);
				}else if("show".equals(oper)){
				}else{
					logger.debug("û���ҵ���Ӧ�Ĳ�������"+oper);
				}
				serviceShow(req,resp);
			}
			//��ʾ���е��û���Ϣ
			private void serviceShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				//��������
				req.getRequestDispatcher("/service/serviceMana.jsp").forward(req, resp);
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

