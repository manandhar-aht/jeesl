package org.jeesl.interfaces.model.module.ts.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.ts.core.JeeslTimeSeries;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsData <TS extends JeeslTimeSeries<?,?,?>,
								TRANSACTION extends JeeslTsTransaction<?,?,?,?>,
								SAMPLE extends JeeslTsSample, 
								WS extends UtilsStatus<WS,?,?>>
		extends EjbWithId,EjbWithRecord,EjbSaveable,Serializable,EjbRemoveable,EjbPersistable
{
	enum Attributes{transaction,timeSeries,workspace,record,value}
	
	TS getTimeSeries();
	void setTimeSeries(TS timeSeries);
	
	WS getWorkspace();
	void setWorkspace(WS workspace);
	
	TRANSACTION getTransaction();
	void setTransaction(TRANSACTION transaction);
	
	SAMPLE getSample();
	void setSample(SAMPLE sample);
	
	Double getValue();
	void setValue(Double value);
}