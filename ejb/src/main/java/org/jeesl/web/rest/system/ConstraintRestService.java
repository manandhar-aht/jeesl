package org.jeesl.web.rest.system;

import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.api.rest.system.constraint.JeeslConstraintRestExport;
import org.jeesl.api.rest.system.constraint.JeeslConstraintRestImport;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.builder.system.ConstraintFactoryBuilder;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintFactory;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintScopeFactory;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.system.Constraint;
import net.sf.ahtutils.xml.system.ConstraintScope;
import net.sf.ahtutils.xml.system.Constraints;

public class ConstraintRestService <L extends UtilsLang, D extends UtilsDescription,
									ALGCAT extends UtilsStatus<ALGCAT,L,D>,
									ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									CONCAT extends UtilsStatus<CONCAT,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
									LEVEL extends UtilsStatus<LEVEL,L,D>,
									TYPE extends UtilsStatus<TYPE,L,D>,
									RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
		extends AbstractJeeslRestService<L,D>
		implements JeeslConstraintRestExport,JeeslConstraintRestImport
{
	final static Logger logger = LoggerFactory.getLogger(ConstraintRestService.class);
	
	private final ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint;
	private JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint;
	
	private final Class<SCOPE> cScope;
	private final Class<CONCAT> cCategory;
	private final Class<CONSTRAINT> cConstraint;
	private final Class<TYPE> cType;
	
	private final EjbConstraintScopeFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> efScope;
	private final EjbConstraintFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> efConstraint;
	
	private ConstraintRestService(final String[] localeCodes, JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint,
			final ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint,
			Class<SCOPE> cScope, Class<CONCAT> cCategory, Class<CONSTRAINT> cConstraint, Class<TYPE> cType)
	{
		super(fConstraint,fbConstraint.getClassL(),fbConstraint.getClassD());
		this.fbConstraint=fbConstraint;
		
		this.fConstraint=fConstraint;
		this.cScope=cScope;
		this.cCategory=cCategory;
		this.cConstraint=cConstraint;
		this.cType=cType;

		efScope = new EjbConstraintScopeFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(cL,cD,cScope,cCategory);
		efConstraint = new EjbConstraintFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(cL,cD,cConstraint,cType);
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
						ALGCAT extends UtilsStatus<ALGCAT,L,D>,
						ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
						SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
						CONCAT extends UtilsStatus<CONCAT,L,D>,
						CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
						LEVEL extends UtilsStatus<LEVEL,L,D>,
						TYPE extends UtilsStatus<TYPE,L,D>,
						RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
	ConstraintRestService<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>
			factory(String[] localeCodes,
					JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint,
					final ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint
					, Class<SCOPE> cScope, Class<CONCAT> cCategory, Class<CONSTRAINT> cConstraint, Class<TYPE> cType)
	{
		return new ConstraintRestService<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(localeCodes,fConstraint,fbConstraint,cScope,cCategory,cConstraint,cType);
	}
	
	@Override public Container exportSystemConstraintCategories() {return xfContainer.build(fConstraint.allOrderedPosition(cCategory));}
	@Override public Container exportSystemConstraintTypes() {return xfContainer.build(fConstraint.allOrderedPosition(cType));}
	@Override public Container exportSystemConstraintLevel() {return xfContainer.build(fConstraint.allOrderedPosition(cType));}
	@Override public Constraints exportConstraints()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override public DataUpdate importSystemConstraintCategories(Container categories) {return super.importStatus(cCategory,categories,null);}
	@Override public DataUpdate importSystemConstraintTypes(Container categories) {return super.importStatus(cType,categories,null);}

	@Override public DataUpdate importConstraints(Constraints constraints)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		for(ConstraintScope xScope : constraints.getConstraintScope())
		{
			try
			{
				SCOPE eScope = efScope.importOrUpdate(fConstraint, xScope);
				dut.createSuccess(cScope);
				
				for(Constraint xConstraint : xScope.getConstraint())
				{
					try
					{
						efConstraint.importOrUpdate(fConstraint,eScope,xConstraint);
						dut.createSuccess(cConstraint);
					}
					catch (UtilsNotFoundException e) {dut.createFail(cConstraint,e);}
					catch (UtilsConstraintViolationException e) {dut.createFail(cConstraint,e);}
					catch (UtilsLockingException e) {dut.createFail(cConstraint,e);}
				}
				
			}
			catch (UtilsNotFoundException e) {dut.createFail(cScope,e);}
			catch (UtilsConstraintViolationException e) {dut.createFail(cScope,e);}
			catch (UtilsLockingException e) {dut.createFail(cScope,e);}
		}
		return dut.toDataUpdate();
	}
}