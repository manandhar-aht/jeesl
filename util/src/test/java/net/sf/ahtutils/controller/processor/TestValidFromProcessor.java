package net.sf.ahtutils.controller.processor;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.model.ejb.utils.UtilsValidFrom;
import net.sf.ahtutils.test.AbstractJeeslTest;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestValidFromProcessor extends AbstractJeeslTest
{
	final static Logger logger = LoggerFactory.getLogger(TestValidFromProcessor.class);
	
	private List<UtilsValidFrom> list;
	
	@Before
	public void init()
	{
		list = new ArrayList<UtilsValidFrom>();
		
		DateTime dt1 = new DateTime(2012, 2, 29, 11, 52, 0);
		DateTime dt2 = new DateTime(2009, 12, 15, 0, 0, 0);
		DateTime dt3 = new DateTime(2009, 11, 15, 0, 0, 0);
		
		UtilsValidFrom v1 = new UtilsValidFrom();v1.setId(1);v1.setValidFrom(dt1.toDate());list.add(v1);
		UtilsValidFrom v2 = new UtilsValidFrom();v2.setId(2);v2.setValidFrom(dt2.toDate());list.add(v2);
		UtilsValidFrom v3 = new UtilsValidFrom();v3.setId(3);v3.setValidFrom(dt3.toDate());list.add(v3);
	}
 
    @Test
    public void testSimple()
    {	
    	ValidFromProcessor<UtilsValidFrom> vfp = new ValidFromProcessor<UtilsValidFrom>(list);
    	
    	DateTime dt = new DateTime(2012, 3, 1, 0, 0, 0);
    	
    	List<UtilsValidFrom> result = vfp.getValid(dt.toDate());
    	Assert.assertEquals(1, result.size());
    	Assert.assertEquals(1, result.get(0).getId());
    }
}