package org.jeesl.api.facade.util;

import java.util.List;

public interface JeeslSqlFacade
{
	List<Object> nativeQuery(String sql);
	List<Object> jpaQuery(String sql);
}