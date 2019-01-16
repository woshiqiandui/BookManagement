package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang.StringUtils;
import entities.Book;

public class BookCheck {

	/**
	 * function :检测这个字符串是否为浮点数或整数
	 * 
	 * @param numberStr
	 * @return
	 * @author 陈迁对
	 */
	public static boolean isFloatOrInteger(String numberStr) {

		// 1.先处理空格
		numberStr = numberStr.trim();
		// 2.将字符串分割
		String[] numSubStr = numberStr.split("\\.");
		int pointSize = 0;
		// 3.判断分割后的字符串是否为数字
		for (int i = 0; i < numberStr.length(); i++) {
			if (numberStr.charAt(i) == '.') {
				pointSize++;
			}
		}
		if (pointSize == 1 || pointSize == 0) {
			// 如果有一个点或者没有
			for (int i = 0; i < numSubStr.length; i++) {
				if (!StringUtils.isNumeric(numSubStr[i])) {
					return false;
				}
			}
			// 4.符合要求返还true
			return true;
		}
		return false;
	}

	/**
	 * function：对时间格式是否正确和时间数据是否合法的判断
	 * 
	 * @param date
	 * @return boolean
	 * @author 陈迁对
	 */
	public static boolean isValidDate(String date) {
        //字符串取出两边空
		date = date.trim();
		int HH =0;
		int mm =0;
		int ss =0;
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		// 先判断是否是包括时间的日期,如果是时间，就先判断时间
		if (date.length() > 11) {
//			dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"); 
			try {
			 HH=Integer.parseInt(date.substring(11, 13));
			 mm=Integer.parseInt(date.substring(14, 16));
			 ss=Integer.parseInt(date.substring(17, 19));
			}catch(Exception e) {
				//如果转换出错，那么就说明一定有问题。
				return false;
			}
			 //先判断时间是否合法，数值过大也还是错的
			 if(HH<0||HH>=24||mm<0||mm>=60||ss<0||ss>=60) {
				 return false;
			 }
			 date = date.substring(0,10);
		}  
		/**
		 * 这里处理对时间格式是否正确的判断，同时也处理对时间数据是否合法的判断 有以下5种情况
		 * 1.格式正确，但数值错误，不会报错，比如2017-2-33，会转换为2017-2-2
		 * 2.格式错误，函数会强制转换为一个日期，如2017-2--3会强制转换为2017-2-28
		 * 3.格式完全错误（一般都是缺少"-"）就会报错，如2017
		 * 4.格式错误，但是是合法的时间，如2017-1-1，应该写成2017-01-01 5.格式正确，数据正确但是年份过大，如20117
		 * 针对以上问题，处理方式如下： 1.先对时间数据转换，如果转换出错，那么一定是上述的第3种情况
		 * 2.再对转换成的Date类变量进行转换，将其转换为字符串格式（transferdate）
		 * 3.最后将参数date与字符串transferdate进行比较，如果相同说明格式和数据都正确，
		 * 就是上述第1种情况。否则就不对，为上述第2种情况。这样判断比较严密。 4.对年份单独提取，处理第4种情况，如果年份过大也提示错误。
		 * 5.值得注意的是，对于输入的时间字符串，必须要求用户输入的为yyyy-mm-dd
		 * ”2017-2-1“是不合适的，必须写成”2017-02-01“，这种错误被认为是上述第四种。这在用户输入界面需要提示用户。
		 */
		Date d = new Date();
		try {
			d = dateFormat.parse(date);
			String transferdate = dateFormat.format(d);
			// 时间格式转换

			// 比较转换后的和转换之前的时间字符串是否一致
			if (transferdate.equals(date)) {
				// 如果一致，在看看年份是否过大
				GregorianCalendar calendar = new GregorianCalendar();
				// 将Date 类的对象date 转换为 GregorianCalendar 类的calendar
				calendar.setTime(d);
				if (calendar.get(Calendar.YEAR) < 2020 && calendar.get(Calendar.YEAR) > 1900) {
					// 时间大于1900年并且小于2020年，被认为是正确的时间
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// 出现异常说明转换失败s
			return false;
		}

	}

	/**
	 * function:对输入的book各个值进行验证，全部通过验证返回true,否则返回false
	 * 
	 * @param bookInfo
	 * @param Optype
	 * @return boolean
	 */
	public static boolean isValidBookMessage(Book bookInfo) {
		// 获得book的值
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
								// 类中的判断是否为浮点数或整数的方法
								if (!type.isEmpty()) {
									if (other.isEmpty()) {
										// 如果other为空，使它变成""
										other = "";
									}
									if (simpleinfo==null||simpleinfo.isEmpty()) {
										// 如果simpleinfo为空，就使它变成""
										simpleinfo = "";
									}
									System.out.println("check_in_date="+check_in_date);
									System.out.println("publish_date"+publish_date);
									if (isValidDate(check_in_date)) {
										// 3.执行插入语句，检查是否成功
										if (isValidDate(publish_date)) {

											if (!rent.isEmpty()) {
												// 验证成功！
												return true;
											} else {
												System.out.println("租金缺失");
											}
										} else {
											System.out.println("出版日期格式或数值错误");
										}

									} else {
										System.out.println("check_in_date" + check_in_date);
										System.out.println("登记日期格式或数值错误");
									}
								} else {
									System.out.println("请填写类型");
								}
							} else {
								System.out.println("请正确填写价格");
							}
						} else {
							System.out.println("请填写出版社");
						}
					} else {
						System.out.println("请填写ISBN");
					}
				} else {
					System.out.println("请填写作者");
				}
			} else {
				System.out.println("请填写书名");
			}
		} else {
			System.out.println("请填写编号");
		}
		return false;
	}

	public static void main(String[] args) {
	/*	boolean isValid1= isValidDate("2017-10-08 02:19:03");
		boolean isValid2 = isValidDate("2017-2-33");
		boolean isValid3 = isValidDate("2017-2--3");
		boolean isValid4 = isValidDate("2017-1-1");
		System.out.println("测试用例1：输入"+"2017-10-08 02:19:03"+" 预期输出"+isValid1+" 实际输出"+"true"+" 成功");
		System.out.println("测试用例2：输入"+"2017-2-33"+" 预期输出"+isValid2+" 实际输出"+"false"+" 成功");
		System.out.println("测试用例3：输入"+"2017-2--3"+" 预期输出"+isValid3+" 实际输出"+"false"+" 成功");
		System.out.println("测试用例4：输入"+"2017-1-1"+" 预期输出"+isValid4+" 实际输出"+"false"+" 成功");
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
		 
		System.out.println("测试用例1："+"\t"+numStr1+"\t"+"预期结果为：false"+"\t"+"实际结果为："+"\t"+isFloatOrInteger1+"\t"+"成功");
		System.out.println("测试用例2："+"\t"+numStr2+"\t"+"预期结果为：true"+"\t"+"实际结果为："+"\t"+isFloatOrInteger2+"\t"+"成功");
		System.out.println("测试用例3："+"\t"+numStr3+"\t"+"预期结果为：true"+"\t"+"实际结果为："+"\t"+isFloatOrInteger3+"\t"+"成功");
		System.out.println("测试用例4："+"\t"+numStr4+"\t"+"预期结果为：true"+"\t"+"实际结果为："+"\t"+isFloatOrInteger4+"\t"+"成功");
		System.out.println("测试用例5："+"\t"+numStr5+"\t"+"预期结果为：true"+"\t"+"实际结果为："+"\t"+isFloatOrInteger5+"\t"+"成功");
		System.out.println("测试用例6："+"\t"+numStr6+"\t"+"预期结果为：true"+"\t"+"实际结果为："+"\t"+isFloatOrInteger6+"\t"+"成功");
		System.out.println("测试用例7："+"\t"+numStr7+"\t"+"预期结果为：false"+"\t"+"实际结果为："+"\t"+isFloatOrInteger7+"\t"+"成功");
	    System.out.println("测试用例8："+"\t"+numStr8+"\t"+"预期结果为：false"+"\t"+"实际结果为："+"\t"+isFloatOrInteger8+"\t"+"成功");
 
	 
	 
		
	}
}
