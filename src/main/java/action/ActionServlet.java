package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import dao.AdminDao;
import dao.BookDao;
import dao.Borrow_historyDao;
import dao.PointDao;
import dao.ReaderDao;
import entities.Admin;
import entities.Book;
import entities.Reader;
import tools.BookCheck;
import entities.Borrow_history;

/**
 * function: 图示管理系统的后台处理
 * 
 * @author 李煜 、江宜瑞、马超、韩云涛、李梅 combined and modified by 陈迁对
 *
 */
public class ActionServlet extends HttpServlet {

	/**
	 * 自动生成的序列号
	 */
	private static final long serialVersionUID = -8446871030854461953L;
	/**
	 * 域（成员变量）：uri:资源路径，action:可以辨识处理的类型，session:会话
	 */
	// 获取请求资源路径
	private String uri;
	// 获取请求资源路径中除应用名以外的部分
	private String action;
	private HttpSession session;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置中文的输入和输出
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		uri = request.getRequestURI();
		action = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
		session = request.getSession();
		// System.out.println(session.getId());
		if ("readerlogin".equals(action)) {
			// 对readerlogin.teg进行处理
			readerloginHandle(request, response);
		} else if ("adminlogin".equals(action)) {
			// 对adminlogin.teg进行处理
			adminloginHandle(request, response);
		} else if ("logout".equals(action)) {
			// 对退出进行处理
			exitHanlde(response);
		} else if ("readerlogout".equals(action)) {
			// 对读者退出进行处理
			readerexitHanlde(response);
		}else if ("adminlogout".equals(action)) {
			// 对管理员退出进行处理
			adminexitHanlde(response);
		}else if ("adminsearch".equals(action)) {
			// 如果是用户进行搜索，那么会进行书籍检索并返回数据
			adminsearchHandle(request, response);
		} else if ("readersearch".equals(action)) {
			// 如果是用户进行搜索，那么会进行书籍检索并返回数据
			readersearchHandle(request, response);
		} else if (("loadbook").equals(action)) {
			// 把将要处理的数据加载到一个新的页面上
			loadbookHandle(request, response);
		} else if ("modifybook".equals(action)) {
			// 修改书信息
			modifybookHandle(request, response);
		} else if ("deletebook".equals(action)) {
			// 删除书操作
			deletebookHandle(request, response);
		} else if ("bookinfo".equals(action)) {
			// 加载书的详情
			bookinfoHandle(request, response);
		} else if ("borrowbook".equals(action)) {
			// 将借阅信息加载到借阅栏，其实就是session处理。
			borrowbookHandle(request, response);
		} else if ("borrowcart".equals(action)) {
			// 显示借阅车的信息
			showborrowcart(request, response);
		} else if ("borrowconfirm".equals(action)) {
			// 对借阅图书动作进行处理
			borrowconfirmHandle(request, response);
		} else if ("showborrowbook".equals(action)) {
			// 对图书借阅的信息显示
			showborrowbook(request, response);
		} else if ("returnbook".equals(action)) {
			// 对图书归还的确认处理
			returnbookHandle(request, response);
		} else if ("showborrowinfo".equals(action)) {
			// 根据用户的要求显示借书图书的内容
			showborrowinfo(request, response);
		} else if ("sign".equals(action)) {
			// 用户签到
			sign(request, response);
		} else if ("showpoint".equals(action)) {
			showpoint(request, response);
		} else if ("showlastsign".equals(action)) {
			showlastsign(request, response);
		}

	}

	/**
	 * @function: 显示上次登录的时间
	 * @param request
	 * @param response
	 */
	public void showlastsign(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		PointDao pointdao = null;
		try {
			// 获得输出流
			out = response.getWriter();
			// 获得读者的id
			String readerid = (String) session.getAttribute("readerid");
			System.out.println("readerid=" + readerid);
			if (readerid == null) {
				// 如果session中没有读者的id,那么读者一定是没有登陆，就跳转到登陆界面
				response.sendRedirect("readerlogin.teg");
				return;
			} else {
				pointdao = new PointDao();
				// 查询读者上次的登陆时间
				String lastsign = pointdao.QueryValueByKey("lastsign", "reader_id", readerid);
				out.print(lastsign);
			}
			pointdao.close();
		} catch (Exception e) {

		}
		out.close();
	}

	/**
	 * @function:动态地显示读者的积分
	 * @param request
	 * @param response
	 */
	public void showpoint(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		PointDao pointdao = null;
		try {
			// 获得输出流
			out = response.getWriter();
			// 获得读者的id
			String readerid = (String) session.getAttribute("readerid");
			System.out.println("readerid=" + readerid);
			if (readerid == null) {
				// 如果session中没有读者的id,那么读者一定是没有登陆，就跳转到登陆界面
				response.sendRedirect("readerlogin.teg");
				return;
			} else {
				pointdao = new PointDao();
				// 查询读者对应的积分数据
				String sumpoint = pointdao.QueryValueByKey("sumpoint", "reader_id", readerid);
				out.print(sumpoint);
			}
			pointdao.close();
		} catch (Exception e) {

		}
		out.close();
	}

	/**
	 * @function:读者签到处理
	 * @param request
	 * @param response
	 */
	public void sign(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("come to sign");
		PrintWriter out = null;
		PointDao pointDao = null;
		try {
			// 获得out
			out = response.getWriter();
			// 获得session 里面存储的 readerid
			String readerid = (String) session.getAttribute("readerid");
			if (readerid == null) {
				// 如果session中没有读者的id,那么读者一定是没有登陆，就跳转到登陆界面
				response.sendRedirect("readerlogin.teg");
				return;
			} else {
				pointDao = new PointDao();
				// 获得该读者上次签到的时间
				String lastsign = pointDao.QueryValueByKey("lastsign", "reader_id", readerid);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// 获得当前时间
				java.util.Date now = new java.util.Date();
				// 转换当前时间为当天日期，并转换为字符串形式
				String nowStr = sdf.format(now);
				System.out.println("now=" + nowStr);
				System.out.println("last=" + lastsign);
				// 如果lastsign不为空,并且和今天日期相同，那么就是一天重复签到了
				if (lastsign != null && lastsign.equals(nowStr)) {
					// 如果数据库里存储的时间和现在一致，返回失败
					out.print("fail");

				} else {
					// 如果数据库里存储的时间和现在不一致，那么就更新数据库
					// 将当天java.util的日期转换java.sql的日期
					java.sql.Date sqlnow = new java.sql.Date(now.getTime());
					pointDao.readerSignOperate(readerid, sqlnow);
					out.print("success");
				}
				// 关闭数据库连接
				pointDao.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.close();
	}

	public void showborrowinfo(HttpServletRequest request, HttpServletResponse response) {
		// 待开发

	}

	/**
	 * @function:显示已经借阅书的相关信息
	 * @param request
	 * @param response
	 */
	public void showborrowbook(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 1.从session中获得该读者的id
			String reader_id = (String) session.getAttribute("readerid");
			if (reader_id == null) {
				String err_msg = "您的登陆身份过期或者您没有登陆，请重新登陆";
				request.setAttribute("err_msg", err_msg);
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
				return;
			}

			// 2.获得当前页
			String currentpage = request.getParameter("currentPage");
			int currentPage = 1;// 默认第一次查询的时候，currentPage为1，当前为第一页
			if (currentpage == null || "".equals(currentpage.trim())) {
				// 什么都不做
			} else {
				// 如果有值传入
				currentPage = Integer.parseInt(currentpage);
			}
			String startpage = null; // startPage的string表示
			int startPage = 0;// 默认从0开始，就是第一条记录开始

			int pageSize = 3; // 默认每次获取 3条记录，这个是定值

			startPage = (currentPage - 1) * pageSize;

			// 3.通过读者id查询该读者的所有借阅信息，并存储信息
			Borrow_historyDao bhd = new Borrow_historyDao();
			// 获得分页查询的结果
			List<Borrow_history> borrow_historys = bhd.queryInfoByReader_idPagination(reader_id, startPage, pageSize);
			// 根据读者的id对获得总结果条数
			int resultRow = bhd.getResultRowByReader_id(reader_id);

			// 关闭连接
			bhd.close();
			// 4计算出总结果的页数
			int pages = resultRow / pageSize + (resultRow % pageSize == 0 ? 0 : 1);
			// 5.转发信息到页面
			// 判断结果是否为空值，如果为空发送提示
			if (borrow_historys == null || borrow_historys.size() == 0) {
				request.setAttribute("emptyresult", "你没有借过任何书");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			} else {
				// 转发结果的借阅记录、总结果分页之后的总页数、当前浏览的所在页数、空list（长度为页数）
				request.setAttribute("borrow_historys", borrow_historys);
				request.setAttribute("pages", pages);
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("arr", new List[pages]);
				System.out.println("resultRow=" + resultRow);
				System.out.println("pages=" + pages);
				request.getRequestDispatcher("showborrowbook.jsp").forward(request, response);
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function:对还书操作进行处理
	 * @param request
	 * @param response
	 */
	public void returnbookHandle(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1.获得session中的用户id,如果为空，则跳转到上一页
			String readerid = (String) session.getAttribute("readerid");
			if (readerid == null) {
				response.sendRedirect("showborrowbook.teg");
				return;
			}
			// 2.获得request的borrow_id,如果为空则返回上一页，就是showborrowbook.teg
			String borrow_id = request.getParameter("borrow_id");
			if (borrow_id == null) {
				response.sendRedirect("showborrowbook.teg");
				return;
			}
			// 3。从数据库中获取时间，并于此刻时间对比，计算差值（天数），从而得到读者应该缴纳的借书金额
			Borrow_historyDao bhd = new Borrow_historyDao();
			List<Borrow_history> borrow_historys = bhd.queryBorrowInfoByBorrow_id(borrow_id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 获取借阅的时间字符串
			String borrow_date = borrow_historys.get(0).getborrow_date();
			// 将借阅时间字符串转换为时间类对象
			java.util.Date borrowdate = sdf.parse(borrow_date);
			// 获取当前时间对应的时间类对象
			java.util.Date nowdate = new java.util.Date();
			// 计算时间差（天数） ,四舍五入
			int days = (int) ((nowdate.getTime() / 86400000 - borrowdate.getTime() / 86400000));
			// 获得费用
			BookDao bookDao = new BookDao();
			String rent = bookDao.QueryRentByBorrowId(borrow_id);
			// 关闭连接
			bookDao.close();
			// 计算花费
			float fee = (float) (days * Double.parseDouble(rent));
			// 计算对应的积分 （0.1元对应4积分 ,所以1元对于40积分）
			int feepoint = (int) (fee * 40);
			System.out.println("feepoint=" + feepoint);
			System.out.println("fee=" + fee);
			// 查看该读者积分是否足够
			PointDao pointDao = new PointDao();
			String sumpointstr = pointDao.QueryValueByKey("sumpoint", "reader_id", readerid);
			// 获得对应积分
			int sumpoint = Integer.parseInt(sumpointstr.trim());
			if (sumpoint < feepoint) {
				// 如果剩余的积分不够
				request.setAttribute("err_msg", "积分不足，无法还书，请充值或多签到");
				request.getRequestDispatcher("showborrowbook.teg").forward(request, response);
			} else {
				// 如果积分还够，就先扣除积分
				boolean isReduce = pointDao.reducepoint(readerid, feepoint);
				// 4.从数据库中修改borrow_history 中的借书标志位为1,同时记录费用
				boolean isReturn = bhd.returnByBorrow_id(borrow_id, fee + "");
				// 5.提示还书操作成功
				String err_msg = null;
				String success_msg = null;
				if (isReturn == true && isReturn == true) {
					System.out.println("还书成功");
					success_msg = "还书成功，花费" + feepoint + "积分";
				} else {
					System.out.println("还书失败");
					err_msg = "还书失败";
				}
				// 6.重定向到showborrowbook.teg
				// 根据不同的结果发送不同的信息
				if (isReturn) {
					request.setAttribute("success_msg", success_msg);
				} else {
					request.setAttribute("err_msg", err_msg);
				}
				request.getRequestDispatcher("showborrowbook.teg").forward(request, response);

			}

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function:对借阅书操作的确认
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void borrowconfirmHandle(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1.从session里获得需要读者的readerid,如果没有则跳转回到首页
			String reader_id = (String) session.getAttribute("readerid");
			if (reader_id == null) {
				System.out.println("您的登陆身份过期或者您没有登陆，请重新登陆");
				String err_msg = "您的登陆身份过期或者您没有登陆，请重新登陆";
				request.setAttribute("err_msg", err_msg);
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
				return;
			}
			// 2.从request获得book的id,如果没有则返回到借书车borrowcart.jsp界面
			String book_id = request.getParameter("id");
			if (book_id == null) {
				response.sendRedirect("borrowcart.jsp");
				return;
			}
			// 3.如果有了readerid和book的id，则在数据库里将图书借阅标记设置为借出，同时也要对用户的借出行为进行保存
			Borrow_historyDao bhd = new Borrow_historyDao();
			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 获得当前时间的字符串
			String borrow_date = sdf.format(date);
			Borrow_history bh = new Borrow_history(book_id, reader_id, borrow_date, "0");
			System.out.println("当前时间是" + borrow_date);
			boolean isAdd = bhd.add(bh);
			if (isAdd) {
				System.out.println("添加借阅记录成功");
			} else {
				System.out.println("添加借阅记录失败");
			}
			// 4.在session中获得bookIds，将处理过的book的id从其中删除
			@SuppressWarnings("unchecked")
			ArrayList<String> bookIds = (ArrayList<String>) session.getAttribute("bookIds");
			String success_msg = null;
			String err_msg = null;
			// 再做一次判断为好
			if (bookIds.contains(book_id)) {
				// 移出book_id
				boolean isRemove = bookIds.remove(book_id);
				if (isRemove) {
					System.out.println("从session中移除bookid成功");
					success_msg = "操作成功";
					// 再移出list中的book
					BookDao bookDao = new BookDao();
					if ((session.getAttribute("books")) != null) {
						((List<Book>) session.getAttribute("books"))
								.remove(bookDao.QueryByBookIdWithInfo(book_id).get(0));
					}
				} else {
					System.out.println("移除bookid失败，请检查");
					err_msg = "移除失败，请重试";
				}
			} else {
				System.out.println("该图书没有被您预先选中");
				err_msg = "该图书没有被您预先选中";
			}

			// 5.转发到borrowcart.jsp页面，其实就是刷新页面
			if (err_msg != null) {
				request.setAttribute("err_msg", err_msg);
			} else if (success_msg != null) {
				request.setAttribute("success_msg", success_msg);
			}

			if (session.getAttribute("books") != null && ((List<Book>) session.getAttribute("books")).size() != 0) {
				System.out.println("长度" + ((List<Book>) session.getAttribute("books")).size());
				System.out.println("跳转到borrwcart");
				// 如果session 里面的 books 对象存在且其中的元素不为空，则取出
				request.setAttribute("books", session.getAttribute("books"));
				request.getRequestDispatcher("borrowcart.teg").forward(request, response);
			} else {
				// 如果没有值了，那么就跳转到首页
				System.out.println("跳转到读者首页");
				request.setAttribute("success_msg", "数据处理成功，借阅车为空，切换到您的首页");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;

	}

	/**
	 * @function:显示借阅车的详细信息
	 * @param request
	 * @param response
	 */
	public void showborrowcart(HttpServletRequest request, HttpServletResponse response) {
		// 1.判断session中的变量bookIds是否有值
		@SuppressWarnings("unchecked")
		ArrayList<String> bookIds = (ArrayList<String>) session.getAttribute("bookIds");
		try {
			// 2.如果没有值，则跳转到读者首页
			if (bookIds == null || bookIds.size() == 0) {
				System.out.println("没有待借阅的书");
				String err_msg = "没有待借阅的书";
				request.setAttribute("emptyresult", "借阅车为空，请添加借阅数据");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
				return;
			}
			// 3.如果有值，就获得对应的book对象，存入数据
			else {
				BookDao bookDao = new BookDao();
				ArrayList<Book> books = new ArrayList<Book>();
				try {
					for (int i = 0; i < bookIds.size(); i++) {
						// 取得每一个id对应的book对象
						List<Book> book = bookDao.QueryByBookIdWithInfo(bookIds.get(i));
						// 从book中取出第一个，因为一共才一个，然后存到books里面。
						books.add(book.get(0));
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
				// 保存books 到 session 中
				session.setAttribute("books", books);
				// 4.先绑定，后重定向至borrowcart界面
				request.setAttribute("books", books);
				request.getRequestDispatcher("borrowcart.jsp").forward(request, response);

				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function:借阅操作的处理
	 * @param request
	 * @param response
	 */
	public void borrowbookHandle(HttpServletRequest request, HttpServletResponse response) {
		// 1.从session里获得需要读者的readerid,如果没有则跳转回到首页
		String reader_id = (String) session.getAttribute("readerid");
		if (reader_id == null) {
			System.out.println("您的登陆身份过期或者您没有登陆，请重新登陆");
			String err_msg = "您的登陆身份过期或者您没有登陆，请重新登陆";
			request.setAttribute("err_msg", err_msg);
			try {
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 1.获取的book的id值,并判断该书是否被借出
		String id = request.getParameter("id");
		if (id == null) {
			try {
				response.sendRedirect("readerdefault.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		Borrow_historyDao bhd = new Borrow_historyDao();
		boolean isReturn = bhd.isReturnByBook_id(id);
		if (!isReturn) {
			System.out.println("该书已被借出，请重新选择");
			// 重定向到首页
			try {
				String err_msg = "该书已被借出，请重新选择";
				request.setAttribute("err_msg", err_msg);
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		System.out.println("bookid=" + id);
		// 2.取出session里面的变量bookIds
		@SuppressWarnings("unchecked")
		ArrayList<String> bookIds = (ArrayList<String>) session.getAttribute("bookIds");
		// 3.判断bookIds是否为空
		if (bookIds == null) {
			// 4.如果为空，就初始化bookIds,并添加这次传入的数据
			bookIds = new ArrayList<String>();
			bookIds.add(id);
			// 保存到session变量中
			session.setAttribute("bookIds", bookIds);
			// 提示操作成功
		} else {
			// 5.如果不为空，查看库中是否存在该数值，如果不存在则进行session保存。zz
			for (int i = 0; i < bookIds.size(); i++) {
				System.out.println("bookIds[" + i + "]=" + bookIds.get(i));
			}
			String err_msg = null;
			String success_msg = null;
			if (!bookIds.contains(id)) {
				// 如果不包含，则添加
				bookIds.add(id);
				// 保存到session变量中
				session.setAttribute("bookIds", bookIds);
				// 提示操作成功
				success_msg = "成功添加到借阅车";
				request.setAttribute("success_msg", success_msg);
			} else {
				// 什么都不做，提示已存在
				System.out.println("已存在该书，请勿重复添加");
				err_msg = "借阅车已存在该书，请勿重复添加";
				request.setAttribute("err_msg", err_msg);
			}
		}
		// 跳转到用户主界面
		try {
			request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function:显示用户搜索的书籍详细信息
	 * @param request
	 * @param response
	 */
	public void bookinfoHandle(HttpServletRequest request, HttpServletResponse response) {
		// 从session里获得需要读者的readerid,如果没有则跳转回到首页
		String reader_id = (String) session.getAttribute("readerid");
		if (reader_id == null) {
			System.out.println("您的登陆身份过期或者您没有登陆，请重新登陆");
			String err_msg = "您的登陆身份过期或者您没有登陆，请重新登陆";
			request.setAttribute("err_msg", err_msg);
			try {
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 跳转到加载页面
		String id = request.getParameter("id");
		// 获得书的信息
		BookDao bookDao;

		List<Book> books = null;
		try {
			bookDao = new BookDao();
			System.out.println(id);
			books = bookDao.QueryByBookIdWithInfo(id);
			System.out.println(books.get(0).getName());
			// 绑定数据
			request.setAttribute("books", books);
			// 转发数据到load.jsp页面
			request.getRequestDispatcher("bookinfo.jsp").forward(request, response);
		} catch (Exception e1) {
			System.out.println("查询图书失败");
			e1.printStackTrace();
		}

	}

	/**
	 * @function: 用于管理员删除图书的操作
	 * @param request
	 * @param response
	 */
	public void deletebookHandle(HttpServletRequest request, HttpServletResponse response) {
		// 判断是否以管理员身份登陆
		String adminname = (String) session.getAttribute("adminname");
		if (adminname == null || "".equals(adminname.trim())) {
			// 如果读者没登陆，那么就跳转到管理员登陆页面
			try {
				response.sendRedirect("adminlogin.teg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 跳转到修改页面
		String id = request.getParameter("id");
		Boolean isDelete = false;
		try {
			BookDao bookDao = new BookDao();
			isDelete = bookDao.deleteBookById(id);
		} catch (SQLException e) {
			System.out.println("删除失败，请重新尝试！");
		}
		String err_msg = null;
		String deletesuccess_msg = null;
		try {
			if (isDelete) {
				System.out.println("删除成功");
				deletesuccess_msg = "删除成功";
				request.setAttribute("deletesuccess_msg", deletesuccess_msg);
			} else {
				System.out.println("删除失败");
				err_msg = "删除失败";
				request.setAttribute("err_msg", err_msg);
			}
			request.getRequestDispatcher("admindefault.jsp").forward(request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function:用于管理员修改书籍信息的操作
	 * @param request
	 * @param response
	 */
	public void modifybookHandle(HttpServletRequest request, HttpServletResponse response) {
		// 判断是否以管理员身份登陆
				String adminname = (String) session.getAttribute("adminname");
				if (adminname == null || "".equals(adminname.trim())) {
					// 如果读者没登陆，那么就跳转到管理员登陆页面
					try {
						response.sendRedirect("adminlogin.teg");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
		// 跳转到修改页面
		String id = request.getParameter("id");
		if (id == null || "".equals(id.trim())) {
			// 如果id为空，说明值就没有传过来
			try {
				response.sendRedirect("adminlogin.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String name = request.getParameter("name");
		String writer = request.getParameter("writer");
		String ISBN = request.getParameter("ISBN");
		String publisher = request.getParameter("publisher");
		String check_in_String = request.getParameter("check_in_String");
		String publish_String = request.getParameter("publish_String");
		String price = request.getParameter("price");
		String type = request.getParameter("type");
		String other = request.getParameter("other");
		String rent = request.getParameter("rent");
		String simpleinfo = request.getParameter("simpleinfo");
		Book book = new Book(id, name, writer, ISBN, publisher, check_in_String, publish_String, price, type,
				simpleinfo, rent, other);
		/** test */
		String[] info = book.getInfo();
		for (int i = 0; i < info.length; i++) {
			System.out.println("输出相关信息");
			System.out.println(info[i]);
		}

		// 检测传入参数的合法性
		boolean isValid = BookCheck.isValidBookMessage(book);
		if (!isValid) {
			// 如果不合法就提示并返回
			System.out.println("插入数据不合法啦");
			String err_msg = "插入数据不合法，请重试！";
			try {
				/* 一次绑定两个数据，一个是错误信息，一个是图书的id */
				request.setAttribute("err_msg", err_msg);
				request.setAttribute("id", id);
				request.getRequestDispatcher("loadbook.jsp").forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		Boolean isUpdate = false;
		String err_msg = null;
		String updatesuccess_msg = null;
		try {
			BookDao bookDao = new BookDao();
			isUpdate = bookDao.updateById(book);
			bookDao.close();
		} catch (SQLException e) {
			System.out.println("更新出错");
			e.printStackTrace();
		}
		if (isUpdate) {
			System.out.println("更新成功");
			updatesuccess_msg = "更新成功";
			request.setAttribute("updatesuccess_msg", updatesuccess_msg);
		} else {
			System.out.println("更新失败");
			err_msg = "更新失败";
			request.setAttribute("err_msg", err_msg);
			System.out.println("err_msg" + err_msg);

		}

		// 转发信息到显示页面
		try {
			request.getRequestDispatcher("admindefault.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * @function:管理员查看书籍详细信息的操作
	 * @param request
	 * @param response
	 */
	public void loadbookHandle(HttpServletRequest request, HttpServletResponse response) {
		// 判断是否以管理员身份登陆
		String adminname = (String) session.getAttribute("adminname");
		if (adminname == null || "".equals(adminname.trim())) {
			// 如果读者没登陆，那么就跳转到管理员登陆页面
			try {
				response.sendRedirect("adminlogin.teg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 跳转到加载页面
		String id = request.getParameter("id");
		// 获得书的信息
		BookDao bookDao;
		List<Book> books = null;
		try {
			bookDao = new BookDao();
			books = bookDao.QueryByBookIdWithInfo(id);
			// 绑定数据
			request.setAttribute("books", books);
			// 转发数据到load.jsp页面
			request.getRequestDispatcher("loadbook.jsp").forward(request, response);
		} catch (Exception e1) {
			System.out.println("查询图书失败");
			e1.printStackTrace();
		}

	}

	/**
	 * @author : 李煜 modified by 陈迁对
	 * @function : 对管理员登陆结果进行处理
	 * @param request
	 * @param response
	 * 
	 */
	public void adminloginHandle(HttpServletRequest request, HttpServletResponse response) {

		if (session.getAttribute("adminname") != null) {
			System.out.println("您已登陆，跳转至主页面");
			String success_msg = "您已登陆，已为您跳转至主页面";
			try {
				request.setAttribute("success_msg", success_msg);
				request.getRequestDispatcher("admindefault.jsp").forward(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String nameOrId = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String number = (String) session.getAttribute("code");
		String code = request.getParameter("vcode");

		// 如果密码和账户都为空，可能是还没有输入任何值
		if (nameOrId == null && pwd == null) {
			try {
				// 直接跳转吧
				response.sendRedirect("adminlogin.jsp");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 如果密码和账户有一个为空，那么就错误提示
		if (nameOrId == null || pwd == null) {
			request.setAttribute("err_msg", "管理员名或密码错误");
			try {
				request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
				return;
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				response.sendRedirect("adminlogin.teg");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		// 查询管理员表的姓名与密码
		Admin admin = new Admin(nameOrId, nameOrId, pwd);
		try {
			AdminDao adminDao = new AdminDao();

			// 先判断是否为读者
			boolean isAdmin = adminDao.isAdmin(admin);
			// 是读者
			if (isAdmin) {
				// if(number.equals(code.toUpperCase())){
				if (true) { // 测试阶段跳过验证码
					boolean isId = adminDao.isRightId(admin);
					if (isId) {
						adminDao = new AdminDao();
						nameOrId = adminDao.getNameById(nameOrId);
						session.setAttribute("adminname", nameOrId);
					} else {
						session.setAttribute("adminname", nameOrId);
						
					}
					// 重定向到首页
					request.setAttribute("success_msg", "欢迎回来！");
					request.getRequestDispatcher("admindefault.jsp").forward(request, response);
					return;
				} else {
					// 重定向到首页
					request.setAttribute("err_msg", "验证码错误");
					request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("err_msg", "用户名或密码错误");
				request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author : 李煜 modified by 陈迁对 function : 对用户登录结果进行处理
	 * @param request
	 * @param response
	 * @return
	 */
	public void readerloginHandle(HttpServletRequest request, HttpServletResponse response) {

		if (session.getAttribute("readername") != null) {
			// 如果该读者已经登陆，则跳转
			System.out.println("您已登陆，将跳转至您的主页面");
			try {
				request.setAttribute("success_msg", "您已登陆，将跳转至您的主页面");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 判断动作是否为登录
		String nameOrId = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String number = (String) session.getAttribute("code");
		String code = request.getParameter("vcode");

		// 如果密码和账户有都为空，可能是第一次进来
		if (nameOrId == null && pwd == null) {
			// 直接跳转吧
			try {
				response.sendRedirect("readerlogin.jsp");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 如果密码和账户有一个为空，那么就错误提示
		if (nameOrId == null || pwd == null) {
			request.setAttribute("err_msg", "用户名或密码错误");
			try {
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// 查询读者表的姓名与密码
		Reader reader = new Reader(nameOrId, nameOrId, pwd);
		try {
			ReaderDao readerDao = new ReaderDao();
			// 判断是否为读者
			boolean isReader = readerDao.isReader(reader);
			// 是读者并且验证码正确则登录成功
			if (isReader) {
				// if(number.equals(code.toUpperCase())){
				if (true) { // 测试阶段跳过验证码
					boolean isId = readerDao.isRightId(reader);
					readerDao = new ReaderDao();
					if (isId) {
						String readername = readerDao.getNameById(nameOrId);
						// 保存用户id和名字到session变量
						session.setAttribute("readername", readername);
						session.setAttribute("readerid", nameOrId);
					} else {
						String readerid = readerDao.getIdByName(nameOrId);
						// 保存用户id和名字到session变量
						session.setAttribute("readername", nameOrId);
						session.setAttribute("readerid", readerid);
					}
					// 重定向到首页
					request.setAttribute("success_msg", "登陆成功，欢迎回来");
					request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
					return;
				} else {
					request.setAttribute("err_msg", "验证码错误");
					request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("err_msg", "用户名或密码错误");
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author : 李煜 modified by 陈迁对 function : 对退出进行处理
	 * @param request
	 * @param response
	 * @return
	 * @deprecated
	 */
	public void exitHanlde(HttpServletResponse response) {
		// 这里用于销毁session,注意先跳转到登陆界面再销毁，否则会生成新的session。
		try {
			response.sendRedirect("default.jsp");
			// session 失效
			session.invalidate();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author :陈迁对 function : 对管理员退出进行处理
	 * @param request
	 * @param response
	 * @return
	 */
	public void adminexitHanlde(HttpServletResponse response) {
		// 这里用于销毁session,注意先跳转到登陆界面再销毁，否则会生成新的session。
		try {
			response.sendRedirect("default.jsp");
			// 管理员session 失效
			session.removeAttribute("adminid");
			session.removeAttribute("adminname");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author :陈迁对 function : 对读者退出进行处理
	 * @param request
	 * @param response
	 * @return
	 */
	public void readerexitHanlde(HttpServletResponse response) {
		// 这里用于销毁session,注意先跳转到登陆界面再销毁，否则会生成新的session。
		try {
			response.sendRedirect("default.jsp");
			// 管理员session 失效
			session.removeAttribute("readerid");
			session.removeAttribute("readername");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * function : 对管理员发出的请求进行处理，如果是查阅书籍就返回结果。
	 * 
	 * @author 陈迁对
	 * @param request
	 * @param response
	 * @return
	 */
	public void adminsearchHandle(HttpServletRequest request, HttpServletResponse response) {

		// 判断是否以管理员身份登陆
		String adminname = (String) session.getAttribute("adminname");
		if (adminname == null || "".equals(adminname.trim())) {
			// 如果读者没登陆，那么就跳转到管理员登陆页面
			try {
				response.sendRedirect("adminlogin.teg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		String value = request.getParameter("value");
		String searchtype = request.getParameter("searchtype");
		// 从request获取数据
		String currentpage = request.getParameter("currentpage");

		if (value == null || "".equals(value.trim())) {
			// 如果没有value传过来，有可能是分页中选择页面
			if (currentpage == null || "".equals(currentpage.trim())) {
				// 如果没有value传过来，也没有当前页面传过来，那么是查询内容为空
				try {
					request.setAttribute("err_msg", "查询内容不能为空");
					request.getRequestDispatcher("admindefault.jsp").forward(request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 如果只是没有value,那一定是分页查询中换了一页，那么重新取出value
				value = (String) session.getAttribute("value");
			}
		}
		// startPage = 0和 currentPage =1 是默认的设置。
		// startPage 从第几行数据开始，currentPage 行数
		int startPage = 0;
		int currentPage = 1;
		int resultRows = 0;
		int pageSize = 3;// pageSize是个定值，自己设置

		if (currentpage != null && !"".equals(currentpage.trim())) {
			// 如果currentpage里面不为空，那么获取其中的值
			currentPage = Integer.parseInt(currentpage);
		} else {
			// 如果currentpage 为空，就从session中取得变量值
			if (session.getAttribute("currentPage") != null) {
				currentPage = (Integer) session.getAttribute("currentPage");
			}
		}
		// startPage 从第几行开始
		startPage = (currentPage - 1) * pageSize;
		BookDao bookDao = null;
		List<Book> books = null;
		try {
			bookDao = new BookDao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (searchtype == null || "".equals(searchtype.trim())) {
			// 如果传入数据为空，那么就从session中获取
			searchtype = (String) session.getAttribute("searchtype");
		}
		try {

			books = bookDao.QueryAndPaginationWithShortIinfo(searchtype, value, startPage, pageSize);
			// 获得结果集的列数
			resultRows = bookDao.getQueryResultRows(searchtype, value);
			// 保存value到session
			session.setAttribute("value", value);
			// 保存查询类型到session
			session.setAttribute("searchtype", searchtype);
			// pages 获得显示在也页面的总页数
			int pages = resultRows / pageSize + (resultRows % pageSize == 0 ? 0 : 1);
			

			// 绑定数据
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pages", pages);
			request.setAttribute("books", books);
			// arr 是个虚拟的list,里面并没有具体的值
			request.setAttribute("arr", new List[pages]);
			request.getRequestDispatcher("admindefault.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			System.out.println("查询失败");
			e.printStackTrace();
		}
	}

	/**
	 * function : 对用户（读者）发出的请求进行处理，如果是查阅书籍就返回结果。
	 * 
	 * @author 陈迁对
	 * @param request
	 * @param response
	 * @return
	 */
	public void readersearchHandle(HttpServletRequest request, HttpServletResponse response) {
		// 1.从session里获得需要读者的readerid,如果没有则跳转回到首页
		String reader_id = (String) session.getAttribute("readerid");
		if (reader_id == null) {
			System.out.println("您的登陆身份过期或者您没有登陆，请重新登陆");
			String err_msg = "您的登陆身份过期或者您没有登陆，请重新登陆";
			request.setAttribute("err_msg", err_msg);
			try {
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		String value = request.getParameter("value");
		String searchtype = request.getParameter("searchtype");
		// 从request获取数据
		String currentpage = request.getParameter("currentpage");

		if (value == null || "".equals(value.trim())) {
			// 如果没有value传过来，有可能是分页中选择页面
			if (currentpage == null || "".equals(currentpage.trim())) {
				// 如果没有value传过来，也没有当前页面传过来，那么是查询内容为空
				try {
					request.setAttribute("err_msg", "查询内容不能为空");
					request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 如果只是没有value,那一定是分页查询中换了一页，那么重新取出value
				value = (String) session.getAttribute("value");
			}
		}
		// startPage = 0和 currentPage =1 是默认的设置。
		// startPage 从第几行数据开始，currentPage 行数
		int startPage = 0;
		int currentPage = 1;
		int resultRows = 0;
		int pageSize = 3;// pageSize是个定值，自己设置

		if (currentpage != null && !"".equals(currentpage.trim())) {
			// 如果currentpage里面不为空，那么获取其中的值
			currentPage = Integer.parseInt(currentpage);
		}
		// startPage 从第几行开始
		startPage = (currentPage - 1) * pageSize;
		BookDao bookDao = null;
		List<Book> books = null;
		try {
			bookDao = new BookDao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (searchtype == null || "".equals(searchtype.trim())) {
			// 如果传入数据为空，那么就从session中获取
			searchtype = (String) session.getAttribute("readersearchtype");
		}
		try {

			books = bookDao.QueryAndPagination(searchtype, value, startPage, pageSize);
			// 获得结果集的列数
			resultRows = bookDao.getQueryResultRows(searchtype, value);
			// 保存value到session
			session.setAttribute("value", value);
			// 保存查询类型到session
			session.setAttribute("readersearchtype", searchtype);
			// pages 获得显示在也页面的总页数
			int pages = resultRows / pageSize + (resultRows % pageSize == 0 ? 0 : 1);

			if (books.size() == 0) {
				request.setAttribute("emptyresult", "查询结果为空");
			} else {
				// 结果不为空，绑定数据
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("pages", pages);
				request.setAttribute("books", books);
				// arr 是个虚拟的list,里面并没有具体的值
				request.setAttribute("arr", new List[pages]);
			}
			request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			System.out.println("查询失败");
			e.printStackTrace();
		}
	}

}