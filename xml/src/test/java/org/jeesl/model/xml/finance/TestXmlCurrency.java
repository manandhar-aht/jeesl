package org.jeesl.model.xml.finance;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Currency;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlCurrency extends AbstractXmlFinanceTest<Currency>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCurrency.class);
	
	public TestXmlCurrency(){super(Currency.class);}
	public static Currency create(boolean withChildren){return (new TestXmlCurrency()).build(withChildren);}
    
    public Currency build(boolean withChilds)
    {
    	Currency xml = new Currency();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setSymbol("mySymbol");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    	}
    	    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlCurrency test = new TestXmlCurrency();
		test.saveReferenceXml();
    }
}