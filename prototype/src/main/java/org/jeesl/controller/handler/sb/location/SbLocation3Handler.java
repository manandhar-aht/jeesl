package org.jeesl.controller.handler.sb.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.location.JeeslLocation3Cache;
import org.jeesl.controller.handler.location.HierarchicalLocationUpdateParameter;
import org.jeesl.interfaces.controller.handler.location.JeeslLocation3Store;
import org.jeesl.interfaces.controller.handler.location.JeeslLocationSelected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbLocation3Handler <L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId> extends SbLocation2Handler<L1,L2>
{
	final static Logger logger = LoggerFactory.getLogger(SbLocation3Handler.class);
	
	private final JeeslLocation3Store<L1,L2,L3> store3;
	private final JeeslLocation3Cache<L1,L2,L3> cache3;
	
	protected final List<L3> list3; public List<L3> getList3() {return list3;}
	protected final Set<L3> allow3;
	protected final Set<L3> ignore3;
	protected L3 l3; public L3 getL3(){return l3;} public void setL3(L3 l3){this.l3 = l3;}
	
	public SbLocation3Handler(JeeslLocationSelected callback, JeeslLocation3Cache<L1,L2,L3> cache3, JeeslLocation3Store<L1,L2,L3> store3)
	{
		super(callback,cache3,store3);
		this.cache3=cache3;
		this.store3=store3;
		list3 = new ArrayList<L3>();
		allow3 = new HashSet<L3>();
		ignore3 = new HashSet<L3>();
	}
	
	protected void reset3(boolean r1, boolean r2, boolean r3)
	{
		super.reset2(r1,r2);
		if(r3) {l3=null;}
	}
	
	protected void addAllowedL3(List<L3> list)
	{
		for(L3 ejb : list)
		{
			if(!allow3.contains(ejb)) {allow3.add(ejb);}
			if(!allow2.contains(getParent3(ejb)))
			{
				List<L2> upper = new ArrayList<L2>();
				upper.add(getParent3(ejb));
				addAllowedL2(upper);
			}
		}
	}
	
	
	public void ui3(L3 ejb) {select3(ejb,HierarchicalLocationUpdateParameter.build(false,true,true,true,true));}
	public void select3(L3 ejb, HierarchicalLocationUpdateParameter hup)
	{
		if(debugOnInfo) {logger.info("Select "+ejb.getClass().getSimpleName()+" "+ejb.toString()+" "+hup.toString());}
		this.l3=ejb;
		store3.setL3(ejb);
		clearL4List();
		if(hup.isFillParent()) {select2(getParent3(l3),hup.copy().selectChild(false).fireEvent(false));}
		if(hup.isFillChilds()) {fillL4List();}
		if(hup.isSelectChild()) {selectDefaultL4(hup.copy().fillParent(false).fireEvent(false));}
		if(hup.isCallback()) {selected3();}
		if(hup.isFireEvent()) {fireEvent();}
	}
	
	protected L2 getParent3(L3 l3) {return null;}
	protected void selected3() {logger.warn("This should not be caleld here, @Override this method");}
	
	//Methods required from previous level
	@Override protected void clearL3List() {list3.clear();}
	@Override protected void fillL3List()
	{
		for(L3 ejb : cache3.cacheL3(l2))
		{
			if((viewIsGlobal || allow3.contains(ejb) || list2.contains(getParent3(ejb))) && !ignore3.contains(ejb)) {list3.add(ejb);}
		}
	}
	@Override protected void selectDefaultL3(HierarchicalLocationUpdateParameter hup)
	{
		if(debugOnInfo) {logger.info("selectDefaultL3 "+hup.toString());}
		reset(3);
		if(!list3.isEmpty()) {select3(list3.get(0),hup.fillParent(false));}
	}
	
	
	//Methods for next level
	protected void clearL4List() {}
	protected void fillL4List() {}
	protected void selectDefaultL4(HierarchicalLocationUpdateParameter hlup) {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			super.debug(debug);
			StringBuilder sb = new StringBuilder();
			sb.append("\tLevel 3 ").append(list3.size()).append("elements ");
			if(l3!=null) {sb.append(l3.getClass().getSimpleName()).append(": ").append(l3.toString());}else {sb.append(": null");}
			logger.info(sb.toString());
		}
	}
}