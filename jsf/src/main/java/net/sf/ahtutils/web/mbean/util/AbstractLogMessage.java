package net.sf.ahtutils.web.mbean.util;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.monitor.ProcessingTimeTracker;

public class AbstractLogMessage <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(AbstractLogMessage.class);
	private static final long serialVersionUID = 1L;
	
	public static String postConstruct(){return "@PostConstruct";}
	
	public static String postConstruct(ProcessingTimeTracker ptt)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(postConstruct());
		sb.append(ptt(ptt));
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String postConstruct(USER user)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(postConstruct());
		sb.append(user(user));
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String postConstruct(ProcessingTimeTracker ptt, USER user)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(postConstruct());
		sb.append(ptt(ptt));
		sb.append(user(user));
		return sb.toString();
	}
	
    public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String postConstruct(USER user, long urlCode){return postConstruct(user, ""+urlCode);}
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String postConstruct(USER user, String urlCode)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("@PostConstruct");
		if(user!=null){sb.append(" {").append(user.toString()).append("}");}
		if(urlCode!=null){sb.append(" urlCode:").append(urlCode);}
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String user(USER user)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" [");
		if(user==null){sb.append("---");}
		else{sb.append(user.toString());}
		sb.append("]");
		return sb.toString();
	}
	
	public static String ptt(ProcessingTimeTracker ptt)
	{
		ptt.stop();
		StringBuffer sb = new StringBuffer();
		sb.append(" in "+ptt.toTotalTime());
		return sb.toString();
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String preDestroy(USER user)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("@PreDestroy");
		sb.append(" {").append(user.toString()).append("}");
		return sb.toString();
	}
	
	public static String addEntity(Class<?> cl){return addEntity(null,cl);}
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
		String addEntity(USER user, Class<?> cl)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Adding");
		if(user!=null){sb.append(" {").append(user.toString()).append("}");}
		sb.append(": ");
		sb.append(cl.getSimpleName());
		return sb.toString();
	}

	 public static <T extends EjbWithId> String addEntity(T t){return addEntity(null,t);}
	 public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>, T extends EjbWithId>
	 		String addEntity(USER user,T t)
	 {
		 StringBuffer sb = new StringBuffer();
		 sb.append("Adding ").append(t.getClass().getSimpleName());
		 if(user!=null){sb.append(" {").append(user.toString()).append("}");}
		 sb.append(": ").append(t.toString());
		 return sb.toString();
	 }
	 
	public static <T extends EjbWithId> String rmEntity(T t)
	{
		 return rmEntity(null,t);
	}
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>, T extends EjbWithId>
		String rmEntity(USER user, T t)
	{
		 StringBuffer sb = new StringBuffer();
		 sb.append("Removing ").append(t.getClass().getSimpleName());
		 if(user!=null){sb.append(" {").append(user.toString()).append("}");}
		 sb.append(": ").append(t.toString());
		 return sb.toString();
	}

	 // Select
	 public static <T extends EjbWithId> String selectEntity(T t)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Selecting ").append(t.getClass().getSimpleName());
        sb.append(": ").append(t.toString());
        return sb.toString();
	 }
	 public static <A extends EjbWithId,B extends EjbWithId> String selectEntity(A a, B b)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Selecting ").append(a.getClass().getSimpleName());
        sb.append(": ").append(a.toString());
        if(b!=null)
        {
        	sb.append(" ... ").append(b.toString());
        }
        return sb.toString();
	 }
	 public static <T extends EjbWithId> String selectEntities(Class<?> c, List<T> list)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Selecting ").append(c.getClass().getSimpleName());
        sb.append(": ").append(list.size());
        return sb.toString();
	 }
	 
	 public static <T extends EjbWithId> String selectOneMenuChange(T t)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Change selectOneMenu ");
        if(t!=null){sb.append(t.getClass().getSimpleName()).append(": ").append(t.toString());}
        else{sb.append("null");}
        return sb.toString();
	 }
	 public static <T extends EjbWithId> String selectOverlayPanel(T t)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Select OverlayPanel ").append(t.getClass().getSimpleName());
        sb.append(": ").append(t.toString());
        return sb.toString();
	 }
	 
	 public static <T extends EjbWithId> String addOpEntity(T t)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Adding from Overlay-Selection ").append(t.getClass().getSimpleName());
        sb.append(": ").append(t.toString());
        return sb.toString();
	 }
	 
	 public static <T extends EjbWithId> String rmOpEntity(T t)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Removing from Overlay-Selection ").append(t.getClass().getSimpleName());
        sb.append(": ").append(t.toString());
        return sb.toString();
	 }
	 
	 public static <T extends EjbWithId> String reorder(Class<T> c, List<T> list)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Reordering ");
        sb.append(" ").append(c.getSimpleName());
        if(list!=null){sb.append(" ").append(list.size()).append(" elements");}
        return sb.toString();
	}
	 
	 public static <T extends EjbWithId> String wizard(T t, String description)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Wizard ").append(t.getClass().getSimpleName());
        sb.append(" (").append(t.toString()).append(")");
        sb.append(": ").append(description);
        return sb.toString();
	 }
	 
	 //Toggle
	 public static <T extends EjbWithId> String toggle(T t)
	 {
        StringBuffer sb = new StringBuffer();
        sb.append("Toggeling ");
        if(t!=null)
        {
        	sb.append(t.getClass().getSimpleName());
        	sb.append(" ... ").append(t.toString());
        }
        return sb.toString();
	}
	public static String toggled(Class<?> c)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Toggled ");
		sb.append(c.getSimpleName());
		return sb.toString();
	}
	 
	public static <T extends EjbWithId> void infoFilter(boolean debug, Class<?> c, T... entites)
	{
		if(debug)
		{
			logger.info("Appying Filter in "+c.getSimpleName());
			for(T t : entites)
			{
				logger.info("\t"+t.getClass().getSimpleName()+" "+t.toString());
			}
		}
		 
	}

	public static <T extends EjbWithId> String saveEntity(T t){return saveEntity(null,t);}
	public static <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>, T extends EjbWithId>
		String saveEntity(USER user, T t)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(user(user));
        sb.append(" Save ").append(t.getClass().getSimpleName());
        sb.append(": ").append(t.toString());
        return sb.toString();
    }
    public static <T extends EjbWithId> String savedEntity(T t)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Saved ").append(t.getClass().getSimpleName());
        sb.append(": ").append(t.toString());
        return sb.toString();
    }
    
    public static  String autoComplete(Class<?> cl, String query, int results)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("AutoComplete");
        sb.append(" ").append(cl.getSimpleName());
        sb.append(" results:").append(results);
        sb.append(" query: ").append(query);
        return sb.toString();
    }
    
    public static <T extends EjbWithId> String autoCompleteSelect(T t)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("AutoComplete Select; ");
        sb.append(t.toString());
        return sb.toString();
    }
    
	public static <T extends EjbWithId> String reloadEntity(T t)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Reloading ").append(t.toString());
		return sb.toString();
	}
	
	public static <T extends EjbWithId> String reloaded(Class<T> c, List<T> list)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Reloaded List ");
		sb.append(c.getSimpleName());
		sb.append(" with ").append(list.size()).append(" elements");
		return sb.toString();
	}
	
	public static <T extends EjbWithId> String multiStatus(Class<T> c, List<T> all, List<T> selected)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("StatusBar ");
		sb.append(c.getSimpleName());
		sb.append(" ").append(selected.size()).append("/").append(all.size());
		return sb.toString();
	}
	
	public static String time(String msg, ProcessingTimeTracker ptt)
	{
		ptt.stop();
		StringBuffer sb = new StringBuffer();
		sb.append(msg);
		sb.append(" ").append(ptt.toTotalTime());
		return sb.toString();
	}
}
