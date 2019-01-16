package entities;

public class Borrow_history {
	// borrow_id 自动生成，无需插入了
    private String book_id ;
    private String reader_id ;
    private String borrow_date ;
    private String is_return ;
    private String borrow_id;
    private String rent;
    private String[] info;
	private static int columnsNumber = 6;
	private static String[] columnsEN ={"borrow_id","book_id","reader_id","borrow_date" ,"rent","is_return"};
    private static String[] columnsCN ={"借阅序号","书名","读者序号","借阅时间","租金","是否归还"};
    private   String[] columns_EN ={"borrow_id","book_id","reader_id","borrow_date" ,"rent","is_return"};
    private   String[] columns_CN ={"借阅序号","书名","读者序号","借阅时间","租金","是否归还"};
    
     /**
      * 构造器，borrow_id 设置为空字符串就行
      * @param book_id
      * @param reader_id
      * @param borrow_date
      * @param is_return
      */
     public Borrow_history( String book_id, String reader_id,
			String borrow_date, String is_return) {
		 
		this.book_id = book_id;
		this.reader_id = reader_id;
		this.borrow_date = borrow_date;
		this.is_return = is_return;
		//借阅id为空字符串
		this.borrow_id ="";
		//租金开始没有计算，为0
		this.rent="0";
	}
     /**
      * 构造器 
      * @param book_id
      * @param reader_id
      * @param borrow_date
      * @param is_return
      */
     public Borrow_history( String borrow_id,String book_id, String reader_id,
			String borrow_date, String rent,String is_return) {
		super(); 
		this.book_id = book_id;
		this.reader_id = reader_id;
		this.borrow_date = borrow_date;
		this.is_return = is_return;
		this.borrow_id = borrow_id;
		//租金开始没有计算，为0
		if(rent==null||rent.length()==0){
		this.rent="0";
		} else {
			this.rent = rent;
		}
	}
	 
   //重写toString方法
	public String toString() {
		return "Borrow_history [book_id="
				+ book_id + ", reader_id=" + reader_id + ", borrow_date="
				+ borrow_date + ", is_return=" + is_return + "]";
	}
	//重写hashCode方法
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book_id == null) ? 0 : book_id.hashCode());
		result = prime * result
				+ ((borrow_date == null) ? 0 : borrow_date.hashCode());
		result = prime * result + is_return.hashCode();
		result = prime * result
				+ ((reader_id == null) ? 0 : reader_id.hashCode());
		return result;
	}
	//重写equals方法
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrow_history other = (Borrow_history) obj;
		if (book_id == null) {
			if (other.book_id != null)
				return false;
		} else if (!book_id.equals(other.book_id))
			return false;
		if (borrow_date == null) {
			if (other.borrow_date != null)
				return false;
		} else if (!borrow_date.equals(other.borrow_date))
			return false;
		if (is_return != other.is_return)
			return false;
		if (reader_id == null) {
			if (other.reader_id != null)
				return false;
		} else if (!reader_id.equals(other.reader_id))
			return false;
		return true;
	}
	//getter()和setter()方法
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getReader_id() {
		return reader_id;
	}
	public void setReader_id(String reader_id) {
		this.reader_id = reader_id;
	}
	public String getborrow_date() {
		return borrow_date;
	}
	public void setborrow_date(String borrow_date) {
		this.borrow_date = borrow_date;
	}
	public String getIs_return() {
		return is_return;
	}

	public void setIs_return(String is_return) {
		this.is_return = is_return;
	}

	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	public static int getColumnsNumber() {
		return columnsNumber;
	}

	public void setColumnsNumber(int columnsNumber) {
		Borrow_history.columnsNumber = columnsNumber;
	}
	public String getBorrow_id() {
		return borrow_id;
	}
	public void setBorrow_id(String borrow_id) {
		this.borrow_id = borrow_id;
	}
	public static String[] getColumnsEN() {
		return columnsEN;
	}
	public static void setColumnsEN(String[] columnsEN) {
		Borrow_history.columnsEN = columnsEN;
	}
	public static String[] getColumnsCN() {
		return columnsCN;
	}
	public static void setColumnsCN(String[] columnsCN) {
		Borrow_history.columnsCN = columnsCN;
	}
	
	public String[] getColumns_EN() {
		return columns_EN;
	}
	public void setColumns_EN(String[] columns_EN) {
		this.columns_EN = columns_EN;
	}
	public String[] getColumns_CN() {
		return columns_CN;
	}
	public void setColumns_CN(String[] columns_CN) {
		this.columns_CN = columns_CN;
	}
	/**
	 * function:获得所有借书信息
	 * @author 陈迁对
	 * @return Borrow_historyInfo
	 */
    public String[] getBorrow_hitoryInfo() {
    	String []Borrow_historyInfo = new String[6];
    	Borrow_historyInfo[0] = getBorrow_id();
    	Borrow_historyInfo[1] = getBook_id();
    	Borrow_historyInfo[2] = getReader_id();
    	Borrow_historyInfo[3] = getborrow_date();
    	Borrow_historyInfo[4] = getRent();
    	Borrow_historyInfo[5] = getIs_return(); 
    	return Borrow_historyInfo;
    }
	/**
	 * function:获得所有借书信息
	 * @author 陈迁对
	 * @return Borrow_historyInfo
	 */
     
    public static void main(String[] args) {
		Borrow_history bh = new Borrow_history("1", "2", "3", "4","rent","is_return");
		String [] Borrow_historyInfo =bh.getBorrow_hitoryInfo();
		for(int i = 0 ;i<Borrow_historyInfo.length ; i++) {
			System.out.println(Borrow_historyInfo[i]);
		}
	}
    /**
	 * function:获得所有借书信息
	 * @author 陈迁对
	 * @return info
	 * 为了在jsp jstl上显示，名字换了
	 */
	public String[] getInfo() {
		String []info = new String[6];
    	info[0] = getBorrow_id();
    	info[1] = getBook_id();
    	info[2] = getReader_id();
    	info[3] = getborrow_date();
    	info[4] = getRent();
    	info[5] = getIs_return();  
		return info;
	}
	public void setInfo(String[] info) {
		this.info = info;
	}
}
