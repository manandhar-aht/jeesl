package org.jeesl.web.mbean.prototype.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public abstract class AbstractSessionRegistryBean <V extends JeeslSecurityView<?,?,?,?,?,?>,
											
											USER extends JeeslUser<?>>
				implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSessionRegistryBean.class);
	
	private final Set<String> setSession;
	private final Map<String,USER> mapUser;
	private List<USER> users; public List<USER> getUsers() {return users;}

	public AbstractSessionRegistryBean()
	{
		setSession = new HashSet<>();
		mapUser = new HashMap<>();
	}
	
	@PostConstruct
	public void init() throws UtilsNotFoundException
	{
		
	}
	
	public void add(String sessionId)
	{
		setSession.add(sessionId);
	}
	
	public void add(String sessionId, USER user)
	{
		mapUser.put(sessionId,user);
		users = new ArrayList<>(mapUser.values());
	}
	
	public void rm(String sessionId)
	{
		if(setSession.contains(sessionId)) {setSession.remove(sessionId);}
		if(mapUser.containsKey(sessionId)) {mapUser.remove(sessionId);}
		users = new ArrayList<>(mapUser.values());
	}
}