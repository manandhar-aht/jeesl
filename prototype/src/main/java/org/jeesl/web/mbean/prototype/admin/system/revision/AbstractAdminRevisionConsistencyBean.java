package org.jeesl.web.mbean.prototype.admin.system.revision;

import java.io.Serializable;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractAdminRevisionConsistencyBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionConsistencyBean.class);
	
	private long index;
	
	private RC category; public RC getCategory() {return category;} public void setCategory(RC category) {this.category = category;}

	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, final Class<L> cLang, final Class<D> cDescription, Class<RC> cCategory,Class<RV> cView,Class<RVM> cMapping, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cEntityMapping, Class<RA> cAttribute, Class<RAT> cRat)
	{
		super.initRevisionSuper(langs,bMessage,fRevision,cLang,cDescription,cCategory,cView,cMapping,cScope,cScopeType,cEntity,cEntityMapping,cAttribute,cRat);
		categories.clear();
		index = 1;
	}
	
	protected void build(Class<?> c)
	{
		try
		{
			RC s = cCategory.newInstance();
			s.setId(index);
			s.setCode(c.getSimpleName());
			s.setImage(c.getName());
			categories.add(s);
			index++;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();};
	}
	
	public void selectCategory() throws ClassNotFoundException
	{
		Class<?> c = Class.forName(category.getImage());
		check(c);
	}
	
	protected void check(Class<?> c){}
}