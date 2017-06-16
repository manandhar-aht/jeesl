package net.sf.ahtutils.factory.xml.status;

import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Condition;
import net.sf.ahtutils.xml.status.Domain;

public class XmlDomainFactory <S extends UtilsStatus<S,L,D>,L extends UtilsLang, D extends UtilsDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlDomainFactory.class);
		
	private Condition q;
	
	public XmlDomainFactory(Condition q)
	{
		this.q=q;
	}
	
	public Condition build(S ejb)
	{
		Condition xml = new Condition();

		if(q.isSetCode()){xml.setCode(ejb.getCode());}
		if(q.isSetPosition()){xml.setPosition(ejb.getPosition());}
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
	
	public static Domain buildLabel(String code, String label)
	{
		Domain xml = new Domain();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}