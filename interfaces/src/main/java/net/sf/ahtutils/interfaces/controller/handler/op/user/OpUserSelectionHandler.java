package net.sf.ahtutils.interfaces.controller.handler.op.user;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public interface OpUserSelectionHandler <L extends UtilsLang,
											D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,U,A>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
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