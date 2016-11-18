package org.jeesl.report.analysis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslPivotAggregator
{
    final static Logger logger = LoggerFactory.getLogger(JeeslPivotAggregator.class);

    void add(DynamicPivotData dpd);
    List<EjbWithId> list(int index);
    Double[] values(int size, EjbWithId... selectors);
}