package net.sf.ahtutils.test.model.ejb.status;

import java.util.Random;

import net.sf.ahtutils.model.ejb.status.Lang;
import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;
import net.sf.ahtutils.test.model.ejb.status.cli.TstStatus;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
@RunWith(Arquillian.class)
public class TestStatus
{
	final static Logger logger = LoggerFactory.getLogger(TstStatus.class);
	
	private Random rnd;
	private String code;
	
//	@Deployment
//	public static JavaArchive createTestArchive(){return AbstractTgTest.getJavaArchive();}
	
	
	@Before
	public void init()
	{
		rnd = new Random();
		code = "code"+rnd.nextInt(1000000000);
	}

	@After
	public void close(){rnd=null;}
	
    public void addStatus()
    {
    	AhtUtilsStatus ejb = create(rnd,code);
 //   	ejb = fUtil.persist(ejb);
 //   	Assert.assertTrue(ejb.getId()>0);
    }
    
    public static AhtUtilsStatus create(Random rnd, String code)
    {
    	AhtUtilsStatus ejb = new AhtUtilsStatus();
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