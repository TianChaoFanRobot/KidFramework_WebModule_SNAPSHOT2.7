package com.tcf.kid.smart.framework.model;

/***
 * TODO TCF ��װ������Ϣ
 * @author 71485
 *
 */
public class Request {

	//TODO TCF ����·��
	private String requestUrl;
	
	//TODO TCF ����ʽ
	private String requestMethod;
	
	public String getRequestUrl() {
		return requestUrl;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	
	//TODO TCF ����ע��
	public Request(String requestUrl,String requestMethod)
	{
		this.requestUrl=requestUrl;
		this.requestMethod=requestMethod;
	}
	
	//TODO TCF Ĭ���޲ι���
	public Request()
	{
		
	}
	
}
