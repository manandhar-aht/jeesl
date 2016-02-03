package net.sf.ahtutils.factory.xml.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.xml.security.Actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlActionsFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlActionsFactory.class);
		
	private Actions q;
	
	public XmlActionsFactory(Actions q)
	{
		this.q=q;
	}
	

	public Actions build(List<A> actions)
	{
		XmlActionFactory<L,D,C,R,V,U,A,USER> f = new XmlActionFactory<L,D,C,R,V,U,A,USER>(q.getAction().get(0));
		
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