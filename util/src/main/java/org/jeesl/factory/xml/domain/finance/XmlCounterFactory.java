package org.jeesl.factory.xml.domain.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Counter;
import net.sf.ahtutils.xml.finance.Figures;

public class XmlCounterFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlCounterFactory.class);
	
	public static Counter create(String code, int counter)
	{
		Counter xml = new Counter();
		xml.setCode(code);
		xml.setCounter(counter);
		return xml;
	}
	
	public static <E extends Enum<E>> Counter build(E code, int counter)
	{
		return create(code.toString(),counter);
	}
	public static <E extends Enum<E>> Counter build(E code, long counter)
	{
		return create(code.toString(),Long.valueOf(counter).intValue());
	}
	
	public static <E extends Enum<E>> void add(Figures figures, E code, Integer value)
	{
		if(value!=null){figures.getCounter().add(XmlCounterFactory.build(code, value));}
	}
}