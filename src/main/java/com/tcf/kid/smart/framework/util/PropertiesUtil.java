package com.tcf.kid.smart.framework.util;

import java.util.Properties;

/***
 * TODO TCF ���������ļ�������
 * TODO TCF �������������ļ����������������ļ�
 * @author 71485
 *
 */
public class PropertiesUtil {

	//TODO TCF ����ģʽ(����ģʽ��������ٶ�������ȡ�����ٶȿ�)
	private static Properties properties=new Properties();
	
	//TODO TCF �������������ļ����������������ļ�
	public static Properties loadPropertiesFile(String propertiesFileName)
	{
		try
		{
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFileName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return properties;
	}
}
