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
	
	public static Figures build(long id){return build(id,null,null);}
	public static Figures build(String code){return (build(null,code,null));}
	public static Figures build(){return build(null,null,null);}
	
	public static Figures label(String label){return build(null,null,label);}
	
	public static Figures build(Long id, String code, String label)
	{
		Figures xml = new Figures();
		if(id!=null){xml.setId(id);}
		if(code!=null){xml.setCode(code);}
		if(label!=null){xml.setLabel(label);}
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
	
	public static boolean hasFinanceElements(Figures figures)
	{
		if(figures.isSetFinance()){return true;}
		else
		{
			for(Figures f : figures.getFigures())
			{
				if(hasFinanceElements(f)){return true;}
			}
		}
		return false;
	}
}