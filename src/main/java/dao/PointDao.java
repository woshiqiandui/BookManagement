package dao;

import java.sql.Connection;
import java.sql.Date;
//java.sql.Date 是java.util.Date 的子类,后面的Date 表示 java.sql.Date,而java.util.Date 必须全称
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.Point;
/**
 * @function: 对point表的操作
 * @author 陈迁对
 * @2017年10月20日 @下午1:05:56
 */
public class PointDao {

	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	List<Point> points = null;
	
	/** 初始化连接*/
	public PointDao() {
		try {
			con = DBUtility.getConnection();// 获得数据库连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @function:通过point表中一个列来查询另一个列
	 * @param resultColumn
	 * @param String column
	 * @param String columnValue
	 * @return String value
	 */
	public String QueryValueByKey(String resultColumn,String column,String columnValue) {
		String value = null;
		try{
		//1.准备sql语句
		String sql ="select "+resultColumn+" from point where "+column+"= ?";
		//2.初始化ps
		ps = con.prepareStatement(sql);
		ps.setString(1, columnValue);
		rs = ps.executeQuery();
		while(rs.next()) {
		value = rs.getString(resultColumn);
		}
		} catch(Exception e) {
		  e.printStackTrace();	
		}
		return value;
	}
	
	/**
	 * @function：一次更新point表的操作，这里不更新totalCharge（现金充值）
	 * @param reader_id
	 * @param sumpoint
	 * @param lastsign
	 * @param signcount
	 * @return
	 */
	public boolean updateBySignOperate(String reader_id,int sumpoint,Date lastsign,int signcount) {
		boolean isUpdate = false;
		try{
		//1.获取sql语句
			String sql = "update  point set sumpoint=?,lastsign=?,signcount=? "
					+ "where reader_id= ?";
		//2.准备ps
			ps = con.prepareStatement(sql);
		//3.绑定数据
			ps.setInt(1,sumpoint);
			ps.setDate(2,lastsign);
			ps.setInt(3,signcount);
			ps.setString(4,reader_id);
		//4.执行sql
			System.out.println(ps);
			int affectRows =ps.executeUpdate();
			if(affectRows==1) {
				//只有插入数据产生了一行影响才被认为成功执行
				isUpdate = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isUpdate;
	}
	
	/**
	 * @function:给读者增加一次签到记录到数据库表 point,这里需要二次查询，第二次查询可以采用已经定义的函数
	 * @param reader_id
	 * @return boolean isSign
	 */
	public boolean readerSignOperate(String reader_id,Date lastsign) {
		boolean isSign =false ;
		int sumpoint = 0;
		int signcount = 0;
		String sql = null;
		try {
		//1.准备sql 语句
		 sql = "select sumpoint,signcount from point where reader_id =?";
		//2.准备ps
		ps = con.prepareStatement(sql);
		//3.绑定数据
		ps.setString(1, reader_id);
		//4.执行查询
		rs = ps.executeQuery();
		//5.获得结果
		if(rs.next()) {
			//获得sumpoint 和 signcount 值
			sumpoint = rs.getInt("sumpoint");
			signcount = rs.getInt("signcount");		
		}
		//6.sumpoint(积分数) 和 signcount(签到数) 各加1
		    sumpoint++;
		    signcount++;
		/*//7.再次准备sql语句
		     sql ="update point set sumpoint = ?,signcount = ?,"
		     		+ "lastsign =?  where reader_id = ? ";
		//8.再次准备ps
		     ps = con.prepareStatement(sql);
		//9.再次绑定数据
		     ps.setInt(1, sumpoint);
		     ps.setInt(2, signcount);
		     ps.setDate(3,lastsign);
		     ps.setString(4, reader_id);
        //10.执行更新操作
		     int affectRows = ps.executeUpdate();
		//11.判断是否成功操作
		     if(affectRows==1) {
		    	 //如果影响行数为1，表示成功操作
		    	 isSign = true;
		     }*/
		 //7.直接调用已定义的方法来处理(更新point 数据)
		    isSign = updateBySignOperate(reader_id, sumpoint, lastsign, signcount);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSign;
	}
	/**
	 * @function:根据读者的id减少其积分
	 * @param int point
	 * @param String  reader_id
	 * @return boolean isReduce
	 */
	public boolean reducepoint(String reader_id,int point ) {
        boolean isReduce = false;
		try{
		//1.准备sql语句
		String sql ="update point set sumpoint = sumpoint-? where reader_id = ?";
		//2.初始化ps
		ps = con.prepareStatement(sql);
		ps.setInt(1, point);
		ps.setString(2, reader_id);
		 int affectRows = ps.executeUpdate();
		if(affectRows == 1) {
			isReduce = true;
		}
		} catch(Exception e) {
		  e.printStackTrace();	
		}
		return isReduce;
	}
	/**
	 * function:关闭资源
	 * @author 陈迁对
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
       PointDao pd = new PointDao();
       String sumpoint = pd.QueryValueByKey("sumpoint", "reader_id", "s001");
       String lastsign = pd.QueryValueByKey("lastsign", "reader_id", "s001");
       System.out.println("sumpoint="+sumpoint);
       System.out.println("lastsign="+lastsign);
       
       java.util.Date udate = new java.util.Date();
       //再转换为 java.sql.Date 类型,这里构造器的参数是long类型的毫秒数
       java.sql.Date sdate = new java.sql.Date(udate.getTime());
       /* 下面执行一次模拟签到操作 */
       //先获得一个java.util.Date的对象 d
       /*boolean isUpdate = pd.updateBySignOperate("s001", 12, sdate, 1);
       System.out.println("isUpdate="+isUpdate);*/
       
       /*下面执行一次真实签到操作*/
       /*boolean isSign = pd.readerSignOperate("s001", sdate);
       System.out.println("isSign="+isSign);*/
       
	}
}
