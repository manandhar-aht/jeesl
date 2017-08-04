package net.sf.ahtutils.model.interfaces.acl;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclView<L extends UtilsLang,
								 D extends UtilsDescription, 
								 CU extends UtilsAclCategoryUsecase<L,D,CU,U>,
								 U extends UtilsAclView<L,D,CU,U>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclViews";
	
	public CU getCategory();
	public void setCategory(CU category);
}