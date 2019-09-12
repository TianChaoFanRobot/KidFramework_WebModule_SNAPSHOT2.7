package com.tcf.kid.smart.framework.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.tcf.kid.smart.framework.common.Const;
import com.tcf.kid.smart.framework.helper.BeanHelper;
import com.tcf.kid.smart.framework.helper.ClassHelper;
import com.tcf.kid.smart.framework.helper.ControllerHelper;
import com.tcf.kid.smart.framework.model.Data;
import com.tcf.kid.smart.framework.model.Handle;
import com.tcf.kid.smart.framework.model.Param;
import com.tcf.kid.smart.framework.model.View;
import com.tcf.kid.smart.framework.util.CodecUtil;
import com.tcf.kid.smart.framework.util.JsonUtil;
import com.tcf.kid.smart.framework.util.PropertiesUtil;
import com.tcf.kid.smart.framework.util.ReflectUtil;
import com.tcf.kid.smart.framework.util.StreamUtil;

/***
 * TODO TCF Web��ܺ��Ŀ����������õ�Ч��Spring MVC���Ŀ�����DispatcherServlet
 * @author 71485
 *
 */
@WebServlet
public class KidDispatcherServlet extends HttpServlet{

	@Override
	public void init(ServletConfig servletConfig) throws ServletException 
	{
	    //TODO TCF ����Web�������������
		ClassHelper.loadCoreClass();
		
		//TODO TCF ע��Servlet��Դ����
		ServletContext servletContext=servletConfig.getServletContext();
		
		//TODO TCF JSP��ͼ��Դ
		ServletRegistration viewServletRegistration=servletContext.getServletRegistration(Const.SERVLET_REGISTRATION_TYPES.JSP);
		viewServletRegistration.addMapping(PropertiesUtil.loadPropertiesFile(Const.PROPERTIES_FILES.BASE_PROPERTIES).getProperty(Const.BASE_PROPERTIES_KEYS.VIEW_RESOURCE));
		
		//TODO TCF Ĭ��������̬��Դ
		ServletRegistration defaultServletRegistration=servletContext.getServletRegistration(Const.SERVLET_REGISTRATION_TYPES.DEFAULT);
		defaultServletRegistration.addMapping(PropertiesUtil.loadPropertiesFile(Const.PROPERTIES_FILES.BASE_PROPERTIES).getProperty(Const.BASE_PROPERTIES_KEYS.STATIC_RESOURCE));
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//TODO TCF ����·��
		String requestUrl=request.getServletPath();
		
		//TODO TCF ����ʽ
		String requestMethod=request.getMethod()!=null?request.getMethod().toLowerCase():"";
		
		if(StringUtils.isNotEmpty(requestUrl) && StringUtils.isNotEmpty(requestMethod))
		{
			//TODO TCF ��������·��������ʽ��ȡ����������Ϣ
			Handle handle=ControllerHelper.getHandleByRequest(requestUrl,requestMethod);
			
			if(handle!=null)
			{
				//TODO TCF �������������
				Class<?> handleController=handle.getHandleController();
				
				//TODO TCF �������󷽷�
				Method handleMethod=handle.getHandleMethod();
				
				if(handleController!=null && handleMethod!=null)
				{
					//TODO TCF �������������ʵ��
					Object handleControllerInstance=BeanHelper.getBeanInstance(handleController);
					
					//TODO TCF ��̨���յ����������������
					Enumeration<String> parameterNames=request.getParameterNames();
					
					if(parameterNames!=null)
					{
						//TODO TCF ��װ������� ParameterName:ParameterValue
						Map<String,Object> paramMap=new HashMap<String,Object>();
						while(parameterNames.hasMoreElements())
						{
							//TODO TCF ���������
							String parameterName=parameterNames.nextElement();
							
							//TODO TCF �������ֵ
							String parameterValue=request.getParameter(parameterName);
							
							paramMap.put(parameterName,parameterValue);
						}
						
						//TODO TCF ��װģ�Ͳ���
						Param param=new Param(paramMap);
						
						//TODO TCF �����������е��������������´�������������
						String requestParameters=CodecUtil.encodeText(StreamUtil.readInputStream(request.getInputStream()));
						
						if(StringUtils.isNotEmpty(requestParameters))
						{
							String[] parameters=requestParameters.split("&");
							
							if(parameters!=null && parameters.length>0)
							{
								for(String parameter:parameters)
								{
									String[] paramPairs=parameter.split("=");
									
									if(paramPairs!=null && paramPairs.length==2)
									{
										//TODO TCF ������
										String paramName=paramPairs[0];
										
										//TODO TCF ����ֵ
										String paramValue=paramPairs[1];
										
										//TODO TCF ���´�������������(requestScope)
										request.setAttribute(paramName,paramValue);
									}
								}
							}
						}
						
						//TODO TCF ���ڷ�����ÿ������������󷽷���������
						Object invokeResult=ReflectUtil.invokeMethod(handleControllerInstance,handleMethod,param);
						
						//TODO TCF ����������������󷽷����ص���Ӧ��Ϣ
						if(invokeResult!=null)
						{
							if(invokeResult instanceof View)
							{
								//TODO TCF ���ص�����ͼ������ʽ����
								View view=(View)invokeResult;
								
								if(view!=null)
								{
									//TODO TCF ��ͼ����·��
									String viewPath=view.getViewPath();
									
									//TODO TCF �ж���ת�������ض���
									if(StringUtils.isNotEmpty(viewPath))
									{
										if(viewPath.startsWith("/"))
										{
											//TODO TCF �ض���
											response.sendRedirect(
													    PropertiesUtil.loadPropertiesFile(Const.PROPERTIES_FILES.BASE_PROPERTIES)
													    .getProperty(Const.BASE_PROPERTIES_KEYS.VIEW_RESOURCE)
													    +viewPath
													);
										}
										else
										{
											//TODO TCF ת��
											//TODO TCF �󶨵�ģ�Ͳ���
											Map<String,Object> modelParams=view.getModelParams();
											
											if(modelParams!=null)
											{
												for(Map.Entry<String,Object> modelParamEntry:modelParams.entrySet())
												{
													//TODO TCF ģ�Ͳ�����
													String modelParamName=modelParamEntry.getKey();
													
													//TODO TCF ģ�Ͳ���ֵ
													Object modelParamValue=modelParamEntry.getValue();
													
													//TODO TCF ��������������(requestScope)
													request.setAttribute(modelParamName,modelParamValue);
												}
											}
											
											//TODO TCF ҳ��ת��
											request.getRequestDispatcher(
													    PropertiesUtil.loadPropertiesFile(Const.PROPERTIES_FILES.BASE_PROPERTIES)
													    .getProperty(Const.BASE_PROPERTIES_KEYS.VIEW_RESOURCE)
													    +viewPath
													).forward(request,response);
										}
									}
								}
							}
							else if (invokeResult instanceof Data) 
							{
								//TODO TCF ֱ�ӷ��ص���Ӧ���ݣ�JSON���л���ֱ��д���������������Ӧ
								Data data=(Data)invokeResult;
								
								if(data!=null)
								{
									//TODO TCF ���������������ص���Ӧ����
									Object responseData=data.getData();
									
									//TODO TCF JSON���л�
									String jsonString=JsonUtil.pojoToJson(responseData);
									
									//TODO TCF ֱ��д���������������Ӧ
									response.setContentType("application/json");
									response.setCharacterEncoding("UTF-8");
									PrintWriter writer=response.getWriter();
									
									//TODO TCF д�������
									writer.write(jsonString);
									
									//TODO TCF ��ջ��������ر������
									writer.flush();
									writer.close();
								}
							}
						}
					}
				}
			}
		}
	}
}
