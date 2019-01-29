package org.jeesl.doc.ofx;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Cell;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class OfxMultiLocaleFactory<L extends UtilsLang, LOC extends UtilsStatus<LOC,L,?>>
{	
	final static Logger logger = LoggerFactory.getLogger(OfxMultiLocaleFactory.class);
		
	public OfxMultiLocaleFactory()
	{
	}
	
	public <S extends UtilsStatus<S,L,D>, D extends UtilsDescription> Cell cellLabel(JeeslLocaleProvider<LOC> lp, UtilsStatus<S,L,D> status)
	{
		Cell cell = OfxCellFactory.build();
//		if(font!=null){cell.getContent().add(font);}
		cell.getContent().addAll(paragraphLabels(lp,status));
		return cell;
	}
	
	private <S extends UtilsStatus<S,L,D>, D extends UtilsDescription> List<Paragraph> paragraphLabels(JeeslLocaleProvider<LOC> lp, UtilsStatus<S,L,D> status)
	{
		List<Paragraph> paragraphs = new ArrayList<Paragraph>();
		
		for(String key : lp.getLocaleCodes())
		{
			Paragraph p = XmlParagraphFactory.lang(key);
//			p.getContent().add(font);
			if(status.getName()!=null && status.getName().containsKey(key)) {p.getContent().add(status.getName().get(key).getLang());}
			else {p.getContent().add("-!-");}
			paragraphs.add(p);
			
		}
		return paragraphs;
	}
	
	public <S extends UtilsStatus<S,L,D>, D extends UtilsDescription> Title title(JeeslLocaleProvider<LOC> lp, UtilsStatus<S,L,D> status) {return title(lp,status,null);}
	public <S extends UtilsStatus<S,L,D>, D extends UtilsDescription> Title title(JeeslLocaleProvider<LOC> lp, UtilsStatus<S,L,D> status, String suffix)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(status.getName().get(lp.getPrimaryLocaleCode()).getLang());
		if(suffix!=null) {sb.append(" ").append(suffix);}
		
		return XmlTitleFactory.build(lp.getPrimaryLocaleCode(), sb.toString());
	}
}