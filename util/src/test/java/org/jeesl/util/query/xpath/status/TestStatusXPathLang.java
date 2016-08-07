package org.jeesl.util.query.xpath.status;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.TestXmlLang;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class TestStatusXPathLang extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestStatusXPathLang.class);
    
	private Langs langs;
	private Lang l1,l2,l3;
	
	@Before
	public void iniDbseed()
	{
		langs = TestXmlLangs.create(false);

		l1 = TestXmlLang.create(false);l1.setKey("ok");langs.getLang().add(l1);
		l2 = TestXmlLang.create(false);l2.setKey("multi");langs.getLang().add(l2);
		l3 = TestXmlLang.create(false);l3.setKey("multi");langs.getLang().add(l3);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Lang actual = StatusXpath.getLang(langs, l1.getKey());
	    Assert.assertEquals(l1,actual);
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		StatusXpath.getLang(langs, "-1");
	}
	
	 @Test(expected=ExlpXpathNotUniqueException.class)
	 public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	 {
		 StatusXpath.getLang(langs, l2.getKey());
	 }
}