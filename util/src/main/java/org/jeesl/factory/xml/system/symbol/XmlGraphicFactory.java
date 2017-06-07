package org.jeesl.factory.xml.system.symbol;

import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.symbol.Graphic;
import net.sf.exlp.factory.xml.io.XmlFileFactory;

public class XmlGraphicFactory <L extends UtilsLang,D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlGraphicFactory.class);
	
	private Graphic q;
	
	private XmlTypeFactory<GT,L,D> xfType;
	private XmlSymbolFactory<L,D,G,GT,F,FS> xfSymbol;
	
	public XmlGraphicFactory(Query query){this(query.getLang(),query.getGraphic());}
	public XmlGraphicFactory(String localeCode, Graphic q)
	{
		this.q=q;
		if(q.isSetType()){xfType = new XmlTypeFactory<GT,L,D>(q.getType());}
		if(q.isSetSymbol()){xfSymbol = new XmlSymbolFactory<L,D,G,GT,F,FS>(localeCode,q.getSymbol());}
	}
	
	public Graphic build(G graphic)
	{
		Graphic xml = build();
		
		if(q.isSetType()){xml.setType(xfType.build(graphic.getType()));}
		
		if(graphic.getType().getCode().equals(JeeslGraphicType.Code.svg.toString()))
		{
			if(q.isSetFile()){xml.setFile(XmlFileFactory.build(graphic.getData()));}
		}
		else if(graphic.getType().getCode().equals(JeeslGraphicType.Code.symbol.toString()))
		{
			if(q.isSetSymbol()){xml.setSymbol(xfSymbol.build(graphic));}
		}
		
		
		return xml;
	}
	
	public static Graphic build()
	{
		Graphic xml = new Graphic();
		return xml;
	}
}