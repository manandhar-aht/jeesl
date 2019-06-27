package org.jeesl.util.comparator.ejb.system;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.system.property.JeeslProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class PropertyComparator<L extends UtilsLang,D extends UtilsDescription,
								C extends UtilsStatus<C,L,D>,
								P extends JeeslProperty<L,D,C,P>>
{
	final static Logger logger = LoggerFactory.getLogger(PropertyComparator.class);

    public enum Type {category};
    
    public Comparator<P> factory(Type type)
    {
        Comparator<P> c = null;
        PropertyComparator<L,D,C,P> factory = new PropertyComparator<L,D,C,P>();
        switch (type)
        {
            case category: c = factory.new CategoryComparator();break;
        }

        return c;
    }

    private class CategoryComparator implements Comparator<P>
    {
        public int compare(P a, P b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  if(a.getCategory()!=null && b.getCategory()!=null){ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());}
			  ctb.append(a.getKey(), b.getKey());
			  return ctb.toComparison();
        }
    }
}