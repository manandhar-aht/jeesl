package org.jeesl.interfaces;

public interface JeeslQuery
{
	public String getLocaleCode();
	
	boolean isDistinct();
	void setDistinct(boolean distinct);

	Integer getFirstResult();
	void setFirstResult(Integer firstResult);

	Integer getMaxResults();
	void setMaxResults(Integer maxResults);
	
	String getSortBy();

	boolean isSortAscending();
	
	void sort(String sortBy, boolean sortAscending);
	void noSort();
	boolean withSort();
	
	void debug(boolean debug);
	void debug(boolean debug,int ident);
}