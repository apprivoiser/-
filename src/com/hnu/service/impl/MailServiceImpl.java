package com.hnu.service.impl;


import org.apache.log4j.Logger;

import com.hnu.dao.MailDao;
import com.hnu.dao.UserDao;
import com.hnu.dao.impl.MailDaoImpl;
import com.hnu.dao.impl.UserDaoImpl;
import com.hnu.pojo.Mail;
import com.hnu.service.MailService;

public class MailServiceImpl implements MailService {
	//������־����
		Logger logger=Logger.getLogger(UserServiceImpl.class);
		//����Dao�����
		MailDao md = new MailDaoImpl();
	@Override
	public int insertService(Mail mail) {
		
		try {
			System.out.println("��MailService�С�����������������������������������"+mail.toString());
			return md.insert(mail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void deleteService(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mail queryByIdService(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
