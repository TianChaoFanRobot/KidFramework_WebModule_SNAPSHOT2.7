package com.tcf.kid.smart.framework.aop;

/***
 * TODO TCF AOP切面基类，实现AOP动态代理接口，定义代理方法和各类增强处理方法，并在代理方法中实现各类增强处理方法动态织入
 * @author 71485
 *
 */
public class ProxyAspect implements Proxy{

	//TODO TCF 代理方法，在代理方法中织入各类增强处理方法
	@Override
	public Object doProxy(ProxyChain proxyChain) 
	{
		Object invokeResult=null;
		
		try
		{
			//TODO TCF 三层汉堡模式-动态织入增强处理方法
			before();
			
			invokeResult=proxyChain.doProxyChain();
			
			afterReturning();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return invokeResult;
	}
	
	//TODO TCF ...自定义各类增强处理方法
	//TODO TCF 前置增强
	public void before()
	{
		System.out.println("====切面基类的前置增强处理方法执行====");
	}
	
	//TODO TCF 后置增强
	public void afterReturning()
	{
		System.out.println("====切面基类的后置增强处理方法执行====");
	}
}
