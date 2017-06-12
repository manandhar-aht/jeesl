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
	@Override public boolean isDistinct() {return distinct;}
	@Override public void setDistinct(boolean distinct) {this.distinct = distinct;}

	private Integer firstResult;
	@Override public Integer getFirstResult() {return firstResult;}
	@Override public void setFirstResult(Integer firstResult) {this.firstResult = firstResult;}

	private Integer maxResults;
	@Override public Integer getMaxResults() {return maxResults;}
	@Override public void setMaxResults(Integer maxResults) {this.maxResults = maxResults;}
	
	//Sorting
	private String sortBy;
	@Override public String getSortBy() {return sortBy;}

	private boolean sortAscending;
	@Override public boolean isSortAscending() {return sortAscending;}
	
	@Override public void sort(String sortBy, boolean sortAscending)
	{
		this.sortBy=sortBy;
		this.sortAscending=sortAscending;
	}
	@Override public void noSort()
	{
		sortBy=null;
	}
	@Override public boolean withSort(){return (sortBy!=null && sortBy.trim().length()>0);}


	@Override public void debug(boolean debug){debug(debug,0);}
	@Override public void debug(boolean debug,int ident)
	{
		if(debug)
		{
			logger.info(StringUtils.repeat("\t",ident)+"distinct: "+distinct);
			if(firstResult!=null){logger.info(StringUtils.repeat("\t",ident)+"firstResult: "+firstResult);}
			if(maxResults!=null){logger.info(StringUtils.repeat("\t",ident)+"maxResults: "+maxResults);}
		}
	}
}
