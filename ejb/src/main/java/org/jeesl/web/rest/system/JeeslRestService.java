package org.jeesl.web.rest.system;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.api.facade.system.graphic.JeeslGraphicFacade;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.xml.system.revision.XmlEntityFactory;
import org.jeesl.factory.xml.system.symbol.XmlGraphicFactory;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.with.EjbWithGraphic;
import org.jeesl.interfaces.rest.JeeslExportRest;
import org.jeesl.model.xml.system.revision.Entity;
import org.jeesl.util.query.xml.RevisionQuery;
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
								G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
								RC extends UtilsStatus<RC,L,D>,
								RE extends JeeslRevisionEntity<L,D,RC,?,RA>,
								RA extends JeeslRevisionAttribute<L,D,RE,?,RAT>,
								RAT extends UtilsStatus<RAT,L,D>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslExportRest<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslRestService.class);
	
	protected final IoRevisionFactoryBuilder<L,D,RC,?,?,?,?,RE,?,RA,?,RAT> fbRevision;
	
	private final JeeslGraphicFacade<L,D,S,G,GT,F,FS> fGraphic;
	private final JeeslIoRevisionFacade<L,D,RC,?,?,?,?,RE,?,RA,?,RAT> fRevision;
	
	private final XmlGraphicFactory<L,D,G,GT,F,FS> xfGraphic;
	private final XmlEntityFactory<L,D,RC,RE,RA,?> xfEntity;
	
	private JeeslRestService(IoRevisionFactoryBuilder<L,D,RC,?,?,?,?,RE,?,RA,?,RAT> fbRevision,
							JeeslGraphicFacade<L,D,S,G,GT,F,FS> fGraphic,
							JeeslIoRevisionFacade<L,D,RC,?,?,?,?,RE,?,RA,?,RAT> fRevision)
	{
		super(fGraphic,fbRevision.getClassL(),fbRevision.getClassD());
		this.fbRevision=fbRevision;
		this.fRevision=fRevision;
		this.fGraphic=fGraphic;
		xfGraphic = new XmlGraphicFactory<L,D,G,GT,F,FS>(SymbolQuery.get(SymbolQuery.Key.GraphicExport));
		xfEntity = new XmlEntityFactory<L,D,RC,RE,RA,RAT>(RevisionQuery.get(RevisionQuery.Key.exEntity));
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
						S extends EjbWithId,
						G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
						F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
						RC extends UtilsStatus<RC,L,D>,
						RE extends JeeslRevisionEntity<L,D,RC,?,RA>,
						RA extends JeeslRevisionAttribute<L,D,RE,?,RAT>,
						RAT extends UtilsStatus<RAT,L,D>>
	JeeslRestService<L,D,S,G,GT,F,FS,RC,RE,RA,RAT>
		factory(IoRevisionFactoryBuilder<L,D,RC,?,?,?,?,RE,?,RA,?,RAT> fbRevision,
				JeeslGraphicFacade<L,D,S,G,GT,F,FS> fGraphic,
				JeeslIoRevisionFacade<L,D,RC,?,?,?,?,RE,?,RA,?,RAT> fRevision)
	{
		return new JeeslRestService<L,D,S,G,GT,F,FS,RC,RE,RA,RAT>(fbRevision,fGraphic,fRevision);
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

	@Override public Entity exportRevisionEntity(String code) throws UtilsConfigurationException
	{
		try
		{
			RE entity = fGraphic.fByCode(fbRevision.getClassEntity(), code);
			entity = fRevision.load(fbRevision.getClassEntity(), entity);
			return xfEntity.build(entity);
		}
		catch (UtilsNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}