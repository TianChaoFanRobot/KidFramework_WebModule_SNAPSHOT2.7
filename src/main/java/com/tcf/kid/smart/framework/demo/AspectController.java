package com.tcf.kid.smart.framework.demo;

import com.tcf.kid.smart.framework.annotation.KidAspect;
import com.tcf.kid.smart.framework.annotation.KidController;
import com.tcf.kid.smart.framework.aop.ProxyAspect;

/***
 * TODO TCF �Զ���AOP�������࣬�̳�AOP������࣬��д������ǿ����������������������doProxy��������ʵ�ָ�����ǿ�������Ķ�̬֯��
 * @author 71485
 *
 */
@KidAspect(KidController.class)
public class AspectController extends ProxyAspect{

	//TODO TCF ǰ����ǿ
	@Override
	public void before() 
	{
		super.before();
		System.out.println(this.getClass().getSimpleName()+"���ǰ����ǿ������ִ��====");
	}
	
	//TODO TCF ������ǿ
	@Override
	public void afterReturning() 
	{
		super.afterReturning();
		System.out.println(this.getClass().getSimpleName()+"��ĺ�����ǿ������ִ��====");
	}
}
