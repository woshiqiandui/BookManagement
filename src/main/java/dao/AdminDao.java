package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Admin;
import tools.MD5Encrypt;

/**
 *Group 2 Class Java06
 *Group member:���ϡ������𡢺����Ρ���÷����Ǩ��  
 *function:���admin�����ݿ����������
 * @author ��Ǩ�� Email: woshiqiandui@gmail.com
 * @version 2017-9-3  ����0:53:32
 */
public class AdminDao {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	/** ���캯��*/
	public AdminDao() throws Exception {
	  
		con = DBUtility.getConnection();//������ݿ�����
	  
	}
	/**
	 * function:ͨ�� id-password �� ���� name-password �� ������Ƿ�Ϊ����Ա
	 * @param admin
	 * @return isAdmin
	 * @author ��Ǩ��
	 */
	 public  boolean isAdmin(Admin admin) {
		  boolean isAdmin = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from admin where id = ? and password= ? or name= ? and password= ?";           
				ps = con.prepareStatement(sql);
				String id = admin.getId().trim();//ȥ����������߿ո�
				String name = admin.getName().trim();//ȥ����������߿ո�
				String password = MD5Encrypt.encryptByMD5(admin.getPassword());//����ո�Ҳ�ǰ�������ɾ��
				//��������м��ܲ�������Ҫ����ʹ��
				 ps.setString(1, id);
				 ps.setString(2, password);
				 ps.setString(3, name);
				 ps.setString(4, password);
				 rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //����Ա����
					 isAdmin = true ;  
				 } 
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DBUtility.closeConnection(con);
			}
	//����Ա������
     return isAdmin;
		
	}
	 /**
		 * function:�������Ĺ���Աid�Ƿ���ȷ
		 * @param admin
		 * @return isRightId
		 * @author ��Ǩ��
		 */
	 public boolean isRightId(Admin admin) {
		 boolean isRightId = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from admin where id = ? ";           
				ps = con.prepareStatement(sql);
				String id = admin.getId().trim();//ȥ����������߿ո�
				 ps.setString(1, id);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //����Աid����
				  isRightId =true ;  
				 } 
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DBUtility.closeConnection(con);
			}
	  //����Աid������
		return isRightId;	 
	 }
	 /**
		 * function:�������Ĺ���Ա�����Ƿ���ȷ
		 * @param admin
		 * @return isRightName
		 * @author ��Ǩ��
		 */
	 public boolean isRightName(Admin admin) {
		 boolean isRightName = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from admin where name = ? ";           
				ps = con.prepareStatement(sql);
				String name = admin.getName().trim();//ȥ����������߿ո�
				 ps.setString(1, name);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //����Աname����
				  isRightName =true ;  
				 } 
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DBUtility.closeConnection(con);
			}
	  //����Աname������
		return isRightName;	 
	 }
	 /**
	  * function:ͨ���û���id���������
	  */
	 public String getNameById(String id) {
		 String name = null;
		 try {
	        	String sql = "select name from admin where id=?";
	        	ps = con.prepareStatement(sql);
	        	ps.setString(1, id);
				rs = ps.executeQuery();
				if(rs.next()) {
					name = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 return name;
	 }
	/** 
	 * function:������й���Ա����Ϣ
	 * @param null
	 * @return ResultSet
	 * @author ��Ǩ��
	 */
	public ResultSet selectAll() {
        
        try {
        	String sql = "select * from admin";
        	ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/** 
	 * function:����idɾ������Ա
	 * @param String id
	 * @return Boolean
	 * @author ��Ǩ��
	 */
	public Boolean deleteAdminById(String id) {
		int isDelete = 0;// ��ʼ��Ϊ0����ʾû�жԱ���Ӱ��
		try {
			String sql = "delete from admin where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			isDelete = ps.executeUpdate();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete == 1) {
			// ���ɾ���ɹ�
			return true;
		}
		return false;
	}
	/** 
	 * function:����һ������Ա���ݵ�admin��
	 * @param Admin admin
	 * @return Boolean
	 * @author ��Ǩ��
	 */
	public Boolean insertAdmin(Admin admin) {
		int isInsert = 0;//��ʼΪ0����ʾû�жԱ����Ӱ��
		try {
			String sql = "insert into admin values(?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, admin.getId().trim());//����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(2,admin.getName().trim());//����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(3, MD5Encrypt.encryptByMD5(admin.getPassword().trim()));//����һ��Ҫȥ�����߶���Ŀո�
			isInsert = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(isInsert == 1){
			return true;
		}
		return false;
		
	}
	 
	 

}
