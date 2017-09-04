package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class OpEntitySelectionHandler <T extends EjbWithId> extends AbstractOpSelectionHandler<T> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

    public OpEntitySelectionHandler(Class<T> c, OpEntityBean bean) {this(c,bean,new ArrayList<T>());}
    public OpEntitySelectionHandler(Class<T> c, OpEntityBean bean, List<T> opEntites) {super(bean,opEntites);}
}