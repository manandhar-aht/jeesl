package org.jeesl.factory.ejb.system.revision;

import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.jeesl.model.xml.system.revision.Attribute;
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbRevisionEntityFactory<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionEntityFactory.class);
	
	final Class<RE> cEntity;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
    
	public EjbRevisionEntityFactory(final Class<L> cL,final Class<D> cD,final Class<RE> cEntity)
	{       
        this.cEntity = cEntity;
		efLang = EjbLangFactory.factory(cL);
		efDescription = EjbDescriptionFactory.factory(cD);
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends JeeslRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
					RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RST extends UtilsStatus<RST,L,D>,
					RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RA extends JeeslRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RAT extends UtilsStatus<RAT,L,D>>
	EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> factory(final Class<L> cL,final Class<D> cD,final Class<RE> cEntity)
	{
		return new EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(cL,cD,cEntity);
	}
	
	public RE build(RC category, Entity xml)
	{
		RE ejb = build(category);
		ejb.setCode(xml.getCode());
		ejb.setPosition(xml.getPosition());
		try
		{
			ejb.setName(efLang.getLangMap(xml.getLangs()));
			ejb.setDescription(efDescription.create(xml.getDescriptions()));
		}
		catch (UtilsConstraintViolationException e) {e.printStackTrace();}
		return ejb;
	}
    
	public RE build(RC category)
	{
		RE ejb = null;
		try
		{
			ejb = cEntity.newInstance();
			ejb.setCategory(category);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void applyValues(RE ejb, Entity xml)
	{
		if(xml.isSetRemark()){ejb.setDeveloperInfo(xml.getRemark().getValue());}
		else{ejb.setDeveloperInfo(null);}
		
		ejb.setPosition(xml.getPosition());
	}
}