package net.sf.ahtutils.interfaces.controller.handler.op.user;

import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface OpUserSelectionHandler <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	public static final long serialVersionUID=1;

    public USER getUser();
    public void setUser(USER user);

    public List<USER> getFvUser();
    public void setFvUser(List<USER> fvUser);

    public List<USER> getUiUsers();
    public void setUiUsers(List<USER> uiUsers);

    public USER getUiUser();
    public void setUiUser(USER uiUser);
    
    public void selectListener() throws UtilsLockingException, UtilsConstraintViolationException;

    public void clearUi();
    public void addUiUser(USER item);
    public void removeUiUser();

    public void selectUiUser();
}