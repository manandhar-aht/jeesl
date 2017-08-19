package org.jeesl.factory.ejb.system.security;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.UtilsUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityActionTemplateFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		extends AbstractEjbSecurityFactory<L,D,C,R,V,U,A,AT,USER>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityActionTemplateFactory.class);
	
    public static <L extends UtilsLang,
	 			   D extends UtilsDescription,
	 			   C extends JeeslSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
	 			   R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
	 			   V extends JeeslSecurityView<L,D,C,R,V,U,A,AT,USER>,
	 			   U extends JeeslSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
	 			   A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
	 			   AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
	 			   USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
    	EjbSecurityActionTemplateFactory<L,D,C,R,V,U,A,AT,USER> factory(final Class<L> clLang,final Class<D> clDescription,Class<AT> clActionTemplate)
    {
        return new EjbSecurityActionTemplateFactory<L,D,C,R,V,U,A,AT,USER>(clLang,clDescription,clActionTemplate);
    }
    
    public EjbSecurityActionTemplateFactory(final Class<L> clLang,final Class<D> clDescription,Class<AT> clActionTemplate)
    {
    	super(clLang,clDescription);
        this.cTemplate = clActionTemplate;
    } 
    
    public AT build(C category, String code, List<AT> list){return build(category,code,list.size()+1);}
    public AT build(C category, String code){return build(category,code,1);}
    private AT build(C category, String code, int position)
    {
    	AT ejb = null;
    	
    	try
    	{
			ejb = cTemplate.newInstance();
			ejb.setCategory(category);
			ejb.setCode(code);
			ejb.setPosition(1);
			ejb.setVisible(false);
			ejb.setDocumentation(false);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}