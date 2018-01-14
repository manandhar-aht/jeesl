package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.facade.module.survey.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainPathFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainQueryFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidationAlgorithm;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.util.comparator.ejb.system.io.revision.RevisionEntityComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminSurveyDomainBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
						SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
						SS extends UtilsStatus<SS,L,D>,
						SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
						VALGORITHM extends JeeslSurveyValidationAlgorithm<L,D>,
						TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,
						VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
						TS extends UtilsStatus<TS,L,D>,
						TC extends UtilsStatus<TC,L,D>,
						SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
						QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
						CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
						QE extends UtilsStatus<QE,L,D>,
						SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
						UNIT extends UtilsStatus<UNIT,L,D>,
						ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
						MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
						DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
						OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
						OPTION extends JeeslSurveyOption<L,D>,
						CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
						DOMAIN extends JeeslSurveyDomain<L,DENTITY>,
						QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN,PATH>,
						PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
						DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
						DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
						ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN,DENTITY,DATTRIBUTE>,
						AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
						AT extends JeeslSurveyAnalysisTool<L,D,QE,QUERY,AQ,ATT>,
						ATT extends UtilsStatus<ATT,L,D>>
					extends AbstractSurveyBean<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyDomainBean.class);
	
	protected List<DENTITY> entities; public List<DENTITY> getEntities(){return entities;}
	protected List<QUERY> queries; public List<QUERY> getQueries(){return queries;}
	protected List<PATH> paths; public List<PATH> getPaths(){return paths;}
	protected List<DATTRIBUTE> attributes; public List<DATTRIBUTE> getAttributes(){return attributes;}
	
	private DOMAIN domain; public DOMAIN getDomain() {return domain;} public void setDomain(DOMAIN domain) {this.domain = domain;}
	private QUERY query; public QUERY getQuery() {return query;} public void setQuery(QUERY query) {this.query = query;}
	private PATH path; public PATH getPath() {return path;} public void setPath(PATH path) {this.path = path;}
	private DENTITY entity; public DENTITY getEntity() {return entity;}
	
	protected final SbSingleHandler<DOMAIN> sbhDomain; public SbSingleHandler<DOMAIN> getSbhDomain() {return sbhDomain;}
	
	private final EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY> efDomain;
	private final EjbSurveyDomainQueryFactory<L,D,DOMAIN,QUERY> efDomainQuery;
	private final EjbSurveyDomainPathFactory<L,D,QUERY,PATH,DENTITY,DATTRIBUTE> efDomainPath;
	
	private final Comparator<DENTITY> cpDentity;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractAdminSurveyDomainBean(SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
			SurveyCoreFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore,
			SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fbAnalysis)
	{
		super(fbTemplate,fbCore,fbAnalysis);

		sbhDomain = new SbSingleHandler<DOMAIN>(fbAnalysis.getClassDomain(),this);
		sbhDomain.setDebugOnInfo(true);
		
		efDomain = fbAnalysis.ejbDomain();
		efDomainQuery = fbAnalysis.ejbDomainQuery();
		efDomainPath = fbAnalysis.ejbDomainPath();
		cpDentity = new RevisionEntityComparator().factory(RevisionEntityComparator.Type.position);
	}
	
	protected void initSuperDomain(String userLocale, JeeslTranslationBean bTranslation, FacesMessageBean bMessage,
			JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
			JeeslSurveyCoreFacade<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fCore,
			JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fAnalysis,
			final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey)
	{
		super.initSuperSurvey(bTranslation.getLangKeys(),bMessage,fTemplate,fCore,fAnalysis,bSurvey);
		initSettings();
		super.initLocales(userLocale);
		
		entities = fCore.allOrderedPositionVisible(fbAnalysis.getClassDomainEntity());
		Collections.sort(entities,cpDentity);
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
	
	@Override protected void initSettings(){}
	
	@SuppressWarnings("unchecked")
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		if(ejb==null) {reset(true,true,true,true);}
		else if(JeeslSurveyDomain.class.isAssignableFrom(ejb.getClass()))
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
		sbhDomain.setList(fAnalysis.all(fbAnalysis.getClassDomain()));
	}
	
	public void addDomain()
	{
		logger.info(AbstractLogMessage.addEntity(fbAnalysis.getClassDomain()));
		domain = efDomain.build(null,sbhDomain.getList());
		domain.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveDomain() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(domain));
		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		domain = fAnalysis.save(domain);
		sbhDomain.setSelection(domain);
		reloadDomains();
		reloadQueries();
	}
	
	public void selectDomain()
	{
		reset(false,true,true,true);
		logger.info(AbstractLogMessage.selectEntity(domain));
		domain = efLang.persistMissingLangs(fAnalysis,localeCodes,domain);
		sbhDomain.setSelection(domain);
		reloadQueries();
	}
	
	private void reloadQueries()
	{
		queries = fAnalysis.allForParent(fbAnalysis.getClassDomainQuery(), domain);
	}
	
	public void addQuery()
	{
		logger.info(AbstractLogMessage.addEntity(fbAnalysis.getClassDomainQuery()));
		query = efDomainQuery.build(domain, queries);
		query.setName(efLang.createEmpty(localeCodes));
		query.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveQuery() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(query));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		query = fAnalysis.save(query);
		reloadQueries();
		reloadPaths();
		if(paths.isEmpty())
		{
			path = fAnalysis.save(efDomainPath.build(query,domain.getEntity(),paths));
			reloadPaths();
		}
	}
	
	public void deleteQuery() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(query));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		fAnalysis.rm(query);
		reloadQueries();
		reset(false,true,true,true);
	}
	
	public void selectQuery()
	{
		reset(false,false,true,true);
		logger.info(AbstractLogMessage.selectEntity(query));
		query = efLang.persistMissingLangs(fAnalysis,localeCodes,query);
		query = efDescription.persistMissingLangs(fAnalysis,localeCodes,query);
		reloadPaths();
	}
	
	private void reloadPaths()
	{
		paths = fAnalysis.allForParent(fbAnalysis.getClassDomainPath(), query);
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
		logger.info(AbstractLogMessage.addEntity(fbAnalysis.getClassDomainQuery()));
		path = efDomainPath.build(query,entity,paths);
		reloadAttributes();
	}
	
	public void savePath() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(path));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		path = fAnalysis.save(path);
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
		fAnalysis.rm(path);
		reloadPaths();
		reset(false,false,true,true);
	}
	
	private void reloadAttributes()
	{
		attributes = fAnalysis.fDomainAttributes(path.getEntity());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbAnalysis.getClassDomainAttribute(), attributes, path.getEntity()));}
	}
	
	public void reorderDomains() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fAnalysis, sbhDomain.getList());}
	public void reorderQueries() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fAnalysis, queries);}
}