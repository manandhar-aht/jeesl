package net.sf.ahtutils.prototype.controller.handler.op.user;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.bean.op.OpUserBean;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.controller.handler.op.user.OpUserSelectionHandler;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class OverlayUserSelectionHandler <L extends UtilsLang, D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
	implements OpUserSelectionHandler<L,D,C,R,V,U,A,AT,USER>
{
	public static final long serialVersionUID=1;

    private OpUserBean<L,D,C,R,V,U,A,AT,USER> bean;

    private USER user;
    public USER getUser() {return user;}
    public void setUser(USER user) {this.user = user;}

    private List<USER> fvUser;
    public List<USER> getFvUser() {return fvUser;}
    public void setFvUser(List<USER> fvUser) {this.fvUser = fvUser;}

    public OverlayUserSelectionHandler(OpUserBean<L,D,C,R,V,U,A,AT,USER> bean)
    {
        this.bean=bean;
        uiUsers = new ArrayList<USER>();
    }

    public void selectListener() throws UtilsLockingException, UtilsConstraintViolationException
    {
        bean.selectOpUser(user);
        user = null;
    }

    //UI
    private List<USER> uiUsers;
    public List<USER> getUiUsers() {return uiUsers;}
    public void setUiUsers(List<USER> uiUsers) {this.uiUsers = uiUsers;}

    private USER uiUser;
    public USER getUiUser(){return uiUser;}
    public void setUiUser(USER uiUser){this.uiUser = uiUser;}

    public void clearUi()
    {
        uiUsers.clear();
        uiUser = null;
    }

    public void addUiUser(USER item)
    {
        if(!uiUsers.contains(item)) {uiUsers.add(item);}
        uiUser=null;
    }

    public void removeUiUser()
    {
        if(uiUsers.contains(uiUser)) {uiUsers.remove(uiUser);}
        uiUser=null;
    }

    public void selectUiUser()
    {

    }
}