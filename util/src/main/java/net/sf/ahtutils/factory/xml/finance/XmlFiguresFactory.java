package net.sf.ahtutils.factory.xml.finance;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;
import net.sf.ahtutils.xml.text.Remark;

public class XmlFiguresFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFiguresFactory.class);
	
	public static Figures build(long id)
	{
		Figures xml = build();
		xml.setId(id);
		return xml;
	}
	
	public static Figures build(String code)
	{
		Figures xml = build();
		xml.setCode(code);
		return xml;
	}
	
	public static Figures build()
	{
		Figures xml = new Figures();
		return xml;
	}
	
	public static Map<Long,Map<String,Remark>> toMapRemark(List<Figures> figures)
	{
		Map<Long,Map<String,Remark>> map = new Hashtable<Long,Map<String,Remark>>();
		for(Figures f : figures)
		{
			long id = f.getId();
			if(!map.containsKey(id)){map.put(id,new Hashtable<String,Remark>());}
			Map<String,Remark> m = map.get(id);
			for(Remark r : f.getRemark())
			{
				m.put(r.getKey(), r);
			}
		}
		return map;
	}
	
	public static Map<Long,Map<String,Finance>> toMapFinance(List<Figures> figures)
	{
		Map<Long,Map<String,Finance>> map = new Hashtable<Long,Map<String,Finance>>();
		for(Figures f : figures)
		{
			long id = f.getId();
			if(!map.containsKey(id)){map.put(id,new Hashtable<String,Finance>());}
			Map<String,Finance> m = map.get(id);
			for(Finance r : f.getFinance())
			{
				m.put(r.getCode(), r);
			}
		}
		return map;
	}
}