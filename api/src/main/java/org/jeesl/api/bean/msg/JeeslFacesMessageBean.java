package org.jeesl.api.bean.msg;

import java.io.Serializable;

public interface JeeslFacesMessageBean extends Serializable
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