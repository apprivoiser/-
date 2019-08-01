package com.hnu.servlet;

import java.io.IOException;
import java.util.List;

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
 * Servlet�ض���·���ܽ᣺
 * 	���·�����ӵ�ǰ�����·��������Դ��·��
 * 		���·�����servlet�ı����а���Ŀ¼��������ض�����Դ����ʧ�ܡ�
 * 	����·������һ��/��ʾ��������Ŀ¼
 * 		/������Ŀ��/��Դ·��
 * 
 * Servlet����ת����
 * 		/��ʾ��Ŀ��Ŀ¼��
 * 		req.getRequestDispatcher("/��Դ·��").forward(req, resp);
 * 
 * @author MyPC
 *
 */
public class UserServlet extends HttpServlet {
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
		System.out.println("#####"+req.getParameter("id"));
		System.out.println("#####"+req.getParameter("oper"));
		String oper=req.getParameter("oper");

		if("login".equals(oper)){
			//���õ�¼������
			try {
				checkUserLogin(req,resp);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("out".equals(oper)){
			//�����˳�����
			userOut(req,resp);
		}else if("pwd".equals(oper)){
			//���������޸Ĺ���
			userChangePwd(req,resp);	
		}else if("show".equals(oper)){
			//������ʾ�����û�����
			userShow(req,resp);
		}else if("reg".equals(oper)){
			//����ע�Ṧ��
			userReg(req,resp);
		}else if("addPage".equals(oper)){
			//����ע�Ṧ��
			userAddPage(req,resp);
		}else if("add".equals(oper)){
			//����ע�Ṧ��
			userAdd(req,resp);
		}else if("update".equals(oper)){
			//�����˳�����
			update(req,resp);
		}else if("delete".equals(oper)){
			//�����˳�����
			delete(req,resp);
		}else if("upd".equals(oper)){
			//�����˳�����
			upd(req,resp);
		}
		else{
			logger.debug("û���ҵ���Ӧ�Ĳ�������"+oper);
		}
	}
	private void userAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		//��ȡ������Ϣ
		String uname=req.getParameter("uname");
		String pwd=req.getParameter("pwd");
		int type=req.getParameter("type")!=""?Integer.parseInt(req.getParameter("type")):0;
		System.out.println(uname+":"+pwd+":"+type);
		User u=new User(0, uname, pwd, type);
	//����������Ϣ
		//����ҵ��㴦��
		int index=us.userAddService(u);
	//��Ӧ������
		if(index>0){
			//��ȡsession
			HttpSession hs=req.getSession();
			hs.setAttribute("reg", "true");
			//�ض���
			resp.sendRedirect("/mg/index2.jsp");
		}
		
	}
	private void userAddPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/user/addPage.jsp").forward(req, resp);
	}
	private void upd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��ȡ������Ϣ
		String uname=req.getParameter("uname");
		String pwd=req.getParameter("pwd");
		int type=req.getParameter("type")!=""?Integer.parseInt(req.getParameter("type")):0;
		System.out.println(uname+":"+pwd+":"+type);
		int uid = Integer.valueOf(req.getParameter("uid"));
		User u=new User(uid, uname, pwd, type);
		System.out.println("@@@@@@@@@@@@@@@@@@@"+u.toString());
	//����������Ϣ
		//����ҵ��㴦��
		int index=us.userUpdateService(u);
	//��Ӧ������
		if(index>0){
			//��ȡsession
			HttpSession hs=req.getSession();
			hs.setAttribute("reg", "true");
			//�ض���
			resp.sendRedirect("/mg/index.jsp");
//			HttpSession hs=req.getSession();
//			hs.setAttribute("reg", "true");
//			req.getRequestDispatcher("/user/showUser.jsp").forward(req, resp);
			//�ض���
//			resp.sendRedirect("/mg/login.jsp");
		}
	}
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String str = req.getParameter("id");
		int id = Integer.valueOf(str);
		int index=us.userDeleteService(id);
		//��Ӧ������
			if(index>0){
				//��ȡsession
				HttpSession hs=req.getSession();
				hs.setAttribute("reg", "true");
				//�ض���
				resp.sendRedirect("/mg/index.jsp");
//				req.setAttribute("reg", "true");
//				req.getRequestDispatcher("/user/showUser.jsp").forward(req, resp);
			}
	}
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String str1 = req.getParameter("uid");
		String uname = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		String str2 = req.getParameter("type");
		
		System.out.println("$$$$$$$$"+str1);
		System.out.println("$$$$$$$$"+uname);
		System.out.println("$$$$$$$$"+pwd);
		System.out.println("$$$$$$$$"+str2);
		int id = Integer.valueOf(str1);
		int type = Integer.valueOf(str2);
		req.setAttribute("id",id);
		req.setAttribute("uname",uname);
		req.setAttribute("pwd",pwd);
		req.setAttribute("type",type);
		//����ת��
		req.getRequestDispatcher("/user/update.jsp").forward(req, resp);
	}
	//ע���û�
	private void userReg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//��ȡ������Ϣ
			String uname=req.getParameter("uname");
			String pwd=req.getParameter("pwd");
			int type=req.getParameter("type")!=""?Integer.parseInt(req.getParameter("type")):0;
			System.out.println(uname+":"+pwd+":"+type);
			User u=new User(0, uname, pwd, type);
		//����������Ϣ
			//����ҵ��㴦��
			int index=us.userRegService(u);
		//��Ӧ������
			if(index>0){
				//��ȡsession
				HttpSession hs=req.getSession();
				hs.setAttribute("reg", "true");
				//�ض���
				resp.sendRedirect("/mg/login.jsp");
			}
		
	}
	//��ʾ���е��û���Ϣ
	private void userShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��������
			//����service
			List<User> lu=us.userShowService();
			//�ж�
			if(lu!=null){
				//����ѯ���û����ݴ洢��request����
				req.setAttribute("lu",lu);
				//����ת��
				req.getRequestDispatcher("/user/showUser.jsp").forward(req, resp);
				return;
			}
		
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
	private void checkUserLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, InterruptedException {
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
