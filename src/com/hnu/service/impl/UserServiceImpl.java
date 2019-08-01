package com.hnu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.hnu.dao.UserDao;
import com.hnu.dao.impl.UserDaoImpl;
import com.hnu.pojo.User;
import com.hnu.service.UserService;

public class UserServiceImpl implements UserService{
	
	//������־����
	Logger logger=Logger.getLogger(UserServiceImpl.class);
	//����Dao�����
	UserDao ud=new UserDaoImpl();
	//�û���¼
	@Override
	public User checkUserLoginService(String uname, String pwd) {
		//��ӡ��־
		logger.debug(uname+"�����¼����");
		User u=ud.checkUserLoginDao(uname, pwd);
		//�ж�
		if(u!=null){
			logger.debug(uname+"��¼�ɹ�");
		}else{
			logger.debug(uname+"��¼ʧ��");
		}
		return u;
	}
	//�޸��û�����
	@Override
	public int userChangePwdService(String newPwd, int uid) {
		logger.debug(uid+":������������");
		int index=ud.userChangePwdDao(newPwd,uid);
		if(index>0){
			logger.debug(uid+":�����޸ĳɹ�");
		}else{
			logger.debug(uid+":�����޸�ʧ��");
		}
		return index;
	}
	//��ȡ���е��û���Ϣ
	@Override
	public List<User> userShowService() {
		List<User> lu=ud.userShowDao();
		logger.debug("��ʾ�����û���Ϣ��"+lu);
		return lu;
	}
	//�û�ע��
		@Override
		public int userRegService(User u) {
			return ud.userRegDao(u);
		}
	@Override
	public int userDeleteService(int uid) {
		
		return ud.delete(uid);
	}
	@Override
	public int userUpdateService(User u) {
		// TODO Auto-generated method stub
		return ud.update(u);
	}
	@Override
	public int userAddService(User u) {
		return ud.add(u);
	}
		
}
