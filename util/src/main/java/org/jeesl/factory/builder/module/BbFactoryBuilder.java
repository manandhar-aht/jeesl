package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.bb.EjbBbBoardFactory;
import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.module.bb.JeeslBbPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;

public class BbFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,POST,USER>,
								PUB extends UtilsStatus<PUB,L,D>,
								POST extends JeeslBbPost<BB,USER>,
								USER extends EjbWithEmail>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(BbFactoryBuilder.class);
	
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<BB> cBb; public Class<BB> getClassBoard() {return cBb;}
	private final Class<PUB> cPublishing; public Class<PUB> getClassPublishing() {return cPublishing;}

	public BbFactoryBuilder(final Class<L> cL,final Class<D> cD,
								final Class<SCOPE> cScope,
								final Class<BB> cBb,
								final Class<PUB> cPublishing)
	{       
		super(cL,cD);
		this.cScope=cScope;
		this.cBb=cBb;
		this.cPublishing=cPublishing;
	}


	public EjbBbBoardFactory<L,D,SCOPE,BB,PUB> bb()
	{
		return new EjbBbBoardFactory<L,D,SCOPE,BB,PUB>(cBb);
	}
}