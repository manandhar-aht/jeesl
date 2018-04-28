package org.jeesl.controller.handler.sb.location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.controller.handler.location.HierarchicalLocationUpdateParameter;
import org.jeesl.interfaces.controller.handler.location.JeeslLocation1Store;
import org.jeesl.interfaces.controller.handler.location.JeeslLocationSelected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SbLocation1Handler <L1 extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(SbLocation1Handler.class);
	
	protected final JeeslLocationSelected callback;
	private final JeeslLocation1Store<L1> store1;
	
	protected final List<L1> list1; public List<L1> getList1() {return list1;}
	protected final Set<L1> allow1;
	protected final Set<L1> ignore1;
	protected L1 l1; public L1 getL1(){return l1;} public void setL1(L1 district){this.l1 = district;}
	protected boolean debugOnInfo; public void setDebugOnInfo(boolean debugOnInfo) {this.debugOnInfo = debugOnInfo;}
	protected boolean viewIsGlobal;
	
	public SbLocation1Handler(JeeslLocationSelected callback, JeeslLocation1Store<L1> store1)
	{
		this.callback=callback;
		this.store1=store1;
		list1 = new ArrayList<L1>();
		
		allow1 = new HashSet<L1>();
		ignore1 = new HashSet<L1>();
	}
	
	protected void fireEvent() {}
	
	protected void reset1(boolean r1)
	{
		if(r1) {l1=null;}
	}
	
	protected void addAllowedL1(List<L1> list)
	{
		for(L1 p : list)
		{
			if(!allow1.contains(p)) {allow1.add(p);}
		}
	}
	
	public void ui1(L1 province) {select1(province,HierarchicalLocationUpdateParameter.build(false,true,true,true,true));}
	public void select1(L1 province, HierarchicalLocationUpdateParameter hlup)
	{
		if(debugOnInfo) {logger.info("Select "+province.getClass().getSimpleName()+" "+province.toString());}
		this.l1=province;
		store1.setL1(province);
		clearL2List();
		if(hlup.isFillParent()) {}
		if(hlup.isFillChilds()) {fillL2List();}
		if(hlup.isSelectChild()) {selectDefaultL2(hlup.copy().fillParent(false).fireEvent(false));}
		if(hlup.isCallback()) {selected1();}
		if(hlup.isFireEvent()) {fireEvent();}
	}
	
	protected void reset(int level) {logger.warn("This should not be caleld here, @Override this method");}
	protected void selected1() {logger.warn("This should not be caleld here, @Override this method");}
	
	//Methods for next level
	protected void clearL2List() {}
	protected void fillL2List() {}
	protected void selectDefaultL2(HierarchicalLocationUpdateParameter hlup) {}
	
	public void debug(boolean debug)
	{
		if(debug)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("\tLevel 1: ").append(list1.size()).append(" elements ");
			if(l1!=null) {sb.append(l1.getClass().getSimpleName()).append(" ").append(l1.toString());}else {sb.append(": null");}
			logger.info(sb.toString());
		}
	}
}