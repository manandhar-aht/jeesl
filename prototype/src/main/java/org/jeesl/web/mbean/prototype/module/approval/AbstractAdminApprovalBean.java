package org.jeesl.web.mbean.prototype.module.approval;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslApprovalFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.module.ApprovalFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.approval.JeeslApprovalProcess;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractAdminApprovalBean <L extends UtilsLang, D extends UtilsDescription,
											CAT extends UtilsStatus<CAT,L,D>,
											PROCESS extends JeeslApprovalProcess<L,D,CAT>
											>
				extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminApprovalBean.class);

	protected JeeslApprovalFacade<L,D,CAT,PROCESS> fApproval;
	protected final ApprovalFactoryBuilder<L,D,CAT,PROCESS> fbApproval;
	
	private final SbMultiHandler<CAT> sbhCategory; public SbMultiHandler<CAT> getSbhCategory() {return sbhCategory;}
	
	public AbstractAdminApprovalBean(final ApprovalFactoryBuilder<L,D,CAT,PROCESS> fbApproval)
	{
		super(fbApproval.getClassL(),fbApproval.getClassD());
		this.fbApproval=fbApproval;
		sbhCategory = new SbMultiHandler<CAT>(fbApproval.getClassCategory(),this);
	}
	
	protected void postConstructApproval(JeeslTranslationBean bTranslation, JeeslApprovalFacade<L,D,CAT,PROCESS> fApproval, JeeslFacesMessageBean bMessage)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fApproval=fApproval;
		initLists();
	}
	
	
	private void initLists()
	{
		sbhCategory.setList(fApproval.allOrderedPositionVisible(fbApproval.getClassCategory()));
		sbhCategory.selectAll();
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbApproval.getClassCategory(), sbhCategory.getList()));}
	}
	
	@Override
	public void toggled(Class<?> c) throws UtilsLockingException, UtilsConstraintViolationException {
		// TODO Auto-generated method stub
		
	}
}