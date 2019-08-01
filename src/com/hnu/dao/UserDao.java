package com.hnu.dao;

import java.util.List;

import com.hnu.pojo.User;

public interface UserDao {
	/**
	 * �����û����������ѯ�û���Ϣ
	 * @param uname �û���
	 * @param pwd	����
	 * @return ���ز�ѯ�����û���Ϣ
	 */
	User checkUserLoginDao(String uname,String pwd);
	/**
	 * �����û�ID�޸��û�����
	 * @param newPwd
	 * @param uid
	 * @return
	 */
	int userChangePwdDao(String newPwd, int uid);
	/**
	 * ��ȡ���е��û���Ϣ
	 * @return
	 */
	List<User> userShowDao();
	/**
	 * ͨ��account��ȡ���û�id
	 * @return
	 */
	int queryByAccount(String uname);
	/**
	 * �û�ע��
	 * @param u
	 * @return
	 */
	int userRegDao(User u);
	int add(User u);
	int delete(int uid);
	int update(User u);
}
