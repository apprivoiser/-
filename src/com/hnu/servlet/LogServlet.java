package com.hnu.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hnu.pojo.Log;
import com.hnu.pojo.User;
import com.hnu.server.switches.MyThread;
import com.hnu.service.LogService;
import com.hnu.service.UserService;
import com.hnu.service.impl.LogServiceImpl;
import com.hnu.service.impl.UserServiceImpl;

public class LogServlet extends HttpServlet {
	Logger logger =Logger.getLogger(ServiceServlet.class);
	//��ȡservice�����
	LogService us=new LogServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
		resp.setContentType("text/html;charset=utf-8");
		//��ȡ������
		String oper=req.getParameter("oper");
		if("show".equals(oper)){
			serviceShow(req,resp);
		}else{
			logger.debug("û���ҵ���Ӧ�Ĳ�������"+oper);
		}
	}
	//��ʾ���е��û���Ϣ
	private void serviceShow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��������
		List<Log> lu=us.logShowService();
		System.out.println(lu.size());
		//�ж�
		if(lu!=null){
			//����ѯ���û����ݴ洢��request����
			req.setAttribute("lu",lu);
			//����ת��
			req.getRequestDispatcher("/log/showLog.jsp").forward(req, resp);
			return;
		}
		
		return;
		
	}
}
