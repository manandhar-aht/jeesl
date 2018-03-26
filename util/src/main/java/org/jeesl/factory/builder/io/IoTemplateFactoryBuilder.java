package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoTemplateFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
									TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateFactoryBuilder.class);
	
	private final Class<CATEGORY> cCategory; public Class<CATEGORY> getClassCategory(){return cCategory;}

	
	public IoTemplateFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory)
	{
		super(cL,cD);
		this.cCategory=cCategory;
	}
}