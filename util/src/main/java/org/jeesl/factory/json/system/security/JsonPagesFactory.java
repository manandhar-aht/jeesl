package org.jeesl.factory.json.system.security;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.model.json.system.json.JsonSecurityPages;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class JsonPagesFactory<L extends UtilsLang, D extends UtilsDescription,
									C extends JeeslSecurityCategory<L,D>,
									R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
									V extends JeeslSecurityView<L,D,C,R,U,A>,
									U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
									A extends JeeslSecurityAction<L,D,R,V,U,AT>,
									AT extends JeeslSecurityTemplate<L,D,C>,
									M extends JeeslSecurityMenu<V,M>,
									USER extends JeeslUser<R>>
{
	public JsonPagesFactory()
	{
		
	}
	
	public static JsonSecurityPages build() {return new JsonSecurityPages();}
	
	public JsonSecurityPages hierarchy(JeeslSecurityFacade<L,D,C,R,V,U,A,AT,M,USER> fSecurity)
	{
		JsonSecurityPages pages = build();
		
		return pages;
	}
}