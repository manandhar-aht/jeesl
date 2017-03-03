package org.jeesl.controller.handler.op;

import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.op.OpEntityBean;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class OpEntitySelectionHandler <T extends EjbWithId> extends AbstractOpSelectionHandler<T> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

    public OpEntitySelectionHandler(OpEntityBean bean) {super(bean);}
    public OpEntitySelectionHandler(OpEntityBean bean, List<T> opEntites) {super(bean,opEntites);}
}