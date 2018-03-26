package org.jeesl.factory.ejb.system.io.template;

import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.mail.template.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoTemplateFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
								TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateFactoryFactory.class);
	
	final Class<L> cL;
	final Class<D> cD;
	final Class<TEMPLATE> cTemplate;
	final Class<DEFINITION> cDefinition;
	final Class<TOKEN> cToken;
    
	private EjbIoTemplateFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<TEMPLATE> cTemplate, final Class<DEFINITION> cDefinition, final Class<TOKEN> cToken)
	{       
		this.cL = cL;
        this.cD = cD;
        this.cTemplate = cTemplate;
        this.cDefinition = cDefinition;
        this.cToken = cToken;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>,
					SCOPE extends UtilsStatus<SCOPE,L,D>,
					DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
					TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
	EjbIoTemplateFactoryFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> factory(final Class<L> cL,final Class<D> cD,final Class<TEMPLATE> cTemplate, final Class<DEFINITION> cDefinition, final Class<TOKEN> cToken)
	{
		return new EjbIoTemplateFactoryFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cL,cD,cTemplate,cDefinition,cToken);
	}
	
	public EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> template()
	{
		return new EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cL,cD,cTemplate);
	}
	
	public EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> definition()
	{
		return new EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cD,cDefinition);
	}
	
	public EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> token()
	{
		return new EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cL,cD,cToken);
	}
}