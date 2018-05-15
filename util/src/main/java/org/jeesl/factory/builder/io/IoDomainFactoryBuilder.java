package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainPathFactory;
import org.jeesl.factory.ejb.module.survey.EjbSurveyDomainQueryFactory;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainQuery;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class IoDomainFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
				DOMAIN extends JeeslSurveyDomain<L,DENTITY>,
				QUERY extends JeeslSurveyDomainQuery<L,D,DOMAIN,PATH>,
				PATH extends JeeslSurveyDomainPath<L,D,QUERY,DENTITY,DATTRIBUTE>,
				DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
				DATTRIBUTE extends JeeslRevisionAttribute<L,D,DENTITY,?,?>>
			extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoDomainFactoryBuilder.class);
	
	private final Class<DOMAIN> cDomain; public Class<DOMAIN> getClassDomain() {return cDomain;}
	private final Class<QUERY> cDomainQuery; public Class<QUERY> getClassDomainQuery() {return cDomainQuery;}
	private final Class<PATH> cDomainPath; public Class<PATH> getClassDomainPath() {return cDomainPath;}
	private final Class<DENTITY> cDomainEntity; public Class<DENTITY> getClassDomainEntity() {return cDomainEntity;}
	private final Class<DATTRIBUTE> cDomainAttribute; public Class<DATTRIBUTE> getClassDomainAttribute() {return cDomainAttribute;}
	
	public IoDomainFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<DOMAIN> cDomain, final Class<QUERY> cDomainQuery, final Class<PATH> cDomainPath, final Class<DENTITY> cDomainEntity, final Class<DATTRIBUTE> cDomainAttribute)
	{
		super(cL,cD);
		this.cDomain=cDomain;
		this.cDomainQuery=cDomainQuery;
		this.cDomainPath=cDomainPath;
		this.cDomainEntity=cDomainEntity;
		this.cDomainAttribute=cDomainAttribute;
	}
	
	public EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY> ejbDomain()
	{
		return new EjbSurveyDomainFactory<L,D,DOMAIN,DENTITY>(cDomain);
	}
	
	public EjbSurveyDomainQueryFactory<L,D,DOMAIN,QUERY,PATH> ejbDomainQuery()
	{
		return new EjbSurveyDomainQueryFactory<L,D,DOMAIN,QUERY,PATH>(cDomainQuery);
	}
	
	public EjbSurveyDomainPathFactory<L,D,QUERY,PATH,DENTITY,DATTRIBUTE> ejbDomainPath()
	{
		return new EjbSurveyDomainPathFactory<L,D,QUERY,PATH,DENTITY,DATTRIBUTE>(cDomainPath);
	}
}