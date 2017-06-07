package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeData <L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									ATTRIBUTES extends JeeslAttributes<L,D,CATEGORY,ATTRIBUTES,DATA,CRITERIA,TYPE>,
									DATA extends JeeslAttributeData<L,D,CATEGORY,ATTRIBUTES,DATA,CRITERIA,TYPE>,
									CRITERIA extends JeeslAttributeCriteria<L,D,CATEGORY,ATTRIBUTES,DATA,CRITERIA,TYPE>,
									TYPE extends UtilsStatus<TYPE,L,D>>
		extends EjbWithId
{
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);

	String getValueString();
	void setValueString(String valueString);
	
	Integer getValueInteger();
	void setValueInteger(Integer valueInteger);
	
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
	
	/*
	private String valueString;
	public String getValueString() {return valueString;}
	public void setValueString(String valueString) {this.valueString = valueString;}
	
	private Integer valueInteger;
	public Integer getValueInteger() {return valueInteger;}
	public void setValueInteger(Integer valueInteger) {this.valueInteger = valueInteger;}

	private Boolean valueBoolean;
	public Boolean getValueBoolean() {return valueBoolean;}
	public void setValueBoolean(Boolean valueBoolean) {this.valueBoolean = valueBoolean;}
*/
}