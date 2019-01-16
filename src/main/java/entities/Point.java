package entities;

import java.util.Date;

/**
 * @function:���ֱ����ڴ�����߻��ֵ������Ϣ
 * @author ��Ǩ��
 * @2017��10��20�� @����1:59:20
 * 
 */
public class Point {
	private String reader_id;
	private int sumpoint;
	private Date lastsign;
	private int signcount; 
	private int totalcharge;
	
	/**
	 * @function:���ڶ���ǩ����ʹ�õ��Ĺ�����
	 * @param reader_id
	 * @param sumpoint
	 * @param lastsign
	 * @param signcount
	 */
	public Point(String reader_id, int sumpoint, Date lastsign, int signcount) {
		super();
		this.reader_id = reader_id;
		this.sumpoint = sumpoint;
		this.lastsign = lastsign;
		this.signcount = signcount;
	}
	
	/**
	 * @function:��������Ĺ�����
	 * @param reader_id
	 * @param sumpoint
	 * @param lastsign
	 * @param signcount
	 * @param totalcharge
	 */
	public Point(String reader_id, int sumpoint, Date lastsign, int signcount, int totalcharge) {
		super();
		this.reader_id = reader_id;
		this.sumpoint = sumpoint;
		this.lastsign = lastsign;
		this.signcount = signcount;
		this.totalcharge = totalcharge;
	}
	
    
	public String getReader_id() {
		return reader_id;
	}


	public void setReader_id(String reader_id) {
		this.reader_id = reader_id;
	}


	public int getSumpoint() {
		return sumpoint;
	}


	public void setSumpoint(int sumpoint) {
		this.sumpoint = sumpoint;
	}


	public Date getLastsign() {
		return lastsign;
	}


	public void setLastsign(Date lastsign) {
		this.lastsign = lastsign;
	}


	public int getSigncount() {
		return signcount;
	}


	public void setSigncount(int signcount) {
		this.signcount = signcount;
	}


	public int getTotalcharge() {
		return totalcharge;
	}


	public void setTotalcharge(int totalcharge) {
		this.totalcharge = totalcharge;
	}
    
    
	@Override
	public String toString() {
		return "Point [reader_id=" + reader_id + ", sumpoint=" + sumpoint + ", lastsign=" + lastsign + ", signcount="
				+ signcount + ", totalcharge=" + totalcharge + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastsign == null) ? 0 : lastsign.hashCode());
		result = prime * result + ((reader_id == null) ? 0 : reader_id.hashCode());
		result = prime * result + signcount;
		result = prime * result + sumpoint;
		result = prime * result + totalcharge;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (lastsign == null) {
			if (other.lastsign != null)
				return false;
		} else if (!lastsign.equals(other.lastsign))
			return false;
		if (reader_id == null) {
			if (other.reader_id != null)
				return false;
		} else if (!reader_id.equals(other.reader_id))
			return false;
		if (signcount != other.signcount)
			return false;
		if (sumpoint != other.sumpoint)
			return false;
		if (totalcharge != other.totalcharge)
			return false;
		return true;
	}


	public static void main(String[] args) {
		
	}

}
