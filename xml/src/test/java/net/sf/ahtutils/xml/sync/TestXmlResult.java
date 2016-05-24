package net.sf.ahtutils.xml.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStatus;

public class TestXmlResult extends AbstractXmlSyncTest<Result>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResult.class);
	
	public TestXmlResult(){super(Result.class);}
	public static Result create(boolean withChildren){return (new TestXmlResult()).build(withChildren);}
    
    public Result build(boolean withChilds)
    {
    	Result xml = new Result();
    	xml.setTotal(3);
    	xml.setSuccess(2);
    	xml.setFail(1);
    	xml.setSkip(0);
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlResult test = new TestXmlResult();
		test.saveReferenceXml();
    }
}