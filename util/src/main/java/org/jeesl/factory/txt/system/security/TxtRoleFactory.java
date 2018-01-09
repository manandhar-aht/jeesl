package org.jeesl.factory.txt.system.security;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class TxtRoleFactory <L extends UtilsLang,
										 D extends UtilsDescription,
										 R extends JeeslSecurityRole<L,D,?,?,?,?,?>
										 >
{
	final static Logger logger = LoggerFactory.getLogger(TxtRoleFactory.class);
    
}