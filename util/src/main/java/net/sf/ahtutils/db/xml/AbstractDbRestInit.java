package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.db.UtilsDbXmlInit.Priority;

import org.apache.commons.configuration.Configuration;
import org.jeesl.model.xml.system.io.db.Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDbRestInit extends UtilsDbXmlSeedUtil
{
	final static Logger logger = LoggerFactory.getLogger(AbstractDbRestInit.class);
	
	protected Configuration config;
	protected UtilsIdMapper idMapper;
	
	public AbstractDbRestInit(Db dbSeed, DataSource datasource, Configuration config, UtilsIdMapper idMapper)
	{
		super(dbSeed, datasource);
		this.config=config;
		this.idMapper=idMapper;
	}
	
	public void initFromXml(Priority priority) throws FileNotFoundException,UtilsConstraintViolationException, UtilsConfigurationException
	{
		switch(priority)
		{
			case statics: initStatics();break;
			case required: initRequired();break;
			case mandatory: initMandatory();break;
			case optional: initOptional();break;
			case A: initA();break;
			case B: initB();break;
			case C: initC();break;
			case D: initD();break;
			case E: initE();break;
		}
	}
	
	protected void initStatics() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initRequired() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initMandatory() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initOptional() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	
	protected void initA() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initB() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initC() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initD() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	protected void initE() throws FileNotFoundException,UtilsConstraintViolationException,UtilsConfigurationException{}
	
	public static List<Priority> allPriorities()
	{
		List<Priority> list = new ArrayList<Priority>();
		list.add(Priority.statics);
		list.add(Priority.required);
		list.add(Priority.mandatory);
		list.add(Priority.optional);
		
		list.add(Priority.A);
		list.add(Priority.B);
		list.add(Priority.C);
		list.add(Priority.D);
		list.add(Priority.E);
		return list;
	}
}