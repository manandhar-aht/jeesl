package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.Map;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.bean.JeeslLabelResolver;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.TranslationHandler;
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
	
	public Map<String, Map<String,L>> getEntities() {return th.getEntities();}
	public Map<String, Map<String, Map<String,L>>> getLabels() {return th.getLabels();}
	public Map<String, Map<String, Map<String,D>>> getDescriptions() {return th.getDescriptions();}
	
	public Map<String,RE> getMapEntities() {return th.getMapEntities();}

	public AbstractLabelBean()
	{
		
	}
	
	protected void init(JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision, final Class<RE> cRE)
	{		
		th = new TranslationHandler<L,D,RE,RA>(fRevision,cRE);
	}
	
	@Override public void reload(RE re)
	{
		
		th.reload(re);
	}
	
	@Override
	public <E extends Enum<E>> String xpath(Class<?> c, E code)
	{
		return "@name";
	}
	@Override
	public String entity(String localeCode, Class<?> c)
	{
		return th.getMapEntities().get(c.getName()).getName().get(localeCode).getLang();
	}
	
	
}