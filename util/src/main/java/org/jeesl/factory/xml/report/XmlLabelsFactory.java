package org.jeesl.factory.xml.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.report.Label;
import net.sf.ahtutils.xml.report.Labels;

public class XmlLabelsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlLabelsFactory.class);
		
	public static Labels build()
	{
		Labels xml = new Labels();
		return xml;
	}
	
	public static <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
		void aggregationGroups(String localeCode, Labels labels, List<S> aggregations)
	{
		for(int i=1;i<=aggregations.size();i++)
		{
			S s = aggregations.get(i-1);
			Label label = XmlLabelFactory.build("labelLevel"+i, s.getName().get(localeCode).getLang());
			labels.getLabel().add(label);
		}
	}
	
	public static void aggregationHeader(String lang, Labels labels, List<String> header)
	{
		for(int i=1;i<=header.size();i++)
		{
			Label label = XmlLabelFactory.build("financeLevel"+i, header.get(header.size()-i));
			labels.getLabel().add(label);
		}
	}
}