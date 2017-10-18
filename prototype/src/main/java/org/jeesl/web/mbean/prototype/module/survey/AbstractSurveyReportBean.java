package org.jeesl.web.mbean.prototype.module.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslSurveyBean;
import org.jeesl.api.facade.module.JeeslSurveyAnalysisFacade;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.json.system.io.report.JsonFlatFigureFactory;
import org.jeesl.factory.json.system.io.report.JsonFlatFiguresFactory;
import org.jeesl.factory.mc.survey.McOptionDataSetFactory;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.factory.txt.JeeslReportAggregationLevelFactory;
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
import org.jeesl.model.json.JsonFlatFigures;
import org.metachart.xml.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractSurveyReportBean <L extends UtilsLang, D extends UtilsDescription, LOC extends UtilsStatus<LOC,L,D>,
						SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						SS extends UtilsStatus<SS,L,D>,
						SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
						TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,ANALYSIS>,
						VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX>,
						TS extends UtilsStatus<TS,L,D>,
						TC extends UtilsStatus<TC,L,D>,
						SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,AQ>,
						QE extends UtilsStatus<QE,L,D>,
						SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE>,
						UNIT extends UtilsStatus<UNIT,L,D>,
						ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,VERSION,SECTION,QUESTION,ANSWER,MATRIX,DATA,OPTIONS,OPTION>,
						OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION>,
						DOMAIN extends JeeslSurveyDomain<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
						PATH extends JeeslSurveyDomainPath<L,D,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?,?,?,DENTITY,?,?,?>,
						ANALYSIS extends JeeslSurveyAnalysis<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
						AQ extends JeeslSurveyAnalysisQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
						AT extends JeeslSurveyAnalysisTool<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>,
						ATT extends UtilsStatus<ATT,L,D>>
					extends AbstractSurveyBean<L,D,LOC,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSurveyReportBean.class);

	private final Class<ANALYSIS> cAnalysis;
	private final Class<AT> cAt; 
	
	private McOptionDataSetFactory<OPTION> mfOption;
	
	private final Map<QUESTION,DataSet> mapDsOption; public Map<QUESTION, DataSet> getMapDsOption() {return mapDsOption;}
	private final Map<QUESTION,List<AT>> mapTool; public Map<QUESTION,List<AT>> getMapTool() {return mapTool;}
	private final Map<SECTION,List<QUESTION>> mapQuestion; public Map<SECTION,List<QUESTION>> getMapQuestion() {return mapQuestion;}
	
	private final Map<AT,JsonFlatFigures> mapToolTableOption; public Map<AT,JsonFlatFigures> getMapToolTableOption() {return mapToolTableOption;}
	private final Map<AT,JsonFlatFigures> mapToolTableBoolean; public Map<AT,JsonFlatFigures> getMapToolTableBoolean() {return mapToolTableBoolean;}
	private final Map<AT,JsonFlatFigures> mapToolTableText; public Map<AT,JsonFlatFigures> getMapToolTableText() {return mapToolTableText;}
	private final Map<AT,JsonFlatFigures> mapToolTableRemark; public Map<AT,JsonFlatFigures> getMapToolTableRemark() {return mapToolTableRemark;}

	
	protected final SbSingleHandler<ANALYSIS> sbhAnalysis; public SbSingleHandler<ANALYSIS> getSbhAnalysis() {return sbhAnalysis;}
	
	private DataSet ds; public DataSet getDs() {return ds;}

	public AbstractSurveyReportBean(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<SURVEY> cSurvey, final Class<SS> cSs, final Class<SCHEME> cScheme, final Class<TEMPLATE> cTemplate, final Class<VERSION> cVersion, final Class<TS> cTs, final Class<TC> cTc, final Class<SECTION> cSection, final Class<QUESTION> cQuestion, final Class<SCORE> cScore, final Class<UNIT> cUnit, final Class<ANSWER> cAnswer, final Class<MATRIX> cMatrix, final Class<DATA> cData, final Class<OPTIONS> cOptions,final Class<OPTION> cOption, final Class<ANALYSIS> cAnalysis, final Class<AT> cAt, final Class<ATT> cAtt)
	{
		super(cL,cD,cLoc,cSurvey,cSs,cScheme,cTemplate,cVersion,cTs,cTc,cSection,cQuestion,cScore,cUnit,cAnswer,cMatrix,cData,cOptions,cOption,cAtt);
		this.cAnalysis=cAnalysis;
		this.cAt=cAt;
		
		mapQuestion = new HashMap<SECTION,List<QUESTION>>();
		mapTool = new HashMap<QUESTION,List<AT>>();
		mapDsOption = new HashMap<QUESTION,DataSet>();
		
		mapToolTableOption = new HashMap<AT,JsonFlatFigures>();
		mapToolTableBoolean = new HashMap<AT,JsonFlatFigures>();
		mapToolTableText = new HashMap<AT,JsonFlatFigures>();
		mapToolTableRemark = new HashMap<AT,JsonFlatFigures>();
		
		sbhAnalysis = new SbSingleHandler<ANALYSIS>(cAnalysis,this);
		sections = new ArrayList<SECTION>();
	}
	
	protected void initSuperSchedule(String userLocale, String[] localeCodes, FacesMessageBean bMessage, JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fSurvey, JeeslSurveyAnalysisFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> fAnalysis, final JeeslSurveyBean<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,OPTION,CORRELATION,DOMAIN,PATH,DENTITY,ANALYSIS,AQ,AT,ATT> bSurvey, JeeslReportAggregationLevelFactory tfName)
	{
		super.initSuperSurvey(localeCodes,bMessage,fSurvey,fAnalysis,bSurvey);
		mfOption = new McOptionDataSetFactory<OPTION>(tfName);
		initSettings();
		
		sbhCategory.silentCallback();
	}
	
	@Override public void selectSbSingle(EjbWithId ejb)
	{
		logger.info("sb" + (ejb!=null));
		if(ejb!=null)
		{
			if(cTc.isAssignableFrom(ejb.getClass()))
			{
				List<TC> categories = new ArrayList<TC>();categories.add(sbhCategory.getSelection());
				sbhSurvey.setList(fSurvey.fSurveysForCategories(categories));
				
				for(SURVEY s : sbhSurvey.getList())
				{
					logger.warn(s.toString()+" "+s.getTemplate().getCategory());
				}
				
				logger.info(AbstractLogMessage.reloaded(cSurvey, sbhSurvey.getList())+" for category="+sbhCategory.getSelection().getCode());
				sbhSurvey.silentCallback();
			}
			else if(cSurvey.isAssignableFrom(ejb.getClass()))
			{
				sbhAnalysis.setList(fSurvey.allForParent(cAnalysis, sbhSurvey.getSelection().getTemplate()));
				logger.info(AbstractLogMessage.reloaded(cAnalysis, sbhAnalysis.getList()));
				sbhAnalysis.silentCallback();
			}
			else if(cAnalysis.isAssignableFrom(ejb.getClass()))
			{
				reloadAnalysis();
			}
		}
	}
	
	private void reloadAnalysis()
	{
		logger.info("Reload Analysis");
		
		reloadSurvey();
	}
	
	private void reloadSurvey()
	{
		mapDsOption.clear();
		sections.clear();
		mapQuestion.clear();
		mapTool.clear();
		
		mapToolTableOption.clear();
		mapToolTableBoolean.clear();
		mapToolTableText.clear();
		
		for(SECTION section : bSurvey.getMapSection().get(sbhSurvey.getSelection().getTemplate()))
		{
			mapQuestion.put(section,new ArrayList<QUESTION>());
			for(QUESTION q : bSurvey.getMapQuestion().get(section))
			{
				try
				{
					AQ analysis = fAnalysis.fAnalysis(sbhAnalysis.getSelection(), q);
					List<AT> tools = new ArrayList<AT>();
					mapQuestion.get(section).add(q);
					for(AT tool : fSurvey.allForParent(cAt, analysis))
					{
						if(tool.isVisible())
						{
							if(tool.getElement().getCode().equals(JeeslSurveyAnalysisTool.Elements.selectOne.toString()))
							{
								JsonFlatFigures f = fSurvey.surveyStatisticOption(q, sbhSurvey.getSelection());
								mapToolTableOption.put(tool,f);
								DataSet ds2 = mfOption.build(f,bSurvey.getMapOption().get(q));
								mapDsOption.put(q,ds2);
//								logger.trace("DS for "+q.getSection().getCode()+"."+q.getCode()+" "+JaxbUtil.toString(ds2));
								this.ds=ds2;
							}
							if(tool.getElement().getCode().equals(JeeslSurveyAnalysisTool.Elements.bool.toString()))
							{
								JsonFlatFigures f = fSurvey.surveyStatisticBoolean(q, sbhSurvey.getSelection());
								mapToolTableBoolean.put(tool,f);
							}
							if(tool.getElement().getCode().equals(JeeslSurveyAnalysisTool.Elements.text.toString()))
							{
								JsonFlatFigures f = JsonFlatFiguresFactory.build();
								for(ANSWER a : fSurvey.fAnswers(sbhSurvey.getSelection(),q))
								{
									if(a.getValueText()!=null && a.getValueText().trim().length()>0)
									{
										f.getFigures().add(JsonFlatFigureFactory.build(a.getValueText()));
									}
								}
								mapToolTableBoolean.put(tool,f);
							}
							if(tool.getElement().getCode().equals(JeeslSurveyAnalysisTool.Elements.remark.toString()))
							{
								JsonFlatFigures f = JsonFlatFiguresFactory.build();
								for(ANSWER a : fSurvey.fAnswers(sbhSurvey.getSelection(),q))
								{
									if(a.getRemark()!=null && a.getRemark().trim().length()>0)
									{
										f.getFigures().add(JsonFlatFigureFactory.build(a.getRemark()));
									}
								}
								mapToolTableRemark.put(tool,f);
							}
							
							tools.add(tool);
						}
					}
					
					mapTool.put(q,tools);
					
					
				}
				catch (UtilsNotFoundException e) {}
			}
			if(!mapQuestion.get(section).isEmpty()) {sections.add(section);}
		}
	}
}