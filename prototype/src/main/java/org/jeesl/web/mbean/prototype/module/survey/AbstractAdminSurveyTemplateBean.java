package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.handler.ui.helper.UiHelperSurvey;
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
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
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
import net.sf.ahtutils.util.comparator.ejb.PositionComparator;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminSurveyTemplateBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
						SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						SS extends UtilsStatus<SS,L,D>,
						SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
						TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS>,
						VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
						TS extends UtilsStatus<TS,L,D>,
						TC extends UtilsStatus<TC,L,D>,
						SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
						QE extends UtilsStatus<QE,L,D>,
						SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE>,
						UNIT extends UtilsStatus<UNIT,L,D>,
						ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
						OPTION extends JeeslSurveyOption<L,D>,
						CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>, DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>, AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>, AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>, ATT extends UtilsStatus<ATT,L,D>>
					extends AbstractSurveyBean<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyTemplateBean.class);
	
	protected List<VERSION> nestedVersions; public List<VERSION> getNestedVersions(){return nestedVersions;}
	protected List<OPTIONS> optionSets; public List<OPTIONS> getOptionSets(){return optionSets;}
	protected List<OPTION> options; public List<OPTION> getOptions(){return options;}
	protected List<SCHEME> schemes; public List<SCHEME> getSchemes() {return schemes;}
	protected List<SCORE> scores; public List<SCORE> getScores() {return scores;}
	
	protected VERSION version; public VERSION getVersion() {return version;}public void setVersion(VERSION version) {this.version = version;}
	protected VERSION nestedVersion; public VERSION getNestedVersion() {return nestedVersion;} public void setNestedVersion(VERSION nestedVersion) {this.nestedVersion = nestedVersion;}
	
	private SCHEME scheme; public SCHEME getScheme() {return scheme;} public void setScheme(SCHEME scheme) {this.scheme = scheme;}
	protected SECTION section; public SECTION getSection(){return section;} public void setSection(SECTION section){this.section = section;}
	protected QUESTION question; public QUESTION getQuestion(){return question;} public void setQuestion(QUESTION question){this.question = question;}
	protected OPTIONS optionSet; public OPTIONS getOptionSet() {return optionSet;} public void setOptionSet(OPTIONS optionSet) {this.optionSet = optionSet;}
	private SCORE score; public SCORE getScore() {return score;} public void setScore(SCORE score) {this.score = score;}
	protected OPTION option; public OPTION getOption(){return option;} public void setOption(OPTION option){this.option = option;}
	
	private UiHelperSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> uiHelper; public UiHelperSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> getUiHelper() {return uiHelper;}
	private Comparator<OPTION> cmpOption;
	
	public AbstractAdminSurveyTemplateBean(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<SURVEY> cSurvey, final Class<SS> cSs, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<TS> cTs, final Class<TC> cTc, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<UNIT> cUnit, final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption, final Class<ATT> cAtt)
	{
		super(cL,cD,cLoc,cSurvey,cSs,cScheme,cTemplate,cVersion,cTs,cTc,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption,cAtt);

		cmpOption = new PositionComparator<OPTION>();
		options = new ArrayList<OPTION>();

		uiHelper = new UiHelperSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>();
	}
	
	protected void initSuperTemplate(String userLocale, String[] localeCodes, FacesMessageBean bMessage, JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey, JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fAnalysis, final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> bSurvey)
	{
		super.initSuperSurvey(localeCodes,bMessage,fSurvey,fAnalysis,bSurvey);
		initSettings();
		super.initLocales(userLocale);

		versions = new ArrayList<VERSION>();
		sbhCategory.silentCallback();
	}
	
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		logger.info(ejb.toString());
		if(cTc.isAssignableFrom(ejb.getClass()))
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
	
	public void cancelScheme(){clear(false,false,false,false,false,true,false,true);}
	public void calcelScore(){clear(false,false,false,false,false,false,true,true);}
	protected void clear(boolean cTemplate, boolean cVersion, boolean cSection, boolean cQuestion, boolean cOption, boolean rScheme, boolean rScore, boolean rSet)
	{
		if(cTemplate){template = null;}
		if(cVersion){version = null;}
		if(cSection){section = null;}
		if(cQuestion){question = null;options.clear();}	
		if(cOption){option=null;}
		if(rScheme){scheme = null;}
		if(rScore){score = null;}
		if(rSet){optionSet = null;}
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
		template = fSurvey.load(template,false,false);
		bSurvey.updateTemplate(template);
		version = template.getVersion();
		sections = template.getSections();
		schemes = template.getSchemes();
		optionSets = template.getOptionSets();
	}
	
	protected void initTemplate() throws UtilsNotFoundException{}
	
	protected <E extends Enum<E>> void initTemplate(boolean withVersion, E statusCode)
	{
		if(sbhCategory.isSelected() && version!=null)
		{
			try
			{
				TS status = fSurvey.fByCode(cTs,statusCode);
				template = fSurvey.fcSurveyTemplate(sbhCategory.getSelection(),version,status,nestedVersion);
				logger.info("Resolved "+cTemplate.getSimpleName()+": "+template.toString());
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
		logger.info(AbstractLogMessage.addEntity(cVersion));
		version = efVersion.build();
		version.setName(efLang.createEmpty(sbhLocale.getList()));
		version.setDescription(efDescription.createEmpty(sbhLocale.getList()));
	}
	
	protected void reloadVersions()
	{
		versions = fSurvey.fVersions(sbhCategory.getSelection());
		
		nestedVersions = new ArrayList<VERSION>();
		
		for(TC c : sbhCategory.getList())
		{
			if(!c.equals(sbhCategory.getSelection()))
			{
				nestedVersions.addAll(fSurvey.fVersions(c));
			}
		}
	}
	
	protected void selectVersion() throws UtilsNotFoundException
	{
		clearSelection();
		logger.info(AbstractLogMessage.selectEntity(version));
		efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), version);
		efDescription.persistMissingLangs(fSurvey, sbhLocale.getList(), version);
		version = fSurvey.find(cVersion, version);
		initTemplate();
		if(version.getTemplate()!=null && version.getTemplate().getNested()!=null){nestedVersion = version.getTemplate().getNested().getVersion();}
		uiHelper.check(version,sections);
	}
	
	protected void saveVersion() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(version));
		if(nestedVersion!=null)
		{
			nestedVersion = fSurvey.find(cVersion,nestedVersion);
		}
		
		if(EjbIdFactory.isSaved(version))
		{
			if(nestedVersion!=null){version.getTemplate().setNested(nestedVersion.getTemplate());}
			version = fSurvey.save(version);
		}
		
		initTemplate();
		reloadVersions();
	}
	
	protected void rmVersion() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(version));
		fSurvey.rmVersion(version);
		clear(true,true,true,true,true,true,true,true);
		reloadVersions();
	}
	
	//Section
	public void addSection()
	{
		logger.info(AbstractLogMessage.addEntity(cSection));
		section = efSection.build(template,0);
		section.setName(efLang.createEmpty(sbhLocale.getList()));
		section.setDescription(efDescription.createEmpty(sbhLocale.getList()));
		nnb.doubleToA(section.getScoreLimit());
		nnb.doubleToB(section.getScoreNormalize());
	}
	
	public void selectSection()
	{
		logger.info(AbstractLogMessage.selectEntity(section));
		efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), section);
		efDescription.persistMissingLangs(fSurvey, sbhLocale.getList(), section);
		loadSection();
		nnb.doubleToA(section.getScoreLimit());
		nnb.doubleToB(section.getScoreNormalize());
	}
	
	protected void loadSection()
	{
		section = fSurvey.load(section);
		questions.clear();
		questions.addAll(section.getQuestions());
		bSurvey.updateSection(section);
	}
		
	public void saveSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(section));
		section.setScoreLimit(nnb.aToDouble());
		section.setScoreNormalize(nnb.bToDouble());
		section = fSurvey.save(section);
		reloadTemplate();
		loadSection();
	}
	
	public void rmSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(section));
		fSurvey.rm(section);
		section=null;
		question=null;
		reloadTemplate();
	}
	
	//Option Sets
	public void addSet()
	{
		logger.info(AbstractLogMessage.addEntity(cOptions));
		clear(false,false,true,true,true,true,true,false);
		optionSet = efOptionSet.build(template,optionSets);
		optionSet.setName(efLang.createEmpty(sbhLocale.getList()));
	}
	
	public void saveSet() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(optionSet));
		optionSet = fSurvey.save(optionSet);
		reloadTemplate();
		reloadOptionSet(true);
	}
	
	public void selectSet()
	{
		logger.info(AbstractLogMessage.selectEntity(optionSet));
		clear(false,false,true,true,true,true,true,false);
		optionSet = efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), optionSet);
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
		logger.info(AbstractLogMessage.addEntity(cQuestion));
		question = efQuestion.build(section,null);
		question.setName(efLang.createEmpty(sbhLocale.getList()));
		question.setText(efDescription.createEmpty(sbhLocale.getList()));
		question.setDescription(efDescription.createEmpty(sbhLocale.getList()));
	}
	
	public void selectQuestion()
	{
		logger.info(AbstractLogMessage.selectEntity(question));
		question = efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), question);
		question = efDescription.persistMissingLangs(fSurvey, sbhLocale.getList(), question);
		if(question.getText()==null) {question.setText(efDescription.createEmpty(sbhLocale.getList()));}
		for(LOC loc : sbhLocale.getList())
		{
			if(!question.getText().containsKey(loc.getCode()))
			{
				try
				{
					D d = fSurvey.persist(efDescription.create(loc.getCode(), ""));
					question.getText().put(loc.getCode(), d);
					question = fSurvey.update(question);
				}
				catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				catch (UtilsLockingException e) {e.printStackTrace();}
			}
		}
		
		reloadQuestion();
		clear(false,false,false,false,true,true,true,true);
	}
	
	private void reloadQuestion()
	{
		question = fSurvey.find(cQuestion,question);
		question = fSurvey.load(question);
		Collections.sort(question.getOptions(),cmpOption);
		options.clear();
		options.addAll(question.getOptions());
		bSurvey.updateOptions(question);
	}
	
	public void saveQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(question));
		if(question.getUnit()!=null){question.setUnit(fSurvey.find(cUnit,question.getUnit()));}
		if(question.getOptionSet()!=null){question.setOptionSet(fSurvey.find(cOptions,question.getOptionSet()));}
		question = fSurvey.save(question);
		loadSection();
		reloadQuestion();
	}
	
	public void rmQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(question));}
		fSurvey.rm(question);
		clear(false,false,false,true,true,true,true,true);
		loadSection();
	}
	
	//Option
	private void reloadOptionSet(boolean updateBean)
	{
		optionSet = fSurvey.find(cOptions,optionSet);
		optionSet = fSurvey.load(optionSet);
		Collections.sort(optionSet.getOptions(),cmpOption);
		options.clear();
		options.addAll(optionSet.getOptions());
		if(updateBean) {bSurvey.updateOptions(optionSet);}
	}
	
	public void addOption()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cOption));}
		option = efOption.build(question,"");
		option.setName(efLang.createEmpty(sbhLocale.getList()));
		option.setDescription(efDescription.createEmpty(sbhLocale.getList()));
	}
	
	public void selectOption()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(option));}
		option = efLang.persistMissingLangs(fSurvey, sbhLocale.getList(), option);
		option = efDescription.persistMissingLangs(fSurvey, sbhLocale.getList(), option);
	}
	
	public void saveQuestionOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(option));}
		option = fSurvey.saveOption(question,option);
		reloadQuestion();
		bMessage.growlSuccessSaved();
	}
	public void saveSetOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(option));}
		option = fSurvey.saveOption(optionSet,option);
		reloadOptionSet(true);
		bMessage.growlSuccessSaved();
	}
	
	public void rmQuestionOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(option));}
		fSurvey.rmOption(optionSet,option);
		reloadQuestion();
		bMessage.growlSuccessRemoved();
	}
	public void rmSetOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(option));}
		fSurvey.rmOption(optionSet,option);
		clear(false,false,true,true,true,true,true,false);
		reloadOptionSet(true);
		bMessage.growlSuccessRemoved();
	}
	
	//Scheme
	public void addScheme()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cScheme));}
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
		scheme = fSurvey.save(scheme);
		reloadTemplate();
	}
	
	public void addScore()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cScore));}
		score = efScore.build(question);
	}
	
	public void saveScore() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(score));}
		score = fSurvey.save(score);
		reloadQuestion();
		bMessage.growlSuccessSaved();
	}
	
	protected void reorderSections() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, sections);}
	protected void reorderQuestions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, questions);}
	protected void reorderOptions() throws UtilsConstraintViolationException, UtilsLockingException
	{
		PositionListReorderer.reorder(fSurvey, options);
		if(questions!=null) {reloadQuestion();}
		if(optionSet!=null) {reloadOptionSet(true);}
	}
}