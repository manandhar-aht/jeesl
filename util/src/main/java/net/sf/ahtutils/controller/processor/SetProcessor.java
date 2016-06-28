package net.sf.ahtutils.controller.processor;

import java.util.List;

import org.apache.commons.collections.ListUtils;

public class SetProcessor
{
	public static <T extends Object> List<T> and(List<T> a, List<T> b)
	{
		return ListUtils.intersection(a,b);
	}
	
	public static <T extends Object> List<T> or(List<T> a, List<T> b)
	{
		return ListUtils.union(ListUtils.subtract(a,b),b);
	}
	
	public static <T extends Object> List<T> query(String query, List<T>... list)
	{
		return null;
	}
}