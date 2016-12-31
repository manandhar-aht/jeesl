package org.jeesl.model.xml.system.io.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Styles;

public class TestXmlStyles extends AbstractXmlReportTest<Styles>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyles.class);
	
	public TestXmlStyles(){super(Styles.class);}
	public static Styles create(boolean withChildren){return (new TestXmlStyles()).build(withChildren);} 
    
    public Styles build(boolean withChildren)
    {
    	Styles xml = new Styles();
    	
    	if(withChildren)
    	{
    		xml.getStyle().add(TestXmlStyle.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlStyles test = new TestXmlStyles();
		test.saveReferenceXml();
    }
}