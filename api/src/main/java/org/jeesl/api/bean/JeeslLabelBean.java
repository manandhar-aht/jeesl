package org.jeesl.api.bean;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslLabelBean<L extends UtilsLang,D extends UtilsDescription,
								RC extends UtilsStatus<RC,L,D>,
								RV extends JeeslRevisionView<L,D,RVM>,
								RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
								RS extends JeeslRevisionScope<L,D,RC,RA>,
								RST extends UtilsStatus<RST,L,D>,
								RE extends JeeslRevisionEntity<L,D,RC,REM,RA>,
								REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
								RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>, RER extends UtilsStatus<RER,L,D>,
								RAT extends UtilsStatus<RAT,L,D>>
{	
	void reload(RE re);
}