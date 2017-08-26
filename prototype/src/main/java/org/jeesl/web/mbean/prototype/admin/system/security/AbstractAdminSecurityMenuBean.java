package org.jeesl.web.mbean.prototype.admin.system.security;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.factory.ejb.system.security.EjbSecurityMenuFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public abstract class AbstractAdminSecurityMenuBean <L extends UtilsLang, D extends UtilsDescription,
											C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
											M extends JeeslSecurityMenu<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,USER>
		implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityMenuBean.class);

	private EjbSecurityMenuFactory<L,D,C,R,V,U,A,AT,M,USER> efMenu;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

	public AbstractAdminSecurityMenuBean(final Class<L> cL, final Class<D> cD, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate, final Class<M> cMenu, final Class<USER> cUser)
	{
		super(cL,cD,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser);
		
		efMenu = ffSecurity.ejbMenu(cMenu);
	}
	
	public void initSuper(String[] langs, JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage)
	{
		initSecuritySuper(langs,fSecurity,bMessage);
		
		for(V v : fSecurity.all(cView))
		{
			M m = efMenu.create(v);
			try {
				fSecurity.save(m);
			} catch (UtilsConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UtilsLockingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public void reload()
    {
    	tree = new DefaultTreeNode(null, null);
    	
    	buildTree(tree, null);
    }
	    
	private void buildTree(TreeNode parent, List<M> sections)
	{
		for(M menu : sections)
		{
			TreeNode n = new DefaultTreeNode(menu, parent);
//				if(!s.getSections().isEmpty()) {buildTree(n,s.getSections());}
		}
	}
}