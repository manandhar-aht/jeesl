package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.link.UtilsLink;

import org.jeesl.model.xml.system.io.mail.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLinkFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLinkFactory.class);
	
	public static <S extends UtilsStatus<S,L,D>, L extends UtilsLang, LI extends UtilsLink<S,L,D>, D extends UtilsDescription> Link create(LI ejb, String url)
	{
		Link xml = new Link();
		xml.setCode(ejb.getCode());
		xml.setUrl(url);
		return xml;
	}
}