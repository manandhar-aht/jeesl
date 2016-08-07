package org.jeesl.util.query.xpath.access;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.access.AclContainer;
import net.sf.ahtutils.xml.access.Group;
import net.sf.ahtutils.xml.access.RoleAutoAssign;
import net.sf.ahtutils.xml.xpath.AccessXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class TestXPathAccessAutoAssign extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathAccessAutoAssign.class);
    
	private AclContainer aclContainer;
	private final String code = "code";
	
	@Before
	public void iniAclContainer()
	{
		RoleAutoAssign raa = new RoleAutoAssign();
		raa.setCode(code);
		
		RoleAutoAssign.Add add = new RoleAutoAssign.Add();
		Group g1 = new Group();g1.setCode("g1");add.getGroup().add(g1);
		Group g2 = new Group();g2.setCode("g2");add.getGroup().add(g2);
		
		RoleAutoAssign.Rm rm = new RoleAutoAssign.Rm();
		rm.setImmediate(false);
		Group g3 = new Group();g3.setCode("g3");rm.getGroup().add(g3);
		Group g4 = new Group();g4.setCode("g4");rm.getGroup().add(g4);
		
		raa.setAdd(add);
		raa.setRm(rm);
		
		aclContainer = new AclContainer();
		aclContainer.getRoleAutoAssign().add(raa);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		RoleAutoAssign test = AccessXpath.getAutoAssign(aclContainer, code);
	    Assert.assertEquals(code,test.getCode());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		AccessXpath.getAutoAssign(aclContainer, "-1");
	}
}