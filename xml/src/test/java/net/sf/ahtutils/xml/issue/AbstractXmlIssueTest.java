package net.sf.ahtutils.xml.issue;

import org.jeesl.AbstractXmlTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXmlIssueTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractXmlIssueTest.class);
	
	public AbstractXmlIssueTest(Class<T> cXml)
	{
		super(cXml,"issue");
	}
}