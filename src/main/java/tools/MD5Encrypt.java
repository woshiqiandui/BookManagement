package tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * function:这是一个md5加密类，方法全部设置为静态
 * @author 陈迁对 Email: woshiqiandui@gmail.com
 * @version 2017-9-5  上午12:06:24
 */
public class MD5Encrypt {

	/**
	 * function:md5加密
	 * @return fillMD5(md5Str)
	 * @param args
	 */
	public static String encryptByMD5(String str) {
		String md5Str = null;//加密后的字符串
		try {
			
			//静态类MessageDigest只能用getInstance()方法来实例化自己
			MessageDigest md =MessageDigest.getInstance("MD5");
			//生成md5加密摘要
			md.update(str.getBytes());//计算md5函数
			md5Str = new BigInteger(1, md.digest()).toString(16); 
			/* 1.public byte[] digest() 
			 * 通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。
			 * 2.public BigInteger(int signum,byte[] magnitude)
			 * 将 BigInteger 的符号-数量表示形式转换为 BigInteger。该符号表示为一个正负号整数值：
			 * -1 表示负，0 表示零，1 表示正。该大小是一个 big-endian 字节顺序的 byte 数组：
			 * 最高有效字节在第零个元素中。
			 * 3.public String toString(int radix)
			 * 返回此 BigInteger 的给定基数的字符串表示形式。
			 * 如果该基数超出从 Character.MIN_RADIX 到 Character.MAX_RADIX（包括）这一范围，
			 * 则其默认值为 10（Integer.toString 就是这种情况）。此处设为16就是转换为十六进制。
			 */
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	 
		//由于中间用BigInteger进行处理，有可能产生一个31位的字符串，需要处理
		return fillMD5(md5Str);		
	} 
	   /**
	    * function:处理31位，使其恢复到32位
	    * @param md5
	    * @return md5 or "0"+md5
	    */
	   public static String fillMD5(String md5){
		   /*产生31位字符串的原因是开头的0被过滤了，但是对于加密的字符串而言，这个必不可缺
		    * 因此，如果字符串长度不是32位，那就要在开头加上一个0
		    */
	        return md5.length()==32?md5:fillMD5("0"+md5);
	    }
	 

}
