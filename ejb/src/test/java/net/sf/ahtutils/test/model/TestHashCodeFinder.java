package net.sf.ahtutils.test.model;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.ahtutils.model.DuplicateHashCodeFinder;

public class TestHashCodeFinder
{
	DuplicateHashCodeFinder hcc;

	@Before
	public void init()
	{
		File f = new File("..\\ejb\\src\\test\\java\\net\\sf\\ahtutils\\test\\model");
		hcc = new DuplicateHashCodeFinder(f);
		hcc.searchForHashCodeBuilder();
	}

	@Test @Ignore
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
		TestHashCodeFinder test = new TestHashCodeFinder();
		test.init();test.test();
	}
}
