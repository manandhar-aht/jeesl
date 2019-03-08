package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.interfaces.model.module.bb.JeeslBb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class BbFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBb<L,D,SCOPE,BB,USER>,
								USER extends EjbWithEmail>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(BbFactoryBuilder.class);
	
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}

	public BbFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<SCOPE> cScope)
	{       
		super(cL,cD);
		this.cScope=cScope;
	}


}