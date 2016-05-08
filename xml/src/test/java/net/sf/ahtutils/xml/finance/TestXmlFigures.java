package net.sf.ahtutils.xml.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.text.TestXmlRemark;

public class TestXmlFigures extends AbstractXmlFinanceTest<Figures>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFigures.class);
	
	public TestXmlFigures(){super(Figures.class);}
	public static Figures create(boolean withChildren){return (new TestXmlFigures()).build(withChildren);}
    
    public Figures build(boolean withChilds)
    {
    	Figures xml = new Figures();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.getFinance().add(TestXmlFinance.create(false));xml.getFinance().add(TestXmlFinance.create(false));
    		xml.getTime().add(TestXmlTime.create(false));xml.getTime().add(TestXmlTime.create(false));
    		xml.getRemark().add(TestXmlRemark.create(false));xml.getRemark().add(TestXmlRemark.create(false));
    		xml.getCounter().add(TestXmlCounter.create(false));xml.getCounter().add(TestXmlCounter.create(false));
    		
    		xml.getFigures().add(TestXmlFigures.create(false));xml.getFigures().add(TestXmlFigures.create(false));
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlFigures test = new TestXmlFigures();
		test.saveReferenceXml();
    }
}