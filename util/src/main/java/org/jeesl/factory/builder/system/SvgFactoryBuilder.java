package org.jeesl.factory.builder.system;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFactory;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFigureFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class SvgFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(SvgFactoryBuilder.class);
	
	final Class<G> cG;
	final Class<F> cF;
	final Class<FS> cFs;
	
	public SvgFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<G> cG, final Class<F> cF, final Class<FS> cFs)
	{       
		super(cL,cD);
		this.cG = cG;
		this.cF = cF;
		this.cFs = cFs;
	}
	
	public static <L extends UtilsLang, D extends UtilsDescription,
					G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
					F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
		SvgFactoryBuilder<L,D,G,GT,F,FS> factory(final Class<L> cL, final Class<D> cD, final Class<G> cG, final Class<F> cF, final Class<FS> cFs)
	{
		return new SvgFactoryBuilder<L,D,G,GT,F,FS>(cL,cD,cG,cF,cFs);
	}
	
    public EjbGraphicFactory<L,D,G,GT,F,FS> efGraphic()
    {
    	return new EjbGraphicFactory<L,D,G,GT,F,FS>(cG);
    }
    
    public EjbGraphicFigureFactory<L,D,G,GT,F,FS> efFigure()
    {
    	return new EjbGraphicFigureFactory<L,D,G,GT,F,FS>(cF);
    }
	
	public EjbStatusFactory<FS,L,D> style()
	{
		return EjbStatusFactory.createFactory(cFs,cL,cD);
	}
}