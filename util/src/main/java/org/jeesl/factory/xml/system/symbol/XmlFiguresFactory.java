package org.jeesl.factory.xml.system.symbol;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Figure;
import net.sf.ahtutils.xml.symbol.Figures;

public class XmlFiguresFactory 
{
	final static Logger logger = LoggerFactory.getLogger(XmlFiguresFactory.class);
		
	public static Figures build()
	{
		Figures xml = new Figures();
		return xml;
	}
	
	public static Figures build(Figure figure)
	{
		Figures xml = build();
		xml.getFigure().add(figure);
		return xml;
	}
	
	public static Figures build(List<Figure> figures)
	{
		Figures xml = build();
		xml.getFigure().addAll(figures);
		return xml;
	}
}