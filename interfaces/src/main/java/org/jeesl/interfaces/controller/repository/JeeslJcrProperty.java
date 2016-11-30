package org.jeesl.interfaces.controller.repository;

public interface JeeslJcrProperty
{
	public static String jcrContent = "jcr:content";
	public static String jcrData = "jcr:data";
	public static String jcrEncoding = "jcr:encoding";
	
	public static String ntFile = "nt:file";
	public static String ntResource = "nt:resource";
	
	public static String jeeslFileCategory = "jeesl:category";
	
	public static String fileName = "fileName";
	public static String fileDate = "fileDate";

	public static String fileType = "fileType";
	public enum FileTypes {doc,xls,pdf};
}