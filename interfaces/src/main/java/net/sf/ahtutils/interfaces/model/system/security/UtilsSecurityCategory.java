package net.sf.ahtutils.interfaces.model.system.security;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithTypeCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionType;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionTypeVisible;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface UtilsSecurityCategory<L extends UtilsLang,
									   D extends UtilsDescription,
									   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
			extends EjbWithId,EjbWithTypeCode,
				EjbWithPositionTypeVisible,EjbWithPositionVisible,EjbWithPositionType,
				EjbWithLang<L>,EjbWithDescription<D>,
				EjbSaveable,EjbRemoveable
{
	public static enum Type {role,view,usecase,action}
	
	public Boolean getDocumentation();
	public void setDocumentation(Boolean documentation);
}