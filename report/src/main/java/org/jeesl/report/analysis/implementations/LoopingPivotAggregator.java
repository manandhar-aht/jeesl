package org.jeesl.report.analysis.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.jeesl.report.analysis.DynamicPivotData;
import org.jeesl.report.analysis.JeeslPivotAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class LoopingPivotAggregator implements JeeslPivotAggregator
{
    final static Logger logger = LoggerFactory.getLogger(LoopingPivotAggregator.class);

    private static boolean debug = false;
    
    private List<Set<EjbWithId>> entitySet;
    private List<DynamicPivotData> list;
    
    public LoopingPivotAggregator(int size)
    {
    	list = new ArrayList<DynamicPivotData>();
    	entitySet = new ArrayList<Set<EjbWithId>>();
    
    	
    	for(int i=0;i<size;i++)
    	{
    		entitySet.add(new HashSet<EjbWithId>());
    	}
    }
    
    @Override public void addAll(List<DynamicPivotData> dpds)
    {
    	for(DynamicPivotData dpd : dpds){add(dpd);}
    }
    
    @Override public void add(DynamicPivotData dpd)
    {
    	for(int i=0;i<dpd.getSize();i++)
    	{
    		EjbWithId entity = dpd.getEntity(i);
    		if(!entitySet.get(i).contains(entity))
    		{
    			if(debug)
    			{
    				StringBuffer sb = new StringBuffer();
    				sb.append("Adding ").append(i);
    				sb.append(" ").append(entity.getClass().getSimpleName());
    				logger.info(sb.toString());
    			}
    			
    			entitySet.get(i).add(entity);
    		}
    	}
    	list.add(dpd);
    }
    
    @Override public List<EjbWithId> list(int index)
    {
    	List<EjbWithId> list = new ArrayList<EjbWithId>();
    	for(EjbWithId ejb : entitySet.get(index))
    	{
    		if(ejb!=null){list.add(ejb);}
    	}
    	return list;
    }
      
    public Double value(EjbWithId... selectors)
    {
    	double value = 0;
    	boolean oneMatches = false;
    	for(DynamicPivotData dpd : list)
    	{
    		boolean selectorMatches = true;
    		for(int i=0;i<selectors.length;i++)
    		{
    			EjbWithId selector = selectors[i];
    			if(selector!=null)
    			{
    				if(!selector.equals(dpd.getEntity(i))){selectorMatches=false;}
    			}
    		}
    		
    		if(selectorMatches)
    		{
    			value=value+dpd.getValue();
    			oneMatches=true;
    		}
    	}
    	if(!oneMatches){return null;}
    	return value;
    }
    
    @Override public Double[] values(int size, EjbWithId... selectors)
    {
    	double[] values = new double[size]; 
    	for(int i=0;i<size;i++){values[i]=0;}

    	boolean oneMatches = false;
    	for(DynamicPivotData dpd : list)
    	{
    		boolean selectorMatches = true;
    		for(int i=0;i<selectors.length;i++)
    		{
    			EjbWithId selector = selectors[i];
    			if(selector!=null)
    			{
    				if(!selector.equals(dpd.getEntity(i))){selectorMatches=false;}
    			}
    		}
    		
    		if(selectorMatches)
    		{
    			for(int i=0;i<size;i++)
    			{
    				values[i]=values[i]+dpd.getValues()[i];
    			}
    			oneMatches=true;
    		}
    	}
    	if(!oneMatches){return null;}
    	return ArrayUtils.toObject(values);
    }
}