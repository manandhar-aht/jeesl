package org.jeesl.controller.facade.module.survey;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.module.survey.JeeslSurveyTemplateFacade;
import org.jeesl.factory.builder.survey.SurveyCoreFactoryBuilder;
import org.jeesl.factory.builder.survey.SurveyTemplateFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyTemplateFactory;
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFigureFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFiguresFactory;
import org.jeesl.factory.txt.system.status.TxtStatusFactory;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurvey;
import org.jeesl.interfaces.model.module.survey.JeeslWithSurveyType;
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
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyCondition;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.with.status.JeeslWithType;
import org.jeesl.model.json.JsonFlatFigure;
import org.jeesl.model.json.JsonFlatFigures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JeeslSurveyTemplateFacadeBean <L extends UtilsLang, D extends UtilsDescription,
											SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
											TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,?>,
											VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
											TS extends UtilsStatus<TS,L,D>,
											TC extends UtilsStatus<TC,L,D>,
											SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
											QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,?>,
											QE extends UtilsStatus<QE,L,D>,
											SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,
											UNIT extends UtilsStatus<UNIT,L,D>,
											OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
											OPTION extends JeeslSurveyOption<L,D>>
	extends UtilsFacadeBean implements JeeslSurveyTemplateFacade<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,OPTION>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslSurveyTemplateFacadeBean.class);
	
	
	public JeeslSurveyTemplateFacadeBean(EntityManager em)
	{
		super(em);
	}
}