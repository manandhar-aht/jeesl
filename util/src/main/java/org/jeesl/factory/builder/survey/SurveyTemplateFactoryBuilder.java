package org.jeesl.factory.builder.survey;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyConditionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySchemeFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyScoreFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveySectionFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateVersionFactory;
import org.jeesl.factory.txt.module.survey.TxtSurveyQuestionFactory;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SurveyTemplateFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
				SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
				TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
				VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
				TS extends UtilsStatus<TS,L,D>,
				TC extends UtilsStatus<TC,L,D>,
				SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
				CONDITION extends JeeslSurveyCondition<QUESTION,QE,OPTION>,
				QE extends UtilsStatus<QE,L,D>,
				SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
				UNIT extends UtilsStatus<UNIT,L,D>,
				OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
				OPTION extends JeeslSurveyOption<L,D>
				>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(SurveyTemplateFactoryBuilder.class);
	
	private final Class<SCHEME> cScheme;
	private final Class<TEMPLATE> cTemplate; public Class<TEMPLATE> getClassTemplate() {return cTemplate;}
	private final Class<VERSION> cVersion;
	private final Class<SECTION> cSection; public Class<SECTION> getClassSection() {return cSection;}
	private final Class<QUESTION> cQuestion; public Class<QUESTION> getClassQuestion() {return cQuestion;}
	private final Class<CONDITION> cCondition; public Class<CONDITION> getClassCondition() {return cCondition;}
	private final Class<QE> cElement; public Class<QE> getClassElement(){return cElement;}
	private final Class<SCORE> cScore;
	private final Class<UNIT> cUnit; public Class<UNIT> getClassUnit() {return cUnit;}
	private final Class<OPTIONS> cOptions; public Class<OPTIONS> getOptionSetClass() {return cOptions;}
	private final Class<OPTION> cOption; public Class<OPTION> getOptionClass() {return cOption;}

	public SurveyTemplateFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<CONDITION> cCondition, final Class<QE> cElement, final Class<SCORE> cScore, final Class<UNIT> cUnit, final Class<OPTIONS> cOptions, final Class<OPTION> cOption)
	{
		super(cL,cD);
		this.cScheme = cScheme;
		this.cTemplate = cTemplate;
		this.cVersion = cVersion;
		this.cSection = cSection;
		this.cQuestion = cQuestion;
		this.cCondition = cCondition;
		this.cElement = cElement;
		this.cScore = cScore;
		this.cUnit = cUnit;
        this.cOptions = cOptions;
        this.cOption = cOption;
	}
	
	public EjbSurveySchemeFactory<SCHEME,TEMPLATE> scheme()
	{
		return new EjbSurveySchemeFactory<SCHEME,TEMPLATE>(cScheme);
	}
	
	public EjbSurveyScoreFactory<QUESTION,SCORE> score()
	{
		return new EjbSurveyScoreFactory<QUESTION,SCORE>(cScore);
	}
	
	public EjbSurveyTemplateFactory<L,D,TEMPLATE,TS,TC,SECTION,QUESTION> template()
	{
		return new EjbSurveyTemplateFactory<L,D,TEMPLATE,TS,TC,SECTION,QUESTION>(cTemplate);
	}
	
	public EjbSurveyTemplateVersionFactory<VERSION> version()
	{
		return new EjbSurveyTemplateVersionFactory<VERSION>(cVersion);
	}
	
	public EjbSurveySectionFactory<L,D,TEMPLATE,SECTION> section()
	{
		return new EjbSurveySectionFactory<L,D,TEMPLATE,SECTION>(cSection);
	}
	
	public EjbSurveyConditionFactory<QUESTION,CONDITION,QE> ejbCondition()
	{
		return new EjbSurveyConditionFactory<QUESTION,CONDITION,QE>(cCondition);
	}
	
	public TxtSurveyQuestionFactory<L,D,QUESTION,OPTION> txtQuestion()
	{
		return new TxtSurveyQuestionFactory<L,D,QUESTION,OPTION>();
	}
}