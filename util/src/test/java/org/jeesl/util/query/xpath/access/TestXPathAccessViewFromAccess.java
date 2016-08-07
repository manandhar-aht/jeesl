package org.jeesl.util.query.xpath.access;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.TestXmlView;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.xpath.AccessXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class TestXPathAccessViewFromAccess extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathAccessViewFromAccess.class);
    
	private Access access;
	private View v1,v2,v3;
	
	@Before
	public void init()
	{
		access = new Access();
		
		Category c1 = new Category();c1.setViews(new Views());
		v1 = TestXmlView.create(false);v1.setCode("ok");c1.getViews().getView().add(v1);
		v2 = TestXmlView.create(false);v2.setCode("multi");c1.getViews().getView().add(v2);
		
		Category c2 = new Category();c2.setViews(new Views());
		v3 = TestXmlView.create(false);v3.setCode("multi");c2.getViews().getView().add(v3);
		
		access.getCategory().add(c1);
		access.getCategory().add(c2);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		View actual = AccessXpath.getView(access, v1.getCode());
	    Assert.assertEquals(v1,actual);
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		AccessXpath.getView(access, "-1");
	}
	
	 @Test(expected=ExlpXpathNotUniqueException.class)
	 public void testUnique() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	 {
		 AccessXpath.getView(access, v2.getCode());
	 }
}