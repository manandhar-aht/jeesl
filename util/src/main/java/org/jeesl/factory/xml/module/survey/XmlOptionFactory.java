package org.jeesl.factory.xml.module.survey;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.survey.Option;

public class XmlOptionFactory<L extends UtilsLang, D extends UtilsDescription, OPTION extends JeeslSurveyOption<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlOptionFactory.class);
		
	private final String localeCode;
	private final Option q;
	
	public XmlOptionFactory(Option q){this(null,q);}
	
	public XmlOptionFactory(String localeCode, Option q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public static Option build() {return new Option();}
	
	public Option build(OPTION ejb)
	{
		Option xml = build();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()) {xml.setCode(ejb.getCode());}
		if(q.isSetLabel() && localeCode!=null && ejb.getName().containsKey(localeCode)) {xml.setLabel(ejb.getName().get(localeCode).getLang());}
		
		return xml;
	}
}