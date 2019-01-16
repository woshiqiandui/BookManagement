package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang.StringUtils;
import entities.Book;

public class BookCheck {

	/**
	 * function :�������ַ����Ƿ�Ϊ������������
	 * 
	 * @param numberStr
	 * @return
	 * @author ��Ǩ��
	 */
	public static boolean isFloatOrInteger(String numberStr) {

		// 1.�ȴ���ո�
		numberStr = numberStr.trim();
		// 2.���ַ����ָ�
		String[] numSubStr = numberStr.split("\\.");
		int pointSize = 0;
		// 3.�жϷָ����ַ����Ƿ�Ϊ����
		for (int i = 0; i < numberStr.length(); i++) {
			if (numberStr.charAt(i) == '.') {
				pointSize++;
			}
		}
		if (pointSize == 1 || pointSize == 0) {
			// �����һ�������û��
			for (int i = 0; i < numSubStr.length; i++) {
				if (!StringUtils.isNumeric(numSubStr[i])) {
					return false;
				}
			}
			// 4.����Ҫ�󷵻�true
			return true;
		}
		return false;
	}

	/**
	 * function����ʱ���ʽ�Ƿ���ȷ��ʱ�������Ƿ�Ϸ����ж�
	 * 
	 * @param date
	 * @return boolean
	 * @author ��Ǩ��
	 */
	public static boolean isValidDate(String date) {
        //�ַ���ȡ�����߿�
		date = date.trim();
		int HH =0;
		int mm =0;
		int ss =0;
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		// ���ж��Ƿ��ǰ���ʱ�������,�����ʱ�䣬�����ж�ʱ��
		if (date.length() > 11) {
//			dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"); 
			try {
			 HH=Integer.parseInt(date.substring(11, 13));
			 mm=Integer.parseInt(date.substring(14, 16));
			 ss=Integer.parseInt(date.substring(17, 19));
			}catch(Exception e) {
				//���ת��������ô��˵��һ�������⡣
				return false;
			}
			 //���ж�ʱ���Ƿ�Ϸ�����ֵ����Ҳ���Ǵ��
			 if(HH<0||HH>=24||mm<0||mm>=60||ss<0||ss>=60) {
				 return false;
			 }
			 date = date.substring(0,10);
		}  
		/**
		 * ���ﴦ���ʱ���ʽ�Ƿ���ȷ���жϣ�ͬʱҲ�����ʱ�������Ƿ�Ϸ����ж� ������5�����
		 * 1.��ʽ��ȷ������ֵ���󣬲��ᱨ������2017-2-33����ת��Ϊ2017-2-2
		 * 2.��ʽ���󣬺�����ǿ��ת��Ϊһ�����ڣ���2017-2--3��ǿ��ת��Ϊ2017-2-28
		 * 3.��ʽ��ȫ����һ�㶼��ȱ��"-"���ͻᱨ����2017
		 * 4.��ʽ���󣬵����ǺϷ���ʱ�䣬��2017-1-1��Ӧ��д��2017-01-01 5.��ʽ��ȷ��������ȷ������ݹ�����20117
		 * ����������⣬����ʽ���£� 1.�ȶ�ʱ������ת�������ת��������ôһ���������ĵ�3�����
		 * 2.�ٶ�ת���ɵ�Date���������ת��������ת��Ϊ�ַ�����ʽ��transferdate��
		 * 3.��󽫲���date���ַ���transferdate���бȽϣ������ͬ˵����ʽ�����ݶ���ȷ��
		 * ����������1�����������Ͳ��ԣ�Ϊ������2������������жϱȽ����ܡ� 4.����ݵ�����ȡ�������4������������ݹ���Ҳ��ʾ����
		 * 5.ֵ��ע����ǣ����������ʱ���ַ���������Ҫ���û������Ϊyyyy-mm-dd
		 * ��2017-2-1���ǲ����ʵģ�����д�ɡ�2017-02-01�������ִ�����Ϊ�����������֡������û����������Ҫ��ʾ�û���
		 */
		Date d = new Date();
		try {
			d = dateFormat.parse(date);
			String transferdate = dateFormat.format(d);
			// ʱ���ʽת��

			// �Ƚ�ת����ĺ�ת��֮ǰ��ʱ���ַ����Ƿ�һ��
			if (transferdate.equals(date)) {
				// ���һ�£��ڿ�������Ƿ����
				GregorianCalendar calendar = new GregorianCalendar();
				// ��Date ��Ķ���date ת��Ϊ GregorianCalendar ���calendar
				calendar.setTime(d);
				if (calendar.get(Calendar.YEAR) < 2020 && calendar.get(Calendar.YEAR) > 1900) {
					// ʱ�����1900�겢��С��2020�꣬����Ϊ����ȷ��ʱ��
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// �����쳣˵��ת��ʧ��s
			return false;
		}

	}

	/**
	 * function:�������book����ֵ������֤��ȫ��ͨ����֤����true,���򷵻�false
	 * 
	 * @param bookInfo
	 * @param Optype
	 * @return boolean
	 */
	public static boolean isValidBookMessage(Book bookInfo) {
		// ���book��ֵ
		String id = bookInfo.getId();
		String name = bookInfo.getName();
		String writer = bookInfo.getWriter();
		String ISBN = bookInfo.getISBN();
		String publisher = bookInfo.getPublisher();
		String price = bookInfo.getPrice();
		String type = bookInfo.getType();
		String other = bookInfo.getOther();
		String check_in_date = bookInfo.getCheck_in_String();
		String publish_date = bookInfo.getPublish_String();
		String simpleinfo = bookInfo.getSimpleinfo();
		String rent = bookInfo.getRent();
		if (!id.isEmpty()) {
			if (!name.isEmpty()) {
				if (!writer.isEmpty()) {
					if (!ISBN.isEmpty()) {
						if (!publisher.isEmpty()) {
							if (isFloatOrInteger(price)) {
								// ���е��ж��Ƿ�Ϊ�������������ķ���
								if (!type.isEmpty()) {
									if (other.isEmpty()) {
										// ���otherΪ�գ�ʹ�����""
										other = "";
									}
									if (simpleinfo==null||simpleinfo.isEmpty()) {
										// ���simpleinfoΪ�գ���ʹ�����""
										simpleinfo = "";
									}
									System.out.println("check_in_date="+check_in_date);
									System.out.println("publish_date"+publish_date);
									if (isValidDate(check_in_date)) {
										// 3.ִ�в�����䣬����Ƿ�ɹ�
										if (isValidDate(publish_date)) {

											if (!rent.isEmpty()) {
												// ��֤�ɹ���
												return true;
											} else {
												System.out.println("���ȱʧ");
											}
										} else {
											System.out.println("�������ڸ�ʽ����ֵ����");
										}

									} else {
										System.out.println("check_in_date" + check_in_date);
										System.out.println("�Ǽ����ڸ�ʽ����ֵ����");
									}
								} else {
									System.out.println("����д����");
								}
							} else {
								System.out.println("����ȷ��д�۸�");
							}
						} else {
							System.out.println("����д������");
						}
					} else {
						System.out.println("����дISBN");
					}
				} else {
					System.out.println("����д����");
				}
			} else {
				System.out.println("����д����");
			}
		} else {
			System.out.println("����д���");
		}
		return false;
	}

	public static void main(String[] args) {
	/*	boolean isValid1= isValidDate("2017-10-08 02:19:03");
		boolean isValid2 = isValidDate("2017-2-33");
		boolean isValid3 = isValidDate("2017-2--3");
		boolean isValid4 = isValidDate("2017-1-1");
		System.out.println("��������1������"+"2017-10-08 02:19:03"+" Ԥ�����"+isValid1+" ʵ�����"+"true"+" �ɹ�");
		System.out.println("��������2������"+"2017-2-33"+" Ԥ�����"+isValid2+" ʵ�����"+"false"+" �ɹ�");
		System.out.println("��������3������"+"2017-2--3"+" Ԥ�����"+isValid3+" ʵ�����"+"false"+" �ɹ�");
		System.out.println("��������4������"+"2017-1-1"+" Ԥ�����"+isValid4+" ʵ�����"+"false"+" �ɹ�");
	*/
		String numStr1 = "-1000000";
		String numStr2 = "1000000";
		String numStr3 = "0";
		String numStr4 = "210000000000000";
		String numStr5 = "100.000";
		String numStr6 = "210000000000000.000";
		String numStr7= "abcdef";
        String numStr8= "2.3fsa";
        boolean isFloatOrInteger1 = isFloatOrInteger(numStr1);
        boolean isFloatOrInteger2 = isFloatOrInteger(numStr2);
        boolean isFloatOrInteger3 = isFloatOrInteger(numStr3);
        boolean isFloatOrInteger4 = isFloatOrInteger(numStr4);
        boolean isFloatOrInteger5 = isFloatOrInteger(numStr5);
        boolean isFloatOrInteger6 = isFloatOrInteger(numStr6);
        boolean isFloatOrInteger7 = isFloatOrInteger(numStr7);
		boolean isFloatOrInteger8 = isFloatOrInteger(numStr8);
		 
		System.out.println("��������1��"+"\t"+numStr1+"\t"+"Ԥ�ڽ��Ϊ��false"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger1+"\t"+"�ɹ�");
		System.out.println("��������2��"+"\t"+numStr2+"\t"+"Ԥ�ڽ��Ϊ��true"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger2+"\t"+"�ɹ�");
		System.out.println("��������3��"+"\t"+numStr3+"\t"+"Ԥ�ڽ��Ϊ��true"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger3+"\t"+"�ɹ�");
		System.out.println("��������4��"+"\t"+numStr4+"\t"+"Ԥ�ڽ��Ϊ��true"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger4+"\t"+"�ɹ�");
		System.out.println("��������5��"+"\t"+numStr5+"\t"+"Ԥ�ڽ��Ϊ��true"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger5+"\t"+"�ɹ�");
		System.out.println("��������6��"+"\t"+numStr6+"\t"+"Ԥ�ڽ��Ϊ��true"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger6+"\t"+"�ɹ�");
		System.out.println("��������7��"+"\t"+numStr7+"\t"+"Ԥ�ڽ��Ϊ��false"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger7+"\t"+"�ɹ�");
	    System.out.println("��������8��"+"\t"+numStr8+"\t"+"Ԥ�ڽ��Ϊ��false"+"\t"+"ʵ�ʽ��Ϊ��"+"\t"+isFloatOrInteger8+"\t"+"�ɹ�");
 
	 
	 
		
	}
}
