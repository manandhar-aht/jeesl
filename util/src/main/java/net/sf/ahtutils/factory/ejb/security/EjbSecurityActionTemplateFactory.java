package net.sf.ahtutils.factory.ejb.security;

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

public class EjbSecurityActionTemplateFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityActionTemplateFactory.class);
	
    private Class<L> clLang;
    private Class<D> clDescription;
    private Class<C> clCategory;
    private Class<R> clRole;
    private Class<V> clView;
    private Class<U> clUsecase;
    private Class<A> clAction;
    private Class<AT> clActionTemplate;
    private Class<USER> clUser;
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			   AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
    	EjbSecurityActionTemplateFactory<L,D,C,R,V,U,A,AT,USER> factory(final Class<L> clLang,final Class<D> clDescription,Class<AT> clActionTemplate)
    {
        return new EjbSecurityActionTemplateFactory<L,D,C,R,V,U,A,AT,USER>(clLang,clDescription,clActionTemplate);
    }
    
    public EjbSecurityActionTemplateFactory(final Class<L> clLang,final Class<D> clDescription,Class<AT> clActionTemplate)
    {
        this.clLang = clLang;
        this.clDescription = clDescription;
        this.clActionTemplate = clActionTemplate;
    } 
    
    public AT build(C category, String code)
    {
    	AT ejb = null;
    	
    	try
    	{
			ejb = clActionTemplate.newInstance();
			ejb.setCategory(category);
			ejb.setCode(code);
			ejb.setPosition(1);
			ejb.setVisible(true);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}