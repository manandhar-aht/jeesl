package net.sf.ahtutils.model.interfaces.acl;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclRole<L extends UtilsLang,
						 D extends UtilsDescription, 
						 C extends UtilsAclCategoryProjectRole<L,D,C,R>,
						 R extends UtilsAclRole<L,D,C,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public static final String extractId = "aclRoles";
	
	public C getCategory();
	public void setCategory(C category);
}