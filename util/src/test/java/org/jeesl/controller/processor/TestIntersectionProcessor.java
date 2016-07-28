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
	
	private static EjbStatusFactory<Status,Lang,Description> ef;
	
	private List<String> a,b;
	private List<String> e,f,g,h;
	private List<Status> x,y;
	private List<Integer> k,m;
	
	@BeforeClass public static void initClass()
	{
		ef = EjbStatusFactory.createFactory(Status.class,Lang.class,Description.class);
	}
	
	@Before public void init()
	{		
		a = Arrays.asList("a b c d"      .split(" "));
		b = Arrays.asList(      "d e f g".split(" "));
		 
		e = Arrays.asList("a byte c dummd1deldumm 141447483187945187"      .split(" "));
		f = Arrays.asList(      "dummd1deldumm e fit g".split(" "));
		g = Arrays.asList(	"byte 141447483187945187 j k".split(" "));
		h = Arrays.asList(	"byte l m nkd 141447483187945187".split(" "));
		//a AND (b OR (c AND d))

		ef = EjbStatusFactory.createFactory(Status.class,Lang.class,Description.class); //within initClass() i got an NPE on .id(long l)
		x = new ArrayList<Status>();
		x.add(ef.id(1));x.add(ef.id(2));x.add(ef.id(3));
		y = new ArrayList<Status>();
		y.add(ef.id(3));x.add(ef.id(4));x.add(ef.id(5));

		k = Arrays.asList(12,19,205,2401,325871);
		m = Arrays.asList(45,365,12,4787,15479912);
	}
	
	@Test public void pre()
    {	
		Assert.assertEquals(4, a.size());
		Assert.assertEquals(4, b.size());
		
    	Assert.assertEquals(5, e.size());
    	Assert.assertEquals(4, f.size());
		Assert.assertEquals(4, g.size());
		Assert.assertEquals(5, h.size());
    }
 
	@Test public void and()
    {	
    	List<String> result = IntersectionProcessor.and(a,b);
    	Assert.assertEquals(1, result.size());
    	Assert.assertEquals("d", result.get(0));
    }
    
    @Test
    public void or()
    {	
    	List<String> result = IntersectionProcessor.or(a,b);
    	Assert.assertEquals(7, result.size());
    }
	
    @Test public void simpleAnd()
    {
		List<Integer> result = IntersectionProcessor.query("a && b",k,m);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(new Integer(12), result.get(0));
    }
    
    @Test
    public void simpleOr()
    {
		List<String> result = IntersectionProcessor.query("a || b",e,f);
		System.out.println(result.toString());
		Assert.assertEquals(8, result.size());
		Assert.assertEquals("[a, byte, c, 141447483187945187, dummd1deldumm, e, fit, g]", result.toString());
    }

	@Test public void simpleCombination()
	{
		List<String> result = IntersectionProcessor.query("a AND (b OR (c && d))",e,f,g,h);
		System.out.println(result.toString());
		Assert.assertEquals(3, result.size());
		Assert.assertEquals("[dummd1deldumm, byte, 141447483187945187]", result.toString());
	}
	
	@Test @Ignore public void idAnd()
    {
		List<Status> expected = new ArrayList<Status>();
		expected.add(ef.id(3));
		
		List<Status> actual = IntersectionProcessor.query("a AND b", x, y);
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(expected.get(0), actual.get(0));
    }
}