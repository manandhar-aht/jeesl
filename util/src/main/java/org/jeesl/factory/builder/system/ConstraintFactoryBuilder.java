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
	private final Class<CONCAT> cCategory; public Class<CONCAT> getClassConstraintCategory() {return cCategory;}
	private final Class<CONSTRAINT> cConstraint; public Class<CONSTRAINT> getClassConstraint() {return cConstraint;}
	private final Class<LEVEL> cLevel; public Class<LEVEL> getClassConstraintLevel() {return cLevel;}
	private final Class<TYPE> cType; public Class<TYPE> getClassConstraintType() {return cType;}
	private final Class<RESOLUTION> cResolution; public Class<RESOLUTION> getClassConstraintResolution() {return cResolution;}
	
	public ConstraintFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<ALGCAT> cAlgorithmCategory,
								final Class<ALGO> cAlgorithm,
								final Class<SCOPE> cScope,
								final Class<CONCAT> cCategory,
								final Class<CONSTRAINT> cConstraint,
								final Class<LEVEL> cLevel,
								final Class<TYPE> cType,
								final Class<RESOLUTION> cResolution)
	{
		super(cL,cD);
		this.cAlgorithmCategory=cAlgorithmCategory;
		this.cAlgorithm=cAlgorithm;
		this.cScope=cScope;
		this.cCategory=cCategory;
		this.cConstraint=cConstraint;
		this.cLevel=cLevel;
		this.cType=cType;
		this.cResolution=cResolution;
	}


	public EjbConstraintAlgorithmFactory<L,D,ALGCAT,ALGO> algorithm()
	{
		return new EjbConstraintAlgorithmFactory<L,D,ALGCAT,ALGO>(cAlgorithm);
	}

}