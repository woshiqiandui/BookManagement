package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.Book;

/**
 * Group 2 Class Java06 Group member:���ϡ������𡢺����Ρ���÷����Ǩ�� function:���Book�����ݿ����������
 * 
 * @author ����
 * @version 2017-9-3 ����0:53:32 function: ��װ���鼮��Ĳ�ѯ��ɾ�������ӡ����µķ���
 *          introduction:�ڲ�ѯ�У����������֡������硢���� ���ǲ�����ģ����ѯ,������ѯ�����˾�׼��ѯ
 */
public class BookDao {

	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	List<Book> books = null;

	/**
	 * ���캯��
	 * 
	 * @throws SQLException
	 */
	public BookDao() throws SQLException {
		try {
			con = DBUtility.getConnection();// ������ݿ�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function:ͨ��rs�������book
	 * 
	 * @author ��Ǩ��
	 * @param rs2
	 * @return
	 * @return books
	 */
	public void getBooksFromRs(ResultSet rs) {

		String[] values = new String[Book.columnsNumber];
		for (int i = 0; i < Book.columnsNumber; i++) {
			try {
				values[i] = rs.getString(i + 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		books.add(new Book(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7],
				values[8], values[9], values[10], values[11]));

	}
	/**
	 * function:ͨ��rs�������book,ͼ����Ϣֻ��ʾ����
	 * 
	 * @author ��Ǩ��
	 * @param rs2
	 * @return
	 * @return books
	 */
	public void getBooksFromRsWithShortInfo(ResultSet rs) {

		String[] values = new String[Book.columnsNumber];
		for (int i = 0; i < Book.columnsNumber; i++) {
			try {
				values[i] = rs.getString(i + 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String shortInfo = null;
		//���ڹ�����������Ҫ���в�����ʾ
		if( values[9].length()>50) { 
			shortInfo =  values[9].substring(0, 50)+"...";
		}else {
			shortInfo = values[9];
		}
		books.add(new Book(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7],
				values[8],shortInfo, values[10], values[11]));

	}
	/**
	 * function:��������鼮����Ϣ
	 * 
	 * @param null
	 * @return ResultSet
	 * @author ����
	 * @throws SQLException
	 */
	public ResultSet selectAll() throws SQLException {

		try {
			String sql = "select * from book";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:��������鼮����Ϣ�ļ���
	 * 
	 * @param null
	 * @return List<Book>
	 * @author ����
	 * @throws SQLException
	 */
	public List<Book> selectAllWithInfo() throws SQLException {
		books = new ArrayList<Book>();
		try {
			String sql = "select * from book";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:����idɾ���鼮
	 * 
	 * @param String
	 *            id
	 * @return Boolean
	 * @author ����
	 * @throws SQLException
	 */
	public Boolean deleteBookById(String id) throws SQLException {
		int isDelete = 0;// ��ʼ��Ϊ0����ʾû�жԱ���Ӱ��
		try {
			String sql = "delete from book where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			isDelete = ps.executeUpdate();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete > 0) {
			// ���ɾ���ɹ�
			return true;
		}
		return false;
	}

	/**
	 * function:��������ɾ���鼮
	 * 
	 * @param String
	 *            name
	 * @return Boolean
	 * @author ���� * �˷���������ʹ��
	 * @throws SQLException
	 */
	@Deprecated
	public Boolean deleteBookByName(String name) throws SQLException {
		int isDelete = 0;// ��ʼ��Ϊ0����ʾû�жԱ���Ӱ��
		try {
			String sql = "delete from book where name = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			isDelete = ps.executeUpdate();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete > 0) {
			// ���ɾ���ɹ�
			return true;
		}
		return false;
	}

	/**
	 * function:���ݹ��ʱ�׼��ɾ���鼮
	 * 
	 * @param String
	 *            name
	 * @return Boolean
	 * @author ����
	 * @throws SQLException
	 */
	public Boolean deleteBookByISBN(String ISBN) throws SQLException {
		int isDelete = 0;// ��ʼ��Ϊ0����ʾû�жԱ���Ӱ��
		try {
			String sql = "delete from book where ISBN = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN);
			isDelete = ps.executeUpdate();// ����ɾ���Ľ�� 1��ʾɾ���ɹ���0��ʾû��ɾ���ɹ�
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete > 0) {
			// ���ɾ���ɹ�
			return true;
		}
		return false;
	}

	/**
	 * function:����һ���鼮���ݵ�book��
	 * 
	 * @param Book
	 *            book
	 * @return Boolean
	 * @author ����
	 * @throws SQLException
	 */
	public Boolean insertBook(Book book) throws SQLException {
		int isInsert = 0;// ��ʼΪ0����ʾû�жԱ����Ӱ��
		try {
			String sql = "insert into book values(?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(1, book.getId().trim());
			ps.setString(2, book.getName().trim());
			ps.setString(3, book.getWriter().trim());
			ps.setString(4, book.getISBN().trim());
			ps.setString(5, book.getPublisher().trim());
			ps.setString(6, book.getCheck_in_String().trim());
			ps.setString(7, book.getPublish_String().trim());
			ps.setString(8, book.getPrice());// price��float���ͣ���Ҫ��������д���ķ�������ת��Ϊ�ַ���
			ps.setString(9, book.getType().trim());
			ps.setString(10, book.getOther().trim());
			isInsert = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isInsert == 1) {
			return true;
		}
		return false;

	}

	/**
	 * function: ͨ�������ж����Ƿ����
	 * 
	 * @param Id
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistByBookId(String id) throws SQLException {
		boolean isExist = false;
		try {
			if (id.isEmpty()) {
				return isExist;
			}
			String sql = "select * from book where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;

	}

	/**
	 * function: ͨ����ISBN�ж����Ƿ����
	 * 
	 * @param Id
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistByISBN(String ISBN) throws SQLException {
		boolean isExist = false;
		try {
			if (ISBN.isEmpty()) {
				return isExist;
			}
			String sql = "select * from book where ISBN = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;

	}

	/**
	 * function:����book_id���в�ѯ������
	 * 
	 * @param String
	 *            id
	 * @author ��Ǩ��
	 * @return rent
	 * @param book_id
	 * @throws SQLException
	 */
	public String QueryRentByBookId(String book_id) throws SQLException {
		
		String rent = null;
		try {
			if (book_id.isEmpty()) {
				return null;
			}
			String sql = "select rent from book where id = ?";
			ps = con.prepareStatement(sql);
		    ps.setString(1, book_id.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			if(rs.next()) { 
				rent = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return rent;
	}
	
	/**
	 * function:����borrow_id���в�ѯ������
	 * 
	 * @param String
	 *            id
	 * @author ��Ǩ��
	 * @return rent
	 * @param borrrow_id
	 * @throws SQLException
	 */
	public String QueryRentByBorrowId(String borrow_id) throws SQLException {
		
		String rent = null;
		try {
			if (borrow_id.isEmpty()) {
				return null;
			}
			//�������˶���ѯ,rent������ȷ��book��
			String sql = "select book.rent from book,borrow_history where borrow_id  = ?";
			ps = con.prepareStatement(sql);
		    ps.setString(1, borrow_id.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			if(rs.next()) { 
				rent = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return rent;
	}
	
	/**
	 * function:����Book-id���в�ѯ
	 * 
	 * @param String
	 *            id
	 * @author ����
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookId(String id) throws SQLException {
		try {
			if (id.isEmpty()) {
				return null;
			}
			String sql = "select * from book where id = ? ORDER BY -id DESC";
			ps = con.prepareStatement(sql);
			ps.setString(1, id.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:����Book-id���в�ѯ�鼮��Ϣ�ļ���
	 * 
	 * @param String
	 *            id
	 * @author ����
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookIdWithInfo(String id) throws SQLException {
		books = new ArrayList<Book>();
		try {
			if (id.isEmpty()) {
				return null;
			}
			String sql = "select * from book where id = ? ORDER BY -id DESC";
			ps = con.prepareStatement(sql);
			ps.setString(1, id.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:����Book-name����ģ����ѯ
	 * 
	 * @param String
	 *            name
	 * @author ����
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookName(String name) throws SQLException {
		try {
			if (name.isEmpty()) {
				return null;
			}
			String sql = "select * from book where name like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + name.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:����Book-name���в�ѯ�鼮�ļ�����Ϣ
	 * 
	 * @param String
	 *            name
	 * @author ����
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookNameWithInfo(String lessname) throws SQLException {
		books = new ArrayList<Book>();
		try {
			if (lessname.isEmpty()) {
				return null;
			}
			String sql = "select * from book where name like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lessname.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:����ISBN���в�ѯ
	 * 
	 * @param String
	 *            ISBN
	 * @author ����
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookISBN(String ISBN) throws SQLException {
		try {
			if (ISBN.isEmpty()) {
				return null;
			}
			String sql = "select * from book where ISBN = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:����ISBN���в�ѯ�鼮�ľ�����Ϣ
	 * 
	 * @param String
	 *            ISBN
	 * @author ����
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookISBNWithInfo(String ISBN) throws SQLException {
		books = new ArrayList<Book>();
		try {
			if (ISBN.isEmpty()) {
				return null;
			}
			String sql = "select * from book where ISBN = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:����Book-writer����ģ����ѯ
	 * 
	 * @param String
	 *            writer
	 * @author ����
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookWriter(String writer) throws SQLException {
		if (writer.isEmpty()) {
			return null;
		}
		try {
			String sql = "select * from book where writer like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + writer.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:����Book-writer����ģ����ѯ�鼮�ľ�����Ϣ
	 * 
	 * @param String
	 *            lesswriter
	 * @author ����
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookWriterWithInfo(String lesswriter) throws SQLException {
		books = new ArrayList<Book>();
		if (lesswriter.isEmpty()) {
			return null;
		}
		try {
			String sql = "select * from book where writer like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesswriter.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:����Book-publisher����ģ����ѯ
	 * 
	 * @param String
	 *            publisher
	 * @author ����
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookPublisher(String publisher) throws SQLException {
		try {
			if (publisher.isEmpty()) {
				return null;
			}
			String sql = "select * from book where publisher like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + publisher.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:����Book-publisher����ģ����ѯ�鼮�ľ�����Ϣ
	 * 
	 * @param String
	 *            lesspublisher
	 * @author ����
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookPublisherWithInfo(String lesspublisher) throws SQLException {
		books = new ArrayList<Book>();
		try {
			if (lesspublisher.isEmpty()) {
				return null;
			}
			String sql = "select * from book where publisher like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesspublisher.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:����Book-publisher����ģ����ѯ�鼮�ľ�����Ϣ,����ҳ
	 * 
	 * @param String lesspublisher,int startPage,int pageSize
	 * @author ��Ǩ��
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookPublisherPagination(String lesspublisher,int startPage, int pageSize) 
			throws SQLException {
		books = new ArrayList<Book>();
		try {
			if (lesspublisher.isEmpty()) {
				return null;
			}
			String sql = "select * from book where publisher like ? ORDER BY -id DESC limit ?,?";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesspublisher.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setInt(2, startPage);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	/**
	 * function:����Book-publisher���ģ����ѯ�鼮�Ľ������
	 * @param String  lesspublisher
	 * @author ��Ǩ��
	 * @return int resultRows
	 * @throws Exception
	 */
	public int getQueryResultRowsByPublisher(String lesspublisher) 
			throws Exception {
		int resultRows = 0;
		try {
			if (lesspublisher==null||"".equals(lesspublisher)) { 
			}else {
			String sql = "select count(*) from book where publisher like ? ";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesspublisher.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			if(rs.next()) {
				resultRows = rs.getInt(1);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultRows;
	}
 
	/**
	 * function:����Book-type����ģ����ѯ
	 * 
	 * @param String
	 *            type
	 * @author ����
	 * @return ResultSet
	 * @throws SQLExceptio
	 */
	public ResultSet QueryByBookType(String type) throws SQLException {
		try {
			String sql = "select * from book where type = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, type.trim());// ����һ��Ҫȥ�����߶���Ŀո�

			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:����Book-type���в�ѯ�鼮�ľ�����Ϣ
	 * 
	 * @param String type
	 * @author ����
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookTypeWithInfo(String type) throws SQLException {
		books = new ArrayList<Book>();
		try {
			String sql = "select * from book where type = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, type.trim());// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * Function:�����鼮��ID�����鼮��Ϣ
	 * 
	 * @param book
	 * @return boolean
	 * @author ����
	 * @throws SQLException
	 */
	public Boolean updateById(Book book) throws SQLException {
		int isUpdate = 0;
		String sql = "update book set name = ?,writer=?,ISBN=?,publisher=?,check_in_date=?,publish_date=?,price=?,type=?,simpleinfo=?,rent=?,other=? where id = ?";
		try {
			System.out.println("book.name="+book.getName());
			con = DBUtility.getConnection();
			ps = con.prepareStatement(sql);
			ps = con.prepareStatement(sql);
			// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(1, book.getName().trim());
			ps.setString(2, book.getWriter().trim());
			ps.setString(3, book.getISBN().trim());
			ps.setString(4, book.getPublisher().trim());
			ps.setString(5, book.getCheck_in_String().trim());
			ps.setString(6, book.getPublish_String().trim());
			ps.setString(7, book.getPrice()); 
			ps.setString(8, book.getType().trim());
			//���simpleinfoΪnull,��ô����Ӧ��Ϊ��Ϊ���ַ���
			ps.setString(9, (book.getSimpleinfo()==null?"":book.getSimpleinfo()).trim());
			ps.setString(10, book.getRent().trim());
			ps.setString(11, book.getOther().trim());
			ps.setString(12, book.getId().trim());
			isUpdate = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isUpdate == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Function:�����鼮�����ָ����鼮��Ϣ
	 * 
	 * @param book
	 * @return
	 * @author ����
	 * @throws SQLException
	 */
	public Boolean updateByName(Book book) throws SQLException {
		int isUpdate = 0;
		String sql = "update book set id = ?,writer=?,ISBN=?,publisher=?,check_in_date=?,publish_date=?,price=?,type=?,simpleinfo=?,rent=?,other=? where name = ?";
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement(sql);
			// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setString(1, book.getId().trim());
			ps.setString(2, book.getWriter().trim());
			ps.setString(3, book.getISBN().trim());
			ps.setString(4, book.getPublisher().trim());
			ps.setString(5, book.getCheck_in_String().trim());
			ps.setString(6, book.getPublish_String().trim());
			ps.setString(7, book.getPrice());// price��float���ͣ���Ҫ��������д���ķ�������ת��Ϊ�ַ���
			ps.setString(8, book.getType().trim());
			ps.setString(9, book.getOther().trim());
			ps.setString(10, book.getSimpleinfo().trim());
			ps.setString(11, book.getRent().trim());
			ps.setString(12, book.getName().trim());
			isUpdate = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isUpdate == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * function:������������ͺͶ�Ӧ�����ݽ���ģ����ѯ�鼮�ľ�����Ϣ,����ҳ
	 * 
	 * @param String searchType,int value, int startPage,int pageSize
	 * @author ��Ǩ��
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryAndPagination
	(String searchType,String value,int startPage, int pageSize) 
			throws SQLException { 
		books = new ArrayList<Book>();
		try {
			if (value==null||"".equals(value.trim())) {
				return null;
			}
			/*if(searchType.equals("id")) {
			}else if(searchType.equals("name")) {
			}else if(searchType.equals("ISBN")) {
			}else if(searchType.equals("writer")) {
			}else if(searchType.equals("publisher")) {
			}else if(searchType.equals("type")) {
			}*/
			String sql = "select * from book where "+searchType+" like ? ORDER BY -id DESC limit ?,?";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + value.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setInt(2, startPage);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books����
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	/**
	 * function:������������ͺͶ�Ӧ�����ݽ���ģ����ѯ�鼮�ľ�����Ϣ,����ҳ,������ֻ��ʾ����
	 * 
	 * @param String searchType,int value, int startPage,int pageSize
	 * @author ��Ǩ��
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryAndPaginationWithShortIinfo
	(String searchType,String value,int startPage, int pageSize) 
			throws SQLException { 
		books = new ArrayList<Book>();
		try {
			if (value==null||"".equals(value.trim())) {
				return null;
			}
			 
			String sql = "select * from book where "+searchType+" like ? ORDER BY -id DESC limit ?,?";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + value.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			ps.setInt(2, startPage);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// �������book��books���棬ͼ����Ϣֻ��ʾ����
				getBooksFromRsWithShortInfo(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	/**
	 * function:�������ͺ�ֵ���ģ����ѯ�鼮�Ľ������
	 * @param String searchType  String value
	 * @author ��Ǩ��
	 * @return int resultRows
	 * @throws Exception
	 */
	public int getQueryResultRows(String searchType, String value ) 
			throws Exception {
		int resultRows = 0;
		try {
			if (value==null||"".equals(value)) { 
			}else {
			String sql = "select count(*) from book where "+searchType+" like ? ";
			con = DBUtility.getConnection();// ��ȡ����
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + value.trim() + "%");// ����һ��Ҫȥ�����߶���Ŀո�
			rs = ps.executeQuery();
			if(rs.next()) {
				resultRows = rs.getInt(1);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultRows;
	}
	/**
	 * function:�ر���Դ
	 * 
	 * @author ����
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
	BookDao bookDao = null;
	try {
		bookDao = new BookDao();
		 List<Book> books =bookDao.QueryAndPagination("id","1", 0, 2);
		 for(Book book:books) {
			 System.out.println(book.getName()+book.getId());
		 }
		 int resultRows = bookDao.getQueryResultRows("id","1");
		 System.out.println("resultRows="+resultRows);
	} catch ( Exception e1) {
		e1.printStackTrace();
	}
	
}
	
}
