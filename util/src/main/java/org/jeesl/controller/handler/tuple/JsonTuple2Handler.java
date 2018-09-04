package org.jeesl.controller.handler.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.controller.processor.finance.AmountRounder;
import org.jeesl.factory.json.db.tuple.JsonTupleFactory;
import org.jeesl.factory.json.db.tuple.t2.Json2TuplesFactory;
import org.jeesl.interfaces.controller.report.JeeslComparatorProvider;
import org.jeesl.model.json.db.tuple.JsonTuple;
import org.jeesl.model.json.db.tuple.two.Json2Tuple;
import org.jeesl.model.json.db.tuple.two.Json2Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuple2Handler <X extends EjbWithId, Y extends EjbWithId> implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JsonTuple2Handler.class);
	
	private final Json2TuplesFactory<X,Y> tf;
	
	private JeeslComparatorProvider<X> cpA; public void setComparatorProviderA(JeeslComparatorProvider<X> cpA) {this.cpA = cpA;}
	private JeeslComparatorProvider<Y> cpB; public void setComparatorProviderB(JeeslComparatorProvider<Y> cpB) {this.cpB = cpB;}
	
	
	private final Map<X,Map<Y,Json2Tuple<X,Y>>> map; public Map<X,Map<Y,Json2Tuple<X,Y>>> getMap() {return map;}
	private final List<X> listX; public List<X> getListA() {return listX;}
	private final List<Y> listY; public List<Y> getListB() {return listY;}

	private boolean withSum; public boolean isWithSum() {return withSum;} public void setWithSum(boolean withSum) {this.withSum = withSum;}

	private int sumDivider; public void setSumDivider(int sumDivider) {this.sumDivider = sumDivider;}
	public int getDimension() {return 2;}
	
	public JsonTuple2Handler(UtilsFacade fUtils, Class<X> cX, Class<Y> cY)
	{
		tf = new Json2TuplesFactory<X,Y>(fUtils,cX,cY);
		
		listX = new ArrayList<X>();
		listY = new ArrayList<Y>();
		map = new HashMap<X,Map<Y,Json2Tuple<X,Y>>>();
		
		withSum = true;
		sumDivider = 1;
	}
	
	public void clear()
	{
		map.clear();
		listX.clear();
		listY.clear();
	}

	public void init(Json2Tuples<X,Y> tuples)
	{
		clear();
	
		for(Json2Tuple<X,Y> t : tuples.getTuples())
		{
			if(t.getSum()!=null) {t.setSum(AmountRounder.two(t.getSum()/sumDivider));}
		}
		
		listX.addAll(tf.toListX(tuples));
		listY.addAll(tf.toListY(tuples));
		map.putAll(tf.toMap(tuples));
		
		
		if(cpA!=null && cpA.provides(tf.getClassA()))
		{
			
			Collections.sort(listX, cpA.provide(tf.getClassA()));
		}
		if(cpB!=null && cpB.provides(tf.getClassB())) {Collections.sort(listY, cpB.provide(tf.getClassB()));}
	}
	
	public boolean contains(X x, Y y)
	{
		return map.containsKey(x) && map.get(x).containsKey(y);
	}
	
	public JsonTuple value(X x, Y y)
	{
		Json2Tuple<X,Y> j2t = map.get(x).get(y);
		return JsonTupleFactory.build(j2t);
	}
}