package net.sf.ahtutils.xml.finance;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFinance extends AbstractXmlFinanceTest<Finance>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFinance.class);
	
	public TestXmlFinance(){super(Finance.class);}
	public static Finance create(boolean withChildren){return (new TestXmlFinance()).build(withChildren);}
    
    public Finance build(boolean withChilds)
    {
    	Finance xml = new Finance();
    	xml.setId(123);
    	xml.setNr(1);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setSymbol("mySymbol");
    	xml.setValue(567.89);
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlFinance test = new TestXmlFinance();
		test.saveReferenceXml();
    }
}