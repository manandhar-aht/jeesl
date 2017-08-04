package org.jeesl.interfaces.model.system.io.importer;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslImportHandler<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								IMPORTER extends JeeslImport<L,D,CATEGORY,IMPORTER,VALIDATOR,HANDLER>,
								VALIDATOR extends JeeslImportValidator<L,D,CATEGORY,IMPORTER,VALIDATOR,HANDLER>,
								HANDLER extends JeeslImportHandler<L,D,CATEGORY,IMPORTER,VALIDATOR,HANDLER>
								>
		extends EjbWithId,EjbSaveable,EjbRemoveable,
				EjbWithCode,EjbWithPositionVisible,
				EjbWithLang<L>,EjbWithDescription<D>
{	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
}