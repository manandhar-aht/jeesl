package net.sf.ahtutils.web.mbean.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.factory.builder.system.StatusFactoryBuilder;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.msgbundle.TranslationFactory;
import net.sf.ahtutils.msgbundle.TranslationMap;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;

public class AbstractTranslationBean<L extends UtilsLang, D extends UtilsDescription,
									LOC extends UtilsStatus<LOC,L,D>>
			implements Serializable,JeeslTranslationBean
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTranslationBean.class);
	private static final long serialVersionUID = 1L;
	
	private UtilsFacade fUtils;
	private StatusFactoryBuilder<L,D,LOC> fbStatus;
	
	
	private TranslationMap tm;
	protected final List<String> langKeys; public List<String> getLangKeys(){return langKeys;}
	
	private final List<LOC> locales; public List<LOC> getLocales() {return locales;}

	public AbstractTranslationBean()
	{
		langKeys = new ArrayList<String>();
		locales = new ArrayList<LOC>();
	}
	
	public AbstractTranslationBean(StatusFactoryBuilder<L,D,LOC> fbStatus)
	{
		langKeys = new ArrayList<String>();
		locales = new ArrayList<LOC>();
		this.fbStatus=fbStatus;
		
	}
	
	
	protected void initMap(ClassLoader cl, String fXml, UtilsFacade fUtils)
	{
		this.fUtils=fUtils;
		initMap(cl,fXml);
	}
	protected void initMap(ClassLoader cl, String fXml)
    {
		StringBuilder sb = new StringBuilder();
		sb.append("Init "+TranslationMap.class.getSimpleName()+" with "+fXml);
		try
		{
			Dir dir = JaxbUtil.loadJAXB(cl,fXml, Dir.class);
			TranslationFactory tFactory = new TranslationFactory();
			for(net.sf.exlp.xml.io.File f : dir.getFile())
			{
				tFactory.add(cl,dir.getName()+"/"+f.getName());
			}
			tm = tFactory.gettMap();
			langKeys.clear();
			langKeys.addAll(tm.getLangKeys());
			sb.append(" ").append(StringUtils.join(langKeys, ", " ));
			logger.info(sb.toString());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			sb.append(" ").append(e.getMessage());
			logger.error(sb.toString());
		}
		
		if(fbStatus!=null) {locales.addAll(fUtils.allOrderedPositionVisible(fbStatus.getClassLocale()));}
		
    }
	
	public void overrideLangKeys(String... key)
	{
	    	langKeys.clear();
	    	for(String s : key)
	    	{
	    		langKeys.add(s);
	    	}
	}
	
	public void overrideLangKeys(List<EjbWithCode> codes)
	{
	    	langKeys.clear();
	    	for(EjbWithCode s : codes)
	    	{
	    		langKeys.add(s.getCode());
	    	}
	}
    
    @Override public String get(String lang, String key){return tm.translate(lang, key);}
}