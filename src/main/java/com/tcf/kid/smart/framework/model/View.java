package com.tcf.kid.smart.framework.model;

import java.util.Map;

/***
 * TODO TCF ��װMVC�������������󷽷����ص���ͼ������ʽ��Ӧ����
 * @author 71485
 *
 */
public class View {

	//TODO TCF ��ͼ����·��
	private String viewPath;
	
	//TODO TCF �󶨵�ģ�Ͳ���
	private Map<String,Object> modelParams;
	
	public String getViewPath() {
		return viewPath;
	}
	public Map<String, Object> getModelParams() {
		return modelParams;
	}
	
	//TODO TCF ����ע��
	public View(String viewPath,Map<String,Object> modelParams)
	{
		this.viewPath=viewPath;
		this.modelParams=modelParams;
	}
	
	//TODO TCF Ĭ���޲ι���
	public View()
	{
		
	}
	
}
