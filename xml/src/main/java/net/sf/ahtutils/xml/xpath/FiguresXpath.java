package net.sf.ahtutils.xml.xpath;

import java.util.List;

import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiguresXpath
{
	final static Logger logger = LoggerFactory.getLogger(FiguresXpath.class);
	
	public static synchronized Finance getFinance(Figures figures,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(figures);
		
		StringBuffer sb = new StringBuffer();
		sb.append("finance[@code='").append(code).append("']");
		
		@SuppressWarnings("unchecked")
		List<Finance> list = (List<Finance>)context.selectNodes(sb.toString());
		if(list.size()==0){throw new ExlpXpathNotFoundException("No "+Finance.class.getSimpleName()+" for code="+code);}
		else if(list.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Finance.class.getSimpleName()+" for code="+code);}
		return list.get(0);
	}
	
	public static Finance getFinance(List<Finance> list, int nr) throws ExlpXpathNotFoundException
	{
		for(Finance f : list)
		{
			if(f.getNr()==nr){return f;}
		}
		throw new ExlpXpathNotFoundException("No "+Finance.class.getSimpleName()+" for nr="+nr);
		
	}
}