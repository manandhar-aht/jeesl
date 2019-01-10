package org.jeesl.factory.xml.module.survey;

import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.survey.Options;

public class XmlOptionsFactory <L extends UtilsLang, D extends UtilsDescription,
								QUESTION extends JeeslSurveyQuestion<L,D,?,?,?,?,?,?,?,OPTION,?>,
								OPTION extends JeeslSurveyOption<L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlOptionsFactory.class);
	
	final Options q;
	
	private XmlOptionFactory<L,D,OPTION> xfOption;
	
	
	
	public XmlOptionsFactory(String localeCode, Options q)
	{
		this.q=q;
		if(q.isSetOption()) {xfOption = new XmlOptionFactory<L,D,OPTION>(localeCode,q.getOption().get(0));}
	}
	
	public static Options build() {return new Options();}
	
	public Options build(QUESTION ejb)
	{
		Options xml = build();
		
		if(ejb.getOptions()!=null && !ejb.getOptions().isEmpty())
		{
			for(OPTION option : ejb.getOptions())
			{
				xml.getOption().add(xfOption.build(option));
			}
		}
		
		return xml;
	}
}