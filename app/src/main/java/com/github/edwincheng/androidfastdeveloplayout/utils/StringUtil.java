package com.github.edwincheng.androidfastdeveloplayout.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * TODO<字符串工具类> 
 */
public class StringUtil {

	/**
	 * 替换字符串
	 */
	public static String replaceString(String source) {
		if (StringUtil.isEmpty(source))
			return "";
		source = source.replace('\'', '\"');
		source = source.replace("\\[", "\\【");
		source = source.replace("\\]", "\\】");
		source = source.replace("\\<", "\\《");
		source = source.replace("\\>", "\\》");
		return source;
	}

	/**
	 * 判断文本为空
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0||str.equals(""));
	}

	/**
	 * 只包含数字和字母
	 */
	public static boolean isNumberLetters(String str) {
		if (str == null || str.length() == 0)
			return true;

		return !str.matches("[a-zA-Z0-9]+");
	}

	/**
	 * 只包含数字字母和中文
	 *
	 */
	public static boolean isNumberLettersCharacter(String str) {
		if (str == null || str.length() == 0)
			return true;

		return !str.matches("[0-9a-zA-Z\\u4e00-\\u9fa5]+");
	}

	/**
	 * 获取文件后缀名
	 */
	public static String getsuffix(String str){
        if(StringUtil.isEmpty(str)){
            return "";
        }
		return str.substring(str.lastIndexOf(".") +1);
	}
	/**
	 * 获取文件全名
	 */
	public static String getallfilename(String str){
		return str.substring(str.lastIndexOf("/")+1);
	}
	/**
	 * 获取绝对文件名
	 */
	public static String getfilename(String str) {
		if(StringUtil.isEmpty(str)){
			return "";
		}
		int first;
		if (str.contains("/")) {
			first = str.lastIndexOf("/") + 1;
		} else {
			first = 0;
		}
		int last;
		if (str.contains(".")) {
			last = str.lastIndexOf(".") + 1;
		} else {
			last = str.length();
		}
		if (first>last){
			return "";
		}else {
			return str.substring(first, last-1);
		}

	}

	/*检测字符串编码*/
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception ignored) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception ignored) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception ignored) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception ignored) {
		}
		return "";
	}

	// 将 GB2312 编码格式的字符串转换为 UTF-8 格式的字符串：
	public static String gb2312ToUtf8(String str) {
		String urlEncode = "" ;
		try {
			urlEncode = URLEncoder.encode (str, "UTF-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlEncode;
	}

	// 将 GBK 编码格式的字节数组转换为 UTF-8 格式的字符串：
	public static String GbkByteToUtf8(byte[] byte1) {
		String urlEncode = "";
		try {
			urlEncode = URLDecoder.decode(new String(byte1,"gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlEncode;
	}

	// 将 GBK 编码格式的字节数组转换为 UTF-8 格式的字符串：
	public static String Utf8ByteToGbk(String utf8) {
		String urlEncode = "";
		try {
			urlEncode = URLDecoder.decode(utf8, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlEncode;
	}

	 /**
	   * 根据路径获取文件id
	   * @param path
	   */
	 public static String getFileID(String path){
		 if(StringUtil.isEmpty(path)){
			 return "";
		 }
		 return path.substring(path.lastIndexOf("=")+1);
	 }

	/**
	 * 根据用户id查询数组是否存在
	 */
	public static boolean isExist(int[] aiFileUserID , int UserID){
		boolean issave = false ;
		for(int j : aiFileUserID){
			if(j == UserID) {
				issave = true;
				break;
			}
		}
		return issave;
	}
}