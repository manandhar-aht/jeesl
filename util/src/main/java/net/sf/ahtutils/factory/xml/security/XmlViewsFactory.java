package net.sf.ahtutils.factory.xml.security;

import java.util.List;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewsFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewsFactory.class);
		
	private Views q;
	
	public XmlViewsFactory(Views q)
	{
		this.q=q;
	}
	

	public  Views build(List<V> views)
	{
		XmlViewFactory<L,D,C,R,V,U,A,USER> f = new XmlViewFactory<L,D,C,R,V,U,A,USER>(q.getView().get(0));
		
		Views xml = build();
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