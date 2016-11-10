package org.jeesl.util.query.xpath.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class TestReportXpathSerializeable extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestReportXpathSerializeable.class);
    
	private List<Serializable> list;
	private Langs langs;
	
	@Before
	public void init()
	{
		list = new ArrayList<Serializable>();
		langs = XmlLangsFactory.build();
	}
	
	@Test
	public void langs() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		list.add("a");
		list.add(langs);
		list.add("b");
		
		Langs actual = ReportXpath.getFirstLangs(list);
		Assert.assertEquals(langs,actual);
	}
}