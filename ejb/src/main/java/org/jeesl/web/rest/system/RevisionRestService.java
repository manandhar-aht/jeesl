package org.jeesl.web.rest.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.api.rest.system.io.revision.JeeslRevisionRestExport;
import org.jeesl.api.rest.system.io.revision.JeeslRevisionRestImport;
import org.jeesl.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.system.revision.XmlEntityFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entities;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.util.query.xml.RevisionQuery;
import org.jeesl.util.query.xml.StatusQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbDescriptionFactory;
import net.sf.ahtutils.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
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
	
	private JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	private final Class<L> cL;
	private final Class<D> cD;
	private final Class<RC> cRC;
	
	@SuppressWarnings("unused") private final Class<RV> cRV;
	@SuppressWarnings("unused") private final Class<RVM> cRVM;
	@SuppressWarnings("unused") private final Class<RS> cRS;
	private final Class<RST> cRST;
	private final Class<RE> cRE;
	@SuppressWarnings("unused") private final Class<REM> cREM;
	private final Class<RA> cRA;
	private final Class<RAT> cRAT;

	private XmlContainerFactory xfContainer;
	private XmlEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> xfEntity;

	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
	private EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efEntity;
	private EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> efAttribute;
	
	private RevisionRestService(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision,final Class<L> cL, final Class<D> cD, Class<RC> cRC, final Class<RV> cRV, final Class<RVM> cRVM, final Class<RS> cRS, final Class<RST> cRST, final Class<RE> cRE, final Class<REM> cREM, final Class<RA> cRA, final Class<RAT> cRAT)
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
	
		xfContainer = new XmlContainerFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
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
			factory(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision,final Class<L> cL, final Class<D> cD, Class<RC> cRC, final Class<RV> cRV, final Class<RVM> cRVM, final Class<RS> cRS, final Class<RST> cRST, final Class<RE> cRE, final Class<REM> cREM, final Class<RA> cRA, final Class<RAT> cRAT)
	{
		return new RevisionRestService<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(fRevision,cL,cD,cRC,cRV,cRVM,cRS,cRST,cRE,cREM,cRA,cRAT);
	}
	
	@Override public Container exportSystemIoRevisionAttributeTypes() {return xfContainer.build(fRevision.allOrderedPosition(cRAT));}
	@Override public Container exportSystemIoRevisionScopeTypes() {return xfContainer.build(fRevision.allOrderedPosition(cRST));}
	@Override public Container exportSystemRevisionCategories() {return xfContainer.build(fRevision.allOrderedPosition(cRC));}

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
	
	@Override public DataUpdate importSystemIoRevisionAttributeTypes(Container categories){return importStatus(cRAT,cL,cD,categories,null);}
	@Override public DataUpdate importSystemIoRevisionScopeTypes(Container categories){return importStatus(cRST,cL,cD,categories,null);}
	@Override public DataUpdate importSystemRevisionCategories(org.jeesl.model.xml.jeesl.Container categories){return importStatus(cRC,cL,cD,categories,null);}
	
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
		
		if(logger.isDebugEnabled())
		{
			logger.debug("Will delete in DB");
			logger.debug("\t"+cRE.getSimpleName()+" "+inDbRevisionEntity.size());
			logger.debug("\t"+cL.getSimpleName()+" "+dbDeleteL.size());
			logger.debug("\t"+cD.getSimpleName()+" "+dbDeleteD.size());
		}
		try
		{
			fRevision.rm(dbDeleteL);
			fRevision.rm(dbDeleteD);
		}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
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
		
		efEntity.applyValues(re, xml);
		
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
			logger.debug("****");
			logger.debug("ra.code "+ra.getCode()+" "+ejbRevisionEntity.getCode());
			logger.debug("xml.code "+xml.getCode());
			
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
		efAttribute.applyValues(ejbAttribute, xml);
		
		ejbAttribute = fRevision.save(cRE,ejbRevisionEntity,ejbAttribute);
		return ejbAttribute;
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Aht container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fRevision);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Container container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fRevision);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }
}