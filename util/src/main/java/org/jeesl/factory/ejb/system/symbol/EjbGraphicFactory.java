package org.jeesl.factory.ejb.system.symbol;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbGraphicFactory<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGraphicFactory.class);
	
	final Class<G> cGrpahic;
	
    public EjbGraphicFactory(final Class<G> cGrpahic)
    {
        this.cGrpahic = cGrpahic;
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
	
	public G buildSymbol(GT type, FS style)
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