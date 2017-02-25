package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.jsf.util.FacesContextMessage;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class AbstractMessageBean implements Serializable
{
	private static final long serialVersionUID = 1;
	final static Logger logger = LoggerFactory.getLogger(AbstractMessageBean.class);

	private JeeslTranslationBean jeeslTranslationBean;
	private String jeeslLocaleCode;
	
	public void initJeesl(String localeCode, JeeslTranslationBean bTranslation)
	{
		this.jeeslLocaleCode=localeCode;
		this.jeeslTranslationBean=bTranslation;
	}
	
	public void growlSuccessSaved(){growlSuccess("jeeslFmObjectSaved");}
	public <T extends EjbWithId> void growlSuccessSaved(T t){growlSuccess("jeeslFmObjectSaved");}
	
	public void growlSuccess(String key)
	{
		FacesContextMessage.info("growl", jeeslTranslationBean.get(jeeslLocaleCode, "jeeslFmSuccess"), jeeslTranslationBean.get(jeeslLocaleCode, key));
	}
}