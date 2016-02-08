package net.sf.ahtutils.util.comparator.ejb.security;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
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

public class SecurityActionComparator<L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
									AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityActionComparator.class);

    public enum Type {position};

    public SecurityActionComparator()
    {
    	
    }
    
    public Comparator<A> factory(Type type)
    {
        Comparator<A> c = null;
        SecurityActionComparator<L,D,C,R,V,U,A,AT,USER> factory = new SecurityActionComparator<L,D,C,R,V,U,A,AT,USER>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<A>
    {
        public int compare(A a, A b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getView().getCategory().getPosition(), b.getView().getCategory().getPosition());
			  ctb.append(a.getView().getPosition(), b.getView().getPosition());
			  ctb.append(a.getPosition(), b.getPosition());
			  return ctb.toComparison();
        }
    }
}