package net.sf.ahtutils.report.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class DynamicPivotAggregator
{
    final static Logger logger = LoggerFactory.getLogger(DynamicPivotAggregator.class);

    private static boolean debug = false;
    
    private List<Set<EjbWithId>> entitySet;
    private List<DynamicPivotData> list;
    
    public DynamicPivotAggregator(int size)
    {
    	list = new ArrayList<DynamicPivotData>();
    	entitySet = new ArrayList<Set<EjbWithId>>();
    
    	
    	for(int i=0;i<size;i++)
    	{
    		entitySet.add(new HashSet<EjbWithId>());
    	}
    }
    
    public void add(DynamicPivotData dpd)
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
    
    public List<EjbWithId> list(int index)
    {
    	return new ArrayList<EjbWithId>(entitySet.get(index));
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
    
}