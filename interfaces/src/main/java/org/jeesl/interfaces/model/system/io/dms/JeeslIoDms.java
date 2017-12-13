package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDms<L extends UtilsLang, D extends UtilsDescription,
							STORAGE extends JeeslFileStorage<L,D,?>,
							AS extends JeeslAttributeSet<L,D,?,?>,
							S extends JeeslIoDmsSection<L,D,S>
							>
								
		extends Serializable,EjbWithId,
				EjbSaveable,EjbRemoveable,EjbWithLang<L>
{	
	public enum Attributes{xx}
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
	
	AS getSet();
	void setSet(AS set);
	
	S getRoot();
	void setRoot(S section);
}