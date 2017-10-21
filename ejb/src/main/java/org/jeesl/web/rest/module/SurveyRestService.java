package org.jeesl.web.rest.module;

import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.api.rest.survey.JeeslSurveyJsonRest;
import org.jeesl.api.rest.survey.JeeslSurveyRestExport;
import org.jeesl.api.rest.survey.JeeslSurveyRestImport;
import org.jeesl.api.rest.survey.JeeslSurveyXmlRest;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.builder.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.factory.json.jeesl.JsonContainerFactory;
import org.jeesl.factory.json.system.survey.JsonSurveyFactory;
import org.jeesl.factory.json.system.survey.JsonTemplateFactory;
import org.jeesl.factory.xml.jeesl.XmlContainerFactory;
import org.jeesl.factory.xml.module.survey.XmlAnswerFactory;
import org.jeesl.factory.xml.module.survey.XmlSurveyFactory;
import org.jeesl.factory.xml.module.survey.XmlTemplateFactory;
import org.jeesl.factory.xml.system.io.sync.XmlMapperFactory;
import org.jeesl.factory.xml.system.status.XmlStatusFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
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
import org.jeesl.model.json.system.status.JsonContainer;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.query.json.JsonStatusQueryProvider;
import org.jeesl.util.query.json.JsonSurveyQueryProvider;
import org.jeesl.util.query.xml.XmlStatusQuery;
import org.jeesl.util.query.xml.module.SurveyQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
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

public class SurveyRestService <L extends UtilsLang, D extends UtilsDescription,
				SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,
				VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
				TS extends UtilsStatus<TS,L,D>,
				TC extends UtilsStatus<TC,L,D>,
				SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
				UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
				DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
				OPTION extends JeeslSurveyOption<L,D>,
				CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>,
				DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
				AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
			extends AbstractJeeslRestService<L,D>	
			implements JeeslSurveyRestExport,JeeslSurveyRestImport,JeeslSurveyJsonRest,JeeslSurveyXmlRest
{
	final static Logger logger = LoggerFactory.getLogger(SurveyRestService.class);
	
	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey;
	
	private final Class<SURVEY> cSurvey;
	private final Class<SS> cSS;
	private final Class<TEMPLATE> cTemplate;
	private final Class<VERSION> cVersion;
	private final Class<TS> cTS;
	private final Class<TC> cTC;
	private final Class<QUESTION> cQuestion;
	private final Class<UNIT> cUNIT;
	private final Class<ANSWER> cAnswer;
	private final Class<DATA> cData;
	private final Class<CORRELATION> cCorrelation;
	
	private JsonContainerFactory jfContainer;
	private JsonSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> jfSurvey;
	
	private XmlContainerFactory xfContainer;
	private XmlStatusFactory xfStatus;
	private final XmlStatusFactory<TC,L,D> xfTemplateCategory;
	private final XmlStatusFactory<TS,L,D> xfTemplateStatus;
	private XmlTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> xfTemplate;
	private XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> xfSurveys;
	private XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> xfSurvey;
	private XmlAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> xfAnswer;
	
	private EjbSurveyTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT> efTemlate;
	private EjbSurveySectionFactory<L,D,TEMPLATE,SECTION> efSection;
	private EjbSurveyQuestionFactory<L,D,SECTION,QUESTION,QE,UNIT,OPTIONS,OPTION> efQuestion;
	private EjbSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efSurvey;
	private EjbSurveyDataFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efData;
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efAnswer;
	
	private SurveyRestService(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey,final Class<L> cL,final Class<D> cD,final Class<SURVEY> cSurvey,final Class<SS> cSS,final Class<SCHEME> cScheme, final Class<TEMPLATE> cTEMPLATE, final Class<VERSION> cVersion,final Class<TS> cTS,final Class<TC> cTC,final Class<SECTION> cSection,final Class<QUESTION> cQuestion,final Class<SCORE> cScore,final Class<UNIT> cUNIT,final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption,final Class<CORRELATION> cCorrelation, final Class<ATT> cAtt)
	{
		super(fSurvey,cL,cD);
		this.fSurvey=fSurvey;
		this.cSurvey=cSurvey;
		this.cSS=cSS;
		
		this.cTemplate=cTEMPLATE;
		this.cVersion=cVersion;
		this.cTS=cTS;
		this.cTC=cTC;
		this.cQuestion=cQuestion;
		this.cUNIT=cUNIT;
		this.cAnswer=cAnswer;
		this.cData=cData;
		this.cCorrelation=cCorrelation;
	
		String localeCode = "en";
		jfContainer = new JsonContainerFactory(localeCode,JsonStatusQueryProvider.statusExport());
		
		xfContainer = new XmlContainerFactory(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		
		xfTemplateCategory = new XmlStatusFactory<TC,L,D>(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		xfTemplateStatus = new XmlStatusFactory<TS,L,D>(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		xfStatus = new XmlStatusFactory(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		
		xfTemplate = new XmlTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,SurveyQuery.get(SurveyQuery.Key.exTemplate).getTemplate());
		xfTemplate.lazyLoad(fSurvey);
		
		xfSurveys = new XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,SurveyQuery.get(SurveyQuery.Key.exSurveys).getSurveys().getSurvey().get(0));
		
		xfSurvey = new XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,SurveyQuery.get(SurveyQuery.Key.exSurvey).getSurvey());
		xfSurvey.lazyLoad(fSurvey,cSurvey,cSection,cData);
		
		xfAnswer = new XmlAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(SurveyQuery.get(SurveyQuery.Key.surveyAnswers));
		
		SurveyTemplateFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT> ffTemplate = SurveyTemplateFactoryBuilder.factory(cL,cD,cSurvey,cSS,cScheme,cTEMPLATE,cVersion,cSection,cQuestion,cScore,cUNIT,cAnswer,cMatrix,cData,cOptions,cOption);
		SurveyCoreFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffSurvey = SurveyCoreFactoryBuilder.factory(cL,cD,cSurvey,cSS,cScheme,cTEMPLATE,cVersion,cSection,cQuestion,cScore,cUNIT,cAnswer,cMatrix,cData,cOptions,cOption);
		
		efTemlate = ffTemplate.template();
		efSection = ffSurvey.section();
		efQuestion = ffSurvey.question();
		efSurvey = ffSurvey.survey();
		efData = ffSurvey.data();
		efAnswer = ffSurvey.answer();
	}
	
	public static <L extends UtilsLang,
					D extends UtilsDescription,
					SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
					SS extends UtilsStatus<SS,L,D>,
					SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>, TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
					TS extends UtilsStatus<TS,L,D>,
					TC extends UtilsStatus<TC,L,D>,
					SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
					QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>, QE extends UtilsStatus<QE,L,D>, SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>, MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
					DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>, OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					OT extends UtilsStatus<OT,L,D>,
					CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>, DOMAIN extends JeeslSurveyDomain<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>, AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>, AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>, ATT extends UtilsStatus<ATT,L,D>>
		SurveyRestService<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
			factory(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey,final Class<L> cL,final Class<D> cD,final Class<SURVEY> cSurvey,final Class<SS> cSS, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTEMPLATE, final Class<VERSION> cVersion, final Class<TS> cTS,final Class<TC> cTC,final Class<SECTION> cSECTION,final Class<QUESTION> cQuestion,final Class<SCORE> cScore, final Class<UNIT> cUNIT,final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption,final Class<CORRELATION> cCorrelation, final Class<ATT> cAtt)
	{
		return new SurveyRestService<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(fSurvey,cL,cD,cSurvey,cSS,cScheme,cTEMPLATE,cVersion,cTS,cTC,cSECTION,cQuestion,cScore,cUNIT,cAnswer,cMatrix,cData,cOptions,cOption,cCorrelation,cAtt);
	}

	@Override public Aht exportSurveyTemplateCategory()
	{
		Aht aht = new Aht();
		for(TC ejb : fSurvey.allOrderedPosition(cTC)){aht.getStatus().add(xfTemplateCategory.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyTemplateStatus()
	{
		Aht aht = new Aht();
		for(TS ejb : fSurvey.allOrderedPosition(cTS)){aht.getStatus().add(xfTemplateStatus.build(ejb));}
		return aht;
	}

	@Override public Aht exportSurveyUnits()
	{
		Aht aht = new Aht();
		for(UNIT ejb : fSurvey.allOrderedPosition(cUNIT)){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}
//	@Override public Container exportSurveyAnalysisType() {return xfContainer.build(fSurvey.allOrderedPosition(cType));}
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
		for(TEMPLATE template : fSurvey.all(cTemplate))
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
		dut.setType(XmlTypeFactory.build(cTemplate.getName(),"DB Import"));
		for(Template xTemplate : templates.getTemplate())
		{
			try
			{
				TS status = fSurvey.fByCode(cTS,xTemplate.getStatus().getCode());
				TC category = fSurvey.fByCode(cTC,xTemplate.getCategory().getCode());
				TEMPLATE eTemplate = efTemlate.build(category,status,xTemplate);
				eTemplate = fSurvey.persist(eTemplate);
				dut.getUpdate().getMapper().add(XmlMapperFactory.create(cTemplate, xTemplate.getId(), eTemplate.getId()));
				
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
			TEMPLATE template = fSurvey.find(cTemplate,survey.getTemplate().getId());
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
			TEMPLATE ejb = fSurvey.find(cTemplate,id);
			xml.setTemplate(xfTemplate.build(ejb));
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		return xml;
	}
	@Override public org.jeesl.model.json.survey.Survey surveyStructureJson(String localeCode, long id)
	{
		JsonSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> jfSurvey = new JsonSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,JsonSurveyQueryProvider.survey());
		JsonTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> jfTemplate = new JsonTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(localeCode,JsonSurveyQueryProvider.templateExport(),fSurvey); 
		
		org.jeesl.model.json.survey.Survey jSurvey = JsonSurveyFactory.build();
		try
		{
			SURVEY eSurvey = fSurvey.find(cSurvey, id);
			jSurvey = jfSurvey.build(eSurvey);
			TEMPLATE ejb = fSurvey.find(cTemplate,eSurvey.getTemplate());
			jSurvey.setTemplate(jfTemplate.build(ejb));
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		
		return jSurvey;
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