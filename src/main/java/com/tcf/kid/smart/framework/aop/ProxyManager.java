package com.tcf.kid.smart.framework.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/***
 * TODO TCF AOP��̬�����������ݴ���Ŀ��ʹ���Ŀ������д���ʵ����������cglib��̬����ʵ������������ʵ��ִ��������˳�����ÿ������ʵ���Ĵ�����
 * @author 71485
 *
 */
public class ProxyManager {

	private static Logger logger=Logger.getLogger(ProxyManager.class.getName());
	
	//TODO TCF ���ݴ���Ŀ��ʹ���Ŀ������д���ʵ����������cglib��̬����ʵ������������ʵ��ִ��������˳�����ÿ������ʵ���Ĵ�����
    public static Object newProxyInstance(final Class<?> targetClass,final List<Proxy> proxyList)
    {
    	Object proxy=Enhancer.create(targetClass,new MethodInterceptor() {
			
			@Override
			public Object intercept(
					                   Object targetInstance, 
					                   Method targetMethod, 
					                   Object[] methodParams, 
					                   MethodProxy methodProxy
					               ) throws Throwable 
			{
				return new ProxyChain(targetClass,targetInstance,targetMethod,methodProxy,methodParams,proxyList)
						.doProxyChain();
			}
			
		});
    	
    	logger.info(proxy!=null?proxy.toString():"");
    	
    	return proxy;
    }
}
