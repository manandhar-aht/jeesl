package org.jeesl.factory.ejb.system.security;

import java.util.List;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityActionTemplateFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										 A extends JeeslSecurityAction<L,D,R,V,U,AT>,
										 AT extends JeeslSecurityTemplate<L,D,C>,
										 USER extends JeeslUser<R>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityActionTemplateFactory.class);
	
	private final Class<AT> cTemplate;
	
    public EjbSecurityActionTemplateFactory(Class<AT> clActionTemplate)
    {
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