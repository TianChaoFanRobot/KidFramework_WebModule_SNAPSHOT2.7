package com.tcf.kid.smart.framework.model;

import java.util.Map;

/***
 * TODO TCF ��װ�������
 * @author 71485
 *
 */
public class Param {

	//TODO TCF ������� ParameterName:ParameterValue
	private Map<String,Object> paramMap;
	
	public Map<String, Object> getParamMap() {
		return paramMap;
	}
	
	//TODO TCF ����ע��
	public Param(Map<String,Object> paramMap)
	{
		this.paramMap=paramMap;
	}
	
	//TODO TCF Ĭ���޲ι���
	public Param()
	{
		
	}
	
}
