package com.esteel.web.vo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 登陆加密
 * 
 * @author chenshouye
 *
 */
public class Encrypt {
	/**
	 * 利用MD5进行加密
	 * 
	 * @param str
	 *            待加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 *             没有这种产生消息摘要的算法
	 * @throws UnsupportedEncodingException
	 */
	public static String EncoderByMd5(String str) {
		// 确定计算方法
		MessageDigest md5;
		String newstr = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			Base64Encoder base64en = new Base64Encoder();
			// 加密后的字符串
			newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	/**
	 * 判断用户密码是否正确
	 * 
	 * @param newpasswd
	 *            用户输入的密码
	 * @param oldpasswd
	 *            数据库中存储的密码－－用户密码的摘要
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkpassword(String newpasswd, String oldpasswd) throws Exception {
		if (EncoderByMd5(newpasswd).equals(oldpasswd))
			return true;
		else
			return false;
	}
	// 测试
	/*
	 * public static void main(String[] args) { try { String str =
	 * Encrypt.EncoderByMd5("1"); System.out.println(str); } catch
	 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } }
	 */
}
