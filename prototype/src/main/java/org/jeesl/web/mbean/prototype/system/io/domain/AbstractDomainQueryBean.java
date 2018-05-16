package org.jeesl.web.mbean.prototype.system.io.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoDomainFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.io.IoDomainFactoryBuilder;
import org.jeesl.factory.ejb.system.io.domain.EjbSurveyDomainFactory;
import org.jeesl.factory.ejb.system.io.domain.EjbSurveyDomainPathFactory;
import org.jeesl.factory.ejb.system.io.domain.EjbSurveyDomainQueryFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainItem;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.util.comparator.ejb.system.io.revision.RevisionEntityComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractDomainQueryBean <L extends UtilsLang, D extends UtilsDescription,
						DOMAIN extends JeeslDomain<L,ENTITY>,
						QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
						PATH extends JeeslDomainPath<L,D,QUERY,ENTITY,DATTRIBUTE>,
						ENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
						DATTRIBUTE extends JeeslRevisionAttribute<L,D,ENTITY,?,?>,
						SET extends JeeslDomainSet<L,D,DOMAIN>,
						ITEM extends JeeslDomainItem<QUERY,SET>>
					extends AbstractDomainBean<L,D,DOMAIN,QUERY,PATH,ENTITY,DATTRIBUTE,SET,ITEM>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDomainQueryBean.class);

	protected List<ENTITY> entities; public List<ENTITY> getEntities(){return entities;}
	protected List<PATH> paths; public List<PATH> getPaths(){return paths;}
	protected List<DATTRIBUTE> attributes; public List<DATTRIBUTE> getAttributes(){return attributes;}
	
	private DOMAIN domain; public DOMAIN getDomain() {return domain;} public void setDomain(DOMAIN domain) {this.domain = domain;}
	private QUERY query; public QUERY getQuery() {return query;} public void setQuery(QUERY query) {this.query = query;}
	private PATH path; public PATH getPath() {return path;} public void setPath(PATH path) {this.path = path;}
	private ENTITY entity; public ENTITY getEntity() {return entity;}
	
	protected final SbSingleHandler<DOMAIN> sbhDomain; public SbSingleHandler<DOMAIN> getSbhDomain() {return sbhDomain;}
	
	private final EjbSurveyDomainFactory<L,D,DOMAIN,ENTITY> efDomain;
	private final EjbSurveyDomainQueryFactory<L,D,DOMAIN,QUERY,PATH> efDomainQuery;
	private final EjbSurveyDomainPathFactory<L,D,QUERY,PATH,ENTITY,DATTRIBUTE> efDomainPath;
	
	protected final Comparator<ENTITY> cpDomainEntity;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractDomainQueryBean(IoDomainFactoryBuilder<L,D,DOMAIN,QUERY,PATH,ENTITY,DATTRIBUTE,SET,ITEM> fbDomain)
	{
		super(fbDomain);
		
		sbhDomain = new SbSingleHandler<DOMAIN>(fbDomain.getClassDomain(),this);
		sbhDomain.setDebugOnInfo(true);
		
		efDomain = fbDomain.ejbDomain();
		efDomainQuery = fbDomain.ejbDomainQuery();
		efDomainPath = fbDomain.ejbDomainPath();
		
		cpDomainEntity = new RevisionEntityComparator().factory(RevisionEntityComparator.Type.position);
	}
	
	protected void postConstructDomainQuery(String userLocale, JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage,
			JeeslIoDomainFacade<L,D,DOMAIN,QUERY,PATH,ENTITY,DATTRIBUTE,SET,ITEM> fDomain)
	{
		super.postConstructDomain(bTranslation,bMessage,fDomain);
//		super.initSuperSurvey(bTranslation.getLangKeys(),bMessage,fTemplate,fCore,fAnalysis,bSurvey);
//		initPageSettings();
		
		entities = fDomain.allOrderedPositionVisible(fbDomain.getClassDomainEntity());
		Collections.sort(entities,cpDomainEntity);
		reloadDomains();
		if(sbhDomain.getHasSome())
		{
			sbhDomain.selectDefault();
			domain = sbhDomain.getSelection();
			reloadQueries();
		}
		else
		{
			try
			{
				addDomain();
				sbhDomain.selectSbSingle(domain);
				sbhDomain.selectSbSingle(domain);
			}
			catch (UtilsLockingException e) {logger.error(e.getMessage());}
			catch (UtilsConstraintViolationException e) {logger.error(e.getMessage());}
		}
	}
	
//	@Override protected void initPageSettings(){}
	
	@SuppressWarnings("unchecked")
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		if(ejb==null) {reset(true,true,true,true);}
		else if(JeeslDomain.class.isAssignableFrom(ejb.getClass()))
		{
			domain = (DOMAIN)ejb;
			if(EjbIdFactory.isSaved(domain))
			{
				selectDomain();
			}
			logger.info("Twice:"+sbhDomain.getTwiceSelected()+" for "+domain.toString());
		}
		else
		{
			logger.info("NOT Assignable");
		}
	}
	
	private void reset(boolean rDomain, boolean rQuery, boolean rPath, boolean rEntity)
	{
		if(rDomain){domain = null;}
		if(rQuery){query = null;}
		if(rPath){path = null;}
		if(rEntity){entity = null;}
	}
	
	private void reloadDomains()
	{
		sbhDomain.setList(fDomain.all(fbDomain.getClassDomain()));
	}
	
	public void addDomain()
	{
		logger.info(AbstractLogMessage.addEntity(fbDomain.getClassDomain()));
		domain = efDomain.build(null,sbhDomain.getList());
		domain.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveDomain() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(domain));
		domain.setEntity(fDomain.find(fbDomain.getClassDomainEntity(),domain.getEntity()));
		domain = fDomain.save(domain);
		sbhDomain.setSelection(domain);
		reloadDomains();
		reloadQueries();
	}
	
	public void selectDomain()
	{
		reset(false,true,true,true);
		logger.info(AbstractLogMessage.selectEntity(domain));
		domain = efLang.persistMissingLangs(fDomain,localeCodes,domain);
		sbhDomain.setSelection(domain);
		reloadQueries();
	}
	
	private void reloadQueries()
	{
		queries = fDomain.allForParent(fbDomain.getClassDomainQuery(), domain);
	}
	
	public void addQuery()
	{
		logger.info(AbstractLogMessage.addEntity(fbDomain.getClassDomainQuery()));
		query = efDomainQuery.build(domain, queries);
		query.setName(efLang.createEmpty(localeCodes));
		query.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveQuery() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(query));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		query = fDomain.save(query);
		reloadQueries();
		reloadPaths();
		if(paths.isEmpty())
		{
			path = fDomain.save(efDomainPath.build(query,domain.getEntity(),paths));
			reloadPaths();
		}
	}
	
	public void deleteQuery() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(query));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		fDomain.rm(query);
		reloadQueries();
		reset(false,true,true,true);
	}
	
	public void selectQuery()
	{
		reset(false,false,true,true);
		logger.info(AbstractLogMessage.selectEntity(query));
		query = efLang.persistMissingLangs(fDomain,localeCodes,query);
		query = efDescription.persistMissingLangs(fDomain,localeCodes,query);
		reloadPaths();
	}
	
	private void reloadPaths()
	{
		paths = fDomain.allForParent(fbDomain.getClassDomainPath(), query);
		if(!paths.isEmpty())
		{
			PATH p = paths.get(paths.size()-1);
			if(p.getAttribute()!=null)
			{
				if(p.getAttribute().getRelation()!=null)
				{
					entity = p.getAttribute().getEntity();
				}
			}
		}
	}
	
	private void reloadPath()
	{
		entity = null;
		if(path.getAttribute()!=null)
		{
			if(path.getAttribute().getRelation()!=null)
			{
				entity = path.getAttribute().getEntity();
			}
		}
	}
	
	public void addPath()
	{
		logger.info(AbstractLogMessage.addEntity(fbDomain.getClassDomainQuery()));
		path = efDomainPath.build(query,entity,paths);
		reloadAttributes();
	}
	
	public void savePath() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(path));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		path = fDomain.save(path);
		reloadPaths();
		reloadAttributes();
		reloadPath();
	}
	
	public void selectPath()
	{
		reset(false,false,false,true);
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(path));}
		reloadAttributes();
		reloadPath();
	}
	
	public void deletePath() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(path));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		fDomain.rm(path);
		reloadPaths();
		reset(false,false,true,true);
	}
	
	private void reloadAttributes()
	{
		attributes = fDomain.fDomainAttributes(path.getEntity());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbDomain.getClassDomainAttribute(), attributes, path.getEntity()));}
	}
	
	public void reorderDomains() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fDomain, sbhDomain.getList());}
	public void reorderQueries() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fDomain, queries);}
}