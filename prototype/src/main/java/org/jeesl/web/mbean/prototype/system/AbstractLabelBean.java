package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.bean.JeeslLabelResolver;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.TranslationHandler;
import org.jeesl.controller.processor.FacadeLabelResolver;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractLabelBean <L extends UtilsLang,D extends UtilsDescription,
								RE extends JeeslRevisionEntity<L,D,?,?,RA>,
								RA extends JeeslRevisionAttribute<L,D,RE,?,?>>
								
					implements Serializable,JeeslLabelBean<RE>,JeeslLabelResolver
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLabelBean.class);
	
	private TranslationHandler<L,D,RE,RA> th;
	private final IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?> fbRevision;
	private FacadeLabelResolver<RE,RA> flr;

	private final Map<RE,Map<MultiKey,String>> mapXpath;
	public Map<String, Map<String,L>> getEntities() {return th.getEntities();}
	public Map<String, Map<String, Map<String,L>>> getLabels() {return th.getLabels();}
	public Map<String, Map<String, Map<String,D>>> getDescriptions() {return th.getDescriptions();}
	
	
	public Map<String,RE> getMapEntities() {return th.getMapEntities();}

	
	public AbstractLabelBean(IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?> fbRevision)
	{
		this.fbRevision=fbRevision;
		mapXpath = new HashMap<RE,Map<MultiKey,String>>();
	}
	
	protected void init(JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision, final Class<RE> cRE)
	{		
		th = new TranslationHandler<L,D,RE,RA>(fRevision,cRE);
		if(fbRevision!=null)
		{
			flr = new FacadeLabelResolver<RE,RA>(fRevision,fbRevision);
		}
		
	}
	
	@Override public void reload(RE re)
	{
		th.reload(re);
		if(mapXpath.containsKey(re)) {mapXpath.remove(re);}
	}
	
	@Override
	public <E extends Enum<E>> String xpath(String localeCode, Class<?> c, E code)
	{
		if(!th.getMapEntities().containsKey(c.getSimpleName()))
		{
			logger.warn("Entity not handled in Engine: "+c.getSimpleName());
			return "@id";
		}
		
		RE re = th.getMapEntities().get(c.getSimpleName());
		if(!mapXpath.containsKey(re)) {mapXpath.put(re, new HashMap<MultiKey,String>());}
		
		MultiKey key = new MultiKey(localeCode,code.toString());
		if(!mapXpath.get(re).containsKey(key))
		{
			mapXpath.get(re).put(key,flr.xpath(localeCode, c, code));
		}
		return mapXpath.get(re).get(key);
	}
	
	@Override
	public String entity(String localeCode, Class<?> c)
	{
//		logger.info("Getting entity ["+localeCode+"] for "+c.getSimpleName());
		if(!th.getMapEntities().containsKey(c.getSimpleName()))
		{
			logger.warn("Entity not handled in Engine: "+c.getSimpleName());
			return "-NO.TRANSLATION-";
		}
		
		return th.getMapEntities().get(c.getSimpleName()).getName().get(localeCode).getLang();
	}
}