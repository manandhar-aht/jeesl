package net.sf.ahtutils.interfaces.bean;

public interface FacesMessageBean
{
	void growlSuccess(String key);
	void growlError(String key);
	
	void errorText(String text);
	
	void growlSuccessSaved();
	void growlSuccessRemoved();
	
	void errorConstraintViolationDuplicateObject();
	void errorConstraintViolationInUse();
}