package org.jeesl.web.mbean.prototype.admin.system.feature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslFeatureManagerBean;
import org.jeesl.interfaces.model.system.JeeslFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class AbstractFeatureManagerBean <F extends JeeslFeature>
		implements Serializable,JeeslFeatureManagerBean<F>
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFeatureManagerBean.class);
	
	private UtilsFacade fUtils;
	
	private Class<F> cFeature;
	
	private List<F> features; public List<F> getFeatures() {return features;}
	protected Map<String,Boolean> map; public Map<String, Boolean> getMap() {return map;}
	
	public void initSuper(UtilsFacade fUtils, final Class<F> cFeature)
	{
		this.fUtils=fUtils;
		this.cFeature=cFeature;
		map = new HashMap<String,Boolean>();
		realodFeatures();
	}
	
	public boolean has(String code)
	{
		return (map.containsKey(code) && map.get(code));
	}

	@Override
	public void realodFeatures()
	{
		features = fUtils.all(cFeature);
		map.clear();
		for(F feature : features)
		{
			map.put(feature.getCode(),feature.isVisible());
		}
	}
}