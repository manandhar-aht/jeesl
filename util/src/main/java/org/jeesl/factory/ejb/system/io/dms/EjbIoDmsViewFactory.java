package org.jeesl.factory.ejb.system.io.dms;

import java.util.List;

import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoDmsViewFactory <DMS extends JeeslIoDms<?,?,?,?,?>,
								 VIEW extends JeeslIoDmsView<?,DMS>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoDmsViewFactory.class);
	
	private final Class<VIEW> cView;

	public EjbIoDmsViewFactory(final Class<VIEW> cView)
	{
        this.cView = cView;
	}
 
	public VIEW build(DMS dms, List<VIEW> views)
	{
		VIEW ejb = null;
		try
		{
			ejb = cView.newInstance();
			ejb.setDms(dms);
			
			if(views!=null) {ejb.setPosition(views.size()+1);}
			else {ejb.setPosition(1);}
			
			ejb.setVisible(true);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}