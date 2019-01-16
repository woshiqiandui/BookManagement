package entities;

/**
     * @author ��Ǩ��
     * Email: woshiqiandui@gmail.com
     * @version 2017��9��27��  ����9:43:41
     */
public class Admin extends Person{ 
	
	/**
	 * @author ��Ǩ��
	 * @param id,name,password
	 */
    public Admin(String id, String name,String password) {
		super(id,name,password);
	}
	
  /**
   * function����дtoString����
   * @author ��Ǩ��  
   * @return String 
   */
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + "]";
	}
	/**
	 * function:��дhashCode����
	 * @author ��Ǩ��
	 * @return result
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/**
	 * function:��дequals����
	 * @author ��Ǩ��
	 * @param obj
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Admin other = (Admin) obj;
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

	 
  
 
}
