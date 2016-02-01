package net.sf.ahtutils.jsf.handler.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.util.comparator.ejb.security.SecurityActionComparator;

public abstract class AbstractJsfSecurityHandler <L extends UtilsLang,
													D extends UtilsDescription,
													C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
													R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
													V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
													U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
													A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
													USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfSecurityHandler.class);
	public static final long serialVersionUID=1;

	protected UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fSecurity;
	
	
	protected List<A> actions; public List<A> getActions() {return actions;}
	protected List<R> roles; public List<R> getRoles() {return roles;}
	
	protected String pageCode;
	protected V view;public V getView() {return view;}

	protected Comparator<A> comparatorAction;
	
	protected Map<String,Boolean> mapAllow; public Map<String, Boolean> getMapAllow(){return mapAllow;}
	protected Map<R,Boolean> mapHasRole;public Map<R, Boolean> getMapHasRole() {return mapHasRole;}
	protected boolean noActions; public boolean isNoActions() {return noActions;}
	protected boolean noRoles; public boolean isNoRoles() {return noRoles;}
	
	public AbstractJsfSecurityHandler(UtilsSecurityFacade<L,D,C,R,V,U,A,USER> fSecurity,String pageCode, Class<V> cV)
	{
		this.fSecurity=fSecurity;
		this.pageCode=pageCode;
		
		noActions=true;
		noRoles=true;
		
		mapAllow = new Hashtable<String,Boolean>();
		mapHasRole = new Hashtable<R,Boolean>();
		actions = new ArrayList<A>();
		
		SecurityActionComparator<L,D,C,R,V,U,A,USER> cfAction = new SecurityActionComparator<L,D,C,R,V,U,A,USER>();
		cfAction.factory(SecurityActionComparator.Type.position);
		comparatorAction = cfAction.factory(SecurityActionComparator.Type.position);
		
		try
		{
			view = fSecurity.fByCode(cV, pageCode);
			view = fSecurity.load(cV, view);
			Collections.sort(actions, comparatorAction);

			roles = fSecurity.rolesForView(cV, view);
			noRoles = roles.size()==0;
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
}