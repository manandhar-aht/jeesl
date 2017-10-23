package org.jeesl.util.comparator.ejb;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.ranking.UtilsRankedResult;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class RankedResultComparator<RR extends UtilsRankedResult<T>, T extends EjbWithId> implements Comparator<RR>
{
	final static Logger logger = LoggerFactory.getLogger(RankedResultComparator.class);

	public int compare(RR a, RR b)
    {
		  CompareToBuilder ctb = new CompareToBuilder();
		  ctb.append(a.getRanking(), b.getRanking());
		  return ctb.toComparison();
    }
}