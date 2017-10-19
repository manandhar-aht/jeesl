package org.jeesl.interfaces.model.module.survey.core;

import java.util.List;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
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

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithRemark;
import net.sf.ahtutils.interfaces.model.with.utils.UtilsWithCategory;
import net.sf.ahtutils.interfaces.model.with.utils.UtilsWithStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslSurveyTemplate<L extends UtilsLang, D extends UtilsDescription,
										
										SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
										TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,ANALYSIS>,
										VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
										TS extends UtilsStatus<TS,L,D>,
										TC extends UtilsStatus<TC,L,D>,
										SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,?,?>,
										QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QUESTION,QE,SCORE,UNIT,OPTIONS,?,?>,
										QE extends UtilsStatus<QE,L,D>,
										SCORE extends JeeslSurveyScore<L,D,?,?,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE>,
										UNIT extends UtilsStatus<UNIT,L,D>,
										ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,?>,
										MATRIX extends JeeslSurveyMatrix<L,D,?,?,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,?,?>,
										DATA extends JeeslSurveyData<L,D,?,?,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,QE,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTIONS,?,?>,
										OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,?>,
										ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>
										>
			extends EjbWithId,EjbWithRecord,EjbWithName,EjbWithRemark,
						UtilsWithStatus<L,D,TS>,
						UtilsWithCategory<L,D,TC>
{
	enum Attributes {category,version,status}
	
	VERSION getVersion();
	void setVersion(VERSION version);
	
	List<SECTION> getSections();
	void setSections(List<SECTION> sections);
	
	List<SCHEME> getSchemes();
	void setSchemes(List<SCHEME> schemes);
	
	List<OPTIONS> getOptionSets();
	void setOptionSets(List<OPTIONS> optionSets);
	
	TEMPLATE getNested();
	void setNested(TEMPLATE nested);
}