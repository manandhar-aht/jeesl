package org.jeesl.web.rest.system;

import org.jeesl.api.exception.xml.JeeslXmlStructureException;
import org.jeesl.api.rest.system.constraint.JeeslConstraintRestExport;
import org.jeesl.api.rest.system.constraint.JeeslConstraintRestImport;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.ejb.system.util.EjbTrafficLightFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.util.query.xml.UtilsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.xml.utils.XmlTrafficLightFactory;
import net.sf.ahtutils.factory.xml.utils.XmlTrafficLightsFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.rest.AbstractUtilsRest;
import net.sf.ahtutils.xml.aht.Container;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.system.Constraints;
import net.sf.ahtutils.xml.utils.TrafficLight;
import net.sf.ahtutils.xml.utils.TrafficLights;

public class ConstraintRestService <L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
		extends AbstractUtilsRest<L,D>
		implements JeeslConstraintRestExport,JeeslConstraintRestImport
{
	final static Logger logger = LoggerFactory.getLogger(ConstraintRestService.class);
	
	private UtilsFacade fUtils;
	
	private  Class<SCOPE> cScope;
	private  Class<CATEGORY> cCategory;
	private  Class<CONSTRAINT> cConstraint;
	private  Class<TYPE> cType;
	
//	private XmlTrafficLightFactory<L,D,SCOPE,LIGHT> xfLight;//
//	private EjbTrafficLightFactory<L,D,SCOPE,LIGHT> efLight;
	
	private ConstraintRestService(UtilsFacade fUtils, final String[] localeCodes, final Class<L> cL, final Class<D> cD)
	{
		super(fUtils,localeCodes,cL,cD);
		this.fUtils=fUtils;
//		this.cScope=cScope;
//		this.cCategory=cCategory;
//		this.cConstraint=cConstraint;
//		this.cType=cType;
		
//		xfLight = new XmlTrafficLightFactory<L,D,SCOPE,LIGHT>(UtilsQuery.get(UtilsQuery.Key.exTrafficLight));
//		efLight = EjbTrafficLightFactory.factory(cLang,cDescription,cLight);
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
						SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
						CATEGORY extends UtilsStatus<CATEGORY,L,D>,
						CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
						TYPE extends UtilsStatus<TYPE,L,D>>
	ConstraintRestService<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>
			factory(UtilsFacade fUtils, final String[] localeCodes, final Class<L> cL, final Class<D> cD)
	{
		return new ConstraintRestService<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>(fUtils,localeCodes,cL,cD);
	}
	
	@Override public Container exportSystemConstraintCategories() {return super.exportContainer(cCategory);}
	@Override public Container exportSystemConstraintTypes() {return super.exportContainer(cType);}

	@Override public DataUpdate importConstraints(Constraints constraints)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override public Constraints exportConstraints()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override public DataUpdate importSystemConstraintCategories(Container categories)
	{
		return null;
//		return super.importStatus(cCategory,null,categories);
	}

	@Override
	public DataUpdate importSystemConstraintTypes(Container categories) {
		// TODO Auto-generated method stub
		return null;
	}
}