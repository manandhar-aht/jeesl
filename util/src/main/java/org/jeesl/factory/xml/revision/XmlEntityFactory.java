package org.jeesl.factory.xml.revision;

import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.xml.aht.Query;

public class XmlEntityFactory <L extends UtilsLang,D extends UtilsDescription,
								RC extends UtilsStatus<RC,L,D>,
								RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RST extends UtilsStatus<RST,L,D>,
								RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
								RAT extends UtilsStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlEntityFactory.class);
	
	private Entity q;
	
	public XmlEntityFactory(Query q){this(q.getEntity());}
	public XmlEntityFactory(Entity q)
	{
		this.q=q;
	}
	
	public Entity build(RE ejb)
	{
		Entity xml = new Entity();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()){xml.setCode(ejb.getCode());}
				
		if(q.isSetAttribute())
		{
			XmlAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> f = new XmlAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(q.getAttribute().get(0));
			for(RA attribute : ejb.getAttributes())
			{
				xml.getAttribute().add(f.build(attribute));
			}
		}
		
		return xml;
	}
}