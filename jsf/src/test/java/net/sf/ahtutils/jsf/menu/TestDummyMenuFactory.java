package net.sf.ahtutils.jsf.menu;

import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.model.xml.system.navigation.Menu;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDummyMenuFactory extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestDummyMenuFactory.class);
	
	private Menu menu;
	
	@Before
	public void init()
	{
		menu = DummyMenuFactory.create();
	}
	
	@Test
	public void testInit()
	{
		JaxbUtil.warn(menu);
	}
}