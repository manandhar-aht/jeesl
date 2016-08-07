package net.sf.ahtutils.xml.symbol;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.TestXmlStyle;

public class TestXmlSymbol extends AbstractXmlSymbolTest<Symbol>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSymbol.class);
	
	public TestXmlSymbol(){super(Symbol.class);}
	public static Symbol create(boolean withChildren){return (new TestXmlSymbol()).build(withChildren);}
    
    public Symbol build(boolean withChilds)
    {
    	Symbol xml = new Symbol();
    	xml.setId(123);
    	xml.setSize(10);
    	xml.setSizeBorder(1);
    	xml.setColor("AABBCC");
    	xml.setColorBorder("DDEEFF");
    	
    	if(withChilds)
    	{
    		xml.setStyle(TestXmlStyle.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlSymbol test = new TestXmlSymbol();
		test.saveReferenceXml();
    }
}