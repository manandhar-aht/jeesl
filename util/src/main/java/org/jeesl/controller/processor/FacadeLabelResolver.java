package org.jeesl.controller.processor;

import org.jeesl.api.bean.JeeslLabelResolver;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public class FacadeLabelResolver <RE extends JeeslRevisionEntity<?,?,?,?,RA>,
								RA extends JeeslRevisionAttribute<?,?,RE,?,?>>
							implements JeeslLabelResolver
{
	final static Logger logger = LoggerFactory.getLogger(FacadeLabelResolver.class);
	
	private final JeeslIoRevisionFacade<?,?,?,?,?,?,?,RE,?,?,?,?> fRevision;
	private final IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?> fbRevision;
	
	public FacadeLabelResolver(JeeslIoRevisionFacade<?,?,?,?,?,?,?,RE,?,?,?,?> fRevision,
			IoRevisionFactoryBuilder<?,?,?,?,?,?,?,RE,?,?,?,?> fbRevision)
	{
		this.fRevision=fRevision;
		this.fbRevision=fbRevision;
	}

	@Override public <E extends Enum<E>> String xpath(String localeCode, Class<?> c, E code)
	{
		try
		{
			RE entity = fRevision.fByCode(fbRevision.getClassEntity(), c.getName());
			entity = fRevision.load(fbRevision.getClassEntity(), entity);
			logger.info(entity.toString()+" for "+code.toString());
			for(RA ra : entity.getAttributes())
			{
				if(ra.getCode().equals(code.toString()) && ra.getXpath()!=null && ra.getXpath().trim().length()>0)
				{
					return ra.getXpath();
				}
//				logger.info("\t"+ra.toString()+" "+ra.getCode());
			}
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		logger.warn("No XPATH devfined for "+c.getSimpleName()+" and attribute:"+code.toString());
		return "@id";
	}
	
	@Override public String entity(String localeCode, Class<?> c)
	{
		try
		{
			RE entity = fRevision.fByCode(fbRevision.getClassEntity(), c.getName());
			return entity.getName().get(localeCode).getLang();
		}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
		return c.getSimpleName();
	}
}