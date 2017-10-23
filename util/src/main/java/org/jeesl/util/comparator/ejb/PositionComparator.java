package org.jeesl.util.comparator.ejb;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;

public class PositionComparator<T extends EjbWithPosition> implements Comparator<T>
{
	final static Logger logger = LoggerFactory.getLogger(PositionComparator.class);

	public int compare(T a, T b)
    {
		  CompareToBuilder ctb = new CompareToBuilder();
		  ctb.append(a.getPosition(), b.getPosition());
		  return ctb.toComparison();
    }
}