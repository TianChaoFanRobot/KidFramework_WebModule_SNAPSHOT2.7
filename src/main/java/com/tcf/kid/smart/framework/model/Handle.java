package com.tcf.kid.smart.framework.model;

import java.lang.reflect.Method;

/***
 * TODO TCF ����������Ϣ
 * @author 71485
 *
 */
public class Handle {

	//TODO TCF �������������
	private Class<?> handleController;
	
	//TODO TCF �������󷽷�
	private Method handleMethod;
	
	public Class<?> getHandleController() {
		return handleController;
	}
	public Method getHandleMethod() {
		return handleMethod;
	}
	
	//TODO TCF ����ע��
	public Handle(Class<?> handleController,Method handleMethod)
	{
		this.handleController=handleController;
		this.handleMethod=handleMethod;
	}
	
	//TODO TCF Ĭ���޲ι���
	public Handle()
	{
		
	}
}
