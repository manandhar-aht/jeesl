package org.jeesl.web.mbean.prototype.system.io.ssi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.controller.handler.tuple.JsonTuple1Handler;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.JsonUtil;

public class AbstractSettingsSsiMappingBean <L extends UtilsLang,D extends UtilsDescription,
										SYSTEM extends JeeslIoSsiSystem,
										MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
										DATA extends JeeslIoSsiData<MAPPING,LINK>,
										LINK extends UtilsStatus<LINK,L,D>,
										ENTITY extends JeeslRevisionEntity<?,?,?,?,?>>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsSsiMappingBean.class);
	
	private final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fbSsi;
	private JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fSsi;
	
	private final JsonTuple1Handler<MAPPING> th; public JsonTuple1Handler<MAPPING> getTh() {return th;}
	
	private final List<MAPPING> mappings; public List<MAPPING> getMappings() {return mappings;}

	private MAPPING mapping; public MAPPING getMapping() {return mapping;} public void setMapping(MAPPING mapping) {this.mapping = mapping;}

	public AbstractSettingsSsiMappingBean(final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fbSsi)
	{
		this.fbSsi=fbSsi;
		mappings = new ArrayList<MAPPING>();
		th = new JsonTuple1Handler<MAPPING>(fbSsi.getClassMapping());
	}

	public void postConstructSsiMapping(JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fSsi)
	{
		this.fSsi=fSsi;
		reload();
	}

	private void reload()
	{
		mappings.clear();
		mappings.addAll(fSsi.all(fbSsi.getClassMapping()));
		
		Json1Tuples<MAPPING> tuples = fSsi.tpMapping();
		JsonUtil.info(tuples);
		
		th.init(tuples);
	}
	
	public void selectMapping()
	{
		logger.info(AbstractLogMessage.selectEntity(mapping));
	}
}