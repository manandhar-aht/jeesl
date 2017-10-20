package org.jeesl.interfaces.model.module.ts;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslTimeSeries <L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslTsScope<L,D,?,SCOPE,?,?,?,?,BRIDGE,?,INT,?,?,?,?,?>,
									BRIDGE extends JeeslTsBridge<L,D,?,SCOPE,?,?,?,?,BRIDGE,?,INT,?,?,?,?,?>,
									INT extends UtilsStatus<INT,L,D>>
		extends EjbWithId
{
	public enum Attributes{scope,interval,bridge}
	
	SCOPE getScope();
	void setScope(SCOPE scope);
	
	INT getInterval();
	void setInterval(INT interval);
	
	BRIDGE getBridge();
	void setBridge(BRIDGE bridge);
}