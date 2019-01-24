package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.jeesl.model.xml.system.io.db.Db;
import org.jeesl.util.db.JeeslStatusDbUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAhtDbXmlInit <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
	extends AbstractDbRestInit
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlInit.class);

	protected UtilsIdMapper idMapper;
	protected JeeslStatusDbUpdater<L,D,S,?> asdi;
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource, UtilsIdMapper idMapper, JeeslStatusDbUpdater<L,D,S,?> asdi)
	{
		super(dbSeed, datasource,null,idMapper);
		this.idMapper=idMapper;
		this.asdi=asdi;
	}
}