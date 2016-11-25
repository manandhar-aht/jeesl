package org.jeesl.factory.txt;

import org.jeesl.AbstractJeeslUtilTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTxtMimeTypeFactory extends AbstractJeeslUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestTxtMimeTypeFactory.class);
		
	@Test public void pre()
    {	
		System.out.println(TxtMimeTypeFactory.build("x.pdf"));
		System.out.println(TxtMimeTypeFactory.build("x.doc"));
		System.out.println(TxtMimeTypeFactory.build("x.docx"));
    }
 

}