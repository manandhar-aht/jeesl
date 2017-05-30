package org.jeesl.factory.ejb.system.symbol;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbGraphicFigureFactory<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbGraphicFigureFactory.class);
	
	private final Class<F> cF;
	
    public EjbGraphicFigureFactory(final Class<F> cF)
    {
        this.cF = cF;
    } 
        
	public F build(FS style, boolean primary, double size, String color, double offsetX, double offsetY, double rotation)
	{
        F ejb = null;
        try
        {
			ejb=cF.newInstance();
			ejb.setStyle(style);
			
			ejb.setPrimary(primary);
			ejb.setSize(size);
			ejb.setColor(color);
			ejb.setOffsetX(offsetX);
			ejb.setOffsetY(offsetY);
			ejb.setRotation(rotation);
		}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        
        return ejb;
    }
}