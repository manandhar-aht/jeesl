package net.sf.ahtutils.interfaces.model.qa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsQaTest<
							GROUP extends UtilsQaGroup<?,?,?>,
							QAC extends UtilsQaCategory<?,?>,
							QAR extends UtilsQaResult<?,?,?>,
							QATD extends UtilsQaTestDiscussion<?,?>,
							QATI extends UtilsQaTestInfo<?>,
							QATS extends UtilsStatus<QATS,?,?>>
			extends Serializable,EjbSaveable,EjbWithNr,EjbWithId,EjbWithName,EjbWithCode
{
	QAC getCategory();
	void setCategory(QAC category);
	
	String getReference();
	void setReference(String reference);

	String getUrl();
	void setUrl(String url);
	
	String getDescription();
	void setDescription(String description);
	
	String getPreCondition();
    void setPreCondition(String preCondition);
    
    String getSteps();
	void setSteps(String steps);
	
	String getExpectedResult();
	void setExpectedResult(String expectedResult);
	
	List<QATD> getDiscussions();
	void setDiscussions(List<QATD> discussions);
	
	List<QAR> getResults();
	void setResults(List<QAR> results);
	
	QATS getClientStatus();
	void setClientStatus(QATS clientStatus);
	
	QATS getDeveloperStatus();
	void setDeveloperStatus(QATS developerStatus);
	
	QATI getInfo();
	void setInfo(QATI info);
	
	Double getWeight();
	void setWeight(Double weight);
	
	Date getRecord();
	void setRecord(Date record);
	
	Integer getDuration();
	void setDuration(Integer duration);
	
	Boolean getVisible();
	void setVisible(Boolean visible);
	
	List<GROUP> getGroups();
	void setGroups(List<GROUP> groups);
}