package net.sf.ahtutils.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.factory.ejb.util.EjbPropertyFactory;
import net.sf.ahtutils.factory.xml.status.XmlTypeFactory;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.util.UtilsProperty;
import net.sf.ahtutils.interfaces.rest.util.status.UtilsStatusRestImport;
import net.sf.ahtutils.monitor.DataUpdateTracker;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.ahtutils.xml.utils.Property;
import net.sf.ahtutils.xml.utils.Utils;

public class UtilsRestService <L extends UtilsLang,
							D extends UtilsDescription,
							G extends UtilsGraphic<L,D,G,GT,GS>,
							GT extends UtilsStatus<GT,L,D>,
							GS extends UtilsStatus<GS,L,D>,
							P extends UtilsProperty>
	extends AbstractUtilsRest<L,D>
	implements UtilsStatusRestImport
{
	final static Logger logger = LoggerFactory.getLogger(UtilsRestService.class);
    
    private final Class<G> cGraphic;
    private final Class<GT> cGraphicType;
    private final Class<GS> cGraphicStyle;
    private final Class<P> cProperty;
    
    private UtilsFacade fUtils;

//    private String[] langKeys;
    
//    private EjbLangFactory<L> ejbLangFactory;
//    private EjbDescriptionFactory<D> ejbDescriptionFactory;
    
    public UtilsRestService(UtilsFacade fUtils, String[] langKeys, final Class<L> cLang, final Class<D> cDescription, final Class<G> cGraphic,final Class<GT> cGraphicType, final Class<GS> cGraphicStyle, final Class<P> cProperty)
	{   
    	super(fUtils,langKeys,cLang,cDescription);
        
        this.cGraphic=cGraphic;
        this.cGraphicType=cGraphicType;
        this.cGraphicStyle=cGraphicStyle;
        this.cProperty=cProperty;
        
        this.fUtils=fUtils;
        
//        this.langKeys=langKeys;      
//        ejbLangFactory = EjbLangFactory.createFactory(cLang);
//		ejbDescriptionFactory = EjbDescriptionFactory.createFactory(cDescription);
	}
	
	public static <L extends UtilsLang,
				D extends UtilsDescription,
				G extends UtilsGraphic<L,D,G,GT,GS>,
				GT extends UtilsStatus<GT,L,D>,
				GS extends UtilsStatus<GS,L,D>,
				P extends UtilsProperty> 
		UtilsRestService<L,D,G,GT,GS,P>
		factory(UtilsFacade fUtils,String[] langKeys,final Class<L> cL, final Class<D> cD, final Class<G> cGraphic,final Class<GT> cGraphicType, final Class<GS> cGraphicStyle, final Class<P> cProperty)
	{
		return new UtilsRestService<L,D,G,GT,GS,P>(fUtils,langKeys,cL,cD,cGraphic,cGraphicType,cGraphicStyle,cProperty);
	}
		
	public DataUpdate importProperties(Utils utils)
	{
		EjbPropertyFactory<P> f = EjbPropertyFactory.factory(cProperty);
		
		DataUpdateTracker dut = new DataUpdateTracker(true);
		dut.setType(XmlTypeFactory.build(cProperty.getName(),UtilsProperty.class.getSimpleName()+"-DB Import"));
		
		for(Property property : utils.getProperty())
		{			
			P ejb;			
			try
			{
				fUtils.valueStringForKey(cProperty, property.getKey(),null);
			}
			catch (UtilsNotFoundException e1)
			{
				ejb = f.build(property);
				dut.success();
				try
				{
					ejb = (P)fUtils.persist(ejb);
				}
				catch (UtilsConstraintViolationException e) {dut.fail(e, true);}
			}
		}

		return dut.toDataUpdate();
	}

	@Override public DataUpdate importUtilsSymbolGraphicTypes(Aht types) {return super.importStatus(cGraphicType, null, types);}
	@Override public DataUpdate importUtilsSymbolGraphicStyle(Aht styles) {return super.importStatus(cGraphicStyle, null, styles);}
}