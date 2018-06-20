package org.jeesl.factory.pivot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.api.bean.JeeslLabelResolver;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.metachart.factory.json.pivot.McPivotFactory;
import org.metachart.factory.json.pivot.PivotValueFactory;
import org.metachart.model.json.pivot.PivotField;
import org.metachart.model.json.pivot.PivotFieldList;
import org.metachart.model.json.pivot.PivotFields;
import org.metachart.model.json.pivot.PivotValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslPivotFactory<RE extends JeeslRevisionEntity<?,?,?,?,?>> extends McPivotFactory
{
	final static Logger logger = LoggerFactory.getLogger(JeeslPivotFactory.class);
	
	private final JeeslLabelResolver labelResolver;
	
	private final String localeCode;
	
	private final Map<Class<?>,String> mapFieldId;
	
	public JeeslPivotFactory(String localeCode, JeeslLabelResolver labelResolver)
	{
		this.localeCode=localeCode;
		this.labelResolver=labelResolver;
		
		mapFieldId = new HashMap<Class<?>,String>();
		this.clear();
	}
	
	public void clear()
	{
		mapFieldId.clear();
		
		fields = new PivotFields();
		fieldList = new PivotFieldList();
	}
	
	public <E extends Enum<E>> void addXPathField(Class<?> c, Collection<?> collection, E code)
	{
		PivotField json = new PivotField();
		json.setId("id"+(fieldList.getFields().size()+1));
		json.setLabel(labelResolver.entity(localeCode,c));		
		
		String xpath = labelResolver.xpath(c, code);
		for(Object o : collection)
		{
			EjbWithId ejb = (EjbWithId)o;
			JXPathContext context = JXPathContext.newContext(ejb);
			json.getMap().put(ejb.getId(),context.getValue(xpath).toString());
		}
		
		fieldList.getFields().add(json);
		mapFieldId.put(c, json.getId());
	}
	
	public <E extends Enum<E>> void addStatusField(Class<?> c, Collection<?> collection)
	{
		PivotField json = new PivotField();
		json.setId("id"+(fieldList.getFields().size()+1));
		json.setLabel(labelResolver.entity(localeCode,c));		
		
		String xpath = "name[@name='"+localeCode+"']/lang";
		for(Object o : collection)
		{
			EjbWithId ejb = (EjbWithId)o;
			JXPathContext context = JXPathContext.newContext(ejb);
			json.getMap().put(ejb.getId(),context.getValue(xpath).toString());
		}
		
		fieldList.getFields().add(json);
		mapFieldId.put(c, json.getId());
	}
	
	public void rows(Class<?>... rows)
	{
		for(Class<?> c : rows)
		{
			fields.getRows().add(mapFieldId.get(c));
		}
	}
	
	public void columns(Class<?>... columns)
	{
		for(Class<?> c : columns)
		{
			fields.getColumns().add(mapFieldId.get(c));
		}
	}
}