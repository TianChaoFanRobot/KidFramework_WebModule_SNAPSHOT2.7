package com.tcf.kid.smart.framework.demo;

import com.tcf.kid.smart.framework.annotation.KidInject;
import com.tcf.kid.smart.framework.annotation.KidRepository;

@KidRepository
public class DemoSecond {

	//TODO TCF ����
	@KidInject
	private Integer age;
	
	public void showAge()
	{
		System.out.println("===="+DemoSecond.class.getSimpleName()+"��====���䣺"+age+"====");
	}
	
}
