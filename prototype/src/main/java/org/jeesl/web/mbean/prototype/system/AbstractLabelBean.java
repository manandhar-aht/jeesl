package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractLabelBean <L extends UtilsLang,D extends UtilsDescription,
								RC extends UtilsStatus<RC,L,D>,
								RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RST extends UtilsStatus<RST,L,D>,
								RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RAT extends UtilsStatus<RAT,L,D>>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLabelBean.class);
	
	private JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	private Class<RE> cRE;
	
	protected Map<String,Map<String,L>> entities; public Map<String,Map<String,L>> getEntities() {return entities;}
	protected Map<String,Map<String,Map<String,L>>> labels; public Map<String, Map<String, Map<String,L>>> getLabels() {return labels;}
	protected Map<String,Map<String,Map<String,D>>> descriptions;public Map<String, Map<String, Map<String,D>>> getDescriptions() {return descriptions;}

	protected void init(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<RE> cRE)
	{
		this.fRevision=fRevision;
		this.cRE=cRE;
		
		entities = new HashMap<String,Map<String,L>>();
		labels = new Hashtable<String,Map<String,Map<String,L>>>();
		descriptions = new Hashtable<String,Map<String,Map<String,D>>>();
		
		for(RE re : fRevision.all(cRE))
		{
			reload(re);
		}
	}
	
	private void reload(RE re)
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