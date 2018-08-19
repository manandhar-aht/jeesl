//package org.jeesl.factory.xml.module.ts;
//
//import net.sf.ahtutils.model.interfaces.with.EjbWithId;
//import net.sf.ahtutils.xml.aht.Query;
//import net.sf.ahtutils.xml.security.User;
//import org.jeesl.factory.xml.system.security.XmlUserFactory;
//import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
//import org.jeesl.model.xml.module.ts.Transaction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class XmlTransactionFactory<TRANSACTION extends JeeslTsTransaction<?,?,?>, USER extends EjbWithId>
//{
//	final static Logger logger = LoggerFactory.getLogger(XmlTransactionFactory.class);
//
//	Transaction q;
//	Query query;
//
//	public XmlTransactionFactory(Query query)
//	{
//		this(query.getTransaction());
//		this.query = query;
//	}
//	public XmlTransactionFactory(Transaction q)
//	{
//		this.q=q;
//	}
//
//	public static Transaction build() { return new Transaction(); }
//
//	public Transaction build(TRANSACTION ejb)
//	{
//		Transaction xml = new Transaction();
//
//		if(q.isSetUser()) {xml.setUser(buildUser(ejb.getUser()));}
//
//		return xml;
//	}
//
//	private User buildUser(USER ejb) {
//			XmlUserFactory xuf = new XmlUserFactory(query.getTransaction().getUser());
//
//			return xuf.build(ejb);
//	}
//}