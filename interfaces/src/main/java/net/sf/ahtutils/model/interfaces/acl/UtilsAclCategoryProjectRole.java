package net.sf.ahtutils.model.interfaces.acl;

import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsAclCategoryProjectRole<L extends UtilsLang,
									  D extends UtilsDescription,
									  C extends UtilsAclCategoryProjectRole<L,D,C,R>,
									  R extends UtilsAclRole<L,D,C,R>>
			extends EjbWithCode,EjbWithLang<L>,EjbWithDescription<D>
{
	public List<R> getRoles();
	public void setRoles(List<R> roles);
}