package org.jeesl.factory.xml.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionViewMapping;
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
	
	public static void aggregationHeader(String lang, Labels labels, Map<Long,String> mapAggregationLabels)
	{
		for(Long i : mapAggregationLabels.keySet())
		{
			Label label = XmlLabelFactory.build("financeLevel"+i, mapAggregationLabels.get(i));
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
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RST extends UtilsStatus<RST,L,D>,
					RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RAT extends UtilsStatus<RAT,L,D>>
		Labels build(RE entity, String localeCode, String scope)
	{
		Labels xml = build();
		for(RA attribute : entity.getAttributes())
		{
			if(attribute.getName().containsKey(localeCode))
			{
				xml.getLabel().add(XmlLabelFactory.build(scope,attribute.getCode(),attribute.getName().get(localeCode).getLang()));
			}
		}
		return xml;
	}
	
	public static Map<String,String> toMap(Labels labels){return toMap(labels,null);}
	public static Map<String,String> toMap(Labels labels, String scope)
	{
		Map<String,String> map = new HashMap<String,String>();
		for(Label label : labels.getLabel())
		{
			if(scope==null){map.put(label.getKey(),label.getValue());}
			else if(label.getScope().equals(scope)){map.put(label.getKey(),label.getValue());}
		}
		return map;
	}
}