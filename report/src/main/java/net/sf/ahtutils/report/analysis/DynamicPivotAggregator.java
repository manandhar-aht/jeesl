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

  
    private Set<EjbWithId> setA,setB,setC;
    
    public DynamicPivotAggregator()
    {
    	setA = new HashSet<EjbWithId>();
    	setB = new HashSet<EjbWithId>();
    	setC = new HashSet<EjbWithId>();
    }
    
    public void add(double value, EjbWithId a, EjbWithId b)
    {
    	
    }
    
    public List<EjbWithId> listA() {return new ArrayList<EjbWithId>(setA);}
    public List<EjbWithId> listB() {return new ArrayList<EjbWithId>(setB);}
      
    public Double value()
    {
    	return null;
    }
    
}