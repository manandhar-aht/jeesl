package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.property.JeeslProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class PropertyFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									C extends UtilsStatus<C,L,D>,
									P extends JeeslProperty<L,D,C,P>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(PropertyFactoryBuilder.class);
	
	private final Class<C> cCategory; public Class<C> getClassCategory() {return cCategory;}
	private final Class<P> cProperty; public Class<P> getClassProperty() {return cProperty;}
	
	public PropertyFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<P> cProperty)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cProperty=cProperty;
	}
}