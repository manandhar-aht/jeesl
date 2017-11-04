package org.jeesl.factory.ejb.system.revision;

import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.jeesl.model.xml.system.revision.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbRevisionAttributeFactory<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
									RA extends JeeslRevisionAttribute<L,D,RE,RAT>,
									RAT extends UtilsStatus<RAT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionAttributeFactory.class);
	
	final Class<RA> cAttribute;
    
	public EjbRevisionAttributeFactory(final Class<RA> cAttribute)
	{       
        this.cAttribute = cAttribute;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					RC extends UtilsStatus<RC,L,D>,
					RV extends JeeslRevisionView<L,D,RVM>,
					RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
					RS extends JeeslRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RST extends UtilsStatus<RST,L,D>,
					RE extends JeeslRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					REM extends JeeslRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
					RA extends JeeslRevisionAttribute<L,D,RE,RAT>,
					RAT extends UtilsStatus<RAT,L,D>>
	EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> factory(final Class<RA> cAttribute)
	{
		return new EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>(cAttribute);
	}
    
	public RA build(RAT type, Attribute xml)
	{
		RA ejb = build(type);
		ejb.setCode(xml.getCode());
		applyValues(ejb,xml);
		
		return ejb;
	}
	
	public void applyValues(RA ejb, Attribute xml)
	{
		if(xml.isSetRemark()){ejb.setDeveloperInfo(xml.getRemark().getValue());}
		else{ejb.setDeveloperInfo(null);}
		
		ejb.setPosition(xml.getPosition());
		ejb.setXpath(xml.getXpath());
		
		ejb.setShowWeb(xml.isWeb());
		ejb.setShowPrint(xml.isPrint());
		ejb.setShowName(xml.isName());
		ejb.setShowEnclosure(xml.isEnclosure());
		ejb.setUi(xml.isUi());
		ejb.setBean(xml.isBean());
		ejb.setConstruction(xml.isSetConstruction() && xml.isConstruction());
	}
	
	public RA build(RAT type)
	{
		RA ejb = null;
		try
		{
			ejb = cAttribute.newInstance();
			ejb.setPosition(1);
			ejb.setType(type);
			
			ejb.setShowWeb(false);
			ejb.setShowPrint(false);
			ejb.setShowEnclosure(false);
			ejb.setShowName(false);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}