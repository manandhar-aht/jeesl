package net.sf.ahtutils.interfaces.controller;

public interface DataUpdateTracker
{		
	void createSuccess(Class<?> c);
	void createFail(Class<?> c, Throwable t);
	
	void updateSuccess(Class<?> c, long id);
	void updateFail(Class<?> c, long id, Throwable t);
}