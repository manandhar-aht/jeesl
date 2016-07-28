package org.jeesl.controller.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.ejb.status.Status;
import net.sf.ahtutils.test.AbstractAhtUtilsTest;

public class TestIntersectionProcessor extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestIntersectionProcessor.class);
	
	private static EjbStatusFactory<Status,Lang,Description> factory;
	
	private List<String> a,b,c;
	private List<List<String>> ab,abc;
	
	private List<Status> x,y;
	private List<List<Status>> xy;
	
	private List<Integer> k,m;
	private List<List<Integer>> km;
	
	@BeforeClass public static void initClass()
	{
		factory = EjbStatusFactory.createFactory(Status.class,Lang.class,Description.class);
	}
	
	@Before public void init()
	{		
		a = Arrays.asList("a b c d e f"      .split(" "));
		b = Arrays.asList(      "d e f g".split(" "));
		c = Arrays.asList("b d e".split(" "));
		
		ab = new ArrayList<List<String>>();ab.add(a);ab.add(b);
		abc = new ArrayList<List<String>>();abc.add(a);abc.add(b);abc.add(c);
		
		factory = EjbStatusFactory.createFactory(Status.class,Lang.class,Description.class); //within initClass() i got an NPE on .id(long l)
		x = new ArrayList<Status>();
		x.add(factory.id(1));x.add(factory.id(2));x.add(factory.id(3));
		y = new ArrayList<Status>();
		y.add(factory.id(3));x.add(factory.id(4));x.add(factory.id(5));
		xy = new ArrayList<List<Status>>();xy.add(x);xy.add(y);

		k = Arrays.asList(12,19,205,2401,325871);
		m = Arrays.asList(45,365,12,4787,15479912);
		km = new ArrayList<List<Integer>>();km.add(k);km.add(m);
	}
	
	@Test public void pre()
    {	
		Assert.assertEquals(6, a.size());
		Assert.assertEquals(4, b.size());
    }
 
	@Test public void basicAnd()
    {	
		List<String> expecteds = Arrays.asList("d e f".split(" "));
    	List<String> actuals = IntersectionProcessor.and(a,b);
    	Assert.assertArrayEquals(expecteds.toArray(new String[expecteds.size()]), actuals.toArray(new String[actuals.size()]));
    }
    
    @Test public void basicOr()
    {	
    	List<String> result = IntersectionProcessor.or(a,b);
    	Assert.assertEquals(7, result.size());
    }
    
    @Test public void stringSingleWorkaround()
    {
		List<String> actualsA = IntersectionProcessor.query("a AND a",ab);
		List<String> actualsB = IntersectionProcessor.query("b AND b",ab);
		Assert.assertArrayEquals(a.toArray(new String[a.size()]), actualsA.toArray(new String[actualsA.size()]));
		Assert.assertArrayEquals(b.toArray(new String[b.size()]), actualsB.toArray(new String[actualsB.size()]));
    }
    
    @Ignore @Test public void stringSingle()
    {
		List<String> actualsA = IntersectionProcessor.query("a",ab);
		List<String> actualsB = IntersectionProcessor.query("b",ab);
		Assert.assertArrayEquals(a.toArray(new String[a.size()]), actualsA.toArray(new String[actualsA.size()]));
		Assert.assertArrayEquals(b.toArray(new String[b.size()]), actualsB.toArray(new String[actualsB.size()]));
    }
    
    @Test public void stringAnd()
    {
    	List<String> expecteds = Arrays.asList("d e f".split(" "));
		List<String> actuals = IntersectionProcessor.query("a AND b",ab);
		Assert.assertArrayEquals(expecteds.toArray(new String[expecteds.size()]), actuals.toArray(new String[actuals.size()]));
    }
    
    @Test public void stirngOr()
    {
    	List<String> expecteds = Arrays.asList("a b c d e f g".split(" "));
		List<String> actuals = IntersectionProcessor.query("a OR b",ab);
		Assert.assertArrayEquals(expecteds.toArray(new String[expecteds.size()]), actuals.toArray(new String[actuals.size()]));
    }
	
    @Test public void integerAnd()
    {
		List<Integer> result = IntersectionProcessor.query("a && b",km);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(new Integer(12), result.get(0));
    }

	@Test public void simpleCombination()
	{
		List<String> expecteds = Arrays.asList("f b d e".split(" "));
		List<String> actuals = IntersectionProcessor.query("a AND (b OR c)",abc);
		Assert.assertArrayEquals(expecteds.toArray(new String[expecteds.size()]), actuals.toArray(new String[actuals.size()]));
	}
	
	@Ignore @Test public void plain3()
	{		
		List<String> expecteds = Arrays.asList("d e".split(" "));
		List<String> actuals = IntersectionProcessor.query("a AND b AND c",abc);
		Assert.assertArrayEquals(expecteds.toArray(new String[expecteds.size()]), actuals.toArray(new String[actuals.size()]));
	}
	
	@Test public void plain3Workaround()
	{
		List<String> expecteds = Arrays.asList("d e".split(" "));
		List<String> actuals = IntersectionProcessor.query("a AND (b AND c)",abc);
		Assert.assertArrayEquals(expecteds.toArray(new String[expecteds.size()]), actuals.toArray(new String[actuals.size()]));
	}
	
	@Test public void idAnd()
    {
		List<Status> expected = new ArrayList<Status>();
		expected.add(factory.id(3));
		
		List<Status> actual = IntersectionProcessor.query("a AND b", xy);
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(expected.get(0), actual.get(0));
    }
}