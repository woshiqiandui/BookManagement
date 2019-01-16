package entities;
/**
 * function:���࣬��Reader��Admin�ĸ���
 * @author ��Ǩ��
 *  Email: woshiqiandui@gmail.com
 * @version 2017-9-6  ����1:49:06
 */
public abstract class Person {

	//final �������޸��еĳ��ȣ��еĳ����Ǹ���ֵ
	final static  int columnsNumber = 3 ;
	protected String id;
	protected String name;
	protected String password;
	//�е����ַ�Ϊ���ĺ�Ӣ�ġ�
	protected static String[] columnsCN ={ "���","����","����"};
	protected static String[] columnsEN ={ "id","name","password"};
   
	
    /** 
     * @author ��Ǩ��
     * @param id,name,password
     */
	public Person(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;

	}

	/** getters��setters ���� */
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