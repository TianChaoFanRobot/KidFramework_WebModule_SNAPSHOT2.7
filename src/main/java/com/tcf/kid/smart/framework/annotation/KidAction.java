package com.tcf.kid.smart.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * TODO TCF ��עMVC�������������󷽷�
 * @author 71485
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KidAction {

	//TODO TCF ��������·��
	String url();
	
	//TODO TCF ��������ʽ
	String method();
	
}
