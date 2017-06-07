package net.sf.ahtutils.factory.xml.sync;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.test.AbstractFileProcessingTest;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.factory.xml.system.io.sync.XmlExceptionFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlExceptionFactory extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlExceptionFactory.class);
	
	private UtilsNotFoundException eSingle;
	private UtilsConstraintViolationException eMulti;
	private UtilsConstraintViolationException eIntegrity;
	
	@Before
	public void init()
	{
		eSingle = new UtilsNotFoundException();
		
		eIntegrity = new UtilsConstraintViolationException("TestIntegriy");
		eMulti = new UtilsConstraintViolationException("TestMulti",eIntegrity);
	}
	
	@Test
	public void single()
	{
		net.sf.ahtutils.xml.sync.Exception xml = XmlExceptionFactory.build(eSingle);
		JaxbUtil.info(xml);
	}
	
	@Test
	public void multi()
	{
		net.sf.ahtutils.xml.sync.Exception xml = XmlExceptionFactory.build(eMulti);
		JaxbUtil.info(xml);
	}
}