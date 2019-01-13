package org.jeesl.web.mbean.prototype.system.io.ssi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

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
	
	private final List<MAPPING> mappings; public List<MAPPING> getMappings() {return mappings;}

	private MAPPING mapping; public MAPPING getMapping() {return mapping;} public void setMapping(MAPPING mapping) {this.mapping = mapping;}

	public AbstractSettingsSsiMappingBean(final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK,ENTITY> fbSsi)
	{
		this.fbSsi=fbSsi;
		mappings = new ArrayList<MAPPING>();
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
	}
	
	public void selectMapping()
	{
		logger.info(AbstractLogMessage.selectEntity(mapping));
	}
}