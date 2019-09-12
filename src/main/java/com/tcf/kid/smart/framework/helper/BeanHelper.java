package com.tcf.kid.smart.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tcf.kid.smart.framework.util.ReflectUtil;

/***
 * TODO TCF Bean�����Bean���ʵ��֮���ӳ���ϵMap������
 * TODO TCF ��ʼ������Bean�����Bean���ʵ��֮���ӳ���ϵMap
 * TODO TCF ����Bean������ͻ�ȡBean���ʵ��
 * TODO TCF ��Bean�����Bean���ʵ��֮���ӳ���ϵMap�д����µ�ӳ���ϵ
 * @author 71485
 *
 */
public class BeanHelper {

	//TODO TCF ȫ��Bean�����Bean���ʵ��֮���ӳ���ϵMap
	public static Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>,Object>();
	
	//TODO TCF ��ʼ������Bean�����Bean���ʵ��֮���ӳ���ϵMap
	static
	{
		try
		{
			//TODO TCF �Ѿ����صĻ�׼�������Ӱ��е�������
			Set<Class<?>> classList=ClassHelper.CLASS_LIST;
			
			if(classList!=null && classList.size()>0)
			{
				for(Class<?> targetClass:classList)
				{
					Object targetInstance=ReflectUtil.newInstance(targetClass);
					BEAN_MAP.put(targetClass,targetInstance);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO TCF ����Bean������ͻ�ȡBean���ʵ��
	public static Object getBeanInstance(Class<?> targetClass)
	{
		return BEAN_MAP.get(targetClass);
	}
	
	//TODO TCF ��Bean�����Bean���ʵ��֮���ӳ���ϵMap�д����µ�ӳ���ϵ
	public static void putBeanMapping(Class<?> targetClass,Object targetInstance)
	{
		BEAN_MAP.put(targetClass,targetInstance);
	}
}
