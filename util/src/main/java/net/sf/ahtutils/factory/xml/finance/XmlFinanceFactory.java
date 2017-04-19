package net.sf.ahtutils.factory.xml.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.finance.UtilsFinance;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;

public class XmlFinanceFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFinanceFactory.class);
	
	public static <F extends UtilsFinance> Finance create(F ejb)
	{
		Finance xml = new Finance();
		xml.setValue(ejb.getValue());
		return xml;
	}
	
	public static <E extends Enum<E>> Finance build(E code, double value){return create(code.toString(),value);}
	public static Finance create(String code, double value)
	{
		return build(code,null,value);
	}
	public static Finance build(String code, String label, double value)
	{
		Finance xml = build();
		xml.setCode(code);
		xml.setLabel(label);
		xml.setValue(value);
		return xml;
	}
	
	public static Finance create(String code, String label)
	{
		Finance xml = build();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
	
	public static void addId(Figures figures, EjbWithId ejb, Double value){addId(figures,ejb.getId(),value);}
	public static void addId(Figures figures, long id, Double value)
	{
		if(value!=null)
		{
			figures.getFinance().add(XmlFinanceFactory.id(id, value));
		}
	}
	
	public static void addCode(Figures figures, String code, Double value)
	{
		if(value!=null)
		{
			figures.getFinance().add(XmlFinanceFactory.create(code, value));
		}
	}
	
	public static Finance id(long id)
	{
		Finance xml = build();
		xml.setId(id);
		return xml;
	}
	
	public static Finance id(long id, double value)
	{
		Finance xml = build();
		xml.setId(id);
		xml.setValue(value);
		return xml;
	}
	
	public static Finance nr(int nr, double value)
	{
		Finance xml = build();
		xml.setNr(nr);
		xml.setValue(value);
		return xml;
	}
	
	public static Finance build(double value)
	{
		Finance xml = build();
		xml.setValue(value);
		return xml;
	}
	
	public static Finance build()
	{
		return new Finance();
	}
	
	public static List<Finance> pivot(Double[] values, List<EjbWithId> last, Map<EjbWithId,Double[]> mapLast)
	{
		List<Finance> finances = new ArrayList<Finance>();
		if(values!=null)
		{
			for(int i=0;i<values.length;i++)
			{
				Finance f = XmlFinanceFactory.nr(i+1,values[i]);
			
				if(last!=null)
				{
					for(EjbWithId entity : last)
					{
						Double[] lastValues = mapLast.get(entity);
						if(lastValues!=null){f.getFinance().add(XmlFinanceFactory.id(entity.getId(), lastValues[i]));}
						else {f.getFinance().add(XmlFinanceFactory.id(entity.getId()));}
					}
				}
				finances.add(f);
			}
		}
		return finances;
	}
}