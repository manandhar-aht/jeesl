package org.jeesl.controller.facade.system.io;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoSsiFacade;
import org.jeesl.factory.builder.io.IoSsiFactoryBuilder;
import org.jeesl.factory.json.db.tuple.t1.Json1TuplesFactory;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoSsiFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									SYSTEM extends JeeslIoSsiSystem,
									MAPPING extends JeeslIoSsiMapping<SYSTEM,?>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends UtilsStatus<LINK,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoSsiFacade<L,D,SYSTEM,MAPPING,DATA,LINK>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoSsiFacadeBean.class);
		
	private final IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK> fbSsi;
	
	
	public JeeslIoSsiFacadeBean(EntityManager em, IoSsiFactoryBuilder<L,D,SYSTEM,MAPPING,DATA,LINK> fbSsi)
	{
		super(em);
		this.fbSsi = fbSsi;
	}
	
	@Override public Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping)
	{
		Json1TuplesFactory<LINK> jtf = new Json1TuplesFactory<LINK>(this,fbSsi.getClassLink());
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cQ = cB.createTupleQuery();
		Root<DATA> data = cQ.from(fbSsi.getClassData());
		
		Expression<Long> cCount = cB.count(data);
		
		Path<LINK> pLink = data.get(JeeslIoSsiData.Attributes.link.toString());
		Join<DATA,MAPPING> jMapping = data.join(JeeslIoSsiData.Attributes.mapping.toString());
		
		cQ.groupBy(pLink.get("id"));
		cQ.multiselect(pLink.get("id"),cCount);
		cQ.where(cB.and(jMapping.in(mapping)));
	       
		TypedQuery<Tuple> tQ = em.createQuery(cQ);
        return jtf.buildCount(tQ.getResultList());
	}
}