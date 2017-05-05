package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemPropertyFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
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

public class AbstractAdminSystemPropertyBean <L extends UtilsLang,D extends UtilsDescription,
												C extends UtilsStatus<C,L,D>,
												P extends JeeslProperty<L,D,C,P>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSystemPropertyBean.class);
	
	private JeeslSystemPropertyFacade<L,D,C,P> fProperty;
	
	private SbMultiHandler<C> sbhCategory; public SbMultiHandler<C> getSbhCategory() {return sbhCategory;}
	private Comparator<P> comparatorProperty;
	
	private Class<P> cProperty;
	private Class<C> cCategory;
	
	protected List<P> properties; public List<P> getProperties() {return properties;}
	
	protected P property;public P getProperty() {return property;}public void setProperty(P property) {this.property = property;}
	
	public AbstractAdminSystemPropertyBean(final Class<L> cL, final Class<D> cD)
	{
		super(cL,cD);
	}
	
	public void initSuper(JeeslSystemPropertyFacade<L,D,C,P> fProperty, final Class<C> cCategory, final Class<P> cProperty)
	{
		this.fProperty=fProperty;
		this.cProperty=cProperty;
		this.cCategory=cCategory;
		
		comparatorProperty = (new PropertyComparator<L,D,C,P>()).factory(PropertyComparator.Type.category);
		
		sbhCategory = new SbMultiHandler<C>(cCategory,fProperty.allOrderedPositionVisible(cCategory),this);
		sbhCategory.selectAll();
		if(debugOnInfo)
		{
			logger.info(SbMultiHandler.class.getSimpleName()+":"+cCategory.getSimpleName()+" "+sbhCategory.getSelected().size()+"/"+sbhCategory.getList().size());
		}
	}
	
	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException
	{
		if(debugOnInfo)
		{
			logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");
		}
	}
	
	protected void refreshList()
	{
		properties = fProperty.all(cProperty);
		Collections.sort(properties,comparatorProperty);
	}
	
	public void selectProperty() throws UtilsNotFoundException
	{
		property = fProperty.find(cProperty, property);
	}
	
	public void save() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		if(property.getCategory()!=null){property.setCategory(fProperty.find(cCategory,property.getCategory()));}
		property = fProperty.save(property);
		refreshList();
	}
}