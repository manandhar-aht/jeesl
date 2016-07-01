package net.sf.ahtutils.controller.processor;

import java.util.*;

import net.sf.ahtutils.controller.processor.set.SetProcessingLexer;
import net.sf.ahtutils.controller.processor.set.SetProcessingParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsTest;

public class TestSetProcessor extends AbstractAhtUtilsTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSetProcessor.class);
	
	private List<String> a,b,c,d;
	
	@Before public void init()
	{
		a = Arrays.asList("a byte c dummd1deldumm 141447483187945187"      .split(" "));
		b = Arrays.asList(      "dummd1deldumm e fit g".split(" "));
		c = Arrays.asList(	"byte 141447483187945187 j k".split(" "));
		d = Arrays.asList(	"byte l m nkd 141447483187945187".split(" "));
		//a AND (b OR (c AND d))
	}
	
	@Test public void pre()
    {	
    	Assert.assertEquals(4, a.size());
    	Assert.assertEquals(4, b.size());
		Assert.assertEquals(4, c.size());
		Assert.assertEquals(4, d.size());
    }
 
    @Test public void and()
    {
		SetProcessingLexer lexer = new SetProcessingLexer(new ANTLRInputStream(a.toString() + "&&" + b.toString()));
		SetProcessingParser parser = new SetProcessingParser(new CommonTokenStream(lexer));
		List<String> result = (List)new SetProcessor().visit(parser.parse());
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("d", result.get(0));
    }
    
    @Test
    public void or()
    {
		SetProcessingLexer lexer = new SetProcessingLexer(new ANTLRInputStream(a.toString() + "||" + b.toString()));
		SetProcessingParser parser = new SetProcessingParser(new CommonTokenStream(lexer));
		List<String> result = (List)new SetProcessor().visit(parser.parse());
		System.out.println(result.toString());
		Assert.assertEquals(8, result.size());
		Assert.assertEquals("[a, byte, c, 141447483187945187, dummd1deldumm, e, fit, g]", result.toString());
    }

	@Test public void combination()
	{
		SetProcessingLexer lexer = new SetProcessingLexer(new ANTLRInputStream(a.toString() + "&& (" + b.toString() + "||" + "("+ c.toString()+"&&" + d.toString() + "))"));
		SetProcessingParser parser = new SetProcessingParser(new CommonTokenStream(lexer));
		List<String> result = (List)new SetProcessor().visit(parser.parse());
		System.out.println(result.toString());
		Assert.assertEquals(3, result.size());
		Assert.assertEquals("[dummd1deldumm, byte, 141447483187945187]", result.toString());
	}
}