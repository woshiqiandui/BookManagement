package entities;
/**
 * function:人类，是Reader和Admin的父类
 * @author 陈迁对
 *  Email: woshiqiandui@gmail.com
 * @version 2017-9-6  上午1:49:06
 */
public abstract class Person {

	//final 不允许修改列的长度，列的长度是个定值
	final static  int columnsNumber = 3 ;
	protected String id;
	protected String name;
	protected String password;
	//列的名字分为中文和英文。
	protected static String[] columnsCN ={ "编号","姓名","密码"};
	protected static String[] columnsEN ={ "id","name","password"};
   
	
    /** 
     * @author 陈迁对
     * @param id,name,password
     */
	public Person(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;

	}

	/** getters和setters 方法 */
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
     
 
	public static String[] getColumnsCN() {
		return columnsCN;
	}

	public static void setColumnsCN(String[] columnsCN) {
		Person.columnsCN = columnsCN;
	}

	public static String[] getColumnsEN() {
		return columnsEN;
	}

	public static void setColumnsEN(String[] columnsEN) {
		Person.columnsEN = columnsEN;
	}
  

}