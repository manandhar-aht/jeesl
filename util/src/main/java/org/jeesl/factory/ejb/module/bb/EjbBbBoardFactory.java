package org.jeesl.factory.ejb.module.bb;

import org.jeesl.interfaces.model.module.bb.JeeslBb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbBbBoardFactory<L extends UtilsLang,D extends UtilsDescription,
								SCOPE extends UtilsStatus<SCOPE,L,D>,
								BB extends JeeslBb<L,D,SCOPE,BB,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbBbBoardFactory.class);
	
	private final Class<BB> cBb;
	
    public EjbBbBoardFactory(final Class<BB> cBb)
    {
        this.cBb = cBb;
    }
	
	public BB build(BB parent, SCOPE scope, long refId)
	{
		try
		{
			BB ejb = cBb.newInstance();
			ejb.setParent(parent);
			ejb.setScope(scope);
			ejb.setRefId(refId);
		    return ejb;
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return null;
    }
	
	public void update(BB src, BB dst)
	{
		dst.setParent(src.getParent());
		dst.setPosition(src.getPosition());
//		dst.setName(src.getName());
	}
}