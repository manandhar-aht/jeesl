package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemPropertyFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.system.PropertyFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.jeesl.util.comparator.ejb.system.PropertyComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSystemPropertyBean <L extends UtilsLang, D extends UtilsDescription,
												C extends UtilsStatus<C,L,D>,
												P extends JeeslProperty<L,D,C,P>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSystemPropertyBean.class);
	
	private JeeslSystemPropertyFacade<L,D,C,P> fProperty;
	private final PropertyFactoryBuilder<L,D,C,P> fbProperty;
	
	private final SbMultiHandler<C> sbhCategory; public SbMultiHandler<C> getSbhCategory() {return sbhCategory;}
	private final Comparator<P> comparatorProperty;
	
	protected List<P> properties; public List<P> getProperties() {return properties;}
	
	protected P prop; public P getProp() {return prop;} public void setProp(P prop) {this.prop = prop;}

	public AbstractAdminSystemPropertyBean(final PropertyFactoryBuilder<L,D,C,P> fbProperty)
	{
		super(fbProperty.getClassL(),fbProperty.getClassD());
		this.fbProperty = fbProperty;
		sbhCategory = new SbMultiHandler<C>(fbProperty.getClassCategory(),this);
		comparatorProperty = (new PropertyComparator<L,D,C,P>()).factory(PropertyComparator.Type.category);
	}
	
	public void initSuper(JeeslSystemPropertyFacade<L,D,C,P> fProperty)
	{
		this.fProperty=fProperty;

		sbhCategory.setList(fProperty.allOrderedPositionVisible(fbProperty.getClassCategory()));
		sbhCategory.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbProperty.getClassCategory().getSimpleName()+" "+sbhCategory.getSelected().size()+"/"+sbhCategory.getList().size());}
		refreshList();
	}
	
	private void refreshList()
	{
		properties = fProperty.all(fbProperty.getClassProperty());
		Collections.sort(properties,comparatorProperty);
	}
	
	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");}
	}
	
	public void selectProperty() throws UtilsNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(prop));}
		prop = fProperty.find(fbProperty.getClassProperty(), prop);
	}
	
	public void saveProperty() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(prop));}
		if(prop.getCategory()!=null){prop.setCategory(fProperty.find(fbProperty.getClassCategory(),prop.getCategory()));}
		prop = fProperty.save(prop);
		refreshList();
	}
}