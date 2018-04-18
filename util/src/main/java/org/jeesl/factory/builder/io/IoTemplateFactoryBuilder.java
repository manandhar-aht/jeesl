package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.template.EjbIoTemplateDefinitionFactory;
import org.jeesl.factory.ejb.system.io.template.EjbIoTemplateFactory;
import org.jeesl.factory.ejb.system.io.template.EjbIoTemplateTokenFactory;
import org.jeesl.factory.txt.system.io.mail.template.TxtIoTemplateFactory;
import org.jeesl.factory.txt.system.io.mail.template.TxtIoTemplateTokenFactory;
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
									TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,SCOPE,DEFINITION,TOKEN>,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									DEFINITION extends JeeslIoTemplateDefinition<D,TYPE,TEMPLATE>,
									TOKEN extends JeeslIoTemplateToken<L,D,TEMPLATE>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoTemplateFactoryBuilder.class);
	
	private final Class<CATEGORY> cCategory; public Class<CATEGORY> getClassCategory(){return cCategory;}
	private final Class<TYPE> cType; public Class<TYPE> getClassType(){return cType;}
	private final Class<TEMPLATE> cTemplate; public Class<TEMPLATE> getClassTemplate(){return cTemplate;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope(){return cScope;}
	private final Class<DEFINITION> cDefinition; public Class<DEFINITION> getClassDefinition(){return cDefinition;}
	private final Class<TOKEN> cToken; public Class<TOKEN> getClassToken(){return cToken;}
	
	public IoTemplateFactoryBuilder(final Class<L> cL, final Class<D> cD,
									final Class<CATEGORY> cCategory,
									final Class<TYPE> cType,
									final Class<TEMPLATE> cTemplate,
									final Class<SCOPE> cScope,
									final Class<DEFINITION> cDefinition,
									final Class<TOKEN> cToken)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cType=cType;
		this.cTemplate=cTemplate;
		this.cScope=cScope;
		this.cDefinition=cDefinition;
		this.cToken=cToken;
	}
	
	public EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> ejbTemplate()
	{
		return new EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cL,cD,cTemplate);
	}
	
	public EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> ejbDefinition()
	{
		return new EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cD,cDefinition);
	}
	
	public EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> ejbTtoken()
	{
		return new EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>(cL,cD,cToken);
	}
	
	public TxtIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> txtToken()
	{
		return new TxtIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>();
	}
	
	public TxtIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN> txtTemplate()
	{
		return new TxtIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,SCOPE,DEFINITION,TOKEN>();
	}
}