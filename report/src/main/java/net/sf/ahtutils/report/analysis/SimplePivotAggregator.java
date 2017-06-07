package net.sf.ahtutils.report.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeesl.factory.ejb.util.EjbRankedResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.ranking.UtilsRankedResult;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.util.comparator.ejb.RankedResultComparator;

public class SimplePivotAggregator <T extends EjbWithId, P extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(SimplePivotAggregator.class);

    @SuppressWarnings("unused")
	private Class<T> cT;
    private Class<P> cP;
    
    private List<SimplePivotData<T,P>> list;
    
    private Set<T> setT;
    private Set<P> setP;
    
    public SimplePivotAggregator(){this(null,null);}
    public SimplePivotAggregator(final Class<T> cT, final Class<P> cP)
    {
    	this.cT=cT;
    	this.cP=cP;
    	
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
    
    public List<UtilsRankedResult<P>> rankedP(T t)
    {
    	EjbRankedResultFactory<P> f = new EjbRankedResultFactory<P>(cP);
    	List<UtilsRankedResult<P>> ranked = new ArrayList<UtilsRankedResult<P>>();
    	for(P p : setP)
    	{
			Double value = value(t,p);
    		if(value!=null){ranked.add(f.build(value(t,p), p));}
    	}
    	
    	RankedResultComparator<UtilsRankedResult<P>,P> rrc = new RankedResultComparator<UtilsRankedResult<P>,P>();
    	Collections.sort(ranked, rrc);
    	return ranked;
    }
    
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
    
    public Double sumT(T t)
    {
    	double value = 0;
    	for(SimplePivotData<T,P> d : list)
    	{
    		if(d.getT().equals(t)){value=value+d.getValue();}
    	}
    	return value;
    }
    
    public Double sumP(P p)
    {
    	double value = 0;
    	for(SimplePivotData<T,P> d : list)
    	{
    		if(d.getP().equals(p)){value=value+d.getValue();}
    	}
    	return value;
    }
    
    public Double sum()
    {
    	double value = 0;
    	for(SimplePivotData<T,P> d : list)
    	{
    		value=value+d.getValue();
    	}
    	return value;
    }
    
    public Map<T,Double> toMapT()
    {
    	Map<T,Double> map = new HashMap<T,Double>();
    	for(T t : setT)
    	{
    		Double v = sumT(t);
    		if(v!=null){map.put(t,v);}
    	}
    	return map;
    }
    
    public Map<P,Double> toMapP()
    {
    	Map<P,Double> map = new HashMap<P,Double>();
    	for(P p : setP)
    	{
    		Double v = sumP(p);
    		if(v!=null){map.put(p,v);}
    	}
    	return map;
    }
}