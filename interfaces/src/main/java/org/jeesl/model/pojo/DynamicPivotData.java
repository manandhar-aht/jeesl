package org.jeesl.model.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class DynamicPivotData
{
    final static Logger logger = LoggerFactory.getLogger(DynamicPivotData.class);

    private int size; public int getSize() {return size;}
    
	private double value; public double getValue() {return value;}
	private double[] values; public double[] getValues() {return values;}
	private List<EjbWithId> entities;  public List<EjbWithId> getEntities() {return entities;}

	public DynamicPivotData(int size)
    {
		this.size=size;
    	entities = new ArrayList<EjbWithId>();
    }
    
    public void setData(double value, EjbWithId... entities)
    {
    	this.value=value;
    	this.entities.addAll(new ArrayList<EjbWithId>(Arrays.asList(entities)));
    }
    
    public void setData(double[] values, EjbWithId... entities)
    {
    	this.values=values;
    	this.entities.addAll(new ArrayList<EjbWithId>(Arrays.asList(entities)));
    }
    
    public EjbWithId getEntity(int index)
    {
    	return entities.get(index);
    }
    
}