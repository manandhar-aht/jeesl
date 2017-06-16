package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.test.AbstractFileProcessingTest;
import net.sf.ahtutils.xml.status.Description;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.factory.xml.system.lang.XmlDescriptionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDescriptionFactory extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDescriptionFactory.class);
	
	@Before
	public void init()
	{
		
	}
	
	@Test
	public void text()
	{
		Description xml = XmlDescriptionFactory.build("test");
		JaxbUtil.trace(xml);
		Assert.assertFalse(JaxbUtil.toString(xml).contains("CDATA"));
	}
	
	@Test
	public void cdata()
	{
		Description xml = XmlDescriptionFactory.build("test XML:"+Character.toString ((char) 1));
		JaxbUtil.trace(xml);
		Assert.assertTrue(JaxbUtil.toString(xml),JaxbUtil.toString(xml).contains("CDATA"));
	}
}