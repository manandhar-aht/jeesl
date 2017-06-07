package net.sf.ahtutils.controller.factory.ejb.finance;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.finance.Currency;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.module.currency.UtilsCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbCurrencyFactory<C extends UtilsCurrency<L>, L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(EjbCurrencyFactory.class);
	
	final Class<C> clCurrency;
    final Class<L> langClass;
    
    private EjbLangFactory<L> ejbLangFactory;
	
    public EjbCurrencyFactory(final Class<C> clCurrency, final Class<L> langClass)
    {
        this.clCurrency = clCurrency;
        this.langClass = langClass;
        
        ejbLangFactory = EjbLangFactory.factory(langClass);
    } 
    
    public static <C extends UtilsCurrency<L>, L extends UtilsLang> EjbCurrencyFactory<C, L>
    		factory(final Class<C> clCurrency, final Class<L> langClass)
    {
        return new EjbCurrencyFactory<C, L>(clCurrency, langClass);
    }
	
	public C create(String code, String symbol) throws InstantiationException, IllegalAccessException
	{
        C c = clCurrency.newInstance();
        c.setCode(code);
        c.setSymbol(symbol);
        return c;
    }
	
	public C create(Currency xml) throws UtilsConstraintViolationException
	{
		if(!xml.isSetLangs()){throw new UtilsConstraintViolationException("No <langs> available for "+JaxbUtil.toString(xml));}
		if(!xml.isSetCode()){throw new UtilsConstraintViolationException("No @code available for "+JaxbUtil.toString(xml));}
		if(!xml.isSetSymbol()){throw new UtilsConstraintViolationException("No @symbol available for "+JaxbUtil.toString(xml));}
		
		try
		{
			C c= create(xml.getCode(),xml.getSymbol());
			c.setName(ejbLangFactory.getLangMap(xml.getLangs()));
			return c;
		}
		catch (InstantiationException e) {throw new UtilsConstraintViolationException(e.getMessage());}
		catch (IllegalAccessException e) {throw new UtilsConstraintViolationException(e.getMessage());}
    }
}