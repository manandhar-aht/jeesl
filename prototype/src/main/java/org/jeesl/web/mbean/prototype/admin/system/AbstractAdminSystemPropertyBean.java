package org.jeesl.web.mbean.prototype.admin.system;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

import org.jeesl.interfaces.model.system.util.JeeslProperty;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminSystemPropertyBean <L extends UtilsLang,D extends UtilsDescription,P extends JeeslProperty<L,D>>
		extends AbstractAdminBean<L,D>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSystemPropertyBean.class);
	
	private UtilsFacade fUtils;
	
	private Class<P> cProperty;
	protected List<P> properties; public List<P> getProperties() {return properties;}
	protected P property;public P getProperty() {return property;}public void setProperty(P property) {this.property = property;}
	
	public void initSuper(UtilsFacade fUtils, final Class<P> cProperty)
	{
		this.fUtils=fUtils;
		this.cProperty=cProperty;
	}
	
	protected void refreshList()
	{
		properties = fUtils.allOrdered(cProperty,"key",true);
	}
	
	public void selectProperty() throws UtilsNotFoundException
	{
		property = fUtils.find(cProperty, property);
	}
	
	public void save() throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		property = fUtils.save(property);
		refreshList();
	}
}