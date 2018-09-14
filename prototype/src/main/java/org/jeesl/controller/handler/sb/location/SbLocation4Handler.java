package org.jeesl.controller.handler.sb.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.location.JeeslLocation4Cache;
import org.jeesl.controller.handler.location.HierarchicalLocationUpdateParameter;
import org.jeesl.interfaces.controller.handler.location.JeeslLocation4Store;
import org.jeesl.interfaces.controller.handler.location.JeeslLocationSelected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbLocation4Handler <L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId> extends SbLocation3Handler<L1,L2,L3>
{
	final static Logger logger = LoggerFactory.getLogger(SbLocation4Handler.class);
	
	private final JeeslLocation4Store<L1,L2,L3,L4> store4;
	private final JeeslLocation4Cache<L1,L2,L3,L4> cache4;
	
	protected final List<L4> list4; public List<L4> getList4() {return list4;}
	protected final Set<L4> allow4;
	protected final Set<L4> ignore4;
	protected L4 l4; public L4 getL4(){return l4;} public void setL4(L4 l4){this.l4 = l4;}
	
	public SbLocation4Handler(JeeslLocationSelected callback, JeeslLocation4Cache<L1,L2,L3,L4> cache4, JeeslLocation4Store<L1,L2,L3,L4> store4)
	{
		super(callback,cache4,store4);
		this.cache4=cache4;
		this.store4=store4;
		list4 = new ArrayList<L4>();
		allow4 = new HashSet<L4>();
		ignore4 = new HashSet<L4>();
	}
	
	protected void reset4(boolean r1, boolean r2, boolean r3, boolean r4)
	{
		super.reset3(r1,r2,r3);
		if(r4) {l4=null;}
	}
	
	protected void addAllowedL4(List<L4> list)
	{
		for(L4 ejb : list)
		{
			if(!allow4.contains(ejb)) {allow4.add(ejb);}
			if(!allow3.contains(getParent4(ejb)))
			{
				List<L3> upper = new ArrayList<L3>();
				upper.add(getParent4(ejb));
				addAllowedL3(upper);
			}
		}
	}
	
	public void ui4(L4 ejb) {select4(ejb,HierarchicalLocationUpdateParameter.build(false,true,true,true,true));}
	public void select4(L4 ejb, HierarchicalLocationUpdateParameter hlup)
	{
		if(debugOnInfo) {logger.info("Select "+ejb.getClass().getSimpleName()+" "+ejb.toString()+" "+hlup.toString());}
		this.l4=ejb;
		store4.setL4(ejb);
		clearL5List();
		
		if(hlup.isFillParent()) {select3(getParent4(l4),hlup.copy().selectChild(false).fireEvent(false));}
		if(hlup.isFillChilds()) {fillL5List();}
		if(hlup.isSelectChild()) {selectDefaultL5(hlup.copy().fillParent(false).fireEvent(false));}
		if(hlup.isCallback()) {selected4();}
		if(hlup.isFireEvent()) {fireEvent();}
	}
	
	protected L3 getParent4(L4 l4) {return null;}
	protected void selected4() {logger.warn("This should not be caleld here, @Override this method");}
	
	//Methods required from previous level
	@Override protected void clearL4List() {list4.clear();}
	@Override protected void fillL4List()
	{
		if(debugOnInfo) {logger.info("Filling Level-4 List ");}
		for(L4 ejb : cache4.cacheL4(l3))
		{
			boolean isAllow4 = allow4.contains(ejb);
			boolean isContains3 = list3.contains(getParent4(ejb));
			boolean isNotIgnore = !ignore4.contains(ejb);
			if(debugOnInfo)
			{
				logger.info("\t"+ejb.toString());
				logger.info("\t\tviewIsGlobal:"+viewIsGlobal);
				logger.info("\t\tisAllow4:"+isAllow4);
				logger.info("\t\tisContains3:"+isContains3);
				logger.info("\t\tisNotIgnore:"+isNotIgnore);
			}
			
			if((viewIsGlobal || isAllow4 || isContains3) && isNotIgnore) {list4.add(ejb);}
		}
	}
	@Override protected void selectDefaultL4(HierarchicalLocationUpdateParameter hlup)
	{
		reset(4);
		if(!list4.isEmpty()) {select4(list4.get(0),hlup.fillParent(false));}
	}
	
	//Methods for next level
	protected void clearL5List() {}
	protected void fillL5List() {}
	protected void selectDefaultL5(HierarchicalLocationUpdateParameter hlup) {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			super.debug(debug);
			StringBuilder sb = new StringBuilder();
			sb.append("\tLevel 4 ").append(list4.size()).append("elements ");
			if(l4!=null) {sb.append(l4.getClass().getSimpleName()).append(": ").append(l4.toString());}else {sb.append(": null");}
			logger.info(sb.toString());
		}
	}
}