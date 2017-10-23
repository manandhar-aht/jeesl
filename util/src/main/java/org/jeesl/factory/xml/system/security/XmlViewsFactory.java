package org.jeesl.factory.xml.system.security;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.system.security.SecurityViewComparator;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Views;

public class XmlViewsFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends JeeslSecurityCategory<L,D>,
								R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
								V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
								U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
								A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
								AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
								USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewsFactory.class);
		
	private Comparator<V> comparator;
	private Views q;
	
	public XmlViewsFactory(Views q)
	{
		this.q=q;
		comparator = (new SecurityViewComparator<L,D,C,R,V,U,A,AT,USER>()).factory(SecurityViewComparator.Type.position);
	}

	public  Views build(List<V> views)
	{
		XmlViewFactory<L,D,C,R,V,U,A,AT,USER> f = new XmlViewFactory<L,D,C,R,V,U,A,AT,USER>(q.getView().get(0));
		
		Views xml = build();
		Collections.sort(views,comparator);
		for(V view : views)
		{
			xml.getView().add(f.build(view));
		}
		return xml;
	}
	
	public static Views build()
	{
		return new Views();
	}
}