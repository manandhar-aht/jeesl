package org.jeesl.interfaces.model.system.io.mail.template;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoTemplateToken<L extends UtilsLang,D extends UtilsDescription,
								TEMPLATE extends JeeslIoTemplate<L,D,?,?,?,?>,
								TOKENTYPE extends UtilsStatus<TOKENTYPE,?,D>>
		extends Serializable,EjbPersistable,EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>,JeeslWithType<TOKENTYPE>
{	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	String getExample();
	void setExample(String example);
	
	TOKENTYPE getType();
	void setType(TOKENTYPE type);
}