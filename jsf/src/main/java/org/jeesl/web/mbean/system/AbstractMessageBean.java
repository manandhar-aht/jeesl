package org.jeesl.web.mbean.system;

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
	protected String jeeslLocaleCode;
	
	public void initJeesl(String localeCode, JeeslTranslationBean bTranslation)
	{
		this.jeeslLocaleCode=localeCode;
		this.jeeslTranslationBean=bTranslation;
	}
	
	public void growlSuccessSaved(){growlSuccess("jeeslFmObjectSaved");}
	public void growlSuccessRemoved(){growlSuccess("fmObjectRemoved");}
	
	public <T extends EjbWithId> void growlSuccessSaved(T t){growlSuccess("jeeslFmObjectSaved");}
	
	public void growlError(String key)
	{
		FacesContextMessage.info("growl", jeeslTranslationBean.get(jeeslLocaleCode, "fmError"), jeeslTranslationBean.get(jeeslLocaleCode, key));
	}
	
	public void growlSuccess(String key)
	{
		FacesContextMessage.info("growl", jeeslTranslationBean.get(jeeslLocaleCode, "jeeslFmSuccess"), jeeslTranslationBean.get(jeeslLocaleCode, key));
	}
	
//	@Override
	public void errorConstraintViolationDuplicateObject() {FacesContextMessage.error(jeeslTranslationBean.get(jeeslLocaleCode,"fmConstraintViolationDuplicateObject"),"");}
	
	
//	@Override
	public <E extends Enum<E>> void errorConstraintViolationDuplicateObject(E id) {FacesContextMessage.error(id.toString(),jeeslTranslationBean.get(jeeslLocaleCode,"fmConstraintViolationDuplicateObject"),"");}
	
	public void errorConstraintViolationInUse() {errorConstraintViolationInUse(null);}
	public void errorConstraintViolationInUse(String id) {errorText(id,jeeslTranslationBean.get(jeeslLocaleCode,"fmConstraintViolationInUse"));}
	public void errorText(String text){errorTextWithId(null,text);}
	public <E extends Enum<E>> void errorText(E id, String text) {errorTextWithId(id.toString(),text);}
	public void errorText(String id, String text){errorTextWithId(id,text);}
	private void errorTextWithId(String id, String text)
	{
		FacesContextMessage.error(id, jeeslTranslationBean.get(jeeslLocaleCode, "fmError"), text);
	}
}