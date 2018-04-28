package org.jeesl.controller.handler.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HierarchicalLocationUpdateParameter implements Cloneable
{
	final static Logger logger = LoggerFactory.getLogger(HierarchicalLocationUpdateParameter.class);
	
	private boolean fillParent;
	public boolean isFillParent() {return fillParent;}
	public void setFillParent(boolean fillParent) {this.fillParent = fillParent;}

	private boolean fillChilds;
	public boolean isFillChilds() {return fillChilds;}
	public void setFillChilds(boolean fillChilds) {this.fillChilds = fillChilds;}
	
	private boolean selectChild;
	public boolean isSelectChild() { return selectChild;}
	public void setSelectChild(boolean selectChild) {this.selectChild = selectChild;}
	
	private boolean callback;
	public boolean isCallback() {return callback;}
	public void setCallback(boolean callback) {this.callback = callback;}
	
	public static HierarchicalLocationUpdateParameter build(boolean fillParent, boolean fillChilds, boolean selectChild, boolean callback)
	{
		HierarchicalLocationUpdateParameter hlup = new HierarchicalLocationUpdateParameter();
		hlup.setFillParent(fillParent);
		hlup.setFillChilds(fillChilds);
		hlup.setSelectChild(selectChild);
		hlup.setCallback(callback);
		return hlup;
	}
	
	public static HierarchicalLocationUpdateParameter copy(HierarchicalLocationUpdateParameter hlup)
	{
		HierarchicalLocationUpdateParameter newHlup = new HierarchicalLocationUpdateParameter();
		newHlup.setFillParent(hlup.isFillParent());
		newHlup.setFillChilds(hlup.isFillChilds());
		newHlup.setSelectChild(hlup.isSelectChild());
		newHlup.setCallback(hlup.isCallback());
		return newHlup;
	}
	
	public HierarchicalLocationUpdateParameter withFillParent(boolean value)
	{
		HierarchicalLocationUpdateParameter next = HierarchicalLocationUpdateParameter.copy(this);
		next.setFillParent(value);
		return next;
	}
	
	public HierarchicalLocationUpdateParameter withSelectChild(boolean value)
	{
		HierarchicalLocationUpdateParameter next = HierarchicalLocationUpdateParameter.copy(this);
		next.setSelectChild(false);
		return next;
	}
}