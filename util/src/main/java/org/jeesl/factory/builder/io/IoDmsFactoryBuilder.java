package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsFactory;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsFileFactory;
import org.jeesl.factory.ejb.system.io.dms.EjbIoDmsSectionFactory;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDms;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsFile;
import org.jeesl.interfaces.model.system.io.dms.JeeslIoDmsSection;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoDmsFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
								DMS extends JeeslIoDms<L,D,STORAGE,?,SECTION>,
								STORAGE extends JeeslFileStorage<L,D,?>,
								SECTION extends JeeslIoDmsSection<L,D,SECTION>,
								FILE extends JeeslIoDmsFile<L,SECTION,?,?>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoDmsFactoryBuilder.class);

	private final Class<LOC> cLoc; public Class<LOC> getClassLocale() {return cLoc;}
	private final Class<DMS> cDms; public Class<DMS> getClassDms() {return cDms;}
	private final Class<STORAGE> cStorage; public Class<STORAGE> getClassStorage() {return cStorage;}
	private final Class<SECTION> cSection; public Class<SECTION> getClassSection() {return cSection;}
	private final Class<FILE> cFile; public Class<FILE> getClassFile() {return cFile;}
    
	public IoDmsFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<LOC> cLoc, final Class<DMS> cDms, final Class<STORAGE> cStorage, final Class<SECTION> cSection, final Class<FILE> cFile)
	{
		super(cL,cD);
		this.cLoc=cLoc;
		this.cDms=cDms;
		this.cStorage=cStorage;

		this.cSection=cSection;
		this.cFile=cFile;
	}
	
	public EjbIoDmsFactory<DMS> ejbDms()
	{
		return new EjbIoDmsFactory<DMS>(cDms);
	}
	
	public EjbIoDmsSectionFactory<SECTION> ejbSection()
	{
		return new EjbIoDmsSectionFactory<SECTION>(cSection);
	}
	
	public EjbIoDmsFileFactory<SECTION,FILE> ejbFile()
	{
		return new EjbIoDmsFileFactory<SECTION,FILE>(cFile);
	}
}