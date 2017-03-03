package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.jeesl.controller.handler.op.AbstractOpSelectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.op.OpEntityBean;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class OpPositionNameSelectionHandler <T extends EjbWithId> extends AbstractOpSelectionHandler<T> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

	private UtilsFacade facade;
	
    public OpPositionNameSelectionHandler(UtilsFacade facade, OpEntityBean bean){this(facade,bean,new ArrayList<T>());}
    public OpPositionNameSelectionHandler(UtilsFacade facade, OpEntityBean bean, List<T> opEntites)
    {
    	super(bean,opEntites);
    	this.facade=facade;
    	showName=true;
    }
}