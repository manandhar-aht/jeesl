package org.jeesl.interfaces.facade;

import java.util.List;

public interface JeeslSqlFacade
{
	List<Object> nativeQuery(String sql);
	List<Object> jpaQuery(String sql);
}