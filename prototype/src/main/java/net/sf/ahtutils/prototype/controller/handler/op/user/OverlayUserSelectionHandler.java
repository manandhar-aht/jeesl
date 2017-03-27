package net.sf.ahtutils.prototype.controller.handler.op.user;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.bean.op.OpUserBean;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.controller.handler.op.user.OpUserSelectionHandler;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class OverlayUserSelectionHandler <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
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