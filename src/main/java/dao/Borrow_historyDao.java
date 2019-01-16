package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Borrow_history;

public class Borrow_historyDao {
	PreparedStatement ps = null;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql = null;
	
	/** ���캯�� */
	public Borrow_historyDao() {
		try {
			con = DBUtility.getConnection();// ������ݿ�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ResultSet selectAll() {
		try {
			String sql = "select * from borrow_history";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
   
	/**
	 * function:���ݽ������Ų����鼮�Ƿ񱻽��
	 */
	public boolean isReturnByBorrow_id(String borrow_id) {
		boolean isReturn = false;
 
		sql = "select is_return from borrow_history where borrow_id = ?";
		try {
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);// ����statement��ѯ
			ps.setString(1,borrow_id.trim()); 
			rs = ps.executeQuery();
			if(rs.next()) { 
				if(rs.getString(1).equals("1")) {
					//�����ʶΪ1����ʾ�Ѿ��黹��
					isReturn = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		 
		return isReturn;
	}
	/**
	 * function:�����鼮�Ų����鼮�Ƿ񱻽��
	 */
	public boolean isReturnByBook_id(String book_id) {
		boolean isReturn = false;
 
		sql = "select * from borrow_history where book_id = ? and is_return= ?";
		try { 
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);// ����statement��ѯ
			ps.setString(1,book_id.trim()); 
			ps.setString(2, "0");
			rs = ps.executeQuery();
			if(!rs.next()) {  
					isReturn = true;
				} 
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return isReturn;
	} 
	
	/** ����borrow-id���в�ѯ 
	 * @return */
	public ResultSet queryByBorrow_id(String borrow_id) {
		sql = "select * from borrow_history where borrow_id = ?";
		try {
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);// ����statement��ѯ
			ps.setString(1,borrow_id.trim()); 
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}

	/** ����reader-id���в�ѯ 
	 * @return */
	public  ResultSet queryByReader_id(String Reader_id) {
		sql = "select * from borrow_history where reader_id = ?";
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1,Reader_id.trim());
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return rs;
	}

	/** ����book-id���в�ѯ 
	 * @return */
	public ResultSet queryByBook_id(String Book_id) {
		sql = "select * from borrow_history where book_id = ?";
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1,Book_id.trim());
			
			rs=ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}

	/**��ѯ�����¼������
	 * @return */
	public ResultSet queryCount(String borrow_date){
	sql = "select count(distinct reader_id) from borrow_history";
	try {
		con = DBUtility.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(0,borrow_date);
		rs=ps.executeQuery(); 
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	return rs;
}

	/** ��ӽ����¼ 
	 * @return */
	public boolean add(Borrow_history bh) {
		 boolean isAdd = false;
		 int success =0;
		String sql = "insert into borrow_history(book_id,"
				+ "reader_id,borrow_date,is_return) values(?,?,?,?)";
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1,bh.getBook_id());
			ps.setString(2,bh.getReader_id());
			ps.setString(3,bh.getborrow_date());
			ps.setString(4,bh.getIs_return());
			
			 success= ps.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(success==1) {
			isAdd = true;
		}
		return isAdd;
	}
    
	/**
	 * function:������𣬲��޸�˳״̬Ϊ�ѹ黹
	 * @author ��Ǩ��
	 * @return
	 */
	 public boolean returnByBorrow_id(String borrow_id,String rent) {
		 boolean isReturn = false;
		 //�޸ĵ�����
		 int affectRow = 0;
		 sql = "update borrow_history set is_return='1',rent = ? where borrow_id = ?";
			try {
				con = DBUtility.getConnection();// ��ȡ����
				ps = con.prepareStatement(sql);// ����statement��ѯ
				ps.setString(1,rent.trim()); 
				ps.setString(2,borrow_id.trim()); 
				affectRow= ps.executeUpdate();
				  
				if(affectRow==1) {
					//�޸�Ӱ��Ϊ1�����ʾ�޸ĳɹ�
					isReturn = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			 
			return isReturn;
	 }

	/**���ݶ��ߺ�ɾ�������¼
	 * @return */
	public boolean deleteByReader_id(String Reader_id) {
		boolean isDelete = false;
		int effectedRow = 0;
		String sql = "delete from borrow_history where reader_id = ? ";
			try {
				con = DBUtility.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1,Reader_id);
				effectedRow =ps.executeUpdate() ;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(effectedRow!=0) {
				isDelete = true;
			}
			return isDelete;
	}
	
	/**�������ɾ�������¼*/
	public boolean deleteByBook_id(String Book_id) {
		boolean isDelete = false;
		int effectedRow = 0;
		String sql = "delete from borrow_history where Book_id = ? ";
			try {
				con = DBUtility.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1,Book_id);
				effectedRow =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(effectedRow!=0) {
				isDelete = true;
			}
			return isDelete;
	}
	/**���ݽ����ɾ�������¼*/
	public boolean deleteByBorrow_id(String Borrow_id) {
		boolean isDelete = false;
		int effectedRow = 0;
		String sql = "delete from borrow_history where Borrow_id = ? ";
			try {
				con = DBUtility.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1,Borrow_id);
				effectedRow = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(effectedRow!=0) {
				isDelete = true;
			}
			return isDelete;
	}
	
	/**���ݽ���ʱ������ɾ�������¼*/
	public boolean deleteBorrow_date(String Borrow_date) {
		boolean isDelete = false;
		int effectedRow = 0;
		String sql = "delete from borrow_history where Borrow_date = ? ";
			try {
				con = DBUtility.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(0,Borrow_date);
				effectedRow =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(effectedRow!=0) {
				isDelete = true;
			}
			return isDelete;
	}

	/**
	 * function :ͨ������id,��ȡ����ĵ��鼮id
	 * @author ��Ǩ��
	 * @param reader_id
	 * @return bookIds
	 */
	 public  List<String>queryBookIdByReader_id(String reader_id) { 
		 List <String> bookIds = new ArrayList<String>();
		 sql = "select * from borrow_history where reader_id = ?";
			try {
				con = DBUtility.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1,reader_id.trim());
				rs = ps.executeQuery();
				while(rs.next()) {
					//����������ݵ�bookIds����
					bookIds.add(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return bookIds;
	      
	 }
	 
	 /**
		 * function :ͨ������id,��ȡ����ĵ��鼮�Ľ�������
		 * @author ��Ǩ��
		 * @param reader_id
		 * @return bookIds
		 */
		 public  List<Borrow_history>queryBorrowInfoByBorrow_id(String borrow_id) { 
			 List <Borrow_history> borrow_historys = new ArrayList<Borrow_history>();
			 sql = "select * from borrow_history where Borrow_id = ?"; 
				try {
					con = DBUtility.getConnection();
					ps = con.prepareStatement(sql);
					ps.setString(1,borrow_id.trim());
					rs = ps.executeQuery();
					while(rs.next()) {
						//��ʼ��Borrow_history,����ø�ֵ
						Borrow_history bh = new Borrow_history(rs.getString(1), 
								rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6));
						//����������ݵ�borrow_historys����
						borrow_historys.add(bh);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				return borrow_historys;
		      
		 }
		 
	 
	    /**
		 * function :ͨ������id,��ȡ����ĵ��鼮�������Ϣ
		 * @author ��Ǩ��
		 * @param reader_id
		 * @return  
		 */
		 public  List<Borrow_history>queryInfoByReader_id(String reader_id) { 
			 List <Borrow_history> borrow_historys = new ArrayList<Borrow_history>();
			 Borrow_history borrow_history = null;
			 sql = "select * from borrow_history where reader_id = ?";
				try {
					con = DBUtility.getConnection();
					ps = con.prepareStatement(sql);
					ps.setString(1,reader_id.trim());
					rs = ps.executeQuery();
					while(rs.next()) {
						// ������ݵ�borrow_history���� 
						borrow_history = new Borrow_history(rs.getString(1),rs.getString(2),rs.getString(3),
								rs.getString(4),rs.getString(5),rs.getString(6));
						//ÿ�ΰѵõ������ݴ���list��
						 borrow_historys.add(borrow_history);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				return borrow_historys;
		      
		 }
	 
		 /**
			 * function :ͨ������id,��ȡ����ĵ��鼮�������Ϣ ����ҳ
			 * @author ��Ǩ��
			 * @param String reader_id ,int startPage ,int pageSize
			 * @return  
			 */
			 public  List<Borrow_history>queryInfoByReader_idPagination(String reader_id,int startPage,int pageSize) { 
				 List <Borrow_history> borrow_historys = new ArrayList<Borrow_history>();
				 Borrow_history borrow_history = null;
				 sql = "select * from borrow_history where reader_id = ? limit ?,?";
					try {
						con = DBUtility.getConnection();
						ps = con.prepareStatement(sql);
						ps.setString(1,reader_id.trim());
						ps.setInt(2, startPage);
						ps.setInt(3, pageSize);
						rs = ps.executeQuery();
						while(rs.next()) {
							// ������ݵ�borrow_history���� 
							borrow_history = new Borrow_history(rs.getString(1),rs.getString(2),rs.getString(3),
									rs.getString(4),rs.getString(5),rs.getString(6));
							//ÿ�ΰѵõ������ݴ���list��
							 borrow_historys.add(borrow_history);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					return borrow_historys;
			      
			 }
			 /**
				 * function :ͨ������id,��ȡ����ĵ��鼮�������Ϣ�Ľ������
				 * @author ��Ǩ��
				 * @param String reader_id
				 * @return  int resultRow
				 */
				 public int getResultRowByReader_id(String reader_id) { 
                            int resultRow = 0;
					 sql = "select count(*) from borrow_history where reader_id = ? ";
						try {
							con = DBUtility.getConnection();
							ps = con.prepareStatement(sql);
							ps.setString(1,reader_id.trim());
							rs = ps.executeQuery();
							if(rs.next()) {
								resultRow = rs.getInt(1);
							}
 						} catch (SQLException e) {
							e.printStackTrace();
						} 
				      return resultRow;
				 }
		 
		 /**
			 * @author ��Ǩ��
			 * function:ͨ��book_id���������йص�������Ϣ
			 * @return ResultSet
			 */
		 
			public ResultSet getBorrowInformation(String id) {
				try {
					 
					String sql = " select  id,name,writer,publisher,publish_date," +
							"price,type,other,is_return from borrow_history,book where book_id = ?;";
					ps = con.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				return rs;
			}
			
			/**
			 * @author ��Ǩ��
			 * function:ͨ��borrow_id��ѯ�Ȿ���Ƿ񱻽��
			 * @param id
			 * @return isBorrowed
			 */
			public boolean isBorrowedByID(String borrow_id) {
				boolean isBorrowed = false;
				try {
					String sql ="select * from borrow_history where borrow_id = ? and is_return=?";
					ps = con.prepareStatement(sql);
				    ps.setString(1, borrow_id);
				    ps.setString(2,"0");//0��ʾû�й黹
				    rs = ps.executeQuery(); 
					if(rs.next()) {
						//�����������Ϣ�д����Ȿ�飬���һ�����û�й黹��״̬
						isBorrowed = true;
					}   
				} catch(Exception e) {
					e.printStackTrace();
				}
			  return isBorrowed;
			}
			 

		 
	 /**
	  * function:�ر����Ӻ���Ӧ����Դ
	  * @author ��Ǩ��
	  * 
	  */
	 public void close() {
		 try{
			if(rs!=null) {
				rs.close();
			}
			if(ps!=null) {
				ps.close();
			}
			if(stmt!=null){
				stmt.close();
			}
			if(con!=null) {
				con.close();
			}
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 public static void main(String[] args) {
			Borrow_historyDao bhd = new Borrow_historyDao();
			boolean isBorrowed = bhd.isBorrowedByID("10");
			System.out.println("isBorrowed="+isBorrowed);
			boolean isReturn = bhd.isReturnByBook_id("13");
			System.out.println("isReturn="+isReturn);
			bhd.returnByBorrow_id("10", "0.2");
			
			List<Borrow_history> bhs = bhd.queryInfoByReader_idPagination("s001", 0, 5);
			for(Borrow_history bh : bhs) {
				System.out.println(bh.getBook_id());
			}
			int resultRow = bhd.getResultRowByReader_id("s001");
			System.out.println("resultRow="+resultRow);
		}
	 
}
