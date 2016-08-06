package net.sf.ahtutils.controller.util.poi;

import net.sf.ahtutils.test.AbstractJeeslTest;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPoiRowColNumerator extends AbstractJeeslTest
{
	final static Logger logger = LoggerFactory.getLogger(TestPoiRowColNumerator.class);
	
	
	@Test
	public void rnd()
	{
		for(int i=0;i<100;i++)
		{
			int expected = rndI(64000)+1;
			String col = PoiRowColNumerator.translateColumnIndexToName(expected);
			int actual =  PoiRowColNumerator.translateNameToIndex(col);
			Assert.assertEquals(expected, actual);
		}
	}
	
	@Test
	public void manualNameToIndex()
	{
		Assert.assertEquals("A", PoiRowColNumerator.translateColumnIndexToName(0));
		Assert.assertEquals("X", PoiRowColNumerator.translateColumnIndexToName(23));
		Assert.assertEquals("AD", PoiRowColNumerator.translateColumnIndexToName(29));
	}
	
	@Test
	public void manualIndexToName()
	{
		Assert.assertEquals(0, PoiRowColNumerator.translateNameToIndex("A"));
		Assert.assertEquals(23, PoiRowColNumerator.translateNameToIndex("X"));
	}
	
}