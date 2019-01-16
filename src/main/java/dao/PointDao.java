package dao;

import java.sql.Connection;
import java.sql.Date;
//java.sql.Date ��java.util.Date ������,�����Date ��ʾ java.sql.Date,��java.util.Date ����ȫ��
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.Point;
/**
 * @function: ��point��Ĳ���
 * @author ��Ǩ��
 * @2017��10��20�� @����1:05:56
 */
public class PointDao {

	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	List<Point> points = null;
	
	/** ��ʼ������*/
	public PointDao() {
		try {
			con = DBUtility.getConnection();// ������ݿ�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @function:ͨ��point����һ��������ѯ��һ����
	 * @param resultColumn
	 * @param String column
	 * @param String columnValue
	 * @return String value
	 */
	public String QueryValueByKey(String resultColumn,String column,String columnValue) {
		String value = null;
		try{
		//1.׼��sql���
		String sql ="select "+resultColumn+" from point where "+column+"= ?";
		//2.��ʼ��ps
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
	 * @function��һ�θ���point��Ĳ��������ﲻ����totalCharge���ֽ��ֵ��
	 * @param reader_id
	 * @param sumpoint
	 * @param lastsign
	 * @param signcount
	 * @return
	 */
	public boolean updateBySignOperate(String reader_id,int sumpoint,Date lastsign,int signcount) {
		boolean isUpdate = false;
		try{
		//1.��ȡsql���
			String sql = "update  point set sumpoint=?,lastsign=?,signcount=? "
					+ "where reader_id= ?";
		//2.׼��ps
			ps = con.prepareStatement(sql);
		//3.������
			ps.setInt(1,sumpoint);
			ps.setDate(2,lastsign);
			ps.setInt(3,signcount);
			ps.setString(4,reader_id);
		//4.ִ��sql
			System.out.println(ps);
			int affectRows =ps.executeUpdate();
			if(affectRows==1) {
				//ֻ�в������ݲ�����һ��Ӱ��ű���Ϊ�ɹ�ִ��
				isUpdate = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isUpdate;
	}
	
	/**
	 * @function:����������һ��ǩ����¼�����ݿ�� point,������Ҫ���β�ѯ���ڶ��β�ѯ���Բ����Ѿ�����ĺ���
	 * @param reader_id
	 * @return boolean isSign
	 */
	public boolean readerSignOperate(String reader_id,Date lastsign) {
		boolean isSign =false ;
		int sumpoint = 0;
		int signcount = 0;
		String sql = null;
		try {
		//1.׼��sql ���
		 sql = "select sumpoint,signcount from point where reader_id =?";
		//2.׼��ps
		ps = con.prepareStatement(sql);
		//3.������
		ps.setString(1, reader_id);
		//4.ִ�в�ѯ
		rs = ps.executeQuery();
		//5.��ý��
		if(rs.next()) {
			//���sumpoint �� signcount ֵ
			sumpoint = rs.getInt("sumpoint");
			signcount = rs.getInt("signcount");		
		}
		//6.sumpoint(������) �� signcount(ǩ����) ����1
		    sumpoint++;
		    signcount++;
		/*//7.�ٴ�׼��sql���
		     sql ="update point set sumpoint = ?,signcount = ?,"
		     		+ "lastsign =?  where reader_id = ? ";
		//8.�ٴ�׼��ps
		     ps = con.prepareStatement(sql);
		//9.�ٴΰ�����
		     ps.setInt(1, sumpoint);
		     ps.setInt(2, signcount);
		     ps.setDate(3,lastsign);
		     ps.setString(4, reader_id);
        //10.ִ�и��²���
		     int affectRows = ps.executeUpdate();
		//11.�ж��Ƿ�ɹ�����
		     if(affectRows==1) {
		    	 //���Ӱ������Ϊ1����ʾ�ɹ�����
		    	 isSign = true;
		     }*/
		 //7.ֱ�ӵ����Ѷ���ķ���������(����point ����)
		    isSign = updateBySignOperate(reader_id, sumpoint, lastsign, signcount);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSign;
	}
	/**
	 * @function:���ݶ��ߵ�id���������
	 * @param int point
	 * @param String  reader_id
	 * @return boolean isReduce
	 */
	public boolean reducepoint(String reader_id,int point ) {
        boolean isReduce = false;
		try{
		//1.׼��sql���
		String sql ="update point set sumpoint = sumpoint-? where reader_id = ?";
		//2.��ʼ��ps
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
	 * function:�ر���Դ
	 * @author ��Ǩ��
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
       //��ת��Ϊ java.sql.Date ����,���ﹹ�����Ĳ�����long���͵ĺ�����
       java.sql.Date sdate = new java.sql.Date(udate.getTime());
       /* ����ִ��һ��ģ��ǩ������ */
       //�Ȼ��һ��java.util.Date�Ķ��� d
       /*boolean isUpdate = pd.updateBySignOperate("s001", 12, sdate, 1);
       System.out.println("isUpdate="+isUpdate);*/
       
       /*����ִ��һ����ʵǩ������*/
       /*boolean isSign = pd.readerSignOperate("s001", sdate);
       System.out.println("isSign="+isSign);*/
       
	}
}
