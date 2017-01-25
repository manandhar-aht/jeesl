package org.jeesl.web.mbean.prototype.admin.system.io.db;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

import org.jeesl.interfaces.facade.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminDbDumpBean <L extends UtilsLang,D extends UtilsDescription,
									HOST extends UtilsStatus<HOST,L,D>,
									DUMP extends JeeslDbDumpFile<L,D,HOST,DUMP,STATUS>,
									STATUS extends UtilsStatus<STATUS,L,D>>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbDumpBean.class);
	
	protected JeeslIoDbFacade fDb;
	
	protected List<DUMP> dumps; public List<DUMP> getDumps(){return dumps;}

	private Class<DUMP> cDump;
	
	public void initSuper(final Class<DUMP> cDump)
	{
		this.cDump=cDump;
		refreshList();
	}
	
	protected void refreshList()
	{
		dumps = fDb.allOrdered(cDump,"startDate",false);
	}
}