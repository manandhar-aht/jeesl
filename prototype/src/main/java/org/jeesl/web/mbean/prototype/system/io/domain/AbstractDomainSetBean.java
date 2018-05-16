package org.jeesl.web.mbean.prototype.system.io.domain;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoDomainFacade;
import org.jeesl.factory.builder.io.IoDomainFactoryBuilder;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainItem;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainPath;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainQuery;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomainSet;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractDomainSetBean <L extends UtilsLang, D extends UtilsDescription,
						DOMAIN extends JeeslDomain<L,ENTITY>,
						QUERY extends JeeslDomainQuery<L,D,DOMAIN,PATH>,
						PATH extends JeeslDomainPath<L,D,QUERY,ENTITY,ATTRIBUTE>,
						ENTITY extends JeeslRevisionEntity<L,D,?,?,ATTRIBUTE>,
						ATTRIBUTE extends JeeslRevisionAttribute<L,D,ENTITY,?,?>,
						SET extends JeeslDomainSet<L,D,DOMAIN>,
						ITEM extends JeeslDomainItem<QUERY,SET>>
					extends AbstractDomainBean<L,D,DOMAIN,QUERY,PATH,ENTITY,ATTRIBUTE,SET,ITEM>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractDomainSetBean.class);

	private List<SET> sets; public List<SET> getSets(){return sets;}
	private List<ITEM> items; public List<ITEM> getItems(){return items;}
	
	private SET set; public SET getSet() {return set;} public void setSet(SET set) {this.set = set;}
	private ITEM item; public ITEM getItem() {return item;} public void setItem(ITEM item) {this.item = item;}
	
	public AbstractDomainSetBean(IoDomainFactoryBuilder<L,D,DOMAIN,QUERY,PATH,ENTITY,ATTRIBUTE,SET,ITEM> fbDomain)
	{
		super(fbDomain);
	}
	
	protected void postConstructDomainQuery(String userLocale, JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage,
			JeeslIoDomainFacade<L,D,DOMAIN,QUERY,PATH,ENTITY,ATTRIBUTE,SET,ITEM> fDomain)
	{
		super.postConstructDomain(bTranslation,bMessage,fDomain);
		reloadSets();
	}
	
	private void reset(boolean rSet, boolean rItem)
	{
		if(rSet){}
		if(rItem){}
	}
	
	private void reloadSets()
	{
		sets = fDomain.allOrderedPosition(fbDomain.getClassDomainSet());
	}
	
	public void addSet()
	{
		logger.info(AbstractLogMessage.addEntity(fbDomain.getClassDomain()));
//		domain = efDomain.build(null,sbhDomain.getList());
//		domain.setName(efLang.createEmpty(localeCodes));
	}
	
	public void saveSet() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(set));
//		domain.setEntity(fDomain.find(fbDomain.getClassDomainEntity(),domain.getEntity()));
//		domain = fDomain.save(domain);
//		sbhDomain.setSelection(domain);
		reloadSets();
//		reloadQueries();
	}
	
	public void selectSet()
	{
		reset(false,true);
		logger.info(AbstractLogMessage.selectEntity(set));
		set = efLang.persistMissingLangs(fDomain,localeCodes,set);

		reloadItems();
	}
	
	private void reloadItems()
	{
//		items = fDomain.allForParent(fbDomain.getClassDomainQuery(), itemSet);
	}
	
	public void addItem()
	{
		logger.info(AbstractLogMessage.addEntity(fbDomain.getClassDomainQuery()));
//		query = efDomainQuery.build(domain, queries);
//		query.setName(efLang.createEmpty(localeCodes));
//		query.setDescription(efDescription.createEmpty(localeCodes));
	}
	
	public void saveItem() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(item));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		item = fDomain.save(item);
		reloadItems();
	}
	
	public void deleteItem() throws UtilsConstraintViolationException, UtilsLockingException
	{
		logger.info(AbstractLogMessage.rmEntity(item));
//		domain.setEntity(fAnalysis.find(fbAnalysis.getClassDomainEntity(),domain.getEntity()));
		fDomain.rm(item);
		reloadItems();
		reset(false,true);
	}
	
	public void selectItem()
	{
		reset(false,false);
		logger.info(AbstractLogMessage.selectEntity(item));
	}
	
	public void reorderSets() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fDomain, sets);}
	public void reorderItems() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fDomain, items);}
}