package org.jeesl.util.comparator.ejb.system.io.attribute;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AttributeCriteriaComparator<CATEGORY extends UtilsStatus<CATEGORY,?,?>,CRITERIA extends JeeslAttributeCriteria<?,?,CATEGORY,?>>
{
	final static Logger logger = LoggerFactory.getLogger(AttributeCriteriaComparator.class);

    public enum Type {position};

    public AttributeCriteriaComparator()
    {
    	
    }
    
    public Comparator<CRITERIA> factory(Type type)
    {
        Comparator<CRITERIA> c = null;
        AttributeCriteriaComparator<CATEGORY,CRITERIA> factory = new AttributeCriteriaComparator<CATEGORY,CRITERIA>();
        switch (type)
        {
            case position: c = factory.new PositionComparator();break;
        }

        return c;
    }

    private class PositionComparator implements Comparator<CRITERIA>
    {
        public int compare(CRITERIA a, CRITERIA b)
        {
			  CompareToBuilder ctb = new CompareToBuilder();
			  ctb.append(a.getCategory().getPosition(), b.getCategory().getPosition());
			  ctb.append(a.getPosition(), b.getPosition());
			  return ctb.toComparison();
        }
    }
}