package entities;

/**
     * @author 陈迁对
     * Email: woshiqiandui@gmail.com
     * @version 2017年9月27日  上午9:43:41
     */
public class Admin extends Person{ 
	
	/**
	 * @author 陈迁对
	 * @param id,name,password
	 */
    public Admin(String id, String name,String password) {
		super(id,name,password);
	}
	
  /**
   * function：重写toString方法
   * @author 陈迁对  
   * @return String 
   */
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + "]";
	}
	/**
	 * function:重写hashCode方法
	 * @author 陈迁对
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
	 * function:重写equals方法
	 * @author 陈迁对
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
