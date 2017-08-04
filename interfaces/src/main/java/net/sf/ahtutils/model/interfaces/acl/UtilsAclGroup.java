package net.sf.ahtutils.model.interfaces.acl;

import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclGroup<L extends UtilsLang,
						 D extends UtilsDescription, 
						 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
						 CR extends UtilsAclCategoryGroup<L,D,CU,CR,U,R>,
						 U extends UtilsAclView<L,D,CU,U>,
						 R extends UtilsAclGroup<L,D,CU,CR,U,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclGroups";
	
	public CR getCategory();
	public void setCategory(CR category);
	
	public List<R> getRoles();
	public void setRoles(List<R> roles);
	
	public List<U> getUsecases() ;
	public void setUsecases(List<U> usecases);
}