package org.jeesl.factory.xml.system.security;

import java.util.List;

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
import net.sf.ahtutils.xml.security.Actions;

public class XmlActionsFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlActionsFactory.class);
		
	private Actions q;
	
	public XmlActionsFactory(Actions q)
	{
		this.q=q;
	}
	

	public Actions build(List<A> actions)
	{
		XmlActionFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlActionFactory<L,D,C,R,V,U,A,AT,USER>(q.getAction().get(0));
		
		Actions xml = build();
		for(A action : actions)
		{
			xml.getAction().add(f.build(action));
		}
		return xml;
	}
	
	public static Actions build()
	{
		return new Actions();
	}
	
	public static net.sf.ahtutils.xml.access.Actions create()
	{
		return new net.sf.ahtutils.xml.access.Actions();
	}
}