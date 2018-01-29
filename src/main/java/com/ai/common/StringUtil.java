/**
 * ai 核心服务层
 * ============================================================================
 * * 版权所有 ai，并保留所有权利。
 * 网站地址: http://www.ai.com；
 * ----------------------------------------------------------------------------
 * $Author: james $
 * $Id: DbHelper.java 2015-04-29 
*/
package com.ai.common;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.resteasy.util.Base64;








public class StringUtil {
	
	
	/**
	 * base64编码
	 * 
	 * @param String
	 * @return String
	 */
	public static String encode(String input) throws Exception {
		String result = Base64.encodeBytes(input.getBytes("utf-8"));

		return result;
	}

	/**
	 * base64解码
	 * 
	 * @param string
	 * @return string
	 */
	public static String decode(String str) {
		byte[] bt = null;
		try {
			bt = Base64.decode(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(bt);
	}  
	
	/**
     * MD5加密（多次）
     * @param salt 
     * @param pwd 
     * @param enctimes 加密次数
     * @throws NoSuchAlgorithmException,UnsupportedEncodingException
     * @author james.wu
     */
	public static String EncoderByMd5(String salt,String pwd,int enctimes) throws NoSuchAlgorithmException,UnsupportedEncodingException{
		String saltTemp = salt;
		for(int i =0;i<enctimes;i++){
			saltTemp = StringUtil.EncoderByMd5(saltTemp, pwd);
		}
		return saltTemp;
	}
	/**
     * MD5加密（单次）
     * @param salt 
     * @param pwd 
     * @throws NoSuchAlgorithmException,UnsupportedEncodingException
     * @author james.wu
     */
	public  static String EncoderByMd5(String salt,String pwd) throws NoSuchAlgorithmException,UnsupportedEncodingException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update((salt+"||"+pwd).getBytes("UTF-8"));
		 StringBuffer buf=new StringBuffer(); 
		 for(byte b:md5.digest()){
			 buf.append(String.format("%02x", b&0xff));        
        }
		
		 String md5pwd = buf.toString();
		return md5pwd;
	}
	
	/**
	 * base64编码
	 * 
	 * @param String
	 * @return String
	 */
	public static String NullToEmpty(String input)  {
		if(input==null){
			return "";
		}else{
			return input;
		}

	}	
	
	public static Boolean IsAlphabet(String input) {
		String reg = "[a-zA-Z]";

		boolean isCract = input.matches(reg);
		return isCract;
	}
}
