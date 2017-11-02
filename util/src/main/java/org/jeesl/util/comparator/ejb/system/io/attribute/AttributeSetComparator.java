package org.jeesl.util.comparator.ejb.system.io.attribute;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AttributeSetComparator<CATEGORY extends UtilsStatus<CATEGORY,?,?>, SET extends JeeslAttributeSet<?,?,CATEGORY,?>>
{
	final static Logger logger = LoggerFactory.getLogger(AttributeSetComparator.class);

    public enum Type {position};

    public AttributeSetComparator()
    {
    	
    }
    
    public Comparator<SET> factory(Type type)
    {
        Comparator<SET> c = null;
        AttributeSetComparator<CATEGORY,SET> factory = new AttributeSetComparator<CATEGORY,SET>();
        switch (type)
        {
            case position: c = factory.new PositionComparator();break;
        }

        return c;
    }

    private class PositionComparator implements Comparator<SET>
    {
        public int compare(SET a, SET b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			  ctb.append(a.getPosition(), b.getPosition());
			  return ctb.toComparison();
        }
    }
}