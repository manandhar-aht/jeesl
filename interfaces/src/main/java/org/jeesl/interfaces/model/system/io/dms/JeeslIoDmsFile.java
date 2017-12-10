package org.jeesl.interfaces.model.system.io.dms;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.attribute.JeeslAttributeContainer;
import org.jeesl.interfaces.model.module.attribute.JeeslWithAttributeContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslWithFileRepositoryContainer;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisibleParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslIoDmsFile<L extends UtilsLang, S extends JeeslIoDmsSection<L,S>,
								FC extends JeeslFileContainer<?,?>, AC extends JeeslAttributeContainer<?,?>>
					extends Serializable,EjbWithId,
							EjbRemoveable,EjbPersistable,EjbSaveable,
							EjbWithPositionVisibleParent,EjbWithLang<L>,
							JeeslWithFileRepositoryContainer<FC>,JeeslWithAttributeContainer<AC>
{	
//	public enum Attributes{section}
	
	S getSection();
	void setSection(S section);

}