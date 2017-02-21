package org.jeesl.interfaces.model.survey;

public interface JeeslSimpleSurveyQuestion
{
	public int getPosition();
	public void setPosition(int position);
	
	public boolean isVisible();
	public void setVisible(boolean visible);
	
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
}