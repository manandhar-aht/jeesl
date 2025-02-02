package org.jeesl.util.db;

import java.util.List;

import org.jeesl.api.facade.system.graphic.JeeslGraphicFacade;
import org.jeesl.factory.builder.system.SvgFactoryBuilder;
import org.jeesl.factory.ejb.system.symbol.EjbGraphicFactory;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.graphic.with.EjbWithCodeGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.io.StringUtil;

public class JeeslGraphicDbUpdater <G extends JeeslGraphic<?,?,GT,?,?>, GT extends UtilsStatus<GT,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslGraphicDbUpdater.class);

	private JeeslGraphicFacade<?,?,?,G,GT,?,?> fGraphic;
	private static boolean debugOnInfo = true;
	
	private SvgFactoryBuilder<?,?,G,GT,?,?> fbGraphic;
	private EjbGraphicFactory<?,?,G,GT,?,?> efGraphic;
	
	public JeeslGraphicDbUpdater(SvgFactoryBuilder<?,?,G,GT,?,?> fbGraphic)
	{
		this.fbGraphic=fbGraphic;
		efGraphic = fbGraphic.efGraphic();
	}
	
	public void setFacade(JeeslGraphicFacade<?,?,?,G,GT,?,?> fGraphic){this.fGraphic=fGraphic;}
	
	
	public <W extends EjbWithCodeGraphic<G>> void update(Class<W> cStatus, List<Status> list)
	{
		if(debugOnInfo) {logger.info(StringUtil.stars());}		
		
		
		
		for(Status xml : list)
		{
			try
			{
				W ejb = fGraphic.fByCode(cStatus,xml.getCode());
				if(debugOnInfo) {logger.info(ejb.toString());}
				
				if(xml.isSetGraphic())
				{
					if(xml.getGraphic().isSetType() && xml.getGraphic().getType().isSetCode() && xml.getGraphic().getType().getCode().equals(JeeslGraphicType.Code.svg.toString())
						&& xml.getGraphic().isSetFile() && xml.getGraphic().getFile().isSetData())
					{
						updateSvg(cStatus,ejb,xml);
					}
					else if(xml.getGraphic().isSetType() && xml.getGraphic().getType().isSetCode() && xml.getGraphic().getType().getCode().equals(JeeslGraphicType.Code.symbol.toString()))
					{
						updateSymbol(cStatus,ejb,xml);	
					}
				}
				
			}
			catch (UtilsNotFoundException e) {e.printStackTrace();}
			catch (UtilsConstraintViolationException e) {e.printStackTrace();}
			catch (UtilsLockingException e) {e.printStackTrace();}
		}
	}
	
	public <W extends EjbWithCodeGraphic<G>> void updateSvg(Class<W> cStatus, W ejb, Status xml) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		GT svg = fGraphic.fByCode(fbGraphic.getClassGraphicType(), JeeslGraphicType.Code.svg);
		
		G graphic;
		try
		{
			graphic = fGraphic.fGraphic(cStatus, ejb.getId());
			graphic.setType(svg);
		}
		catch (UtilsNotFoundException e)
		{
			if(debugOnInfo) {logger.info("Creating new "+fbGraphic.getClassGraphic());}
			graphic = fGraphic.save(efGraphic.build(svg));
			ejb.setGraphic(graphic);
			fGraphic.update(ejb);
		}
		graphic.setData(xml.getGraphic().getFile().getData().getValue());
		fGraphic.update(graphic);
	}
	
	public <W extends EjbWithCodeGraphic<G>> void updateSymbol(Class<W> cStatus, W ejb, Status xml) throws UtilsNotFoundException, UtilsConstraintViolationException, UtilsLockingException
	{
		GT symbol = fGraphic.fByCode(fbGraphic.getClassGraphicType(), JeeslGraphicType.Code.symbol);
		
		G graphic;
		try
		{
			graphic = fGraphic.fGraphic(cStatus, ejb.getId());
			graphic.setType(symbol);
		}
		catch (UtilsNotFoundException e)
		{
			if(debugOnInfo) {logger.info("Creating new "+fbGraphic.getClassGraphic());}
			graphic = fGraphic.save(efGraphic.build(symbol));
			ejb.setGraphic(graphic);
			fGraphic.update(ejb);
		}
		logger.warn("NYI: Further Processing of symbol");
		
		fGraphic.update(graphic);
	}
}