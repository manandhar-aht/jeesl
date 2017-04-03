package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jeesl.api.bean.JeeslFeatureManagerBean;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractFeatureManagerBean <L extends UtilsLang,D extends UtilsDescription>
		extends AbstractAdminBean<L,D>
		implements Serializable,JeeslFeatureManagerBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFeatureManagerBean.class);
	
	private UtilsFacade fUtils;
	
	protected Map<String,Boolean> map; public Map<String, Boolean> getMap() {return map;}

	public void initSuper(UtilsFacade fUtils)
	{
		this.fUtils=fUtils;
		map = new HashMap<String,Boolean>();
	}

	@Override
	public void realodFeatures()
	{

	}
}