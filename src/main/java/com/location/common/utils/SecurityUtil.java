package com.location.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 加密工具类
 * @author bwy
 * @date 2018/7/3 15:25
 */
public class SecurityUtil {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	private final static String[] STR_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * MD5加密(方式同devops平台)
	 * @author bawy
	 * @date 2018/7/3 15:22
	 * @param str 待加密的字符串
	 * @return 加密后转成16进制的字符串
	 */
	public static String getMD5Code(String str) {
		String result;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			result = str;
		}
		return result;
	}

	/**
	 * byte转出十六进制字符串
	 * @param bByte byte类型数据
	 * @return 转换后的字符串
	 */
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return STR_DIGITS[iD1] + STR_DIGITS[iD2];
	}

	/**
	 * 转换字节数组为16进制字串
	 * @param bytes 字节数组
	 * @return 转换后的数据
	 */
	private static String byteToString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte bByte : bytes) {
			sb.append(byteToArrayString(bByte));
		}
		return sb.toString();
	}
	/**
	 * 生成随机账号
	 * @return String
	 */
	public static String uuid() {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {//有可能是负数
			hashCodeV = -hashCodeV;
		}
		return String.format("%08d", hashCodeV);
	}
	public static void main(String[] args){
	    System.out.println(SecurityUtil.uuid());
	}

}
