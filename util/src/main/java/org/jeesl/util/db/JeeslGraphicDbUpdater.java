package org.jeesl.util.db;

import org.jeesl.interfaces.model.system.symbol.JeeslGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

public class JeeslGraphicDbUpdater <G extends JeeslGraphic<?,?,G,?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslGraphicDbUpdater.class);

	private UtilsFacade fGraphic;
	
	public JeeslGraphicDbUpdater()
	{
		
	}
	
	public void setFacade(UtilsFacade fGraphic){this.fGraphic=fGraphic;}
	
	
	
	
}