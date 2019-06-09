package org.jeesl.interfaces.model.system.io.db;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslDbDump<SYSTEM extends JeeslIoSsiSystem,
							FILE extends JeeslDbDumpFile<?,?,?>
							>
					extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,EjbWithRecord,EjbWithName
{
	public static enum Attributes{record};
	
	long getSize();
	void setSize(long size);
	
	List<FILE> getFiles();
	void setFiles(List<FILE> files);
}