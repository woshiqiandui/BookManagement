package entities;

/**
 * 
 * @author 陈迁对 
 * Email: woshiqiandui@gmail.com
 * @version 2017年9月26日  下午6:26:32
 */
 
 
public class Reader extends Person{
	private String gender;
	private String birthday;
	final static int columnsNumber = 5;

	protected static String[] columnsCN ={ "编号","姓名","性别","生日","密码"};
	protected static String[] columnsEN ={ "id","name","gender","birthday","password"};
	  
    /** 构造方法1*/
	public Reader(String id, String name, String gender, String birthday,String password) {
		super(id,name,password);
		this.gender = gender;
		this.birthday = birthday;
	}
	/** 构造方法2*/
	public Reader(String id, String name, String password) {
		super(id,name,password);
		gender = "";
		birthday ="";
	}
	/**
	 * @author 陈迁对 
	 * function:重写toString方法
	 */
	public String toString() {
		return "Reader [id=" + id + ", name=" + name + ", gender=" + gender
				+ ", birthday=" + birthday + "]";
	}
	/**
	 * @author 陈迁对
	 * function:重写hashCode方法
	 */
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/**
	 * @author 陈迁对
	 * function：重写equals方法
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reader other = (Reader) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/** getter()和setter()方法 */
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public static String[] getColumnsCN() {
		return columnsCN;
	}
	public static void setColumnsCN(String[] columnsCN) {
		Reader.columnsCN = columnsCN;
	}
	public static String[] getColumnsEN() {
		return columnsEN;
	}
	public static void setColumnsEN(String[] columnsEN) {
		Reader.columnsEN = columnsEN;
	} 
	 
	
 
	
}
