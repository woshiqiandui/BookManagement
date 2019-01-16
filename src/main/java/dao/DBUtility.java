package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/**
 * ���������ࣺ�������ݿ⡢��ѯ��ɾ��������ȹ���
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
		//���������ļ�
		try {
			properties.load(DBUtility.class.getClassLoader().getResourceAsStream
					("db.properties"));
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");//�������ݿ����ӵ�ַ���˿ڡ�����������
			user = properties.getProperty("jdbc.user");//�����û�����
			pwd = properties.getProperty("jdbc.password");//��������
			Class.forName(driver);//��������ط�ʽ
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**��ȡ���ݿ�����*/
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,user,pwd);
	}
	/**�ر����ݿ�����*/
	public static void closeConnection(Connection con){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
	}
	/**��ѯ�����ݵķ����������ѯ*/
	public ResultSet query(String tableName){
		sql = "select * from "+tableName;//����SQL���
		try {
			con = DBUtility.getConnection();
			con.setAutoCommit(false);//�ر��Զ��ύ
			stmt = con.createStatement();
		 
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
 
	 
}
