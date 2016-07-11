package org.jeesl.web.rest.system;

import org.jeesl.factory.xml.revision.XmlEntityFactory;
import org.jeesl.interfaces.rest.system.revision.JeeslRevisionRestExport;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.util.query.xml.RevisionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.util.query.StatusQuery;
import net.sf.ahtutils.factory.xml.status.XmlStatusFactory;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;

public class RevisionRestService <L extends UtilsLang,D extends UtilsDescription,
								RC extends UtilsStatus<RC,L,D>,
								RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RST extends UtilsStatus<RST,L,D>,
								RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RAT extends UtilsStatus<RAT,L,D>>
					implements JeeslRevisionRestExport
{
	final static Logger logger = LoggerFactory.getLogger(RevisionRestService.class);
	
	private UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<RC> cRC;
	private final Class<RV> cRV;
	private final Class<RVM> cRVM;
	private final Class<RS> cRS;
	private final Class<RST> cRST;
	private final Class<RE> cRE;
	private final Class<REM> cREM;
	private final Class<RA> cRA;
	private final Class<RAT> cRAT;

	private XmlStatusFactory fStatus;
	private XmlEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> xfEntity;

	
	private RevisionRestService(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision,final Class<L> cL, final Class<D> cD, Class<RC> cRC, final Class<RV> cRV, final Class<RVM> cRVM, final Class<RS> cRS, final Class<RST> cRST, final Class<RE> cRE, final Class<REM> cREM, final Class<RA> cRA, final Class<RAT> cRAT)
	{
		this.fRevision=fRevision;
		this.cL=cL;
		this.cD=cD;
		
		this.cRC=cRC;
		this.cRV=cRV;
		this.cRVM=cRVM;
		this.cRS=cRS;
		this.cRST=cRST;
		this.cREM=cREM;
		this.cRE=cRE;
		this.cRA=cRA;
		this.cRAT=cRAT;
	
		fStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		xfEntity = new XmlEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(RevisionQuery.get(RevisionQuery.Key.exEntities));
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RST extends UtilsStatus<RST,L,D>,
					RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RAT extends UtilsStatus<RAT,L,D>>
		RevisionRestService<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>
			factory(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision,final Class<L> cL, final Class<D> cD, Class<RC> cRC, final Class<RV> cRV, final Class<RVM> cRVM, final Class<RS> cRS, final Class<RST> cRST, final Class<RE> cRE, final Class<REM> cREM, final Class<RA> cRA, final Class<RAT> cRAT)
	{
		return new RevisionRestService<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(fRevision,cL,cD,cRC,cRV,cRVM,cRS,cRST,cRE,cREM,cRA,cRAT);
	}

	@Override public Entities exportSystemRevisionEntities()
	{
		Entities entities = new Entities();
		
		for(RE re : fRevision.all(cRE))
		{
			re = fRevision.load(cRE, re);
			entities.getEntity().add(xfEntity.build(re));
		}
		
		return entities;
	}
}