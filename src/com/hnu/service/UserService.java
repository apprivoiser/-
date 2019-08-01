package com.hnu.service;

import java.util.List;

import com.hnu.pojo.User;

public interface UserService {
	/**
	 * У���û���¼
	 * @param uname �û���
	 * @param pwd	����
	 * @return	���ز�ѯ�����û���Ϣ
	 */
	User checkUserLoginService(String uname,String pwd);
	/**
	 * �޸��û�����
	 * @param newPwd
	 * @param uid
	 * @return
	 */
	int userChangePwdService(String newPwd, int uid);
	/**
	 * ��ȡ���е��û���Ϣ
	 * @return
	 */
	List<User> userShowService();
	/**
	 * �û�ע��
	 * @param u
	 * @return
	 */
	int userRegService(User u);
	int userAddService(User u);
	int userDeleteService(int uid);
	int userUpdateService(User u);
}
