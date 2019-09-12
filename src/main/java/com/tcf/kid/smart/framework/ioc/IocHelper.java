package com.tcf.kid.smart.framework.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.tcf.kid.smart.framework.annotation.KidAspect;
import com.tcf.kid.smart.framework.annotation.KidController;
import com.tcf.kid.smart.framework.annotation.KidInject;
import com.tcf.kid.smart.framework.demo.DemoFirst;
import com.tcf.kid.smart.framework.demo.DemoSecond;
import com.tcf.kid.smart.framework.demo.DemoThird;
import com.tcf.kid.smart.framework.helper.BeanHelper;
import com.tcf.kid.smart.framework.model.Param;
import com.tcf.kid.smart.framework.util.ReflectUtil;

/***
 * TODO TCF IOC/DI����ע�����������
 * TODO TCF ��ʼ������IOC/DI����ע�����������ڷ����Ŀ��ʵ����Ŀ������ע������ֵ�����ڷ������Ŀ��ʵ����Ŀ�귽����ʵ������ע��
 * @author 71485
 *
 */
public class IocHelper {

	//TODO TCF ��ʼ������IOC/DI����ע������
	static
	{
		//TODO TCF ģ��IOC/DI����ע��
		Map<String,Map<String,Object>> fieldMap=new HashMap<String,Map<String,Object>>();
		
		//TODO TCF ���Բ��� FieldName:FieldValue
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("name","����");
		fieldMap.put(DemoFirst.class.getSimpleName(),paramMap);
		
		paramMap=new HashMap<String,Object>();
		paramMap.put("age",19);
		fieldMap.put(DemoSecond.class.getSimpleName(),paramMap);
		
		paramMap=new HashMap<String,Object>();
		paramMap.put("demoFirst",new DemoFirst());
		paramMap.put("demoSecond",new DemoSecond());
		fieldMap.put(DemoThird.class.getSimpleName(),paramMap);
		
		//TODO TCF ����IOC/DI����ע��������ʵ������ע��
		init(fieldMap);
	}
	
	//TODO TCF ����IOC/DI����ע��������ʵ������ע��
	public static void init(Map<String,Map<String,Object>> fieldMap)
	{
		try
		{
			//TODO TCF �Ѿ����ص�Bean�����Bean���ʵ��֮���ӳ���ϵMap
			Map<Class<?>,Object> beanMap=BeanHelper.BEAN_MAP;
			
			if(beanMap!=null)
			{
				for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet())
				{
					//TODO TCF Bean�������
					Class<?> targetClass=beanEntry.getKey();
					
					//TODO TCF Bean���ʵ��
					Object targetInstance=beanEntry.getValue();
					
					if(targetClass!=null && targetInstance!=null)
					{
						//TODO TCF ����AOP�����Ѿ����ص�������
						if(targetClass.isAnnotationPresent(KidAspect.class))
						{
							continue;
						}
						
						//TODO TCF Bean��������ж������������
						Field[] fields=targetClass.getDeclaredFields();
						
						if(fields!=null && fields.length>0)
						{
							for(Field field:fields)
							{
								//TODO TCF ������Ҫע�������
								if(injectFieldFilter(field))
								{
									//TODO TCF ������
									String fieldName=field.getName();
									
									if(StringUtils.isNotEmpty(fieldName))
									{
										//TODO TCF ��ʼIOC/DI����ע��
										if(fieldMap!=null)
										{
											for(Map.Entry<String,Map<String,Object>> fieldEntry:fieldMap.entrySet())
											{
												//TODO TCF ��Ҫע�������������simpleName
												String classSimpleName=fieldEntry.getKey();
												
												//TODO TCF ��Ҫע�������������simpleName����͵�ǰ���ص���simpleName��ͬ����ע��
												if(StringUtils.isNotEmpty(classSimpleName) && classSimpleName.equals(targetClass.getSimpleName()))
												{
													//TODO TCF ���Բ��� FieldName:FieldValue
													Map<String,Object> paramMap=fieldEntry.getValue();
													
													if(paramMap!=null)
													{
														for(Map.Entry<String,Object> paramEntry:paramMap.entrySet())
														{
															//TODO TCF ��Ҫע���������
															String injectFieldName=paramEntry.getKey();
															
															//TODO TCF ��Ҫע�������ֵ
															Object injectFieldValue=paramEntry.getValue();
															
															//TODO TCF ��Ҫע�������������͵�ǰ���ص���������ͬ����ע������ֵ
															if(StringUtils.isNotEmpty(injectFieldName) && injectFieldName.equals(fieldName))
															{
																//TODO TCF ���ڷ����Ŀ��ʵ����Ŀ������ע������ֵ
																ReflectUtil.setField(targetInstance,field,injectFieldValue);
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						
						//TODO TCF ������������ȥ�����������Ŀ����ֻ����AOP��̬����֯����ǿ������ִ��Ч��
						if(targetClass.isAnnotationPresent(KidController.class))
						{
							//TODO TCF ���ڷ������Ŀ��ʵ����Ŀ�귽��
							Method[] methods=targetClass.getDeclaredMethods();
							
							if(methods!=null && methods.length>0)
							{
								for(Method method:methods)
								{
									//TODO TCF ������Ҫ���õķ���
									if(invokeMethodFilter(method))
									{
										//TODO TCF ��������
										Object[] methodParams=new Object[1];
										methodParams[0]=new Param();
										
										//TODO TCF ...�Զ��巽������
										
										//TODO TCF ���ڷ������Ŀ��ʵ����Ŀ�귽��
										ReflectUtil.invokeMethod(targetInstance,method,methodParams);
									}
								}
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
	}
	
	//TODO TCF ������Ҫע������ֵ������
	public static boolean injectFieldFilter(Field field)
	{
		boolean result=false;
		
		//TODO TCF ...�Զ����������������Ҫע������ֵ������
		//TODO TCF ֻע��ʹ��KidInjectע���ע������
		if(field.isAnnotationPresent(KidInject.class))
		{
			result=true;
		}
		else
		{
			//TODO TCF ...�Զ���������������������Ҫע������ֵ������
		}
		
		return result;
	}
	
	//TODO TCF ������Ҫ���õķ���
	public static boolean invokeMethodFilter(Method method)
	{
		boolean result=true;
		
		//TODO TCF ...�Զ����������������Ҫ���õķ���
		
		return result;
	}
}
