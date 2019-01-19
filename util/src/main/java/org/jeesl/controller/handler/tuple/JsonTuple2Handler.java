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
import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.factory.json.db.tuple.JsonTupleFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.model.json.db.tuple.JsonTuple;
import org.jeesl.model.json.db.tuple.two.Json2Tuple;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuple2Handler <A extends EjbWithId, B extends EjbWithId>
							extends JsonTuple1Handler<A>
							implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JsonTuple2Handler.class);

	private JeeslComparatorProvider<B> jcpB; public void setComparatorProviderB(JeeslComparatorProvider<B> jppB) {this.jcpB = jppB;}
	

	private final Class<B> cB;
	protected final Set<B> setB;
	
	private int sizeB; public int getSizeB() {return sizeB;}
	private final List<B> listB; public List<B> getListB() {return listB;}
	private final Map<A,Map<B,Json2Tuple<A,B>>> map; public Map<A,Map<B,Json2Tuple<A,B>>> getMap() {return map;}
	private final List<Json2Tuple<A,B>> tuples2; public List<Json2Tuple<A,B>> getTuples2() {return tuples2;}
	
	public JsonTuple2Handler(Class<A> cA, Class<B> cB)
	{
		super(cA);
		this.cB=cB;
		
		setB = new HashSet<B>();
		listB = new ArrayList<B>();
		map = new HashMap<A,Map<B,Json2Tuple<A,B>>>();
		tuples2 = new ArrayList<Json2Tuple<A,B>>();
		
		dimension = 2;
	}
	
	public void clear()
	{
		super.clear();
		map.clear();
		setB.clear();
		listB.clear();
	}

	public void init(Json2Tuples<A,B> tuples, UtilsFacade fUtils, boolean loadA, boolean loadB)
	{
		clear();
		Set<Long> setIdA = new HashSet<>();
		Set<Long> setIdB = new HashSet<>();
		
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			setIdA.add(t.getId1());
			setIdB.add(t.getId2());
		}
		
		Map<Long,A> mapA = null;
		Map<Long,B> mapB = null; 
		
		if(loadA) {mapA = EjbIdFactory.toIdMap(fUtils.find(cA, setIdA));}
		if(loadB) {mapB = EjbIdFactory.toIdMap(fUtils.find(cB, setIdB));}
		
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			try
			{
				if(loadA){t.setEjb1(mapA.get(t.getId1()));}
				else
				{
					A a = cA.newInstance();
					a.setId(t.getId1());
					t.setEjb1(a);
				}
				
				if(loadB){t.setEjb2(mapB.get(t.getId2()));}
				else
				{
					B b = cB.newInstance();
					b.setId(t.getId2());
					t.setEjb2(b);
				}
			}
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
		}
		init(tuples);
		
	}
	public void init(Json2Tuples<A,B> tuples)
	{
		clear();
	
		for(Json2Tuple<A,B> t : tuples.getTuples())
		{
			if(t.getSum()!=null) {t.setSum(AmountRounder.two(t.getSum()/sumDivider));}
			
			setA.add(t.getEjb1());
			setB.add(t.getEjb2());
			
			if(!map.containsKey(t.getEjb1())) {map.put(t.getEjb1(), new HashMap<B,Json2Tuple<A,B>>());}
			map.get(t.getEjb1()).put(t.getEjb2(), t);
		}
	
		initA();
		initB();
		tuples2.addAll(tuples.getTuples());
	}
	
	protected void initB()
	{
		listB.addAll(setB);
		sizeB = listB.size();
		if(jcpB!=null && jcpB.provides(cB)){Collections.sort(listB, jcpB.provide(cB));}
	}
	
	public boolean contains(A a, B b) {return map.containsKey(a) && map.get(a).containsKey(b);}
	
	public JsonTuple value(A a, B b)
	{
		Json2Tuple<A,B> json = map.get(a).get(b);
		return JsonTupleFactory.build(json);
	}
}