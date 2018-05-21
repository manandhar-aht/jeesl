package org.jeesl.controller.processor;

import org.w3c.dom.Document;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class FtlW3cProcessor
{	
	public static Document transformWithoutNs(Object jaxb)
	{
		org.jdom2.Document jdoc = JaxbUtil.toDocument(jaxb);
		jdoc = JDomUtil.unsetNameSpace(jdoc);
		return JDomUtil.toW3CDocument(jdoc);
	}
}