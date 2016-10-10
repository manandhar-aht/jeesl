package org.jeesl.factory.java.security;

import java.io.File;

import net.sf.ahtutils.controller.factory.java.security.AbstractJavaSecurityFactoryTst;
import net.sf.exlp.exception.ExlpConfigurationException;

import org.jeesl.factory.java.security.AbstractJavaSecurityFileFactory;
import org.jeesl.factory.java.security.JavaSecurityViewIdentifierFactory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAbstractJavaSecurityFileFactory extends AbstractJavaSecurityFactoryTst
{
	final static Logger logger = LoggerFactory.getLogger(TestAbstractJavaSecurityFileFactory.class);
	
	private JavaSecurityViewIdentifierFactory idFactory;
	
	private File fPackage;
	private String classPrefix;
	

	
	@Test
	public void buildPackage() throws ExlpConfigurationException
	{
		String actual = AbstractJavaSecurityFileFactory.buildPackage("adminSecurity");
		String expected = "admin.security";
		Assert.assertEquals(expected,actual);
	}
}