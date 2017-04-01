package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSystemPropertyFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.util.JeeslProperty;
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
	
	private SbMultiHandler<C> sbhCategory;
	
	private Class<P> cProperty;
	private Class<C> cCategory;
	
	protected List<P> properties; public List<P> getProperties() {return properties;}
	
	
	protected P property;public P getProperty() {return property;}public void setProperty(P property) {this.property = property;}
	
	public void initSuper(JeeslSystemPropertyFacade<L,D,C,P> fProperty, final Class<C> cCategory, final Class<P> cProperty)
	{
		this.fProperty=fProperty;
		this.cProperty=cProperty;
		this.cCategory=cCategory;
		
		sbhCategory = new SbMultiHandler<C>(cCategory,fProperty.allOrderedPositionVisible(cCategory),this);
		sbhCategory.selectAll();
	}
	
	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException {
		// TODO Auto-generated method stub
		
	}
	
	protected void refreshList()
	{
		properties = fProperty.allOrdered(cProperty,"key",true);
	}
	
	public void selectProperty() throws UtilsNotFoundException
	{
		property = fProperty.find(cProperty, property);
	}
	
	public void save() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		property = fProperty.save(property);
		refreshList();
	}
}