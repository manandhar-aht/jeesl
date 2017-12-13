package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;

public interface JeeslAttributeData <CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,
							OPTION extends JeeslAttributeOption<?,?,CRITERIA>,
							CONTAINER extends JeeslAttributeContainer<?,?>>
		extends Serializable,EjbSaveable,EjbRemoveable
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
	void setValueRecord(Date valueRecord);
	
	OPTION getValueOption();
	void setValueOption(OPTION option);
	
	List<OPTION> getValueOptions();
	void setValueOptions(List<OPTION> valueOptions);
}