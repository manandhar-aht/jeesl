package org.jeesl.web.mbean.prototype.admin.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.factory.ejb.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateVersionFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.interfaces.facade.JeeslSurveyFacade;
import org.jeesl.interfaces.model.survey.JeeslSurvey;
import org.jeesl.interfaces.model.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplateVersion;
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
										SURVEY extends JeeslSurvey<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										SS extends UtilsStatus<SS,L,D>,
										TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends JeeslSurveySection<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										DATA extends JeeslSurveyData<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>,
										CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSurveyTemplateBean.class);

	protected JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	
	Class<TEMPLATE> cTemplate;
	Class<VERSION> cVersion;
	Class<SECTION> cSection;
	protected Class<QUESTION> cQuestion;
	protected Class<UNIT> cUnit;
	
	protected SurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> ffSurvey;
	protected EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efVersion;
	protected EjbSurveySectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efSection;
	protected EjbSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efQuestion;

	protected List<TC> categories; public List<TC> getCategories(){return categories;}
	protected List<VERSION> versions; public List<VERSION> getVersions(){return versions;}
	protected List<SECTION> sections; public List<SECTION> getSections(){return sections;}
	protected List<QUESTION> questions; public List<QUESTION> getQuestions(){return questions;}
	
	protected TC category; public TC getCategory() {return category;} public void setCategory(TC category) {this.category = category;}
	protected VERSION version; public VERSION getVersion() {return version;}public void setVersion(VERSION version) {this.version = version;}
	protected TEMPLATE template; public TEMPLATE getTemplate(){return template;} public void setTemplate(TEMPLATE template){this.template = template;}
	protected SECTION section; public SECTION getSection(){return section;} public void setSection(SECTION section){this.section = section;}
	protected QUESTION question; public QUESTION getQuestion(){return question;} public void setQuestion(QUESTION question){this.question = question;}
	
	protected void initSuper(String[] localeCodes, FacesMessageBean bMessage, JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey, final Class<L> cL, final Class<D> cD, final Class<SURVEY> cSurvey, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<UNIT> cUnit, final Class<ANSWER> cAnswer, final Class<DATA> cData)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);
		this.fSurvey = fSurvey;
		
		this.cTemplate = cTemplate;
		this.cVersion = cVersion;
		this.cSection = cSection;
		this.cQuestion = cQuestion;
		this.cUnit = cUnit;
		
		ffSurvey = SurveyFactoryFactory.factory(cSurvey,cTemplate,cVersion,cSection, cQuestion, cAnswer, cData);
		efVersion = ffSurvey.version();
		efSection = ffSurvey.section();
		efQuestion = ffSurvey.question();
		
		categories = new ArrayList<TC>();
		versions = new ArrayList<VERSION>();
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
		template = fSurvey.load(cTemplate,template);
		version = template.getVersion();
		sections = template.getSections();
	}
	
	protected void initTemplate() throws UtilsNotFoundException{}
	
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
	
	//Section
	protected void loadSection()
	{
		section = fSurvey.load(cSection,section);
		questions = section.getQuestions();
	}
	
	public void addSection()
	{
		logger.info(AbstractLogMessage.addEntity(cSection));
		section = efSection.build(template,"",0);
	}
	
	public void selectSection()
	{
		logger.info(AbstractLogMessage.selectEntity(section));
		loadSection();
	}
	
	public void saveSection() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(section));
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
		logger.info(AbstractLogMessage.rmEntity(question));
		fSurvey.rm(question);
		question = null;
		loadSection();
	}
	
	protected void reorderSections() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, sections);}
	protected void reorderQuestions() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSurvey, questions);}
}