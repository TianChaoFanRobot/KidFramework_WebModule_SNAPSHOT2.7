package com.tcf.kid.smart.framework.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/***
 * TODO TCF �ļ���������
 * @author 71485
 *
 */
public class StreamUtil {

	//TODO TCF ���������ж�ȡ�ַ���
	public static String readInputStream(InputStream inputStream)
	{
		StringBuffer stb=new StringBuffer();
		
		try
		{
			//TODO TCF �ַ�������
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
			
			if(reader!=null)
			{
				//TODO TCF �Ƿ������β
				int readPoint=-1;
				
				while((readPoint=reader.read())>-1)
				{
					stb.append(reader.readLine());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return stb.toString();
	}
}
