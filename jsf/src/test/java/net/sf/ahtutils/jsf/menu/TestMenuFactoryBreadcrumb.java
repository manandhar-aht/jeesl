package net.sf.ahtutils.jsf.menu;

import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;

import org.jeesl.jsf.menu.MenuXmlBuilder;
import org.jeesl.model.xml.system.navigation.Breadcrumb;
import org.jeesl.model.xml.system.navigation.MenuItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMenuFactoryBreadcrumb extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuFactoryBreadcrumb.class);
		
	private MenuXmlBuilder mf;
	
	
	@Before
	public void init()
	{
		TestMenuFactory tMf = TestMenuFactory.factory();
		mf = tMf.getMf();
	}
	
	@Test
	public void withRoutRootLevel1()
	{
		Breadcrumb actual = mf.breadcrumb(false,TestMenuFactory.code1);
		Assert.assertEquals(1, actual.getMenuItem().size());
	}
	
	@Test
	public void name()
	{
		mf.build(TestMenuFactory.code2);
		Breadcrumb actual = mf.breadcrumb(false,TestMenuFactory.code2);
		MenuItem mi = actual.getMenuItem().get(0);
		Assert.assertNotNull(mi.getName());
	}
	
	@Test
	public void withRootLevel1()
	{
		Breadcrumb actual = mf.breadcrumb(true,TestMenuFactory.code1);
		Assert.assertEquals(2, actual.getMenuItem().size());
	}
	
	@Test
	public void withoutRootLevel2()
	{
		Breadcrumb actual = mf.breadcrumb(false,TestMenuFactory.code2);
		Assert.assertEquals(2, actual.getMenuItem().size());
	}
	
	@Test
	public void withRootLevel2()
	{
		Breadcrumb actual = mf.breadcrumb(true,TestMenuFactory.code2);
		Assert.assertEquals(3, actual.getMenuItem().size());
	}
}