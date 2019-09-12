package com.tcf.kid.smart.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.tcf.kid.smart.framework.annotation.KidAction;
import com.tcf.kid.smart.framework.annotation.KidController;
import com.tcf.kid.smart.framework.model.Handle;
import com.tcf.kid.smart.framework.model.Request;

/***
 * TODO TCF MVC�������������������
 * TODO TCF ��ʼ����������ʹ���֮���ӳ���ϵMap(Request -> Handle)
 * @author 71485
 *
 */
public class ControllerHelper {

	//TODO TCF ȫ������ʹ���֮���ӳ���ϵMap(Request -> Handle)
	public static Map<Request,Handle> MAPPING_MAP=new HashMap<Request,Handle>();
	
	//TODO TCF ��ʼ����������ʹ���֮���ӳ���ϵMap(Request -> Handle)
	static
	{
		initRequestAndHandle();
	}
	
	//TODO TCF ��������ʹ���֮���ӳ���ϵMap(Request -> Handle)
	public static void initRequestAndHandle()
	{
		try
		{
			//TODO TCF ����ʹ��KidControllerע���ע��MVC�������������
			Set<Class<?>> controllerClassList=ClassHelper.loadClassByAnnotation(KidController.class);
			
			if(controllerClassList!=null && controllerClassList.size()>0)
			{
				for(Class<?> controllerClass:controllerClassList)
				{
					//TODO TCF �������ж�������з���
					Method[] methods=controllerClass.getDeclaredMethods();
					
					if(methods!=null && methods.length>0)
					{
						for(Method method:methods)
						{
							//TODO TCF ֻ����ʹ��KidActionע���ע�Ĵ������󷽷�
							if(method.isAnnotationPresent(KidAction.class))
							{
								//TODO TCF ��ȡ��ע�ڿ������������󷽷��Ϸ���KidActionע��
								KidAction kidAction=method.getAnnotation(KidAction.class);
								
								if(kidAction!=null)
								{
									//TODO TCF ��������·��
									String requestUrl=kidAction.url();
									
									//TODO TCF ��������ʽ
									String requestMethod=kidAction.method()!=null?kidAction.method().toLowerCase():"";
									
									//TODO TCF ��������ʹ���֮���ӳ���ϵMap(Request -> Handle)
									Request request=new Request(requestUrl,requestMethod);
									
									Handle handle=new Handle(controllerClass,method);
									
									MAPPING_MAP.put(request,handle);
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
	
	//TODO TCF ��������·��������ʽ��ȡ����������Ϣ
	public static Handle getHandleByRequest(String requestUrl,String requestMethod)
	{
		Handle result=null;
		
		try
		{
			if(MAPPING_MAP!=null)
			{
				for(Map.Entry<Request,Handle> mappingEntry:MAPPING_MAP.entrySet())
				{
					//TODO TCF ����
					Request request=mappingEntry.getKey();
					
					//TODO TCF ����������Ϣ
					Handle handle=mappingEntry.getValue();
					
					if(request!=null)
					{
						if(StringUtils.isNotEmpty(request.getRequestUrl()) && StringUtils.isNotEmpty(request.getRequestMethod())
						&& request.getRequestUrl().equals(requestUrl) && request.getRequestMethod().equals(requestMethod))
						{
							result=handle;
							break;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}
