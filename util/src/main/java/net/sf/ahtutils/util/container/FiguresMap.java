package net.sf.ahtutils.util.container;

import java.util.List;
import java.util.Map;

import org.jeesl.factory.xml.domain.finance.XmlFiguresFactory;

import net.sf.ahtutils.xml.finance.Figures;
import net.sf.ahtutils.xml.finance.Finance;
import net.sf.ahtutils.xml.text.Remark;

public class FiguresMap
{
	private List<Figures> figures;
	public List<Figures> getFigures() {return figures;}

	private Map<Long,Map<String,Remark>> remarks;
	public Map<Long, Map<String, Remark>> getRemarks() {return remarks;}

	private Map<Long,Map<String,Finance>> finances;
	public Map<Long, Map<String, Finance>> getFinances() {return finances;}
	
	public FiguresMap(List<Figures> figures)
	{
		this.figures=figures;
		remarks = XmlFiguresFactory.toMapRemark(figures);
		finances = XmlFiguresFactory.toMapFinance(figures);
	}

}