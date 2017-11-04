package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.status.UtilsWithSymbol;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslRevisionAttribute<L extends UtilsLang,D extends UtilsDescription,
										RC extends UtilsStatus<RC,L,D>,
										RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
										RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RST extends UtilsStatus<RST,L,D>,
										RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
										RAT extends UtilsStatus<RAT,L,D>>
		extends Serializable,EjbRemoveable,EjbPersistable,UtilsWithSymbol,EjbWithId,
				EjbWithCode,EjbWithPosition,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public static enum Type{text,number,date,amount,bool}
	
	RAT getType();
	void setType(RAT type);
	
	String getXpath();
	void setXpath(String xpath);
	
	boolean isShowPrint();
	void setShowPrint(boolean showPrint);
	
	boolean isShowWeb();
	void setShowWeb(boolean showWeb);
	
	boolean isShowName();
	void setShowName(boolean showName);
	
	boolean isShowEnclosure();
	void setShowEnclosure(boolean showEnclosure);
	
	Boolean getUi();
	void setUi(Boolean ui);
	
	Boolean getBean();
	void setBean(Boolean bean);
	
	Boolean getConstruction();
	void setConstruction(Boolean construction);
	
	Boolean getManualUser();
	void setManualUser(Boolean manualUser);
	
	Boolean getManualAdmin();
	void setManualAdmin(Boolean manualAdmin);
	
	String getDeveloperInfo();
	void setDeveloperInfo(String developerInfo);
	
	RE getEntity();
	void setEntity(RE entity);
}