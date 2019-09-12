package com.tcf.kid.smart.framework.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tcf.kid.smart.framework.annotation.KidAspect;
import com.tcf.kid.smart.framework.helper.BeanHelper;
import com.tcf.kid.smart.framework.helper.ClassHelper;
import com.tcf.kid.smart.framework.util.ReflectUtil;

/***
 * TODO TCF AOP��̬�������������
 * TODO TCF ��ʼ������AOP��������AOP�Զ�����������֮���ӳ���ϵMap
 * TODO TCF ��ʼ�����ش���Ŀ��ʹ���Ŀ������д���ʵ��֮���ӳ���ϵMap
 * TODO TCF ���ô��������ݴ���Ŀ��ʹ���Ŀ������д���ʵ����������cglib��̬����ʵ������������ʵ��ִ��������˳�����ÿ������ʵ���Ĵ�����
 * TODO TCF ������Ŀ�����ͺʹ���Ŀ���cglib��̬����ʵ������Bean�����Bean���ʵ��֮���ӳ���ϵMap
 * @author 71485
 *
 */
public class AopHelper {

	//TODO TCF ����AOP��̬��������
	static
	{
		try
		{
			//TODO TCF ����AOP���������Զ���AOP��������֮���ӳ���ϵMap
			Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
			
			//TODO TCF ���ش���Ŀ��ʹ���Ŀ������д���ʵ��֮���ӳ���ϵMap
			Map<Class<?>,List<Proxy>> targetMap=createTargetMap(proxyMap);
			
			if(targetMap!=null)
			{
				for(Map.Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet())
				{
					//TODO TCF ����Ŀ������
					Class<?> targetClass=targetEntry.getKey();
					
					//TODO TCF ����Ŀ������д���ʵ��
					List<Proxy> proxyList=targetEntry.getValue();
					
					//TODO TCF ����AOP��̬���������ݴ���Ŀ��ʹ���Ŀ������д���ʵ����������cglib��̬����ʵ������������ʵ��ִ��������˳�����ÿ������ʵ���Ĵ�����
					Object proxy=ProxyManager.newProxyInstance(targetClass,proxyList);
					
					//TODO TCF ������Ŀ��ʹ���Ŀ���cglib��̬����ʵ������Bean�����Bean���ʵ��֮���ӳ���ϵMap
					BeanHelper.putBeanMapping(targetClass,proxy);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO TCF ����ʹ��KidAspect����ע���ע���Զ���AOP��������
	public static Set<Class<?>> loadAspectClass(KidAspect kidAspect)
	{
		Set<Class<?>> classList=new HashSet<Class<?>>();
		
		//TODO TCF ����Ŀ������
		Class<? extends Annotation> annotation=kidAspect.value();
		
		if(annotation!=null && !annotation.equals(KidAspect.class))
		{
			classList.addAll(ClassHelper.loadClassByAnnotation(annotation));
		}
		
	    return classList;
	}
	
	//TODO TCF ����AOP��������AOP�Զ�����������֮���ӳ���ϵMap
	public static Map<Class<?>,Set<Class<?>>> createProxyMap()
	{
		Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>,Set<Class<?>>>();
		
		try
		{
			//TODO TCF AOP�������������Զ���AOP��������
			Set<Class<?>> aspectClassList=ClassHelper.loadClassBySuperClass(ProxyAspect.class);
			
			if(aspectClassList!=null && aspectClassList.size()>0)
			{
				for(Class<?> aspectClass:aspectClassList)
				{
					//TODO TCF ��ȡ��ע��AOP�Զ������������Ϸ���ע��KidAspect����ע��
					if(aspectClass.isAnnotationPresent(KidAspect.class))
					{
						KidAspect kidAspect=aspectClass.getAnnotation(KidAspect.class);
						
						//TODO TCF ����KidAspect����ע���ע���Զ���AOP��������
						Set<Class<?>> targetClassList=loadAspectClass(kidAspect);
						
						proxyMap.put(aspectClass,targetClassList);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return proxyMap;
	}
	
	//TODO TCF ���ش���Ŀ��ʹ���Ŀ������д���ʵ��֮���ӳ���ϵMap
	public static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap)
	{
		Map<Class<?>,List<Proxy>> targetMap=new HashMap<Class<?>,List<Proxy>>();
		
		try
		{
			if(proxyMap!=null)
			{
				for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry:proxyMap.entrySet())
				{
					//TODO TCF AOP�������
					Class<?> aspectClass=proxyEntry.getKey();
					
					//TODO TCF AOP�Զ�����������
					Set<Class<?>> targetClassList=proxyEntry.getValue();
					
					if(targetClassList!=null && targetClassList.size()>0)
					{
						for(Class<?> targetClass:targetClassList)
						{
							//TODO TCF AOP�������ʵ�ֵ�AOP��̬����ӿ�ʵ��
							Proxy proxy=(Proxy)ReflectUtil.newInstance(aspectClass);
							
							if(targetMap.containsKey(targetClass))
							{
								targetMap.get(targetClass).add(proxy);
							}
							else
							{
								List<Proxy> proxyList=new ArrayList<Proxy>();
								proxyList.add(proxy);
								targetMap.put(targetClass,proxyList);
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return targetMap;
	}
}
