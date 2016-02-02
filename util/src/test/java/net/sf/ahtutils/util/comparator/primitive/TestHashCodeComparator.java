package net.sf.ahtutils.util.comparator.primitive;

import java.io.File;
import java.util.Map;


public class TestHashCodeComparator
{
	public static void main(String[] args)
	{
		File f = new File("..\\meis\\entities");
		HashCodeComparator hcc = new HashCodeComparator(f);
		hcc.searchForHashCodeBuilder();
		Map<String, String> testList = hcc.compareCode();
		for(Map.Entry<String, String> entry : testList.entrySet())
			System.out.println(entry);
		System.out.println(testList.size());
	}
}
