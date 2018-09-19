package org.jeesl.controller.handler.sb.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.api.bean.tree.JeeslTree2Cache;
import org.jeesl.controller.handler.tree.TreeUpdateParameter;
import org.jeesl.interfaces.controller.handler.tree.JeeslTree2Store;
import org.jeesl.interfaces.controller.handler.tree.JeeslTreeSelected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbLocation2Handler <L1 extends EjbWithId, L2 extends EjbWithId> extends SbLocation1Handler<L1>
{
	final static Logger logger = LoggerFactory.getLogger(SbLocation2Handler.class);
	
	private final JeeslTree2Store<L1,L2> store2;
	private final JeeslTree2Cache<L1,L2> cache2;
	
	protected final List<L2> list2; public List<L2> getList2() {return list2;}
	protected final Set<L2> allow2;
	protected final Set<L2> ignore2;
	protected L2 l2; public L2 getL2(){return l2;} public void setL2(L2 district){this.l2 = district;}
	
	public SbLocation2Handler(JeeslTreeSelected callback, JeeslTree2Cache<L1,L2> cache2, JeeslTree2Store<L1,L2> store2)
	{
		super(callback,store2);
		this.cache2=cache2;
		this.store2=store2;
		list2 = new ArrayList<L2>();
		allow2 = new HashSet<L2>();
		ignore2 = new HashSet<L2>();
	}
	
	protected void reset2(boolean r1, boolean r2)
	{
		super.reset1(r1);
		if(r2) {l2=null;}
	}
	
	protected void addAllowedL2(List<L2> list)
	{
		for(L2 ejb : list)
		{
			if(!allow2.contains(ejb)) {allow2.add(ejb);}
			if(!allow1.contains(getParent2(ejb)))
			{
				List<L1> upper = new ArrayList<L1>();
				upper.add(getParent2(ejb));
				addAllowedL1(upper);
			}
		}
	}
	
	public void ui2(L2 province) {select2(province,TreeUpdateParameter.build(false,true,true,true,true));}
	public void select2(L2 district, TreeUpdateParameter hup)
	{
		if(debugOnInfo) {logger.info("select2 "+district.getClass().getSimpleName()+" "+district.toString()+" "+hup.toString());}
		this.l2=district;
		store2.storeTreeLevel2(district);
		clearL3List();
		if(hup.isFillParent()) {select1(getParent2(l2),hup.copy().selectChild(false).fireEvent(false));}
		if(hup.isFillChilds()) {fillL3List();}
		if(hup.isSelectChild()) {selectDefaultL3(hup.copy().fillParent(false).fillParent(false));}
		if(hup.isCallback()) {selected2();}
		if(hup.isFireEvent()) {fireEvent();}
	}
	
	protected L1 getParent2(L2 l2) {return null;}
	protected void selected2() {logger.warn("This should not be caleld here, @Override this method");}
	
	//Methods required from previous level (clear,fill,select)
	@Override protected void clearL2List() {list2.clear();}
	@Override protected void fillL2List()
	{
		for(L2 ejb : cache2.getCachedChildsForL1(l1))
		{
			if(debugOnInfo) {logger.info("Filling Level-2 List");}
			boolean isAllow2 = allow2.contains(ejb);
			boolean isContains1 = list1.contains(getParent2(ejb));
			boolean isNotIgnore2 = !ignore2.contains(ejb);
			boolean finalL2 = (viewIsGlobal || isAllow2 || isContains1) && isNotIgnore2;
		
			if(debugOnInfo)
			{
				logger.info("\t"+ejb.toString());
				logger.info("\t\tviewIsGlobal:"+viewIsGlobal);
				logger.info("\t\tisAllow2:"+isAllow2);
				logger.info("\t\tisContains1:"+isContains1);
				logger.info("\t\tisNotIgnore2:"+isNotIgnore2);
				logger.info("\t\t\tfinalL2:"+finalL2);
			}
			
			if(finalL2) {list2.add(ejb);}
		}
	}
	@Override protected void selectDefaultL2(TreeUpdateParameter hup)
	{
		if(debugOnInfo) {logger.info("selectDefaultL2 "+hup.toString());}
		reset(2);
		if(!list2.isEmpty()) {select2(list2.get(0),hup.fillParent(false));}
	}
	
	
	//Methods for next level
	protected void clearL3List() {}
	protected void fillL3List() {}
	protected void selectDefaultL3(TreeUpdateParameter hlup) {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			super.debug(debug);
			StringBuilder sb = new StringBuilder();
			sb.append("\tLevel 2 ").append(list2.size()).append("elements ");
			if(l2!=null) {sb.append(l2.getClass().getSimpleName()).append(": ").append(l2.toString());}else {sb.append(": null");}
			logger.info(sb.toString());
		}
	}
}