package org.jeesl.doc.ofx;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Row;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class AbstractJeeslOfxFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslOfxFactory.class);
		
	protected final List<String> localeCodes;
	protected final List<String> tableHeaderKeys;
	
	protected Translations translations;
//	protected String imagePathPrefix;
	
	public AbstractJeeslOfxFactory(List<String> localeCodes, Translations translations)
	{
		this.localeCodes=localeCodes;
		this.translations=translations;
		
		tableHeaderKeys = new ArrayList<String>();
	}
	
	protected Row createHeaderRow(List<String> headerKeys)
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			Cell cell = OfxCellFactory.build();
			for(String lang : localeCodes)
			{
				StringBuffer sb = new StringBuffer();
				if(headerKey.length()>0)
				{
					try
					{
						sb.append(StatusXpath.getLang(translations, headerKey, lang).getTranslation());
					}
					catch (ExlpXpathNotFoundException e)
					{
						sb.append(e.getMessage());
						logger.warn(sb.toString());
					}
					catch (ExlpXpathNotUniqueException e)
					{
						sb.append(e.getMessage());
						logger.warn(sb.toString());
					}
				}
				else
				{
					sb.append("");
				}
				Paragraph p = new Paragraph();
				p.setLang(lang);
				p.getContent().add(sb.toString());
				cell.getContent().add(p);
			}
			
			row.getCell().add(cell);
		}
		return row;
	}
}