package org.jeesl.api.facade.module;

import java.util.List;

import org.jeesl.interfaces.model.module.bb.JeeslBb;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public interface JeeslBbFacade <L extends UtilsLang,D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBb<L,D,SCOPE,BB,USER>,
								USER extends EjbWithEmail>
			extends UtilsFacade
{	
	List<BB> fBulletinBoards(SCOPE scope, long refId);
}