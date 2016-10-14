package org.jeesl.controller.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.factory.ejb.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyFactoryFactory;
import org.jeesl.factory.ejb.survey.EjbSurveyTemplateFactory;
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

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslSurveyFacadeBean <L extends UtilsLang,
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
	extends UtilsFacadeBean implements JeeslSurveyFacade<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION>
{		
	private EjbSurveyFactoryFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> ffSurvey;
	private EjbSurveyAnswerFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> efAnswer;
	EjbSurveyTemplateFactory<L,D,SURVEY,SS,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,UNIT,ANSWER,DATA,OPTION,CORRELATION> eTemplate;
	
	public JeeslSurveyFacadeBean(EntityManager em, Class<SURVEY> cSurvey, Class<TEMPLATE> cTemplate, Class<VERSION> cVersion, Class<SECTION> cSection, Class<QUESTION> cQuestion, Class<ANSWER> cAnswer,  Class<DATA> cData)
	{
		super(em);
		ffSurvey = EjbSurveyFactoryFactory.factory(cSurvey,cTemplate,cVersion,cSection,cQuestion,cAnswer,cData);
		eTemplate = ffSurvey.template();
		efAnswer = ffSurvey.answer();
	}

	@Override public TEMPLATE load(Class<TEMPLATE> cTemplate, TEMPLATE template)
	{
		template = em.find(cTemplate,template.getId());
		template.getSections().size();
		return template;
	}
	
	@Override public SECTION load(Class<SECTION> cSection, SECTION section)
	{
		section = em.find(cSection,section.getId());
		for(SECTION sub : section.getSections())
		{
			sub.getQuestions().size();
		}
		section.getQuestions().size();
		return section;
	}
	
	@Override public SURVEY load(Class<SURVEY> cSurvey, SURVEY survey)
	{
		survey = em.find(cSurvey,survey.getId());
		survey.getSurveyData().size();
		return survey;
	}

	@Override public DATA load(Class<DATA> cData, DATA data)
	{
		data = em.find(cData,data.getId());
		data.getAnswers().size();
		return data;
	}
	
	@Override
	public TEMPLATE fcSurveyTemplate(Class<TEMPLATE> cTemplate, Class<TS> cTS, TC category)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();

		CriteriaQuery<TEMPLATE> cQ = cB.createQuery(cTemplate);
		Root<TEMPLATE> root = cQ.from(cTemplate);

		Path<TC> pathCategory = root.get("category");

		cQ.where(pathCategory.in(category));
		cQ.select(root).distinct(true);

		List<TEMPLATE> list = em.createQuery(cQ).getResultList();
		if(list.size()==0)
		{
			TEMPLATE template = null;
			try
			{
				TS status = this.fByCode(cTS,JeeslSurveyOption.Status.open.toString());		
				template = eTemplate.build(category,status,"");
				em.persist(template);
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
			return template;
		}
		else
		{
			return list.get(0);
		}
	}

	@Override
	public List<ANSWER> fcAnswers(Class<DATA> cData, Class<ANSWER> cAnswer, Class<TEMPLATE> cTemplate, DATA data)
	{
		data = em.find(cData,data.getId());
		TEMPLATE template = em.find(cTemplate,data.getSurvey().getTemplate().getId());
		List<ANSWER> result = new ArrayList<ANSWER>();

		
		Set<Long> existing = new HashSet<Long>();
		for(ANSWER a : data.getAnswers()){existing.add(a.getQuestion().getId());result.add(a);}
		createAnswers(existing,template.getSections(),data,result);
		
		return result;
	}
	
	private void createAnswers(Set<Long> existing, List<SECTION> sections, DATA data, List<ANSWER> result)
	{
		for(SECTION s : sections)
		{
			for(QUESTION q : s.getQuestions())
			{
				if(!existing.contains(q.getId()))
				{
					try
					{
						ANSWER answer = this.persist(efAnswer.build(q,data));
						result.add(answer);
					}
					catch (UtilsConstraintViolationException e) {e.printStackTrace();}
				}
			}
			createAnswers(existing,s.getSections(),data,result);
		}
	}

	@Override
	public DATA saveData(Class<DATA> cData, Class<CORRELATION> cCorrelation, DATA data) throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(data.getCorrelation().getId()>0)
		{
			data.setCorrelation(em.find(cCorrelation,data.getCorrelation().getId()));
		}
		return this.saveProtected(data);
	}

	@Override public List<ANSWER> fAnswers(Class<ANSWER> cAnswer, Class<DATA> cData, SURVEY survey)
	{
		
		return this.allForGrandParent(cAnswer, cData, "data", survey, "survey");
	}
}