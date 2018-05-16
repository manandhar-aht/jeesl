package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainItem;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface JeeslIoDomainFacade <L extends UtilsLang, D extends UtilsDescription,
									DOMAIN extends JeeslDomain<L,DENTITY>,
									QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
									PATH extends JeeslDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
									DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
									DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
									SET extends JeeslDomainSet<L,D,DOMAIN>,
									ITEM extends JeeslDomainItem<QUERY,SET>>
	extends UtilsFacade
{
	List<DATTRIBUTE> fDomainAttributes(DENTITY entity);
}