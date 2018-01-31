package net.sf.ahtutils.interfaces.bean;

public interface FacesMessageBean
{
	void growlSuccess(String key);
	void growlError(String key);
	
	<E extends Enum<E>> void errorText(E id, String text);
	void errorText(String text);
	
	void growlSuccessSaved();
	void growlSuccessRemoved();
	
	void errorConstraintViolationDuplicateObject();
	<E extends Enum<E>> void errorConstraintViolationDuplicateObject(E id);
	void errorConstraintViolationInUse();
	void errorConstraintViolationInUse(String id);
}