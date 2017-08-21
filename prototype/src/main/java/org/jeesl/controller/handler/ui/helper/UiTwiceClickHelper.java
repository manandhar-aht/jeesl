package org.jeesl.controller.handler.ui.helper;

import java.io.Serializable;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class UiTwiceClickHelper implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(UiTwiceClickHelper.class);
		
	private EjbWithId a,b;
	
	private boolean showA; public boolean isShowA() {return showA;}
	private boolean showB; public boolean isShowB() {return showB;}

	public UiTwiceClickHelper()
	{
		showA = false;
		showB = false;
	}
	
	public void checkA(EjbWithId a)
	{
		if(a!=null && EjbIdFactory.isUnSaved(a)){showA=true;}
		else if(this.a==null){showA = false;}
		else if(this.a!=null && a!=null) {showA = this.a.equals(a);}
		else {showA = false;}
		this.a=a;
	}
	
	public void checkB(EjbWithId b)
	{
		if(b!=null && EjbIdFactory.isUnSaved(b)){showB=true;}
		else if(this.b==null){showB = false;}
		else if(this.b!=null && b!=null) {showB = this.b.equals(b);}
		else {showB = false;}
		this.b=b;
	}
}