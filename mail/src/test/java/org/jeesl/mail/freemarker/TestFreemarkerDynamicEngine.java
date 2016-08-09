package org.jeesl.mail.freemarker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jeesl.mail.AbstractJeeslMailTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.InvalidReferenceException;
import freemarker.template.TemplateException;

public class TestFreemarkerDynamicEngine extends AbstractJeeslMailTest
{
	final static Logger logger = LoggerFactory.getLogger(TestFreemarkerDynamicEngine.class);
		
	private FreemarkerDynamicEngine fde;
	private Map<String,String> model;
	
	private String code1,code2,code3;
	
	@Before
	public void init()
	{	
		code1 = "welcome";
		code2 = "byebye";
		code3 = "invalid";
		
		fde = new FreemarkerDynamicEngine();
		fde.addTemplate(code1,"Welcome ${user}");
		fde.addTemplate(code2,"ByeBye ${user} from ${sender}");
		fde.addTemplate(code3,"ByeBye ${unknwon}");
				
		model = new HashMap<String,String>();
		model.put("user","Big Joe");
		model.put("sender","JEESL");
	}
	
    @Test
    public void code1() throws IOException, TemplateException
    {
    	String expected = "Welcome Big Joe";
    	String actual = fde.process(code1,model);
    	Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void code2() throws IOException, TemplateException
    {
    	String expected = "ByeBye Big Joe from JEESL";
    	String actual = fde.process(code2,model);
    	Assert.assertEquals(expected, actual);
    }
    
    @Test(expected=InvalidReferenceException.class)
    public void code3() throws IOException, TemplateException
    {
    	String expected = "ByeBye Big Joe from JEESL";
    	String actual = fde.process(code3,model);
    	Assert.assertEquals(expected, actual);
    }
}