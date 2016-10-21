package org.jeesl.factory.java.security;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.factory.java.security.AbstractJavaSecurityFactoryTst;
import net.sf.exlp.exception.ExlpConfigurationException;

public class TestAbstractJavaSecurityFileFactory extends AbstractJavaSecurityFactoryTst
{
	final static Logger logger = LoggerFactory.getLogger(TestAbstractJavaSecurityFileFactory.class);
	
	@Test
	public void buildPackage() throws ExlpConfigurationException
	{
		String actual = AbstractJavaSecurityFileFactory.buildPackage("adminSecurity");
		String expected = "admin.security";
		Assert.assertEquals(expected,actual);
	}
}