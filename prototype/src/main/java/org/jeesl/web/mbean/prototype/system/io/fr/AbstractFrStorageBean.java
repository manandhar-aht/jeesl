package org.jeesl.web.mbean.prototype.system.io.fr;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.facade.io.JeeslIoFrFacade;
import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class AbstractFrStorageBean <L extends UtilsLang, D extends UtilsDescription,
										STORAGE extends JeeslFileStorage<L,D,ENGINE>,
										ENGINE extends UtilsStatus<ENGINE,L,D>
										>
						implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractFrStorageBean.class);
	
	private JeeslIoFrFacade<L,D,STORAGE,ENGINE> fFr;
	private final IoFileRepositoryFactoryBuilder<L,D,STORAGE,ENGINE> fbFr;
	
	private List<STORAGE> storages; public List<STORAGE> getStorages() {return storages;}
	private List<ENGINE> engines; public List<ENGINE> getEngines() {return engines;}
	
	private STORAGE storage; public STORAGE getStorage() {return storage;} public void setStorage(STORAGE storage) {this.storage = storage;}

	public AbstractFrStorageBean(final IoFileRepositoryFactoryBuilder<L,D,STORAGE,ENGINE> fbFr)
	{
		this.fbFr=fbFr;
	}
	
	public void initStorage(JeeslIoFrFacade<L,D,STORAGE,ENGINE> fFr, JeeslTranslationBean bTranslation, FacesMessageBean bMessage)
	{
		this.fFr=fFr;
		reloadStorages();
		engines = fFr.allOrderedPositionVisible(fbFr.getClassEngine());
	}
	
	private void reloadStorages()
	{
		storages = fFr.all(fbFr.getClassStorage());
	}
}