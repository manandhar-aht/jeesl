package net.sf.ahtutils.util.comparator.ejb.security;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.security.UtilsUser;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class SecurityViewComparator<L extends UtilsLang,
									D extends UtilsDescription,
									C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
									R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
									V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
									U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
									A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
									USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(SecurityViewComparator.class);

    public enum Type {position};

    public SecurityViewComparator()
    {
    	
    }
    
    public Comparator<V> factory(Type type)
    {
        Comparator<V> c = null;
        SecurityViewComparator<L,D,C,R,V,U,A,USER> factory = new SecurityViewComparator<L,D,C,R,V,U,A,USER>();
        switch (type)
        {
            case position: c = factory.new PositionCodeComparator();break;
        }

        return c;
    }

    private class PositionCodeComparator implements Comparator<V>
    {
        public int compare(V a, V b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			  ctb.append(a.getPosition(), b.getPosition());
			  return ctb.toComparison();
        }
    }
}