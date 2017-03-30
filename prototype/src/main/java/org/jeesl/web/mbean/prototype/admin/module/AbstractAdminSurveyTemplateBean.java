package org.jeesl.web.mbean.prototype.admin.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.handler.ui.helper.UiHelperSurvey;
import org.jeesl.factory.ejb.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveySchemeFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyScoreFactory;
import org.jeesl.factory.ejb.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateVersionFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.interfaces.model.module.survey.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.JeeslSurveyTemplateVersion;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
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
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminSurveyTemplateBean <L extends UtilsLang,
										D extends UtilsDescription,
										SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										SS extends UtilsStatus<SS,L,D>,
										SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
										CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyTemplateBean.class);

	protected JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey;
	
	private Class<SCHEME> cScheme;
	private Class<TEMPLATE> cTemplate;
	private Class<VERSION> cVersion;
	private Class<TS> cTs;
	private Class<SECTION> cSection;
	protected Class<QUESTION> cQuestion;
	private Class<SCORE> cScore;
	protected Class<UNIT> cUnit;
	private Class<OPTION> cOption;

	protected SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> ffSurvey;
	protected EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efVersion;
	protected EjbSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efSection;
	protected EjbSurveyQuestionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efQuestion;
	private EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efOption;
	private EjbSurveySchemeFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efScheme;
	private EjbSurveyScoreFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efScore;

	protected List<TC> categories; public List<TC> getCategories(){return categories;}
	protected List<VERSION> versions; public List<VERSION> getVersions(){return versions;}
	protected List<SECTION> sections; public List<SECTION> getSections(){return sections;}
	protected List<QUESTION> questions; public List<QUESTION> getQuestions(){return questions;}
	protected List<OPTION> options; public List<OPTION> getOptions(){return options;}
	protected List<SCHEME> schemes; public List<SCHEME> getSchemes() {return schemes;}
	protected List<SCORE> scores; public List<SCORE> getScores() {return scores;}
	
	protected TC category; public TC getCategory() {return category;} public void setCategory(TC category) {this.category = category;}
	protected VERSION version; public VERSION getVersion() {return version;}public void setVersion(VERSION version) {this.version = version;}
	protected TEMPLATE template; public TEMPLATE getTemplate(){return template;} public void setTemplate(TEMPLATE template){this.template = template;}
	protected SECTION section; public SECTION getSection(){return section;} public void setSection(SECTION section){this.section = section;}
	protected QUESTION question; public QUESTION getQuestion(){return question;} public void setQuestion(QUESTION question){this.question = question;}
	protected OPTION option; public OPTION getOption(){return option;} public void setOption(OPTION option){this.option = option;}
	private SCHEME scheme; public SCHEME getScheme() {return scheme;} public void setScheme(SCHEME scheme) {this.scheme = scheme;}
	private SCORE score; public SCORE getScore() {return score;} public void setScore(SCORE score) {this.score = score;}
	
	private UiHelperSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> uiHelper; public UiHelperSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> getUiHelper() {return uiHelper;}

	public AbstractAdminSurveyTemplateBean()
	{
		uiHelper = new UiHelperSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>();
	}
	
	protected void initSuper(String[] localeCodes, FacesMessageBean bMessage, JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey, final Class<L> cL, final Class<D> cD, final Class<SURVEY> cSurvey, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, Class<TS> cTs, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<UNIT> cUnit, final Class<ANSWER> cAnswer, final Class<DATA> cData, final Class<OPTION> cOption)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fSurvey = fSurvey;
		
		this.cScheme = cScheme;
		this.cTemplate = cTemplate;
		this.cVersion = cVersion;
		this.cTs = cTs;
		this.cSection = cSection;
		this.cQuestion = cQuestion;
		this.cScore = cScore;
		this.cUnit = cUnit;
		this.cOption = cOption;
		
		ffSurvey = SurveyFactoryFactory.factory(cSurvey,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cAnswer,cData,cOption);
		efVersion = ffSurvey.version();
		efSection = ffSurvey.section();
		efQuestion = ffSurvey.question();
		efScore = ffSurvey.score();
		efOption = ffSurvey.option();
		efScheme = ffSurvey.scheme();
		
		categories = new ArrayList<TC>();
		versions = new ArrayList<VERSION>();
	}
	
	public void cancelScheme(){clear(false,false,false,false,false,true);}
	protected void clear(boolean cTemplate, boolean cVersion, boolean cSection, boolean cQuestion, boolean cOption, boolean rScheme)
	{
		if(cTemplate){template = null;}
		if(cVersion){version = null;}
		if(cSection){section = null;}
		if(cQuestion){question = null;}	
		if(cOption){option=null;}
		if(rScheme){scheme = null;}
	}
	
	public void selectCategory() throws UtilsNotFoundException
	{
		clearSelection();
		initTemplate();
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
		logger.info("Reloading "+template.toString());
		template = fSurvey.load(template);
		version = template.getVersion();
		sections = template.getSections();
		schemes = template.getSchemes();
	}
	
	protected void initTemplate() throws UtilsNotFoundException{}
	
	protected <E extends Enum<E>> void initTemplate(boolean withVersion, E statusCode)
	{
		if(category!=null && version!=null)
		{
			try
			{
				TS status = fSurvey.fByCode(cTs,statusCode);
				template = fSurvey.fcSurveyTemplate(category,version,status);
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
	}
	
	protected void reloadVersions()
	{
		versions = fSurvey.fVersions(category);
	}
	
	protected void selectVersion() throws UtilsNotFoundException
	{
		clearSelection();
		logger.info(AbstractLogMessage.selectEntity(version));
		version = fSurvey.find(cVersion, version);
		initTemplate();
		uiHelper.check(version,sections);
	}
	
	protected void saveVersion() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(version));
		if(EjbIdFactory.isSaved(version))
		{
			version = fSurvey.save(version);
		}
		initTemplate();
		reloadVersions();
	}
	
	protected void rmVersion() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(version));
		fSurvey.rmVersion(version);
		clear(true,true,true,true,true,true);
		reloadVersions();
	}
	
	//Section
	public void selectSection()
	{
		logger.info(AbstractLogMessage.selectEntity(section));
		loadSection();
		nnb.doubleToA(section.getScoreLimit());
		nnb.doubleToB(section.getScoreNormalize());
	}
	
	protected void loadSection()
	{
		section = fSurvey.load(section);
		questions = section.getQuestions();
	}
	
	public void addSection()
	{
		logger.info(AbstractLogMessage.addEntity(cSection));
		section = efSection.build(template,"",0);
		nnb.doubleToA(section.getScoreLimit());
		nnb.doubleToB(section.getScoreNormalize());
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
	
	//Question
	public void addQuestion()
	{
		logger.info(AbstractLogMessage.addEntity(cQuestion));
		question = efQuestion.build(section,null);
	}
	
	public void selectQuestion()
	{
		logger.info(AbstractLogMessage.selectEntity(question));
		reloadQuestion();
		clear(false,false,false,false,true,true);
	}
	
	private void reloadQuestion()
	{
		question = fSurvey.find(cQuestion,question);
		options = question.getOptions();
	}
	
	public void saveQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(question));
		if(question.getUnit()!=null){question.setUnit(fSurvey.find(cUnit,question.getUnit()));}
		question = fSurvey.save(question);
		loadSection();
	}
	
	public void rmQuestion() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(question));}
		fSurvey.rm(question);
		clear(false,false,false,true,true,true);
		loadSection();
	}
	
	public void addOption()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(cOption));}
		option = efOption.build(question,"");
		option.setName(efLang.createEmpty(langs));
		option.setDescription(efDescription.createEmpty(langs));
	}
	
	public void selectOption()
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.selectEntity(option));}
	}
	
	public void saveOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.saveEntity(option));}
		option = fSurvey.save(option);
		reloadQuestion();
		bMessage.growlSuccessSaved();
	}
	
	public void rmOption() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(option));}
		fSurvey.rmOption(option);
		clear(false,false,false,false,true,true);
		reloadQuestion();
		bMessage.growlSuccessRemoved();
	}
	
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
	
	protected void reorderSections() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, sections);}
	protected void reorderQuestions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, questions);}
	protected void reorderOptions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, options);}
}