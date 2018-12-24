package org.jeesl.controller.handler.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jeesl.controller.processor.finance.AmountRounder;
import org.jeesl.factory.json.db.tuple.JsonTupleFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.model.json.db.tuple.JsonTuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuple1Handler <A extends EjbWithId> extends JsonTupleHandler implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JsonTuple1Handler.class);
	
	private JeeslComparatorProvider<A> jcpA; public void setComparatorProviderA(JeeslComparatorProvider<A> jcpA) {this.jcpA = jcpA;}
	
	private final Class<A> cA;
	protected final Set<A> setA;
	private int sizeA; public int getSizeA() {return sizeA;}
	private final List<A> listA; public List<A> getListA() {return listA;}
	private final Map<A,Json1Tuple<A>> map1; public Map<A,Json1Tuple<A>> getMap1() {return map1;}

	public JsonTuple1Handler(Class<A> cA)
	{
		this.cA=cA;
		
		setA = new HashSet<A>();
		listA = new ArrayList<A>();
		map1 = new HashMap<A,Json1Tuple<A>>();
		
		dimension = 1;
	}
	
	public void clear()
	{
		setA.clear();
		listA.clear();
		map1.clear();
	}

	public void init(Json1Tuples<A> tuples)
	{
		clear();
		for(Json1Tuple<A> t : tuples.getTuples())
		{
			if(t.getSum()!=null) {t.setSum(AmountRounder.two(t.getSum()/sumDivider));}
			
			setA.add(t.getEjb());
			if(!map1.containsKey(t.getEjb())) {map1.put(t.getEjb(), t);}
		}
		initA();
	}
	
	protected void initA()
	{
		listA.addAll(setA);
		sizeA = listA.size();
		if(jcpA!=null && jcpA.provides(cA)){Collections.sort(listA, jcpA.provide(cA));}
	}
	
	public boolean contains(A a){return map1.containsKey(a);}
	public JsonTuple value(A a)
	{
		Json1Tuple<A> json = map1.get(a);
		return JsonTupleFactory.build(json);
	}
}