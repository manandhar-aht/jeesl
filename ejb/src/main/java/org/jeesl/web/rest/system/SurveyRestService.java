package org.jeesl.web.rest.system;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.factory.json.jeesl.JeeslContainerFactory;
import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.survey.XmlAnswerFactory;
import org.jeesl.factory.xml.survey.XmlSurveyFactory;
import org.jeesl.factory.xml.survey.XmlTemplateFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.survey.JeeslSurvey;
import org.jeesl.interfaces.model.survey.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.survey.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.survey.JeeslSurveyData;
import org.jeesl.interfaces.model.survey.JeeslSurveyOption;
import org.jeesl.interfaces.model.survey.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.survey.JeeslSurveySection;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.survey.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.rest.survey.JeeslSurveyJsonRest;
import org.jeesl.interfaces.rest.survey.JeeslSurveyRestExport;
import org.jeesl.interfaces.rest.survey.JeeslSurveyRestImport;
import org.jeesl.interfaces.rest.survey.JeeslSurveyXmlRest;
import org.jeesl.model.json.system.status.JsonContainer;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.query.json.JsonStatusQueryProvider;
import org.jeesl.util.query.xml.StatusQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.factory.xml.sync.XmlMapperFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.util.query.xml.SurveyQuery;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.survey.Answer;
import net.sf.ahtutils.xml.survey.Correlation;
import net.sf.ahtutils.xml.survey.Data;
import net.sf.ahtutils.xml.survey.Question;
import net.sf.ahtutils.xml.survey.Section;
import net.sf.ahtutils.xml.survey.Survey;
import net.sf.ahtutils.xml.survey.Surveys;
import net.sf.ahtutils.xml.survey.Template;
import net.sf.ahtutils.xml.survey.Templates;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SurveyRestService <L extends UtilsLang,
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
				extends AbstractJeeslRestService<L,D>	
				implements JeeslSurveyRestExport,JeeslSurveyRestImport,JeeslSurveyJsonRest,JeeslSurveyXmlRest
{
	final static Logger logger = LoggerFactory.getLogger(SurveyRestService.class);
	
	private JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey;
	
	private final Class<SURVEY> cSurvey;
	private final Class<SS> cSS;
	private final Class<TEMPLATE> cTEMPLATE;
	private final Class<VERSION> cVersion;
	private final Class<TS> cTS;
	private final Class<TC> cTC;
	private final Class<QUESTION> cQuestion;
	private final Class<UNIT> cUNIT;
	private final Class<ANSWER> cAnswer;
	private final Class<DATA> cData;
	private final Class<OPTION> cOption;
	private final Class<CORRELATION> cCorrelation;
	
	private JeeslContainerFactory jfContainer;
	private XmlContainerFactory xfContainer;
	private XmlStatusFactory xfStatus;
	private XmlTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfTemplate;
	private XmlSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfSurveys;
	private XmlSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfSurvey;
	private XmlAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> xfAnswer;
	
	private EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efTemlate;
	private EjbSurveySectionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efSection;
	private EjbSurveyQuestionFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efQuestion;
	private EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efSurvey;
	private EjbSurveyDataFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efData;
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efAnswer;
	
	private SurveyRestService(JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey,final Class<L> cL,final Class<D> cD,final Class<SURVEY> cSurvey,final Class<SS> cSS,final Class<TEMPLATE> cTEMPLATE, final Class<VERSION> cVersion,final Class<TS> cTS,final Class<TC> cTC,final Class<SECTION> cSection,final Class<QUESTION> cQuestion,final Class<UNIT> cUNIT,final Class<ANSWER> cAnswer,final Class<DATA> cData,final Class<OPTION> cOption,final Class<CORRELATION> cCorrelation)
	{
		super(fSurvey,cL,cD);
		this.fSurvey=fSurvey;
		this.cSurvey=cSurvey;
		this.cSS=cSS;
		this.cTEMPLATE=cTEMPLATE;
		this.cVersion=cVersion;
		this.cTS=cTS;
		this.cTC=cTC;
		this.cQuestion=cQuestion;
		this.cUNIT=cUNIT;
		this.cAnswer=cAnswer;
		this.cData=cData;
		this.cOption=cOption;
		this.cCorrelation=cCorrelation;
	
		jfContainer = new JeeslContainerFactory(JsonStatusQueryProvider.statusExport());
		xfContainer = new XmlContainerFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		xfStatus = new XmlStatusFactory(StatusQuery.get(StatusQuery.Key.StatusExport).getStatus());
		xfTemplate = new XmlTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(SurveyQuery.get(SurveyQuery.Key.exTemplate).getTemplate());
		xfTemplate.lazyLoad(fSurvey,cSection);
		
		xfSurveys = new XmlSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(SurveyQuery.get(SurveyQuery.Key.exSurveys).getSurveys().getSurvey().get(0));
		
		xfSurvey = new XmlSurveyFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(SurveyQuery.get(SurveyQuery.Key.exSurvey).getSurvey());
		xfSurvey.lazyLoad(fSurvey,cSurvey,cSection,cData);
		
		xfAnswer = new XmlAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(SurveyQuery.get(SurveyQuery.Key.surveyAnswers));
		
		SurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> ffSurvey = SurveyFactoryFactory.factory(cSurvey,cTEMPLATE,cVersion,cSection,cQuestion,cAnswer,cData,cOption);
		
		efTemlate = ffSurvey.template();
		efSection = ffSurvey.section();
		efQuestion = ffSurvey.question();
		efSurvey = ffSurvey.survey();
		efData = ffSurvey.data();
		efAnswer = ffSurvey.answer();
	}
	
	public static <L extends UtilsLang,
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
					OT extends UtilsStatus<OT,L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>>
		SurveyRestService<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>
			factory(JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> fSurvey,final Class<L> cL,final Class<D> cD,final Class<SURVEY> cSurvey,final Class<SS> cSS,final Class<TEMPLATE> cTEMPLATE, final Class<VERSION> cVersion, final Class<TS> cTS,final Class<TC> cTC,final Class<SECTION> cSECTION,final Class<QUESTION> cQuestion,final Class<UNIT> cUNIT,final Class<ANSWER> cAnswer,final Class<DATA> cData,final Class<OPTION> cOption,final Class<CORRELATION> cCorrelation)
	{
		return new SurveyRestService<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>(fSurvey,cL,cD,cSurvey,cSS,cTEMPLATE,cVersion,cTS,cTC,cSECTION,cQuestion,cUNIT,cAnswer,cData,cOption,cCorrelation);
	}

	@Override public Aht exportSurveyTemplateCategory()
	{
		Aht aht = new Aht();
		for(TC ejb : fSurvey.allOrderedPosition(cTC)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyTemplateStatus()
	{
		Aht aht = new Aht();
		for(TS ejb : fSurvey.allOrderedPosition(cTS)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyUnits()
	{
		Aht aht = new Aht();
		for(UNIT ejb : fSurvey.allOrderedPosition(cUNIT)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}
	@Override public Container surveyQuestionUnits() {return xfContainer.build(fSurvey.allOrderedPosition(cUNIT));}

	@Override public Aht exportSurveyStatus()
	{
		Aht aht = new Aht();
		for(SS ejb : fSurvey.allOrderedPosition(cSS)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}

	@Override
	public Templates exportSurveyTemplates()
	{
		Templates xml = new Templates();
		for(TEMPLATE template : fSurvey.all(cTEMPLATE))
		{
			xml.getTemplate().add(xfTemplate.build(template));
		}
		return xml;
	}
	
	@Override
	public Surveys exportSurveys()
	{
		Surveys xml = new Surveys();
		for(SURVEY survey : fSurvey.all(cSurvey))
		{
			xml.getSurvey().add(xfSurveys.build(survey));
		}
		return xml;
	}

	@Override
	public Survey exportSurvey(long id)
	{
		try
		{
			SURVEY ejb = fSurvey.find(cSurvey,id);
			return xfSurvey.build(ejb);
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		return new Survey();
	}
	
	@Override
	public Correlation exportSurveyCorrelations()
	{
		logger.warn("This method should be never used here! You have to implement your project-specific method!");
		return null;
	}
	
	//*******************************************************************************************
	
	@Override public DataUpdate importSurveyTemplateCategory(Aht categories){return importStatus(cTC,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyTemplateStatus(Aht categories){return importStatus(cTS,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyUnits(Aht categories){return importStatus(cUNIT,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyStatus(Aht categories){return importStatus(cSS,cL,cD,categories,null);}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Aht container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		AhtStatusDbInit asdi = new AhtStatusDbInit();
        asdi.setStatusEjbFactory(EjbStatusFactory.createFactory(clStatus, clLang, clDescription));
        asdi.setFacade(fSurvey);
        DataUpdate dataUpdate = asdi.iuStatus(container.getStatus(), clStatus, clLang, clParent);
        asdi.deleteUnusedStatus(clStatus, clLang, clDescription);
        return dataUpdate;
    }

	@Override
	public DataUpdate importSurveyTemplates(Templates templates)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cTEMPLATE.getName(),"DB Import"));
		for(Template xTemplate : templates.getTemplate())
		{
			try
			{
				TS status = fSurvey.fByCode(cTS,xTemplate.getStatus().getCode());
				TC category = fSurvey.fByCode(cTC,xTemplate.getCategory().getCode());
				TEMPLATE eTemplate = efTemlate.build(category,status,xTemplate);
				eTemplate = fSurvey.persist(eTemplate);
				dut.getUpdate().getMapper().add(XmlMapperFactory.create(cTEMPLATE, xTemplate.getId(), eTemplate.getId()));
				
				for(Section xSection : xTemplate.getSection())
				{
					SECTION eSection = efSection.build(eTemplate,xSection);
					eSection = fSurvey.persist(eSection);
					for(Question xQuestion : xSection.getQuestion())
					{
						UNIT unit = fSurvey.fByCode(cUNIT,xQuestion.getUnit().getCode());
						QUESTION eQuestion = efQuestion.build(eSection,unit,xQuestion);
						eQuestion = fSurvey.persist(eQuestion);
						dut.getUpdate().getMapper().add(XmlMapperFactory.create(cQuestion, xQuestion.getId(), eQuestion.getId()));
					}
				}
				
				dut.success();
			}
			catch (UtilsNotFoundException e) {dut.fail(e,true);}
			catch (UtilsConstraintViolationException e) {dut.fail(e,true);}
		}
		return dut.toDataUpdate();
	}

	@Override
	public DataUpdate importSurvey(Survey survey)
	{
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cSurvey.getName(),"DB Import"));
		
		try
		{
			SS status = fSurvey.fByCode(cSS,survey.getStatus().getCode());
			TEMPLATE template = fSurvey.find(cTEMPLATE,survey.getTemplate().getId());
			SURVEY eSurvey = efSurvey.build(template,status,survey);
			eSurvey = fSurvey.persist(eSurvey);
			
			for(Data xData : survey.getData())
			{
				CORRELATION eCorrelation = fSurvey.find(cCorrelation,xData.getCorrelation().getId());
				DATA eData = efData.build(eSurvey,eCorrelation);
				eData = fSurvey.saveData(eData);
				logger.trace("EDATA: "+eData.toString());
				
				for(Answer xAnswer : xData.getAnswer())
				{
					QUESTION eQuestion = fSurvey.find(cQuestion,xAnswer.getQuestion().getId());
					ANSWER eAnswer = efAnswer.build(eQuestion,eData,xAnswer);
					eAnswer = fSurvey.persist(eAnswer);
				}
			}
			
			dut.success();
		}
		catch (UtilsNotFoundException e) {dut.fail(e,true);}
		catch (UtilsConstraintViolationException e) {dut.fail(e,true);}
		catch (UtilsLockingException e) {dut.fail(e,true);}
		
		return dut.toDataUpdate();
	}
	
	@Override public DataUpdate importCorrelations(Correlation correlations)
	{
		logger.warn("This method should be never used here! You have to implement your project-specific method!");
		return null;
	}
	
	// Survey
	
//	@Override
	public Survey surveyStructure(long id)
	{
		Survey xml = new Survey();
		try
		{
			TEMPLATE ejb = fSurvey.find(cTEMPLATE,id);
			xml.setTemplate(xfTemplate.build(ejb));
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		return xml;
	}
	
	public Survey surveyAnswers(long id)
	{
		Survey xml = new Survey();
		Data data = new Data();
		try
		{
			SURVEY survey = fSurvey.find(cSurvey,id);
			for(ANSWER answer : fSurvey.fAnswers(survey))
			{
				data.getAnswer().add(xfAnswer.build(answer));
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		xml.getData().add(data);
		return xml;
	}

	@Override
	public JsonContainer surveyQuestionUnitsJson()
	{
		return jfContainer.build(fSurvey.allOrderedPosition(cUNIT));
	}


}