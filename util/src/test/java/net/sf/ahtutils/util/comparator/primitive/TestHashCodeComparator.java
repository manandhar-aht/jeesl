package net.sf.ahtutils.util.comparator.primitive;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;


public class TestHashCodeComparator
{
	HashCodeComparator hcc;

	@Before
	public void init()
	{
		File f = new File("..\\util\\src\\test\\java\\net\\sf\\ahtutils\\util\\comparator\\primitive");
		hcc = new HashCodeComparator(f);
		hcc.searchForHashCodeBuilder();
	}

	@Test
	public void test()
	{
		int expectedSize = 2;
		Map<String, String> testList = hcc.compareCode();
		int actual = testList.size();
		Assert.assertEquals(expectedSize, actual);
		for(Map.Entry<String, String> entry : testList.entrySet()){System.out.println(entry);}
		System.out.println(actual);
	}

	public int hashCode(){return new HashCodeBuilder(17, 43).toHashCode();}
	public static void main(String[] args)
	{
		TestHashCodeComparator test = new TestHashCodeComparator();
		test.init();test.test();
	}
}
