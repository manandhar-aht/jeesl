package org.jeesl.controller.handler.sb.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.tree.JeeslTree3Cache;
import org.jeesl.controller.handler.tree.TreeUpdateParameter;
import org.jeesl.interfaces.controller.handler.tree.JeeslTree3Store;
import org.jeesl.interfaces.controller.handler.tree.JeeslTreeSelected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbLocation3Handler <L1 extends EjbWithId, L2 extends EjbWithId, L3 extends EjbWithId> extends SbLocation2Handler<L1,L2>
{
	final static Logger logger = LoggerFactory.getLogger(SbLocation3Handler.class);
	
	private final JeeslTree3Store<L1,L2,L3> store3;
	private final JeeslTree3Cache<L1,L2,L3> cache3;
	
	protected final List<L3> list3; public List<L3> getList3() {return list3;}
	protected final Set<L3> allow3;
	protected final Set<L3> ignore3;
	protected L3 l3; public L3 getL3(){return l3;} public void setL3(L3 l3){this.l3 = l3;}
	
	public SbLocation3Handler(JeeslTreeSelected callback, JeeslTree3Cache<L1,L2,L3> cache3, JeeslTree3Store<L1,L2,L3> store3)
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
			if(!allow2.contains(getParentOf3(ejb)))
			{
				List<L2> upper = new ArrayList<L2>();
				upper.add(getParentOf3(ejb));
				addAllowedL2(upper);
			}
		}
	}
	
	public void ui3(L3 ejb) {select3(ejb,TreeUpdateParameter.build(false,true,true,true,true));}
	public void select3(L3 ejb, TreeUpdateParameter hup)
	{
		if(debugOnInfo) {logger.info("Select "+ejb.getClass().getSimpleName()+" "+ejb.toString()+" "+hup.toString());}
		this.l3=ejb;
		store3.storeTreeLevel3(ejb);
		clearL4List();
		if(hup.isFillParent()) {select2(getParentOf3(l3),hup.copy().selectChild(false).fireEvent(false));}
		if(hup.isFillChilds()) {fillL4List();}
		if(hup.isSelectChild()) {selectDefaultL4(hup.copy().fillParent(false).fireEvent(false));}
		if(hup.isCallback()) {selected3();}
		if(hup.isFireEvent()) {fireEvent();}
	}
	
	protected L2 getParentOf3(L3 l3) {return null;}
	protected void selected3() {logger.warn("This should not be caleld here, @Override this method");}
	
	//Methods required from previous level
	@Override protected void clearL3List() {list3.clear();}
	@Override protected void fillL3List()
	{
		for(L3 ejb : cache3.getCachedChildsForL2(l2))
		{
			if(debugOnInfo) {logger.info("Filling Level-3 List");}
			
			boolean isAllow3 = allow3.contains(ejb);
			boolean isContains2 = list2.contains(getParentOf3(ejb));
			boolean isNotIgnore3 = !ignore3.contains(ejb);
			boolean finalL3 = (viewIsGlobal || isAllow3 || isContains2) && isNotIgnore3;
			if(debugOnInfo)
			{
				logger.info("\t"+ejb.toString());
				logger.info("\t\tviewIsGlobal:"+viewIsGlobal);
				logger.info("\t\tisAllow3:"+isAllow3);
				logger.info("\t\tisContains2:"+isContains2);
				logger.info("\t\tisNotIgnore3:"+isNotIgnore3);
				logger.info("\t\t\tfinalL3:"+finalL3);
			}
			
			if(finalL3) {list3.add(ejb);}
		}
	}
	@Override protected void selectDefaultL3(TreeUpdateParameter hup)
	{
		if(debugOnInfo) {logger.info("selectDefaultL3 "+hup.toString());}
		reset(3);
		if(!list3.isEmpty()) {select3(list3.get(0),hup.fillParent(false));}
	}
	
	//Methods for next level
	protected void clearL4List() {}
	protected void fillL4List() {}
	protected void selectDefaultL4(TreeUpdateParameter hlup) {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			super.debug(debug);
			StringBuilder sb = new StringBuilder();
			sb.append("\tLevel 3 with ").append(list3.size()).append(" elements ");
			if(l3!=null) {sb.append(l3.getClass().getSimpleName()).append(": ").append(l3.toString());}else {sb.append(": null");}
			logger.info(sb.toString());
			
			sb = new StringBuilder();
			sb.append("\t\tAllow3: ");
			for(L3 l : allow3){sb.append(l.toString()+" ");}
			logger.info(sb.toString());
		}
	}
}