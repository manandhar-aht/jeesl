package org.jeesl.web.mbean.prototype.system.constraint;

import org.jeesl.api.bean.JeeslConstraintsBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslConstraintMessageBean;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.jeesl.web.mbean.system.AbstractMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.FacesContextMessage;

public class AbstractConstraintMessageBean <L extends UtilsLang, D extends UtilsDescription,
											ALGCAT extends UtilsStatus<ALGCAT,L,D>,
											ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
											SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
											CONCAT extends UtilsStatus<CONCAT,L,D>,
											CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
											LEVEL extends UtilsStatus<LEVEL,L,D>,
											TYPE extends UtilsStatus<TYPE,L,D>,
											RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
		extends AbstractMessageBean implements JeeslConstraintMessageBean<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>
{
	private static final long serialVersionUID = 1;
	final static Logger logger = LoggerFactory.getLogger(AbstractConstraintMessageBean.class);
	
	private final static boolean debugOnInfo=true;
	
	protected void postConstruct(String localeCode, JeeslTranslationBean bTranslation, JeeslConstraintsBean bConstraint)
	{
		super.initJeesl(localeCode,bTranslation);
	}
	
	@Override
	public void show(CONSTRAINT constraint)
	{
		if(debugOnInfo)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("Show "+constraint.toString()+" for "+jeeslLocaleCode);
			sb.append(" ").append(constraint.getLevel().getName().get(jeeslLocaleCode).getLang());
			sb.append(" ").append(constraint.getDescription().get(jeeslLocaleCode).getLang());
			logger.info(sb.toString());
		}
		
		FacesContextMessage.error(null, constraint.getLevel().getName().get(jeeslLocaleCode).getLang(), constraint.getDescription().get(jeeslLocaleCode).getLang());
		
	}


}