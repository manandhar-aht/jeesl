package org.jeesl.model.xml.system.io.db;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.dbseed.Db;

public class TestDb extends AbstractXmlDbseedTest<Db>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlDbseedTest.class);
	
	public TestDb(){super(Db.class);}
	public static Db create(boolean withChildren){return (new TestDb()).build(withChildren);}
	
    public Db build(boolean withChilds)
    {
    	Db xml = new Db();
    	xml.setPathIde("myPathIde");
    	xml.setPathExport("myPathExport");
    	
    	if(withChilds)
    	{
    		xml.getSeed().add(TestSeed.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestDb test = new TestDb();
		test.saveReferenceXml();
    }
}