package net.sf.ahtutils.report.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class PivotAggregator <T extends EjbWithId, P extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(PivotAggregator.class);

    private List<SimplePivotData<T,P>> list;
    
    private Set<T> setT;
    private Set<P> setP;
    
    public PivotAggregator()
    {
    	list = new ArrayList<SimplePivotData<T,P>>();
    	setT = new HashSet<T>();
    	setP = new HashSet<P>();
    }
    
    public void add(T t, P p, double value)
    {
    	list.add(new SimplePivotData<T,P>(t,p,value));
    	if(!setT.contains(t)){setT.add(t);}
    	if(!setP.contains(t)){setP.add(p);}
    }
    
    public List<T> listT() {return new ArrayList<T>(setT);}
    public List<P> listP() {return new ArrayList<P>(setP);}
    
    public Double value(T t, P p)
    {
    	double value = 0;
    	boolean match = false;
    	for(SimplePivotData<T,P> d : list)
    	{
    		if(d.getT().equals(t) && d.getP().equals(p)){value=value+d.getValue();match=true;}
    	}
    	if(!match){return null;}
    	return value;
    }
}