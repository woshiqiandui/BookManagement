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
 * Group 2 Class Java06 Group member:李煜、江宜瑞、韩云涛、李梅、陈迁对 function:表格Book的数据库操作工具类
 * 
 * @author 李煜
 * @version 2017-9-3 上午0:53:32 function: 封装对书籍表的查询、删除、增加、更新的方法
 *          introduction:在查询中，对于书名字、出版社、作者 我们采用了模糊查询,其他查询运用了精准查询
 */
public class BookDao {

	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection con = null;
	List<Book> books = null;

	/**
	 * 构造函数
	 * 
	 * @throws SQLException
	 */
	public BookDao() throws SQLException {
		try {
			con = DBUtility.getConnection();// 获得数据库连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function:通过rs不断添加book
	 * 
	 * @author 陈迁对
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
	 * function:通过rs不断添加book,图书信息只显示部分
	 * 
	 * @author 陈迁对
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
		//对于过长的数据需要进行部分显示
		if( values[9].length()>50) { 
			shortInfo =  values[9].substring(0, 50)+"...";
		}else {
			shortInfo = values[9];
		}
		books.add(new Book(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7],
				values[8],shortInfo, values[10], values[11]));

	}
	/**
	 * function:获得所有书籍的信息
	 * 
	 * @param null
	 * @return ResultSet
	 * @author 李煜
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
	 * function:获得所有书籍的信息的集合
	 * 
	 * @param null
	 * @return List<Book>
	 * @author 李煜
	 * @throws SQLException
	 */
	public List<Book> selectAllWithInfo() throws SQLException {
		books = new ArrayList<Book>();
		try {
			String sql = "select * from book";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:根据id删除书籍
	 * 
	 * @param String
	 *            id
	 * @return Boolean
	 * @author 李煜
	 * @throws SQLException
	 */
	public Boolean deleteBookById(String id) throws SQLException {
		int isDelete = 0;// 初始化为0，表示没有对表有影响
		try {
			String sql = "delete from book where id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			isDelete = ps.executeUpdate();// 返回删除的结果 1表示删除成功，0表示没有删除成功
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete > 0) {
			// 如果删除成功
			return true;
		}
		return false;
	}

	/**
	 * function:根据书名删除书籍
	 * 
	 * @param String
	 *            name
	 * @return Boolean
	 * @author 李煜 * 此方法不建议使用
	 * @throws SQLException
	 */
	@Deprecated
	public Boolean deleteBookByName(String name) throws SQLException {
		int isDelete = 0;// 初始化为0，表示没有对表有影响
		try {
			String sql = "delete from book where name = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			isDelete = ps.executeUpdate();// 返回删除的结果 1表示删除成功，0表示没有删除成功
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete > 0) {
			// 如果删除成功
			return true;
		}
		return false;
	}

	/**
	 * function:根据国际标准号删除书籍
	 * 
	 * @param String
	 *            name
	 * @return Boolean
	 * @author 李煜
	 * @throws SQLException
	 */
	public Boolean deleteBookByISBN(String ISBN) throws SQLException {
		int isDelete = 0;// 初始化为0，表示没有对表有影响
		try {
			String sql = "delete from book where ISBN = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN);
			isDelete = ps.executeUpdate();// 返回删除的结果 1表示删除成功，0表示没有删除成功
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isDelete > 0) {
			// 如果删除成功
			return true;
		}
		return false;
	}

	/**
	 * function:增加一条书籍数据到book表
	 * 
	 * @param Book
	 *            book
	 * @return Boolean
	 * @author 李煜
	 * @throws SQLException
	 */
	public Boolean insertBook(Book book) throws SQLException {
		int isInsert = 0;// 初始为0，表示没有对表产生影响
		try {
			String sql = "insert into book values(?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			// 插入一定要去掉两边多余的空格
			ps.setString(1, book.getId().trim());
			ps.setString(2, book.getName().trim());
			ps.setString(3, book.getWriter().trim());
			ps.setString(4, book.getISBN().trim());
			ps.setString(5, book.getPublisher().trim());
			ps.setString(6, book.getCheck_in_String().trim());
			ps.setString(7, book.getPublish_String().trim());
			ps.setString(8, book.getPrice());// price是float类型，需要调用我们写过的方法将其转换为字符串
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
	 * function: 通过书编号判断书是否存在
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
			ps.setString(1, id.trim());// 插入一定要去掉两边多余的空格
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
	 * function: 通过书ISBN判断书是否存在
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
			ps.setString(1, ISBN.trim());// 插入一定要去掉两边多余的空格
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
	 * function:根据book_id进行查询书的租金
	 * 
	 * @param String
	 *            id
	 * @author 陈迁对
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
		    ps.setString(1, book_id.trim());// 插入一定要去掉两边多余的空格
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
	 * function:根据borrow_id进行查询书的租金
	 * 
	 * @param String
	 *            id
	 * @author 陈迁对
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
			//这里用了多表查询,rent必须明确是book的
			String sql = "select book.rent from book,borrow_history where borrow_id  = ?";
			ps = con.prepareStatement(sql);
		    ps.setString(1, borrow_id.trim());// 插入一定要去掉两边多余的空格
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
	 * function:根据Book-id进行查询
	 * 
	 * @param String
	 *            id
	 * @author 李煜
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
			ps.setString(1, id.trim());// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:根据Book-id进行查询书籍信息的集合
	 * 
	 * @param String
	 *            id
	 * @author 李煜
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
			ps.setString(1, id.trim());// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:根据Book-name进行模糊查询
	 * 
	 * @param String
	 *            name
	 * @author 李煜
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookName(String name) throws SQLException {
		try {
			if (name.isEmpty()) {
				return null;
			}
			String sql = "select * from book where name like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + name.trim() + "%");// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:根据Book-name进行查询书籍的集体信息
	 * 
	 * @param String
	 *            name
	 * @author 李煜
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lessname.trim() + "%");// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:根据ISBN进行查询
	 * 
	 * @param String
	 *            ISBN
	 * @author 李煜
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookISBN(String ISBN) throws SQLException {
		try {
			if (ISBN.isEmpty()) {
				return null;
			}
			String sql = "select * from book where ISBN = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN.trim());// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:根据ISBN进行查询书籍的具体信息
	 * 
	 * @param String
	 *            ISBN
	 * @author 李煜
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, ISBN.trim());// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:根据Book-writer进行模糊查询
	 * 
	 * @param String
	 *            writer
	 * @author 李煜
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookWriter(String writer) throws SQLException {
		if (writer.isEmpty()) {
			return null;
		}
		try {
			String sql = "select * from book where writer like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + writer.trim() + "%");// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:根据Book-writer进行模糊查询书籍的具体信息
	 * 
	 * @param String
	 *            lesswriter
	 * @author 李煜
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesswriter.trim() + "%");// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:根据Book-publisher进行模糊查询
	 * 
	 * @param String
	 *            publisher
	 * @author 李煜
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet QueryByBookPublisher(String publisher) throws SQLException {
		try {
			if (publisher.isEmpty()) {
				return null;
			}
			String sql = "select * from book where publisher like ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + publisher.trim() + "%");// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:根据Book-publisher进行模糊查询书籍的具体信息
	 * 
	 * @param String
	 *            lesspublisher
	 * @author 李煜
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesspublisher.trim() + "%");// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * function:根据Book-publisher进行模糊查询书籍的具体信息,并分页
	 * 
	 * @param String lesspublisher,int startPage,int pageSize
	 * @author 陈迁对
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesspublisher.trim() + "%");// 插入一定要去掉两边多余的空格
			ps.setInt(2, startPage);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	/**
	 * function:根据Book-publisher获得模糊查询书籍的结果条数
	 * @param String  lesspublisher
	 * @author 陈迁对
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + lesspublisher.trim() + "%");// 插入一定要去掉两边多余的空格
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
	 * function:根据Book-type进行模糊查询
	 * 
	 * @param String
	 *            type
	 * @author 李煜
	 * @return ResultSet
	 * @throws SQLExceptio
	 */
	public ResultSet QueryByBookType(String type) throws SQLException {
		try {
			String sql = "select * from book where type = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, type.trim());// 插入一定要去掉两边多余的空格

			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * function:根据Book-type进行查询书籍的具体信息
	 * 
	 * @param String type
	 * @author 李煜
	 * @return List<Book>
	 * @throws SQLException
	 */
	public List<Book> QueryByBookTypeWithInfo(String type) throws SQLException {
		books = new ArrayList<Book>();
		try {
			String sql = "select * from book where type = ? ORDER BY -id DESC";
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, type.trim());// 插入一定要去掉两边多余的空格
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	/**
	 * Function:根据书籍的ID更新书籍信息
	 * 
	 * @param book
	 * @return boolean
	 * @author 李煜
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
			// 插入一定要去掉两边多余的空格
			ps.setString(1, book.getName().trim());
			ps.setString(2, book.getWriter().trim());
			ps.setString(3, book.getISBN().trim());
			ps.setString(4, book.getPublisher().trim());
			ps.setString(5, book.getCheck_in_String().trim());
			ps.setString(6, book.getPublish_String().trim());
			ps.setString(7, book.getPrice()); 
			ps.setString(8, book.getType().trim());
			//如果simpleinfo为null,那么就是应该为改为空字符串
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
	 * Function:根据书籍的名字更新书籍信息
	 * 
	 * @param book
	 * @return
	 * @author 李煜
	 * @throws SQLException
	 */
	public Boolean updateByName(Book book) throws SQLException {
		int isUpdate = 0;
		String sql = "update book set id = ?,writer=?,ISBN=?,publisher=?,check_in_date=?,publish_date=?,price=?,type=?,simpleinfo=?,rent=?,other=? where name = ?";
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement(sql);
			// 插入一定要去掉两边多余的空格
			ps.setString(1, book.getId().trim());
			ps.setString(2, book.getWriter().trim());
			ps.setString(3, book.getISBN().trim());
			ps.setString(4, book.getPublisher().trim());
			ps.setString(5, book.getCheck_in_String().trim());
			ps.setString(6, book.getPublish_String().trim());
			ps.setString(7, book.getPrice());// price是float类型，需要调用我们写过的方法将其转换为字符串
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
	 * function:根据输入的类型和对应的数据进行模糊查询书籍的具体信息,并分页
	 * 
	 * @param String searchType,int value, int startPage,int pageSize
	 * @author 陈迁对
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + value.trim() + "%");// 插入一定要去掉两边多余的空格
			ps.setInt(2, startPage);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面
				getBooksFromRs(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	/**
	 * function:根据输入的类型和对应的数据进行模糊查询书籍的具体信息,并分页,书资料只显示部分
	 * 
	 * @param String searchType,int value, int startPage,int pageSize
	 * @author 陈迁对
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + value.trim() + "%");// 插入一定要去掉两边多余的空格
			ps.setInt(2, startPage);
			ps.setInt(3, pageSize);
			rs = ps.executeQuery();
			while (rs.next()) {
				// 不断添加book到books里面，图书信息只显示部分
				getBooksFromRsWithShortInfo(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	/**
	 * function:根据类型和值获得模糊查询书籍的结果条数
	 * @param String searchType  String value
	 * @author 陈迁对
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
			con = DBUtility.getConnection();// 获取连接
			ps = con.prepareStatement(sql);
			ps.setString(1, "%" + value.trim() + "%");// 插入一定要去掉两边多余的空格
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
	 * function:关闭资源
	 * 
	 * @author 李煜
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
