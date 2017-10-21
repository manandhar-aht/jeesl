package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.survey.SurveyAnalysisFactoryBuilder;
import org.jeesl.factory.builder.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.survey.SurveyTemplateFactoryBuilder;
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
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
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
					extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSurveyBean.class);
	
	protected JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey;
	protected JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fAnalysis;
	protected JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> bSurvey;

	protected final Class<LOC> cLoc;
	protected final Class<SURVEY> cSurvey;
	protected final Class<SS> cSs;
	protected final Class<SCHEME> cScheme;
	protected final Class<TEMPLATE> cTemplate;
	protected final Class<VERSION> cVersion;
	protected final Class<TS> cTs;
	protected final Class<TC> cTc;
	protected final Class<SECTION> cSection;
	protected final Class<QUESTION> cQuestion;
	protected final Class<SCORE> cScore;
	protected final Class<UNIT> cUnit;
	protected final Class<ANSWER> cAnswer;
	protected final Class<MATRIX> cMatrix;
	protected final Class<DATA> cData;
	protected final Class<OPTIONS> cOptions;
	protected final Class<OPTION> cOption;
	
	protected final SurveyTemplateFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT> ffTemplate;
	protected final SurveyCoreFactoryBuilder<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffSurvey;
	protected final SurveyAnalysisFactoryBuilder<L,D,TEMPLATE,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> ffAnalysis;
	
	
	protected final EjbSurveyFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efSurvey;
	protected final EjbSurveyTemplateVersionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efVersion;
	protected final EjbSurveySectionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efSection;
	protected final EjbSurveyQuestionFactory<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION> efQuestion;
	protected final EjbSurveyOptionSetFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efOptionSet;
	protected final EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efOption;
	protected final EjbSurveySchemeFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efScheme;
	protected final EjbSurveyScoreFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> efScore;
	protected final EjbSurveyTemplateFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT> efTemplate;
	
	protected final SbSingleHandler<TC> sbhCategory; public SbSingleHandler<TC> getSbhCategory() {return sbhCategory;}
	protected final SbSingleHandler<SURVEY> sbhSurvey; public SbSingleHandler<SURVEY> getSbhSurvey() {return sbhSurvey;}
	protected final SbSingleHandler<LOC> sbhLocale; public SbSingleHandler<LOC> getSbhLocale() {return sbhLocale;}
	
	protected List<VERSION> versions; public List<VERSION> getVersions(){return versions;}
	protected List<SECTION> sections; public List<SECTION> getSections(){return sections;}
	protected final List<QUESTION> questions; public List<QUESTION> getQuestions(){return questions;}
	
	protected SURVEY survey; public SURVEY getSurvey() {return survey;} public void setSurvey(SURVEY survey) {this.survey = survey;}
	protected TEMPLATE template; public TEMPLATE getTemplate(){return template;} public void setTemplate(TEMPLATE template){this.template = template;}
	
	public AbstractSurveyBean(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<SURVEY> cSurvey, final Class<SS> cSs, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<TS> cTs, final Class<TC> cTc, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<UNIT> cUnit,final Class<ANSWER> cAnswer,final Class<MATRIX> cMatrix,final Class<DATA> cData, final Class<OPTIONS> cOptions, final Class<OPTION> cOption, final Class<ATT> cAtt)
	{
		super(cL,cD);
		this.cLoc = cLoc;
		this.cSurvey = cSurvey;
		this.cSs = cSs;
		this.cScheme = cScheme;
		this.cTemplate = cTemplate;
		this.cVersion = cVersion;
		this.cTs = cTs;
		this.cTc = cTc;
		this.cSection = cSection;
		this.cQuestion = cQuestion;
		this.cScore = cScore;
		this.cUnit = cUnit;
		this.cAnswer = cAnswer;
		this.cMatrix = cMatrix;
		this.cData = cData;
		this.cOptions = cOptions;
		this.cOption = cOption;
		
		ffTemplate = SurveyTemplateFactoryBuilder.factory(cL,cD,cSurvey,cSs,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption);
		ffSurvey = SurveyCoreFactoryBuilder.factory(cL,cD,cSurvey,cSs,cScheme,cTemplate,cVersion,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption);
		ffAnalysis = SurveyAnalysisFactoryBuilder.factory(cL,cD,cAtt);
		
		efSurvey = ffSurvey.survey();
		efVersion = ffSurvey.version();
		efSection = ffSurvey.section();
		efQuestion = ffSurvey.question();
		efScore = ffSurvey.score();
		efOptionSet = ffSurvey.optionSet();
		efOption = ffSurvey.option();
		efScheme = ffSurvey.scheme();
		efTemplate = ffTemplate.template();
		
		sbhCategory = new SbSingleHandler<TC>(cTc,this);
		sbhSurvey = new SbSingleHandler<SURVEY>(cSurvey,this);
		sbhLocale = new SbSingleHandler<LOC>(cLoc,this);
		
		questions = new ArrayList<QUESTION>();
	}
	
	protected abstract void initSettings();
	
	protected void initSuperSurvey(String[] localeCodes, FacesMessageBean bMessage, JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey, JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fAnalysis, final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> bSurvey)
	{
		super.initAdmin(localeCodes,cL,cD,bMessage);

		this.fSurvey = fSurvey;
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
				list.add(fSurvey.fByCode(cLoc, localeCodes[0]));
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