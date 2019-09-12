package com.tcf.kid.smart.framework.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/***
 * TODO TCF ����ʵ��ִ����
 * @author 71485
 *
 */
public class ProxyChain {

	//TODO TCF ����Ŀ������
	private Class<?> targetClass;
	
	//TODO TCF ����Ŀ��ʵ��
	private Object targetInstance;
	
	//TODO TCF ����Ŀ�귽��
	private Method targetMethod;
	
	//TODO TCF ������
	private MethodProxy methodProxy;
	
	//TODO TCF ��������
	private Object[] methodParams;
	
	//TODO TCF ����ʵ��
	private List<Proxy> proxyList;
	
	//TODO TCF ȫ�־�̬��������¼��ǰ���õ��ǵڼ�������ʵ���Ĵ�����
	public static Integer executeIndex=0;
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public Object getTargetInstance() {
		return targetInstance;
	}
	public Method getTargetMethod() {
		return targetMethod;
	}
	public MethodProxy getMethodProxy() {
		return methodProxy;
	}
	public Object[] getMethodParams() {
		return methodParams;
	}
	public List<Proxy> getProxyList() {
		return proxyList;
	}
	
	//TODO TCF ����ע��
	public ProxyChain(
			    Class<?> targetClass,
			    Object targetInstance,
			    Method targetMethod,
			    MethodProxy methodProxy,
			    Object[] methodParams,
			    List<Proxy> proxyList
			)
	{
		this.targetClass=targetClass;
		this.targetInstance=targetInstance;
		this.targetMethod=targetMethod;
		this.methodProxy=methodProxy;
		this.methodParams=methodParams;
		this.proxyList=proxyList;
	}
	
	//TODO TCF ����ʵ��ִ����ִ�����壬��˳�����ÿ������ʵ���Ĵ�����
	public Object doProxyChain()
	{
		Object invokeResult=null;
		
		try
		{
			if(executeIndex<proxyList.size())
			{
				invokeResult=proxyList.get(executeIndex++).doProxy(this);
			}
			else
			{
				invokeResult=methodProxy.invokeSuper(targetInstance,methodParams);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		
		return invokeResult;
	}
}
