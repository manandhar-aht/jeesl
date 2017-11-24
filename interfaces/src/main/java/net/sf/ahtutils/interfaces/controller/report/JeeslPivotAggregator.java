package net.sf.ahtutils.interfaces.controller.report;

import java.util.List;

import org.jeesl.model.pojo.DynamicPivotData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslPivotAggregator
{
    final static Logger logger = LoggerFactory.getLogger(JeeslPivotAggregator.class);

    void add(DynamicPivotData dpd);
    void addAll(List<DynamicPivotData> dpds);
    List<EjbWithId> list(int index);
    
    Double[] values(EjbWithId... selectors);
    Double[] values(int size, EjbWithId... selectors);
    
    int size();
}