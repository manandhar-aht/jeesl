package org.jeesl.controller.handler.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.controller.processor.finance.AmountRounder;
import org.jeesl.factory.json.db.tuple.t1.Json1TuplesFactory;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonTuple1Handler <X extends EjbWithId> implements Serializable
{
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JsonTuple1Handler.class);
	
	private final Json1TuplesFactory<X> tf;
	
	private final Map<X,Json1Tuple<X>> map; public Map<X,Json1Tuple<X>> getMap() {return map;}
	private final List<X> listX; public List<X> getListX() {return listX;}

	private boolean withSum; public boolean isWithSum() {return withSum;} public void setWithSum(boolean withSum) {this.withSum = withSum;}

	private int sumDivider; public void setSumDivider(int sumDivider) {this.sumDivider = sumDivider;}
	public int getDimension() {return 1;}
	
	public JsonTuple1Handler(UtilsFacade fUtils, Class<X> cX)
	{
		tf = new Json1TuplesFactory<X>(fUtils,cX);
		
		listX = new ArrayList<X>();
		map = new HashMap<X,Json1Tuple<X>>();
		
		withSum = true;
		sumDivider = 1;
	}

	public void init(Json1Tuples<X> tuples)
	{
		map.clear();
		listX.clear();
	
		for(Json1Tuple<X> t : tuples.getTuples())
		{
			t.setSum(AmountRounder.two(t.getSum()/sumDivider));
		}
		
		listX.addAll(tf.toListA(tuples));
		map.putAll(tf.toMap(tuples));
	}
	
	public boolean contains(X x)
	{
		return map.containsKey(x);
	}
}