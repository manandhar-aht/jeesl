package org.jeesl.model.xml.system.symbol;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.status.TestXmlStyles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Symbol;

public class TestXmlSymbol extends AbstractXmlSymbolTest<Symbol>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSymbol.class);
	
	public TestXmlSymbol(){super(Symbol.class);}
	public static Symbol create(boolean withChildren){return (new TestXmlSymbol()).build(withChildren);}
    
    public Symbol build(boolean withChilds)
    {
    	Symbol xml = new Symbol();
//    	xml.setId(123);
    	xml.setSize(10);
    	xml.setSizeBorder(1);
    	
    	if(withChilds)
    	{
    		xml.setStyles(TestXmlStyles.create(false));
    		xml.setColors(TestXmlColors.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlSymbol test = new TestXmlSymbol();
		test.saveReferenceXml();
    }
}