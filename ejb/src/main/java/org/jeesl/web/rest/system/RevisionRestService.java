package org.jeesl.web.rest.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.xml.revision.XmlEntityFactory;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.interfaces.rest.system.revision.JeeslRevisionRestExport;
import org.jeesl.interfaces.rest.system.revision.JeeslRevisionRestImport;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.util.query.xml.RevisionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.sync.DataUpdate;

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
					implements JeeslRevisionRestExport,JeeslRevisionRestImport
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

	private XmlEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> xfEntity;

	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	private EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efEntity;
	private EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efAttribute;
	
	
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
	
		xfEntity = new XmlEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(RevisionQuery.get(RevisionQuery.Key.exEntity));
			
		efLang = EjbLangFactory.createFactory(cL);
		efDescription = EjbDescriptionFactory.createFactory(cD);
		efEntity = EjbRevisionEntityFactory.factory(cL,cD,cRE);
		efAttribute = EjbRevisionAttributeFactory.factory(cRA);
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
	
	@Override public DataUpdate importSystemRevisionEntities(Entities entities)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cRE.getName(),"DB Import"));
		
		Set<RE> inDbRevisionEntity = new HashSet<RE>(fRevision.all(cRE));
		List<L> dbDeleteL = new ArrayList<L>();
		List<D> dbDeleteD = new ArrayList<D>();
		
		if(logger.isInfoEnabled())
		{
			logger.info("Already in DB");
			logger.info("\t"+cRE.getSimpleName()+" "+inDbRevisionEntity.size());
		}
		
		for(Entity xml : entities.getEntity())
		{
			try
			{
				iuRevisionEntity(inDbRevisionEntity,xml,dbDeleteL,dbDeleteD);
				dut.success();
			}
			catch (UtilsNotFoundException e) {dut.fail(e, true);}
			catch (UtilsConstraintViolationException e) {dut.fail(e, true);}
			catch (UtilsLockingException e) {dut.fail(e, true);}
		}
		
		if(logger.isInfoEnabled())
		{
			logger.info("Will delete in DB");
			logger.info("\t"+cRE.getSimpleName()+" "+inDbRevisionEntity.size());
			logger.info("\t"+cL.getSimpleName()+" "+dbDeleteL.size());
			logger.info("\t"+cD.getSimpleName()+" "+dbDeleteD.size());
		}
		try
		{
			fRevision.rm(dbDeleteL);
			fRevision.rm(dbDeleteD);
		}
		catch (UtilsConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dut.toDataUpdate();
	}
	
	private void iuRevisionEntity(Set<RE> inDbRevisionEntity, Entity xml, List<L> dbDeleteL, List<D> dbDeleteD) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		RE re;
		try
		{
			re = fRevision.fByCode(cRE, xml.getCode());
			inDbRevisionEntity.remove(re);
		}
		catch (UtilsNotFoundException e)
		{
			RC category = fRevision.fByCode(cRC, xml.getCategory().getCode());
			re = efEntity.build(category,xml);
			re = fRevision.persist(re);
		}
		re = fRevision.load(cRE, re);
		
		dbDeleteL.addAll(re.getName().values());
		dbDeleteD.addAll(re.getDescription().values());
		re.getName().clear();
		re.getDescription().clear();
		
		re.setName(efLang.getLangMap(xml.getLangs()));
		re.setDescription(efDescription.create(xml.getDescriptions()));
		
		if(xml.isSetRemark()){re.setDeveloperInfo(xml.getRemark().getValue());}
		else{re.setDeveloperInfo(null);}
		
		Set<RA> set = new HashSet<RA>(re.getAttributes());		
		for(Attribute xmlAttribute : xml.getAttribute())
		{
			RA  ra = iuRevisionAttribute(re,xmlAttribute,dbDeleteL,dbDeleteD);
			if(set.contains(ra)){set.remove(ra);}
		}
		for(RA ra : new ArrayList<RA>(set))
		{
			fRevision.rm(cRE,re,ra);
		}
	}
	
	private RA iuRevisionAttribute(RE ejbRevisionEntity, Attribute xml, List<L> dbDeleteL, List<D> dbDeleteD) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		RA ejbAttribute = null;
		
		for(RA ra : ejbRevisionEntity.getAttributes())
		{
			logger.info("****");
			logger.info("ra.code "+ra.getCode()+" "+ejbRevisionEntity.getCode());
			logger.info("xml.code "+xml.getCode());
			
			if(ra.getCode().equals(xml.getCode()))
			{
				ejbAttribute=fRevision.find(cRA, ra);
				dbDeleteL.addAll(ejbAttribute.getName().values());
				dbDeleteD.addAll(ejbAttribute.getDescription().values());
					
				ejbAttribute.getName().clear();
				ejbAttribute.getDescription().clear();
			}
		}
		
		if(ejbAttribute==null)
		{
			RAT type = fRevision.fByCode(cRAT, xml.getType().getCode());
			ejbAttribute = efAttribute.build(type,xml);
			ejbAttribute = fRevision.save(cRE,ejbRevisionEntity,ejbAttribute);
		}
		ejbAttribute.setName(efLang.getLangMap(xml.getLangs()));
		ejbAttribute.setDescription(efDescription.create(xml.getDescriptions()));
		ejbAttribute = fRevision.save(cRE,ejbRevisionEntity,ejbAttribute);
		
		return ejbAttribute;
	}
}