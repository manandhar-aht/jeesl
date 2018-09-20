package org.jeesl.controller.handler.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.controller.processor.finance.AmountRounder;
import org.jeesl.factory.json.db.tuple.t1.Json1TuplesFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuple1Handler <A extends EjbWithId> implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JsonTuple1Handler.class);
	
	private final Json1TuplesFactory<A> tf;
	private JeeslComparatorProvider<A> cpA; public void setComparatorProviderA(JeeslComparatorProvider<A> cpA) {this.cpA = cpA;}
	
	private final Map<A,Json1Tuple<A>> map; public Map<A,Json1Tuple<A>> getMap() {return map;}
	private final List<A> listA; public List<A> getListX() {return listA;} public List<A> getListA() {return listA;}

	private boolean withSum; public boolean isWithSum() {return withSum;} public void setWithSum(boolean withSum) {this.withSum = withSum;}

	private int sumDivider; public void setSumDivider(int sumDivider) {this.sumDivider = sumDivider;}
	public int getDimension() {return 1;}
	
	public JsonTuple1Handler(UtilsFacade fUtils, Class<A> cA)
	{
		tf = new Json1TuplesFactory<A>(fUtils,cA);
		
		listA = new ArrayList<A>();
		map = new HashMap<A,Json1Tuple<A>>();
		
		withSum = true;
		sumDivider = 1;
	}
	
	public void clear()
	{
		map.clear();
		listA.clear();
	}

	public void init(Json1Tuples<A> tuples)
	{
		clear();
	
		for(Json1Tuple<A> t : tuples.getTuples())
		{
			if(t.getSum()!=null) {t.setSum(AmountRounder.two(t.getSum()/sumDivider));}
		}
		
		listA.addAll(tf.toListA(tuples));
		
		if(cpA!=null && cpA.provides(tf.getClassA())){Collections.sort(listA, cpA.provide(tf.getClassA()));}
		
		map.putAll(tf.toMap(tuples));
	}
	
	public boolean contains(A a)
	{
		return map.containsKey(a);
	}
}