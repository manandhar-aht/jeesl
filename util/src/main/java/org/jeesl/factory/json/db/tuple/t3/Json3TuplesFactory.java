package org.jeesl.factory.json.db.tuple.t3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.model.json.db.tuple.t3.Json3Tuple;
import org.jeesl.model.json.db.tuple.t3.Json3Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json3TuplesFactory <A extends EjbWithId, B extends EjbWithId, C extends EjbWithId>
//			extends Json2TuplesFactory<A,B>
{
	final static Logger logger = LoggerFactory.getLogger(Json3TuplesFactory.class);
	
	private final Json3TupleFactory<A,B,C> jtf;
	
	protected final Class<A> cA;
	protected final Class<B> cB;
	protected final Class<C> cC;
	
	protected final Set<Long> setId1;
	protected final Set<Long> setId2;
	protected final Set<Long> setId3;
	
	protected final Map<Long,A> mapA; public Map<Long,A> getMapA() {return mapA;}
	protected final Map<Long,B> mapB; public Map<Long,B> getMapB() {return mapB;}
	protected final Map<Long,C> mapC; public Map<Long,C> getMapC() {return mapC;}

	private Json3Tuples<A,B,C> tuples; public Json3Tuples<A,B,C> get3Tuples() {return tuples;} public void set3Tuples(Json3Tuples<A, B, C> tuples) {this.tuples = tuples;}

	public Json3TuplesFactory(Class<A> cA, Class<B> cB, Class<C> cC)
	{
//		super(cA,cB);
		this.cA=cA;
		this.cB=cB;
		this.cC=cC;
		
		setId1 = new HashSet<Long>();
		setId2 = new HashSet<Long>();
		setId3 = new HashSet<Long>();
		
		mapA = new HashMap<Long,A>();
		mapB = new HashMap<Long,B>();
		mapC = new HashMap<Long,C>();
		
		jtf = new Json3TupleFactory<A,B,C>();
	}
	
	protected void clear()
	{
		setId1.clear();
		setId2.clear();
		setId3.clear();
		
		mapA.clear();
		mapB.clear();
		mapC.clear();
	}
	
	public void init(UtilsFacade fUtils, Json3Tuples<A,B,C> json)
	{
		clear();
		this.tuples = json;
		
		for(Json3Tuple<A,B,C> t : json.getTuples())
		{
			setId1.add(t.getId1());
			setId2.add(t.getId2());
			setId3.add(t.getId3());
		}
		
		logger.info("Futils:"+(fUtils!=null));
		
		mapA.putAll(EjbIdFactory.toIdMap(fUtils.find(cA, setId1)));
		mapB.putAll(EjbIdFactory.toIdMap(fUtils.find(cB, setId2)));
		mapC.putAll(EjbIdFactory.toIdMap(fUtils.find(cC, setId3)));
	}
	
	public Json3Tuples<A,B,C> build3Sum(List<Tuple> tuples)
	{
		Json3Tuples<A,B,C> json = new Json3Tuples<A,B,C>();
		for(Tuple t : tuples)
        {
        	json.getTuples().add(jtf.buildSum(t));
        }
		return json;
	}
	
	public Json3Tuples<A,B,C> build3Count(List<Tuple> tuples)
	{
		Json3Tuples<A,B,C> json = new Json3Tuples<A,B,C>();
		for(Tuple t : tuples)
        {
        	json.getTuples().add(jtf.buildCount(t));
        }
		return json;
	} 
}