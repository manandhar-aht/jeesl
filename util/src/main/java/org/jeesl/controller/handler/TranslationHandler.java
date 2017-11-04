package org.jeesl.controller.handler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TranslationHandler<L extends UtilsLang,D extends UtilsDescription,
								RC extends UtilsStatus<RC,L,D>,
								RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
								RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RST extends UtilsStatus<RST,L,D>,
								RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RAT extends UtilsStatus<RAT,L,D>>
	implements Serializable,JeeslLabelBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
{
	final static Logger logger = LoggerFactory.getLogger(TranslationHandler.class);
	private static final long serialVersionUID = 1L;
	
	private final Class<RE> cRE;
	
	private final JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	private final Map<String,Map<String,L>> entities; public Map<String,Map<String,L>> getEntities() {return entities;}
	private final Map<String,Map<String,Map<String,L>>> labels; public Map<String, Map<String, Map<String,L>>> getLabels() {return labels;}
	private final Map<String,Map<String,Map<String,D>>> descriptions;public Map<String, Map<String, Map<String,D>>> getDescriptions() {return descriptions;}

	public TranslationHandler(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<RE> cRE)
	{
		this.cRE = cRE;
		this.fRevision=fRevision;
		
        entities = new HashMap<String,Map<String,L>>();
        labels = new HashMap<String,Map<String,Map<String,L>>>();
        descriptions = new HashMap<String,Map<String,Map<String,D>>>();
        
        List<RE> list = fRevision.all(cRE);
        logger.info("building "+list.size());
        
		for(RE re : list)
		{
			load(re);
		}
	}
	
	@Override public void reload(RE re)
	{
		try
		{
			Class<?> c = Class.forName(re.getCode());
			
			if(entities.containsKey(c.getSimpleName())) {entities.remove(c.getSimpleName());}
			if(labels.containsKey(c.getSimpleName())) {labels.remove(c.getSimpleName());}
			if(descriptions.containsKey(c.getSimpleName())) {descriptions.remove(c.getSimpleName());}
		}
		catch (ClassNotFoundException e) {logger.warn("CNFE: "+re.getCode());}
	}
	
	public void load(RE re)
	{
		try
		{
			Class<?> c = Class.forName(re.getCode());
			
			re = fRevision.load(cRE, re);
			if(entities.containsKey(c.getSimpleName())){logger.warn("Duplicate classs in Revisions "+re.getCode());}
			
			entities.put(c.getSimpleName(), re.getName());
			labels.put(c.getSimpleName(), new Hashtable<String,Map<String,L>>());
			descriptions.put(c.getSimpleName(), new Hashtable<String,Map<String,D>>());
			
			for(RA attribute : re.getAttributes())
			{
				if(attribute.getCode()!=null && attribute.getCode().trim().length()>0)
				{
					labels.get(c.getSimpleName()).put(attribute.getCode(), attribute.getName());
					descriptions.get(c.getSimpleName()).put(attribute.getCode(), attribute.getDescription());
				}
			}
		}
		catch (ClassNotFoundException e) {logger.warn("CNFE: "+re.getCode());}
	}
}