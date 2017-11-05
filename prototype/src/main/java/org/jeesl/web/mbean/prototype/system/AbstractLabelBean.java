package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.Map;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.TranslationHandler;
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

public class AbstractLabelBean <L extends UtilsLang,D extends UtilsDescription,
								
								RE extends JeeslRevisionEntity<L,D,?,?,RA>,
								
								RA extends JeeslRevisionAttribute<L,D,RE,?,?>>
								
					implements Serializable,JeeslLabelBean<RE>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLabelBean.class);
	
	private TranslationHandler<L,D,RE,RA> th;
	
	public Map<String, Map<String,L>> getEntities() {return th.getEntities();}
	public Map<String, Map<String, Map<String,L>>> getLabels() {return th.getLabels();}
	public Map<String, Map<String, Map<String,D>>> getDescriptions() {return th.getDescriptions();}

	protected void init(JeeslIoRevisionFacade<L,D,?,?,?,?,?,RE,?,RA,?,?> fRevision, final Class<RE> cRE)
	{		
		th = new TranslationHandler<L,D,RE,RA>(fRevision,cRE);
	}
	
	@Override public void reload(RE re) {th.reload(re);}
}