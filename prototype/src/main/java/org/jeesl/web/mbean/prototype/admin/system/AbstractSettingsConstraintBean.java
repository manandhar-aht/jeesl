package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.ejb.system.constraint.EjbConstraintScopeFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
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
										SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
										TYPE extends UtilsStatus<TYPE,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSettingsConstraintBean.class);
	
	private JeeslSystemConstraintFacade<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE> fConstraint;
	
	private final Class<SCOPE> cScope;
	private final Class<CATEGORY> cCategory;
	
	private List<SCOPE> scopes; public List<SCOPE> getScopes() {return scopes;}
	
	private SCOPE scope; public SCOPE getScope() {return scope;} public void setScope(SCOPE scope) {this.scope = scope;}

	private final EjbConstraintScopeFactory<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE> efScope;
	
	protected SbMultiHandler<CATEGORY> sbhCategory; public SbMultiHandler<CATEGORY> getSbhCategory() {return sbhCategory;}

	public AbstractSettingsConstraintBean(Class<L> cL, Class<D> cD, Class<SCOPE> cScope, Class<CATEGORY> cCategory, Class<CONSTRAINT> cConstraint, Class<TYPE> cType)
	{
		super(cL,cD);
		this.cScope=cScope;
		this.cCategory=cCategory;
		
		efScope = new EjbConstraintScopeFactory<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>(cL,cD,cScope,cCategory);
	}
	
	protected void initSuper(String[] localeCodes, FacesMessageBean bMessage, JeeslSystemConstraintFacade<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE> fConstraint)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fConstraint=fConstraint;
		sbhCategory = new SbMultiHandler<CATEGORY>(cCategory,fConstraint.allOrderedPosition(cCategory),this);
	}
	
	@Override public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		reloadScopes();
	}
	
	private void reset()
	{
		
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
		scope.setName(efLang.createEmpty(langs));
		scope.setDescription(efDescription.createEmpty(langs));
		reset();
	}
	
	public void selectScope() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(scope));}
		scope.setCategory(fConstraint.find(cCategory,scope.getCategory()));
		scope = efLang.persistMissingLangs(fConstraint,langs,scope);
		scope = efDescription.persistMissingLangs(fConstraint,langs,scope);
		scope = fConstraint.save(scope);
		reloadScopes();
	}
	
//	protected void reorderEntites() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fRevision, cEntity, entities);Collections.sort(entities, comparatorEntity);}

}