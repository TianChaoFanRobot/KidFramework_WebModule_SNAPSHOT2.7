package com.tcf.kid.smart.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/***
 * TODO TCF ���乤����
 * TODO TCF ���ڷ��䴴��ָ������ʵ��
 * TODO TCF ���ڷ����Ŀ��ʵ����Ŀ������ע������ֵ
 * TODO TCF ���ڷ������Ŀ��ʵ����Ŀ�귽��
 * @author 71485
 *
 */
public class ReflectUtil {

	//TODO TCF ���ڷ��䴴��ָ������ʵ��
	public static Object newInstance(Class<?> targetClass)
	{
		Object instance=null;
		
		try
		{
			instance=targetClass.newInstance();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return instance;
	}
	
	//TODO TCF ���ڷ����Ŀ��ʵ����Ŀ������ע������ֵ
	public static void setField(Object targetInstance,Field field,Object fieldValue)
	{
		try
		{
			field.setAccessible(true);
			field.set(targetInstance,fieldValue);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO TCF ���ڷ������Ŀ��ʵ����Ŀ�귽��
	public static Object invokeMethod(Object targetInstance,Method targetMethod,Object...methodParams)
	{
		Object invokeResult=null;
		
		try
		{
			invokeResult=targetMethod.invoke(targetInstance,methodParams);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return invokeResult;
	}
}
