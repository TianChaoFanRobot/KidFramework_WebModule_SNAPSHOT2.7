package com.tcf.kid.smart.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.tcf.kid.smart.framework.annotation.KidAspect;
import com.tcf.kid.smart.framework.annotation.KidComponent;
import com.tcf.kid.smart.framework.annotation.KidController;
import com.tcf.kid.smart.framework.annotation.KidRepository;
import com.tcf.kid.smart.framework.annotation.KidService;
import com.tcf.kid.smart.framework.aop.AopHelper;
import com.tcf.kid.smart.framework.common.Const;
import com.tcf.kid.smart.framework.ioc.IocHelper;
import com.tcf.kid.smart.framework.util.ClassUtil;
import com.tcf.kid.smart.framework.util.PropertiesUtil;

/***
 * TODO TCF �������������
 * TODO TCF ��ʼ�����ػ�׼�������Ӱ��е�������
 * TODO TCF ����ָ��ע�����ͱ�ע��
 * TODO TCF ���ظ���(�ӿ�)����������(�ӿ�ʵ����)
 * TODO TCF ����Ԥ����ע���ע��
 * @author 71485
 *
 */
public class ClassHelper {

	//TODO TCF ȫ���������������
	public static Set<Class<?>> CLASS_LIST=new HashSet<Class<?>>();
	
	//TODO TCF ��ʼ�����ػ�׼�������Ӱ��е�������
	static
	{
		CLASS_LIST=ClassUtil.loadClassByPackageName(PropertiesUtil.loadPropertiesFile(Const.PROPERTIES_FILES.BASE_PROPERTIES).getProperty(Const.BASE_PROPERTIES_KEYS.BASE_PACKAGE));
	}
	
	//TODO TCF ����ָ��ע�����ͱ�ע����
	public static Set<Class<?>> loadClassByAnnotation(Class<? extends Annotation> annotation)
	{
		Set<Class<?>> classList=new HashSet<Class<?>>();
		
		try
		{
			if(CLASS_LIST!=null && CLASS_LIST.size()>0)
			{
				for(Class<?> cls:CLASS_LIST)
				{
					if(cls.isAnnotationPresent(annotation))
					{
						classList.add(cls);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return classList;
	}
	
	//TODO TCF ���ظ���(�ӿ�)����������(�ӿ�ʵ����)
	public static Set<Class<?>> loadClassBySuperClass(Class<?> superClass)
	{
		Set<Class<?>> classList=new HashSet<Class<?>>();
		
		try
		{
			if(CLASS_LIST!=null && CLASS_LIST.size()>0)
			{
				for(Class<?> cls:CLASS_LIST)
				{
					if(superClass.isAssignableFrom(cls) && !superClass.equals(cls))
					{
						classList.add(cls);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return classList;
	}
	
	//TODO TCF ����Ԥ����ע���ע��
	public static Set<Class<?>> loadClassByAnnotationExample()
	{
		Set<Class<?>> classList=new HashSet<Class<?>>();
		
		classList.addAll(loadClassByAnnotation(KidComponent.class));
		classList.addAll(loadClassByAnnotation(KidRepository.class));
		classList.addAll(loadClassByAnnotation(KidService.class));
		classList.addAll(loadClassByAnnotation(KidController.class));
		classList.addAll(loadClassByAnnotation(KidAspect.class));
		
		return classList;
	}
	
	//TODO TCF ���ؿ������������
	public static void loadCoreClass()
	{
		//TODO TCF ��Ҫ���ص���
		List<Class<?>> classList=new ArrayList<Class<?>>();
		
		classList.add(ClassHelper.class);
		classList.add(BeanHelper.class);
		classList.add(AopHelper.class);
		classList.add(IocHelper.class);
		classList.add(ControllerHelper.class);
		
		for(Class<?> cls:classList)
		{
			ClassUtil.loadClassByName(cls.getName());
		}
	}
}
