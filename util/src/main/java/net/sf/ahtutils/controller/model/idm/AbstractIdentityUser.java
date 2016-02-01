package net.sf.ahtutils.controller.model.idm;

import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class AbstractIdentityUser <L extends UtilsLang,
								   D extends UtilsDescription,
								   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								   R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								   V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								   A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								   USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
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
	public void allowAction(A action) {mapActions.put(action.getCode(), true);}
	
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