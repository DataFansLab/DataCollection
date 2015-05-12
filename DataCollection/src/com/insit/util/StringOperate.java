/**
 * DataCollection/com.insit.util/StringOperate.java
 * 2014-4-16/下午9:07:30 by Ruby
 */
package com.insit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ruby
 *
 */
public class StringOperate {
	public static String unicodeToString(String str) {
	    Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
	    Matcher matcher = pattern.matcher(str);
	    char ch;
	    while (matcher.find()) {
	        ch = (char) Integer.parseInt(matcher.group(2), 16);
	        str = str.replace(matcher.group(1), ch + "");    
	    }
	    return str;
	}
	
	
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isChinese(String str){
		if(str.getBytes().length == str.length())
			return false;
		return true;
	}
}
