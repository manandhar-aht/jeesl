package org.jeesl.web.rest.system;

import org.jeesl.api.facade.util.JeeslGraphicFacade;
import org.jeesl.api.rest.JeeslExportRest;
import org.jeesl.factory.xml.system.symbol.XmlGraphicFactory;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.jeesl.util.query.xml.SymbolQuery;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.status.Status;

public class JeeslRestService <L extends UtilsLang,D extends UtilsDescription,
								S extends EjbWithId,
								G extends JeeslGraphic<L,D,G,GT,GS>,
								GT extends UtilsStatus<GT,L,D>,
								GS extends UtilsStatus<GS,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslExportRest<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslRestService.class);
		
	private final JeeslGraphicFacade<L,D,S,G,GT,GS> fGraphic;
	private final XmlGraphicFactory<L,D,G,GT,GS> xfGraphic;
	
	private JeeslRestService(JeeslGraphicFacade<L,D,S,G,GT,GS> fGraphic,final Class<L> cL, final Class<D> cD)
	{
		super(fGraphic,cL,cD);
		this.fGraphic=fGraphic;
		xfGraphic = new XmlGraphicFactory<L,D,G,GT,GS>(SymbolQuery.get(SymbolQuery.Key.GraphicExport));
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
						S extends EjbWithId,
						G extends JeeslGraphic<L,D,G,GT,GS>,
						GT extends UtilsStatus<GT,L,D>,
						GS extends UtilsStatus<GS,L,D>>
	JeeslRestService<L,D,S,G,GT,GS>
		factory(JeeslGraphicFacade<L,D,S,G,GT,GS> fGraphic ,final Class<L> cL, final Class<D> cD)
	{
		return new JeeslRestService<L,D,S,G,GT,GS>(fGraphic,cL,cD);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <X extends UtilsStatus<X,L,D>> org.jeesl.model.xml.jeesl.Container exportStatus(String code) throws UtilsConfigurationException
	{	
		try
		{
			Class<X> x = (Class<X>)Class.forName(code).asSubclass(UtilsStatus.class);
			org.jeesl.model.xml.jeesl.Container xml = xfContainer.build(fGraphic.allOrderedPosition(x));
			
			if(EjbWithGraphic.class.isAssignableFrom(x))
			{
				for(Status xStatus : xml.getStatus())
				{
					X eStatus = fGraphic.fByCode(x,xStatus.getCode());
					try
					{
						G eGraphic = fGraphic.fGraphicForStatus(eStatus.getId());
						xStatus.setGraphic(xfGraphic.build(eGraphic));
					}
					catch (UtilsNotFoundException e) {}
				}
			}
			return xml;
		}
		catch (ClassNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (UtilsNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}