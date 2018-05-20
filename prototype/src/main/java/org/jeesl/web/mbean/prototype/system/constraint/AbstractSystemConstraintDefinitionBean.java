package org.jeesl.web.mbean.prototype.system.constraint;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslConstraintsBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.controller.handler.ui.helper.UiTwiceClickHelper;
import org.jeesl.factory.builder.system.ConstraintFactoryBuilder;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintFactory;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintScopeFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractSystemConstraintDefinitionBean <L extends UtilsLang, D extends UtilsDescription,
										ALGCAT extends UtilsStatus<ALGCAT,L,D>,
										ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
										SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
										CONCAT extends UtilsStatus<CONCAT,L,D>,
										CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
										LEVEL extends UtilsStatus<LEVEL,L,D>,
										TYPE extends UtilsStatus<TYPE,L,D>,
										RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
					extends AbstractSystemConstraintBean<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSystemConstraintDefinitionBean.class);
	
	private JeeslConstraintsBean<CONSTRAINT> bConstraint;
	
	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	private List<CONSTRAINT> constraints; public List<CONSTRAINT> getConstraints() {return constraints;}
	private List<TYPE> types; public List<TYPE> getTypes() {return types;}
	private List<LEVEL> levels; public List<LEVEL> getLevels() {return levels;}
	
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}
	private CONSTRAINT constraint; public CONSTRAINT getConstraint() {return constraint;} public void setConstraint(CONSTRAINT constraint) {this.constraint = constraint;}
	
	private final EjbConstraintScopeFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> efScope;
	private final EjbConstraintFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> efConstraint;
	
	protected SbMultiHandler<CONCAT> sbhCategory; public SbMultiHandler<CONCAT> getSbhCategory() {return sbhCategory;}
	private final UiTwiceClickHelper ui2; public UiTwiceClickHelper getUi2() {return ui2;}

	public AbstractSystemConstraintDefinitionBean(ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint)
	{
		super(fbConstraint);
		
		ui2 = new UiTwiceClickHelper();
		efScope = new EjbConstraintScopeFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(fbConstraint.getClassL(),fbConstraint.getClassD(),fbConstraint.getClassScope(),fbConstraint.getClassConstraintCategory());
		efConstraint = new EjbConstraintFactory<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>(fbConstraint.getClassL(),fbConstraint.getClassD(),fbConstraint.getClassConstraint(),fbConstraint.getClassConstraintType());
	}
	
	protected void postConstructConstraintDefinition(JeeslConstraintsBean<CONSTRAINT> bConstraint, JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage, JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint)
	{
		super.initConstraint(bTranslation,bMessage,fConstraint);
		this.bConstraint=bConstraint;
		sbhCategory = new SbMultiHandler<CONCAT>(fbConstraint.getClassConstraintCategory(),fConstraint.allOrderedPosition(fbConstraint.getClassConstraintCategory()),this);
		types = fConstraint.allOrderedPosition(fbConstraint.getClassConstraintType());
		levels = fConstraint.allOrderedPosition(fbConstraint.getClassConstraintLevel());
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
		scopes = fConstraint.all(fbConstraint.getClassScope());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbConstraint.getClassScope(),scopes));}
	}
	
	public void addScope() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbConstraint.getClassScope()));}
		scope = efScope.build(null);
		scope.setName(efLang.createEmpty(localeCodes));
		scope.setDescription(efDescription.createEmpty(localeCodes));
		reset(false,true);
		ui2.checkA(scope);
	}
	
	public void selectScope() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(scope));}
		scope = fConstraint.find(fbConstraint.getClassScope(),scope);
		scope = efScope.updateLD(fConstraint, scope, localeCodes);
		reloadConstraints();
		ui2.checkA(scope);
	}
	
	public void saveScope() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(scope));}
		scope.setCategory(fConstraint.find(fbConstraint.getClassConstraintCategory(),scope.getCategory()));
		scope = fConstraint.save(scope);
		reloadScopes();
		reloadConstraints();
	}
	
//	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cEntity, entities);Collections.sort(entities, comparatorEntity);}
	
	public void reloadConstraints()
	{
		constraints = fConstraint.allForParent(fbConstraint.getClassConstraint(),scope);
	}
	
	public void addConstraint() throws UtilsNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbConstraint.getClassConstraint()));}
		constraint = efConstraint.build(scope,null);
		constraint.setName(efLang.createEmpty(localeCodes));
		constraint.setDescription(efDescription.createEmpty(localeCodes));
		reset(false,false);
		ui2.checkB(constraint);
	}
	
	public void saveConstraint() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(constraint));}
		constraint.setType(fConstraint.find(fbConstraint.getClassConstraintType(),constraint.getType()));
		if(constraint.getLevel()!=null) {constraint.setLevel(fConstraint.find(fbConstraint.getClassConstraintLevel(),constraint.getLevel()));}
		constraint = fConstraint.save(constraint);
		reloadConstraints();
		
		bConstraint.update(constraint);
	}
	
	public void selectConstraint() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(constraint));}
		constraint = fConstraint.find(fbConstraint.getClassConstraint(),constraint);
		efConstraint.updateLD(fConstraint, constraint, localeCodes);
		ui2.checkB(constraint);
	}
}