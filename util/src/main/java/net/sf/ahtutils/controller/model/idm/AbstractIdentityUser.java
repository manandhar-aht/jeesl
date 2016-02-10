package net.sf.ahtutils.controller.model.idm;

import java.util.Hashtable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;

public class AbstractIdentityUser <L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractIdentityUser.class);
	public static final long serialVersionUID=1;
	
	private Map<String,Boolean> mapUsecases,mapRoles,mapActions;
	
	private Map<String,Boolean> mapSystemViews; //Only systems views, domain views not included
	
	private Map<String,Boolean> mapViews;
	
	public AbstractIdentityUser()
	{		
		mapUsecases = new Hashtable<String,Boolean>();
		mapViews = new Hashtable<String,Boolean>();
		mapSystemViews = new Hashtable<String,Boolean>();
		mapRoles = new Hashtable<String,Boolean>();
		mapActions = new Hashtable<String,Boolean>();
	}
	
	public void allowUsecase(U usecase) {mapUsecases.put(usecase.getCode(), true);}
	public void allowView(V view) {mapViews.put(view.getCode(), true);}
	public void allowRole(R role) {mapRoles.put(role.getCode(), true);}
	public void allowAction(A action) {mapActions.put(action.toCode(), true);}
	
	public boolean hasUsecase(String code)
	{
		if(mapUsecases.containsKey(code))
		{
			return mapUsecases.get(code);
		}
		return false;
	}
	
	public boolean hasView(String code)
	{
		if(mapViews.containsKey(code)){return mapViews.get(code);}
		return false;
	}
	
	public boolean hasSystemView(String code)
	{
		if(mapSystemViews.containsKey(code)){return mapSystemViews.get(code);}
		return false;
	}
	
	public boolean hasRole(String code)
	{
		if(mapRoles.containsKey(code))
		{
			return mapRoles.get(code);
		}
		return false;
	}
	
	public boolean hasAction(String code)
	{
		if(mapActions.containsKey(code))
		{
			return mapActions.get(code);
		}
		return false;
	}
	
	public int sizeAllowedUsecases() {return mapUsecases.size();}
	public int sizeAllowedViews() {return mapViews.size();}
	public int sizeAllowedSystemViews() {return mapSystemViews.size();}
	public int sizeAllowedRoles() {return mapRoles.size();}
	public int sizeAllowedActions() {return mapActions.size();}
	
	public Map<String, Boolean> getMapUsecases() {return mapUsecases;}
	
	public Map<String, Boolean> getMapRoles() {return mapRoles;}
	public Map<String, Boolean> getMapActions() {return mapActions;}
	
	public Map<String, Boolean> getMapViews() {return mapViews;}
	public Map<String, Boolean> getMapSystemViews() {return mapSystemViews;}
	public void setMapSystemViews(Map<String, Boolean> mapSystemViews) {this.mapSystemViews = mapSystemViews;}
}