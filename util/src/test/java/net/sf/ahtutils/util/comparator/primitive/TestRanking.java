package net.sf.ahtutils.util.comparator.primitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilTest;

public class TestRanking extends AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRanking.class);
	
	private Ranking ranking;
	private Random rnd;
	
	@Before
	public void init()
	{
		ranking = new Ranking();
		rnd = new Random();
	}
    
    @After
    public void close()
    {
    	ranking = null;
    	rnd = null;
    }
 
    @Test
    public void testOrderedAsc()
    {
    	int[] points = {1,2,3};
    	int[] expected = {3,2,1};
    	int[] actual = ranking.rank(points);
    	Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testOrderedRnd()
    {
    	int[] points = {1,6,5};
    	int[] expected = {3,1,2};
    	int[] actual = ranking.rank(points);
    	Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testUnOrdered()
    {
    	int[] points = {5,1,4};
    	int[] expected = {1,3,2};
    	int[] actual = ranking.rank(points);
    	Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testDoubleLow()
    {
    	int[] points = {3,5,3};
    	int[] expected = {2,1,2};
    	int[] actual = ranking.rank(points);
    	Assert.assertArrayEquals(expected, actual);
    }
   
    @Test
    public void testDoubleHigh()
    {
    	int[] points = {7,5,7};
    	int[] expected = {1,3,1};
    	int[] actual = ranking.rank(points);
    	Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void testRandom()
    {
    	int nr = 1000;
    	List<Integer> points = new ArrayList<Integer>();
    	for(int i=0;i<nr;i++){points.add(rnd.nextInt());}
    	
    	List<Integer> actual = ranking.rank(points);
    	Assert.assertEquals(nr, points.size());
    	Assert.assertEquals(nr, actual.size());
    	for(Integer i : actual)
    	{
    		Assert.assertTrue("Rank must be > 0, but is "+i,i>0);
    		Assert.assertTrue("Rank must be <= "+nr+", but is "+i, +i<=1000);
    	}
    }
    
    @Test
    public void complex()
    {
    	int[] points   = {3,5,7,2,3,4,6,7,4};
    	int[] expected = {7,4,1,9,7,5,3,1,5};
    	int[] actual = ranking.rank(points);
    	Assert.assertArrayEquals(expected, actual);
    }
    
    @Test
    public void comparator()
    {	
    	Ranking r = new Ranking();
    	List<Ranking.Rank> list = new ArrayList<Ranking.Rank>();
    	Ranking.Rank a = r.new Rank(1,4);list.add(a);
    	Ranking.Rank b = r.new Rank(2,5);list.add(b);
    	
    	Collections.sort(list, r.new Rank());
    	Assert.assertEquals("b1="+b.getScore()+" b2="+list.get(0).getScore(), b, list.get(0));
    	Assert.assertEquals(a, list.get(1));
    }
	public int hashCode()
	{
		return new HashCodeBuilder(17, 43).toHashCode();
	}
}