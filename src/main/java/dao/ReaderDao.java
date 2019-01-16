package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Reader;
import tools.MD5Encrypt;

/**
 * Group 2 Class Java06 Group member:���ϡ������𡢺����Ρ���÷����Ǩ��
 * function:���Reader�����ݿ����������
 * @author ������ Email��1457908216@qq.com
 * @version 2017��9��27�� ����10:05:20
 */
public class ReaderDao {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;

	/** ���캯�� */
	
	public ReaderDao() throws Exception {
			con = DBUtility.getConnection();// ������ݿ�����
	}
	/**
	 * function:ͨ�� id-password �� ���� name-password �� ������Ƿ�Ϊ����
	 * @author ������
	 * @param reader
	 * @return Boolean
	 * @exception
	 */
	 public  boolean isReader(Reader reader) throws Exception{
		  boolean isReader = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from reader where id = ? and password= ? or name= ? and password= ?";           
				ps = con.prepareStatement(sql);
				String id = reader.getId().trim();//ȥ����������߿ո�
				String name = reader.getName().trim();//ȥ����������߿ո�
				String password = MD5Encrypt.encryptByMD5(reader.getPassword());//����ո�Ҳ�ǰ�������ɾ��
				//��������м��ܲ�������Ҫ����ʹ��
				 ps.setString(1, id);
				 ps.setString(2, password);
				 ps.setString(3, name);
				 ps.setString(4, password);
				 rs = ps.executeQuery();
				 /**
				   * �û�����
				   */
			  if(rs.next()) {
					 isReader = true ;  
				 } 
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
				DBUtility.closeConnection(con);
			}
	/**
	 * ���߲�����
	 */
     return isReader;
		
	}
	 /**
		 * function:�������Ķ���id�Ƿ���ȷ
		 * @param reader
		 * @return isRightId
		 * @author ������
		 * @exception
		 */
	 public boolean isRightId(Reader reader) throws Exception{
		 boolean isRightId = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from reader where id = ? ";           
				ps = con.prepareStatement(sql);
				String id = reader.getId().trim();//ȥ����������߿ո�
				 ps.setString(1, id);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //����Աid����
				  isRightId =true ;  
				 } 
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
				DBUtility.closeConnection(con);
			}
	  //����id������
		return isRightId;	 
	 }
	 /**
		 * function:�������Ķ��������Ƿ���ȷ
		 * @param admin
		 * @return isRightName
		 * @author ��Ǩ��
		 * @exception
		 */
	 public boolean isRightName(Reader reader) throws Exception{
		 boolean isRightName = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from reader where name = ? ";           
				ps = con.prepareStatement(sql);
				String name = reader.getName().trim();//ȥ����������߿ո�
				 ps.setString(1, name);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //����Աname����
				  isRightName =true ;  
				 } 
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
				DBUtility.closeConnection(con);
			}
	  //����name������
		return isRightName;	 
	 }
	 /**
	  * function:ͨ��id���name
	  * @param String id
	  * @return name
	  * @author ������
	  * @exception
	  */
	 public String getNameById(String id) throws Exception{
		 String name = null;
		 try {
				String sql = "select name from reader where id =?";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				if(rs.next()) {
					name = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
				DBUtility.closeConnection(con);
			}
		 return name;
	 }
	 /**
	  * function:ͨ��name���id
	  * @param name
	  * @return id
	  * @author ������
	  * @exception
	  */
	 public String getIdByName(String name) throws Exception{
		 String id = null;
		 try {
				String sql = "select id from reader where name =?";
				ps = con.prepareStatement(sql);
				ps.setString(1, name);
				rs = ps.executeQuery();
				if(rs.next()) {
					id = rs.getString(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}finally{
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
				DBUtility.closeConnection(con);
			}
		 return id;
	 }

	/**
	 * function:��������û�����Ϣ
	 * 
	 * @param null
	 * @return ResultSet
	 * @author ������
	 * @exception
	 */
	public List<Reader> selectAll() throws Exception{
        List<Reader> readers=new ArrayList<Reader>();
		try {
			String sql = "select * from reader";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String id=rs.getString("id");
				String name=rs.getString("name");
				String gender=rs.getString("gender");
				String birthday=rs.getString("birthday");
				String password=rs.getString("password");
				readers.add(new Reader(id,name,gender,birthday,password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			DBUtility.closeConnection(con);
		}
		return readers;
	}
    /**
     * function: ͨ��id�õ���һ������
     * @return rs
     * @param id
     * @author ������
     * @exception
     */
    public Reader QueryById(String id) throws Exception{	
    	Reader reader=null;
    	try {
    		String sql = "select * from reader where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs= ps.executeQuery();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
			if(rs.next()){
				String name=rs.getString("name");
				String gender=rs.getString("gender");
				String birthday=rs.getString("birthday");
				String password=rs.getString("password");
				reader=new Reader(name,name,gender,birthday, password);
			}
    	}catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}finally{
    		try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			DBUtility.closeConnection(con);
    	}
    	return reader;
    }
    /**
     * function: ͨ��name�õ���һ������
     * @return rs
     * @param id
     * @author ������
     * @exception
     */
    public Reader QueryByName(String name) throws Exception{	
    	Reader reader=null;
    	try {
    		String sql = "select * from reader where name = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs= ps.executeQuery();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
			if(rs.next()){
				String id=rs.getString("id");
				String gender=rs.getString("gender");
				String birthday=rs.getString("birthday");
				String password=rs.getString("password");
				reader=new Reader(id,name,gender,birthday, password);
			}
    	}catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}finally{
    		DBUtility.closeConnection(con);
    	}
    	return  reader;
    }
	/**
	 * function:����idɾ���û�
	 * 
	 * @param String id
	 * @return Boolean
	 * @author ������
	 * @exception
	 */
	public Boolean deleteReaderById(String id) throws Exception{
		int isDelete = 0;// ��ʼ��Ϊ0����ʾû�жԱ���Ӱ��
		try {
			String sql = "delete from reader where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			isDelete = ps.executeUpdate();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			DBUtility.closeConnection(con);
		}
		if (isDelete == 1) {
			// ���ɾ���ɹ�
			return true;
		}
		return false;
	}

	/**
	 * function:����һ���û����ݵ�Reader��
	 * @param Reader  reader
	 * @return Boolean
	 * @author ������
	 * @exception
	 */
	public Boolean insertReader(Reader reader) throws Exception{
		int isInsert = 0;// ��ʼΪ0����ʾû�жԱ����Ӱ��
		try {
			String sql = "insert into reader values"+"(reader_id_seq.nextval,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, reader.getId().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(2, reader.getName().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(3, reader.getGender().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(4, reader.getBirthday().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			isInsert = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			DBUtility.closeConnection(con);
		}
		if (isInsert == 1) {
			return true;
		}
		return false;
	}
	/**
	 * function:����id����һ��������Ϣ
	 * @author ������
	 * @return Boolean
	 * @exception
	 */
	public Boolean updateById(Reader reader) throws Exception{
		int isUpdate = 0;// ��ʼΪ0����ʾû�жԱ����Ӱ��
		try {
			String sql = "update reader set name=?,gender=?,birthday=?,password=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, reader.getName().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(2, reader.getGender().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(3, reader.getBirthday().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(4, reader.getPassword());// ����һ��Ҫȥ�����߶���Ŀո�
			isUpdate = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			DBUtility.closeConnection(con);
		}
		if (isUpdate == 1) {
			return true;
		}
		return false;
	}
	/**
	 * function:����name����һ��������Ϣ
	 * @author ������
	 * @return Boolean
	 * @exception
	 */
	public Boolean updateByName(Reader reader) throws Exception{
		int isUpdate = 0;// ��ʼΪ0����ʾû�жԱ����Ӱ��
		try {
			String sql = "update reader set id=?,gender=?,birthday=?,password=? where name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, reader.getId().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(2, reader.getGender().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(3, reader.getBirthday().trim());// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(4, reader.getPassword());// ����һ��Ҫȥ�����߶���Ŀո�
			isUpdate = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
			DBUtility.closeConnection(con);
		}
		if (isUpdate == 1) {
			return true;
		}
		return false;
	}
}

