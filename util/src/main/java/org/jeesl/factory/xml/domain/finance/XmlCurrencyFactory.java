package org.jeesl.factory.xml.domain.finance;

import org.jeesl.factory.xml.system.lang.XmlLangFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.module.currency.UtilsCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.finance.Currency;

public class XmlCurrencyFactory <L extends UtilsLang, C extends UtilsCurrency<L>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlCurrencyFactory.class);

	private final String localeCode;
	private final Currency q;

	private XmlLangsFactory<L> xfLangs;
	
	public XmlCurrencyFactory(String localeCode, Currency q)
	{
		this.localeCode=localeCode;
		this.q=q;
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
	}
	
	public Currency build(C currency)
	{
		Currency xml = build();
		
		if(q.isSetId()) {xml.setId(currency.getId());}
		if(q.isSetCode()) {xml.setCode(currency.getCode());}
		if(q.isSetSymbol()) {xml.setSymbol(currency.getSymbol());}
		
		if(q.isSetLabel() && localeCode!=null){xml.setLabel(XmlLangFactory.label(localeCode,currency));}
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(currency.getName()));}
		
		return xml;
	}
	
	public static Currency build()
	{
		return new Currency();
	}
}