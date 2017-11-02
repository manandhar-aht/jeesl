package org.jeesl.interfaces.model.module.attribute;

import java.util.Date;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeData <CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,
							OPTION extends JeeslAttributeOption<?,?,CRITERIA>,
							CONTAINER extends JeeslAttributeContainer<?,?>>
		extends EjbWithId,EjbSaveable
{
	public static enum Attributes{container,criteria};
	
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);

	
	String getValueString();
	void setValueString(String valueString);
	
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
	
	Integer getValueInteger();
	void setValueInteger(Integer valueInteger);
	
	Double getValueDouble();
	void setValueDouble(Double valueDouble);
	
	Date getValueRecord();
	void setValueRecord(Date date);

}