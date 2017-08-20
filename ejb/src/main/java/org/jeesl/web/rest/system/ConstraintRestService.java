package org.jeesl.web.rest.system;

import org.jeesl.api.facade.system.JeeslConstraintFacade;
import org.jeesl.api.rest.system.constraint.JeeslConstraintRestExport;
import org.jeesl.api.rest.system.constraint.JeeslConstraintRestImport;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.system.Constraints;

public class ConstraintRestService <L extends UtilsLang, D extends UtilsDescription,
									SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
		extends AbstractJeeslRestService<L,D>
		implements JeeslConstraintRestExport,JeeslConstraintRestImport
{
	final static Logger logger = LoggerFactory.getLogger(ConstraintRestService.class);
	
	private JeeslConstraintFacade<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE> fConstraint;
	
	private final Class<SCOPE> cScope;
	private final Class<CATEGORY> cCategory;
	private final Class<CONSTRAINT> cConstraint;
	private final Class<TYPE> cType;
	
//	private XmlTrafficLightFactory<L,D,SCOPE,LIGHT> xfLight;//
//	private EjbTrafficLightFactory<L,D,SCOPE,LIGHT> efLight;
	
	private ConstraintRestService(final String[] localeCodes, JeeslConstraintFacade<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE> fConstraint, final Class<L> cL, final Class<D> cD, Class<SCOPE> cScope, Class<CATEGORY> cCategory, Class<CONSTRAINT> cConstraint, Class<TYPE> cType)
	{
		super(fConstraint,cL,cD);
		this.fConstraint=fConstraint;
		this.cScope=cScope;
		this.cCategory=cCategory;
		this.cConstraint=cConstraint;
		this.cType=cType;
		
//		xfLight = new XmlTrafficLightFactory<L,D,SCOPE,LIGHT>(UtilsQuery.get(UtilsQuery.Key.exTrafficLight));
//		efLight = EjbTrafficLightFactory.factory(cLang,cDescription,cLight);
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
						SCOPE extends JeeslConstraintScope<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
						CATEGORY extends UtilsStatus<CATEGORY,L,D>,
						CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>,
						TYPE extends UtilsStatus<TYPE,L,D>>
	ConstraintRestService<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>
			factory(String[] localeCodes, JeeslConstraintFacade<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE> fConstraint, Class<L> cL, Class<D> cD, Class<SCOPE> cScope, Class<CATEGORY> cCategory, Class<CONSTRAINT> cConstraint, Class<TYPE> cType)
	{
		return new ConstraintRestService<L,D,SCOPE,CATEGORY,CONSTRAINT,TYPE>(localeCodes,fConstraint,cL,cD,cScope,cCategory,cConstraint,cType);
	}
	
	@Override public Container exportSystemConstraintCategories() {return xfContainer.build(fConstraint.allOrderedPosition(cCategory));}
	@Override public Container exportSystemConstraintTypes() {return xfContainer.build(fConstraint.allOrderedPosition(cType));}

	@Override public DataUpdate importSystemConstraintCategories(Container categories) {return super.importStatus(cCategory,categories,null);}
	@Override public DataUpdate importSystemConstraintTypes(Container categories) {return super.importStatus(cType,categories,null);}
	
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
}