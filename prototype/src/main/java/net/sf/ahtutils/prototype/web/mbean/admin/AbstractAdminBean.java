package net.sf.ahtutils.prototype.web.mbean.admin;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractAdminBean <L extends UtilsLang,D extends UtilsDescription>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminBean.class);
	
	protected FacesMessageBean bMessage;
	
	protected boolean debugOnInfo;
	protected String[] langs;
	
	protected EjbLangFactory<L> efLang;
	protected EjbDescriptionFactory<D> efDescription;
	
	public AbstractAdminBean()
	{
		debugOnInfo = false;
	}
	
	public void initAdmin(String[] langs, final Class<L> cLang, final Class<D> cDescription, FacesMessageBean bMessage)
	{	
		this.langs=langs;
		this.bMessage=bMessage;
		
		efLang = new EjbLangFactory<L>(cLang);
		efDescription = new EjbDescriptionFactory<D>(cDescription);
	}
}