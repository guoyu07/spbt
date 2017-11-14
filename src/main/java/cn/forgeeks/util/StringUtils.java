package cn.forgeeks.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * @author hechao
 *
 */
public class StringUtils {

	/**
	 * 生成36位uuid, 带 -
	 * 
	 * @return
	 */
	public static String getUUID36() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成32位uuid, 去掉 -
	 * 
	 * @return
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 返回长度为 strLength 的随机数，在前面补0
	 * 
	 */
	public static String getRandomNumber(int strLength) {
		Random rm = new Random();
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		String fixLenthString = String.valueOf(pross);
		return fixLenthString.substring(2, strLength + 2);
	}

	/**
	 * 随机生成人的中文名
	 * 
	 * @return
	 */
	public static String getRandomCN(int num) {
		String string = "";
		while (num-- > 0) {
			string += getRandomChar();
		}
		return string;
	}

	/**
	 * 随机生成常见汉字
	 * 
	 * @return
	 */
	public static String getRandomChar() {
		String str = "";
		int highCode;
		int lowCode;
		Random random = new Random();
		highCode = (176 + Math.abs(random.nextInt(39))); // B0 + 0~39(16~55)
		lowCode = (161 + Math.abs(random.nextInt(93))); // A1 + 0~93 每区有94个汉字
		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(highCode)).byteValue();
		b[1] = (Integer.valueOf(lowCode)).byteValue();
		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

}
