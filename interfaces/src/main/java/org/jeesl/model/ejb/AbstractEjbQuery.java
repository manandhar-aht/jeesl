package org.jeesl.model.ejb;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.jeesl.interfaces.util.query.JeeslQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEjbQuery implements Serializable,JeeslQuery
{
	final static Logger logger = LoggerFactory.getLogger(AbstractEjbQuery.class);
	private static final long serialVersionUID = 1;

	private String localeCode; public String getLocaleCode() {return localeCode;}

	public AbstractEjbQuery() {this("en");}
	public AbstractEjbQuery(String localeCode)
	{
		this.localeCode=localeCode;
		distinct = false;
	}
	
	private boolean distinct;
	public boolean isDistinct() {return distinct;}
	public void setDistinct(boolean distinct) {this.distinct = distinct;}

	private Integer firstResult;
	public Integer getFirstResult() {return firstResult;}
	public void setFirstResult(Integer firstResult) {this.firstResult = firstResult;}

	private Integer maxResults;
	public Integer getMaxResults() {return maxResults;}
	public void setMaxResults(Integer maxResults) {this.maxResults = maxResults;}
	
	//Sorting
	private String sortBy;
	public String getSortBy() {return sortBy;}

	private boolean sortAscending;
	public boolean isSortAscending() {return sortAscending;}
	
	public void sort(String sortBy, boolean sortAscending)
	{
		this.sortBy=sortBy;
		this.sortAscending=sortAscending;
	}
	public void noSort()
	{
		sortBy=null;
	}
	public boolean withSort(){return (sortBy!=null && sortBy.trim().length()>0);}


	public void debug(boolean debug){debug(debug,0);}
	public void debug(boolean debug,int ident)
	{
		if(debug)
		{
			logger.info(StringUtils.repeat("\t",ident)+"distinct: "+distinct);
			if(firstResult!=null){logger.info(StringUtils.repeat("\t",ident)+"firstResult: "+firstResult);}
			if(maxResults!=null){logger.info(StringUtils.repeat("\t",ident)+"maxResults: "+maxResults);}
		}
	}
}
