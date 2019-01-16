package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/**
 * 构建工具类：连接数据库、查询、删除、插入等功能
 * @author LiYu
 */
public class DBUtility {
	private static Properties  properties = new Properties();
	private static String driver = null;
	private static String url = null;
	private static String user = null;
	private static String pwd = null;
	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	public  PreparedStatement ps = null;
	String sql = null;
	static{
		//加载配置文件
		try {
			properties.load(DBUtility.class.getClassLoader().getResourceAsStream
					("db.properties"));
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");//加载数据库连接地址、端口、连接命、名
			user = properties.getProperty("jdbc.user");//加载用户名称
			pwd = properties.getProperty("jdbc.password");//加载密码
			Class.forName(driver);//驱动类加载方式
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**获取数据库连接*/
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,user,pwd);
	}
	/**关闭数据库连接*/
	public static void closeConnection(Connection con){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
	}
	/**查询表数据的方法：整表查询*/
	public ResultSet query(String tableName){
		sql = "select * from "+tableName;//定义SQL语句
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);//关闭自动提交
			stmt = con.createStatement();
		 
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
 
	 
}
