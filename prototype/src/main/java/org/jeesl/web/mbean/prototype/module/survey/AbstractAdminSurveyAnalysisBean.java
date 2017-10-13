package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
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
										SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										SS extends UtilsStatus<SS,L,D>,
										SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>, QE extends UtilsStatus<QE,L,D>,
										SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										OPTIONS extends JeeslSurveyOptionSet<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>,
										ATT extends UtilsStatus<ATT,L,D>>
					extends AbstractSurveyBean<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyAnalysisBean.class);
	
	private final Class<ANALYSIS> cAnalysis;
	private final Class<AT> cTool;
	private final Class<ATT> cAtt;
	private final Class<QE> cQe;
	
	protected List<ANALYSIS> analyses; public List<ANALYSIS> getAnalyses(){return analyses;}
	protected List<QUESTION> questions; public List<QUESTION> getQuestions(){return questions;}
	protected List<OPTIONS> optionSets; public List<OPTIONS> getOptionSets(){return optionSets;}
	protected List<OPTION> options; public List<OPTION> getOptions(){return options;}
	protected List<SCHEME> schemes; public List<SCHEME> getSchemes() {return schemes;}
	protected List<SCORE> scores; public List<SCORE> getScores() {return scores;}
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
	
	private final EjbSurveyAnalysisFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT> efAnalysis;
	private final EjbSurveyAnalysisQuestionFactory <L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT> efAnalysisQuestion;
	private final EjbSurveyAnalysisToolFactory <L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT> efAnalysisTool;
	
	
	public AbstractAdminSurveyAnalysisBean(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<SURVEY> cSurvey, final Class<SS> cSs, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<TS> cTs, final Class<TC> cTc, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<QE> cQe, final Class<SCORE> cScore, final Class<UNIT> cUnit, final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption, final Class<ANALYSIS> cAnalysis, final Class<AQ> cAq, final Class<AT> cTool, final Class<ATT> cAtt)
	{
		super(cL,cD,cLoc,cSurvey,cSs,cScheme,cTemplate,cVersion,cTs,cTc,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption,cAtt);
		this.cAnalysis=cAnalysis;
		this.cTool=cTool;
		this.cAtt=cAtt;
		this.cQe=cQe;
		
		efAnalysis = ffSurvey.ejbAnalysis(cAnalysis);
		efAnalysisQuestion = ffSurvey.ejbAnalysisQuestion(cAq);
		efAnalysisTool = ffSurvey.ejbAnalysisTool(cTool);
	}
	
	protected void initSuperAnalysis(String userLocale, String[] localeCodes, FacesMessageBean bMessage, JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT> fSurvey, final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS,AQ,AT,ATT> bSurvey)
	{
		super.initSuperSurvey(localeCodes,bMessage,fSurvey, bSurvey);
		initSettings();
		super.initLocales(userLocale);
		
		toolTypes = bSurvey.getToolTypes();
		questionElements = fSurvey.allOrderedPositionVisible(cQe);
		
		versions = new ArrayList<VERSION>();
		
		sbhCategory.silentCallback();
	}
	
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		logger.info(ejb.toString());
		if(cTc.isAssignableFrom(ejb.getClass()))
		{
			reset(true,true,true,true,true,true);
			versions = fSurvey.fVersions(sbhCategory.getSelection());
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
	
	protected void selectVersion() throws UtilsNotFoundException
	{
		reset(false,false,true,true,true,true);
		logger.info(AbstractLogMessage.selectEntity(version));
		version = fSurvey.find(cVersion, version);
		reloadAnalyses();
		sections = bSurvey.getMapSection().get(version.getTemplate());
	}
	
	private void reloadAnalyses()
	{
		analyses = fSurvey.allForParent(cAnalysis, version.getTemplate());
	}
		
	public void addAnalysis()
	{
		logger.info(AbstractLogMessage.addEntity(cAnalysis));
		analysis = efAnalysis.build(version.getTemplate());
		analysis.setName(efLang.createEmpty(sbhLocale.getList()));
	}
	
	public void saveAnalysis() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(analysis));
		analysis = fSurvey.save(analysis);
		reloadAnalyses();
	}
	
	public void selectAnalysis()
	{
		logger.info(AbstractLogMessage.selectEntity(analysis));
		analysis = efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), analysis);
//		analysis = efDescription.persistMissingLangs(fSurvey, localeCodes, analysis);
		reset(false,false,true,true,true,true);
	}

	public void selectSection()
	{
		logger.info(AbstractLogMessage.selectEntity(section));
		questions = bSurvey.getMapQuestion().get(section);
		reset(false,false,false,true,true,true);
	}
	
	public void selectQuestion()
	{
		logger.info(AbstractLogMessage.selectEntity(question));
		try
		{
			analysisQuestion = fSurvey.fAnalysis(analysis, question);
			analysisQuestion = efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), analysisQuestion);
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
		logger.info(AbstractLogMessage.saveEntity(analysisQuestion));
		analysisQuestion = fSurvey.save(analysisQuestion);
		reloadTools();
	}
	
	private void reloadTools()
	{
		tools = fSurvey.allForParent(cTool, analysisQuestion);
	}
	
	public void addTool()
	{
		tool = efAnalysisTool.build(analysisQuestion, toolTypes.get(0));
	}
	
	public void saveTool() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(tool));
		tool.setType(fSurvey.find(cAtt,tool.getType()));
		tool.setElement(fSurvey.find(cQe,tool.getElement()));
		tool = fSurvey.save(tool);
		reloadTools();
	}
	
	public void selectTool()
	{
		logger.info(AbstractLogMessage.selectEntity(question));
		tool = fSurvey.find(cTool,tool);
	}
	
	protected void reorderAnalyses() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, analyses);}
}