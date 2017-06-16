package net.sf.ahtutils.factory.xml.status;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.ProcurementMethod;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlProcurementMethodFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlProcurementMethodFactory.class);
		
	private ProcurementMethod q;
	
	public XmlProcurementMethodFactory(ProcurementMethod q)
	{
		this.q=q;
	}
	
	public ProcurementMethod build(S ejb){return build(ejb,null);}
	public ProcurementMethod build(S ejb, String group)
	{
		ProcurementMethod xml = new ProcurementMethod();
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
		xml.setGroup(group);
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(ejb.getName()));
		}
		if(q.isSetDescriptions())
		{

		}
		
		return xml;
	}
	
	public static ProcurementMethod build(String code)
	{
		ProcurementMethod xml = new ProcurementMethod();
		xml.setCode(code);
		return xml;
	}
}