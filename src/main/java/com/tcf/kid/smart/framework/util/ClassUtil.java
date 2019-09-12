package com.tcf.kid.smart.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

import com.tcf.kid.smart.framework.common.Const;

/***
 * TODO TCF �������������
 * TODO TCF ��ȡ�������
 * TODO TCF ��������������
 * TODO TCF һ�μ���һ���ൽ��ʱ����
 * TODO TCF һ�μ��ض���ൽ��ʱ����(�ݹ�ɨ��)
 * TODO TCF ���ػ�׼�������Ӱ��е�������
 * @author 71485
 *
 */
public class ClassUtil {

	//TODO TCF ��ȡ�������
	public static ClassLoader getClassLoader()
	{
		return Thread.currentThread().getContextClassLoader();
	}
	
	//TODO TCF ��������������
	public static Class<?> loadClassByName(String className)
	{
		Class<?> cls=null;
		
		try
		{
			cls=Class.forName(className,true,getClassLoader());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return cls;
	}
	
	//TODO TCF һ�μ���һ���ൽ��ʱ����
	public static void addClass(Set<Class<?>> classList,String className)
	{
		try
		{
			Class<?> cls=loadClassByName(className);
			classList.add(cls);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO TCF һ�μ��ض���ൽ��ʱ����(�ݹ�ɨ��)
	public static void addClassReceive(Set<Class<?>> classList,String packagePath,String packageName)
	{
		try
		{
			//TODO TCF ɨ��Ļ�׼��Ŀ¼
			File dirFile=new File(packagePath);
			
			if(dirFile!=null)
			{
				//TODO TCF ��׼���µ������ļ���Ŀ¼
				File[] files=dirFile.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File file) 
					{
						return (file.isFile() && file.getName().endsWith(".class")) || (file.isDirectory());
					}
					
				});
				
				if(files!=null && files.length>0)
				{
					for(File file:files)
					{
						//TODO TCF ��ȡ�����ļ�����Ŀ¼��
						String fileName=file.getName();
						
						if(StringUtils.isNotEmpty(fileName))
						{
							if(file.isFile())
							{
								//TODO TCF ��ȡ�������ļ�
								//TODO TCF ��ȡ����ֱ�Ӽ�����
								String className=fileName.substring(0,fileName.lastIndexOf("."));
								
								if(StringUtils.isNotEmpty(packageName))
								{
									className=packageName+"."+className;
								}
								
								addClass(classList,className);
							}
							else
							{
								//TODO TCF ��ȡ������Ŀ¼
								//TODO TCF ��ȡ�Ӱ�·�����Ӱ����ݹ���ذ������Ӱ��е�������
								String subPackagePath=fileName;
								
								if(StringUtils.isNotEmpty(packagePath))
								{
									subPackagePath=packagePath+"/"+subPackagePath;
								}
								
								String subPackageName=fileName;
								
								if(StringUtils.isNotEmpty(packageName))
								{
									subPackageName=packageName+"."+subPackageName;
								}
								
								addClassReceive(classList,subPackagePath,subPackageName);
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO TCF ���ػ�׼�������Ӱ��е�������
	public static Set<Class<?>> loadClassByPackageName(String packageName)
	{
		Set<Class<?>> classList=new HashSet<Class<?>>();
		
		try
		{
			Enumeration<URL> urls=getClassLoader().getResources(packageName.replace(".","/"));
			
			if(urls!=null)
			{
				while(urls.hasMoreElements())
				{
					URL url=urls.nextElement();
					
					if(url!=null)
					{
						//TODO TCF URLЭ������
						String protocol=url.getProtocol();
						
						if(StringUtils.isNotEmpty(protocol))
						{
							if(protocol.equals(Const.URL_PROTOCOLS.FILE))
							{
								//TODO TCF URL�ļ�Э��File
								//TODO TCF ��ȡ��·���ݹ���ذ������Ӱ��е�������
								String packagePath=url.getPath().replace("%20"," ");
								addClassReceive(classList,packagePath,packageName);
							}
							else if (protocol.equals(Const.URL_PROTOCOLS.JAR))
							{
								//TODO TCF URLĿ¼Э��Jar������Jar��
								JarURLConnection jarURLConnection=(JarURLConnection)url.openConnection();
								
								if(jarURLConnection!=null)
								{
									JarFile jarFile=jarURLConnection.getJarFile();
									
									if(jarFile!=null)
									{
										Enumeration<JarEntry> entries=jarFile.entries();
										
										if(entries!=null)
										{
											while(entries.hasMoreElements())
											{
												JarEntry jarEntry=entries.nextElement();
												
												//TODO TCF ��ȡ�������ļ���
												if(jarEntry!=null)
												{
													String entryName=jarEntry.getName();
													
													if(StringUtils.isNotEmpty(entryName) && entryName.endsWith(".class"))
													{
														String className=entryName.substring(0,entryName.lastIndexOf("."));
														addClass(classList,className);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return classList;
	}
}
