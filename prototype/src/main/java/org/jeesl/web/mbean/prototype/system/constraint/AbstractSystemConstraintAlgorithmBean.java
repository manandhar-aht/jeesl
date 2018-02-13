package org.jeesl.web.mbean.prototype.system.constraint;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.factory.builder.system.ConstraintFactoryBuilder;
import org.jeesl.factory.ejb.system.constraint.algorithm.EjbConstraintAlgorithmFactory;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractSystemConstraintAlgorithmBean <L extends UtilsLang, D extends UtilsDescription,
														ALGCAT extends UtilsStatus<ALGCAT,L,D>,
														ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
														SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
														CONCAT extends UtilsStatus<CONCAT,L,D>,
														CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
														LEVEL extends UtilsStatus<LEVEL,L,D>,
														TYPE extends UtilsStatus<TYPE,L,D>,
														RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
					extends AbstractSystemConstraintBean<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>
					implements Serializable//,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSystemConstraintAlgorithmBean.class);
	
	protected final SbMultiHandler<ALGCAT> sbhAlgorithmCategory; public SbMultiHandler<ALGCAT> getSbhAlgorithmCategory() {return sbhAlgorithmCategory;}
	
	protected final EjbConstraintAlgorithmFactory<L,D,ALGCAT,ALGO> efAlgorithm;
	
	private List<ALGO> algorithms; public List<ALGO> getAlgorithms() {return algorithms;}

	private ALGO algorithm; public ALGO getAlgorithm() {return algorithm;} public void setAlgorithm(ALGO algorithm) {this.algorithm = algorithm;}

	
	public AbstractSystemConstraintAlgorithmBean(ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint)
	{
		super(fbConstraint);
		efAlgorithm = fbConstraint.algorithm();
	
		sbhAlgorithmCategory = new SbMultiHandler<ALGCAT>(fbConstraint.getClassAlgorithmCategory(),this);
	}
	
	protected void initConstraintAlgorithms(JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage, JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint)
	{
		super.initConstraint(bTranslation,bMessage,fConstraint);
		initImplementationSettings();
		reloadAlgorithms();
	}
	
	protected abstract void initImplementationSettings();
	
	public void toggled(Class<?> c)
	{
		logger.info(AbstractLogMessage.toggled(c));
//		if(fbAttribute.getClassCategory().isAssignableFrom(c))
		{
			reloadAlgorithms();
		}
//		sbhCategory.debug(true);
	}
	
	public void resetAlgorithm() {reset(true);}
	private void reset(boolean rAlgorithm)
	{
		if(rAlgorithm) {algorithm=null;}
	}
	
	private void reloadAlgorithms()
	{
		algorithms = fConstraint.allForParents(fbConstraint.getClassAlgorithm(), sbhAlgorithmCategory.getSelected());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbConstraint.getClassAlgorithm(),algorithms));}
	}
	
	public void addAlgorithm()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbConstraint.getClassAlgorithm()));}
		algorithm = efAlgorithm.build(null,algorithms);
		algorithm.setName(efLang.createEmpty(localeCodes));
		algorithm.setDescription(efDescription.createEmpty(localeCodes));
		reset(false);
	}
	
	public void saveAlgorithm() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(algorithm));}
		algorithm.setCategory(fConstraint.find(fbConstraint.getClassAlgorithmCategory(),algorithm.getCategory()));
		algorithm = fConstraint.save(algorithm);
		reloadAlgorithms();
	}
	
	public void selectAlgorithm()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(algorithm));}
		algorithm = efLang.persistMissingLangs(fConstraint,localeCodes,algorithm);
		algorithm = efDescription.persistMissingLangs(fConstraint,localeCodes,algorithm);
		reloadAlgorithms();
		reset(false);
	}
	
	public void deleteAlgorithm() throws UtilsConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(algorithm));}
		fConstraint.rm(algorithm);
		reloadAlgorithms();
		reset(true);
	}
	
	protected void reorderAlgorithms() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fConstraint, fbConstraint.getClassAlgorithm(),algorithms);Collections.sort(algorithms,cpAlgorithm);}
}