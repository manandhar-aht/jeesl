package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslTsData <L extends UtilsLang, D extends UtilsDescription,
								TS extends JeeslTimeSeries<L,D,?,?,?>,
								TRANSACTION extends JeeslTsTransaction<L,D,?,?,?,TS,TRANSACTION,?,?,?,?,?,SAMPLE,?,WS,?>,
								SAMPLE extends JeeslTsSample, 
								WS extends UtilsStatus<WS,L,D>>
		extends EjbWithId,EjbWithRecord,EjbSaveable
{
	public enum Attributes{transaction,timeSeries,workspace,record,value}
	
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