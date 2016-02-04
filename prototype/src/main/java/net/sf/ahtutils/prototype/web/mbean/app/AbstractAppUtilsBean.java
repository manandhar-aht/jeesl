package net.sf.ahtutils.prototype.web.mbean.app;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.graphic.UtilsGraphic;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractAppUtilsBean<L extends UtilsLang,
									D extends UtilsDescription,
									G extends UtilsGraphic<L,D,G,GT,GS>,
									GT extends UtilsStatus<GT,L,D>,
									GS extends UtilsStatus<GS,L,D>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAppUtilsBean.class);
	
	protected UtilsFacade fUtils;
	
	protected Class<GT> cGraphicType;
	protected Class<GS> cGraphicStyle;

	protected void initSuper(Class<GT> cGraphicType,Class<GS> cGraphicStyle)
	{
		this.cGraphicType=cGraphicType;
		this.cGraphicStyle=cGraphicStyle;
		
		reloadGraphicTypes();
		reloadGraphicStyles();
	}
	
	private List<GT> graphicTypes;
	public List<GT> getGraphicTypes() {return graphicTypes;}
	public void reloadGraphicTypes(){graphicTypes = fUtils.allOrderedPositionVisible(cGraphicType);}
	
	private List<GS> graphicStyles;
	public List<GS> getGraphicStyles(){return graphicStyles;}
	public void reloadGraphicStyles(){graphicStyles = fUtils.allOrderedPositionVisible(cGraphicStyle);}	
}