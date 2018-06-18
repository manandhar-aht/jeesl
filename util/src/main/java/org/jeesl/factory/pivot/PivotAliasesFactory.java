package org.jeesl.factory.pivot;

import java.util.Collection;

import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.api.bean.JeeslLabelResolver;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.metachart.model.json.pivot.PivotAliases;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class PivotAliasesFactory<RE extends JeeslRevisionEntity<?,?,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(PivotAliasesFactory.class);
	
	private final JeeslLabelResolver labelResolver;
	
	public PivotAliasesFactory(JeeslLabelResolver labelResolver)
	{
		this.labelResolver=labelResolver;
	}
	
	public <E extends Enum<E>> PivotAliases  build(Collection<?> collection, Class<?> c, E code)
	{
		PivotAliases aliases = new PivotAliases();
		String xpath = labelResolver.xpath(c, code);
		logger.info("xPath: : "+xpath);
		for(Object o : collection)
		{
			EjbWithId ejb = (EjbWithId)o;
			JXPathContext context = JXPathContext.newContext(ejb);
			aliases.getMap().put(ejb.getId(),context.getValue(xpath).toString());
		}
		return aliases;
	}
	
}