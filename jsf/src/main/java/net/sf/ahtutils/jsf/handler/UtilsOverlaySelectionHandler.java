package net.sf.ahtutils.jsf.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.web.OverlaySelectionBean;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UtilsOverlaySelectionHandler <T extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(UtilsOverlaySelectionHandler.class);
	
	public static final long serialVersionUID=1;

    private OverlaySelectionBean bean;

    private T selection;
	public T getSelection() {return selection;}
	public void setSelection(T selection) {this.selection = selection;}

	//All available entities for Selection
	private List<T> available;
	public List<T> getAvailable() {return available;}
	public void setAvailable(List<T> available) {this.available = available;}

	private List<T> subset;
	public List<T> getSubset() {return subset;}
	public void setSubset(List<T> subset) {this.subset = subset;}
	
	public UtilsOverlaySelectionHandler(OverlaySelectionBean bean)
    {
		this.bean=bean;
		subset = new ArrayList<T>();
    }
	
    public void selectItem() throws UtilsLockingException, UtilsConstraintViolationException
    {
    	logger.warn("selectUi");
    }
    
    public void addItems(List<T> list)
    {
    	for(T t : list){addItem(t);}
    }
    public void addItem(T t)
    {
    	if(!subset.contains(t)){subset.add(t);}
    }
    
    public void addItem() throws UtilsLockingException, UtilsConstraintViolationException
    {
    	jsfErrors();
    	bean.opCallbackAdd(selection);
    	if(!subset.contains(selection)){subset.add(selection);}
    	selection=null;
    }
        
    public void removeItem() throws UtilsLockingException, UtilsConstraintViolationException
    {
    	jsfErrors();
    	logger.warn("removeListener");
    	if(subset.contains(selection)){subset.remove(selection);}
    	bean.opCallbackRemove(selection);
    	selection=null;
    }
    
    private void jsfErrors()
    {
    	if(selection==null){logger.warn("The selection parameter is null, is the JSF component inside a form?");}
    }
    
    public void clear()
    {
    	selection=null;
    	if(subset!=null){subset.clear();}
    }
}