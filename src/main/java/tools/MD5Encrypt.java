package tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * function:����һ��md5�����࣬����ȫ������Ϊ��̬
 * @author ��Ǩ�� Email: woshiqiandui@gmail.com
 * @version 2017-9-5  ����12:06:24
 */
public class MD5Encrypt {

	/**
	 * function:md5����
	 * @return fillMD5(md5Str)
	 * @param args
	 */
	public static String encryptByMD5(String str) {
		String md5Str = null;//���ܺ���ַ���
		try {
			
			//��̬��MessageDigestֻ����getInstance()������ʵ�����Լ�
			MessageDigest md =MessageDigest.getInstance("MD5");
			//����md5����ժҪ
			md.update(str.getBytes());//����md5����
			md5Str = new BigInteger(1, md.digest()).toString(16); 
			/* 1.public byte[] digest() 
			 * ͨ��ִ���������֮������ղ�����ɹ�ϣ���㡣�ڵ��ô˷���֮��ժҪ�����á�
			 * 2.public BigInteger(int signum,byte[] magnitude)
			 * �� BigInteger �ķ���-������ʾ��ʽת��Ϊ BigInteger���÷��ű�ʾΪһ������������ֵ��
			 * -1 ��ʾ����0 ��ʾ�㣬1 ��ʾ�����ô�С��һ�� big-endian �ֽ�˳��� byte ���飺
			 * �����Ч�ֽ��ڵ����Ԫ���С�
			 * 3.public String toString(int radix)
			 * ���ش� BigInteger �ĸ����������ַ�����ʾ��ʽ��
			 * ����û��������� Character.MIN_RADIX �� Character.MAX_RADIX����������һ��Χ��
			 * ����Ĭ��ֵΪ 10��Integer.toString ����������������˴���Ϊ16����ת��Ϊʮ�����ơ�
			 */
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	 
		//�����м���BigInteger���д����п��ܲ���һ��31λ���ַ�������Ҫ����
		return fillMD5(md5Str);		
	} 
	   /**
	    * function:����31λ��ʹ��ָ���32λ
	    * @param md5
	    * @return md5 or "0"+md5
	    */
	   public static String fillMD5(String md5){
		   /*����31λ�ַ�����ԭ���ǿ�ͷ��0�������ˣ����Ƕ��ڼ��ܵ��ַ������ԣ�����ز���ȱ
		    * ��ˣ�����ַ������Ȳ���32λ���Ǿ�Ҫ�ڿ�ͷ����һ��0
		    */
	        return md5.length()==32?md5:fillMD5("0"+md5);
	    }
	 

}
