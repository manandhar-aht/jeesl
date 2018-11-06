package org.jeesl.interfaces.model.survey;

public interface JeeslSimpleSurveyQuestion
{
	public Integer getPosition();
	public void setPosition(Integer position);
	
	public Boolean getVisible();
	public void setVisible(Boolean visible);
	
	public String getCode();
	public void setCode(String code);
	
	String getTopic();
	void setTopic(String topic);
	
	String getQuestion();
	void setQuestion(String question);
	
	public String getRemark();
	public void setRemark(String remark);
	
	
	Boolean getShowBoolean();
	void setShowBoolean(Boolean showBoolean);
	
	Boolean getShowInteger();
	void setShowInteger(Boolean showInteger);
	
	Boolean getShowDouble();
	void setShowDouble(Boolean showDouble);
	
	Boolean getShowText();
	void setShowText(Boolean showText);
	
	Boolean getShowScore();
	void setShowScore(Boolean showScore);
	
	Boolean getShowRemark();
	void setShowRemark(Boolean showRemark);
	
	Boolean getShowSelectOne();
	void setShowSelectOne(Boolean showSelectOne);
	
	Boolean getShowSelectMulti();
	void setShowSelectMulti(Boolean showSelectMulti);
	
	Boolean getShowMatrix();
	void setShowMatrix(Boolean showMatrix);
}