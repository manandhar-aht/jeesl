package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.survey.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.controller.handler.ui.helper.UiHelperSurvey;
import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.util.EjbIdFactory;
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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidationAlgorithm;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.model.json.module.survey.JsonSurveyValue;
import org.jeesl.util.comparator.ejb.PositionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminSurveyTemplateBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
						DOMAIN extends JeeslDomain<L,DENTITY>,
						QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
						PATH extends JeeslDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
						DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
						DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>,
						ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN,DENTITY,DATTRIBUTE>,
						AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
						AT extends JeeslSurveyAnalysisTool<L,D,QE,QUERY,DATTRIBUTE,AQ,ATT>,
						ATT extends UtilsStatus<ATT,L,D>,
						TOOLCACHETEMPLATE extends JeeslJobTemplate<L,D,?,?,?>,
						CACHE extends JeeslJobCache<TOOLCACHETEMPLATE,?>>
					extends AbstractSurveyBean<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT,TOOLCACHETEMPLATE,CACHE>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyTemplateBean.class);
	
	protected List<VERSION> nestedVersions; public List<VERSION> getNestedVersions(){return nestedVersions;}
	protected List<OPTIONS> optionSets; public List<OPTIONS> getOptionSets(){return optionSets;}
	private final List<CONDITION> conditions; public List<CONDITION> getConditions() {return conditions;}
	private final List<QUESTION> triggerQuestions; public List<QUESTION> getTriggerQuestions() {return triggerQuestions;}
	private final  List<OPTION> triggerOptions; public List<OPTION> getTriggerOptions(){return triggerOptions;}
	protected List<OPTION> options; public List<OPTION> getOptions(){return options;}
	protected List<SCHEME> schemes; public List<SCHEME> getSchemes() {return schemes;}
	protected List<SCORE> scores; public List<SCORE> getScores() {return scores;}
	
	protected VERSION version; public VERSION getVersion() {return version;}public void setVersion(VERSION version) {this.version = version;}
	protected VERSION nestedVersion; public VERSION getNestedVersion() {return nestedVersion;} public void setNestedVersion(VERSION nestedVersion) {this.nestedVersion = nestedVersion;}
	
	private SCHEME scheme; public SCHEME getScheme() {return scheme;} public void setScheme(SCHEME scheme) {this.scheme = scheme;}
	protected SECTION section; public SECTION getSection(){return section;} public void setSection(SECTION section){this.section = section;}
	protected QUESTION question; public QUESTION getQuestion(){return question;} public void setQuestion(QUESTION question){this.question = question;}
	private CONDITION condition; public CONDITION getCondition() {return condition;} public void setCondition(CONDITION condition) {this.condition = condition;}
	protected OPTIONS optionSet; public OPTIONS getOptionSet() {return optionSet;} public void setOptionSet(OPTIONS optionSet) {this.optionSet = optionSet;}
	private SCORE score; public SCORE getScore() {return score;} public void setScore(SCORE score) {this.score = score;}
	protected OPTION option; public OPTION getOption(){return option;} public void setOption(OPTION option){this.option = option;}
	
	private JsonSurveyValue questionCount; public JsonSurveyValue getQuestionCount() {return questionCount;}
	
	private UiHelperSurvey<VERSION,SECTION> uiHelper; public UiHelperSurvey<VERSION,SECTION> getUiHelper() {return uiHelper;}
	private Comparator<OPTION> cmpOption;
	
	public AbstractAdminSurveyTemplateBean(SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
			SurveyCoreFactoryBuilder<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore,
			SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT,TOOLCACHETEMPLATE> fbAnalysis)
	{
		super(fbTemplate,fbCore,fbAnalysis);

		cmpOption = new PositionComparator<OPTION>();
		options = new ArrayList<OPTION>();

		conditions = new ArrayList<CONDITION>();
		triggerQuestions = new ArrayList<QUESTION>();
		triggerOptions = new ArrayList<OPTION>();
		
		
		uiHelper = new UiHelperSurvey<VERSION,SECTION>();
	}
	
	protected void initSuperTemplate(String userLocale, String[] localeCodes, JeeslFacesMessageBean bMessage,
			JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
			JeeslSurveyCoreFacade<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fCore,
			JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fAnalysis,
			final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey)
	{
		super.initSuperSurvey(new ArrayList<String>(Arrays.asList(localeCodes)),bMessage,fTemplate,fCore,fAnalysis,bSurvey);
		initPageSettings();
		super.initLocales(userLocale);

		versions = new ArrayList<VERSION>();
		sbhCategory.silentCallback();
	}
	
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		logger.info(ejb.toString());
		if(fbTemplate.getClassTemplateCategory().isAssignableFrom(ejb.getClass()))
		{
			try
			{
				clearSelection();
				initTemplate();
				reloadVersions();
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
		}
	}
	
	protected void cancelAll() {clear(true,true,true,true,true,true,true,true,true);}
	public void cancelScheme(){clear(false,false,false,false,false,true,false,true,true);}
	public void calcelScore(){clear(false,false,false,false,false,false,true,true,true);}
	public void cancelCondition() {clear(false,false,false,false,false,false,true,true,true);}
	protected void clear(boolean cTemplate, boolean cVersion, boolean cSection, boolean cQuestion, boolean cOption, boolean rScheme, boolean rScore, boolean rSet, boolean rCondition)
	{
		if(cTemplate){template = null;}
		if(cVersion){version = null;}
		if(cSection){section = null;}
		if(cQuestion){question = null;options.clear();}	
		if(cOption){option=null;}
		if(rScheme){scheme = null;}
		if(rScore){score = null;}
		if(rSet){optionSet = null;}
		if(rCondition) {condition=null;}
	}
	
	protected void clearSelection()
	{
		if(sections!=null){sections.clear();}
		template = null;
		section=null;
		question=null;
	}
		
	//Template
	protected void reloadTemplate()
	{
		template = fTemplate.load(template,false,false);
		bSurvey.updateTemplate(template);
		version = template.getVersion();
		sections = template.getSections();
		schemes = template.getSchemes();
		optionSets = template.getOptionSets();
		reloadTemplateQuestions();
	}
	
	private void reloadTemplateQuestions()
	{
		triggerQuestions.clear();
		for(SECTION section : sections)
		{
			if(bSurvey.getMapQuestion().containsKey(section))
			{
				triggerQuestions.addAll(bSurvey.getMapQuestion().get(section));
			}
		}
	}
	
	protected void initTemplate() throws UtilsNotFoundException{}
	
	protected <E extends Enum<E>> void initTemplate(boolean withVersion, E statusCode)
	{
		if(sbhCategory.isSelected() && version!=null)
		{
			try
			{
				TS status = fTemplate.fByCode(fbTemplate.getClassTemplateStatus(),statusCode);
				template = fTemplate.fcSurveyTemplate(sbhCategory.getSelection(),version,status,nestedVersion);
				logger.info("Resolved "+fbTemplate.getClassTemplate().getSimpleName()+": "+template.toString());
				version = template.getVersion();
				reloadTemplate();
				section=null;
				question=null;
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
		}
	}
	
	//Version
	public void addVersion()
	{
		logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassVersion()));
		version = efVersion.build(refId);
		version.setName(efLang.createEmpty(sbhLocale.getList()));
		version.setDescription(efDescription.createEmpty(sbhLocale.getList()));
	}
	
	protected void reloadVersions()
	{
		if(refId!=null && refId<=0) {versions = new ArrayList<VERSION>();}
		else{versions = fCore.fVersions(sbhCategory.getSelection(),refId);}
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbTemplate.getClassVersion(), versions));}
		
		nestedVersions = new ArrayList<VERSION>();
		for(TC c : sbhCategory.getList())
		{
			if(debugOnInfo) {logger.info("Testing nested for "+c.toString());}
			if(!c.equals(sbhCategory.getSelection()))
			{
				if(refId!=null && refId<=0) {}
				else{nestedVersions.addAll(fCore.fVersions(c,refId));}
			}
		}
	}
	
	public void selectVersion() throws UtilsNotFoundException
	{
		clearSelection();
		logger.info(AbstractLogMessage.selectEntity(version));
		efLang.persistMissingLangs(fCore, sbhLocale.getList(), version);
		efDescription.persistMissingLangs(fCore, sbhLocale.getList(), version);
		version = fCore.find(fbTemplate.getClassVersion(), version);
		initTemplate();
		if(version.getTemplate()!=null && version.getTemplate().getNested()!=null){nestedVersion = version.getTemplate().getNested().getVersion();}
		uiHelper.check(version,sections);
	}
	
	public void saveVersion() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(version));
		if(nestedVersion!=null)
		{
			nestedVersion = fCore.find(fbTemplate.getClassVersion(),nestedVersion);
		}
		
		if(EjbIdFactory.isSaved(version))
		{
			if(nestedVersion!=null){version.getTemplate().setNested(nestedVersion.getTemplate());}
			version = fCore.save(version);
		}
		
		initTemplate();
		reloadVersions();
	}
	
	public void deleteVersion() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(version));
		fCore.rmVersion(version);
		clear(true,true,true,true,true,true,true,true,true);
		reloadVersions();
	}
	
	//Section
	public void addSection()
	{
		logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassSection()));
		section = efSection.build(template,0);
		section.setName(efLang.createEmpty(sbhLocale.getList()));
		section.setDescription(efDescription.createEmpty(sbhLocale.getList()));
		nnb.doubleToA(section.getScoreLimit());
		nnb.doubleToB(section.getScoreNormalize());
	}
	
	public void selectSection()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(section));}
		efLang.persistMissingLangs(fCore, sbhLocale.getList(), section);
		efDescription.persistMissingLangs(fCore, sbhLocale.getList(), section);
		loadSection();
		nnb.doubleToA(section.getScoreLimit());
		nnb.doubleToB(section.getScoreNormalize());
	}
	
	protected void loadSection()
	{
		section = fCore.load(section);
		questions.clear();
		questions.addAll(section.getQuestions());
		bSurvey.updateSection(section);
	}
		
	public void saveSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(section));
		section.setScoreLimit(nnb.aToDouble());
		section.setScoreNormalize(nnb.bToDouble());
		section = fCore.save(section);
		reloadTemplate();
		loadSection();
	}
	
	public void rmSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(section));
		fCore.rm(section);
		section=null;
		question=null;
		reloadTemplate();
	}
	
	//Option Sets
	public void addSet()
	{
		logger.info(AbstractLogMessage.addEntity(fbTemplate.getOptionSetClass()));
		clear(false,false,true,true,true,true,true,false,true);
		optionSet = efOptionSet.build(template,optionSets);
		optionSet.setName(efLang.createEmpty(sbhLocale.getList()));
	}
	
	public void saveSet() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(optionSet));
		optionSet = fCore.save(optionSet);
		reloadTemplate();
		reloadOptionSet(true);
	}
	
	public void selectSet()
	{
		logger.info(AbstractLogMessage.selectEntity(optionSet));
		clear(false,false,true,true,true,true,true,false,true);
		optionSet = efLang.persistMissingLangs(fCore, sbhLocale.getList(), optionSet);
		reloadOptionSet(false);
	}
/*	
	public void rmSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(section));
		fSurvey.rm(section);
		section=null;
		question=null;
		reloadTemplate();
	}
*/	
	//Question
	public void addQuestion()
	{
		logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassQuestion()));
		question = efQuestion.build(section,null);
		question.setName(efLang.createEmpty(sbhLocale.getList()));
		question.setText(efDescription.createEmpty(sbhLocale.getList()));
		question.setDescription(efDescription.createEmpty(sbhLocale.getList()));
	}
	
	public void selectQuestion()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(question));}
		question = efLang.persistMissingLangs(fCore, sbhLocale.getList(), question);
		question = efDescription.persistMissingLangs(fCore, sbhLocale.getList(), question);
		if(question.getText()==null) {question.setText(efDescription.createEmpty(sbhLocale.getList()));}
		for(LOC loc : sbhLocale.getList())
		{
			if(!question.getText().containsKey(loc.getCode()))
			{
				try
				{
					D d = fCore.persist(efDescription.create(loc.getCode(), ""));
					question.getText().put(loc.getCode(), d);
					question = fCore.update(question);
				}
				catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				catch (UtilsLockingException e) {e.printStackTrace();}
			}
		}
		
		reloadQuestion();
		clear(false,false,false,false,true,true,true,true,true);
	}

	private void reloadQuestion()
	{
		question = fCore.find(fbTemplate.getClassQuestion(),question);
		question = fCore.load(question);
//		questionCount = fAnalysis.surveyCountAnswers(question);
		Collections.sort(question.getOptions(),cmpOption);
		options.clear(); options.addAll(question.getOptions());
		reloadConditions();
		bSurvey.updateOptions(question);
	}
	
	public void saveQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(question));
		if(question.getUnit()!=null){question.setUnit(fCore.find(fbTemplate.getClassUnit(),question.getUnit()));}
		if(question.getOptionSet()!=null){question.setOptionSet(fCore.find(fbTemplate.getOptionSetClass(),question.getOptionSet()));}
		question = fCore.save(question);
		loadSection();
		reloadQuestion();
		reloadTemplateQuestions();
	}
	
	public void rmQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(question));}
		fCore.rm(question);
		clear(false,false,false,true,true,true,true,true,true);
		loadSection();
	}
	
	//Option
	private void reloadOptionSet(boolean updateBean)
	{
		optionSet = fCore.find(fbTemplate.getOptionSetClass(),optionSet);
		optionSet = fCore.load(optionSet);
		Collections.sort(optionSet.getOptions(),cmpOption);
		options.clear();
		options.addAll(optionSet.getOptions());
		if(updateBean) {bSurvey.updateOptions(optionSet);}
	}
	
	public void addOption()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassOption()));}
		option = efOption.build(question,"");
		option.setName(efLang.createEmpty(sbhLocale.getList()));
		option.setDescription(efDescription.createEmpty(sbhLocale.getList()));
	}
	
	public void selectOption()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(option));}
		option = efLang.persistMissingLangs(fCore, sbhLocale.getList(), option);
		option = efDescription.persistMissingLangs(fCore, sbhLocale.getList(), option);
	}
	
	public void saveQuestionOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(option));}
		option = fCore.saveOption(question,option);
		reloadQuestion();
		bMessage.growlSuccessSaved();
	}
	public void saveSetOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(option));}
		option = fCore.saveOption(optionSet,option);
		reloadOptionSet(true);
		bMessage.growlSuccessSaved();
	}
	
	public void rmQuestionOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(option));}
		fCore.rmOption(optionSet,option);
		reloadQuestion();
		bMessage.growlSuccessRemoved();
	}
	public void rmSetOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(option));}
		fCore.rmOption(optionSet,option);
		clear(false,false,true,true,true,true,true,false,true);
		reloadOptionSet(true);
		bMessage.growlSuccessRemoved();
	}
	
	//Scheme
	public void addScheme()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassScheme()));}
		scheme = efScheme.build(template, "", schemes);
		scheme.setName(efLang.createEmpty(langs));
		scheme.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectScheme()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(scheme));}
	}
	
	public void saveScheme() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(scheme));}
		scheme = fCore.save(scheme);
		reloadTemplate();
	}
	
	public void addScore()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassScore()));}
		score = efScore.build(question);
	}
	
	public void saveScore() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(score));}
		score = fCore.save(score);
		reloadQuestion();
		bMessage.growlSuccessSaved();
	}
	
	private void reloadConditions()
	{
		conditions.clear(); conditions.addAll(fTemplate.allForParent(fbTemplate.getClassCondition(), question));
	}
	
	public void addCondition()
	{
		clear(false,false,false,false,true,true,true,true,false);
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbTemplate.getClassCondition()));}
		QUESTION triggerQuestion = null; if(!triggerQuestions.isEmpty()) {triggerQuestion = triggerQuestions.get(0);}
		QE element = null; if(!bSurvey.getElements().isEmpty()){element = bSurvey.getElements().get(0);}
		condition = efCondition.build(question, element,triggerQuestion, conditions);
		triggerChanged();
	}
	
	public void triggerChanged()
	{
		triggerOptions.clear();
		if(condition.getTriggerQuestion()!=null && bSurvey.getMapOption().containsKey(condition.getTriggerQuestion())) 
		{
			triggerOptions.addAll(bSurvey.getMapOption().get(condition.getTriggerQuestion()));
		}
	}
	
	public void saveCondition() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(condition));}
		condition.setTriggerQuestion(fTemplate.find(fbTemplate.getClassQuestion(), condition.getTriggerQuestion()));
		condition.setElement(fTemplate.find(fbTemplate.getClassElement(), condition.getElement()));
		if(condition.getOption()!=null) {condition.setOption(fTemplate.find(fbTemplate.getClassOption(), condition.getOption()));}
		condition = fTemplate.save(condition);
		reloadConditions();
	}
	
	public void selectCondition()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(condition));}
		condition = fTemplate.find(fbTemplate.getClassCondition(), condition);
		triggerChanged();
	}
	
	public void deleteCondition() throws UtilsConstraintViolationException
	{
		fTemplate.rm(condition);
		clear(false,false,false,false,true,true,true,true,true);
		reloadConditions();
	}
	
	public void reorderSections() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCore, sections);}
	public void reorderQuestions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fCore, questions);}
	public void reorderConditions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fTemplate, conditions);}
	public void reorderOptions() throws UtilsConstraintViolationException, UtilsLockingException
	{
		PositionListReorderer.reorder(fCore, options);
		if(questions!=null) {reloadQuestion();}
		if(optionSet!=null) {reloadOptionSet(true);}
	}
	
}