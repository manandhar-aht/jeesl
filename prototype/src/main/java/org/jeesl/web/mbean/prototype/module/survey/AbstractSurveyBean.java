package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.survey.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyCoreFacade;
import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.module.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.module.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyConditionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionSetFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyQuestionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySchemeFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyScoreFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateVersionFactory;
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
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractSurveyBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
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
						AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>,
						ATT extends UtilsStatus<ATT,L,D>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSurveyBean.class);
	
	protected JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate;
	protected JeeslSurveyCoreFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fCore;
	protected JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fAnalysis;
	
	protected JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey;
	protected Long refId;

	protected final SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate;
	protected final SurveyCoreFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore;
	protected final SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fbAnalysis;
	
	protected final EjbSurveyFactory<L,D,SURVEY,SS,TEMPLATE> efSurvey;
	protected final EjbSurveyTemplateVersionFactory<VERSION> efVersion;
	protected final EjbSurveySectionFactory<L,D,TEMPLATE,SECTION> efSection;
	protected final EjbSurveyQuestionFactory<L,D,SECTION,QUESTION,QE,UNIT,OPTIONS,OPTION> efQuestion;
	protected final EjbSurveyConditionFactory<QUESTION,CONDITION,QE> efCondition;
	protected final EjbSurveyOptionSetFactory<TEMPLATE,OPTIONS> efOptionSet;
	protected final EjbSurveyOptionFactory<QUESTION,OPTION> efOption;
	protected final EjbSurveySchemeFactory<SCHEME,TEMPLATE> efScheme;
	protected final EjbSurveyScoreFactory<QUESTION,SCORE> efScore;
	protected final EjbSurveyTemplateFactory<L,D,TEMPLATE,TS,TC,SECTION,QUESTION> efTemplate;
	
	protected final SbSingleHandler<TC> sbhCategory; public SbSingleHandler<TC> getSbhCategory() {return sbhCategory;}
	protected final SbSingleHandler<SURVEY> sbhSurvey; public SbSingleHandler<SURVEY> getSbhSurvey() {return sbhSurvey;}
	protected final SbSingleHandler<LOC> sbhLocale; public SbSingleHandler<LOC> getSbhLocale() {return sbhLocale;}
	
	protected List<VERSION> versions; public List<VERSION> getVersions(){return versions;}
	protected List<SECTION> sections; public List<SECTION> getSections(){return sections;}
	protected final List<QUESTION> questions; public List<QUESTION> getQuestions(){return questions;}
	
	protected SURVEY survey; public SURVEY getSurvey() {return survey;} public void setSurvey(SURVEY survey) {this.survey = survey;}
	protected TEMPLATE template; public TEMPLATE getTemplate(){return template;} public void setTemplate(TEMPLATE template){this.template = template;}
	
	public AbstractSurveyBean(SurveyTemplateFactoryBuilder<L,D,LOC,SCHEME,VALGORITHM,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,CONDITION,QE,SCORE,UNIT,OPTIONS,OPTION> fbTemplate,
			SurveyCoreFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> fbCore,
			SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,ANSWER,MATRIX,DATA,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fbAnalysis)
	{
		super(fbTemplate.getClassL(),fbTemplate.getClassD());
		this.fbTemplate=fbTemplate;
		this.fbCore=fbCore;
		this.fbAnalysis=fbAnalysis;
		
		efTemplate = fbTemplate.template();
		efSection = fbTemplate.section();
		
		efSurvey = fbCore.survey();
		efVersion = fbTemplate.version();
		efQuestion = fbTemplate.question();
		efCondition = fbTemplate.ejbCondition();
		efScore = fbTemplate.score();
		efOptionSet = fbCore.optionSet();
		efOption = fbCore.option();
		efScheme = fbTemplate.scheme();
		
		
		sbhCategory = new SbSingleHandler<TC>(fbTemplate.getClassTemplateCategory(),this);
		sbhSurvey = new SbSingleHandler<SURVEY>(fbCore.getClassSurvey(),this);
		sbhLocale = new SbSingleHandler<LOC>(fbTemplate.getClassLocale(),this);
		
		questions = new ArrayList<QUESTION>();
	}
	
	protected abstract void initSettings();
	
	protected void initSuperSurvey(List<String> localeCodes, FacesMessageBean bMessage,
			JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION> fTemplate,
			JeeslSurveyCoreFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION> fCore,
			JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,QUERY,PATH,DENTITY,DATTRIBUTE,ANALYSIS,AQ,AT,ATT> fAnalysis,
			final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ATT> bSurvey)
	{
		super.initAdmin(localeCodes.toArray(new String[localeCodes.size()]),cL,cD,bMessage);

		this.fTemplate = fTemplate;
		this.fCore = fCore;
		this.fAnalysis = fAnalysis;
		this.bSurvey = bSurvey;
	}
	
	protected void initLocales(String userLocale)
	{
		if(sbhLocale.getList().isEmpty())
		{
			List<LOC> list = new ArrayList<LOC>();
			try
			{
				list.add(fCore.fByCode(fbTemplate.getClassLocale(), localeCodes[0]));
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
			sbhLocale.setList(list);
		}
		for(LOC loc : sbhLocale.getList())
		{
			if(loc.getCode().equals(userLocale)) {sbhLocale.setSelection(loc);}
		}

		sbhLocale.selectDefault();
	}
}