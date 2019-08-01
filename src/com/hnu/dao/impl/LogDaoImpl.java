package com.hnu.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hnu.dao.LogDao;
import com.hnu.pojo.Log;
import com.hnu.pojo.User;
import com.hnu.utils.DBConnection;

public class LogDaoImpl implements LogDao{

	@Override
	public int insert(Log log) throws Exception {
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
			String sql="insert into ms_log values(default,?,?,?)";
			//����SQL�������
			ps=conn.getConnection().prepareStatement(sql);
			//��ռλ����ֵ
			ps.setString(1,log.getOperation());
			ps.setTimestamp(2, log.getDate());
			ps.setString(3, log.getState());
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
	public List<Log> logShowDao() {
		//����jdbc����
				DBConnection conn=null;
						PreparedStatement ps=null;
						ResultSet rs=null;
						//��������
						List<Log> lu=null;
						try {
							//��������
//							Class.forName("com.mysql.jdbc.Driver");
							//��ȡ����
//							conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mailsystem","root", "151819");
							conn = new DBConnection();
							//����sql����
							String sql="select * from ms_log";
							//����sql�������
							ps=conn.getConnection().prepareStatement(sql);
							//ִ��sql
							rs=ps.executeQuery();
							//�����ϸ�ֵ
							lu=new ArrayList<Log>();
							//�������
								while(rs.next()){
									//��������ֵ
									Log u=new Log();
									u.setId(rs.getInt("id"));
									u.setOperation(rs.getString("operation"));
									u.setDate(rs.getTimestamp("date"));
									u.setState(rs.getString("state"));
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

}
