package com.tcf.kid.smart.framework.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/***
 * TODO TCF �ַ�������/���빤����
 * @author 71485
 *
 */
public class CodecUtil {

	//TODO TCF �ַ�������
	public static String encodeText(String str)
	{
		String result="";
		
		try
		{
			result=URLEncoder.encode(str,"UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	//TODO TCF �ַ�������
	public static String decodeText(String str)
	{
		String result="";
		
		try
		{
			result=URLDecoder.decode(str,"UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}
