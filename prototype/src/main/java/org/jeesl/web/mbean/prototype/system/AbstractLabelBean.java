package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.Map;

import org.jeesl.api.bean.JeeslLabelBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.handler.TranslationHandler;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractLabelBean <L extends UtilsLang,D extends UtilsDescription,
								RC extends UtilsStatus<RC,L,D>,
								RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RST extends UtilsStatus<RST,L,D>,
								RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RAT extends UtilsStatus<RAT,L,D>>
								
					implements Serializable,JeeslLabelBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLabelBean.class);
	
	@SuppressWarnings("unused")
	private JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	private TranslationHandler<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> th;
	
	public Map<String,Map<String,L>> getEntities() {return th.getEntities();}
	public Map<String, Map<String, Map<String,L>>> getLabels() {return th.getLabels();}
	public Map<String, Map<String, Map<String,D>>> getDescriptions() {return th.getDescriptions();}

	protected void init(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<RE> cRE)
	{
		this.fRevision=fRevision;
		
		th = new TranslationHandler<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(fRevision,cRE);
	}
	
	@Override public void reload(RE re) {th.reload(re);}
}