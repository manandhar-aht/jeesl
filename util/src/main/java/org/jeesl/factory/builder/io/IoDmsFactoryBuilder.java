package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsFactory;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsSectionFactory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;

public class IoDmsFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								DMS extends JeeslIoDms<L,D,AS,S>,
								AS extends JeeslAttributeSet<L,D,?,?>,
								S extends JeeslIoDmsSection<L,S>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoDmsFactoryBuilder.class);

	private final Class<DMS> cDms; public Class<DMS> getClassDms() {return cDms;}
	private final Class<AS> cAttributeSet; public Class<AS> getClassAttributeSet() {return cAttributeSet;}
	private final Class<S> cSection; public Class<S> getClassSection() {return cSection;}
    
	public IoDmsFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<DMS> cDms, final Class<AS> cAttributeSet, final Class<S> cSection)
	{
		super(cL,cD);
		this.cDms=cDms;
		this.cAttributeSet=cAttributeSet;
		this.cSection=cSection;
	}
	
	public EjbIoDmsFactory<DMS> ejbDms()
	{
		return new EjbIoDmsFactory<DMS>(cDms);
	}
	
	public EjbIoDmsSectionFactory<S> ejbSection()
	{
		return new EjbIoDmsSectionFactory<S>(cSection);
	}
}