package org.jeesl.web.mbean.prototype.system.io.revision;

import java.io.Serializable;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.factory.builder.RevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractAdminRevisionConsistencyBean <L extends UtilsLang,D extends UtilsDescription,
											RC extends UtilsStatus<RC,L,D>,
											RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RVM extends JeeslRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RST extends UtilsStatus<RST,L,D>,
											RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
											RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractAdminRevisionBean<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionConsistencyBean.class);
	
	private long index;
	
	private RC category; public RC getCategory() {return category;} public void setCategory(RC category) {this.category = category;}

	public AbstractAdminRevisionConsistencyBean(final RevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fbRevision){super(fbRevision);}

	protected void initSuper(String[] langs, FacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision)
	{
		super.initRevisionSuper(langs,bMessage,fRevision);
		categories.clear();
		index = 1;
	}
	
	protected void build(Class<?> c)
	{
		try
		{
			RC s = fbRevision.getClassCategory().newInstance();
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