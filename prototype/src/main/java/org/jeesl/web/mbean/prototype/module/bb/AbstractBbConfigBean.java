package org.jeesl.web.mbean.prototype.module.bb;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslBbFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.module.BbFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.bb.JeeslBb;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractBbConfigBean <L extends UtilsLang,D extends UtilsDescription,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									BB extends JeeslBb<L,D,SCOPE,BB,USER>,
									USER extends EjbWithEmail>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractBbConfigBean.class);
	
	protected JeeslBbFacade<L,D,SCOPE,BB,USER> fBb;
	
	private final BbFactoryBuilder<L,D,SCOPE,BB,USER> fbBb;
	
	protected final SbSingleHandler<SCOPE> sbhScope; public SbSingleHandler<SCOPE> getSbhScope() {return sbhScope;}
	
	private List<BB> boards; public List<BB> getBoards() {return boards;} public void setBoards(List<BB> boards) {this.boards = boards;}

	protected long refId;
	private BB board; public BB getBoard() {return board;} public void setBoard(BB board) {this.board = board;}

	public AbstractBbConfigBean(BbFactoryBuilder<L,D,SCOPE,BB,USER> fbBb)
	{
		super(fbBb.getClassL(),fbBb.getClassD());
		this.fbBb=fbBb;
		sbhScope = new SbSingleHandler<SCOPE>(fbBb.getClassScope(),this);
	}

	protected void postConstructBb(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslBbFacade<L,D,SCOPE,BB,USER> fBb)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fBb=fBb;
		pageConfig();
	}
	
	//This method can be overriden
	protected void pageConfig()
	{
		refId = 0;
		sbhScope.setList(fBb.allOrderedPositionVisible(fbBb.getClassScope()));
		sbhScope.setDefault();
	}
	
	@Override public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		reloadBoards();
	}
	
	private void reloadBoards()
	{
		
		
	}
}