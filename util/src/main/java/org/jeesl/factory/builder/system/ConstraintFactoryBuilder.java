package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.constraint.algorithm.EjbConstraintAlgorithmFactory;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class ConstraintFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									ALGCAT extends UtilsStatus<ALGCAT,L,D>,
									ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CONCAT extends UtilsStatus<CONCAT,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									LEVEL extends UtilsStatus<LEVEL,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(ConstraintFactoryBuilder.class);
	
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<ALGCAT> cAlgorithmCategory; public Class<ALGCAT> getClassAlgorithmCategory() {return cAlgorithmCategory;}
	private final Class<ALGO> cAlgorithm; public Class<ALGO> getClassAlgorithm() {return cAlgorithm;}
	
	private final Class<CONSTRAINT> cConstraint; public Class<CONSTRAINT> getClassConstraint() {return cConstraint;}
	
	public ConstraintFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<ALGCAT> cAlgorithmCategory,
								final Class<ALGO> cAlgorithm,
								final Class<SCOPE> cScope,
								final Class<CONSTRAINT> cConstraint)
	{
		super(cL,cD);
		this.cAlgorithmCategory=cAlgorithmCategory;
		this.cAlgorithm=cAlgorithm;
		this.cScope=cScope;
		this.cConstraint=cConstraint;
	}


	public EjbConstraintAlgorithmFactory<L,D,ALGCAT,ALGO> algorithm()
	{
		return new EjbConstraintAlgorithmFactory<L,D,ALGCAT,ALGO>(cAlgorithm);
	}

}