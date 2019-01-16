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
 * function: ͼʾ����ϵͳ�ĺ�̨����
 * 
 * @author ���� �����������������Ρ���÷ combined and modified by ��Ǩ��
 *
 */
public class ActionServlet extends HttpServlet {

	/**
	 * �Զ����ɵ����к�
	 */
	private static final long serialVersionUID = -8446871030854461953L;
	/**
	 * �򣨳�Ա��������uri:��Դ·����action:���Ա�ʶ��������ͣ�session:�Ự
	 */
	// ��ȡ������Դ·��
	private String uri;
	// ��ȡ������Դ·���г�Ӧ��������Ĳ���
	private String action;
	private HttpSession session;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �������ĵ���������
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		uri = request.getRequestURI();
		action = uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf("."));
		session = request.getSession();
		// System.out.println(session.getId());
		if ("readerlogin".equals(action)) {
			// ��readerlogin.teg���д���
			readerloginHandle(request, response);
		} else if ("adminlogin".equals(action)) {
			// ��adminlogin.teg���д���
			adminloginHandle(request, response);
		} else if ("logout".equals(action)) {
			// ���˳����д���
			exitHanlde(response);
		} else if ("readerlogout".equals(action)) {
			// �Զ����˳����д���
			readerexitHanlde(response);
		}else if ("adminlogout".equals(action)) {
			// �Թ���Ա�˳����д���
			adminexitHanlde(response);
		}else if ("adminsearch".equals(action)) {
			// ������û�������������ô������鼮��������������
			adminsearchHandle(request, response);
		} else if ("readersearch".equals(action)) {
			// ������û�������������ô������鼮��������������
			readersearchHandle(request, response);
		} else if (("loadbook").equals(action)) {
			// �ѽ�Ҫ��������ݼ��ص�һ���µ�ҳ����
			loadbookHandle(request, response);
		} else if ("modifybook".equals(action)) {
			// �޸�����Ϣ
			modifybookHandle(request, response);
		} else if ("deletebook".equals(action)) {
			// ɾ�������
			deletebookHandle(request, response);
		} else if ("bookinfo".equals(action)) {
			// �����������
			bookinfoHandle(request, response);
		} else if ("borrowbook".equals(action)) {
			// ��������Ϣ���ص�����������ʵ����session����
			borrowbookHandle(request, response);
		} else if ("borrowcart".equals(action)) {
			// ��ʾ���ĳ�����Ϣ
			showborrowcart(request, response);
		} else if ("borrowconfirm".equals(action)) {
			// �Խ���ͼ�鶯�����д���
			borrowconfirmHandle(request, response);
		} else if ("showborrowbook".equals(action)) {
			// ��ͼ����ĵ���Ϣ��ʾ
			showborrowbook(request, response);
		} else if ("returnbook".equals(action)) {
			// ��ͼ��黹��ȷ�ϴ���
			returnbookHandle(request, response);
		} else if ("showborrowinfo".equals(action)) {
			// �����û���Ҫ����ʾ����ͼ�������
			showborrowinfo(request, response);
		} else if ("sign".equals(action)) {
			// �û�ǩ��
			sign(request, response);
		} else if ("showpoint".equals(action)) {
			showpoint(request, response);
		} else if ("showlastsign".equals(action)) {
			showlastsign(request, response);
		}

	}

	/**
	 * @function: ��ʾ�ϴε�¼��ʱ��
	 * @param request
	 * @param response
	 */
	public void showlastsign(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		PointDao pointdao = null;
		try {
			// ��������
			out = response.getWriter();
			// ��ö��ߵ�id
			String readerid = (String) session.getAttribute("readerid");
			System.out.println("readerid=" + readerid);
			if (readerid == null) {
				// ���session��û�ж��ߵ�id,��ô����һ����û�е�½������ת����½����
				response.sendRedirect("readerlogin.teg");
				return;
			} else {
				pointdao = new PointDao();
				// ��ѯ�����ϴεĵ�½ʱ��
				String lastsign = pointdao.QueryValueByKey("lastsign", "reader_id", readerid);
				out.print(lastsign);
			}
			pointdao.close();
		} catch (Exception e) {

		}
		out.close();
	}

	/**
	 * @function:��̬����ʾ���ߵĻ���
	 * @param request
	 * @param response
	 */
	public void showpoint(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		PointDao pointdao = null;
		try {
			// ��������
			out = response.getWriter();
			// ��ö��ߵ�id
			String readerid = (String) session.getAttribute("readerid");
			System.out.println("readerid=" + readerid);
			if (readerid == null) {
				// ���session��û�ж��ߵ�id,��ô����һ����û�е�½������ת����½����
				response.sendRedirect("readerlogin.teg");
				return;
			} else {
				pointdao = new PointDao();
				// ��ѯ���߶�Ӧ�Ļ�������
				String sumpoint = pointdao.QueryValueByKey("sumpoint", "reader_id", readerid);
				out.print(sumpoint);
			}
			pointdao.close();
		} catch (Exception e) {

		}
		out.close();
	}

	/**
	 * @function:����ǩ������
	 * @param request
	 * @param response
	 */
	public void sign(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("come to sign");
		PrintWriter out = null;
		PointDao pointDao = null;
		try {
			// ���out
			out = response.getWriter();
			// ���session ����洢�� readerid
			String readerid = (String) session.getAttribute("readerid");
			if (readerid == null) {
				// ���session��û�ж��ߵ�id,��ô����һ����û�е�½������ת����½����
				response.sendRedirect("readerlogin.teg");
				return;
			} else {
				pointDao = new PointDao();
				// ��øö����ϴ�ǩ����ʱ��
				String lastsign = pointDao.QueryValueByKey("lastsign", "reader_id", readerid);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// ��õ�ǰʱ��
				java.util.Date now = new java.util.Date();
				// ת����ǰʱ��Ϊ�������ڣ���ת��Ϊ�ַ�����ʽ
				String nowStr = sdf.format(now);
				System.out.println("now=" + nowStr);
				System.out.println("last=" + lastsign);
				// ���lastsign��Ϊ��,���Һͽ���������ͬ����ô����һ���ظ�ǩ����
				if (lastsign != null && lastsign.equals(nowStr)) {
					// ������ݿ���洢��ʱ�������һ�£�����ʧ��
					out.print("fail");

				} else {
					// ������ݿ���洢��ʱ������ڲ�һ�£���ô�͸������ݿ�
					// ������java.util������ת��java.sql������
					java.sql.Date sqlnow = new java.sql.Date(now.getTime());
					pointDao.readerSignOperate(readerid, sqlnow);
					out.print("success");
				}
				// �ر����ݿ�����
				pointDao.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.close();
	}

	public void showborrowinfo(HttpServletRequest request, HttpServletResponse response) {
		// ������

	}

	/**
	 * @function:��ʾ�Ѿ�������������Ϣ
	 * @param request
	 * @param response
	 */
	public void showborrowbook(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 1.��session�л�øö��ߵ�id
			String reader_id = (String) session.getAttribute("readerid");
			if (reader_id == null) {
				String err_msg = "���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½";
				request.setAttribute("err_msg", err_msg);
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
				return;
			}

			// 2.��õ�ǰҳ
			String currentpage = request.getParameter("currentPage");
			int currentPage = 1;// Ĭ�ϵ�һ�β�ѯ��ʱ��currentPageΪ1����ǰΪ��һҳ
			if (currentpage == null || "".equals(currentpage.trim())) {
				// ʲô������
			} else {
				// �����ֵ����
				currentPage = Integer.parseInt(currentpage);
			}
			String startpage = null; // startPage��string��ʾ
			int startPage = 0;// Ĭ�ϴ�0��ʼ�����ǵ�һ����¼��ʼ

			int pageSize = 3; // Ĭ��ÿ�λ�ȡ 3����¼������Ƕ�ֵ

			startPage = (currentPage - 1) * pageSize;

			// 3.ͨ������id��ѯ�ö��ߵ����н�����Ϣ�����洢��Ϣ
			Borrow_historyDao bhd = new Borrow_historyDao();
			// ��÷�ҳ��ѯ�Ľ��
			List<Borrow_history> borrow_historys = bhd.queryInfoByReader_idPagination(reader_id, startPage, pageSize);
			// ���ݶ��ߵ�id�Ի���ܽ������
			int resultRow = bhd.getResultRowByReader_id(reader_id);

			// �ر�����
			bhd.close();
			// 4������ܽ����ҳ��
			int pages = resultRow / pageSize + (resultRow % pageSize == 0 ? 0 : 1);
			// 5.ת����Ϣ��ҳ��
			// �жϽ���Ƿ�Ϊ��ֵ�����Ϊ�շ�����ʾ
			if (borrow_historys == null || borrow_historys.size() == 0) {
				request.setAttribute("emptyresult", "��û�н���κ���");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			} else {
				// ת������Ľ��ļ�¼���ܽ����ҳ֮�����ҳ������ǰ���������ҳ������list������Ϊҳ����
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
	 * @function:�Ի���������д���
	 * @param request
	 * @param response
	 */
	public void returnbookHandle(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1.���session�е��û�id,���Ϊ�գ�����ת����һҳ
			String readerid = (String) session.getAttribute("readerid");
			if (readerid == null) {
				response.sendRedirect("showborrowbook.teg");
				return;
			}
			// 2.���request��borrow_id,���Ϊ���򷵻���һҳ������showborrowbook.teg
			String borrow_id = request.getParameter("borrow_id");
			if (borrow_id == null) {
				response.sendRedirect("showborrowbook.teg");
				return;
			}
			// 3�������ݿ��л�ȡʱ�䣬���ڴ˿�ʱ��Աȣ������ֵ�����������Ӷ��õ�����Ӧ�ý��ɵĽ�����
			Borrow_historyDao bhd = new Borrow_historyDao();
			List<Borrow_history> borrow_historys = bhd.queryBorrowInfoByBorrow_id(borrow_id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// ��ȡ���ĵ�ʱ���ַ���
			String borrow_date = borrow_historys.get(0).getborrow_date();
			// ������ʱ���ַ���ת��Ϊʱ�������
			java.util.Date borrowdate = sdf.parse(borrow_date);
			// ��ȡ��ǰʱ���Ӧ��ʱ�������
			java.util.Date nowdate = new java.util.Date();
			// ����ʱ�������� ,��������
			int days = (int) ((nowdate.getTime() / 86400000 - borrowdate.getTime() / 86400000));
			// ��÷���
			BookDao bookDao = new BookDao();
			String rent = bookDao.QueryRentByBorrowId(borrow_id);
			// �ر�����
			bookDao.close();
			// ���㻨��
			float fee = (float) (days * Double.parseDouble(rent));
			// �����Ӧ�Ļ��� ��0.1Ԫ��Ӧ4���� ,����1Ԫ����40���֣�
			int feepoint = (int) (fee * 40);
			System.out.println("feepoint=" + feepoint);
			System.out.println("fee=" + fee);
			// �鿴�ö��߻����Ƿ��㹻
			PointDao pointDao = new PointDao();
			String sumpointstr = pointDao.QueryValueByKey("sumpoint", "reader_id", readerid);
			// ��ö�Ӧ����
			int sumpoint = Integer.parseInt(sumpointstr.trim());
			if (sumpoint < feepoint) {
				// ���ʣ��Ļ��ֲ���
				request.setAttribute("err_msg", "���ֲ��㣬�޷����飬���ֵ���ǩ��");
				request.getRequestDispatcher("showborrowbook.teg").forward(request, response);
			} else {
				// ������ֻ��������ȿ۳�����
				boolean isReduce = pointDao.reducepoint(readerid, feepoint);
				// 4.�����ݿ����޸�borrow_history �еĽ����־λΪ1,ͬʱ��¼����
				boolean isReturn = bhd.returnByBorrow_id(borrow_id, fee + "");
				// 5.��ʾ��������ɹ�
				String err_msg = null;
				String success_msg = null;
				if (isReturn == true && isReturn == true) {
					System.out.println("����ɹ�");
					success_msg = "����ɹ�������" + feepoint + "����";
				} else {
					System.out.println("����ʧ��");
					err_msg = "����ʧ��";
				}
				// 6.�ض���showborrowbook.teg
				// ���ݲ�ͬ�Ľ�����Ͳ�ͬ����Ϣ
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
	 * @function:�Խ����������ȷ��
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void borrowconfirmHandle(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 1.��session������Ҫ���ߵ�readerid,���û������ת�ص���ҳ
			String reader_id = (String) session.getAttribute("readerid");
			if (reader_id == null) {
				System.out.println("���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½");
				String err_msg = "���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½";
				request.setAttribute("err_msg", err_msg);
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
				return;
			}
			// 2.��request���book��id,���û���򷵻ص����鳵borrowcart.jsp����
			String book_id = request.getParameter("id");
			if (book_id == null) {
				response.sendRedirect("borrowcart.jsp");
				return;
			}
			// 3.�������readerid��book��id���������ݿ��ｫͼ����ı������Ϊ�����ͬʱҲҪ���û��Ľ����Ϊ���б���
			Borrow_historyDao bhd = new Borrow_historyDao();
			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// ��õ�ǰʱ����ַ���
			String borrow_date = sdf.format(date);
			Borrow_history bh = new Borrow_history(book_id, reader_id, borrow_date, "0");
			System.out.println("��ǰʱ����" + borrow_date);
			boolean isAdd = bhd.add(bh);
			if (isAdd) {
				System.out.println("��ӽ��ļ�¼�ɹ�");
			} else {
				System.out.println("��ӽ��ļ�¼ʧ��");
			}
			// 4.��session�л��bookIds�����������book��id������ɾ��
			@SuppressWarnings("unchecked")
			ArrayList<String> bookIds = (ArrayList<String>) session.getAttribute("bookIds");
			String success_msg = null;
			String err_msg = null;
			// ����һ���ж�Ϊ��
			if (bookIds.contains(book_id)) {
				// �Ƴ�book_id
				boolean isRemove = bookIds.remove(book_id);
				if (isRemove) {
					System.out.println("��session���Ƴ�bookid�ɹ�");
					success_msg = "�����ɹ�";
					// ���Ƴ�list�е�book
					BookDao bookDao = new BookDao();
					if ((session.getAttribute("books")) != null) {
						((List<Book>) session.getAttribute("books"))
								.remove(bookDao.QueryByBookIdWithInfo(book_id).get(0));
					}
				} else {
					System.out.println("�Ƴ�bookidʧ�ܣ�����");
					err_msg = "�Ƴ�ʧ�ܣ�������";
				}
			} else {
				System.out.println("��ͼ��û�б���Ԥ��ѡ��");
				err_msg = "��ͼ��û�б���Ԥ��ѡ��";
			}

			// 5.ת����borrowcart.jspҳ�棬��ʵ����ˢ��ҳ��
			if (err_msg != null) {
				request.setAttribute("err_msg", err_msg);
			} else if (success_msg != null) {
				request.setAttribute("success_msg", success_msg);
			}

			if (session.getAttribute("books") != null && ((List<Book>) session.getAttribute("books")).size() != 0) {
				System.out.println("����" + ((List<Book>) session.getAttribute("books")).size());
				System.out.println("��ת��borrwcart");
				// ���session ����� books ������������е�Ԫ�ز�Ϊ�գ���ȡ��
				request.setAttribute("books", session.getAttribute("books"));
				request.getRequestDispatcher("borrowcart.teg").forward(request, response);
			} else {
				// ���û��ֵ�ˣ���ô����ת����ҳ
				System.out.println("��ת��������ҳ");
				request.setAttribute("success_msg", "���ݴ���ɹ������ĳ�Ϊ�գ��л���������ҳ");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;

	}

	/**
	 * @function:��ʾ���ĳ�����ϸ��Ϣ
	 * @param request
	 * @param response
	 */
	public void showborrowcart(HttpServletRequest request, HttpServletResponse response) {
		// 1.�ж�session�еı���bookIds�Ƿ���ֵ
		@SuppressWarnings("unchecked")
		ArrayList<String> bookIds = (ArrayList<String>) session.getAttribute("bookIds");
		try {
			// 2.���û��ֵ������ת��������ҳ
			if (bookIds == null || bookIds.size() == 0) {
				System.out.println("û�д����ĵ���");
				String err_msg = "û�д����ĵ���";
				request.setAttribute("emptyresult", "���ĳ�Ϊ�գ�����ӽ�������");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
				return;
			}
			// 3.�����ֵ���ͻ�ö�Ӧ��book���󣬴�������
			else {
				BookDao bookDao = new BookDao();
				ArrayList<Book> books = new ArrayList<Book>();
				try {
					for (int i = 0; i < bookIds.size(); i++) {
						// ȡ��ÿһ��id��Ӧ��book����
						List<Book> book = bookDao.QueryByBookIdWithInfo(bookIds.get(i));
						// ��book��ȡ����һ������Ϊһ����һ����Ȼ��浽books���档
						books.add(book.get(0));
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
				// ����books �� session ��
				session.setAttribute("books", books);
				// 4.�Ȱ󶨣����ض�����borrowcart����
				request.setAttribute("books", books);
				request.getRequestDispatcher("borrowcart.jsp").forward(request, response);

				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function:���Ĳ����Ĵ���
	 * @param request
	 * @param response
	 */
	public void borrowbookHandle(HttpServletRequest request, HttpServletResponse response) {
		// 1.��session������Ҫ���ߵ�readerid,���û������ת�ص���ҳ
		String reader_id = (String) session.getAttribute("readerid");
		if (reader_id == null) {
			System.out.println("���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½");
			String err_msg = "���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½";
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
		// 1.��ȡ��book��idֵ,���жϸ����Ƿ񱻽��
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
			System.out.println("�����ѱ������������ѡ��");
			// �ض�����ҳ
			try {
				String err_msg = "�����ѱ������������ѡ��";
				request.setAttribute("err_msg", err_msg);
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		System.out.println("bookid=" + id);
		// 2.ȡ��session����ı���bookIds
		@SuppressWarnings("unchecked")
		ArrayList<String> bookIds = (ArrayList<String>) session.getAttribute("bookIds");
		// 3.�ж�bookIds�Ƿ�Ϊ��
		if (bookIds == null) {
			// 4.���Ϊ�գ��ͳ�ʼ��bookIds,�������δ��������
			bookIds = new ArrayList<String>();
			bookIds.add(id);
			// ���浽session������
			session.setAttribute("bookIds", bookIds);
			// ��ʾ�����ɹ�
		} else {
			// 5.�����Ϊ�գ��鿴�����Ƿ���ڸ���ֵ����������������session���档zz
			for (int i = 0; i < bookIds.size(); i++) {
				System.out.println("bookIds[" + i + "]=" + bookIds.get(i));
			}
			String err_msg = null;
			String success_msg = null;
			if (!bookIds.contains(id)) {
				// ����������������
				bookIds.add(id);
				// ���浽session������
				session.setAttribute("bookIds", bookIds);
				// ��ʾ�����ɹ�
				success_msg = "�ɹ���ӵ����ĳ�";
				request.setAttribute("success_msg", success_msg);
			} else {
				// ʲô����������ʾ�Ѵ���
				System.out.println("�Ѵ��ڸ��飬�����ظ����");
				err_msg = "���ĳ��Ѵ��ڸ��飬�����ظ����";
				request.setAttribute("err_msg", err_msg);
			}
		}
		// ��ת���û�������
		try {
			request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function:��ʾ�û��������鼮��ϸ��Ϣ
	 * @param request
	 * @param response
	 */
	public void bookinfoHandle(HttpServletRequest request, HttpServletResponse response) {
		// ��session������Ҫ���ߵ�readerid,���û������ת�ص���ҳ
		String reader_id = (String) session.getAttribute("readerid");
		if (reader_id == null) {
			System.out.println("���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½");
			String err_msg = "���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½";
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
		// ��ת������ҳ��
		String id = request.getParameter("id");
		// ��������Ϣ
		BookDao bookDao;

		List<Book> books = null;
		try {
			bookDao = new BookDao();
			System.out.println(id);
			books = bookDao.QueryByBookIdWithInfo(id);
			System.out.println(books.get(0).getName());
			// ������
			request.setAttribute("books", books);
			// ת�����ݵ�load.jspҳ��
			request.getRequestDispatcher("bookinfo.jsp").forward(request, response);
		} catch (Exception e1) {
			System.out.println("��ѯͼ��ʧ��");
			e1.printStackTrace();
		}

	}

	/**
	 * @function: ���ڹ���Աɾ��ͼ��Ĳ���
	 * @param request
	 * @param response
	 */
	public void deletebookHandle(HttpServletRequest request, HttpServletResponse response) {
		// �ж��Ƿ��Թ���Ա��ݵ�½
		String adminname = (String) session.getAttribute("adminname");
		if (adminname == null || "".equals(adminname.trim())) {
			// �������û��½����ô����ת������Ա��½ҳ��
			try {
				response.sendRedirect("adminlogin.teg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// ��ת���޸�ҳ��
		String id = request.getParameter("id");
		Boolean isDelete = false;
		try {
			BookDao bookDao = new BookDao();
			isDelete = bookDao.deleteBookById(id);
		} catch (SQLException e) {
			System.out.println("ɾ��ʧ�ܣ������³��ԣ�");
		}
		String err_msg = null;
		String deletesuccess_msg = null;
		try {
			if (isDelete) {
				System.out.println("ɾ���ɹ�");
				deletesuccess_msg = "ɾ���ɹ�";
				request.setAttribute("deletesuccess_msg", deletesuccess_msg);
			} else {
				System.out.println("ɾ��ʧ��");
				err_msg = "ɾ��ʧ��";
				request.setAttribute("err_msg", err_msg);
			}
			request.getRequestDispatcher("admindefault.jsp").forward(request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function:���ڹ���Ա�޸��鼮��Ϣ�Ĳ���
	 * @param request
	 * @param response
	 */
	public void modifybookHandle(HttpServletRequest request, HttpServletResponse response) {
		// �ж��Ƿ��Թ���Ա��ݵ�½
				String adminname = (String) session.getAttribute("adminname");
				if (adminname == null || "".equals(adminname.trim())) {
					// �������û��½����ô����ת������Ա��½ҳ��
					try {
						response.sendRedirect("adminlogin.teg");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
		// ��ת���޸�ҳ��
		String id = request.getParameter("id");
		if (id == null || "".equals(id.trim())) {
			// ���idΪ�գ�˵��ֵ��û�д�����
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
			System.out.println("��������Ϣ");
			System.out.println(info[i]);
		}

		// ��⴫������ĺϷ���
		boolean isValid = BookCheck.isValidBookMessage(book);
		if (!isValid) {
			// ������Ϸ�����ʾ������
			System.out.println("�������ݲ��Ϸ���");
			String err_msg = "�������ݲ��Ϸ��������ԣ�";
			try {
				/* һ�ΰ��������ݣ�һ���Ǵ�����Ϣ��һ����ͼ���id */
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
			System.out.println("���³���");
			e.printStackTrace();
		}
		if (isUpdate) {
			System.out.println("���³ɹ�");
			updatesuccess_msg = "���³ɹ�";
			request.setAttribute("updatesuccess_msg", updatesuccess_msg);
		} else {
			System.out.println("����ʧ��");
			err_msg = "����ʧ��";
			request.setAttribute("err_msg", err_msg);
			System.out.println("err_msg" + err_msg);

		}

		// ת����Ϣ����ʾҳ��
		try {
			request.getRequestDispatcher("admindefault.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * @function:����Ա�鿴�鼮��ϸ��Ϣ�Ĳ���
	 * @param request
	 * @param response
	 */
	public void loadbookHandle(HttpServletRequest request, HttpServletResponse response) {
		// �ж��Ƿ��Թ���Ա��ݵ�½
		String adminname = (String) session.getAttribute("adminname");
		if (adminname == null || "".equals(adminname.trim())) {
			// �������û��½����ô����ת������Ա��½ҳ��
			try {
				response.sendRedirect("adminlogin.teg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// ��ת������ҳ��
		String id = request.getParameter("id");
		// ��������Ϣ
		BookDao bookDao;
		List<Book> books = null;
		try {
			bookDao = new BookDao();
			books = bookDao.QueryByBookIdWithInfo(id);
			// ������
			request.setAttribute("books", books);
			// ת�����ݵ�load.jspҳ��
			request.getRequestDispatcher("loadbook.jsp").forward(request, response);
		} catch (Exception e1) {
			System.out.println("��ѯͼ��ʧ��");
			e1.printStackTrace();
		}

	}

	/**
	 * @author : ���� modified by ��Ǩ��
	 * @function : �Թ���Ա��½������д���
	 * @param request
	 * @param response
	 * 
	 */
	public void adminloginHandle(HttpServletRequest request, HttpServletResponse response) {

		if (session.getAttribute("adminname") != null) {
			System.out.println("���ѵ�½����ת����ҳ��");
			String success_msg = "���ѵ�½����Ϊ����ת����ҳ��";
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

		// ���������˻���Ϊ�գ������ǻ�û�������κ�ֵ
		if (nameOrId == null && pwd == null) {
			try {
				// ֱ����ת��
				response.sendRedirect("adminlogin.jsp");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ���������˻���һ��Ϊ�գ���ô�ʹ�����ʾ
		if (nameOrId == null || pwd == null) {
			request.setAttribute("err_msg", "����Ա�����������");
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
		// ��ѯ����Ա�������������
		Admin admin = new Admin(nameOrId, nameOrId, pwd);
		try {
			AdminDao adminDao = new AdminDao();

			// ���ж��Ƿ�Ϊ����
			boolean isAdmin = adminDao.isAdmin(admin);
			// �Ƕ���
			if (isAdmin) {
				// if(number.equals(code.toUpperCase())){
				if (true) { // ���Խ׶�������֤��
					boolean isId = adminDao.isRightId(admin);
					if (isId) {
						adminDao = new AdminDao();
						nameOrId = adminDao.getNameById(nameOrId);
						session.setAttribute("adminname", nameOrId);
					} else {
						session.setAttribute("adminname", nameOrId);
						
					}
					// �ض�����ҳ
					request.setAttribute("success_msg", "��ӭ������");
					request.getRequestDispatcher("admindefault.jsp").forward(request, response);
					return;
				} else {
					// �ض�����ҳ
					request.setAttribute("err_msg", "��֤�����");
					request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("err_msg", "�û������������");
				request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author : ���� modified by ��Ǩ�� function : ���û���¼������д���
	 * @param request
	 * @param response
	 * @return
	 */
	public void readerloginHandle(HttpServletRequest request, HttpServletResponse response) {

		if (session.getAttribute("readername") != null) {
			// ����ö����Ѿ���½������ת
			System.out.println("���ѵ�½������ת��������ҳ��");
			try {
				request.setAttribute("success_msg", "���ѵ�½������ת��������ҳ��");
				request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// �ж϶����Ƿ�Ϊ��¼
		String nameOrId = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String number = (String) session.getAttribute("code");
		String code = request.getParameter("vcode");

		// ���������˻��ж�Ϊ�գ������ǵ�һ�ν���
		if (nameOrId == null && pwd == null) {
			// ֱ����ת��
			try {
				response.sendRedirect("readerlogin.jsp");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ���������˻���һ��Ϊ�գ���ô�ʹ�����ʾ
		if (nameOrId == null || pwd == null) {
			request.setAttribute("err_msg", "�û������������");
			try {
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		// ��ѯ���߱������������
		Reader reader = new Reader(nameOrId, nameOrId, pwd);
		try {
			ReaderDao readerDao = new ReaderDao();
			// �ж��Ƿ�Ϊ����
			boolean isReader = readerDao.isReader(reader);
			// �Ƕ��߲�����֤����ȷ���¼�ɹ�
			if (isReader) {
				// if(number.equals(code.toUpperCase())){
				if (true) { // ���Խ׶�������֤��
					boolean isId = readerDao.isRightId(reader);
					readerDao = new ReaderDao();
					if (isId) {
						String readername = readerDao.getNameById(nameOrId);
						// �����û�id�����ֵ�session����
						session.setAttribute("readername", readername);
						session.setAttribute("readerid", nameOrId);
					} else {
						String readerid = readerDao.getIdByName(nameOrId);
						// �����û�id�����ֵ�session����
						session.setAttribute("readername", nameOrId);
						session.setAttribute("readerid", readerid);
					}
					// �ض�����ҳ
					request.setAttribute("success_msg", "��½�ɹ�����ӭ����");
					request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
					return;
				} else {
					request.setAttribute("err_msg", "��֤�����");
					request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
					return;
				}
			} else {
				request.setAttribute("err_msg", "�û������������");
				request.getRequestDispatcher("readerlogin.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author : ���� modified by ��Ǩ�� function : ���˳����д���
	 * @param request
	 * @param response
	 * @return
	 * @deprecated
	 */
	public void exitHanlde(HttpServletResponse response) {
		// ������������session,ע������ת����½���������٣�����������µ�session��
		try {
			response.sendRedirect("default.jsp");
			// session ʧЧ
			session.invalidate();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author :��Ǩ�� function : �Թ���Ա�˳����д���
	 * @param request
	 * @param response
	 * @return
	 */
	public void adminexitHanlde(HttpServletResponse response) {
		// ������������session,ע������ת����½���������٣�����������µ�session��
		try {
			response.sendRedirect("default.jsp");
			// ����Աsession ʧЧ
			session.removeAttribute("adminid");
			session.removeAttribute("adminname");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author :��Ǩ�� function : �Զ����˳����д���
	 * @param request
	 * @param response
	 * @return
	 */
	public void readerexitHanlde(HttpServletResponse response) {
		// ������������session,ע������ת����½���������٣�����������µ�session��
		try {
			response.sendRedirect("default.jsp");
			// ����Աsession ʧЧ
			session.removeAttribute("readerid");
			session.removeAttribute("readername");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * function : �Թ���Ա������������д�������ǲ����鼮�ͷ��ؽ����
	 * 
	 * @author ��Ǩ��
	 * @param request
	 * @param response
	 * @return
	 */
	public void adminsearchHandle(HttpServletRequest request, HttpServletResponse response) {

		// �ж��Ƿ��Թ���Ա��ݵ�½
		String adminname = (String) session.getAttribute("adminname");
		if (adminname == null || "".equals(adminname.trim())) {
			// �������û��½����ô����ת������Ա��½ҳ��
			try {
				response.sendRedirect("adminlogin.teg");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		String value = request.getParameter("value");
		String searchtype = request.getParameter("searchtype");
		// ��request��ȡ����
		String currentpage = request.getParameter("currentpage");

		if (value == null || "".equals(value.trim())) {
			// ���û��value���������п����Ƿ�ҳ��ѡ��ҳ��
			if (currentpage == null || "".equals(currentpage.trim())) {
				// ���û��value��������Ҳû�е�ǰҳ�洫��������ô�ǲ�ѯ����Ϊ��
				try {
					request.setAttribute("err_msg", "��ѯ���ݲ���Ϊ��");
					request.getRequestDispatcher("admindefault.jsp").forward(request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// ���ֻ��û��value,��һ���Ƿ�ҳ��ѯ�л���һҳ����ô����ȡ��value
				value = (String) session.getAttribute("value");
			}
		}
		// startPage = 0�� currentPage =1 ��Ĭ�ϵ����á�
		// startPage �ӵڼ������ݿ�ʼ��currentPage ����
		int startPage = 0;
		int currentPage = 1;
		int resultRows = 0;
		int pageSize = 3;// pageSize�Ǹ���ֵ���Լ�����

		if (currentpage != null && !"".equals(currentpage.trim())) {
			// ���currentpage���治Ϊ�գ���ô��ȡ���е�ֵ
			currentPage = Integer.parseInt(currentpage);
		} else {
			// ���currentpage Ϊ�գ��ʹ�session��ȡ�ñ���ֵ
			if (session.getAttribute("currentPage") != null) {
				currentPage = (Integer) session.getAttribute("currentPage");
			}
		}
		// startPage �ӵڼ��п�ʼ
		startPage = (currentPage - 1) * pageSize;
		BookDao bookDao = null;
		List<Book> books = null;
		try {
			bookDao = new BookDao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (searchtype == null || "".equals(searchtype.trim())) {
			// �����������Ϊ�գ���ô�ʹ�session�л�ȡ
			searchtype = (String) session.getAttribute("searchtype");
		}
		try {

			books = bookDao.QueryAndPaginationWithShortIinfo(searchtype, value, startPage, pageSize);
			// ��ý����������
			resultRows = bookDao.getQueryResultRows(searchtype, value);
			// ����value��session
			session.setAttribute("value", value);
			// �����ѯ���͵�session
			session.setAttribute("searchtype", searchtype);
			// pages �����ʾ��Ҳҳ�����ҳ��
			int pages = resultRows / pageSize + (resultRows % pageSize == 0 ? 0 : 1);
			

			// ������
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pages", pages);
			request.setAttribute("books", books);
			// arr �Ǹ������list,���沢û�о����ֵ
			request.setAttribute("arr", new List[pages]);
			request.getRequestDispatcher("admindefault.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			System.out.println("��ѯʧ��");
			e.printStackTrace();
		}
	}

	/**
	 * function : ���û������ߣ�������������д�������ǲ����鼮�ͷ��ؽ����
	 * 
	 * @author ��Ǩ��
	 * @param request
	 * @param response
	 * @return
	 */
	public void readersearchHandle(HttpServletRequest request, HttpServletResponse response) {
		// 1.��session������Ҫ���ߵ�readerid,���û������ת�ص���ҳ
		String reader_id = (String) session.getAttribute("readerid");
		if (reader_id == null) {
			System.out.println("���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½");
			String err_msg = "���ĵ�½��ݹ��ڻ�����û�е�½�������µ�½";
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
		// ��request��ȡ����
		String currentpage = request.getParameter("currentpage");

		if (value == null || "".equals(value.trim())) {
			// ���û��value���������п����Ƿ�ҳ��ѡ��ҳ��
			if (currentpage == null || "".equals(currentpage.trim())) {
				// ���û��value��������Ҳû�е�ǰҳ�洫��������ô�ǲ�ѯ����Ϊ��
				try {
					request.setAttribute("err_msg", "��ѯ���ݲ���Ϊ��");
					request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// ���ֻ��û��value,��һ���Ƿ�ҳ��ѯ�л���һҳ����ô����ȡ��value
				value = (String) session.getAttribute("value");
			}
		}
		// startPage = 0�� currentPage =1 ��Ĭ�ϵ����á�
		// startPage �ӵڼ������ݿ�ʼ��currentPage ����
		int startPage = 0;
		int currentPage = 1;
		int resultRows = 0;
		int pageSize = 3;// pageSize�Ǹ���ֵ���Լ�����

		if (currentpage != null && !"".equals(currentpage.trim())) {
			// ���currentpage���治Ϊ�գ���ô��ȡ���е�ֵ
			currentPage = Integer.parseInt(currentpage);
		}
		// startPage �ӵڼ��п�ʼ
		startPage = (currentPage - 1) * pageSize;
		BookDao bookDao = null;
		List<Book> books = null;
		try {
			bookDao = new BookDao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (searchtype == null || "".equals(searchtype.trim())) {
			// �����������Ϊ�գ���ô�ʹ�session�л�ȡ
			searchtype = (String) session.getAttribute("readersearchtype");
		}
		try {

			books = bookDao.QueryAndPagination(searchtype, value, startPage, pageSize);
			// ��ý����������
			resultRows = bookDao.getQueryResultRows(searchtype, value);
			// ����value��session
			session.setAttribute("value", value);
			// �����ѯ���͵�session
			session.setAttribute("readersearchtype", searchtype);
			// pages �����ʾ��Ҳҳ�����ҳ��
			int pages = resultRows / pageSize + (resultRows % pageSize == 0 ? 0 : 1);

			if (books.size() == 0) {
				request.setAttribute("emptyresult", "��ѯ���Ϊ��");
			} else {
				// �����Ϊ�գ�������
				request.setAttribute("currentPage", currentPage);
				request.setAttribute("pages", pages);
				request.setAttribute("books", books);
				// arr �Ǹ������list,���沢û�о����ֵ
				request.setAttribute("arr", new List[pages]);
			}
			request.getRequestDispatcher("readerdefault.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			System.out.println("��ѯʧ��");
			e.printStackTrace();
		}
	}

}