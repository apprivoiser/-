package com.hnu.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hnu.dao.UserDao;
import com.hnu.pojo.User;
import com.hnu.utils.DBConnection;

public class UserDaoImpl implements UserDao{
	//�����û����������ѯ�û���Ϣ
	@Override
	public User checkUserLoginDao(String uname, String pwd) {
		//����jdbc����
		DBConnection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//��������
		User u=null;
		try {
			//��������
//			Class.forName("com.mysql.jdbc.Driver");
			//��ȡ����
//			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
			conn = new DBConnection();
			
			//����sql����
			String sql="select * from ms_user where uname=? and pwd=?";
			//����sql�������
			ps=conn.getConnection().prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, uname);
			ps.setString(2, pwd);
			//ִ��sql
			rs=ps.executeQuery();
			//�������
				while(rs.next()){
					//��������ֵ
					u=new User();
					u.setUid(rs.getInt("uid"));
					u.setUname(rs.getString("uname"));
					u.setPwd(rs.getString("pwd"));
					u.setType(rs.getInt("type"));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر���Դ
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		}
		
		
		//���ؽ��
		return u;
	}
	//�����û�ID�޸��û�����
	@Override
	public int userChangePwdDao(String newPwd, int uid) {
		//����jdbc����
		DBConnection conn=null;
		PreparedStatement ps=null;
		//��������
		int index=-1;
		try {
			//��������
//			Class.forName("com.mysql.jdbc.Driver");
			//��ȡ����
//			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
			conn = new DBConnection();
			//����SQL����
			String sql="update ms_user set pwd=? where uid=?";
			//����SQL�������
			ps=conn.getConnection().prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1, newPwd);
			ps.setInt(2, uid);
			//ִ��
			index=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{//�ر���Դ
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		}
		//���ؽ��
		return index;
	}
	//��ȡ���е��û���Ϣ
	@Override
	public List<User> userShowDao() {
		//����jdbc����
		DBConnection conn=null;
				PreparedStatement ps=null;
				ResultSet rs=null;
				//��������
				List<User> lu=null;
				try {
					//��������
//					Class.forName("com.mysql.jdbc.Driver");
					//��ȡ����
//					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
					conn = new DBConnection();
					//����sql����
					String sql="select * from ms_user";
					//����sql�������
					ps=conn.getConnection().prepareStatement(sql);
					//ִ��sql
					rs=ps.executeQuery();
					//�����ϸ�ֵ
					lu=new ArrayList<User>();
					//�������
						while(rs.next()){
							//��������ֵ
							User u=new User();
							u.setUid(rs.getInt("uid"));
							u.setUname(rs.getString("uname"));
							u.setPwd(rs.getString("pwd"));
							u.setType(rs.getInt("type"));
							//������洢��������
							lu.add(u);
						}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					//�ر���Դ
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					conn.close();
				}
				
				
				//���ؽ��
				return lu;
	}
	//ͨ��uname��ȡ�û���Ϣ
	public int queryByAccount(String uname) {
		//����jdbc����
				DBConnection conn=null;
						PreparedStatement ps=null;
						ResultSet rs=null;
						int ret=0;
						try {
							//��������
//							Class.forName("com.mysql.jdbc.Driver");
							//��ȡ����
//							conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
							conn = new DBConnection();
							//����sql����
							String sql="select * from ms_user where uname=?";
							//����sql�������
							ps=conn.getConnection().prepareStatement(sql);
							ps.setString(1, uname);
							//ִ��sql
							rs=ps.executeQuery();
							//�������
								while(rs.next()){
									ret=rs.getInt("uid");
								}
							
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							//�ر���Դ
							try {
								rs.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								ps.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							conn.close();
						}
						
						
						//���ؽ��
						return ret;
	}
	//�û�ע��
	@Override
	public int userRegDao(User u) {
		//����jdbc����
		DBConnection conn=null;
		PreparedStatement ps=null;
		//��������
		int index=-1;
		try {
			//��������
//			Class.forName("com.mysql.jdbc.Driver");
			//��ȡ����
//			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
			conn = new DBConnection();
			//����SQL����
			String sql="insert into ms_user values(default,?,?,?)";
			//����SQL�������
			ps=conn.getConnection().prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1,u.getUname());
			ps.setString(2, u.getPwd());
			ps.setInt(3, u.getType());
			//ִ��
			index=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{//�ر���Դ
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		}
		//���ؽ��
		return index;
	}
	@Override
	public int delete(int uid) {
		// TODO Auto-generated method stub
		//����jdbc����
		DBConnection conn=null;
				PreparedStatement ps=null;
				//��������
				int index=-1;
				try {
					//��������
//					Class.forName("com.mysql.jdbc.Driver");
					//��ȡ����
//					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
					conn = new DBConnection();
					//����SQL����
					String sql="delete from ms_user where uid=?";
					//����SQL�������
					ps=conn.getConnection().prepareStatement(sql);
					//��ռλ����ֵ
					ps.setInt(1,uid);

					//ִ��
					index=ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{//�ر���Դ
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					conn.close();
				}
				//���ؽ��
				return index;
	}
	@Override
	public int update(User u) {
		// TODO Auto-generated method stub
		//����jdbc����
		DBConnection conn=null;
		PreparedStatement ps=null;
		//��������
		int index=-1;
		try {
			//��������
//			Class.forName("com.mysql.jdbc.Driver");
			//��ȡ����
//			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
			conn = new DBConnection();
			//����SQL����
			String sql="update ms_user set uname=?, pwd=?,type=? where uid=?";
			//����SQL�������
			ps=conn.getConnection().prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1,u.getUname());
			ps.setString(2,u.getPwd());
			ps.setInt(3,u.getType());
			ps.setInt(4,u.getUid());

			//ִ��
			index=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{//�ر���Դ
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		}
		//���ؽ��
		return index;
	}
	@Override
	public int add(User u) {
		//����jdbc����
				DBConnection conn=null;
				PreparedStatement ps=null;
				//��������
				int index=-1;
				try {
					//��������
//					Class.forName("com.mysql.jdbc.Driver");
					//��ȡ����
//					conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
					conn = new DBConnection();
					//����SQL����
					String sql="insert into ms_user values(default,?,?,?)";
					//����SQL�������
					ps=conn.getConnection().prepareStatement(sql);
					//��ռλ����ֵ
					ps.setString(1,u.getUname());
					ps.setString(2, u.getPwd());
					ps.setInt(3, u.getType());
					//ִ��
					index=ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{//�ر���Դ
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					conn.close();
				}
				//���ؽ��
				return index;

	}
	
}
