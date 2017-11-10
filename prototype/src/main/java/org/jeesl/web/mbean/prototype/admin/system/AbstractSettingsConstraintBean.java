package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.controller.handler.ui.helper.UiTwiceClickHelper;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintFactory;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintScopeFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSettingsConstraintBean <L extends UtilsLang, D extends UtilsDescription,
										ALGCAT extends UtilsStatus<ALGCAT,L,D>,
										ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
										SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
										LEVEL extends UtilsStatus<LEVEL,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsConstraintBean.class);
	
	private JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint;
	
	private final Class<SCOPE> cScope;
	private final Class<CATEGORY> cCategory;
	private final Class<CONSTRAINT> cConstraint;
	private final Class<LEVEL> cLevel;
	private final Class<TYPE> cType;
	
	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<CONSTRAINT> constraints; public List<CONSTRAINT> getConstraints() {return constraints;}
	private List<TYPE> types; public List<TYPE> getTypes() {return types;}
	private List<LEVEL> levels; public List<LEVEL> getLevels() {return levels;}
	
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private CONSTRAINT constraint; public CONSTRAINT getConstraint() {return constraint;} public void setConstraint(CONSTRAINT constraint) {this.constraint = constraint;}
	
	private final EjbConstraintScopeFactory<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION> efScope;
	private final EjbConstraintFactory<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION> efConstraint;
	
	protected SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}
	private final UiTwiceClickHelper ui2; public UiTwiceClickHelper getUi2() {return ui2;}

	public AbstractSettingsConstraintBean(Class<L> cL, Class<D> cD, Class<SCOPE> cScope, Class<CATEGORY> cCategory, Class<CONSTRAINT> cConstraint, Class<LEVEL> cLevel, Class<TYPE> cType)
	{
		super(cL,cD);
		this.cScope=cScope;
		this.cCategory=cCategory;
		this.cConstraint=cConstraint;
		this.cLevel=cLevel;
		this.cType=cType;
		
		ui2 = new UiTwiceClickHelper();
		efScope = new EjbConstraintScopeFactory<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(cL,cD,cScope,cCategory);
		efConstraint = new EjbConstraintFactory<L,D,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(cL,cD,cConstraint,cType);
	}
	
	protected void initSuper(String[] localeCodes, FacesMessageBean bMessage, JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CATEGORY,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fConstraint=fConstraint;
		sbhCategory = new SbMultiHandler<CATEGORY>(cCategory,fConstraint.allOrderedPosition(cCategory),this);
		types = fConstraint.allOrderedPosition(cType);
		levels = fConstraint.allOrderedPosition(cLevel);
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		reloadScopes();
	}
	
	private void reset(boolean rScope, boolean rConstraint)
	{
		if(rScope){scope=null;ui2.checkA(scope);}
		if(rConstraint){constraint=null;ui2.checkB(constraint);}
	}

	private void reloadScopes()
	{
		scopes = fConstraint.all(cScope);
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(cScope,scopes));}
	}
	
	public void addScope() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cScope));}
		scope = efScope.build(null);
		scope.setName(efLang.createEmpty(localeCodes));
		scope.setDescription(efDescription.createEmpty(localeCodes));
		reset(false,true);
		ui2.checkA(scope);
	}
	
	public void selectScope() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(scope));}
		scope = fConstraint.find(cScope,scope);
		scope = efScope.updateLD(fConstraint, scope, localeCodes);
		reloadConstraints();
		ui2.checkA(scope);
	}
	
	public void saveScope() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(scope));}
		scope.setCategory(fConstraint.find(cCategory,scope.getCategory()));
		scope = fConstraint.save(scope);
		reloadScopes();
		reloadConstraints();
	}
	
//	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cEntity, entities);Collections.sort(entities, comparatorEntity);}
	
	public void reloadConstraints()
	{
		constraints = fConstraint.allForParent(cConstraint,scope);
	}
	
	public void addConstraint() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cConstraint));}
		constraint = efConstraint.build(scope,null);
		constraint.setName(efLang.createEmpty(localeCodes));
		constraint.setDescription(efDescription.createEmpty(localeCodes));
		reset(false,false);
		ui2.checkB(constraint);
	}
	
	public void saveConstraint() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(constraint));}
		constraint.setType(fConstraint.find(cType,constraint.getType()));
		constraint.setLevel(fConstraint.find(cLevel,constraint.getLevel()));
		constraint = fConstraint.save(constraint);
		reloadConstraints();
	}
	
	public void selectConstraint() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(constraint));}
		constraint = fConstraint.find(cConstraint,constraint);
		efConstraint.updateLD(fConstraint, constraint, localeCodes);
		ui2.checkB(constraint);
	}
}