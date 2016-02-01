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
import net.sf.ahtutils.xml.security.Usecases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUsecasesFactory <L extends UtilsLang,
								D extends UtilsDescription, 
								C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
								R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
								V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
								U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
								A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
								USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlUsecasesFactory.class);
		
	private Usecases q;
	
	public XmlUsecasesFactory(Usecases q)
	{
		this.q=q;
	}
	

	public  Usecases build(List<U> usecases)
	{
		XmlUsecaseFactory<L,D,C,R,V,U,A,USER> f = new XmlUsecaseFactory<L,D,C,R,V,U,A,USER>(q.getUsecase().get(0));
		
		Usecases xml = build();
		for(U usecase : usecases)
		{
			xml.getUsecase().add(f.build(usecase));
		}
		return xml;
	}
	
	public static Usecases build()
	{
		return new Usecases();
	}
}