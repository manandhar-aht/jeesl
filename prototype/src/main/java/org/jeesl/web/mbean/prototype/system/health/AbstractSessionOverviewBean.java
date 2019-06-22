package org.jeesl.web.mbean.prototype.system.health;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.factory.builder.system.StatusFactoryBuilder;
import org.jeesl.interfaces.bean.system.JeeslSessionRegistryBean;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.metachart.xml.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractSessionOverviewBean <L extends UtilsLang, D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
													USER extends JeeslUser<?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSessionOverviewBean.class);
	
	@SuppressWarnings("unused")
	private JeeslSessionRegistryBean<USER> bSession;
	
	private List<USER> users; public List<USER> getUsers() {return users;}

	private USER user; public USER getUser() {return user;} public void setUser(USER user) {this.user = user;}
	protected Chart chart; public Chart getChart() {return chart;}
	
	public AbstractSessionOverviewBean(StatusFactoryBuilder<L,D,LOC> fbStatus)
	{
		super(fbStatus.getClassL(),fbStatus.getClassD());
		
	}
	
	protected void postConstructOverview(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslSessionRegistryBean<USER> bSession)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		users = bSession.getUsers();
	}
	
	
}