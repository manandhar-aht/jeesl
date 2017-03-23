package net.sf.ahtutils.test.model.ejb.status;

import java.util.Random;

import org.jeesl.model.ejb.system.status.Lang;
import org.jeesl.model.ejb.system.status.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.model.ejb.status.cli.TstStatus;

public class TestStatus
{
	final static Logger logger = LoggerFactory.getLogger(TstStatus.class);
	
	private Random rnd;
	private String code;
	
	@Before
	public void init()
	{
		rnd = new Random();
		code = "code"+rnd.nextInt(1000000000);
	}

	@After
	public void close(){rnd=null;}
	
	@Test
    public void addStatus()
    {
    	Status ejb = create(rnd,code);
 //   	ejb = fUtil.persist(ejb);
 //   	Assert.assertTrue(ejb.getId()>0);
    }
    
    public static Status create(Random rnd, String code)
    {
    	Status ejb = new Status();
    	ejb.setCode(code);
    	ejb.setVisible(true);
    	ejb.getName().put("en", create("en", "en"+rnd.nextInt(10000)));
    	ejb.getName().put("de", create("de", "de"+rnd.nextInt(10000)));
    	return ejb;
    }
    
    public static Lang create(String key, String lang)
    {
    	Lang ejb = new Lang();
    	ejb.setLang(lang);
    	ejb.setLkey(key);
    	return ejb;
    }
}