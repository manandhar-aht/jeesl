package org.jeesl.web.rest.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.api.rest.survey.JeeslSurveyJsonRest;
import org.jeesl.api.rest.survey.JeeslSurveyRestExport;
import org.jeesl.api.rest.survey.JeeslSurveyRestImport;
import org.jeesl.api.rest.survey.JeeslSurveyXmlRest;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDataFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyMatrixFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.factory.json.jeesl.JsonContainerFactory;
import org.jeesl.factory.json.module.survey.JsonSurveyAnswerFactory;
import org.jeesl.factory.json.module.survey.JsonSurveyFactory;
import org.jeesl.factory.json.module.survey.JsonTemplateFactory;
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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidation;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyValidationAlgorithm;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.model.json.system.status.JsonContainer;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.util.db.JeeslStatusDbUpdater;
import org.jeesl.util.query.json.JsonStatusQueryProvider;
import org.jeesl.util.query.json.JsonSurveyQueryProvider;
import org.jeesl.util.query.xml.XmlStatusQuery;
import org.jeesl.util.query.xml.module.XmlSurveyQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class SurveyRestService <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
				SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
				SS extends UtilsStatus<SS,L,D>,
				SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
				VALGORITHM extends JeeslSurveyValidationAlgorithm<L,D>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,
				VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
				TS extends UtilsStatus<TS,L,D>,
				TC extends UtilsStatus<TC,L,D>,
				SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SECTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
				CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
				VALIDATION extends JeeslSurveyValidation<L,D,QUESTION,VALGORITHM>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
				UNIT extends UtilsStatus<UNIT,L,D>,
				ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>,
				MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
				DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
				OPTION extends JeeslSurveyOption<L,D>,
				CORRELATION extends JeeslSurveyCorrelation<DATA>,
				DOMAIN extends JeeslDomain<L,DENTITY>,
				QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
				PATH extends JeeslDomainPath<L,D,QUERY,DENTITY,?>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
				ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN,DENTITY,?>,
				AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
				AT extends JeeslSurveyAnalysisTool<L,D,QE,QUERY,?,AQ,ATT>,
				ATT extends UtilsStatus<ATT,L,D>>
			extends AbstractJeeslRestService<L,D>	
			implements JeeslSurveyRestExport,JeeslSurveyRestImport,JeeslSurveyJsonRest,JeeslSurveyXmlRest
{
	final static Logger logger = LoggerFactory.getLogger(SurveyRestService.class);
	
	private JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate;
	private JeeslSurveyCoreFacade<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey;
	
	private final SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate;
	private final SurveyCoreFactoryBuilder<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore;
	
	private final Class<TC> cTC;
	private final Class<QUESTION> cQuestion;
	private final Class<UNIT> cUNIT;
	private final Class<CORRELATION> cCorrelation;
	
	private JsonContainerFactory jfContainer;
	private JsonSurveyFactory<L,D,SURVEY,SS> jfSurvey;
	
	private XmlContainerFactory xfContainer;
	private XmlStatusFactory xfStatus;
	private final XmlStatusFactory<TC,L,D> xfTemplateCategory;
	private final XmlStatusFactory<TS,L,D> xfTemplateStatus;
	private XmlTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfTemplate;
	private XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfSurveys;
	private XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfSurvey;
	private XmlAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> xfAnswer;
	
	private EjbSurveyTemplateFactory<L,D,TEMPLATE,TS,TC,SECTION,QUESTION> efTemlate;
	private EjbSurveySectionFactory<L,D,TEMPLATE,SECTION> efSection;
	private EjbSurveyQuestionFactory<L,D,SECTION,QUESTION,QE,UNIT,OPTIONS,OPTION> efQuestion;
	private EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE> efSurvey;
	private EjbSurveyDataFactory<SURVEY,DATA,CORRELATION> efData;
	private EjbSurveyAnswerFactory<SECTION,QUESTION,ANSWER,MATRIX,DATA,OPTION> efAnswer;
	private EjbSurveyMatrixFactory<ANSWER,MATRIX,OPTION> efMatrix;
	
	private final JsonSurveyAnswerFactory<L,D,VALGORITHM,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION> jfAnswer;
	
	private SurveyRestService(JeeslSurveyCoreFacade<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey,
			SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
			SurveyCoreFactoryBuilder<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore,
			JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
			final Class<TS> cTS,final Class<TC> cTC,final Class<SECTION> cSection,final Class<QUESTION> cQuestion,final Class<SCORE> cScore,final Class<UNIT> cUNIT, final Class<MATRIX> cMatrix, final Class<OPTIONS> cOptions, final Class<OPTION> cOption,final Class<CORRELATION> cCorrelation, final Class<ATT> cAtt)
	{
		super(fSurvey,fbTemplate.getClassL(),fbTemplate.getClassD());
		this.fTemplate=fTemplate;
		this.fSurvey=fSurvey;
		this.fbCore=fbCore;
		this.fbTemplate=fbTemplate;
		
		this.cTC=cTC;
		this.cQuestion=cQuestion;
		this.cUNIT=cUNIT;
		this.cCorrelation=cCorrelation;
	
		String localeCode = "en";
		jfContainer = new JsonContainerFactory(localeCode,JsonStatusQueryProvider.statusExport());
		jfAnswer = fbCore.jsonAnswer(localeCode,JsonSurveyQueryProvider.answers());
		
		xfContainer = new XmlContainerFactory(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		
		xfTemplateCategory = new XmlStatusFactory<TC,L,D>(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		xfTemplateStatus = new XmlStatusFactory<TS,L,D>(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		xfStatus = new XmlStatusFactory(XmlStatusQuery.get(XmlStatusQuery.Key.StatusExport).getStatus());
		
		xfTemplate = new XmlTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,XmlSurveyQuery.get(XmlSurveyQuery.Key.exTemplate).getTemplate());
		xfTemplate.lazyLoad(fTemplate,fSurvey);
		
		xfSurveys = new XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,XmlSurveyQuery.get(XmlSurveyQuery.Key.exSurveys).getSurveys().getSurvey().get(0));
		
		xfSurvey = new XmlSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,XmlSurveyQuery.get(XmlSurveyQuery.Key.exSurvey).getSurvey());
		xfSurvey.lazyLoad(fTemplate,fSurvey,fbCore.getClassSurvey(),cSection,fbCore.getClassData());
		
		xfAnswer = new XmlAnswerFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(XmlSurveyQuery.get(XmlSurveyQuery.Key.surveyAnswers));
		
		efTemlate = fbTemplate.template();
		efSection = fbTemplate.section();
		efQuestion = fbTemplate.question();
		efSurvey = fbCore.survey();
		efData = fbCore.data();
		efAnswer = fbCore.answer();
		efMatrix = fbCore.ejbMatrix();
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
					SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
					SS extends UtilsStatus<SS,L,D>,
					SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
					VALGORITHM extends JeeslSurveyValidationAlgorithm<L,D>,
					TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,
					VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
					TS extends UtilsStatus<TS,L,D>,
					TC extends UtilsStatus<TC,L,D>,
					SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
					QUESTION extends JeeslSurveyQuestion<L,D,SECTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>,
					CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
					VALIDATION extends JeeslSurveyValidation<L,D,QUESTION,VALGORITHM>,
					QE extends UtilsStatus<QE,L,D>, SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
					UNIT extends UtilsStatus<UNIT,L,D>,
					ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>, MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,
					DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>, OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
					OPTION extends JeeslSurveyOption<L,D>,
					OT extends UtilsStatus<OT,L,D>,
					CORRELATION extends JeeslSurveyCorrelation<DATA>,
					DOMAIN extends JeeslDomain<L,DENTITY>,
					QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
					PATH extends JeeslDomainPath<L,D,QUERY,DENTITY,?>,
					DENTITY extends JeeslRevisionEntity<L,D,?,?,?>,
					ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE,DOMAIN,DENTITY,?>,
					AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>,
					AT extends JeeslSurveyAnalysisTool<L,D,QE,QUERY,?,AQ,ATT>,
					ATT extends UtilsStatus<ATT,L,D>>
		SurveyRestService<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
			factory(JeeslSurveyCoreFacade<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fSurvey,
					SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,OPTIONS,OPTION> ffTemplate,
					SurveyCoreFactoryBuilder<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore,
					JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
					final Class<TS> cTS,final Class<TC> cTC,final Class<SECTION> cSECTION,
					final Class<QUESTION> cQuestion,final Class<SCORE> cScore,
					final Class<UNIT> cUNIT,final Class<ANSWER> cAnswer,
					final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption,final Class<CORRELATION> cCorrelation, final Class<ATT> cAtt)
	{
		return new SurveyRestService<L,D,LOC,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>(fSurvey,ffTemplate,fbCore,fTemplate,cTS,cTC,cSECTION,cQuestion,cScore,cUNIT,cMatrix,cOptions,cOption,cCorrelation,cAtt);
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
		for(TS ejb : fSurvey.allOrderedPosition(fbTemplate.getClassTemplateStatus())){aht.getStatus().add(xfTemplateStatus.build(ejb));}
		return aht;
	}

	@Override public Container surveyQuestionUnits() {return xfContainer.build(fSurvey.allOrderedPosition(cUNIT));}
	
	
	@Override public Aht exportSurveyStatus()
	{
		Aht aht = new Aht();
		for(SS ejb : fSurvey.allOrderedPosition(fbCore.getClassSurveyStatus())){aht.getStatus().add(xfStatus.build(ejb));}
		return aht;
	}

	@Override
	public Templates exportSurveyTemplates()
	{
		Templates xml = new Templates();
		for(TEMPLATE template : fSurvey.all(fbTemplate.getClassTemplate()))
		{
			xml.getTemplate().add(xfTemplate.build(template));
		}
		return xml;
	}
	
	@Override
	public Surveys exportSurveys()
	{
		Surveys xml = new Surveys();
		for(SURVEY survey : fSurvey.all(fbCore.getClassSurvey()))
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
			SURVEY ejb = fSurvey.find(fbCore.getClassSurvey(),id);
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
	@Override public DataUpdate importSurveyTemplateStatus(Aht categories){return importStatus(fbTemplate.getClassTemplateStatus(),cL,cD,categories,null);}
	@Override public DataUpdate importSurveyUnits(Aht categories){return importStatus(cUNIT,cL,cD,categories,null);}
	@Override public DataUpdate importSurveyStatus(Aht categories){return importStatus(fbCore.getClassSurveyStatus(),cL,cD,categories,null);}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <S extends UtilsStatus<S,L,D>, P extends UtilsStatus<P,L,D>> DataUpdate importStatus(Class<S> clStatus, Class<L> clLang, Class<D> clDescription, Aht container, Class<P> clParent)
    {
    	for(Status xml : container.getStatus()){xml.setGroup(clStatus.getSimpleName());}
		JeeslStatusDbUpdater asdi = new JeeslStatusDbUpdater();
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
		dut.setType(XmlTypeFactory.build(fbTemplate.getClassTemplate().getName(),"DB Import"));
		for(Template xTemplate : templates.getTemplate())
		{
			try
			{
				TS status = fSurvey.fByCode(fbTemplate.getClassTemplateStatus(),xTemplate.getStatus().getCode());
				TC category = fSurvey.fByCode(cTC,xTemplate.getCategory().getCode());
				TEMPLATE eTemplate = efTemlate.build(category,status,xTemplate);
				eTemplate = fSurvey.persist(eTemplate);
				dut.getUpdate().getMapper().add(XmlMapperFactory.create(fbTemplate.getClassTemplate(), xTemplate.getId(), eTemplate.getId()));
				
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
		dut.setType(XmlTypeFactory.build(fbCore.getClassSurvey().getName(),"DB Import"));
		
		try
		{
			SS status = fSurvey.fByCode(fbCore.getClassSurveyStatus(),survey.getStatus().getCode());
			TEMPLATE template = fSurvey.find(fbTemplate.getClassTemplate(),survey.getTemplate().getId());
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
			TEMPLATE ejb = fSurvey.find(fbTemplate.getClassTemplate(),id);
			xml.setTemplate(xfTemplate.build(ejb));
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		return xml;
	}
	
	@Override public org.jeesl.model.json.survey.Survey surveyStructureJson(String localeCode, long id)
	{
		JsonSurveyFactory<L,D,SURVEY,SS> jfSurvey = new JsonSurveyFactory<L,D,SURVEY,SS>(localeCode,JsonSurveyQueryProvider.survey());
		JsonTemplateFactory<L,D,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> jfTemplate = new JsonTemplateFactory<L,D,SURVEY,SS,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,VALIDATION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>(localeCode,JsonSurveyQueryProvider.templateExport(),fbTemplate,fTemplate,fSurvey); 
		
		org.jeesl.model.json.survey.Survey jSurvey = JsonSurveyFactory.build();
		try
		{
			SURVEY eSurvey = fSurvey.find(fbCore.getClassSurvey(),id);
			jSurvey = jfSurvey.build(eSurvey);
			TEMPLATE eTemplate = fSurvey.find(fbTemplate.getClassTemplate(),eSurvey.getTemplate());
			
			org.jeesl.model.json.survey.Template jTemplate = jfTemplate.build(eTemplate);
			jTemplate.setCode("primary");
			jSurvey.setTemplate(jTemplate);
			
			jSurvey.setTemplates(new ArrayList<org.jeesl.model.json.survey.Template>());
			jSurvey.getTemplates().add(jTemplate);
			if(eTemplate.getNested()!=null)
			{
				org.jeesl.model.json.survey.Template jNested = jfTemplate.build(eTemplate.getNested());
				jNested.setCode("nested");
				jSurvey.getTemplates().add(jNested);
			}
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
			SURVEY survey = fSurvey.find(fbCore.getClassSurvey(),id);
			for(ANSWER answer : fSurvey.fAnswers(survey))
			{
				data.getAnswer().add(xfAnswer.build(answer));
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		xml.getData().add(data);
		return xml;
	}
	
	public org.jeesl.model.json.survey.Data jsonAnswers(DATA data)
	{
		org.jeesl.model.json.survey.Data result = new org.jeesl.model.json.survey.Data();
		List<ANSWER> answers = fSurvey.fAnswers(Arrays.asList(data));
		Map<ANSWER,List<MATRIX>> map = efMatrix.toMapAnswer(fSurvey.fCells(answers));
		
		for(ANSWER a : answers)
		{
			if(map.containsKey(a)) {a.setMatrix(map.get(a));}
			else {a.setMatrix(new ArrayList<MATRIX>());}
			result.getAnswers().add(jfAnswer.build(a));
		}

		return result;
	}
	
	@Override
	public JsonContainer surveyQuestionUnitsJson()
	{
		return jfContainer.build(fSurvey.allOrderedPosition(cUNIT));
	}
}