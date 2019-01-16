package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Admin;
import tools.MD5Encrypt;

/**
 *Group 2 Class Java06
 *Group member:李煜、江宜瑞、韩云涛、李梅、陈迁对  
 *function:表格admin的数据库操作工具类
 * @author 陈迁对 Email: woshiqiandui@gmail.com
 * @version 2017-9-3  上午0:53:32
 */
public class AdminDao {
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	/** 构造函数*/
	public AdminDao() throws Exception {
	  
		con = DBUtility.getConnection();//获得数据库连接
	  
	}
	/**
	 * function:通过 id-password 对 或者 name-password 对 来检查是否为管理员
	 * @param admin
	 * @return isAdmin
	 * @author 陈迁对
	 */
	 public  boolean isAdmin(Admin admin) {
		  boolean isAdmin = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from admin where id = ? and password= ? or name= ? and password= ?";           
				ps = con.prepareStatement(sql);
				String id = admin.getId().trim();//去掉多余的两边空格
				String name = admin.getName().trim();//去掉多余的两边空格
				String password = MD5Encrypt.encryptByMD5(admin.getPassword());//密码空格也是爱，不能删除
				//对密码进行加密操作，需要谨慎使用
				 ps.setString(1, id);
				 ps.setString(2, password);
				 ps.setString(3, name);
				 ps.setString(4, password);
				 rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //管理员存在
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
	//管理员不存在
     return isAdmin;
		
	}
	 /**
		 * function:检查给定的管理员id是否正确
		 * @param admin
		 * @return isRightId
		 * @author 陈迁对
		 */
	 public boolean isRightId(Admin admin) {
		 boolean isRightId = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from admin where id = ? ";           
				ps = con.prepareStatement(sql);
				String id = admin.getId().trim();//去掉多余的两边空格
				 ps.setString(1, id);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //管理员id存在
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
	  //管理员id不存在
		return isRightId;	 
	 }
	 /**
		 * function:检查给定的管理员名字是否正确
		 * @param admin
		 * @return isRightName
		 * @author 陈迁对
		 */
	 public boolean isRightName(Admin admin) {
		 boolean isRightName = false ;
			try {
				con =DBUtility.getConnection();
				String sql="select * from admin where name = ? ";           
				ps = con.prepareStatement(sql);
				String name = admin.getName().trim();//去掉多余的两边空格
				 ps.setString(1, name);
				  rs = ps.executeQuery() ;
				  
			  if(rs.next()) {
				  //管理员name存在
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
	  //管理员name不存在
		return isRightName;	 
	 }
	 /**
	  * function:通过用户的id获得其名字
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
	 * function:获得所有管理员的信息
	 * @param null
	 * @return ResultSet
	 * @author 陈迁对
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
	 * function:根据id删除管理员
	 * @param String id
	 * @return Boolean
	 * @author 陈迁对
	 */
	public Boolean deleteAdminById(String id) {
		int isDelete = 0;// 初始化为0，表示没有对表有影响
		try {
			String sql = "delete from admin where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			isDelete = ps.executeUpdate();// 返回删除的结果 1表示删除成功，0表示没有删除成功
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete == 1) {
			// 如果删除成功
			return true;
		}
		return false;
	}
	/** 
	 * function:增加一条管理员数据到admin表
	 * @param Admin admin
	 * @return Boolean
	 * @author 陈迁对
	 */
	public Boolean insertAdmin(Admin admin) {
		int isInsert = 0;//初始为0，表示没有对表产生影响
		try {
			String sql = "insert into admin values(?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, admin.getId().trim());//插入一定要去掉两边多余的空格
			ps.setString(2,admin.getName().trim());//插入一定要去掉两边多余的空格
			ps.setString(3, MD5Encrypt.encryptByMD5(admin.getPassword().trim()));//插入一定要去掉两边多余的空格
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
