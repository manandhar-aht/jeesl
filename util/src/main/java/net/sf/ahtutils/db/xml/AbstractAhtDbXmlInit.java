package net.sf.ahtutils.db.xml;

import org.jeesl.model.xml.system.io.db.Db;
import org.jeesl.util.db.JeeslStatusDbUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractAhtDbXmlInit <L extends UtilsLang, D extends UtilsDescription>
	extends AbstractDbRestInit
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlInit.class);

	protected UtilsIdMapper idMapper;
	protected JeeslStatusDbUpdater<L,D,?,?> asdi;
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource, UtilsIdMapper idMapper, JeeslStatusDbUpdater<L,D,?,?> asdi)
	{
		super(dbSeed, datasource,null,idMapper);
		this.idMapper=idMapper;
		this.asdi=asdi;
	}
}