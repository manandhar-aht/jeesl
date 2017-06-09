package org.jeesl.factory.xls.system.module;

import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeesl.api.facade.module.JeeslSurveyFacade;
import org.jeesl.factory.ejb.module.survey.EjbSurveyAnswerFactory;
import org.jeesl.factory.xls.system.io.report.XlsSheetFactory;

import java.util.HashMap;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.jeesl.factory.ejb.module.survey.EjbSurveyMatrixFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyOptionFactory;
import org.jeesl.factory.factory.SurveyFactoryFactory;
import org.jeesl.factory.xls.system.io.report.XlsCellFactory;
import org.jeesl.model.pojo.map.Nested2Map;


public class XlsSurveyDataFactory <L extends UtilsLang, D extends UtilsDescription,
							SURVEY extends JeeslSurvey<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							SS extends UtilsStatus<SS,L,D>,
							SCHEME extends JeeslSurveyScheme<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							TEMPLATE extends JeeslSurveyTemplate<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							VERSION extends JeeslSurveyTemplateVersion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							TS extends UtilsStatus<TS,L,D>,
							TC extends UtilsStatus<TC,L,D>,
							SECTION extends JeeslSurveySection<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							QUESTION extends JeeslSurveyQuestion<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							SCORE extends JeeslSurveyScore<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							ANSWER extends JeeslSurveyAnswer<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							MATRIX extends JeeslSurveyMatrix<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							DATA extends JeeslSurveyData<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							OPTION extends JeeslSurveyOption<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>,
							CORRELATION extends JeeslSurveyCorrelation<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION>>
{
	final static Logger logger = LoggerFactory.getLogger(XlsSurveyDataFactory.class);
		
	private JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey;
	private final String localeCode;
	private final EjbSurveyOptionFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> efOption;
	
	
	private Map<Long, HeaderData> sectionHeaders;
	private Map<Long, HeaderData> questionHeaders;
	private CellStyle style;
	String answerTypes[] = {"Yes/No","Number","Natural Number","Score","Option","Text","Remark"};
	
	public XlsSurveyDataFactory(String localeCode, SurveyFactoryFactory<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> ffSurvey)
	{
		this.localeCode = localeCode;
		efOption = ffSurvey.option();
	}
	
	public void lazy(JeeslSurveyFacade<L,D,SURVEY,SS,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,QUESTION,SCORE,UNIT,ANSWER,MATRIX,DATA,OPTION,CORRELATION> fSurvey)
	{
		this.fSurvey = fSurvey;
	}
	
	public Workbook build(SURVEY survey, List<DATA> list)
	{
		// Reset the section and question overview
		sectionHeaders  = new HashMap<Long, HeaderData>();
		questionHeaders = new HashMap<Long, HeaderData>();
		
		// Create an Excel workbook to be filled with given data
		Workbook wb = new XSSFWorkbook();
		
		// Create a sheet in the new workbook to write data into
		Sheet sheet = XlsSheetFactory.getSheet(wb, localeCode);
		
		// Create a style for the cells
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
				 
		//Get data for lazy loading
		TEMPLATE template = survey.getTemplate();
		if (fSurvey!=null) {template = fSurvey.load(template);}
		// The survey object holds information about the structure of the survey
		logger.info("Using survey: "+survey.getName());
		
		// Data Rendering Section
		// Individual Answers
		for (DATA surveyData : list)
		{
			if (fSurvey!=null) {surveyData = fSurvey.load(surveyData);}
			Map<QUESTION, ANSWER> infoInventory = EjbSurveyAnswerFactory.toQuestionMap(surveyData.getAnswers());
			for (QUESTION question : infoInventory.keySet())
			{
				ANSWER answer = infoInventory.get(question);
				
				// Add or Update the section info on offset and width in section header map
				// Use the computed question length for question header rendering
				int questionLength = addQuestionToSection(answer);
			}
		}
		Row sectionHeaderRow  = sheet.createRow(0);
		Row questionHeaderRow = sheet.createRow(1);
		
		renderHeaderData(sectionHeaderRow, sectionHeaders, false);
		renderHeaderData(questionHeaderRow, questionHeaders, true);
		return wb;
	}
	
	public void renderHeaderData(Row row, Map<Long, HeaderData> headerData, boolean renderQuestionHeader)
	{
		MutableInt columnNr = new MutableInt(0);
		Row subRow    = null;
		Row subsubRow = null;
		Row answerRow = null;
		Row matrixRow = null;
		
		if (renderQuestionHeader) 
		{
			subRow    = row.getSheet().createRow(row.getRowNum()+1);
			subsubRow = row.getSheet().createRow(row.getRowNum()+2);
			answerRow = row.getSheet().createRow(row.getRowNum()+3);
			matrixRow = row.getSheet().createRow(row.getRowNum()+4);
		}
		for (Long id : headerData.keySet())
		{
			HeaderData header = headerData.get(id);
			XlsCellFactory.build(row, columnNr, style, header.name, header.width);
			
			if (renderQuestionHeader)
			{
				// Render the Option header
				columnNr.subtract(header.width);
				columnNr = buildCells(answerTypes, subRow, columnNr);
				
				// Render the valid options
				ANSWER answer = header.answer;
				if (fSurvey!=null) {answer = fSurvey.load(answer);}
				QUESTION question = answer.getQuestion();
				if (fSurvey!=null) {question = fSurvey.load(question);}
				columnNr = buildValidOptionsCells(answerTypes, subsubRow, columnNr, question);

				// Render the Answers
				columnNr = buildAnswerCells(answerTypes, answerRow, columnNr, answer);
				
				// Render the Matrix
				columnNr = buildMatrixCells(answerTypes, answerRow, columnNr, answer);
				
				// Get to the begin of the next one
				columnNr.add(header.width);
			}
			
		}
		
		// Finally autosize columns
		if (renderQuestionHeader)
		{
			int lastColumn = columnNr.intValue();
			for (int i=0; i<lastColumn; i++)
			{
				row.getSheet().autoSizeColumn(i);
			}
		}
		
		
	}
	
	private MutableInt buildCells(String[] answerTypes, Row row, MutableInt columnNr)
	{
		for (String s : answerTypes)
		{
			XlsCellFactory.build(row, columnNr, style, s, 1);
		}
		columnNr.subtract(answerTypes.length);
		return columnNr;
	}
	
	private MutableInt buildValidOptionsCells(String[] answerTypes, Row row, MutableInt columnNr, QUESTION question)
	{
		for (String s : answerTypes)
		{
			if (s.equals("Yes/No")){						
				if (question.getShowBoolean()!= null && question.getShowBoolean()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Number"))
				{if(question.getShowDouble()!= null && question.getShowDouble()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Natural Number")){
				if (question.getShowInteger()!= null && question.getShowInteger()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Score")){
				if (question.getShowScore()!= null && question.getShowScore()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Multi Option")){
				if (question.getShowSelectMulti()!= null && question.getShowSelectMulti()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Option")){
				if (question.getShowSelectOne()!= null && question.getShowSelectOne()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Text")){
				if (question.getShowText()!= null && question.getShowText()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Remark")){
				if (question.getShowRemark()!= null && question.getShowRemark()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
			if (s.equals("Matrix")){
				if (question.getShowMatrix()!= null && question.getShowMatrix()) {XlsCellFactory.build(row, columnNr, style, "X", 1);} else {columnNr.add(1);}}
	
		}
		columnNr.subtract(answerTypes.length);
		return columnNr;
	}
	
	private MutableInt buildAnswerCells(String[] answerTypes, Row row, MutableInt columnNr, ANSWER answer)
	{
		QUESTION question = answer.getQuestion();
		for (String s : answerTypes)
		{
			if (s.equals("Yes/No")){
				if (question.getShowBoolean()!= null && question.getShowBoolean() && answer.getValueBoolean()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getValueBoolean(), 1);} else {columnNr.add(1);}}
			if (s.equals("Number")){
				if (question.getShowDouble()!= null && question.getShowDouble() && answer.getValueDouble()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getValueDouble(), 1);} else {columnNr.add(1);}}
			if (s.equals("Natural Number")){
				if (question.getShowInteger()!= null && question.getShowInteger() && answer.getValueNumber()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getValueNumber(), 1);} else {columnNr.add(1);}}
			if (s.equals("Score")){
				if (question.getShowScore()!= null && question.getShowScore() && answer.getScore()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getScore(), 1);} else {columnNr.add(1);}}
			if (s.equals("Multi Option")){
				if (question.getShowSelectMulti()!= null && question.getShowSelectMulti() && answer.getOptions()!=null) {XlsCellFactory.build(row, columnNr, style, renderOptionsSingle(answer.getOptions()), 1);} else {columnNr.add(1);}}
			if (s.equals("Option")){
				if (question.getShowSelectOne()!= null && question.getShowSelectOne() && answer.getOption()!=null) {XlsCellFactory.build(row, columnNr, style, renderOptionsSingle(answer.getOption()), 1);} else {columnNr.add(1);}}
			if (s.equals("Text")){
				if (question.getShowText()!= null && question.getShowText() && answer.getValueText()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getValueText(), 1);} else {columnNr.add(1);}}
			if (s.equals("Remark")){
				if (question.getShowRemark()!= null && question.getShowRemark() && answer.getRemark()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getRemark(), 1);} else {columnNr.add(1);}}
			if (s.equals("Matrix")){
				if (question.getShowMatrix()!= null && question.getShowMatrix() && answer.getMatrix()!=null) {XlsCellFactory.build(row, columnNr, style, answer.getMatrix(), 1);} else {columnNr.add(1);}}
		}
		columnNr.subtract(answerTypes.length);
		return columnNr;
	}
	
	
	private MutableInt buildMatrixCells(String[] answerTypes, Row firstRow, MutableInt columnNr, ANSWER answer)
	{
		//logger.info("Starting MATRIX --------- ");
		final int offset = columnNr.intValue();
		QUESTION question = answer.getQuestion();
		if (fSurvey!=null) {answer = fSurvey.load(answer);}
		if (fSurvey!=null) {question = fSurvey.load(question);}
		if (question.getShowMatrix()!=null && question.getShowMatrix() && answer.getMatrix() != null && !answer.getMatrix().isEmpty())
		{
			EjbSurveyMatrixFactory eSMF = new EjbSurveyMatrixFactory(answer.getMatrix().get(0).getClass());
			Nested2Map<OPTION,OPTION,MATRIX> map = eSMF.build(answer.getMatrix());
			// Build a Map to get cell data based on the Row and Column
			
			// Get a sorted collection of rows and columns (OPTION objects to request MATRIX CELL)
			TreeMap<Integer, OPTION> rowData    = new TreeMap<Integer, OPTION>();
			TreeMap<Integer, OPTION> columnData = new TreeMap<Integer, OPTION>();
			for (OPTION row : efOption.toRows(question.getOptions()))
				{
					rowData.put(row.getPosition(), row);
				}
			for (OPTION column : efOption.toColumns(question.getOptions()))
				{
					columnData.put(column.getPosition(), column);
				}
			
			// Iterate the row and column keys (maybe with gaps like 1, 8, 9, 10) and fill cells
			Row row = firstRow;
			final int startRow = firstRow.getRowNum();
			boolean firstIteration = true;
			for (Integer rowKey : rowData.keySet())
			{
				if (!firstIteration ) {row = row.getSheet().createRow(startRow +rowKey);}
				for (Integer columnKey : columnData.keySet())
				{
					columnNr = new MutableInt(offset + columnKey);
					MATRIX m = map.get(rowData.get(rowKey), columnData.get(columnKey));
					if (m!= null && m.getAnswer()!=null)
					{
						Object cellContent = buildAnswer(m);
						logger.info("Row " +rowKey +" : Column " +columnKey +" will be set to " +cellContent.toString());
						XlsCellFactory.build(row, columnNr, style, cellContent, 1);
					}
				}
				firstIteration = false;
			}
		}
		return new MutableInt(offset);
	}
	
	public Object buildAnswer(MATRIX m)
	{
		Object value = null;
		ANSWER answer = m.getAnswer();
		if (fSurvey!=null) {answer = fSurvey.load(answer);}
		QUESTION question = answer.getQuestion();
		if (fSurvey!=null) {question = fSurvey.load(question);}
		for (String s : answerTypes)
		{
			if (s.equals("Yes/No") && question.getShowBoolean()!= null && question.getShowBoolean() && m.getValueBoolean()!=null) 
			{
				if (value==null)
				{
					value = m.getValueBoolean();
				} else
				{
					value = value.toString();
					value = value + ", " +m.getValueBoolean().toString();
				}
			}
			if (s.equals("Number") && question.getShowDouble()!= null && question.getShowDouble() && m.getValueDouble()!=null)
				if (value==null)
				{
					value = m.getValueDouble();
				} else
				{
					value = value.toString();
					value = value + ", " +m.getValueDouble().toString();
				}
			if (s.equals("Natural Number") && question.getShowInteger()!= null && question.getShowInteger() && m.getValueNumber()!=null)
				if (value==null)
				{
					value = m.getValueNumber();
				} else
				{
					value = value.toString();
					value = value + ", " +m.getValueNumber().toString();
				}
			if (s.equals("Option") && question.getShowSelectOne()!= null && question.getShowSelectOne() && m.getOption()!=null) 
				if (value==null)
				{
					value = renderOptionsSingle(m.getOption());
				} else
				{
					value = value.toString();
					value = value + ", " +renderOptionsSingle(m.getOption());
				}
			if (s.equals("Text") && question.getShowText()!= null && question.getShowText() && m.getValueText()!=null)
				if (value==null)
				{
					value = m.getValueText();
				} else
				{
					value = value.toString();
					value = value + ", " +m.getValueText();
				}
		}
		if (value==null) {value="not set";}
		return value;
	}	
	
	public int addQuestionToSection(ANSWER answer)
	{
		SECTION section   = answer.getQuestion().getSection();
		Long    sectionId = section.getId();
		Long   questionId = answer.getQuestion().getId();
		HeaderData headerInfo;
		HeaderData headerInfoForQuestion = new HeaderData();
		headerInfoForQuestion.name = answer.getQuestion().getQuestion();
		headerInfoForQuestion.answer = answer;
		int questionLength = answerTypes.length;
		
		// See if section is already present in map
		if (sectionHeaders.containsKey(sectionId))
		{
			headerInfo = sectionHeaders.get(sectionId);
		}
		else
		{
			headerInfo = new HeaderData();
			headerInfo.name = section.getName();
		}
		
		if (fSurvey!=null) {answer = fSurvey.load(answer);}
		// See if Matrix options are to be shown and if so, see if it extends standard range of 9 cells
		
		if (answer.getQuestion().getShowMatrix()!=null && answer.getQuestion().getShowMatrix() && answer.getMatrix()!=null)
		{
			if (efOption.toColumns(answer.getQuestion().getOptions()).size() >= answerTypes.length)
			{
				questionLength = efOption.toColumns(answer.getQuestion().getOptions()).size();
			}
		}
		headerInfo.width = headerInfo.width + questionLength;
		headerInfoForQuestion.width = questionLength;
		sectionHeaders.put(sectionId, headerInfo);
		questionHeaders.put(questionId, headerInfoForQuestion);
		return questionLength;
	}
	
	private String renderOptionsSingle(OPTION o)
	{
		return o.getName().get("en").getLang();
	}
	
	private String renderOptionsSingle(List<OPTION> list)
	{
		StringBuilder sb = new StringBuilder();
		for (OPTION o : list)
		{
			sb.append("- ");
			sb.append(renderOptionsSingle(o));
			sb.append("\n");
		}
		String formattedList = sb.toString();
		StringUtils.trim(formattedList);
		return formattedList;
	}
	
	private class HeaderData {
		int offset;
		int width;
		String name;
		ANSWER answer;
	}
}