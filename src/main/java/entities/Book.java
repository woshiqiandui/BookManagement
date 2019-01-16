package entities;

 
import dao.BookDao;

/**
 * @author 江宜瑞 Email：1457908216@qq.com
 * @version 2017年9月27日 上午9:35:56
 */
public class Book { 
	private String id;
	private String name;
	private String writer;
	private String ISBN;
	private String publisher;
	private String check_in_String;
	private String publish_String;
	private String price;
	private String type;
	private String simpleinfo;
	private String rent;
	private String other;

	private String[]info ;
	public static int columnsNumber = 12;
	public int columnsNum = 12 ;
  
	
	/**
	 * 中文列
	 */
	private static String[] columnsCN = { "序号", "书名", "作者", "ISBN", "出版社", "入库时间", "出版时间", "价格", "分类","信息","租金","其他" };
	private String[] columns_CN = { "序号", "书名", "作者", "ISBN", "出版社", "入库时间", "出版时间", "价格", "分类","信息","租金","其他" };

	/**
	 * 英文列
	 */
	private static String[] columnsEN = { "id", "name", "writer", "ISBN", "publisher", "check_in_String",
			"publish_String", "price", "type","simpleinfo","rent","other" };
	private  String[] columns_EN = { "id", "name", "writer", "ISBN", "publisher", "check_in_String",
			"publish_String", "price", "type","simpleinfo","rent","other" };

	/** 构造函数，十二个传入参数 */
	/**
	 * @author 江宜瑞
	 * @param id，name,writer，iSBN，publisher，check_in_String，
	 *            publish_String，price，type，info,rent,other
	 */
	
	public Book(String id, String name, String writer, String iSBN, String publisher, String check_in_String,
			String publish_String, String price, String type,  String simpleinfo,String rent,String other) {
		 
		this.id = id;
		this.name = name;
		this.writer = writer;
		ISBN = iSBN;
		this.publisher = publisher;
		if (check_in_String!=null) {
			// 日期可以为空值,日期最长长度为20
			if(check_in_String.length()>=20) {
			this.check_in_String = check_in_String.substring(0,19);
			}else {
				this.check_in_String = check_in_String;
			}
		}
		if (publish_String!=null) {
			// 此处日期也可以为空值
			if(publish_String.length()>=10) {
				this.publish_String = publish_String.substring(0,10); 
			} else {
				this.publish_String = publish_String;
			}
		}
		this.price = price;
		this.type = type;
		if (other != null) {
			// 如果不为空值就传入参数
			this.other = other;
		} else{
			this.other = "";
		}
		if (simpleinfo!= null) {
			// 此处简介也可以为空值
			this.simpleinfo = simpleinfo;
		}
		if (rent!= null) {
			// 此处租金也可以为空值
			this.rent = rent;
		}
	}

	/**
	 * @author 江宜瑞 重写hashCode方法
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		result = prime * result + ((check_in_String == null) ? 0 : check_in_String.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((simpleinfo == null) ? 0 : simpleinfo.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((other == null) ? 0 : other.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((publish_String == null) ? 0 : publish_String.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((rent == null) ? 0 : rent.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}
	/**
	 * @author 江宜瑞 重写equals方法
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		if (check_in_String == null) {
			if (other.check_in_String != null)
				return false;
		} else if (!check_in_String.equals(other.check_in_String))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (simpleinfo == null) {
			if (other.simpleinfo != null)
				return false;
		} else if (!simpleinfo.equals(other.simpleinfo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (this.other == null) {
			if (other.other != null)
				return false;
		} else if (!this.other.equals(other.other))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (publish_String == null) {
			if (other.publish_String != null)
				return false;
		} else if (!publish_String.equals(other.publish_String))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (rent == null) {
			if (other.rent != null)
				return false;
		} else if (!rent.equals(other.rent))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
	return true;
	}

	/**
	 * @author 江宜瑞 重写toString方法
	 */
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", writer=" + writer + ", ISBN=" + ISBN + ", publisher="
				+ publisher + ", check_in_String=" + check_in_String + ", publish_String=" + publish_String + ", price="
				+ price + ", type=" + type + ", info=" + simpleinfo + ", rent=" + rent + ", other=" + other + "]";
	}
	/**
	 * @author 江宜瑞 getter()和setter()方法
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCheck_in_String() {
		return check_in_String;
	}

	public void setCheck_in_String(String check_in_String) {
		this.check_in_String = check_in_String;
	}

	public String getPublish_String() {  
		return publish_String;
	}

	public void setPublish_String(String publish_String) {
		this.publish_String = publish_String;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRent() {
		return rent;
	}

 
	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getSimpleinfo() {
		return simpleinfo;
	}

	public void setSimpleinfo(String simpleinfo) {
		this.simpleinfo = simpleinfo;
	} 
 
	public static String[] getColumnsCN() {
		return columnsCN;
	}

	public static void setColumnsCN(String[] columnsCN) {
		 Book.columnsCN = columnsCN;
	}

	public  static String[] getColumnsEN() {
		return Book.columnsEN;
	}

	public static void setColumnsEN(String[] columnsEN) {
		Book.columnsEN = columnsEN;
	}

	public static int getColumnsnumber() {
		return columnsNumber;
	}   
	 
	public static int getColumnsNumber() {
		return columnsNumber;
	}

	public static void setColumnsNumber(int columnsNumber) {
		Book.columnsNumber = columnsNumber;
	}

	public String[] getColumns_CN() {
		return columns_CN;
	}

	public void setColumns_CN(String[] columns_CN) {
		this.columns_CN = columns_CN;
	}

	public String[] getColumns_EN() {
		return columns_EN;
	}

	public void setColumns_EN(String[] columns_EN) {
		this.columns_EN = columns_EN;
	}
	

	public int getColumnsNum() {
		return columnsNum;
	}

	public void setColumnsNum(int columnsNum) {
		this.columnsNum = columnsNum;
	}

	 
	public String[] getInfo() {
		String[] info = new String[12];
	    info[0] = this.getId();
	    info[1] = this.getName();
	    info[2] = this.getWriter();
	    info[3] = this.getISBN();
	    info[4] = this.getPublisher();
	    info[5] = this.getCheck_in_String();
	    info[6] = this.getPublish_String();
	    info[7] = this.getPrice();
	    info[8] = this.getType();
	    info[9] = this.getSimpleinfo();
	    info[10] = this.getRent();
	    info[11] = this.getOther();
	    return info;
	}
	public void setInfo(String[] info) {
		this.info = info;
	}
	 
}