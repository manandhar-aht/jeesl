package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.survey.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnalysisToolFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminSurveyAnalysisBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
						DOMAIN extends JeeslSurveyDomain<L,D,DENTITY>,
						QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN>,
						PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
						DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
						DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
						ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN>,
						AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
						AT extends JeeslSurveyAnalysisTool<L,D,QE,QUERY,AQ,ATT>,
						ATT extends UtilsStatus<ATT,L,D>>
					extends AbstractSurveyBean<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyAnalysisBean.class);
		
	protected List<ANALYSIS> analyses; public List<ANALYSIS> getAnalyses(){return analyses;}
	protected List<DOMAIN> domains; public List<DOMAIN> getDomains(){return domains;}
	protected List<QUESTION> questions; public List<QUESTION> getQuestions(){return questions;}
	protected List<OPTIONS> optionSets; public List<OPTIONS> getOptionSets(){return optionSets;}
	protected List<OPTION> options; public List<OPTION> getOptions(){return options;}
	protected List<SCHEME> schemes; public List<SCHEME> getSchemes() {return schemes;}
	protected List<SCORE> scores; public List<SCORE> getScores() {return scores;}
	private final List<QUERY> queries; public List<QUERY> getQueries() {return queries;}
	private List<AT> tools; public List<AT> getTools() {return tools;}
	private List<ATT> toolTypes; public List<ATT> getToolTypes() {return toolTypes;}
	private List<QE> questionElements; public List<QE> getQuestionElements() {return questionElements;}
	
	protected VERSION version; public VERSION getVersion() {return version;}public void setVersion(VERSION version) {this.version = version;}
	protected VERSION nestedVersion; public VERSION getNestedVersion() {return nestedVersion;} public void setNestedVersion(VERSION nestedVersion) {this.nestedVersion = nestedVersion;}
	private ANALYSIS analysis; public ANALYSIS getAnalysis() {return analysis;} public void setAnalysis(ANALYSIS analysis) {this.analysis = analysis;}
	protected SECTION section; public SECTION getSection(){return section;} public void setSection(SECTION section){this.section = section;}
	protected QUESTION question; public QUESTION getQuestion(){return question;} public void setQuestion(QUESTION question){this.question = question;}
	private AQ analysisQuestion; public AQ getAnalysisQuestion() {return analysisQuestion;} public void setAnalysisQuestion(AQ analysisQuestion) {this.analysisQuestion = analysisQuestion;}
	private AT tool; public AT getTool() {return tool;} public void setTool(AT tool) {this.tool = tool;}
	
	private final EjbSurveyAnalysisFactory<TEMPLATE,ANALYSIS> efAnalysis;
	private final EjbSurveyAnalysisQuestionFactory <L,D,QUESTION,ANALYSIS,AQ> efAnalysisQuestion;
	private final EjbSurveyAnalysisToolFactory <L,D,AQ,AT,ATT> efAnalysisTool;
	
	
	public AbstractAdminSurveyAnalysisBean(SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
											SurveyCoreFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore,
											SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fbAnalysis)
	{
		super(fbTemplate,fbCore,fbAnalysis);
		
		queries = new ArrayList<QUERY>();
		
		efAnalysis = fbAnalysis.ejbAnalysis();
		efAnalysisQuestion = fbAnalysis.ejbAnalysisQuestion();
		efAnalysisTool = fbAnalysis.ejbAnalysisTool();
	}
	
	protected void initSuperAnalysis(String userLocale, String[] localeCodes, FacesMessageBean bMessage,
			JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
			JeeslSurveyCoreFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fCore,
			JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fAnalysis,
			final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey)
	{
		super.initSuperSurvey(new ArrayList<String>(Arrays.asList(localeCodes)),bMessage,fTemplate,fCore,fAnalysis,bSurvey);
		initSettings();
		super.initLocales(userLocale);
		
		domains = fAnalysis.allOrderedPosition(fbAnalysis.getClassDomain());		
		toolTypes = bSurvey.getToolTypes();
		questionElements = fCore.allOrderedPositionVisible(fbTemplate.getClassElement());
		
		versions = new ArrayList<VERSION>();
		
		sbhCategory.silentCallback();
	}
	
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		logger.info(ejb.toString());
		if(fbTemplate.getClassTemplateCategory().isAssignableFrom(ejb.getClass()))
		{
			reset(true,true,true,true,true,true);
			versions = fCore.fVersions(sbhCategory.getSelection(),refId);
		}
	}
	
	private void reset(boolean rTemplate, boolean rVersion, boolean rSection, boolean rQuestion, boolean rAnalysisQuestion, boolean rTool)
	{
		if(rTemplate){template = null;}
		if(rVersion){version = null;}
		if(rSection){section = null;}
		if(rQuestion){question = null;}
		if(rAnalysisQuestion) {analysisQuestion=null;}
		if(rTool) {tool = null;}
	}
	
	public void selectVersion() throws UtilsNotFoundException
	{
		reset(false,false,true,true,true,true);
		logger.info(AbstractLogMessage.selectEntity(version));
		version = fCore.find(fbTemplate.getClassVersion(), version);
		reloadAnalyses();
		sections = bSurvey.getMapSection().get(version.getTemplate());
	}
	
	private void reloadAnalyses()
	{
		analyses = fAnalysis.allForParent(fbAnalysis.getClassAnalysis(), version.getTemplate());
	}
		
	public void addAnalysis()
	{
		logger.info(AbstractLogMessage.addEntity(fbAnalysis.getClassAnalysis()));
		analysis = efAnalysis.build(version.getTemplate(),analyses);
		analysis.setName(efLang.createEmpty(sbhLocale.getList()));
	}
	
	public void saveAnalysis() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(analysis));
		analysis.setDomain(fAnalysis.find(fbAnalysis.getClassDomain(),analysis.getDomain()));
		analysis = fAnalysis.save(analysis);
		reloadAnalyses();
		reloadAnalysis();
	}
	
	public void selectAnalysis()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(analysis));}
		analysis = efLang.persistMissingLangs(fCore, sbhLocale.getList(), analysis);
//		analysis = efDescription.persistMissingLangs(fSurvey, localeCodes, analysis);
		reset(false,false,true,true,true,true);
		reloadAnalysis();
	}
	
	private void reloadAnalysis()
	{
		queries.clear();
		queries.addAll(fAnalysis.allForParent(fbAnalysis.getClassDomainQuery(), analysis.getDomain()));
	}
	
	public void deleteAnalysis() throws UtilsConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(analysis));}
		fCore.rm(analysis);
		reset(true,false,false,true,true,true);
	}

	public void selectSection()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(section));}
		questions = bSurvey.getMapQuestion().get(section);
		reset(false,false,false,true,true,true);
	}
	
	public void selectQuestion()
	{
		logger.info(AbstractLogMessage.selectEntity(question));
		try
		{
			analysisQuestion = fAnalysis.fAnalysis(analysis, question);
			analysisQuestion = efLang.persistMissingLangs(fCore, sbhLocale.getList(), analysisQuestion);
			reloadTools();
		}
		catch (UtilsNotFoundException e)
		{
			analysisQuestion = efAnalysisQuestion.build(analysis, question);
			analysisQuestion.setName(efLang.createEmpty(sbhLocale.getList()));
		}
		reset(false,false,false,false,false,true);
	}
	
	public void saveAnalysisQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(analysisQuestion));}
		analysisQuestion = fAnalysis.save(analysisQuestion);
		reloadTools();
	}
	
	private void reloadTools()
	{
		tools = fAnalysis.allForParent(fbAnalysis.getClassAnalysisTool(), analysisQuestion);
	}
	
	public void addTool()
	{
		tool = efAnalysisTool.build(analysisQuestion, toolTypes.get(0),tools);
	}
	
	public void saveTool() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(tool));
		tool.setType(fAnalysis.find(fbAnalysis.getAttClass(),tool.getType()));
		tool.setElement(fAnalysis.find(fbTemplate.getClassElement(),tool.getElement()));
		tool = fAnalysis.save(tool);
		reloadTools();
	}
	
	public void selectTool()
	{
		logger.info(AbstractLogMessage.selectEntity(question));
		tool = fCore.find(fbAnalysis.getClassAnalysisTool(),tool);
	}
	
	public void deleteTool() throws UtilsConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(tool));}
		fAnalysis.rm(tool);
		reloadTools();
		reset(false,false,false,false,false,true);
	}
	
	public void reorderAnalyses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCore, analyses);}
}