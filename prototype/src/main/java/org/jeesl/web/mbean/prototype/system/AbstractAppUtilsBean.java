package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractAppUtilsBean<L extends UtilsLang, D extends UtilsDescription,
											G extends JeeslGraphic<L,D,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
											F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppUtilsBean.class);
	
	protected UtilsFacade fUtils;
	
	protected Class<GT> cGraphicType;
	protected Class<FS> cGraphicStyle;

	public AbstractAppUtilsBean()
	{
		
	}
	
	protected void initSuper(Class<GT> cGraphicType,Class<FS> cGraphicStyle)
	{
		this.cGraphicType=cGraphicType;
		this.cGraphicStyle=cGraphicStyle;
		
		reloadGraphicTypes();
		reloadGraphicStyles();
	}
	
	private List<GT> graphicTypes;
	public List<GT> getGraphicTypes() {return graphicTypes;}
	public void reloadGraphicTypes(){graphicTypes = fUtils.allOrderedPositionVisible(cGraphicType);}
	
	private List<FS> graphicStyles;
	public List<FS> getGraphicStyles(){return graphicStyles;}
	public void reloadGraphicStyles(){graphicStyles = fUtils.allOrderedPositionVisible(cGraphicStyle);}	
}