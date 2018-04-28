package org.jeesl.controller.handler.sb.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.location.JeeslLocation5Cache;
import org.jeesl.controller.handler.location.HierarchicalLocationUpdateParameter;
import org.jeesl.interfaces.controller.handler.location.JeeslLocation5Store;
import org.jeesl.interfaces.controller.handler.location.JeeslLocationSelected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbLocation5Handler <L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId, L4 extends EjbWithId, L5 extends EjbWithId> extends SbLocation4Handler<L1,L2,L3,L4>
{
	final static Logger logger = LoggerFactory.getLogger(SbLocation5Handler.class);
	
	private final JeeslLocation5Store<L1,L2,L3,L4,L5> store5;
	private final JeeslLocation5Cache<L1,L2,L3,L4,L5> cache5;
	
	protected final List<L5> list5; public List<L5> getList5() {return list5;}
	protected final Set<L5> allow5;
	protected final Set<L5> ignore5;
	protected L5 l5; public L5 getL5(){return l5;} public void setL5(L5 l5){this.l5 = l5;}
	private boolean includeAll5; public void setIncludeAlll5(boolean includeAlll5) {this.includeAll5 = includeAlll5;}
	
	public SbLocation5Handler(JeeslLocationSelected callback, JeeslLocation5Cache<L1,L2,L3,L4,L5> cache5, JeeslLocation5Store<L1,L2,L3,L4,L5> store5)
	{
		super(callback,cache5,store5);
		this.cache5=cache5;
		this.store5=store5;
		list5 = new ArrayList<L5>();
		allow5 = new HashSet<L5>();
		ignore5 = new HashSet<L5>();
		includeAll5 = false;
	}
	
	protected void reset5(boolean r1, boolean r2, boolean r3, boolean r4, boolean r5)
	{
		super.reset4(r1,r2,r3,r4);
		if(r5) {l5=null;}
	}
	
	protected void addAllowedL5(List<L5> list)
	{
		for(L5 ejb : list)
		{
			if(!allow5.contains(ejb)) {allow5.add(ejb);}
			if(!allow4.contains(getParent5(ejb)))
			{
				List<L4> upper = new ArrayList<L4>();
				upper.add(getParent5(ejb));
				addAllowedL4(upper);
			}
		}
	}
	
	public void ui5(L5 ejb) {select5(ejb,HierarchicalLocationUpdateParameter.build(false,true,true,true,true));}
	public void select5(L5 ejb, HierarchicalLocationUpdateParameter hlup)
	{
		if(debugOnInfo) {logger.info("Select "+ejb.getClass().getSimpleName()+" "+ejb.toString());}
		this.l5=ejb;
		store5.setL5(ejb);
		clearL6List();
		if(hlup.isFillParent()) {select4(getParent5(l5),hlup.copy().selectChild(false).fireEvent(false));}
		if(hlup.isFillChilds()) {fillL6List();}
		if(hlup.isSelectChild()) {selectDefaultL6(hlup.copy().fillParent(false).fireEvent(false));}
		if(hlup.isCallback()) {selected5();}
		if(hlup.isFireEvent()) {fireEvent();}
	}
	
	protected L4 getParent5(L5 l5) {return null;}
	protected void selected5() {logger.warn("This should not be caleld here, @Override this method");}
	
	//Methods required from previous level
	@Override protected void clearL5List() {list5.clear();}
	@Override protected void fillL5List()
	{
		for(L5 ejb : cache5.cacheL5(l4))
		{
			if((viewIsGlobal || includeAll5 || allow5.contains(ejb)) && !ignore5.contains(ejb)) {list5.add(ejb);}
		}
	}
	@Override protected void selectDefaultL5(HierarchicalLocationUpdateParameter hlup)
	{
		reset(5);
		if(!list5.isEmpty()) {select5(list5.get(0),hlup.fillParent(false));}
	}
	
	//Methods for next level
	protected void clearL6List() {}
	protected void fillL6List() {}
	protected void selectDefaultL6(HierarchicalLocationUpdateParameter hlup) {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			super.debug(debug);
			StringBuilder sb = new StringBuilder();
			sb.append("\tLevel 5 ").append(list5.size()).append("elements ");
			if(l5!=null) {sb.append(l5.getClass().getSimpleName()).append(": ").append(l5.toString());}else {sb.append(": null");}
			logger.info(sb.toString());
		}
	}
}