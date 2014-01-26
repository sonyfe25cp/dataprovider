package dataprovider.utils;

import java.io.UnsupportedEncodingException;

public class CharsetUtils {
	
	/**
	 * 转换成%e5%8c%97%e4%ba%ac这种形式
	 * @param str
	 * @return
	 */
	public static String toUtf8(String str){
		byte[] bytes;
		String string ="";
		try {
			bytes = str.getBytes("UTF8");
			StringBuilder sb = new StringBuilder();
			for(byte b : bytes){
				String s = Integer.toHexString(b);
				s = s.replace("ffffff", "%");
				sb.append(s);
			}
			string = sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}

}
