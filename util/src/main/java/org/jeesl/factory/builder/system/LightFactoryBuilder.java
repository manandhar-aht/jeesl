package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class LightFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								LIGHT extends JeeslTrafficLight<L,D,SCOPE>,
								SCOPE extends UtilsStatus<SCOPE,L,D>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LightFactoryBuilder.class);
	
	private final Class<LIGHT> cLight; public Class<LIGHT> getClassLight() {return cLight;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	
	public LightFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<LIGHT> cLight, final Class<SCOPE> cScope)
	{
		super(cL,cD);
		this.cLight=cLight;
		this.cScope=cScope;
	}
}