package org.jeesl.factory.ejb.system.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityMenu;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.util.comparator.ejb.PositionComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class EjbSecurityMenuFactory <L extends UtilsLang, D extends UtilsDescription,
										 C extends JeeslSecurityCategory<L,D>,
										 R extends JeeslSecurityRole<L,D,C,R,V,U,A,AT,USER>,
										 V extends JeeslSecurityView<L,D,C,R,U,A>,
										 U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
										 A extends JeeslSecurityAction<L,D,C,R,V,U,A,AT,USER>,
										 AT extends JeeslSecurityTemplate<L,D,C,R,V,U,A,AT,USER>,
										 M extends JeeslSecurityMenu<V,M>,
										 USER extends JeeslUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSecurityMenuFactory.class);
	
	private final Class<M> cM;
	private final Comparator<M> comparator;
    
    public EjbSecurityMenuFactory(final Class<M> cM)
    {
		this.cM = cM;
		comparator = new PositionComparator<M>();
    } 
    
    
    public M build()
    {
    	M ejb = null;
    	
    	try
    	{
			ejb = cM.newInstance();
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
    
    public M create(V view)
    {
		M ejb = null;
    	
    	try
    	{
			ejb = cM.newInstance();
			ejb.setView(view);
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
    
    public Map<V,M> toMapView(List<M> list)
    {
		Map<V,M> map = new HashMap<V,M>();
		for(M m : list) {map.put(m.getView(),m);}
		return map;
    }
    
    public List<M> toListRoot(List<M> list)
    {
    		List<M> result = new ArrayList<M>();
    		for(M m : list) {if(m.getParent()==null) {result.add(m);}}
    		Collections.sort(result,comparator);
    		return result;
    }
    
    public Map<M,List<M>> toMapParent(List<M> list)
	{
    		Map<M,List<M>> map = new HashMap<M,List<M>>();
    		for(M m : list)
    		{
    			if(m.getParent()!=null)
    			{
    				if(!map.containsKey(m.getParent())) {map.put(m.getParent(), new ArrayList<M>());}
    				map.get(m.getParent()).add(m);
    			}
    		}
    		for(M m : map.keySet())
    		{
    			Collections.sort(map.get(m),comparator);
    		}
    		return map;
    }
}