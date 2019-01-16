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
 * Group 2 Class Java06 Group member:李煜、江宜瑞、韩云涛、李梅、陈迁对
 * function:表格Reader的数据库操作工具类
 * @author 江宜瑞 Email：1457908216@qq.com
 * @version 2017年9月27日 上午10:05:20
 */
public class ReaderDao {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;

	/** 构造函数 */
	
	public ReaderDao() throws Exception {
			con = DBUtility.getConnection();// 获得数据库连接
	}
	/**
	 * function:通过 id-password 对 或者 name-password 对 来检查是否为读者
	 * @author 江宜瑞
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
				String id = reader.getId().trim();//去掉多余的两边空格
				String name = reader.getName().trim();//去掉多余的两边空格
				String password = MD5Encrypt.encryptByMD5(reader.getPassword());//密码空格也是爱，不能删除
				//对密码进行加密操作，需要谨慎使用
				 ps.setString(1, id);
				 ps.setString(2, password);
				 ps.setString(3, name);
				 ps.setString(4, password);
				 rs = ps.executeQuery();
				 /**
				   * 用户存在
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
	 * 读者不存在
	 */
     return isReader;
		
	}
	 /**
		 * function:检查给定的读者id是否正确
		 * @param reader
		 * @return isRightId
		 * @author 江宜瑞
		 * @exception
		 */
	 public boolean isRightId(Reader reader) throws Exception{
		 boolean isRightId = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from reader where id = ? ";           
				ps = con.prepareStatement(sql);
				String id = reader.getId().trim();//去掉多余的两边空格
				 ps.setString(1, id);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //管理员id存在
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
	  //读者id不存在
		return isRightId;	 
	 }
	 /**
		 * function:检查给定的读者名字是否正确
		 * @param admin
		 * @return isRightName
		 * @author 陈迁对
		 * @exception
		 */
	 public boolean isRightName(Reader reader) throws Exception{
		 boolean isRightName = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from reader where name = ? ";           
				ps = con.prepareStatement(sql);
				String name = reader.getName().trim();//去掉多余的两边空格
				 ps.setString(1, name);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //管理员name存在
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
	  //读者name不存在
		return isRightName;	 
	 }
	 /**
	  * function:通过id获得name
	  * @param String id
	  * @return name
	  * @author 江宜瑞
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
	  * function:通过name获得id
	  * @param name
	  * @return id
	  * @author 江宜瑞
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
	 * function:获得所有用户的信息
	 * 
	 * @param null
	 * @return ResultSet
	 * @author 江宜瑞
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
     * function: 通过id得到这一行数据
     * @return rs
     * @param id
     * @author 江宜瑞
     * @exception
     */
    public Reader QueryById(String id) throws Exception{	
    	Reader reader=null;
    	try {
    		String sql = "select * from reader where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs= ps.executeQuery();// 返回删除的结果 1表示删除成功，0表示没有删除成功
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
     * function: 通过name得到这一行数据
     * @return rs
     * @param id
     * @author 江宜瑞
     * @exception
     */
    public Reader QueryByName(String name) throws Exception{	
    	Reader reader=null;
    	try {
    		String sql = "select * from reader where name = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs= ps.executeQuery();// 返回删除的结果 1表示删除成功，0表示没有删除成功
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
	 * function:根据id删除用户
	 * 
	 * @param String id
	 * @return Boolean
	 * @author 江宜瑞
	 * @exception
	 */
	public Boolean deleteReaderById(String id) throws Exception{
		int isDelete = 0;// 初始化为0，表示没有对表有影响
		try {
			String sql = "delete from reader where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			isDelete = ps.executeUpdate();// 返回删除的结果 1表示删除成功，0表示没有删除成功
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
			// 如果删除成功
			return true;
		}
		return false;
	}

	/**
	 * function:增加一条用户数据到Reader表
	 * @param Reader  reader
	 * @return Boolean
	 * @author 江宜瑞
	 * @exception
	 */
	public Boolean insertReader(Reader reader) throws Exception{
		int isInsert = 0;// 初始为0，表示没有对表产生影响
		try {
			String sql = "insert into reader values"+"(reader_id_seq.nextval,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, reader.getId().trim());// 插入一定要去掉两边多余的空格
			ps.setString(2, reader.getName().trim());// 插入一定要去掉两边多余的空格
			ps.setString(3, reader.getGender().trim());// 插入一定要去掉两边多余的空格
			ps.setString(4, reader.getBirthday().trim());// 插入一定要去掉两边多余的空格
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
	 * function:根据id更新一条读者信息
	 * @author 江宜瑞
	 * @return Boolean
	 * @exception
	 */
	public Boolean updateById(Reader reader) throws Exception{
		int isUpdate = 0;// 初始为0，表示没有对表产生影响
		try {
			String sql = "update reader set name=?,gender=?,birthday=?,password=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, reader.getName().trim());// 更新一定要去掉两边多余的空格
			ps.setString(2, reader.getGender().trim());// 更新一定要去掉两边多余的空格
			ps.setString(3, reader.getBirthday().trim());// 更新一定要去掉两边多余的空格
			ps.setString(4, reader.getPassword());// 更新一定要去掉两边多余的空格
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
	 * function:根据name更新一条读者信息
	 * @author 江宜瑞
	 * @return Boolean
	 * @exception
	 */
	public Boolean updateByName(Reader reader) throws Exception{
		int isUpdate = 0;// 初始为0，表示没有对表产生影响
		try {
			String sql = "update reader set id=?,gender=?,birthday=?,password=? where name=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, reader.getId().trim());// 更新一定要去掉两边多余的空格
			ps.setString(2, reader.getGender().trim());// 更新一定要去掉两边多余的空格
			ps.setString(3, reader.getBirthday().trim());// 更新一定要去掉两边多余的空格
			ps.setString(4, reader.getPassword());// 更新一定要去掉两边多余的空格
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

