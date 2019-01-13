package org.jeesl.doc.ofx.cms.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.model.json.system.translation.JsonTranslation;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxHeadFactory;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.status.Translations;

public class AbstractJeeslOfxTableFactory<L extends UtilsLang> extends AbstractJeeslOfxFactory<L>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslOfxTableFactory.class);
	
	protected List<String> localeCodes;
	protected List<String> tableHeaderKeys;
	
	protected final List<JsonTranslation> tableHeaders;
	
	protected Translations translations;
	
	public AbstractJeeslOfxTableFactory(OfxTranslationProvider tp)
	{	
		super(tp);
		tableHeaderKeys = new ArrayList<String>();
		tableHeaders = new ArrayList<JsonTranslation>();
	}
	
	protected <E extends Enum<E>>void addHeaderKey(Class<?> c)
	{
		JsonTranslation json = new JsonTranslation();
		json.setEntity(c.getSimpleName());
		tableHeaders.add(json);
	}
	protected <E extends Enum<E>>void addHeaderKey(Class<?> c, E code)
	{
		JsonTranslation json = new JsonTranslation();
		json.setEntity(c.getSimpleName());
		json.setCode(code.toString());
		tableHeaders.add(json);
	}
	protected void addHeaderMulti(Map<String,L> multiLang)
	{
		JsonTranslation json = new JsonTranslation();
		
		Map<String,String> map = new HashMap<String,String>();
		for(String key : multiLang.keySet())
		{
			if(tp.hasLocale(key))
			{
				map.put(key,multiLang.get(key).getLang());
			}
		}
		json.setMultiLang(map);
		tableHeaders.add(json);
	}
	protected void addHeader(String label)
	{
		JsonTranslation json = new JsonTranslation();
		json.setLabel(label);
		tableHeaders.add(json);
	}
	
	protected <E extends Enum<E>>void addHeaderKey(E code)
	{
		tableHeaderKeys.add(code.toString());
	}
	
	protected Head buildHead()
	{
		Row row = new Row();
		logger.info("Building Head with keys:"+tableHeaderKeys.size()+" locales:"+tp.getLocaleCodes().size());
		for(String headerKey : tableHeaderKeys)
		{
			Cell cell = OfxCellFactory.build();
			for(String localeCode : tp.getLocaleCodes())
			{
				cell.getContent().add(XmlParagraphFactory.build(localeCode,tp.toTranslation(localeCode, headerKey)));
			}
			row.getCell().add(cell);
		}
		
		return OfxHeadFactory.build(row);
	}
	
	protected Head buildTableHeader()
	{
		Row row = new Row();
		logger.info("Building Head with keys:"+tableHeaderKeys.size()+" locales:"+tp.getLocaleCodes().size());
		for(JsonTranslation json : tableHeaders)
		{
			Cell cell = OfxCellFactory.build();
			if(json.getEntity()!=null)
			{
				for(String localeCode : tp.getLocaleCodes())
				{
					cell.getContent().add(XmlParagraphFactory.build(localeCode,tp.toTranslation(localeCode,json.getEntity(),json.getCode())));
				}
			}
			else if(json.getMultiLang()!=null)
			{
				for(String localeCode : json.getMultiLang().keySet())
				{
					cell.getContent().add(XmlParagraphFactory.build(localeCode,json.getMultiLang().get(localeCode)));
				}
			}
			else if(json.getLabel()!=null)
			{
				cell.getContent().add(XmlParagraphFactory.text(json.getLabel()));
			}
			row.getCell().add(cell);
		}
		
		return OfxHeadFactory.build(row);
	}
}