package net.sf.ahtutils.report.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class SimplePivotData <T extends EjbWithId, P extends EjbWithId>
{
    final static Logger logger = LoggerFactory.getLogger(SimplePivotData.class);

    private T t;
    private P p;
    private double value;
    
    
    public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public P getP() {
		return p;
	}

	public void setP(P p) {
		this.p = p;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public SimplePivotData(T t, P p, double value)
    {
    	this.t=t;
    	this.p=p;
    	this.value=value;
    }
}
