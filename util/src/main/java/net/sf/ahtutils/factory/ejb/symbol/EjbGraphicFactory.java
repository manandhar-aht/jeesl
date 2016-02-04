package net.sf.ahtutils.factory.ejb.symbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbGraphicFactory<L extends UtilsLang,
								D extends UtilsDescription,
								G extends UtilsGraphic<L,D,G,GT,GS>,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGraphicFactory.class);
	
	final Class<G> cGrpahic;
	
    public EjbGraphicFactory(final Class<G> cGrpahic)
    {
        this.cGrpahic = cGrpahic;
    } 
    
    public static <L extends UtilsLang,
					D extends UtilsDescription,
					G extends UtilsGraphic<L,D,G,GT,GS>,
					GT extends UtilsStatus<GT,L,D>,
					GS extends UtilsStatus<GS,L,D>>
    	EjbGraphicFactory<L,D,G,GT,GS>
    	factory(final Class<G> cGrpahic)
    {
        return new EjbGraphicFactory<L,D,G,GT,GS>(cGrpahic);
    }
    
	public G build(GT type)
	{
        G ejb = null;
        try
        {
			ejb=cGrpahic.newInstance();
			ejb.setType(type);
		}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        
        return ejb;
    }
	
	public G buildSymbol(GT type, GS style)
	{
        G ejb = null;
        try
        {
			ejb=cGrpahic.newInstance();
			ejb.setType(type);
			ejb.setStyle(style);
			ejb.setSize(5);
			ejb.setColor("aaaaaa");
		}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        
        return ejb;
    }

}